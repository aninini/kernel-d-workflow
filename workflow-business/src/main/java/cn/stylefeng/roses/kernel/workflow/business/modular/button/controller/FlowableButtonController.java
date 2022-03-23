package cn.stylefeng.roses.kernel.workflow.business.modular.button.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.button.param.FlowableButtonParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.button.service.FlowableButtonService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 活动节点按钮控制器
 *
 * @author fengshuonan
 * @date 2020/4/17 9:28
 */
@RestController
@ApiResource(name = "工作流按钮管理")
public class FlowableButtonController {

    @Resource
    private FlowableButtonService flowableActButtonService;

    /**
     * 添加活动节点按钮
     *
     * @author fengshuonan
     * @date 2020/4/17 15:42
     */
    @PostResource(name = "添加活动节点按钮", path = "/flowableButton/add")
    public ResponseData add(@RequestBody @Validated(FlowableButtonParam.add.class) FlowableButtonParam flowableButtonParam) {
        flowableActButtonService.add(flowableButtonParam);
        return new SuccessResponseData();
    }

    /**
     * 删除活动节点按钮
     *
     * @author fengshuonan
     * @date 2020/4/17 15:42
     */
    @PostResource(name = "删除活动节点按钮", path = "/flowableButton/delete")
    public ResponseData delete(@RequestBody @Validated(FlowableButtonParam.delete.class) FlowableButtonParam flowableButtonParam) {
        flowableActButtonService.delete(flowableButtonParam);
        return new SuccessResponseData();
    }

    /**
     * 添加活动节点按钮
     *
     * @author fengshuonan
     * @date 2020/4/17 15:43
     */
    @PostResource(name = "添加活动节点按钮", path = "/flowableButton/edit")
    public ResponseData edit(@RequestBody @Validated(FlowableButtonParam.edit.class) FlowableButtonParam flowableButtonParam) {
        flowableActButtonService.edit(flowableButtonParam);
        return new SuccessResponseData();
    }

    /**
     * 查看活动节点按钮
     *
     * @author fengshuonan
     * @date 2020/4/17 15:44
     */
    @GetResource(name = "查看活动节点按钮", path = "/flowableButton/detail")
    public ResponseData detail(@Validated(FlowableButtonParam.detail.class) FlowableButtonParam flowableButtonParam) {
        return new SuccessResponseData(flowableActButtonService.detail(flowableButtonParam));
    }

    /**
     * 根据流程定义查询活动节点按钮列表（用于定义配置按钮）
     *
     * @author fengshuonan
     * @date 2020/4/17 15:32
     */
    @GetResource(name = "根据流程定义查询活动节点按钮列表", path = "/flowableButton/list")
    public ResponseData page(@Validated(FlowableButtonParam.list.class) FlowableButtonParam flowableButtonParam) {
        return new SuccessResponseData(flowableActButtonService.list(flowableButtonParam));
    }

    /**
     * 根据任务id查询当前活动节点按钮
     *
     * @author fengshuonan
     * @date 2020/8/3 16:42
     **/
    @GetResource(name = "根据任务id查询当前活动节点按钮", path = "/flowableButton/trace")
    public ResponseData trace(@Validated(FlowableButtonParam.trace.class) FlowableButtonParam flowableButtonParam) {
        return new SuccessResponseData(flowableActButtonService.trace(flowableButtonParam));
    }

}
