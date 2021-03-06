package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableHandleTaskExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.FlowableDataTaskService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 获取任务数据Service接口实现类
 *
 * @author fengshuonan
 * @date 2020/8/16 17:57
 */
@Service
public class FlowableDataTaskServiceImpl implements FlowableDataTaskService {

    private final String FORM_DATA_VARIABLE_NAME = "formData";

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Override
    public Map<String, Object> taskData(String taskId) {
        Map<String, Object> variables = MapUtil.newHashMap();
        //获取运行中的任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //如果运行中的任务不存在，则表示任务已结束，通过historyService获取
        if (ObjectUtil.isNull(task)) {
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
            //如果历史的任务也不存在，则抛异常
            if (ObjectUtil.isNull(historicTaskInstance)) {
                throw new ServiceException(FlowableHandleTaskExceptionEnum.TASK_NOT_EXIST);
            }
            //获取流程实例id
            String processInstanceId = historicTaskInstance.getProcessInstanceId();
            //获取其历史变量集合
            List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId)
                    .list();
            historicVariableInstanceList.forEach(historicVariableInstance -> {
                if (FORM_DATA_VARIABLE_NAME.equals(historicVariableInstance.getVariableName())) {
                    variables.put(historicVariableInstance.getVariableName(), historicVariableInstance.getValue());
                }
            });
        } else {
            //获取流程实例id
            String processInstanceId = task.getProcessInstanceId();
            Map<String, Object> runtimeVariables = runtimeService.getVariables(processInstanceId);
            runtimeVariables.forEach((key, value) -> {
                if (FORM_DATA_VARIABLE_NAME.equals(key)) {
                    variables.put(key, value);
                }
            });
        }
        return variables;
    }
}
