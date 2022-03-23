package cn.stylefeng.roses.kernel.workflow.business.modular.model.controller;

import cn.stylefeng.roses.kernel.demo.exception.DemoException;
import cn.stylefeng.roses.kernel.demo.exception.enums.DemoExceptionEnum;
import cn.stylefeng.roses.kernel.demo.expander.DemoConfigExpander;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.api.pojo.BaseWorkflowRequest;
import cn.stylefeng.roses.kernel.workflow.business.modular.model.param.FlowableModelParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.model.service.FlowableModelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 流程模型控制器
 *
 * @author fengshuonan
 * @date 2020/8/15 17:11
 */
@RestController
@ApiResource(name = "流程模型控制器")
public class FlowableModelController {

    @Resource
    private FlowableModelService flowableModelService;

    /**
     * 查询流程模型
     *
     * @author fengshuonan
     * @date 2020/8/15 17:21
     */
    @GetResource(name = "添加活动节点按钮", path = "/flowableModel/list")
    public ResponseData list(FlowableModelParam flowableModelParam) {
        return new SuccessResponseData(flowableModelService.list(flowableModelParam));
    }

    /**
     * 流程模型增加
     *
     * @author fengshuonan
     * @date 2020/8/15 17:15
     */
    @PostResource(name = "添加活动节点按钮", path = "/flowableModel/add")
    public ResponseData add(@RequestBody @Validated(FlowableModelParam.add.class) FlowableModelParam flowableModelParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        return new SuccessResponseData(flowableModelService.add(flowableModelParam));
    }

    /**
     * 流程模型删除
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     **/
    @PostResource(name = "添加活动节点按钮", path = "/flowableModel/delete")
    public ResponseData delete(@RequestBody @Validated(FlowableModelParam.delete.class) FlowableModelParam flowableModelParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableModelService.delete(flowableModelParam);
        return new SuccessResponseData();
    }

    /**
     * 流程模型编辑
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     */
    @PostResource(name = "添加活动节点按钮", path = "/flowableModel/edit")
    public ResponseData edit(@RequestBody @Validated(FlowableModelParam.edit.class) FlowableModelParam flowableModelParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        return new SuccessResponseData(flowableModelService.edit(flowableModelParam));
    }

    /**
     * 流程模型预览
     *
     * @author fengshuonan
     * @date 2020/8/15 23:14
     */
    @GetResource(name = "添加活动节点按钮", path = "/flowableModel/preview")
    public ResponseData preview(@Validated(BaseWorkflowRequest.detail.class) FlowableModelParam flowableModelParam) {
        return new SuccessResponseData(flowableModelService.preview(flowableModelParam));
    }

    /**
     * 模型版本
     *
     * @author fengshuonan
     * @date 2020/8/16 1:50
     */
    @GetResource(name = "添加活动节点按钮", path = "/flowableModel/version")
    public ResponseData version(@Validated(FlowableModelParam.trace.class) FlowableModelParam flowableModelParam) {
        return new SuccessResponseData(flowableModelService.version(flowableModelParam));
    }

    /**
     * 流程模型设为新版
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     **/
    @PostResource(name = "添加活动节点按钮", path = "/flowableModel/setAsNew")
    public ResponseData setAsNew(@RequestBody @Validated(FlowableModelParam.force.class) FlowableModelParam flowableModelParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableModelService.setAsNew(flowableModelParam);
        return new SuccessResponseData();
    }

    /**
     * 流程模型导入
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     **/
    @PostResource(name = "添加活动节点按钮", path = "/flowableModel/importModel")
    public ResponseData importModel(@RequestPart("file") MultipartFile file) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        return new SuccessResponseData(flowableModelService.importModel(file));
    }

}
