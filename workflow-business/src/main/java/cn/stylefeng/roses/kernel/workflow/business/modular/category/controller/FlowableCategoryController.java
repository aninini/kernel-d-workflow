package cn.stylefeng.roses.kernel.workflow.business.modular.category.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.param.FlowableCategoryParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.service.FlowableCategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程分类控制器
 *
 * @author fengshuonan
 * @date 2020/4/13 11:24
 */
@RestController
@ApiResource(name = "流程分类控制器")
public class FlowableCategoryController {

    @Resource
    private FlowableCategoryService flowableCategoryService;

    /**
     * 流程分类查询
     *
     * @author fengshuonan
     * @date 2020/4/13 11:25
     */
    @GetResource(name = "流程分类查询", path = "/flowableCategory/page")
    public ResponseData page(FlowableCategoryParam flowableCategoryParam) {
        return new SuccessResponseData(flowableCategoryService.page(flowableCategoryParam));
    }

    /**
     * 流程分类列表
     *
     * @author fengshuonan
     * @date 2020/6/22 16:37
     */
    @GetResource(name = "流程分类列表", path = "/flowableCategory/list")
    public ResponseData list(FlowableCategoryParam flowableCategoryParam) {
        return new SuccessResponseData(flowableCategoryService.list(flowableCategoryParam));
    }

    /**
     * 添加流程分类
     *
     * @author fengshuonan
     * @date 2020/4/13 11:25
     */
    @PostResource(name = "添加流程分类", path = "/flowableCategory/add")
    public ResponseData add(@RequestBody @Validated(FlowableCategoryParam.add.class) FlowableCategoryParam flowableCategoryParam) {
        flowableCategoryService.add(flowableCategoryParam);
        return new SuccessResponseData();
    }

    /**
     * 删除流程分类
     *
     * @author fengshuonan
     * @date 2020/4/13 11:25
     */
    @PostResource(name = "删除流程分类", path = "/flowableCategory/delete")
    public ResponseData delete(@RequestBody @Validated(FlowableCategoryParam.delete.class) FlowableCategoryParam flowableCategoryParam) {
        flowableCategoryService.delete(flowableCategoryParam);
        return new SuccessResponseData();
    }

    /**
     * 编辑流程分类
     *
     * @author fengshuonan
     * @date 2020/4/13 11:25
     */
    @PostResource(name = "编辑流程分类", path = "/flowableCategory/edit")
    public ResponseData edit(@RequestBody @Validated(FlowableCategoryParam.edit.class) FlowableCategoryParam flowableCategoryParam) {
        flowableCategoryService.edit(flowableCategoryParam);
        return new SuccessResponseData();
    }

    /**
     * 查看流程分类
     *
     * @author fengshuonan
     * @date 2020/4/13 11:26
     */
    @GetResource(name = "查看流程分类", path = "/flowableCategory/detail")
    public ResponseData detail(@Validated(FlowableCategoryParam.detail.class) FlowableCategoryParam flowableCategoryParam) {
        return new SuccessResponseData(flowableCategoryService.detail(flowableCategoryParam));
    }
}
