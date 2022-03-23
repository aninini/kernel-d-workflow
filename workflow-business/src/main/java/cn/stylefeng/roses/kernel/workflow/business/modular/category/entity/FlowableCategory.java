package cn.stylefeng.roses.kernel.workflow.business.modular.category.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程分类表
 *
 * @author fengshuonan
 * @date 2020/4/13 11:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("flw_tz_category")
public class FlowableCategory extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "category_id", type = IdType.ASSIGN_ID)
    private Long categoryId;

    /**
     * 名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 编码
     */
    @TableField("category_code")
    private String categoryCode;

    /**
     * 排序
     */
    @TableField("category_sort")
    private Integer categorySort;

    /**
     * 备注
     */
    @TableField(value = "remark", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String remark;

    /**
     * 状态（1-正常，2-停用）
     */
    @TableField("category_status")
    private Integer categoryStatus;

    /**
     * 是否删除：Y-删除，N-未删除
     */
    @TableField("del_flag")
    private String delFlag;

}
