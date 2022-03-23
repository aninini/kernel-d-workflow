package cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程申请入口表
 *
 * @author fengshuonan
 * @date 2020/6/30 10:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("flw_tz_shortcut")
public class FlowableShortcut extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "shortcut_id", type = IdType.ASSIGN_ID)
    private Long shortcutId;

    /**
     * 流程定义id
     */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /**
     * 名称
     */
    @TableField("shortcut_name")
    private String shortcutName;

    /**
     * 分类编码
     */
    @TableField("category")
    private String category;

    /**
     * 分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 定义版本
     */
    @TableField("version")
    private Integer version;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 状态（字典 1正常 2停用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否删除
     */
    @TableField("del_flag")
    private String delFlag;

}
