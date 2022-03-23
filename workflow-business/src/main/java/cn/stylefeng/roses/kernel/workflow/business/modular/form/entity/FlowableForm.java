package cn.stylefeng.roses.kernel.workflow.business.modular.form.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程表单实体
 *
 * @author fengshuonan
 * @date 2020/8/14 14:41
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("flw_tz_form")
public class FlowableForm extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "form_id", type = IdType.ASSIGN_ID)
    private Long formId;

    /**
     * 表单id
     */
    @TableField("form_resource_id")
    private Long formResourceId;

    /**
     * 流程定义id
     */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /**
     * 活动节点id
     */
    @TableField("act_id")
    private String actId;

    /**
     * 活动节点名称
     */
    @TableField("act_name")
    private String actName;

    /**
     * 表单节点类型（字典 1启动 2全局 3节点）
     */
    @TableField("node_type")
    private Integer nodeType;

}
