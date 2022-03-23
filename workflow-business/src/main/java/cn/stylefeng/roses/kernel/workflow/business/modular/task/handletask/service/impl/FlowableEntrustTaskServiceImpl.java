package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.impl;

import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.workflow.business.core.utils.BpmCommentUtil;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.factory.FlowableAssigneeFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.operator.FlowableCommonOperator;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.FlowableEntrustTaskService;
import org.flowable.engine.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 任务委托service接口实现类
 *
 * @author fengshuonan
 * @date 2020/8/4 16:35
 **/
@Service
public class FlowableEntrustTaskServiceImpl implements FlowableEntrustTaskService {

    @Resource
    private TaskService taskService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void entrust(String taskId, String assignee, String comment) {
        //校验任务是否存在
        FlowableCommonOperator.me().queryTask(taskId);
        //要委托的人的姓名
        String assigneeName = FlowableAssigneeFactory.getAssigneeNameByUserId(assignee);
        //获取当前操作人姓名
        String name = LoginContext.me().getLoginUser().getSimpleUserInfo().getRealName();
        //生成委托意见
        comment = BpmCommentUtil.genEntrustComment(name, assigneeName, comment);
        //添加意见
        FlowableCommonOperator.me().addComment(taskId, comment);
        //执行委托操作
        taskService.delegateTask(taskId, assignee);
    }
}
