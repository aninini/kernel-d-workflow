package cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.param.FlowableInstanceTaskParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.service.FlowableInstanceTaskService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 实例任务控制器
 *
 * @author fengshuonan
 * @date 2020/5/25 17:06
 */
@RestController
@ApiResource(name = "实例任务控制器")
public class FlowableInstanceTaskController {

    @Resource
    private FlowableInstanceTaskService flowableInstanceTaskService;

    /**
     * 查询流程实例的任务
     *
     * @author fengshuonan
     * @date 2020/5/25 17:07
     */
    @GetResource(name = "查询流程实例的任务", path = "/flowableInstanceTask/page")
    public ResponseData page(@Validated(FlowableInstanceTaskParam.page.class) FlowableInstanceTaskParam flowableInstanceTaskParam) {
        return new SuccessResponseData(flowableInstanceTaskService.page(flowableInstanceTaskParam));
    }

}
