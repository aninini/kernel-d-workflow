package cn.stylefeng.roses.kernel.workflow.business.modular.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableFormResourceExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.entity.FlowableFormResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.mapper.FlowableFormResourceMapper;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.param.FlowableFormResourceParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.service.FlowableFormResourceService;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.service.FlowableFormService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 表单管理service接口实现类
 *
 * @author fengshuonan
 * @date 2020/8/14 15:06
 */
@Service
public class FlowableFormResourceServiceImpl extends ServiceImpl<FlowableFormResourceMapper, FlowableFormResource> implements FlowableFormResourceService {

    @Resource(name = "gunsFlowableFormService")
    private FlowableFormService flowableFormService;

    @Override
    public PageResult<FlowableFormResource> page(FlowableFormResourceParam flowableFormResourceParam) {
        LambdaQueryWrapper<FlowableFormResource> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(flowableFormResourceParam)) {
            //根据表单名称查询
            if (ObjectUtil.isNotEmpty(flowableFormResourceParam.getFormResourceName())) {
                queryWrapper.like(FlowableFormResource::getFormResourceName, flowableFormResourceParam.getFormResourceName());
            }

            //根据表单编码查询
            if (ObjectUtil.isNotEmpty(flowableFormResourceParam.getFormResourceCode())) {
                queryWrapper.like(FlowableFormResource::getFormResourceCode, flowableFormResourceParam.getFormResourceCode());
            }

            //根据表单分类查询
            if (ObjectUtil.isNotEmpty(flowableFormResourceParam.getCategory())) {
                queryWrapper.eq(FlowableFormResource::getCategory, flowableFormResourceParam.getCategory());
            }

        }
        queryWrapper.eq(FlowableFormResource::getStatus, StatusEnum.ENABLE.getCode());
        queryWrapper.ne(FlowableFormResource::getDelFlag, YesOrNotEnum.Y.getCode());

        Page<FlowableFormResource> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<FlowableFormResource> list(FlowableFormResourceParam flowableFormResourceParam) {
        LambdaQueryWrapper<FlowableFormResource> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(flowableFormResourceParam)) {
            //根据表单名称查询
            if (ObjectUtil.isNotEmpty(flowableFormResourceParam.getFormResourceName())) {
                queryWrapper.like(FlowableFormResource::getFormResourceName, flowableFormResourceParam.getFormResourceName());
            }

            //根据表单编码查询
            if (ObjectUtil.isNotEmpty(flowableFormResourceParam.getFormResourceCode())) {
                queryWrapper.like(FlowableFormResource::getFormResourceCode, flowableFormResourceParam.getFormResourceCode());
            }

            //根据表单分类查询
            if (ObjectUtil.isNotEmpty(flowableFormResourceParam.getCategory())) {
                queryWrapper.eq(FlowableFormResource::getCategory, flowableFormResourceParam.getCategory());
            }

        }

        queryWrapper.eq(FlowableFormResource::getStatus, StatusEnum.ENABLE.getCode());
        queryWrapper.ne(FlowableFormResource::getDelFlag, YesOrNotEnum.Y.getCode());

