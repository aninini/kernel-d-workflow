package cn.stylefeng.roses.kernel.workflow.business.modular.draft.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.draft.param.FlowableDraftParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.draft.service.FlowableDraftService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 申请草稿控制器
 *
 * @author fengshuonan
 * @date 2020/9/9 14:57
 **/
@RestController
@ApiResource(name = "申请草稿控制器")
public class FlowableDraftController {

    @Resource
    private FlowableDraftService flowableDraftService;

    /**
     * 申请草稿查询
     *
     * @author fengshuonan
     * @date 2020/8/14 15:02
     **/
    @GetResource(name = "申请草稿查询", path = "/flowableDraft/page")
    public ResponseData page(FlowableDraftParam flowableDraftParam) {
        return new SuccessResponseData(flowableDraftService.page(flowableDraftParam));
    }

    /**
     * 添加/编辑申请草稿
     *
     * @author fengshuonan
     * @date 2020/8/14 14:57
     **/
    @PostResource(name = "添加/编辑申请草稿", path = "/flowableDraft/addOrUpdate")
    public ResponseData addOrUpdate(@RequestBody @Validated(FlowableDraftParam.add.class) FlowableDraftParam flowableDraftParam) {
        flowableDraftService.addOrUpdate(flowableDraftParam);
        return new SuccessResponseData();
    }

    /**
     * 申请草稿删除，提交成功删除，和主动删除
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     **/
    @PostResource(name = "申请草稿删除，提交成功删除，和主动删除", path = "/flowableDraft/delete")
    public ResponseData delete(@RequestBody @Validated(FlowableDraftParam.delete.class) FlowableDraftParam flowableDraftParam) {
        flowableDraftService.delete(flowableDraftParam);
        return new SuccessResponseData();
    }

}
