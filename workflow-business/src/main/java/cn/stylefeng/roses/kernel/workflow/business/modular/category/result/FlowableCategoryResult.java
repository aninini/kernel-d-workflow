package cn.stylefeng.roses.kernel.workflow.business.modular.category.result;

import lombok.Data;

/**
 * 流程分类结果集
 *
 * @author fengshuonan
 * @date 2020/4/13 11:23
 */
@Data
public class FlowableCategoryResult {

    /**
     * 主键
     */
    private Long categoryId;

    /**
     * 名称
     */
    private String categoryName;

    /**
     * 编码
     */
    private String categoryCode;

    /**
     * 排序
     */
    private Integer categorySort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（1-正常，2-停用）
     */
    private Integer categoryStatus;

    /**
     * 是否可编辑名称编码，当该分类下有流程定义时不可编辑
     */
    private boolean writable;

}
