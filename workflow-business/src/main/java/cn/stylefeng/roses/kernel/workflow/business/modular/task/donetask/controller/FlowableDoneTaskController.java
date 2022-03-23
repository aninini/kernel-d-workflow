package cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.param.FlowableDoneTaskParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.service.FlowableDoneTaskService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 已办任务控制器
 *
 * @author fengshuonan
 * @date 2020/4/19 16:19
 */
@RestController
@ApiResource(name = "已办任务控制器")
public class FlowableDoneTaskController {

    @Resource
    private FlowableDoneTaskService flowableDoneTaskService;

    /**
     * 查询已办任务
     *
     * @author fengshuonan
     * @date 2020/4/21 14:59
     */
    @GetResource(name = "查询已办任务", path = "/flowableDoneTask/page")
    public ResponseData page(FlowableDoneTaskParam flowableDoneTaskParam) {
        return new SuccessResponseData(flowableDoneTaskService.page(flowableDoneTaskParam));
    }

}
