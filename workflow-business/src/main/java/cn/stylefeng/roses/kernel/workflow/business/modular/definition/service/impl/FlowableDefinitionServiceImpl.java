package cn.stylefeng.roses.kernel.workflow.business.modular.definition.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.demo.exception.DemoException;
import cn.stylefeng.roses.kernel.demo.exception.enums.DemoExceptionEnum;
import cn.stylefeng.roses.kernel.demo.expander.DemoConfigExpander;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableDefinitionExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.modular.button.service.FlowableButtonService;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.factory.FlowableDefinitionFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.param.FlowableDefinitionParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.result.FlowableDefinitionResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.result.FlowableUserTaskResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.service.FlowableDefinitionService;
import cn.stylefeng.roses.kernel.workflow.business.modular.event.service.FlowableEventService;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.service.FlowableFormService;
import cn.stylefeng.roses.kernel.workflow.business.modular.option.service.FlowableOptionService;
import cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.service.FlowableShortcutService;
import cn.stylefeng.roses.kernel.workflow.business.modular.user.factory.FlowableUserFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.flowable.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.service.exception.NotFoundException;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.service.BpmnDisplayJsonConverter;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ????????????service???????????????
 *
 * @author fengshuonan
 * @date 2020/4/14 19:26
 */
@Service
public class FlowableDefinitionServiceImpl implements FlowableDefinitionService {

    /**
     * ??????????????????
     */
    private static final String MODEL_BPMN_SUFFIX = ".bpmn20.xml";

    /**
     * ?????????????????????????????????
     */
    private static final String INITIATOR = "INITIATOR";

    /**
     * ?????????????????????????????????????????????
     */
    private static final String SYMBOL_INITIATOR = "$INITIATOR";

    /**
     * ??????????????????????????????????????????????????????????????????
     */
    private static final String DOUBLE_SYMBOL_INITIATOR = "${INITIATOR}";

    private static final Log log = Log.get();

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ModelService modelService;

    @Resource
    private BpmnDisplayJsonConverter bpmnDisplayJsonConverter;

    @Resource
    private FlowableOptionService flowableOptionService;

    @Resource
    private FlowableButtonService flowableButtonService;

    @Resource
    private FlowableEventService flowableEventService;

    @Resource
    private FlowableShortcutService flowableShortcutService;

    @Resource(name = "gunsFlowableFormService")
    private FlowableFormService flowableFormService;

