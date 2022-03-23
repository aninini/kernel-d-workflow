package cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.core.utils.PastTimeFormatUtil;
import cn.stylefeng.roses.kernel.workflow.business.modular.instance.result.FlowableInstanceResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.instance.service.FlowableInstanceService;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.result.FlowableDoneTaskResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.factory.FlowableAssigneeFactory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.flowable.task.api.history.HistoricTaskInstance;

import javax.annotation.Resource;
import java.util.List;

/**
 * 已办任务工厂类，用于把flowable返回的实体转换为自定义实体
 *
 * @author fengshuonan
 * @date 2020/4/20 17:26
 */
public class FlowableDoneTaskFactory {

    @Resource
    private static final FlowableInstanceService flowableInstanceService = SpringUtil.getBean(FlowableInstanceService.class);

    /**
     * 根据已办任务集合和默认分页，返回自定义PageResult
     *
     * @author fengshuonan
     * @date 2020/4/21 15:07
     */
    public static PageResult<FlowableDoneTaskResult> pageResult(List<HistoricTaskInstance> taskList,
                                                                Page<FlowableDoneTaskResult> defaultPage) {
        List<FlowableDoneTaskResult> flowableDoneTaskResultList = convertToFlowableDoneTaskResultList(taskList);
        defaultPage.setRecords(flowableDoneTaskResultList);
        return PageResultFactory.createPageResult(defaultPage);
    }

    /**
     * 将已办任务结果转换为自定义结果
     *
     * @author fengshuonan
     * @date 2020/4/21 15:08
     */
    private static List<FlowableDoneTaskResult> convertToFlowableDoneTaskResultList(List<HistoricTaskInstance> taskList) {
        List<FlowableDoneTaskResult> flowableDoneTaskResultList = CollectionUtil.newArrayList();
        taskList.forEach(historicTaskInstance -> {
            FlowableDoneTaskResult flowableDoneTaskResult = convertToFlowableDoneTaskResult(historicTaskInstance);
            flowableDoneTaskResultList.add(flowableDoneTaskResult);
        });
        return flowableDoneTaskResultList;
    }

    /**
     * 将单个已办任务结果转换为自定义结果
     *
     * @author fengshuonan
     * @date 2020/4/21 15:09
     */
    private static FlowableDoneTaskResult convertToFlowableDoneTaskResult(HistoricTaskInstance historicTaskInstance) {
        FlowableDoneTaskResult flowableDoneTaskResult = new FlowableDoneTaskResult();
        flowableDoneTaskResult.setId(historicTaskInstance.getId());
        flowableDoneTaskResult.setName(historicTaskInstance.getName());
        flowableDoneTaskResult.setActivityId(historicTaskInstance.getTaskDefinitionKey());
        flowableDoneTaskResult.setExecutionId(historicTaskInstance.getExecutionId());
        flowableDoneTaskResult.setAssignee(historicTaskInstance.getAssignee());
        flowableDoneTaskResult.setAssigneeName(FlowableAssigneeFactory.getAssigneeNameByUserId(historicTaskInstance.getAssignee()));
        flowableDoneTaskResult.setAssigneeInfo(FlowableAssigneeFactory.getAssigneeInfoByUserId(historicTaskInstance.getAssignee()));
        flowableDoneTaskResult.setPriority(historicTaskInstance.getPriority());
        flowableDoneTaskResult.setCreateTime(historicTaskInstance.getCreateTime());
        flowableDoneTaskResult.setFormatCreateTime(PastTimeFormatUtil.formatPastTime(historicTaskInstance.getCreateTime()));
        flowableDoneTaskResult.setClaimTime(historicTaskInstance.getClaimTime());
        flowableDoneTaskResult.setEndTime(historicTaskInstance.getEndTime());
        flowableDoneTaskResult.setFormatEndTime(PastTimeFormatUtil.formatPastTime(historicTaskInstance.getEndTime()));
        flowableDoneTaskResult.setDuration(DateUtil.formatBetween(historicTaskInstance.getDurationInMillis(), BetweenFormatter.Level.SECOND));
        flowableDoneTaskResult.setDueDate(historicTaskInstance.getDueDate());

        FlowableInstanceResult procIns = flowableInstanceService.detail(historicTaskInstance.getProcessInstanceId());
        flowableDoneTaskResult.setProcIns(procIns);

        return flowableDoneTaskResult;
    }
}
