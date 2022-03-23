package cn.stylefeng.roses.kernel.workflow.business.modular.draft.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableDraftExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.modular.draft.entity.FlowableDraft;
import cn.stylefeng.roses.kernel.workflow.business.modular.draft.mapper.FlowableDraftMapper;
import cn.stylefeng.roses.kernel.workflow.business.modular.draft.param.FlowableDraftParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.draft.service.FlowableDraftService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 申请草稿service接口实现类
 *
 * @author fengshuonan
 * @date 2020/9/9 14:59
 **/
@Service
public class FlowableDraftServiceImpl extends ServiceImpl<FlowableDraftMapper, FlowableDraft> implements FlowableDraftService {

    @Override
    public PageResult<FlowableDraft> page(FlowableDraftParam flowableDraftParam) {
        LambdaQueryWrapper<FlowableDraft> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(flowableDraftParam)) {
            //根据流程名称查询
            if (ObjectUtil.isNotEmpty(flowableDraftParam.getProcessName())) {
                queryWrapper.like(FlowableDraft::getProcessName, flowableDraftParam.getProcessName());
            }

            //根据表单分类查询
            if (ObjectUtil.isNotEmpty(flowableDraftParam.getCategory())) {
                queryWrapper.eq(FlowableDraft::getCategory, flowableDraftParam.getCategory());
            }
        }
        //查询自己的
        queryWrapper.eq(FlowableDraft::getCreateUser, LoginContext.me().getLoginUser().getUserId());

        Page<FlowableDraft> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public void addOrUpdate(FlowableDraftParam flowableDraftParam) {
        FlowableDraft flowableDraft = new FlowableDraft();
        BeanUtil.copyProperties(flowableDraftParam, flowableDraft);
        this.saveOrUpdate(flowableDraft);
    }

    @Override
    public void delete(FlowableDraftParam flowableDraftParam) {
        FlowableDraft flowableDraft = this.queryFlowableDraft(flowableDraftParam);
        this.removeById(flowableDraft.getDraftId());
    }

    /**
     * 获取申请草稿
     *
     * @author fengshuonan
     * @date 2020/8/14 15:06
     */
    private FlowableDraft queryFlowableDraft(FlowableDraftParam flowableDraftParam) {
        FlowableDraft flowableDraft = this.getById(flowableDraftParam.getDraftId());
        if (ObjectUtil.isNull(flowableDraft)) {
            throw new ServiceException(FlowableDraftExceptionEnum.DRAFT_NOT_EXIST);
        }
        return flowableDraft;
    }
}
