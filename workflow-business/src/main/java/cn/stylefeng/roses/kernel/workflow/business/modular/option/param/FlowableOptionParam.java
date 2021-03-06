package cn.stylefeng.roses.kernel.workflow.business.modular.option.param;

import cn.stylefeng.roses.kernel.validator.api.validators.flag.FlagValue;
import cn.stylefeng.roses.kernel.workflow.api.pojo.BaseWorkflowRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程事件参数
 *
 * @author fengshuonan
 * @date 2020/4/17 14:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlowableOptionParam extends BaseWorkflowRequest {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long optionId;

    /**
     * 流程定义id
     */
    @NotBlank(message = "流程定义id不能为空，请检查processDefinitionId参数", groups = {edit.class, list.class})
    private String processDefinitionId;

    /**
     * 标题规则
     */
    private String title;

    /**
     * 自动完成第一个任务（Y-是，N-否）
     */
    @NotBlank(message = "自动完成第一个任务不能为空，请检查jumpFirst参数", groups = {edit.class})
    @FlagValue(message = "自动完成第一个任务格式错误，正确格式应该Y或者N，请检查jumpFirst参数", groups = {edit.class})
    private String jumpFirst;

    /**
     * 跳过相同处理人（Y-是，N-否）
     */
    @NotBlank(message = "跳过相同处理人不能为空，请检查smartComplete参数", groups = {edit.class})
    @FlagValue(message = "跳过相同处理人格式错误，正确格式应该Y或者N，请检查smartComplete参数", groups = {edit.class})
    private String smartComplete;
}
