package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.controller;

import cn.stylefeng.roses.kernel.demo.exception.DemoException;
import cn.stylefeng.roses.kernel.demo.exception.enums.DemoExceptionEnum;
import cn.stylefeng.roses.kernel.demo.expander.DemoConfigExpander;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.param.FlowableHandleTaskParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.FlowableHandleTaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 任务办理控制器
 *
 * @author fengshuonan
 * @date 2020/8/4 21:27
 */
@RestController
@ApiResource(name = "任务办理控制器")
public class FlowableHandleTaskController {

    @Resource
    private FlowableHandleTaskService flowableHandleTaskService;

    @PostResource(name = "开始", path = "/flowableHandleTask/start")
    public ResponseData start(@RequestBody @Validated(FlowableHandleTaskParam.start.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.start(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @PostResource(name = "提交", path = "/flowableHandleTask/submit")
    public ResponseData submit(@RequestBody @Validated(FlowableHandleTaskParam.submit.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.submit(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @PostResource(name = "保存", path = "/flowableHandleTask/save")
    public ResponseData save(@RequestBody @Validated(FlowableHandleTaskParam.add.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.save(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @PostResource(name = "退回", path = "/flowableHandleTask/back")
    public ResponseData back(@RequestBody @Validated(FlowableHandleTaskParam.back.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.back(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @PostResource(name = "流转", path = "/flowableHandleTask/turn")
    public ResponseData turn(@RequestBody @Validated(FlowableHandleTaskParam.turn.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.turn(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @PostResource(name = "委托", path = "/flowableHandleTask/entrust")
    public ResponseData entrust(@RequestBody @Validated(FlowableHandleTaskParam.entrust.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.entrust(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @PostResource(name = "跳转", path = "/flowableHandleTask/jump")
    public ResponseData jump(@RequestBody @Validated(FlowableHandleTaskParam.jump.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.jump(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @PostResource(name = "终止", path = "/flowableHandleTask/end")
    public ResponseData end(@RequestBody @Validated(FlowableHandleTaskParam.end.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.end(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @PostResource(name = "挂起", path = "/flowableHandleTask/suspend")
    public ResponseData suspend(@RequestBody @Validated(FlowableHandleTaskParam.suspend.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.suspend(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @PostResource(name = "加签", path = "/flowableHandleTask/addSign")
    public ResponseData addSign(@RequestBody @Validated(FlowableHandleTaskParam.addSign.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.addSign(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @PostResource(name = "减签", path = "/flowableHandleTask/deleteSign")
    public ResponseData deleteSign(@RequestBody @Validated(FlowableHandleTaskParam.deleteSign.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableHandleTaskService.deleteSign(flowableHandleTaskParam);
        return new SuccessResponseData();
    }

    @GetResource(name = "获取任务数据", path = "/flowableHandleTask/taskData")
    public ResponseData taskData(@Validated(FlowableHandleTaskParam.trace.class) FlowableHandleTaskParam flowableHandleTaskParam) {
        return new SuccessResponseData(flowableHandleTaskService.taskData(flowableHandleTaskParam));
    }
}
