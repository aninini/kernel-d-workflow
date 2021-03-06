package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.rule.exception.enums.defaults.DefaultBusinessExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableHandleTaskExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.core.utils.BpmCommentUtil;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.service.FlowableDefinitionService;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.factory.FlowableAssigneeFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.operator.FlowableCommonOperator;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.service.FlowableAddSignTaskService;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.RuntimeService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 任务加签service接口实现类
 *
 * @author fengshuonan
 * @date 2020/8/4 21:31
 */
@Service
public class FlowableAddSignTaskServiceImpl implements FlowableAddSignTaskService {

    @Resource
    private FlowableDefinitionService flowableDefinitionService;

    @Resource
    private RuntimeService runtimeService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addSign(String taskId, List<String> assigneeList, String comment) {
        //校验任务是否存在
        Task task = FlowableCommonOperator.me().queryTask(taskId);
        //流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        //流程实例id
        String processInstanceId = task.getProcessInstanceId();
        //当前活动节点id
        String currentActId = task.getTaskDefinitionKey();
        //当前活动节点名称（任务名称）
        String currentActName = task.getName();
        //多实例用户任务节点的元素变量名
        String multiInstanceActAssigneeParam = flowableDefinitionService
                .getMultiInstanceActAssigneeParam(processDefinitionId, currentActId);
        //如果元素变量名为空则表示该节点不是会签节点
        if (ObjectUtil.isEmpty(multiInstanceActAssigneeParam)) {
            throw new ServiceException(FlowableHandleTaskExceptionEnum.TASK_ADD_SIGN_ERROR);
        }
        //加签人的姓名
        List<String> assigneeNameList = CollectionUtil.newArrayList();
        //遍历要加签的人
        assigneeList.forEach(assignee -> {
            //获取加签人名称
            String assigneeName = FlowableAssigneeFactory.getAssigneeNameByUserId(assignee);
            assigneeNameList.add(assigneeName);
            //定义参数
            Map<String, Object> assigneeVariables = MapUtil.newHashMap();
            //根据获取的变量名加参数
            assigneeVariables.put(multiInstanceActAssigneeParam, assignee);
            //执行加签操作
            try {
                runtimeService.addMultiInstanceExecution(currentActId, processInstanceId, assigneeVariables);
            } catch (FlowableException e) {
                //抛异常加签失败
                throw new ServiceException(FlowableHandleTaskExceptionEnum.TASK_ADD_SIGN_ERROR);
            } catch (Exception e) {
                //否则的话，可能出现服务器内部异常
                e.printStackTrace();
                throw new ServiceException(DefaultBusinessExceptionEnum.SYSTEM_RUNTIME_ERROR);
            }
        });
        //当前办理人姓名
        String name = LoginContext.me().getLoginUser().getSimpleUserInfo().getRealName();
        //生成加签意见（此处在加签后添加意见，无影响）
        comment = BpmCommentUtil.genAddSignComment(name, assigneeNameList, currentActName, comment);
        //保存意见
        FlowableCommonOperator.me().addComment(taskId, comment);
    }
}
