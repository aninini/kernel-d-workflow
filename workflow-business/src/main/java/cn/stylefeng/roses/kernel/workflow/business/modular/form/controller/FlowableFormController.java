package cn.stylefeng.roses.kernel.workflow.business.modular.form.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.param.FlowableFormParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.service.FlowableFormService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程表单控制器
 *
 * @author fengshuonan
 * @date 2020/4/17 10:23
 */
@RestController
@ApiResource(name = "流程表单控制器")
public class FlowableFormController {

    @Resource(name = "gunsFlowableFormService")
    private FlowableFormService flowableFormService;

    /**
     * 流程表单列表
     *
     * @author fengshuonan
     * @date 2020/8/14 15:02
     **/
    @GetResource(name = "流程表单列表", path = "/flowableForm/list")
    public ResponseData list(@Validated(FlowableFormParam.list.class) FlowableFormParam flowableFormParam) {
        return new SuccessResponseData(flowableFormService.list(flowableFormParam));
    }

    /**
     * 添加流程表单
     *
     * @author fengshuonan
     * @date 2020/8/14 14:57
     **/
    @PostResource(name = "添加流程表单", path = "/flowableForm/add")
    public ResponseData add(@RequestBody @Validated(FlowableFormParam.add.class) FlowableFormParam flowableFormParam) {
        flowableFormService.add(flowableFormParam);
        return new SuccessResponseData();
    }

    /**
     * 流程表单删除
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     **/
    @PostResource(name = "流程表单删除", path = "/flowableForm/delete")
    public ResponseData delete(@RequestBody @Validated(FlowableFormParam.delete.class) FlowableFormParam flowableFormParam) {
        flowableFormService.delete(flowableFormParam);
        return new SuccessResponseData();
    }

    /**
     * 流程表单编辑
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     */
    @PostResource(name = "流程表单编辑", path = "/flowableForm/edit")
    public ResponseData edit(@RequestBody @Validated(FlowableFormParam.edit.class) FlowableFormParam flowableFormParam) {
        flowableFormService.edit(flowableFormParam);
        return new SuccessResponseData();
    }

    /**
     * 流程表单查看
     *
     * @author fengshuonan
     * @date 2020/4/17 15:32
     */
    @GetResource(name = "流程表单查看", path = "/flowableForm/detail")
    public ResponseData detail(@Validated(FlowableFormParam.detail.class) FlowableFormParam flowableFormParam) {
        return new SuccessResponseData(flowableFormService.detail(flowableFormParam));
    }

    /**
     * 流程定义的启动表单
     *
     * @author fengshuonan
     * @date 2020/8/3 14:17
     **/
    @GetResource(name = "流程定义的启动表单", path = "/flowableForm/startFormData")
    public ResponseData startFormData(@Validated(FlowableFormParam.trace.class) FlowableFormParam flowableFormParam) {
        return new SuccessResponseData(flowableFormService.getStartFormData(flowableFormParam));
    }

    /**
     * 流程定义的全局表单
     *
     * @author fengshuonan
     * @date 2020/8/3 14:17
     **/
    @GetResource(name = "流程定义的全局表单", path = "/flowableForm/globalFormData")
    public ResponseData globalFormData(@Validated(FlowableFormParam.trace.class) FlowableFormParam flowableFormParam) {
        return new SuccessResponseData(flowableFormService.getGlobalFormData(flowableFormParam));
    }

    /**
     * 当前任务的任务表单
     *
     * @author fengshuonan
     * @date 2020/8/3 14:17
     **/
    @GetResource(name = "当前任务的任务表单", path = "/flowableForm/taskFormData")
    public ResponseData taskFormData(@Validated(FlowableFormParam.force.class) FlowableFormParam flowableFormParam) {
        return new SuccessResponseData(flowableFormService.getTaskFormData(flowableFormParam));
    }
}
