package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableHandleTaskExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.core.utils.BpmCommentUtil;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.factory.FlowableAssigneeFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.operator.FlowableCommonOperator;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.FlowableDeleteSignTaskService;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 任务减签service接口实现类
 *
 * @author fengshuonan
 * @date 2020/8/4 21:44
 */
@Service
public class FlowableDeleteSignTaskServiceImpl implements FlowableDeleteSignTaskService {

    @Resource
    private RuntimeService runtimeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteSign(String taskId, List<String> assigneeList, String comment) {
        //校验任务是否存在
        Task task = FlowableCommonOperator.me().queryTask(taskId);
        //执行实例id
        String executionId = task.getExecutionId();
        //当前活动节点名称（任务名称）
        String currentActName = task.getName();
        //当前办理人姓名
        String name = LoginContext.me().getLoginUser().getSimpleUserInfo().getRealName();
        //减签人的姓名
        List<String> assigneeNameList = CollectionUtil.newArrayList();
        //遍历要加签的人
        assigneeList.forEach(assignee -> {
            //获取减签人名称
            String assigneeName = FlowableAssigneeFactory.getAssigneeNameByUserId(assignee);
            assigneeNameList.add(assigneeName);
        });
        //生成减签意见
        comment = BpmCommentUtil.genDeleteSignComment(name, assigneeNameList, currentActName, comment);
        //保存意见
        FlowableCommonOperator.me().addComment(taskId, comment);
        //执行减签操作并完成
        try {
            runtimeService.deleteMultiInstanceExecution(executionId, true);
        } catch (FlowableException e) {
            //抛异常减签失败
            throw new ServiceException(FlowableHandleTaskExceptionEnum.TASK_DELETE_SIGN_ERROR);
        } catch (Exception e) {
            //否则的话，可能出现服务器内部异常
            e.printStackTrace();
            throw new ServiceException(DefaultBusinessExceptionEnum.SYSTEM_RUNTIME_ERROR);
        }
    }
}
