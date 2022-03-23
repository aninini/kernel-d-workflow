package cn.stylefeng.roses.kernel.workflow.business.modular.form.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表单资源实体
 *
 * @author fengshuonan
 * @date 2020/8/14 14:41
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("flw_tz_form_resource")
public class FlowableFormResource extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long formResourceId;

    /**
     * 表单编码
     */
    @TableField("form_resource_code")
    private String formResourceCode;

    /**
     * 表单名称
     */
    @TableField("form_resource_name")
    private String formResourceName;

    /**
     * 表单分类
     */
    @TableField("category")
    private String category;

    /**
     * 表单json数据
     */
    @TableField("form_json")
    private String formJson;

    /**
     * 备注
     */
    @TableField(value = "remark", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String remark;

    /**
     * 状态（字典 1-正常，2-停用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否删除
     */
    @TableField("del_flag")
    private String delFlag;

}