    @Override
    public PageResult<FlowableDefinitionResult> page(FlowableDefinitionParam flowableDefinitionParam) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        if (ObjectUtil.isNotNull(flowableDefinitionParam)) {
            //??????key????????????
            if (ObjectUtil.isNotEmpty(flowableDefinitionParam.getKey())) {
                processDefinitionQuery.processDefinitionKeyLike(flowableDefinitionParam.getKey());
            }
            //????????????????????????
            if (ObjectUtil.isNotEmpty(flowableDefinitionParam.getName())) {
                processDefinitionQuery.processDefinitionNameLike(flowableDefinitionParam.getName());
            }
            //??????????????????
            if (ObjectUtil.isNotEmpty(flowableDefinitionParam.getCategory())) {
                processDefinitionQuery.processDefinitionCategory(flowableDefinitionParam.getCategory());
            }
            //????????????????????????
            if (flowableDefinitionParam.getSuspended()) {
                processDefinitionQuery.suspended();
            }
            //????????????????????????
            if (flowableDefinitionParam.getLastedVersion()) {
                processDefinitionQuery.latestVersion();
            }
        }
        Page<FlowableDefinitionResult> defaultPage = PageFactory.defaultPage();
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery
                .listPage(Convert.toInt((current - 1) * size), Convert.toInt(size));
        defaultPage.setTotal(processDefinitionQuery.count());
        return FlowableDefinitionFactory.pageResult(processDefinitionList, defaultPage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deploy(FlowableDefinitionParam flowableDefinitionParam) {

        Boolean demoEnvFlag = DemoConfigExpander.getDemoEnvFlag();
        if (demoEnvFlag) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }

        //???model????????????model??????act_re_model
        String modelId = flowableDefinitionParam.getModelId();

        //????????????
        String category = flowableDefinitionParam.getCategory();
        //????????????
        Model designModel;
        try {
            //??????????????????
            designModel = modelService.getModel(modelId);
        } catch (NotFoundException e) {
            //????????????????????????????????????
            throw new ServiceException(FlowableDefinitionExceptionEnum.MODEL_NOT_EXIST);
        }

        //??????bpmnModel
        BpmnModel bpmnModel = modelService.getBpmnModel(designModel);

        //?????????????????????????????????????????????????????????????????????????????????https://github.com/flowable/flowable-engine/issues/534
        //??????initiator?????????????????????http://www.shareniu.com/article/191.htm
        bpmnModel.getMainProcess().getFlowElements().forEach(flowElement -> {
            //?????????????????????????????????????????????INITIATOR
            if (flowElement instanceof StartEvent) {
                StartEvent startEvent = (StartEvent) flowElement;
                startEvent.setInitiator(INITIATOR);
            }
            //?????????????????????????????????????????????$INITIATOR???????????????????????????????????????????????????????????????????????????????????????${INITIATOR}???
            if (flowElement instanceof UserTask) {
                UserTask userTask = (UserTask) flowElement;
                String assignee = userTask.getAssignee();
                if (ObjectUtil.isNotEmpty(assignee)) {
                    //??????????????????????????????$INITIATOR????????????${INITIATOR}
                    if (SYMBOL_INITIATOR.equals(assignee)) {
                        userTask.setAssignee(DOUBLE_SYMBOL_INITIATOR);
                    }
                }
            }
        });

        if (ObjectUtil.isEmpty(bpmnModel)) {
            //?????????????????????????????????????????????????????????
            throw new ServiceException(FlowableDefinitionExceptionEnum.MODEL_HAS_NO_RESOURCE);
        }

        //???????????????????????????
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        //???????????????????????????
        deploymentBuilder.name(designModel.getName());
        //?????????????????????????????????
        String deploymentName = designModel.getName() + MODEL_BPMN_SUFFIX;
        //??????????????????
        deploymentBuilder.addBpmnModel(deploymentName, bpmnModel);
        //??????????????????
        deploymentBuilder.category(category);
        //??????
        try {
            Deployment deployment = deploymentBuilder.deploy();
            String deploymentId = deployment.getId();
            //???????????????deploymentId?????????processDefinition???????????????
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deploymentId).singleResult();
            repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);

