package cn.stylefeng.roses.kernel.workflow.business.modular.definition.controller;

import cn.stylefeng.roses.kernel.demo.exception.DemoException;
import cn.stylefeng.roses.kernel.demo.exception.enums.DemoExceptionEnum;
import cn.stylefeng.roses.kernel.demo.expander.DemoConfigExpander;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.param.FlowableDefinitionParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.service.FlowableDefinitionService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 流程定义控制器
 *
 * @author fengshuonan
 * @date 2020/4/14 17:49
 */
@RestController
@ApiResource(name = "流程定义控制器")
public class FlowableDefinitionController {

    @Resource
    private FlowableDefinitionService flowableDefinitionService;

    /**
     * 查询流程定义
     *
     * @author fengshuonan
     * @date 2020/4/14 19:46
     */
    @GetResource(name = "查询流程定义", path = "/flowableDefinition/page")
    public ResponseData page(FlowableDefinitionParam flowableDefinitionParam) {
        return new SuccessResponseData(flowableDefinitionService.page(flowableDefinitionParam));
    }

    /**
     * 部署流程定义
     *
     * @author fengshuonan
     * @date 2020/4/14 19:46
     */
    @PostResource(name = "部署流程定义", path = "/flowableDefinition/deploy")
    public ResponseData deploy(@RequestBody @Validated(FlowableDefinitionParam.deploy.class) FlowableDefinitionParam flowableDefinitionParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableDefinitionService.deploy(flowableDefinitionParam);
        return new SuccessResponseData();
    }

    /**
     * 删除流程定义，根据版本删除，级联删除流程实例和相关任务
     *
     * @author fengshuonan
     * @date 2020/4/14 19:46
     */
    @PostResource(name = "删除流程定义", path = "/flowableDefinition/delete")
    public ResponseData delete(@RequestBody @Validated(FlowableDefinitionParam.delete.class) FlowableDefinitionParam flowableDefinitionParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableDefinitionService.delete(flowableDefinitionParam);
        return new SuccessResponseData();
    }

    /**
     * 导出流程文件
     *
     * @author fengshuonan
     * @date 2020/4/15 9:47
     */
    @GetResource(name = "导出流程文件", path = "/flowableDefinition/export", produces = MediaType.APPLICATION_XML_VALUE)
    public void export(@Validated(FlowableDefinitionParam.export.class) FlowableDefinitionParam flowableDefinitionParam, HttpServletResponse response) {
        flowableDefinitionService.export(flowableDefinitionParam, response);
    }

    /**
     * 映射流程定义，将已部署的流程映射到模型
     *
     * @author fengshuonan
     * @date 2020/4/14 19:46
     */
    @PostResource(name = "映射流程定义", path = "/flowableDefinition/mapping")
    public ResponseData mapping(@RequestBody @Validated(FlowableDefinitionParam.mapping.class) FlowableDefinitionParam flowableDefinitionParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableDefinitionService.mapping(flowableDefinitionParam);
        return new SuccessResponseData();
    }

    /**
     * 挂起流程定义
     *
     * @author fengshuonan
     * @date 2020/4/15 17:17
     */
    @PostResource(name = "挂起流程定义", path = "/flowableDefinition/suspend")
    public ResponseData suspend(@RequestBody @Validated(FlowableDefinitionParam.suspend.class) FlowableDefinitionParam flowableDefinitionParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableDefinitionService.activeOrSuspend(flowableDefinitionParam, true);
        return new SuccessResponseData();
    }

    /**
     * 激活流程定义
     *
     * @author fengshuonan
     * @date 2020/4/15 17:17
     */
    @PostResource(name = "激活流程定义", path = "/flowableDefinition/active")
    public ResponseData active(@RequestBody @Validated(FlowableDefinitionParam.active.class) FlowableDefinitionParam flowableDefinitionParam) {
        // 演示环境开启，则不允许操作
        if (DemoConfigExpander.getDemoEnvFlag()) {
            throw new DemoException(DemoExceptionEnum.DEMO_OPERATE);
        }
        flowableDefinitionService.activeOrSuspend(flowableDefinitionParam, false);
        return new SuccessResponseData();
    }

    /**
     * 流程定义的流程图
     *
     * @author fengshuonan
     * @date 2020/4/15 17:17
     */
    @GetResource(name = "流程定义的流程图", path = "/flowableDefinition/trace")
    public ResponseData trace(@Validated(FlowableDefinitionParam.trace.class) FlowableDefinitionParam flowableDefinitionParam) {
        return new SuccessResponseData(flowableDefinitionService.trace(flowableDefinitionParam));
    }

    /**
     * 流程定义的用户任务节点，用于跳转时选择节点
     *
     * @author fengshuonan
     * @date 2020/4/26 11:19
     */
    @GetResource(name = "流程定义的用户任务节点", path = "/flowableDefinition/userTasks")
    public ResponseData userTasks(@Validated(FlowableDefinitionParam.trace.class) FlowableDefinitionParam flowableDefinitionParam) {
        return new SuccessResponseData(flowableDefinitionService.userTasks(flowableDefinitionParam));
    }

}
