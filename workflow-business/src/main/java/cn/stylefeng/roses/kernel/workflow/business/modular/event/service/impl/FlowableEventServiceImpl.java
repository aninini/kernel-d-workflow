package cn.stylefeng.roses.kernel.workflow.business.modular.event.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.workflow.api.enums.EventNodeTypeEnum;
import cn.stylefeng.roses.kernel.workflow.api.enums.FormNodeTypeEnum;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableEventExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.modular.event.entity.FlowableEvent;
import cn.stylefeng.roses.kernel.workflow.business.modular.event.mapper.FlowableEventMapper;
import cn.stylefeng.roses.kernel.workflow.business.modular.event.param.FlowableEventParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.event.service.FlowableEventService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程事件service接口实现类
 *
 * @author fengshuonan
 * @date 2020/4/17 14:58
 */
@Service
public class FlowableEventServiceImpl extends ServiceImpl<FlowableEventMapper, FlowableEvent> implements FlowableEventService {

    @Override
    public void add(FlowableEventParam flowableEventParam) {
        //校验参数
        checkParam(flowableEventParam);
        FlowableEvent flowableEvent = new FlowableEvent();
        BeanUtil.copyProperties(flowableEventParam, flowableEvent);
        this.save(flowableEvent);
    }

    @Override
    public void delete(FlowableEventParam flowableEventParam) {
        FlowableEvent flowableEvent = this.queryFlowableEvent(flowableEventParam);
        //真删除
        this.removeById(flowableEvent.getEventId());
    }

    @Override
    public void edit(FlowableEventParam flowableEventParam) {
        FlowableEvent flowableEvent = this.queryFlowableEvent(flowableEventParam);
        //校验参数
        checkParam(flowableEventParam);
        BeanUtil.copyProperties(flowableEventParam, flowableEvent);
        this.updateById(flowableEvent);
    }

    @Override
    public FlowableEvent detail(FlowableEventParam flowableEventParam) {
        return this.queryFlowableEvent(flowableEventParam);
    }

    @Override
    public List<FlowableEvent> list(FlowableEventParam flowableEventParam) {
        String processDefinitionId = flowableEventParam.getProcessDefinitionId();
        LambdaQueryWrapper<FlowableEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FlowableEvent::getProcessDefinitionId, processDefinitionId);
        return this.list(queryWrapper);
    }

    @Override
    public void delete(String processDefinitionId) {
        LambdaQueryWrapper<FlowableEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FlowableEvent::getProcessDefinitionId, processDefinitionId);
        this.remove(queryWrapper);
    }

    @Override
    public List<String> getScript(String processDefinitionId, String actId, String eventType) {
        List<String> resultList = CollectionUtil.newArrayList();
        if (ObjectUtil.isAllNotEmpty(processDefinitionId, actId, eventType)) {
            //查询全局配置的事件类型，升序排列
            LambdaQueryWrapper<FlowableEvent> queryWrapperGlobal = new LambdaQueryWrapper<>();
            queryWrapperGlobal.and(o -> o.and(a -> a.eq(FlowableEvent::getProcessDefinitionId, processDefinitionId)
                    .eq(FlowableEvent::getNodeType, EventNodeTypeEnum.GLOBAL.getCode()))
                    .or(b -> b.eq(FlowableEvent::getActId, actId)
                            .eq(FlowableEvent::getNodeType, EventNodeTypeEnum.NODE.getCode())))
                    .eq(FlowableEvent::getEventType, eventType)
                    .orderByAsc(FlowableEvent::getExecSort);
            //多条按照执行顺序排序
            this.list(queryWrapperGlobal).forEach(flowableEvent -> {
                resultList.add(flowableEvent.getScript());
            });
            return resultList;
        }
        return null;
    }

    /**
     * 校验参数
     *
     * @author fengshuonan
     * @date 2020/6/10 15:57
     */
    private void checkParam(FlowableEventParam flowableEventParam) {
        //类型（字典 1全局 2节点）
        Integer type = flowableEventParam.getNodeType();
        if (FormNodeTypeEnum.NODE.getCode().equals(type)) {
            if (ObjectUtil.isEmpty(flowableEventParam.getActId())) {
                throw new ServiceException(FlowableEventExceptionEnum.ACT_ID_EMPTY);
            }
            if (ObjectUtil.isEmpty(flowableEventParam.getActName())) {
                throw new ServiceException(FlowableEventExceptionEnum.ACT_NAME_EMPTY);
            }
        }
    }

    /**
     * 获取流程事件
     *
     * @author fengshuonan
     * @date 2020/4/26 16:37
     */
    private FlowableEvent queryFlowableEvent(FlowableEventParam flowableEventParam) {
        FlowableEvent flowableEvent = this.getById(flowableEventParam.getEventId());
        if (ObjectUtil.isNull(flowableEvent)) {
            throw new ServiceException(FlowableEventExceptionEnum.EVENT_NOT_EXIST);
        }
        return flowableEvent;
    }
}
