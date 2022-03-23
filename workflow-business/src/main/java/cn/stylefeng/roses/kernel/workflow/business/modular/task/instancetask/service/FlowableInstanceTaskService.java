package cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.param.FlowableInstanceTaskParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.task.instancetask.result.FlowableInstanceTaskResult;

/**
 * 实例任务Service接口
 *
 * @author fengshuonan
 * @date 2020/5/25 17:12
 */
public interface FlowableInstanceTaskService {

    /**
     * 查询实例任务
     *
     * @param flowableInstanceTaskParam 查询参数
     * @return 查询结果
     * @author fengshuonan
     * @date 2020/5/25 17:13
     */
    PageResult<FlowableInstanceTaskResult> page(FlowableInstanceTaskParam flowableInstanceTaskParam);
    
}
