package cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.param.FlowableShortcutParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.service.FlowableShortcutService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程申请入口控制器
 *
 * @author fengshuonan
 * @date 2020/6/30 10:03
 */
@RestController
@ApiResource(name = "流程申请入口控制器")
public class FlowableShortcutController {

    @Resource
    private FlowableShortcutService flowableShortcutService;

    /**
     * 流程申请入口查询
     *
     * @author fengshuonan
     * @date 2020/6/30 11:18
     */
    @GetResource(name = "流程申请入口查询", path = "/flowableShortcut/page")
    public ResponseData page(FlowableShortcutParam flowableShortcutParam) {
        return new SuccessResponseData(flowableShortcutService.page(flowableShortcutParam));
    }

    /**
     * 流程申请入口列表
     *
     * @author fengshuonan
     * @date 2020/6/30 11:19
     */
    @GetResource(name = "流程申请入口列表", path = "/flowableShortcut/list")
    public ResponseData list(FlowableShortcutParam flowableShortcutParam) {
        return new SuccessResponseData(flowableShortcutService.list(flowableShortcutParam));
    }

    /**
     * 添加流程申请入口
     *
     * @author fengshuonan
     * @date 2020/6/30 11:19
     */
    @PostResource(name = "添加流程申请入口", path = "/flowableShortcut/add")
    public ResponseData add(@RequestBody @Validated(FlowableShortcutParam.add.class) FlowableShortcutParam flowableShortcutParam) {
        flowableShortcutService.add(flowableShortcutParam);
        return new SuccessResponseData();
    }

    /**
     * 删除流程申请入口
     *
     * @author fengshuonan
     * @date 2020/6/30 11:19
     */
    @PostResource(name = "删除流程申请入口", path = "/flowableShortcut/delete")
    public ResponseData delete(@RequestBody @Validated(FlowableShortcutParam.delete.class) FlowableShortcutParam flowableShortcutParam) {
        flowableShortcutService.delete(flowableShortcutParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑流程申请入口
     *
     * @author fengshuonan
     * @date 2020/6/30 11:19
     */
    @PostResource(name = "编辑流程申请入口", path = "/flowableShortcut/edit")
    public ResponseData edit(@RequestBody @Validated(FlowableShortcutParam.edit.class) FlowableShortcutParam flowableShortcutParam) {
        flowableShortcutService.edit(flowableShortcutParam);
        return new SuccessResponseData();
    }

    /**
     * 查看流程申请入口
     *
     * @author fengshuonan
     * @date 2020/6/30 11:20
     */
    @GetResource(name = "查看流程申请入口", path = "/flowableShortcut/detail")
    public ResponseData detail(@Validated(FlowableShortcutParam.detail.class) FlowableShortcutParam flowableShortcutParam) {
        return new SuccessResponseData(flowableShortcutService.detail(flowableShortcutParam));
    }

}
