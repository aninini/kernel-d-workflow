package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service;

/**
 * 任务挂起service接口
 *
 * @author fengshuonan
 * @date 2020/8/4 16:37
 **/
public interface FlowableSuspendTaskService {

    /**
     * 挂起（即挂起流程）
     *
     * @param taskId 任务id
     * @author fengshuonan
     * @date 2020/8/4 20:33
     */
    void suspend(String taskId);
}
