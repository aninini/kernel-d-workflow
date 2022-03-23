package cn.stylefeng.roses.kernel.workflow.business.modular.script.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableScriptExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.modular.script.entity.FlowableScript;
import cn.stylefeng.roses.kernel.workflow.business.modular.script.mapper.FlowableScriptMapper;
import cn.stylefeng.roses.kernel.workflow.business.modular.script.param.FlowableScriptParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.script.service.FlowableScriptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程脚本service接口实现类
 *
 * @author fengshuonan
 * @date 2020/4/17 9:59
 */
@Service
public class FlowableScriptServiceImpl extends ServiceImpl<FlowableScriptMapper, FlowableScript> implements FlowableScriptService {

    @Override
    public PageResult<FlowableScript> page(FlowableScriptParam flowableScriptParam) {
        LambdaQueryWrapper<FlowableScript> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(flowableScriptParam)) {
            if (ObjectUtil.isNotEmpty(flowableScriptParam.getScriptName())) {
                queryWrapper.like(FlowableScript::getScriptName, flowableScriptParam.getScriptName());
            }
        }
        queryWrapper.eq(FlowableScript::getStatus, StatusEnum.ENABLE.getCode());
        queryWrapper.ne(FlowableScript::getDelFlag, YesOrNotEnum.Y.getCode());

        Page<FlowableScript> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<FlowableScript> list(FlowableScriptParam flowableScriptParam) {
        LambdaQueryWrapper<FlowableScript> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(flowableScriptParam)) {
            if (ObjectUtil.isNotEmpty(flowableScriptParam.getScriptName())) {
                queryWrapper.like(FlowableScript::getScriptName, flowableScriptParam.getScriptName());
            }
        }
        queryWrapper.eq(FlowableScript::getStatus, StatusEnum.ENABLE.getCode());
        queryWrapper.ne(FlowableScript::getDelFlag, YesOrNotEnum.Y.getCode());

        return this.list(queryWrapper);
    }

    @Override
    public void add(FlowableScriptParam flowableScriptParam) {
        FlowableScript flowableScript = new FlowableScript();
        BeanUtil.copyProperties(flowableScriptParam, flowableScript);
        flowableScript.setStatus(StatusEnum.ENABLE.getCode());
        flowableScript.setDelFlag(YesOrNotEnum.N.getCode());
        this.save(flowableScript);
    }

    @Override
    public void delete(FlowableScriptParam flowableScriptParam) {
        FlowableScript flowableScript = this.queryFlowableScript(flowableScriptParam);
        flowableScript.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(flowableScript);
    }

    @Override
    public void edit(FlowableScriptParam flowableScriptParam) {
        FlowableScript flowableScript = this.queryFlowableScript(flowableScriptParam);
        BeanUtil.copyProperties(flowableScriptParam, flowableScript);
        //不能修改状态，用修改状态接口修改状态
        flowableScript.setStatus(null);
        this.updateById(flowableScript);
    }

    @Override
    public FlowableScript detail(FlowableScriptParam flowableScriptParam) {
        return this.queryFlowableScript(flowableScriptParam);
    }

    /**
     * 获取流程脚本
     *
     * @author fengshuonan
     * @date 2020/4/26 17:08
     */
    private FlowableScript queryFlowableScript(FlowableScriptParam flowableScriptParam) {
        FlowableScript flowableScript = this.getById(flowableScriptParam.getScriptId());
        if (ObjectUtil.isNull(flowableScript)) {
            throw new ServiceException(FlowableScriptExceptionEnum.SCRIPT_NOT_EXIST);
        }
        return flowableScript;
    }
}
