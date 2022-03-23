package cn.stylefeng.roses.kernel.workflow.business.modular.button.param;

import cn.stylefeng.roses.kernel.workflow.api.pojo.BaseWorkflowRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 活动节点按钮参数
 *
 * @author fengshuonan
 * @date 2020/4/17 9:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlowableButtonParam extends BaseWorkflowRequest {

    /**
     * 主键
     */
    @NotNull(message = "buttonId不能为空，请检查buttonId参数", groups = {edit.class, delete.class, detail.class})
    private Long buttonId;

    /**
     * 流程定义id
     */
    @NotBlank(message = "流程定义id不能为空，请检查processDefinitionId参数", groups = {add.class, edit.class, list.class})
    private String processDefinitionId;

    /**
     * 活动节点id
     */
    @NotBlank(message = "活动节点actId不能为空，请检查actId参数", groups = {add.class, edit.class, trace.class})
    private String actId;

    /**
     * 活动节点名称
     */
    @NotBlank(message = "活动节点名称不能为空，请检查actName参数", groups = {add.class, edit.class})
    private String actName;

    /**
     * 提交（Y-是，N-否）
     */
    private String submitFlag;

    /**
     * 保存（Y-是，N-否）
     */
    private String saveFlag;

    /**
     * 退回（Y-是，N-否）
     */
    private String backFlag;

    /**
     * 转办（Y-是，N-否）
     */
    private String turnFlag;

    /**
     * 指定（Y-是，N-否）
     */
    private String nextFlag;

    /**
     * 委托（Y-是，N-否）
     */
    private String entrustFlag;

    /**
     * 终止（Y-是，N-否）
     */
    private String endFlag;

    /**
     * 追踪（Y-是，N-否）
     */
    private String traceFlag;

    /**
     * 挂起（Y-是，N-否）
     */
    private String suspendFlag;

    /**
     * 跳转（Y-是，N-否）
     */
    private String jumpFlag;

    /**
     * 加签（Y-是，N-否）
     */
    private String addSignFlag;

    /**
     * 减签（Y-是，N-否）
     */
    private String deleteSignFlag;

}
