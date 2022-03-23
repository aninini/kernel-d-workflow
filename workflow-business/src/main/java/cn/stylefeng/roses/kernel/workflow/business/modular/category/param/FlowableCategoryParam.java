package cn.stylefeng.roses.kernel.workflow.business.modular.category.param;

import cn.stylefeng.roses.kernel.workflow.api.pojo.BaseWorkflowRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 流程分类参数
 *
 * @author fengshuonan
 * @date 2020/4/13 11:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlowableCategoryParam extends BaseWorkflowRequest {

    /**
     * 主键
     */
    @NotNull(message = "id不能为空，请检查id参数", groups = {edit.class, delete.class, detail.class})
    private Long categoryId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空，请检查name参数", groups = {add.class, edit.class})
    private String categoryName;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空，请检查code参数", groups = {add.class, edit.class})
    private String categoryCode;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空，请检查sort参数", groups = {add.class, edit.class})
    private Integer categorySort;

    /**
     * 备注
     */
    private String remark;

}
