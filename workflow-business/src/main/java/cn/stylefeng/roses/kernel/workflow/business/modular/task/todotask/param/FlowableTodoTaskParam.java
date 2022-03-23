package cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.param;

import cn.stylefeng.roses.kernel.workflow.api.pojo.BaseWorkflowRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 待办任务参数
 *
 * @author fengshuonan
 * @date 2020/4/20 9:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlowableTodoTaskParam extends BaseWorkflowRequest {

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
