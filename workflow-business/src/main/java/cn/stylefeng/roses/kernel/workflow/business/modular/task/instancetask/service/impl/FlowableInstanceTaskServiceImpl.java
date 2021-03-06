package cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.factory.FlowableInstanceTaskFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.param.FlowableInstanceTaskParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.result.FlowableInstanceTaskResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.service.FlowableInstanceTaskService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.flowable.engine.HistoryService;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实例任务Service接口实现类
 *
 * @author fengshuonan
 * @date 2020/5/25 17:19
 */
@Service
public class FlowableInstanceTaskServiceImpl implements FlowableInstanceTaskService {

    @Resource
    private HistoryService historyService;

    @Override
    public PageResult<FlowableInstanceTaskResult> page(FlowableInstanceTaskParam flowableInstanceTaskParam) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        if (ObjectUtil.isNotNull(flowableInstanceTaskParam)) {
            if (ObjectUtil.isNotEmpty(flowableInstanceTaskParam.getProcessInstanceId())) {
                //根据实例查询
                historicTaskInstanceQuery.processInstanceId(flowableInstanceTaskParam.getProcessInstanceId());
            }

            if (ObjectUtil.isNotEmpty(flowableInstanceTaskParam.getName())) {
                //根据任务名称模糊查询
                historicTaskInstanceQuery.taskNameLike(flowableInstanceTaskParam.getName());
            }

            if (ObjectUtil.isNotEmpty(flowableInstanceTaskParam.getFinished())) {
                //根据是否结束查询
                if (flowableInstanceTaskParam.getFinished()) {
                    historicTaskInstanceQuery.finished();
                } else {
                    historicTaskInstanceQuery.unfinished();
                }
            }
        }
        //根据任务创建时间倒序，最新创建的任务在最上面
        historicTaskInstanceQuery.orderByHistoricTaskInstanceStartTime().desc();
        Page<FlowableInstanceTaskResult> defaultPage = PageFactory.defaultPage();
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        List<HistoricTaskInstance> taskList = historicTaskInstanceQuery
                .listPage(Convert.toInt((current - 1) * size), Convert.toInt(size));
        defaultPage.setTotal(historicTaskInstanceQuery.count());
        return FlowableInstanceTaskFactory.pageResult(taskList, defaultPage);
    }
}
