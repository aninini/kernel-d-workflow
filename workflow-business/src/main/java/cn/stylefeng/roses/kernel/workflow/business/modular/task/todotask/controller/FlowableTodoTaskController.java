package cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.param.FlowableTodoTaskParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.service.FlowableTodoTaskService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 待办任务控制器
 *
 * @author fengshuonan
 * @date 2020/4/19 16:20
 */
@RestController
@ApiResource(name = "待办任务控制器")
public class FlowableTodoTaskController {

    @Resource
    private FlowableTodoTaskService flowableTodoTaskService;

    /**
     * 查询待办任务
     *
     * @author fengshuonan
     * @date 2020/4/17 16:47
     */
    @GetResource(name = "查询待办任务", path = "/flowableTodoTask/page")
    public ResponseData page(FlowableTodoTaskParam flowableTodoTaskParam) {
        return new SuccessResponseData(flowableTodoTaskService.page(flowableTodoTaskParam));
    }

}
