package cn.stylefeng.roses.kernel.workflow.business.modular.event.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.event.param.FlowableEventParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.event.service.FlowableEventService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程事件控制器
 *
 * @author fengshuonan
 * @date 2020/4/17 14:53
 */
@RestController
@ApiResource(name = "流程事件控制器")
public class FlowableEventController {

    @Resource
    private FlowableEventService flowableEventService;

    /**
     * 添加流程事件
     *
     * @author fengshuonan
     * @date 2020/4/17 15:31
     */
    @PostResource(name = "添加流程事件", path = "/flowableEvent/add")
    public ResponseData add(@RequestBody @Validated(FlowableEventParam.add.class) FlowableEventParam flowableEventParam) {
        flowableEventService.add(flowableEventParam);
        return new SuccessResponseData();
    }

    /**
     * 删除流程事件
     *
     * @author fengshuonan
     * @date 2020/4/17 15:31
     */
    @PostResource(name = "删除流程事件", path = "/flowableEvent/delete")
    public ResponseData delete(@RequestBody @Validated(FlowableEventParam.delete.class) FlowableEventParam flowableEventParam) {
        flowableEventService.delete(flowableEventParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑流程事件
     *
     * @author fengshuonan
     * @date 2020/4/17 15:32
     */
    @PostResource(name = "编辑流程事件", path = "/flowableEvent/edit")
    public ResponseData edit(@RequestBody @Validated(FlowableEventParam.edit.class) FlowableEventParam flowableEventParam) {
        flowableEventService.edit(flowableEventParam);
        return new SuccessResponseData();
    }

    /**
     * 查看流程事件
     *
     * @author fengshuonan
     * @date 2020/4/17 15:32
     */
    @GetResource(name = "查看流程事件", path = "/flowableEvent/detail")
    public ResponseData detail(@Validated(FlowableEventParam.detail.class) FlowableEventParam flowableEventParam) {
        return new SuccessResponseData(flowableEventService.detail(flowableEventParam));
    }

    /**
     * 根据流程定义查询事件列表
     *
     * @author fengshuonan
     * @date 2020/4/17 15:32
     */
    @GetResource(name = "根据流程定义查询事件列表", path = "/flowableEvent/list")
    public ResponseData page(@Validated(FlowableEventParam.list.class) FlowableEventParam flowableEventParam) {
        return new SuccessResponseData(flowableEventService.list(flowableEventParam));
    }
}
