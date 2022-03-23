package cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.param.FlowableTodoTaskParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.todotask.result.FlowableTodoTaskResult;

/**
 * 待办任务service接口
 *
 * @author fengshuonan
 * @date 2020/4/19 16:21
 */
public interface FlowableTodoTaskService {

    /**
     * 查询当前登录用户的待办任务
     *
     * @param flowableTodoTaskParam 查询参数
     * @return 查询结果
     * @author fengshuonan
     * @date 2020/4/20 10:02
     */
    PageResult<FlowableTodoTaskResult> page(FlowableTodoTaskParam flowableTodoTaskParam);

}
