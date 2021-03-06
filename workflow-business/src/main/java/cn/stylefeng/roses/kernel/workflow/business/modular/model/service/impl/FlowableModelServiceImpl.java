package cn.stylefeng.roses.kernel.workflow.business.modular.model.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import cn.stylefeng.roses.kernel.rule.constants.SymbolConstant;
import cn.stylefeng.roses.kernel.workflow.api.exception.WorkflowException;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableModelExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.modular.model.factory.FlowableModelFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.model.param.FlowableModelParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.model.result.FlowableModelResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.model.service.FlowableModelService;
import cn.stylefeng.roses.kernel.workflow.business.modular.user.factory.FlowableUserFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.ui.common.security.SecurityUtils;
import org.flowable.ui.common.util.XmlUtil;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.domain.ModelHistory;
import org.flowable.ui.modeler.model.ModelKeyRepresentation;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.repository.ModelHistoryRepository;
import org.flowable.ui.modeler.repository.ModelRepository;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ????????????service???????????????
 *
 * @author fengshuonan
 * @date 2020/8/15 17:12
 */
@Service
public class FlowableModelServiceImpl implements FlowableModelService {

    private static final Log log = Log.get();

    /**
     * ??????????????????
     */
    private static final String DEFAULT_ORDER_BY = "modifiedDesc";

    /**
     * ?????????????????????????????????
     */
    private static final Integer DEFAULT_MODEL_TYPE = 0;

    /**
     * ??????????????????
     */
    private static final String MODEL_BPMN_SUFFIX_BPMN = ".bpmn";

    /**
     * ??????????????????
     */
    private static final String MODEL_BPMN_SUFFIX_BPMN20 = ".bpmn20.xml";

    private final BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();

    private final BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    @Resource
    private ModelService modelService;

    @Resource
    private ModelRepository modelRepository;

