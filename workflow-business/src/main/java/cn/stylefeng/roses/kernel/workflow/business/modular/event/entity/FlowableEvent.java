package cn.stylefeng.roses.kernel.workflow.business.modular.event.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程事件表
 *
 * @author fengshuonan
 * @date 2020/4/17 14:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("flw_tz_event")
public class FlowableEvent extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "event_id", type = IdType.ASSIGN_ID)
    private Long eventId;

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
     * 事件节点类型（字典 1全局 2节点）
     */
    @TableField("node_type")
    private Integer nodeType;

    /**
     * 名称
     */
    @TableField("event_name")
    private String eventName;

    /**
     * 类型（见事件类型字典）
     */
    @TableField("event_type")
    private String eventType;

    /**
     * 脚本
     */
    @TableField("script")
    private String script;

    /**
     * 执行顺序（越小越先执行）
     */
    @TableField("exec_sort")
    private String execSort;

    /**
     * 备注
     */
    @TableField(value = "remark", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String remark;

}
