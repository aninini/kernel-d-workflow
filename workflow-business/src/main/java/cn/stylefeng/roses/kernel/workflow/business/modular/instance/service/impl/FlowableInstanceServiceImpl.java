package cn.stylefeng.roses.kernel.workflow.business.modular.instance.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import cn.stylefeng.roses.kernel.workflow.api.constants.CommonConstant;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableInstanceExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.core.utils.BpmGraphicUtil;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.result.FlowableUserTaskResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.instance.factory.FlowableCommentHistoryFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.instance.factory.FlowableInstanceFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.instance.param.FlowableInstanceParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.instance.result.FlowableCommentHistoryResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.instance.result.FlowableInstanceResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.instance.service.FlowableInstanceService;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.factory.FlowableAssigneeFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.FlowableEndTaskService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ????????????service???????????????
 *
 * @author fengshuonan
 * @date 2020/4/20 11:36
 */
@Service
public class FlowableInstanceServiceImpl implements FlowableInstanceService {

    private final String FORM_DATA_VARIABLE_NAME = "formData";

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private HistoryService historyService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private FlowableEndTaskService flowableEndTaskService;

    @Resource
    private SysUserService sysUserService;

    @Override
    public PageResult<FlowableInstanceResult> page(FlowableInstanceParam flowableInstanceParam) {
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        if (ObjectUtil.isNotNull(flowableInstanceParam)) {
            if (ObjectUtil.isNotEmpty(flowableInstanceParam.getStartUserId())) {
                //???????????????id??????
                historicProcessInstanceQuery.startedBy(flowableInstanceParam.getStartUserId());
            }

            if (ObjectUtil.isNotEmpty(flowableInstanceParam.getCategory())) {
                //??????????????????
                historicProcessInstanceQuery.processDefinitionCategory(flowableInstanceParam.getCategory());
            }

            if (ObjectUtil.isNotEmpty(flowableInstanceParam.getEnded())) {
                //??????????????????
                if (flowableInstanceParam.getEnded()) {
                    historicProcessInstanceQuery.finished();
                } else {
                    historicProcessInstanceQuery.unfinished();
                }
            }

            //????????????????????????
            if (ObjectUtil.isAllNotEmpty(flowableInstanceParam.getSearchBeginTime(), flowableInstanceParam.getSearchEndTime())) {
                DateTime beginTime = DateUtil.parseDateTime(flowableInstanceParam.getSearchBeginTime());
                DateTime endTime = DateUtil.parseDateTime(flowableInstanceParam.getSearchEndTime());
                historicProcessInstanceQuery.startedAfter(beginTime).startedBefore(endTime);
            }

        }
        //?????????????????????????????????????????????????????????????????????
        historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
        Page<FlowableInstanceResult> defaultPage = PageFactory.defaultPage();
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        List<HistoricProcessInstance> historicProcessInstanceList = historicProcessInstanceQuery
                .listPage(Convert.toInt((current - 1) * size), Convert.toInt(size));
        defaultPage.setTotal(historicProcessInstanceQuery.count());
        return FlowableInstanceFactory.pageResult(historicProcessInstanceList, defaultPage);
    }

    @Override
    public FlowableInstanceResult detail(String processInstanceId) {
        FlowableInstanceParam flowableInstanceParam = new FlowableInstanceParam();
        flowableInstanceParam.setId(processInstanceId);
        HistoricProcessInstance historicProcessInstance = this.queryHistoricProcessInstance(flowableInstanceParam);
        return FlowableInstanceFactory.convertToFlowableInstanceResult(historicProcessInstance);
    }

