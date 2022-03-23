package cn.stylefeng.roses.kernel.workflow.business.modular.draft.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 申请草稿实体
 *
 * @author fengshuonan
 * @date 2020/9/9 14:58
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("flw_tz_draft")
public class FlowableDraft extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "draft_id", type = IdType.ASSIGN_ID)
    private Long draftId;

    /**
     * 流程定义id
     */
    @TableField("process_definition_id")
    private String processDefinitionId;

    /**
     * 表单布局数据
     */
    @TableField("form_json")
    private String formJson;

    /**
     * 表单填写数据
     */
    @TableField("form_data")
    private String formData;

    /**
     * 流程名称
     */
    @TableField("process_name")
    private String processName;

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

}
