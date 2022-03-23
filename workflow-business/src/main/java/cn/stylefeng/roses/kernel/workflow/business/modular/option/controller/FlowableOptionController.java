package cn.stylefeng.roses.kernel.workflow.business.modular.option.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.option.param.FlowableOptionParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.option.service.FlowableOptionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程选项控制器，在流程定义部署时自动插入
 *
 * @author fengshuonan
 * @date 2020/4/17 16:31
 */
@RestController
@ApiResource(name = "流程选项控制器")
public class FlowableOptionController {

    @Resource
    private FlowableOptionService flowableOptionService;

    /**
     * 编辑流程选项
     *
     * @author fengshuonan
     * @date 2020/4/17 16:34
     */
    @PostResource(name = "编辑流程选项", path = "/flowableOption/edit")
    public ResponseData edit(@RequestBody @Validated(FlowableOptionParam.edit.class) FlowableOptionParam flowableOptionParam) {
        flowableOptionService.edit(flowableOptionParam);
        return new SuccessResponseData();
    }

    /**
     * 查看流程选项
     *
     * @author fengshuonan
     * @date 2020/4/17 16:34
     */
    @GetResource(name = "查看流程选项", path = "/flowableOption/detail")
    public ResponseData detail(@Validated(FlowableOptionParam.detail.class) FlowableOptionParam flowableOptionParam) {
        return new SuccessResponseData(flowableOptionService.detail(flowableOptionParam));
    }

    /**
     * 根据流程定义查询选项列表
     *
     * @author fengshuonan
     * @date 2020/4/17 16:34
     */
    @GetResource(name = "根据流程定义查询选项列表", path = "/flowableOption/list")
    public ResponseData list(@Validated(FlowableOptionParam.list.class) FlowableOptionParam flowableOptionParam) {
        return new SuccessResponseData(flowableOptionService.list(flowableOptionParam));
    }

}