    @Override
    public List<FlowableUserTaskResult> hisUserTasks(FlowableInstanceParam flowableInstanceParam) {
        //??????????????????????????????
        HistoricProcessInstance historicProcessInstance = this.queryHistoricProcessInstance(flowableInstanceParam);
        String processInstanceId = historicProcessInstance.getId();
        //??????set??????
        Set<FlowableUserTaskResult> resultSet = CollectionUtil.newHashSet();
        //????????????key??????
        Set<String> definitionKeySet = CollectionUtil.newHashSet();
        //???????????????
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).finished().orderByHistoricTaskInstanceStartTime().desc();
        historicTaskInstanceQuery.list().forEach(historicTaskInstance -> {
            if (!definitionKeySet.contains(historicTaskInstance.getTaskDefinitionKey())) {
                FlowableUserTaskResult flowableUserTaskResult = new FlowableUserTaskResult();
                flowableUserTaskResult.setId(historicTaskInstance.getTaskDefinitionKey());
                flowableUserTaskResult.setProcessDefinitionId(historicTaskInstance.getProcessDefinitionId());
                flowableUserTaskResult.setName(historicTaskInstance.getName());
                if (ObjectUtil.isNotEmpty(historicTaskInstance.getAssignee())) {
                    flowableUserTaskResult.setAssigneeName(FlowableAssigneeFactory.getAssigneeNameByUserId(historicTaskInstance.getAssignee()));
                }
                definitionKeySet.add(historicTaskInstance.getTaskDefinitionKey());
                resultSet.add(flowableUserTaskResult);
            }
        });