        return this.list(queryWrapper);
    }

    @Override
    public void add(FlowableFormResourceParam flowableFormResourceParam) {
        //校验参数
        checkParam(flowableFormResourceParam, false);
        FlowableFormResource flowableFormResource = new FlowableFormResource();
        BeanUtil.copyProperties(flowableFormResourceParam, flowableFormResource);
        flowableFormResource.setStatus(StatusEnum.ENABLE.getCode());
        flowableFormResource.setDelFlag(YesOrNotEnum.N.getCode());
        this.save(flowableFormResource);
    }

    @Override
    public void delete(FlowableFormResourceParam flowableFormResourceParam) {
        FlowableFormResource flowableFormResource = this.queryFlowableFormResource(flowableFormResourceParam);
        Long formResourceId = flowableFormResource.getFormResourceId();
        //该表单是否关联流程定义
        boolean hasDefinition = flowableFormService.hasDefinition(formResourceId);
        //只要还有，则不能删
        if (hasDefinition) {
            throw new ServiceException(FlowableFormResourceExceptionEnum.FORM_CANNOT_DELETE);
        }
        flowableFormResource.setStatus(StatusEnum.DISABLE.getCode());
        flowableFormResource.setDelFlag(YesOrNotEnum.Y.getCode());
        this.updateById(flowableFormResource);
    }

    @Override
    public void edit(FlowableFormResourceParam flowableFormResourceParam) {
        FlowableFormResource flowableFormResource = this.queryFlowableFormResource(flowableFormResourceParam);
        //校验参数
        checkParam(flowableFormResourceParam, true);
        BeanUtil.copyProperties(flowableFormResourceParam, flowableFormResource);
        //不能修改状态，用修改状态接口修改状态
        flowableFormResource.setStatus(null);
        this.updateById(flowableFormResource);
    }

    @Override
    public void design(FlowableFormResourceParam flowableFormResourceParam) {
        FlowableFormResource flowableFormResource = this.queryFlowableFormResource(flowableFormResourceParam);
        String formJson = flowableFormResourceParam.getFormJson();
        flowableFormResource.setFormJson(formJson);
        this.updateById(flowableFormResource);
    }

    @Override
    public FlowableFormResource detail(FlowableFormResourceParam flowableFormResourceParam) {
        return this.queryFlowableFormResource(flowableFormResourceParam);
    }

    /**
     * 校验参数
     *
     * @author fengshuonan
     * @date 2020/8/14 15:06
     */
    private void checkParam(FlowableFormResourceParam flowableFormResourceParam, boolean isExcludeSelf) {
        Long id = flowableFormResourceParam.getFormResourceId();
        String name = flowableFormResourceParam.getFormResourceName();
        String code = flowableFormResourceParam.getFormResourceCode();

        //构建带name和code的查询条件
        LambdaQueryWrapper<FlowableFormResource> queryWrapperByName = new LambdaQueryWrapper<>();
        queryWrapperByName.eq(FlowableFormResource::getFormResourceName, name)
                .ne(FlowableFormResource::getDelFlag, YesOrNotEnum.N.getCode());

        LambdaQueryWrapper<FlowableFormResource> queryWrapperByCode = new LambdaQueryWrapper<>();
        queryWrapperByCode.eq(FlowableFormResource::getFormResourceCode, code)
                .ne(FlowableFormResource::getDelFlag, YesOrNotEnum.N.getCode());

        //如果排除自己，则增加查询条件主键id不等于本条id
        if (isExcludeSelf) {
            queryWrapperByName.ne(FlowableFormResource::getFormResourceId, id);
            queryWrapperByCode.ne(FlowableFormResource::getFormResourceId, id);
        }

        //查询重复记录的数量
        int countByName = this.count(queryWrapperByName);
        int countByCode = this.count(queryWrapperByCode);

        //如果存在重复的记录，抛出异常，直接返回前端
        if (countByName >= 1) {
            throw new ServiceException(FlowableFormResourceExceptionEnum.FORM_RESOURCE_NAME_REPEAT);
        }
        if (countByCode >= 1) {
            throw new ServiceException(FlowableFormResourceExceptionEnum.FORM_RESOURCE_CODE_REPEAT);
        }
    }

    /**
     * 获取表单
     *
     * @author fengshuonan
     * @date 2020/8/14 15:06
     */
    private FlowableFormResource queryFlowableFormResource(FlowableFormResourceParam flowableFormResourceParam) {
        FlowableFormResource flowableFormResource = this.getById(flowableFormResourceParam.getFormResourceId());
        if (ObjectUtil.isNull(flowableFormResource)) {
            throw new ServiceException(FlowableFormResourceExceptionEnum.FORM_RESOURCE_NOT_EXIST);
        }
        return flowableFormResource;
    }
}
