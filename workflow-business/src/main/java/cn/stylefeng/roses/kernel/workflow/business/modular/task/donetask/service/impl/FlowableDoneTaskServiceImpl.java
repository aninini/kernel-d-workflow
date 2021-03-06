package cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.factory.FlowableDoneTaskFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.param.FlowableDoneTaskParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.result.FlowableDoneTaskResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.service.FlowableDoneTaskService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.flowable.engine.HistoryService;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 已办任务service接口实现类
 *
 * @author fengshuonan
 * @date 2020/4/19 16:22
 */
@Service
public class FlowableDoneTaskServiceImpl implements FlowableDoneTaskService {

    @Resource
    private HistoryService historyService;

    @Override
    public PageResult<FlowableDoneTaskResult> page(FlowableDoneTaskParam flowableDoneTaskParam) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        Long userId = LoginContext.me().getLoginUser().getUserId();
        historicTaskInstanceQuery.taskAssignee(Convert.toStr(userId));
        historicTaskInstanceQuery.finished();
        //根据任务办理时间倒序，最新办完的在最上面
        historicTaskInstanceQuery.orderByHistoricTaskInstanceEndTime().desc();

        if (ObjectUtil.isNotNull(flowableDoneTaskParam)) {
            //流程名称模糊查询
            if (ObjectUtil.isNotEmpty(flowableDoneTaskParam.getProcessName())) {
                historicTaskInstanceQuery.processDefinitionNameLike(flowableDoneTaskParam.getProcessName());
            }
            //任务名称模糊查询
            if (ObjectUtil.isNotEmpty(flowableDoneTaskParam.getName())) {
                historicTaskInstanceQuery.taskNameLike(flowableDoneTaskParam.getName());
            }
            //流程分类查询
            if (ObjectUtil.isNotEmpty(flowableDoneTaskParam.getCategory())) {
                historicTaskInstanceQuery.processCategoryIn(CollectionUtil.newHashSet(flowableDoneTaskParam.getCategory()));
            }
            //创建时间范围查询
            if (ObjectUtil.isAllNotEmpty(flowableDoneTaskParam.getSearchBeginTime(), flowableDoneTaskParam.getSearchEndTime())) {
                historicTaskInstanceQuery.taskCreatedAfter(DateUtil.parseDateTime(flowableDoneTaskParam.getSearchBeginTime()))
                        .taskCreatedBefore(DateUtil.parseDateTime(flowableDoneTaskParam.getSearchEndTime()));
            }
            //优先级查询
            if (ObjectUtil.isNotEmpty(flowableDoneTaskParam.getPriority())) {
                historicTaskInstanceQuery.taskPriority(flowableDoneTaskParam.getPriority());
            }
        }

        Page<FlowableDoneTaskResult> defaultPage = PageFactory.defaultPage();
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        List<HistoricTaskInstance> taskList = historicTaskInstanceQuery
                .listPage(Convert.toInt((current - 1) * size), Convert.toInt(size));
        defaultPage.setTotal(historicTaskInstanceQuery.count());
        return FlowableDoneTaskFactory.pageResult(taskList, defaultPage);
    }
}
