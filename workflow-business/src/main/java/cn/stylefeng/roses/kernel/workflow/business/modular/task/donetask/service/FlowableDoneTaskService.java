package cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.param.FlowableDoneTaskParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.donetask.result.FlowableDoneTaskResult;

/**
 * 已办任务service接口
 *
 * @author fengshuonan
 * @date 2020/4/19 16:21
 */
public interface FlowableDoneTaskService {

    /**
     * 查询当前登录用户的已办任务
     *
     * @param flowableDoneTaskParam 查询参数
     * @return 查询结果
     * @author fengshuonan
     * @date 2020/4/21 15:01
     */
    PageResult<FlowableDoneTaskResult> page(FlowableDoneTaskParam flowableDoneTaskParam);

}