    @Resource
    private ModelHistoryRepository modelHistoryRepository;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public List<FlowableModelResult> list(FlowableModelParam flowableModelParam) {
        List<Model> modelList;
        if (ObjectUtil.isNotNull(flowableModelParam)) {
            if (ObjectUtil.isNotEmpty(flowableModelParam.getName())) {
                String name = SymbolConstant.PERCENT + flowableModelParam.getName().trim() + SymbolConstant.PERCENT;
                modelList = modelRepository.findByModelTypeAndFilter(DEFAULT_MODEL_TYPE, name, DEFAULT_ORDER_BY);
            } else {
                modelList = modelRepository.findByModelType(DEFAULT_MODEL_TYPE, DEFAULT_ORDER_BY);
            }
        } else {
            modelList = modelRepository.findByModelType(DEFAULT_MODEL_TYPE, DEFAULT_ORDER_BY);
        }
        return FlowableModelFactory.convertToFlowableModelResultList(modelList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String add(FlowableModelParam flowableModelParam) {
        //???flowable??????????????????
        FlowableUserFactory.assumeUser();
        //????????????
        checkParam(flowableModelParam, false);
        //??????modeler ui???????????????
        ModelRepresentation modelRepresentation = new ModelRepresentation();
        modelRepresentation.setKey(flowableModelParam.getKey());
        modelRepresentation.setName(flowableModelParam.getName());
        modelRepresentation.setDescription(flowableModelParam.getDescription());
        modelRepresentation.setModelType(DEFAULT_MODEL_TYPE);
        String json = modelService.createModelJson(modelRepresentation);
        Model model = modelService.createModel(modelRepresentation, json, SecurityUtils.getCurrentUserObject());
        return model.getId();
    }

    @Override
    public void delete(FlowableModelParam flowableModelParam) {
        //???flowable??????????????????
        FlowableUserFactory.assumeUser();
        FlowableModelResult flowableModelResult = this.queryFlowableModel(flowableModelParam);
        modelService.deleteModel(flowableModelResult.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String edit(FlowableModelParam flowableModelParam) {
        //???flowable??????????????????
        FlowableUserFactory.assumeUser();
        FlowableModelResult flowableModelResult = this.queryFlowableModel(flowableModelParam);
        //????????????
        Model model = modelService.getModel(flowableModelResult.getId());
        //????????????
        checkParam(flowableModelParam, true);
        //??????????????????
        model.setKey(flowableModelParam.getKey());
        model.setName(flowableModelParam.getName());
        model.setDescription(flowableModelParam.getDescription());
        model.setModelType(DEFAULT_MODEL_TYPE);

        //???????????????editorJson
        ObjectNode modelNode;
        try {
            modelNode = (ObjectNode) objectMapper.readTree(model.getModelEditorJson());
            modelNode.put("name", model.getName());
            modelNode.put("key", model.getKey());
            //?????????editorJson??????
            ObjectNode propertiesNode = (ObjectNode) modelNode.get("properties");
            propertiesNode.put("process_id", model.getKey());
            propertiesNode.put("name", model.getName());
            if (StringUtils.isNotEmpty(model.getDescription())) {
                propertiesNode.put("documentation", model.getDescription());
            }
            modelNode.set("properties", propertiesNode);
            model.setModelEditorJson(modelNode.toString());
            modelRepository.save(model);
            return model.getId();
        } catch (Exception e) {
            log.error(">>> ?????????????????????{}", e.getMessage());
            throw new WorkflowException(FlowableModelExceptionEnum.MODEL_EDIT_ERROR);
        }
    }

    @Override
    public String preview(FlowableModelParam flowableModelParam) {
        //???flowable??????????????????
        FlowableUserFactory.assumeUser();
        FlowableModelResult flowableModelResult = this.queryFlowableModel(flowableModelParam);
        Model model = modelService.getModel(flowableModelResult.getId());
        byte[] bpmnXml = modelService.getBpmnXML(model);
        return new String(bpmnXml);
    }

    @Override
    public List<FlowableModelResult> version(FlowableModelParam flowableModelParam) {
        //???flowable??????????????????
        FlowableUserFactory.assumeUser();
        FlowableModelResult flowableModelResult = this.queryFlowableModel(flowableModelParam);
        List<ModelHistory> modelHistoryList = modelHistoryRepository.findByModelId(flowableModelResult.getId());
        return FlowableModelFactory.convertToFlowableModelResultHistoryList(modelHistoryList);
    }

    @Override
    public void setAsNew(FlowableModelParam flowableModelParam) {
        //???flowable??????????????????
        FlowableUserFactory.assumeUser();
        FlowableModelParam queryParam = new FlowableModelParam();
        queryParam.setId(flowableModelParam.getModelId());
        FlowableModelResult flowableModelResult = this.queryFlowableModel(queryParam);
        String modelHistoryId = flowableModelParam.getId();
        String modelId = flowableModelResult.getId();
        ModelHistory modelHistory = modelService.getModelHistory(modelId, modelHistoryId);
        modelService.reviveProcessModelHistory(modelHistory, SecurityUtils.getCurrentUserObject(), null);
    }

    @Override
    public String importModel(MultipartFile file) {
        if (ObjectUtil.isNull(file)) {
            throw new WorkflowException(FlowableModelExceptionEnum.MODEL_FILE_EMPTY);
        }
        String fileName = file.getOriginalFilename();
        if (ObjectUtil.isNull(fileName)) {
            throw new WorkflowException(FlowableModelExceptionEnum.MODEL_FILE_SUFFIX_ERROR);
        }
        if (!fileName.endsWith(MODEL_BPMN_SUFFIX_BPMN) && !fileName.endsWith(MODEL_BPMN_SUFFIX_BPMN20)) {
            throw new WorkflowException(FlowableModelExceptionEnum.MODEL_FILE_SUFFIX_ERROR);
        } else {
            try {
                XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
                InputStreamReader xmlIn = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
                XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
                BpmnModel bpmnModel = bpmnXmlConverter.convertToBpmnModel(xtr);
                if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                    throw new WorkflowException(FlowableModelExceptionEnum.MODEL_IMPORT_NO_RESOURCE);
                } else {
                    if (ObjectUtil.isEmpty(bpmnModel.getLocationMap())) {
                        BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
                        bpmnLayout.execute();
                    }

                    ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);
                    Process process = bpmnModel.getMainProcess();
                    String name = process.getId();
                    if (StringUtils.isNotEmpty(process.getName())) {
                        name = process.getName();
                    }

                    String description = process.getDocumentation();
                    ModelRepresentation model = new ModelRepresentation();
                    model.setKey(process.getId());
                    model.setName(name);
                    model.setDescription(description);
                    model.setModelType(DEFAULT_MODEL_TYPE);
                    Model resultModel = modelService.createModel(model, modelNode.toString(), SecurityUtils.getCurrentUserObject());
                    return resultModel.getId();
                }
            } catch (Exception e) {
                log.error(">>> ?????????????????????{}", e.getMessage());
                throw new WorkflowException(FlowableModelExceptionEnum.MODEL_IMPORT_ERROR);
            }
        }
    }

    /**
     * ????????????
     *
     * @author fengshuonan
     * @date 2020/4/26 16:25
     */
    private FlowableModelResult queryFlowableModel(FlowableModelParam flowableModelParam) {
        Model model = modelRepository.get(flowableModelParam.getId());
        if (ObjectUtil.isNull(model)) {
            throw new WorkflowException(FlowableModelExceptionEnum.MODEL_NOT_EXIST);
        }
        return FlowableModelFactory.convertToFlowableModelResult(model);
    }

    /**
     * ????????????
     *
     * @author fengshuonan
     * @date 2020/8/16 0:14
     */
    private void checkParam(FlowableModelParam flowableModelParam, boolean isExcludeSelf) {
        //????????????????????????
        String key = flowableModelParam.getKey();
        ModelKeyRepresentation modelKeyInfo;
        if (isExcludeSelf) {
            String id = flowableModelParam.getId();
            Model model = modelService.getModel(id);
            modelKeyInfo = modelService.validateModelKey(model, DEFAULT_MODEL_TYPE, key);
        } else {
            modelKeyInfo = modelService.validateModelKey(null, DEFAULT_MODEL_TYPE, key);
        }
        if (modelKeyInfo.isKeyAlreadyExists()) {
            throw new WorkflowException(FlowableModelExceptionEnum.MODEL_KEY_REPEAT);
        }
    }
}
