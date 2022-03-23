package cn.stylefeng.roses.kernel.workflow.business.modular.script.param;

import cn.stylefeng.roses.kernel.workflow.api.pojo.BaseWorkflowRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程脚本参数
 *
 * @author fengshuonan
 * @date 2020/4/17 9:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlowableScriptParam extends BaseWorkflowRequest {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long scriptId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空，请检查scriptName参数", groups = {add.class, edit.class})
    private String scriptName;

    /**
     * 类别（字典 1流程脚本 2系统脚本）
     */
    @NotNull(message = "类别不能为空，请检查scriptType参数", groups = {add.class, edit.class})
    private Integer scriptType;

    /**
     * 语言（字典1 groovy)
     */
    @NotNull(message = "脚本语言不能为空，请检查lang参数", groups = {add.class, edit.class})
    private Integer lang;

    /**
     * 内容
     */
    @NotBlank(message = "内容不能为空，请检查content参数", groups = {add.class, edit.class})
    private String content;

    /**
     * 描述
     */
    private String remark;
}
