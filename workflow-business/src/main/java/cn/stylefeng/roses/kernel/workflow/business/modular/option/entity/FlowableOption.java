package cn.stylefeng.roses.kernel.workflow.business.modular.option.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程选项表
 *
 * @author fengshuonan
 * @date 2020/4/17 16:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("flw_tz_option")
public class FlowableOption extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "option_id", type = IdType.ASSIGN_ID)
    private Long optionId;

    /**
     * 流程定义id
     */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /**
     * 标题规则
     */
    @TableField("title")
    private String title;

    /**
     * 自动完成第一个任务（Y-是，N-否）
     */
    @TableField("jump_first")
    private String jumpFirst;

    /**
     * 跳过相同处理人（Y-是，N-否）
     */
    @TableField("smart_complete")
    private String smartComplete;

}