        return CollectionUtil.newArrayList(resultSet);
    }

    @Override
    public JsonNode trace(FlowableInstanceParam flowableInstanceParam) {
        //??????????????????????????????
        HistoricProcessInstance historicProcessInstance = this.queryHistoricProcessInstance(flowableInstanceParam);
        String processInstanceId = historicProcessInstance.getId();
        //?????????????????????BpmnModel
        BpmnModel pojoModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());

        //????????????????????????????????????
        if (ObjectUtil.hasEmpty(pojoModel, pojoModel.getLocationMap())) {
            throw new ServiceException(FlowableInstanceExceptionEnum.INSTANCE_HAS_NO_DEFINITION);
        }
        //????????????????????????
        List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();

        Set<String> completedActivityInstances = new HashSet<>();
        Set<String> currentActivityInstances = new HashSet<>();
        if (ObjectUtil.isNotEmpty(activityInstances)) {
            for (HistoricActivityInstance activityInstance : activityInstances) {
                if (activityInstance.getEndTime() != null) {
                    completedActivityInstances.add(activityInstance.getActivityId());
                } else {
                    currentActivityInstances.add(activityInstance.getActivityId());
                }
            }
        }
        return BpmGraphicUtil.processProcessElements(pojoModel, completedActivityInstances, currentActivityInstances, CollectionUtil.newArrayList());
    }

    @Override
    public void activeOrSuspend(FlowableInstanceParam flowableInstanceParam, boolean isSuspend) {
        //??????id??????????????????
        String id = flowableInstanceParam.getId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(id).singleResult();
        if (ObjectUtil.isNull(processInstance)) {
            throw new ServiceException(FlowableInstanceExceptionEnum.INSTANCE_NOT_EXIST);
        }
        boolean suspended = processInstance.isSuspended();

        //???????????????????????????????????????
        if (suspended && isSuspend) {
            throw new ServiceException(FlowableInstanceExceptionEnum.INSTANCE_SUSPEND);
        }

        //???????????????????????????????????????
        if (!suspended && !isSuspend) {
            throw new ServiceException(FlowableInstanceExceptionEnum.INSTANCE_ACTIVE);
        }

        if (isSuspend) {
            //??????
            runtimeService.suspendProcessInstanceById(id);
        } else {
            //??????
            runtimeService.activateProcessInstanceById(id);
        }
    }

    @Override
    public List<FlowableCommentHistoryResult> commentHistory(FlowableInstanceParam flowableInstanceParam) {
        HistoricProcessInstance historicProcessInstance = this.queryHistoricProcessInstance(flowableInstanceParam);
        String processInstanceId = historicProcessInstance.getId();
        //??????????????????????????????????????????
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).finished().orderByHistoricTaskInstanceEndTime().asc().list();
        return FlowableCommentHistoryFactory.convertToFlowableCommentHistoryResultList(historicTaskInstanceList);
    }

    @Override
    public void end(FlowableInstanceParam flowableInstanceParam) {
        HistoricProcessInstance historicProcessInstance = this.queryHistoricProcessInstance(flowableInstanceParam);
        String processInstanceId = historicProcessInstance.getId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        if (ObjectUtil.isNull(processInstance)) {
            throw new ServiceException(FlowableInstanceExceptionEnum.INSTANCE_NOT_EXIST);
        }
        boolean suspended = processInstance.isSuspended();
        //???????????????????????????
        if (suspended) {
            throw new ServiceException(FlowableInstanceExceptionEnum.INSTANCE_SUSPEND_CAN_NOT_END);
        }
        //??????????????????????????????????????????
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
        if (ObjectUtil.isNotEmpty(taskList)) {
            //??????????????????????????????
            Task task = taskList.get(0);
            String comment = flowableInstanceParam.getComment();
            flowableEndTaskService.end(task.getId(), comment);
        }
    }

    @Override
    public Map<String, Object> formData(FlowableInstanceParam flowableInstanceParam) {
        Map<String, Object> variables = MapUtil.newHashMap();
        //??????????????????id
        String processInstanceId = flowableInstanceParam.getId();
        //???????????????????????????
        List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId)
                .list();
        historicVariableInstanceList.forEach(historicVariableInstance -> {
            if (FORM_DATA_VARIABLE_NAME.equals(historicVariableInstance.getVariableName())) {
                variables.put(historicVariableInstance.getVariableName(), historicVariableInstance.getValue());
            }
        });
        return variables;
    }

    @Override
    public List<Dict> addSignUserSelector(FlowableInstanceParam flowableInstanceParam) {
        List<Dict> resultList = CollectionUtil.newArrayList();
        List<Long> taskAssigneeList = CollectionUtil.newArrayList();
        HistoricProcessInstance historicProcessInstance = this.queryHistoricProcessInstance(flowableInstanceParam);
        //?????????????????????????????????
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(historicProcessInstance.getId()).list();
        if (ObjectUtil.isNotNull(taskList)) {
            taskList.forEach(task -> {
                //?????????????????????
                String assignee = task.getAssignee();
                if (ObjectUtil.isNotNull(assignee)) {
                    taskAssigneeList.add(Convert.toLong(assignee));
                }
            });
        }
        //????????????????????????id??????
        List<Long> allUserIdList = sysUserService.getAllUserIds();
        //??????????????????????????????
        if (ObjectUtil.isNotEmpty(taskAssigneeList)) {
            //?????????
            allUserIdList = (List<Long>) CollectionUtil.disjunction(taskAssigneeList, allUserIdList);
        }

        //???????????????
        allUserIdList.forEach(userId -> {
            Dict dict = Dict.create();
            dict.put(CommonConstant.ID, userId);

            SysUserDTO userDetail = sysUserService.getUserInfoByUserId(userId);
            if (userDetail != null) {
                dict.put(CommonConstant.NAME, userDetail.getRealName());
            } else {
                dict.put(CommonConstant.NAME, "");
            }

            resultList.add(dict);
        });
        return resultList;
    }

    @Override
    public List<Dict> deleteSignUserSelector(FlowableInstanceParam flowableInstanceParam) {
        List<Dict> resultList = CollectionUtil.newArrayList();
        List<Long> taskAssigneeList = CollectionUtil.newArrayList();
        HistoricProcessInstance historicProcessInstance = this.queryHistoricProcessInstance(flowableInstanceParam);
        //?????????????????????????????????
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(historicProcessInstance.getId()).list();
        if (ObjectUtil.isNotNull(taskList)) {
            taskList.forEach(task -> {
                //?????????????????????
                String assignee = task.getAssignee();
                if (ObjectUtil.isNotNull(assignee)) {
                    taskAssigneeList.add(Convert.toLong(assignee));
                }
            });
        }
        //??????????????????????????????
        if (ObjectUtil.isNotEmpty(taskAssigneeList)) {
            taskAssigneeList.forEach(userId -> {
                Dict dict = Dict.create();
                dict.put(CommonConstant.ID, userId);

                SysUserDTO userDetail = sysUserService.getUserInfoByUserId(userId);
                if (userDetail != null) {
                    dict.put(CommonConstant.NAME, userDetail.getRealName());
                } else {
                    dict.put(CommonConstant.NAME, "");
                }

                resultList.add(dict);
            });
        }
        return resultList;
    }

    /**
     * ??????????????????
     *
     * @author fengshuonan
     * @date 2020/8/11 14:27
     **/
    private HistoricProcessInstance queryHistoricProcessInstance(FlowableInstanceParam flowableInstanceParam) {
        String id = flowableInstanceParam.getId();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(id).singleResult();
        if (ObjectUtil.isEmpty(historicProcessInstance)) {
            throw new ServiceException(FlowableInstanceExceptionEnum.INSTANCE_NOT_EXIST);
        }
        return historicProcessInstance;
    }
}
