package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service;

import java.util.Map;

/**
 * 获取任务数据Service接口
 *
 * @author fengshuonan
 * @date 2020/8/16 17:57
 */
public interface FlowableDataTaskService {

    /**
     * 根据任务id获取数据
     *
     * @param taskId 任务id
     * @return 数据
     * @author fengshuonan
     * @date 2020/8/16 17:58
     */
    Map<String, Object> taskData(String taskId);
}
