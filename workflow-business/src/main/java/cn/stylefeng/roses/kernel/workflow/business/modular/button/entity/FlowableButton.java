package cn.stylefeng.roses.kernel.workflow.business.modular.button.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 活动节点按钮表
 *
 * @author fengshuonan
 * @date 2020/4/17 10:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("flw_tz_button")
public class FlowableButton extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "button_id", type = IdType.ASSIGN_ID)
    private Long buttonId;

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
     * 提交（Y-是，N-否）
     */
    @TableField("submit_flag")
    private String submitFlag;

    /**
     * 保存（Y-是，N-否）
     */
    @TableField("save_flag")
    private String saveFlag;

    /**
     * 退回（Y-是，N-否）
     */
    @TableField("back_flag")
    private String backFlag;

    /**
     * 转办（Y-是，N-否）
     */
    @TableField("turn_flag")
    private String turnFlag;

    /**
     * 指定（Y-是，N-否）
     */
    @TableField("next_flag")
    private String nextFlag;

    /**
     * 委托（Y-是，N-否）
     */
    @TableField("entrust_flag")
    private String entrustFlag;

    /**
     * 终止（Y-是，N-否）
     */
    @TableField("end_flag")
    private String endFlag;

    /**
     * 追踪（Y-是，N-否）
     */
    @TableField("trace_flag")
    private String traceFlag;

    /**
     * 挂起（Y-是，N-否）
     */
    @TableField("suspend_flag")
    private String suspendFlag;

    /**
     * 跳转（Y-是，N-否）
     */
    @TableField("jump_flag")
    private String jumpFlag;

    /**
     * 加签（Y-是，N-否）
     */
    @TableField("add_sign_flag")
    private String addSignFlag;

    /**
     * 减签（Y-是，N-否）
     */
    @TableField("delete_sign_flag")
    private String deleteSignFlag;

}
