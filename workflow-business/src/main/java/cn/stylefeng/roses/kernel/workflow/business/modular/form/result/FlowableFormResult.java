package cn.stylefeng.roses.kernel.workflow.business.modular.form.result;

import cn.stylefeng.roses.kernel.workflow.business.modular.form.entity.FlowableFormResource;
import lombok.Data;

/**
 * 流程表单结果集
 *
 * @author fengshuonan
 * @date 2020/8/14 14:41
 **/
@Data
public class FlowableFormResult {

    /**
     * 主键
     */
    private Long formId;

    /**
     * 表单id
     */
    private Long formResourceId;

    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 活动节点id
     */
    private String actId;

    /**
     * 活动节点名称
     */
    private String actName;

    /**
     * 表单节点类型（字典 1启动 2全局 3节点）
     */
    private Integer nodeType;

    /**
     * 表单信息
     */
    private FlowableFormResource flowableFormResource;
}
