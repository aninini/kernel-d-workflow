package cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableCategoryExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableShortcutExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.entity.FlowableShortcut;
import cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.mapper.FlowableShortcutMapper;
import cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.node.FlowableShortcutTreeNode;
import cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.param.FlowableShortcutParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.service.FlowableShortcutService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程申请入口service接口实现类
 *
 * @author fengshuonan
 * @date 2020/6/30 10:50
 */
@Service
public class FlowableShortcutServiceImpl extends ServiceImpl<FlowableShortcutMapper, FlowableShortcut> implements FlowableShortcutService {

    @Override
    public PageResult<FlowableShortcut> page(FlowableShortcutParam flowableShortcutParam) {
        LambdaQueryWrapper<FlowableShortcut> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(flowableShortcutParam)) {
            //根据名称查询
            if (ObjectUtil.isNotEmpty(flowableShortcutParam.getShortcutName())) {
                queryWrapper.like(FlowableShortcut::getShortcutName, flowableShortcutParam.getShortcutName());
            }
            //根据分类查询
            if (ObjectUtil.isNotEmpty(flowableShortcutParam.getCategory())) {
                queryWrapper.like(FlowableShortcut::getCategory, flowableShortcutParam.getCategory());
            }
        }

        queryWrapper.ne(FlowableShortcut::getStatus, StatusEnum.DISABLE.getCode());

        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(FlowableShortcut::getSort);
        Page<FlowableShortcut> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<FlowableShortcutTreeNode> list(FlowableShortcutParam flowableShortcutParam) {
        QueryWrapper<FlowableShortcutTreeNode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("b.status", StatusEnum.ENABLE.getCode());
        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc("b.sort");
        return this.baseMapper.list(queryWrapper);
    }

    @Override
    public void add(FlowableShortcutParam flowableShortcutParam) {
        //校验参数，检查是否存在相同的名称和流程定义key
        checkParam(flowableShortcutParam, false);
        FlowableShortcut flowableShortcut = new FlowableShortcut();
        BeanUtil.copyProperties(flowableShortcutParam, flowableShortcut);
        flowableShortcut.setStatus(StatusEnum.ENABLE.getCode());
        flowableShortcut.setDelFlag(YesOrNotEnum.N.getCode());
        this.save(flowableShortcut);
    }

    @Override
    public void delete(FlowableShortcutParam flowableShortcutParam) {
        FlowableShortcut flowableShortcut = this.queryFlowableShortcut(flowableShortcutParam);
        flowableShortcut.setDelFlag(YesOrNotEnum.Y.getCode());
        flowableShortcut.setStatus(StatusEnum.DISABLE.getCode());
        this.removeById(flowableShortcut);
    }

    @Override
    public void delete(String processDefinitionId) {
        LambdaQueryWrapper<FlowableShortcut> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FlowableShortcut::getProcessDefinitionId, processDefinitionId);
        FlowableShortcut flowableShortcut = this.getOne(queryWrapper);
        if (ObjectUtil.isNotNull(flowableShortcut)) {
            flowableShortcut.setDelFlag(YesOrNotEnum.Y.getCode());
            flowableShortcut.setStatus(StatusEnum.DISABLE.getCode());
            this.updateById(flowableShortcut);
        }
    }

    @Override
    public void edit(FlowableShortcutParam flowableShortcutParam) {
        FlowableShortcut flowableShortcut = this.queryFlowableShortcut(flowableShortcutParam);
        //校验参数，检查是否存在相同的名称和流程定义key
        checkParam(flowableShortcutParam, true);
        BeanUtil.copyProperties(flowableShortcutParam, flowableShortcut);
        //不能修改状态，用修改状态接口修改状态
        flowableShortcut.setStatus(null);
        this.updateById(flowableShortcut);
    }

    @Override
    public FlowableShortcut detail(FlowableShortcutParam flowableShortcutParam) {
        return this.queryFlowableShortcut(flowableShortcutParam);
    }

    @Override
    public void changeStatus(String processDefinitionId, Integer status) {
        LambdaQueryWrapper<FlowableShortcut> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FlowableShortcut::getProcessDefinitionId, processDefinitionId);
        FlowableShortcut flowableShortcut = this.getOne(queryWrapper);
        if (ObjectUtil.isNotNull(flowableShortcut)) {
            flowableShortcut.setStatus(status);
            this.updateById(flowableShortcut);
        }
    }

    /**
     * 校验参数，检查是否存在相同的名称和流程定义key
     *
     * @author fengshuonan
     * @date 2020/6/30 12:08
     */
    private void checkParam(FlowableShortcutParam flowableShortcutParam, boolean isExcludeSelf) {
        Long id = flowableShortcutParam.getShortcutId();
        String name = flowableShortcutParam.getShortcutName();
        String processDefinitionId = flowableShortcutParam.getProcessDefinitionId();

        LambdaQueryWrapper<FlowableShortcut> queryWrapperByName = new LambdaQueryWrapper<>();
        queryWrapperByName.eq(FlowableShortcut::getShortcutName, name)
                .ne(FlowableShortcut::getDelFlag, YesOrNotEnum.Y.getCode());

        LambdaQueryWrapper<FlowableShortcut> queryWrapperByProcessInstanceId = new LambdaQueryWrapper<>();
        queryWrapperByProcessInstanceId.eq(FlowableShortcut::getProcessDefinitionId, processDefinitionId)
                .ne(FlowableShortcut::getDelFlag, YesOrNotEnum.Y.getCode());

        if (isExcludeSelf) {
            queryWrapperByName.ne(FlowableShortcut::getShortcutId, id);
            queryWrapperByProcessInstanceId.ne(FlowableShortcut::getShortcutId, id);
        }
        int countByName = this.count(queryWrapperByName);
        int countByKey = this.count(queryWrapperByProcessInstanceId);

        if (countByName >= 1) {
            throw new ServiceException(FlowableCategoryExceptionEnum.CATEGORY_NAME_REPEAT);
        }
        if (countByKey >= 1) {
            throw new ServiceException(FlowableCategoryExceptionEnum.CATEGORY_CODE_REPEAT);
        }
    }

    /**
     * 获取流程申请入口
     *
     * @author fengshuonan
     * @date 2020/6/30 12:03
     */
    private FlowableShortcut queryFlowableShortcut(FlowableShortcutParam flowableShortcutParam) {
        FlowableShortcut flowableShortcut = this.getById(flowableShortcutParam.getShortcutId());
        if (ObjectUtil.isNull(flowableShortcut)) {
            throw new ServiceException(FlowableShortcutExceptionEnum.SHORTCUT_NOT_EXIST);
        }
        return flowableShortcut;
    }

}
