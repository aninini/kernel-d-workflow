package cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.basic.SimpleRoleInfo;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.factory.FlowableTodoTaskFactory;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.param.FlowableTodoTaskParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.result.FlowableTodoTaskResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.service.FlowableTodoTaskService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 待办任务service接口实现类
 *
 * @author fengshuonan
 * @date 2020/4/19 16:22
 */
@Service
public class FlowableTodoTaskServiceImpl implements FlowableTodoTaskService {

    @Resource
    private TaskService taskService;

    @Override
    public PageResult<FlowableTodoTaskResult> page(FlowableTodoTaskParam flowableTodoTaskParam) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        Long userId = LoginContext.me().getLoginUser().getUserId();

        // 获取用户的角色
        List<String> roleIds = new ArrayList<>();
        List<SimpleRoleInfo> simpleRoleInfoList = LoginContext.me().getLoginUser().getSimpleRoleInfoList();
        if (simpleRoleInfoList != null) {
            roleIds = simpleRoleInfoList.stream().map(SimpleRoleInfo::getRoleId).map(Object::toString).collect(Collectors.toList());
        }

        //查询激活的
        taskQuery.active();
        //候选人或办理人
        taskQuery.taskCandidateOrAssigned(Convert.toStr(userId));
        //候选组（角色），此处判空是为了防止用户无角色时报错（如超级管理员角色是不存在的），一般不会存在此问题
        if (ObjectUtil.isNotEmpty(roleIds)) {
            taskQuery.taskCandidateGroupIn(roleIds);
        }

        if (ObjectUtil.isNotNull(flowableTodoTaskParam)) {
            //流程名称模糊查询
            if (ObjectUtil.isNotEmpty(flowableTodoTaskParam.getProcessName())) {
                taskQuery.processDefinitionNameLike(flowableTodoTaskParam.getProcessName());
            }
            //任务名称模糊查询
            if (ObjectUtil.isNotEmpty(flowableTodoTaskParam.getName())) {
                taskQuery.taskNameLike(flowableTodoTaskParam.getName());
            }
            //流程分类查询
            if (ObjectUtil.isNotEmpty(flowableTodoTaskParam.getCategory())) {
                taskQuery.processCategoryIn(CollectionUtil.newHashSet(flowableTodoTaskParam.getCategory()));
            }
            //创建时间范围查询
            if (ObjectUtil.isAllNotEmpty(flowableTodoTaskParam.getSearchBeginTime(), flowableTodoTaskParam.getSearchEndTime())) {
                taskQuery.taskCreatedAfter(DateUtil.parseDateTime(flowableTodoTaskParam.getSearchBeginTime()))
                        .taskCreatedBefore(DateUtil.parseDateTime(flowableTodoTaskParam.getSearchEndTime()));
            }
            //优先级查询
            if (ObjectUtil.isNotEmpty(flowableTodoTaskParam.getPriority())) {
                taskQuery.taskPriority(flowableTodoTaskParam.getPriority());
            }
        }
        //根据任务创建时间倒序，最新任务在最上面
        taskQuery.orderByTaskCreateTime().desc();
        Page<FlowableTodoTaskResult> defaultPage = PageFactory.defaultPage();
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        List<Task> taskList = taskQuery
                .listPage(Convert.toInt((current - 1) * size), Convert.toInt(size));
        defaultPage.setTotal(taskQuery.count());
        return FlowableTodoTaskFactory.pageResult(taskList, defaultPage);
    }
}
