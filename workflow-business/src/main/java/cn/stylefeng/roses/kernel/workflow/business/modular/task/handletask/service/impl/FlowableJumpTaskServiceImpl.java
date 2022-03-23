package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.impl;

import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.workflow.business.core.utils.BpmCommentUtil;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.operator.FlowableCommonOperator;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.FlowableJumpTaskService;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 任务跳转service接口实现类
 *
 * @author fengshuonan
 * @date 2020/8/4 16:36
 **/
@Service
public class FlowableJumpTaskServiceImpl implements FlowableJumpTaskService {

    @Resource
    private RuntimeService runtimeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void jump(String taskId, String targetActId, String targetActName, String comment) {
        //校验任务是否存在
        Task task = FlowableCommonOperator.me().queryTask(taskId);
        //当前节点id
        String currentActId = task.getTaskDefinitionKey();
        //获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        //获取当前操作人姓名
        String name = LoginContext.me().getLoginUser().getSimpleUserInfo().getRealName();
        //生成跳转意见
        comment = BpmCommentUtil.genJumpComment(name, targetActName, comment);
        //添加意见
        FlowableCommonOperator.me().addComment(taskId, comment);
        //执行跳转操作
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(processInstanceId)
                .moveActivityIdTo(currentActId, targetActId).changeState();
    }
}
