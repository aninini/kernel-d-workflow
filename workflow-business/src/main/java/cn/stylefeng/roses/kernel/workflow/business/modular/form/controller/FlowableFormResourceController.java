package cn.stylefeng.roses.kernel.workflow.business.modular.form.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.param.FlowableFormResourceParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.service.FlowableFormResourceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 表单管理控制器
 *
 * @author fengshuonan
 * @date 2020/8/14 14:57
 **/
@RestController
@ApiResource(name = "表单管理控制器")
public class FlowableFormResourceController {

    @Resource
    private FlowableFormResourceService flowableFormResourceService;

    /**
     * 表单查询
     *
     * @author fengshuonan
     * @date 2020/8/14 15:02
     **/
    @GetResource(name = "表单查询", path = "/flowableFormResource/page")
    public ResponseData page(FlowableFormResourceParam flowableFormResourceParam) {
        return new SuccessResponseData(flowableFormResourceService.page(flowableFormResourceParam));
    }

    /**
     * 表单查询
     *
     * @author fengshuonan
     * @date 2020/8/17 18:00
     **/
    @GetResource(name = "表单查询", path = "/flowableFormResource/list")
    public ResponseData list(FlowableFormResourceParam flowableFormResourceParam) {
        return new SuccessResponseData(flowableFormResourceService.list(flowableFormResourceParam));
    }

    /**
     * 添加表单
     *
     * @author fengshuonan
     * @date 2020/8/14 14:57
     **/
    @PostResource(name = "添加表单", path = "/flowableFormResource/add")
    public ResponseData add(@RequestBody @Validated(FlowableFormResourceParam.add.class) FlowableFormResourceParam flowableFormResourceParam) {
        flowableFormResourceService.add(flowableFormResourceParam);
        return new SuccessResponseData();
    }

    /**
     * 表单删除
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     **/
    @PostResource(name = "表单删除", path = "/flowableFormResource/delete")
    public ResponseData delete(@RequestBody @Validated(FlowableFormResourceParam.delete.class) FlowableFormResourceParam flowableFormResourceParam) {
        flowableFormResourceService.delete(flowableFormResourceParam);
        return new SuccessResponseData();
    }

    /**
     * 表单编辑
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     */
    @PostResource(name = "表单编辑", path = "/flowableFormResource/edit")
    public ResponseData edit(@RequestBody @Validated(FlowableFormResourceParam.edit.class) FlowableFormResourceParam flowableFormResourceParam) {
        flowableFormResourceService.edit(flowableFormResourceParam);
        return new SuccessResponseData();
    }

    /**
     * 表单设计
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     */
    @PostResource(name = "表单设计", path = "/flowableFormResource/design")
    public ResponseData design(@RequestBody @Validated(FlowableFormResourceParam.edit.class) FlowableFormResourceParam flowableFormResourceParam) {
        flowableFormResourceService.design(flowableFormResourceParam);
        return new SuccessResponseData();
    }

    /**
     * 表单查看
     *
     * @author fengshuonan
     * @date 2020/8/14 14:59
     */
    @GetResource(name = "表单查看", path = "/flowableFormResource/detail")
    public ResponseData detail(@Validated(FlowableFormResourceParam.detail.class) FlowableFormResourceParam flowableFormResourceParam) {
        return new SuccessResponseData(flowableFormResourceService.detail(flowableFormResourceParam));
    }

}
