package cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.param;

import cn.stylefeng.roses.kernel.workflow.api.pojo.BaseWorkflowRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 已办任务参数
 *
 * @author fengshuonan
 * @date 2020/4/21 15:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlowableDoneTaskParam extends BaseWorkflowRequest {

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 分类
     */
    private String category;

    /**
     * 优先级
     */
    private Integer priority;

}
