package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service;

/**
 * 任务终止service接口
 *
 * @author fengshuonan
 * @date 2020/8/4 16:35
 **/
public interface FlowableEndTaskService {

    /**
     * 终止任务（即终止流程）
     *
     * @param taskId  任务id
     * @param comment 终止意见
     * @author fengshuonan
     * @date 2020/8/4 20:18
     */
    void end(String taskId, String comment);
}