            //?????????????????????????????????????????????
            flowableOptionService.addByProcessDefinition(processDefinition);
        } catch (Exception e) {
            log.error(">>> ???????????????????????????????????????{}", e.getMessage());
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEPLOYMENT_ERROR);
        }

    }

    @Override
    public void export(FlowableDefinitionParam flowableDefinitionParam, HttpServletResponse response) {
        //??????id??????????????????
        String id = flowableDefinitionParam.getId();

        //??????????????????
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);

        //????????????????????????deployment
        String deploymentId = processDefinition.getDeploymentId();
        Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId(deploymentId).singleResult();
        if (ObjectUtil.isEmpty(deployment)) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEPLOYMENT_NOT_EXIST);
        }

        //??????????????????
        String resourceName = processDefinition.getResourceName();
        List<String> resourceList = repositoryService.getDeploymentResourceNames(deploymentId);

        //?????????????????????????????????????????????
        if (resourceList.contains(resourceName)) {
            try {
                final InputStream resourceStream = repositoryService.getResourceAsStream(deploymentId, resourceName);
                byte[] bytes = IoUtil.readBytes(resourceStream);
                response.reset();
                response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(resourceName, "UTF-8") + "\"");
                response.addHeader("Content-Length", "" + bytes.length);
                response.setContentType("application/octet-stream;charset=UTF-8");
                IoUtil.write(response.getOutputStream(), true, bytes);
            } catch (Exception e) {
                log.error(">>> ???????????????????????????????????????{}", e.getMessage());
                throw new ServiceException(FlowableDefinitionExceptionEnum.RESOURCE_EXPORT_ERROR);
            }
        } else {
            log.error(">>> ?????????????????????????????????????????????");
            throw new ServiceException(FlowableDefinitionExceptionEnum.RESOURCE_EXPORT_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void mapping(FlowableDefinitionParam flowableDefinitionParam) {
        //???flowable??????????????????
        FlowableUserFactory.assumeUser();

        //??????id??????????????????
        String id = flowableDefinitionParam.getId();

        //??????????????????
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);

        //????????????????????????deployment
        String deploymentId = processDefinition.getDeploymentId();
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (ObjectUtil.isEmpty(deployment)) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEPLOYMENT_NOT_EXIST);
        }

        //??????bpmnModel?????????modelNode
        BpmnModel bpmnModel = repositoryService.getBpmnModel(id);

        ObjectNode modelNode = new BpmnJsonConverter().convertToJson(bpmnModel);

        //????????????
        ModelRepresentation model = new ModelRepresentation();
        model.setKey(processDefinition.getKey());
        model.setName(processDefinition.getName());
        model.setDescription(processDefinition.getDescription());
        model.setModelType(AbstractModel.MODEL_TYPE_BPMN);
        //????????????
        modelService.createModel(model, modelNode.toString(), SecurityUtils.getCurrentUserObject());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void activeOrSuspend(FlowableDefinitionParam flowableDefinitionParam, boolean isSuspend) {
        //??????id??????????????????
        String id = flowableDefinitionParam.getId();
        //??????????????????
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);

        boolean suspended = processDefinition.isSuspended();

        //???????????????????????????????????????
        if (suspended && isSuspend) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEFINITION_SUSPEND);
        }

        //???????????????????????????????????????
        if (!suspended && !isSuspend) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEFINITION_ACTIVE);
        }
        if (isSuspend) {
            //??????
            repositoryService.suspendProcessDefinitionById(id);
            //???????????????????????????
            flowableShortcutService.changeStatus(id, StatusEnum.DISABLE.getCode());
        } else {
            //??????
            repositoryService.activateProcessDefinitionById(id);
            //???????????????????????????
            flowableShortcutService.changeStatus(id, StatusEnum.ENABLE.getCode());
        }
    }

    @Override
    public JsonNode trace(FlowableDefinitionParam flowableDefinitionParam) {
        //??????id??????????????????
        String id = flowableDefinitionParam.getId();
        //??????????????????
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);

        //????????????????????????deployment
        String deploymentId = processDefinition.getDeploymentId();
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (ObjectUtil.isEmpty(deployment)) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEPLOYMENT_NOT_EXIST);
        }

        //??????bpmnModel?????????modelNode
        BpmnModel bpmnModel = repositoryService.getBpmnModel(id);
        ObjectNode modelNode = new BpmnJsonConverter().convertToJson(bpmnModel);

        //??????model???????????????????????????
        Model model = new Model();
        model.setModelEditorJson(modelNode.toString());

        //?????????????????????????????????
        ObjectNode displayNode = new ObjectMapper().createObjectNode();
        bpmnDisplayJsonConverter.processProcessElements(model, displayNode, new GraphicInfo());

        return displayNode;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(FlowableDefinitionParam flowableDefinitionParam) {
        //??????id??????????????????
        String id = flowableDefinitionParam.getId();
        //??????????????????
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);
        String deploymentId = processDefinition.getDeploymentId();
        repositoryService.deleteDeployment(deploymentId, true);
        //??????????????????????????????????????????
        flowableFormService.delete(id);
        //??????????????????????????????????????????
        flowableOptionService.delete(id);
        //??????????????????????????????????????????
        flowableEventService.delete(id);
        //??????????????????????????????????????????
        flowableButtonService.delete(id);
        //????????????????????????????????????????????????
        flowableShortcutService.delete(id);
    }

    @Override
    public List<FlowableUserTaskResult> userTasks(FlowableDefinitionParam flowableDefinitionParam) {
        List<FlowableUserTaskResult> resultList = CollectionUtil.newArrayList();
        //??????id??????????????????
        String id = flowableDefinitionParam.getId();

        //??????????????????
        ProcessDefinition processDefinition = this.queryProcessDefinition(id);

        //????????????????????????deployment
        String deploymentId = processDefinition.getDeploymentId();
        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
        if (ObjectUtil.isEmpty(deployment)) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEPLOYMENT_NOT_EXIST);
        }

        //??????bpmnModel?????????modelNode
        BpmnModel bpmnModel = repositoryService.getBpmnModel(id);
        //???????????????
        Process mainProcess = bpmnModel.getMainProcess();
        //????????????????????????????????????????????????
        mainProcess.findFlowElementsOfType(UserTask.class, true).forEach(userTask -> {
            FlowableUserTaskResult flowableUserTaskResult = new FlowableUserTaskResult();
            flowableUserTaskResult.setId(userTask.getId());
            flowableUserTaskResult.setProcessDefinitionId(processDefinition.getId());
            flowableUserTaskResult.setName(userTask.getName());
            resultList.add(flowableUserTaskResult);
        });
        return resultList;
    }

    @Override
    public FlowableDefinitionResult detail(String processDefinitionId) {
        ProcessDefinition processDefinition = this.queryProcessDefinition(processDefinitionId);
        return FlowableDefinitionFactory.convertToFlowableDefinitionResult(processDefinition);
    }

    @Override
    public ProcessDefinition queryProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        if (ObjectUtil.isNull(processDefinition)) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEFINITION_NOT_EXIST);
        }
        return processDefinition;
    }

    @Override
    public ProcessDefinition queryProcessDefinitionWithValidStatus(String processDefinitionId) {
        ProcessDefinition processDefinition = this.queryProcessDefinition(processDefinitionId);
        boolean suspended = processDefinition.isSuspended();
        if (suspended) {
            throw new ServiceException(FlowableDefinitionExceptionEnum.DEFINITION_SUSPEND);
        }
        return processDefinition;
    }

    @Override
    public String getMultiInstanceActAssigneeParam(String processDefinitionId, String actId) {
        AtomicReference<String> resultParam = new AtomicReference<>();
        ProcessDefinition processDefinition = this.queryProcessDefinition(processDefinitionId);
        //??????bpmnModel?????????modelNode
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());
        //???????????????
        Process mainProcess = bpmnModel.getMainProcess();
        //????????????????????????????????????????????????
        mainProcess.findFlowElementsOfType(UserTask.class, true).forEach(userTask -> {
            String userTaskId = userTask.getId();
            if (userTaskId.equals(actId)) {
                Object behavior = userTask.getBehavior();
                if (ObjectUtil.isNotNull(behavior)) {
                    //?????????????????????
                    if (behavior instanceof ParallelMultiInstanceBehavior) {
                        ParallelMultiInstanceBehavior parallelMultiInstanceBehavior =
                                (ParallelMultiInstanceBehavior) behavior;
                        String collectionElementVariable = parallelMultiInstanceBehavior
                                .getCollectionElementVariable();
                        if (ObjectUtil.isNotEmpty(collectionElementVariable)) {
                            resultParam.set(collectionElementVariable);
                        }
                    }
                    //?????????????????????
                    if (behavior instanceof SequentialMultiInstanceBehavior) {
                        SequentialMultiInstanceBehavior sequentialMultiInstanceBehavior =
                                (SequentialMultiInstanceBehavior) behavior;
                        String collectionElementVariable = sequentialMultiInstanceBehavior
                                .getCollectionElementVariable();
                        if (ObjectUtil.isNotEmpty(collectionElementVariable)) {
                            resultParam.set(collectionElementVariable);
                        }
                    }
                }
            }
        });
        return resultParam.get();
    }

    @Override
    public boolean hasDefinition(String category) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        processDefinitionQuery.processDefinitionCategory(category);
        return processDefinitionQuery.list().size() > 0;
    }
}
