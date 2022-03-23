package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.impl;

import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.workflow.business.core.utils.BpmCommentUtil;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.operator.FlowableCommonOperator;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.FlowableEndTaskService;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 任务终止service接口实现类
 *
 * @author fengshuonan
 * @date 2020/8/4 16:35
 **/
@Service
public class FlowableEndTaskServiceImpl implements FlowableEndTaskService {

    @Resource
    private RuntimeService runtimeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void end(String taskId, String comment) {
        //校验任务是否存在
        Task task = FlowableCommonOperator.me().queryTask(taskId);
        //获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        //获取当前操作人姓名
        String name = LoginContext.me().getLoginUser().getSimpleUserInfo().getRealName();
        //生成终止意见
        comment = BpmCommentUtil.genEndComment(name, comment);
        //添加意见
        FlowableCommonOperator.me().addComment(taskId, comment);
        //结束流程
        runtimeService.deleteProcessInstance(processInstanceId, comment);
    }
}
