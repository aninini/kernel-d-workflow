package cn.stylefeng.roses.kernel.workflow.business.core.listener;

import cn.hutool.log.Log;
import cn.stylefeng.roses.kernel.workflow.business.core.utils.BpmEventUtil;
import org.flowable.common.engine.api.delegate.event.FlowableEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.event.impl.FlowableEntityEventImpl;

/**
 * 全局任务结束监听器
 *
 * @author fengshuonan
 * @date 2020/5/29 17:57
 */
public class GlobalTaskCompletedListener implements FlowableEventListener {

    private static final Log log = Log.get();

    @Override
    public void onEvent(FlowableEvent flowableEvent) {
        FlowableEntityEventImpl flowableEntityEvent = (FlowableEntityEventImpl) flowableEvent;
        DelegateExecution execution = flowableEntityEvent.getExecution();
        String eventType = flowableEntityEvent.getType().name();
        //根据事件类型获取脚本并执行
        BpmEventUtil.getAndExecuteScript(execution, eventType);
        log.info(">>> 任务完成了........");
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    @Override
    public boolean isFireOnTransactionLifecycleEvent() {
        return false;
    }

    @Override
    public String getOnTransaction() {
        return null;
    }
}
