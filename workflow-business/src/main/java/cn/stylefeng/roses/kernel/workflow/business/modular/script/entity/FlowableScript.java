package cn.stylefeng.roses.kernel.workflow.business.modular.script.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程脚本表
 *
 * @author fengshuonan
 * @date 2020/4/17 9:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("flw_tz_script")
public class FlowableScript extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "script_id", type = IdType.ASSIGN_ID)
    private Long scriptId;

    /**
     * 名称
     */
    @TableField("script_name")
    private String scriptName;

    /**
     * 类别（字典 1流程脚本 2系统脚本）
     */
    @TableField("script_type")
    private Integer scriptType;

    /**
     * 语言（字典1 groovy)
     */
    @TableField("lang")
    private Integer lang;

    /**
     * 脚本内容
     */
    @TableField("content")
    private String content;

    /**
     * 描述
     */
    @TableField(value = "remark", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String remark;

    /**
     * 状态（1-正常,2-停用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否删除
     */
    @TableField("del_flag")
    private String delFlag;

}
