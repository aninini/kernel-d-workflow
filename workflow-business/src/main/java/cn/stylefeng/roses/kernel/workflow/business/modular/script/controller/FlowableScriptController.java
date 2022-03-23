package cn.stylefeng.roses.kernel.workflow.business.modular.script.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.script.param.FlowableScriptParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.script.service.FlowableScriptService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程脚本控制器
 *
 * @author fengshuonan
 * @date 2020/4/17 9:38
 */
@RestController
@ApiResource(name = "流程脚本控制器")
public class FlowableScriptController {

    @Resource
    private FlowableScriptService flowableScriptService;

    /**
     * 查询流程脚本
     *
     * @author fengshuonan
     * @date 2020/4/17 16:47
     */
    @GetResource(name = "查询流程脚本", path = "/flowableScript/page")
    public ResponseData page(FlowableScriptParam flowableScriptParam) {
        return new SuccessResponseData(flowableScriptService.page(flowableScriptParam));
    }

    /**
     * 流程脚本列表
     *
     * @author fengshuonan
     * @date 2020/4/17 16:47
     */
    @GetResource(name = "流程脚本列表", path = "/flowableScript/list")
    public ResponseData list(FlowableScriptParam flowableScriptParam) {
        return new SuccessResponseData(flowableScriptService.list(flowableScriptParam));
    }

    /**
     * 添加流程脚本
     *
     * @author fengshuonan
     * @date 2020/4/17 16:48
     */
    @PostResource(name = "添加流程脚本", path = "/flowableScript/add")
    public ResponseData add(@RequestBody @Validated(FlowableScriptParam.add.class) FlowableScriptParam flowableScriptParam) {
        flowableScriptService.add(flowableScriptParam);
        return new SuccessResponseData();
    }

    /**
     * 删除流程脚本
     *
     * @author fengshuonan
     * @date 2020/4/17 17:10
     */
    @PostResource(name = "删除流程脚本", path = "/flowableScript/delete")
    public ResponseData delete(@RequestBody @Validated(FlowableScriptParam.delete.class) FlowableScriptParam flowableScriptParam) {
        flowableScriptService.delete(flowableScriptParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑流程脚本
     *
     * @author fengshuonan
     * @date 2020/4/17 17:10
     */
    @PostResource(name = "编辑流程脚本", path = "/flowableScript/edit")
    public ResponseData edit(@RequestBody @Validated(FlowableScriptParam.edit.class) FlowableScriptParam flowableScriptParam) {
        flowableScriptService.edit(flowableScriptParam);
        return new SuccessResponseData();
    }

    /**
     * 查看流程脚本
     *
     * @author fengshuonan
     * @date 2020/4/17 17:11
     */
    @GetResource(name = "查看流程脚本", path = "/flowableScript/detail")
    public ResponseData detail(@Validated(FlowableScriptParam.detail.class) FlowableScriptParam flowableScriptParam) {
        return new SuccessResponseData(flowableScriptService.detail(flowableScriptParam));
    }

}
