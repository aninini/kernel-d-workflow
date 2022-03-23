package cn.stylefeng.roses.kernel.workflow.business.modular.instance.controller;

import cn.hutool.core.convert.Convert;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.demo.exception.DemoException;
import cn.stylefeng.roses.kernel.demo.exception.enums.DemoExceptionEnum;
import cn.stylefeng.roses.kernel.demo.expander.DemoConfigExpander;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.instance.param.FlowableInstanceParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.instance.service.FlowableInstanceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程实例控制器
 *
 * @author fengshuonan
 * @date 2020/4/20 11:12
 */
@RestController
@ApiResource(name = "流程实例控制器")
public class FlowableInstanceController {

    @Resource
    private FlowableInstanceService flowableInstanceService;

    /**
     * 查询流程实例
     *
     * @author fengshuonan
     * @date 2020/4/14 19:46
     */
    @GetResource(name = "查询流程实例", path = "/flowableInstance/page")
    public ResponseData page(FlowableInstanceParam flowableInstanceParam) {
        return new SuccessResponseData(flowableInstanceService.page(flowableInstanceParam));
    }

    /**
     * 查询我的申请
     *
     * @author fengshuonan
     * @date 2020/7/28 11:01
     **/
    @GetResource(name = "查询我的申请", path = "/flowableInstance/my")
    public ResponseData my(FlowableInstanceParam flowableInstanceParam) {
        String startUserId = Convert.toStr(LoginContext.me().getLoginUser().getUserId());
        flowableInstanceParam.setStartUserId(startUserId);
        return new SuccessResponseData(flowableInstanceService.page(flowableInstanceParam));
    }

    /**
     * 查询流程实例的历史任务节点，用于退回时选择节点
     *
     * @author fengshuonan
     * @date 2020/4/27 15:07
     */
    @GetResource(name = "查询流程实例的历史任务节点，用于退回时选择节点", path = "/flowableInstance/hisUserTasks")
    public ResponseData hisUserTasks(@Validated(FlowableInstanceParam.dropDown.class) FlowableInstanceParam flowableInstanceParam) {
        return new SuccessResponseData(flowableInstanceService.hisUserTasks(flowableInstanceParam));
    }

    /**
     * 流程实例的流程图，显示当前节点和走过的节点
     *
     * @author fengshuonan
     * @date 2020/4/28 14:27
     */
    @GetResource(name = "流程实例的流程图，显示当前节点和走过的节点", path = "/flowableInstance/trace")
    public ResponseData trace(@Validated(FlowableInstanceParam.trace.class) FlowableInstanceParam flowableInstanceParam) {
        return new SuccessResponseData(flowableInstanceService.trace(flowableInstanceParam));
    }

    /**
     * 挂起流程实例
     *
     * @author fengshuonan
     * @date 2020/4/29 16:18
     */
    @PostResource(name = "挂起流程实例", path = "/flowableInstance/suspend")
    public ResponseData suspend(@RequestBody @Validated(FlowableInstanceParam.suspend.class) FlowableInstanceParam flowableInstanceParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableInstanceService.activeOrSuspend(flowableInstanceParam, true);
        return new SuccessResponseData();
    }

    /**
     * 激活流程实例
     *
     * @author fengshuonan
     * @date 2020/4/29 16:18
     */
    @PostResource(name = "激活流程实例", path = "/flowableInstance/active")
    public ResponseData active(@RequestBody @Validated(FlowableInstanceParam.active.class) FlowableInstanceParam flowableInstanceParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableInstanceService.activeOrSuspend(flowableInstanceParam, false);
        return new SuccessResponseData();
    }

    /**
     * 流程实例的审批记录
     *
     * @author fengshuonan
     * @date 2020/4/28 14:27
     */
    @GetResource(name = "流程实例的审批记录", path = "/flowableInstance/commentHistory")
    public ResponseData commentHistory(@Validated(FlowableInstanceParam.commentHistory.class) FlowableInstanceParam flowableInstanceParam) {
        return new SuccessResponseData(flowableInstanceService.commentHistory(flowableInstanceParam));
    }

    /**
     * 终止流程实例
     *
     * @author fengshuonan
     * @date 2020/8/11 14:21
     **/
    @PostResource(name = "终止流程实例", path = "/flowableInstance/end")
    public ResponseData end(@RequestBody @Validated(FlowableInstanceParam.end.class) FlowableInstanceParam flowableInstanceParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableInstanceService.end(flowableInstanceParam);
        return new SuccessResponseData();
    }

    /**
     * 获取流程实例中表单填写的数据
     *
     * @author fengshuonan
     * @date 2020/8/11 14:21
     **/
    @GetResource(name = "获取流程实例中表单填写的数据", path = "/flowableInstance/formData")
    public ResponseData formData(@Validated(FlowableInstanceParam.trace.class) FlowableInstanceParam flowableInstanceParam) {
        return new SuccessResponseData(flowableInstanceService.formData(flowableInstanceParam));
    }

    /**
     * 加签人员选择器
     *
     * @author fengshuonan
     * @date 2020/9/11 17:44
     **/
    @GetResource(name = "加签人员选择器", path = "/flowableInstance/addSignUserSelector")
    public ResponseData addSignUserSelector(@Validated(FlowableInstanceParam.trace.class) FlowableInstanceParam flowableInstanceParam) {
        return new SuccessResponseData(flowableInstanceService.addSignUserSelector(flowableInstanceParam));
    }

    /**
     * 减签人员选择器
     *
     * @author fengshuonan
     * @date 2020/9/11 17:44
     **/
    @GetResource(name = "减签人员选择器", path = "/flowableInstance/deleteSignUserSelector")
    public ResponseData deleteSignUserSelector(@Validated(FlowableInstanceParam.trace.class) FlowableInstanceParam flowableInstanceParam) {
        return new SuccessResponseData(flowableInstanceService.deleteSignUserSelector(flowableInstanceParam));
    }

}
