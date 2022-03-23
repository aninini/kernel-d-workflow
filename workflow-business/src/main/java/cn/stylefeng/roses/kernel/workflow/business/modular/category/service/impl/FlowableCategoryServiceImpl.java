package cn.stylefeng.roses.kernel.workflow.business.modular.category.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.enums.StatusEnum;
import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.workflow.api.exception.enums.FlowableCategoryExceptionEnum;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.entity.FlowableCategory;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.mapper.FlowableCategoryMapper;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.param.FlowableCategoryParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.result.FlowableCategoryResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.service.FlowableCategoryService;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.service.FlowableDefinitionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流程分类service接口实现类
 *
 * @author fengshuonan
 * @date 2020/4/13 14:14
 */
@Service
public class FlowableCategoryServiceImpl extends ServiceImpl<FlowableCategoryMapper, FlowableCategory> implements FlowableCategoryService {

    @Resource
    private FlowableDefinitionService flowableDefinitionService;

    @Override
    public PageResult<FlowableCategory> page(FlowableCategoryParam flowableCategoryParam) {
        LambdaQueryWrapper<FlowableCategory> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(flowableCategoryParam)) {
            //根据名称查询
            if (ObjectUtil.isNotEmpty(flowableCategoryParam.getCategoryName())) {
                queryWrapper.like(FlowableCategory::getCategoryName, flowableCategoryParam.getCategoryName());
            }
            //根据编码查询
            if (ObjectUtil.isNotEmpty(flowableCategoryParam.getCategoryCode())) {
                queryWrapper.like(FlowableCategory::getCategoryCode, flowableCategoryParam.getCategoryCode());
            }
        }

        queryWrapper.eq(FlowableCategory::getCategoryStatus, StatusEnum.ENABLE.getCode());
        queryWrapper.ne(FlowableCategory::getDelFlag, YesOrNotEnum.Y.getCode());

        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(FlowableCategory::getCategorySort);

        Page<FlowableCategory> page = this.page(PageFactory.defaultPage(), queryWrapper);
        return PageResultFactory.createPageResult(page);
    }

    @Override
    public List<FlowableCategory> list(FlowableCategoryParam flowableCategoryParam) {
        LambdaQueryWrapper<FlowableCategory> queryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotNull(flowableCategoryParam)) {
            //根据名称查询
            if (ObjectUtil.isNotEmpty(flowableCategoryParam.getCategoryName())) {
                queryWrapper.eq(FlowableCategory::getCategoryName, flowableCategoryParam.getCategoryName());
            }
            //根据编码查询
            if (ObjectUtil.isNotEmpty(flowableCategoryParam.getCategoryCode())) {
                queryWrapper.eq(FlowableCategory::getCategoryCode, flowableCategoryParam.getCategoryCode());
            }
        }
        queryWrapper.eq(FlowableCategory::getCategoryStatus, StatusEnum.ENABLE.getCode());
        queryWrapper.ne(FlowableCategory::getDelFlag, YesOrNotEnum.Y.getCode());

        //根据排序升序排列，序号越小越在前
        queryWrapper.orderByAsc(FlowableCategory::getCategorySort);
        return this.list(queryWrapper);
    }

    @Override
    public void add(FlowableCategoryParam flowableCategoryParam) {
        //校验参数，检查是否存在相同的名称和编码
        checkParam(flowableCategoryParam, false);
        FlowableCategory flowableCategory = new FlowableCategory();
        BeanUtil.copyProperties(flowableCategoryParam, flowableCategory);
        flowableCategory.setCategoryStatus(StatusEnum.ENABLE.getCode());
        flowableCategory.setDelFlag(YesOrNotEnum.N.getCode());
        this.save(flowableCategory);
    }

    @Override
    public void delete(FlowableCategoryParam flowableCategoryParam) {
        FlowableCategory flowableCategory = this.queryFlowableCategory(flowableCategoryParam);
        String code = flowableCategory.getCategoryCode();
        //该分类下是否有流程定义
        boolean hasDefinition = flowableDefinitionService.hasDefinition(code);
        //只要还有，则不能删
        if (hasDefinition) {
            throw new ServiceException(FlowableCategoryExceptionEnum.CATEGORY_CANNOT_DELETE);
        }
        flowableCategory.setCategoryStatus(StatusEnum.DISABLE.getCode());
        flowableCategory.setDelFlag(YesOrNotEnum.N.getCode());

        this.updateById(flowableCategory);
    }

    @Override
    public void edit(FlowableCategoryParam flowableCategoryParam) {
        FlowableCategory flowableCategory = this.queryFlowableCategory(flowableCategoryParam);
        //校验参数，检查是否存在相同的名称和编码
        checkParam(flowableCategoryParam, true);
        BeanUtil.copyProperties(flowableCategoryParam, flowableCategory);
        //不能修改状态，用修改状态接口修改状态
        flowableCategory.setCategoryStatus(null);
        this.updateById(flowableCategory);
    }

    @Override
    public FlowableCategoryResult detail(FlowableCategoryParam flowableCategoryParam) {
        FlowableCategory flowableCategory = this.queryFlowableCategory(flowableCategoryParam);
        FlowableCategoryResult flowableCategoryResult = new FlowableCategoryResult();
        BeanUtil.copyProperties(flowableCategory, flowableCategoryResult);
        //该分类下是否有流程定义
        boolean hasDefinition = flowableDefinitionService.hasDefinition(flowableCategory.getCategoryCode());
        //有定义则不可编辑名称和编码
        flowableCategoryResult.setWritable(!hasDefinition);
        return flowableCategoryResult;
    }

    @Override
    public String getNameByCode(String categoryCode) {
        LambdaQueryWrapper<FlowableCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FlowableCategory::getCategoryCode, categoryCode)
                .eq(FlowableCategory::getCategoryStatus, StatusEnum.ENABLE.getCode());
        queryWrapper.ne(FlowableCategory::getDelFlag, YesOrNotEnum.Y.getCode());

        FlowableCategory flowableCategory = this.getOne(queryWrapper);
        if (ObjectUtil.isNull(flowableCategory)) {
            throw new ServiceException(FlowableCategoryExceptionEnum.CATEGORY_NOT_EXIST);
        }
        return flowableCategory.getCategoryName();
    }

    /**
     * 校验参数，检查是否存在相同的名称和编码
     *
     * @author fengshuonan
     * @date 2020/3/25 21:23
     */
    private void checkParam(FlowableCategoryParam flowableCategoryParam, boolean isExcludeSelf) {
        Long id = flowableCategoryParam.getCategoryId();
        String name = flowableCategoryParam.getCategoryName();
        String code = flowableCategoryParam.getCategoryCode();

        LambdaQueryWrapper<FlowableCategory> queryWrapperByName = new LambdaQueryWrapper<>();
        queryWrapperByName.eq(FlowableCategory::getCategoryName, name)
                .ne(FlowableCategory::getDelFlag, YesOrNotEnum.Y.getCode());

        LambdaQueryWrapper<FlowableCategory> queryWrapperByCode = new LambdaQueryWrapper<>();
        queryWrapperByCode.eq(FlowableCategory::getCategoryCode, code)
                .ne(FlowableCategory::getDelFlag, YesOrNotEnum.Y.getCode());

        if (isExcludeSelf) {
            queryWrapperByName.ne(FlowableCategory::getCategoryId, id);
            queryWrapperByCode.ne(FlowableCategory::getCategoryId, id);
        }
        int countByName = this.count(queryWrapperByName);
        int countByCode = this.count(queryWrapperByCode);

        if (countByName >= 1) {
            throw new ServiceException(FlowableCategoryExceptionEnum.CATEGORY_NAME_REPEAT);
        }
        if (countByCode >= 1) {
            throw new ServiceException(FlowableCategoryExceptionEnum.CATEGORY_CODE_REPEAT);
        }
    }

    /**
     * 获取流程分类
     *
     * @author fengshuonan
     * @date 2020/4/13 14:16
     */
    private FlowableCategory queryFlowableCategory(FlowableCategoryParam flowableCategoryParam) {
        FlowableCategory flowableCategory = this.getById(flowableCategoryParam.getCategoryId());
        if (ObjectUtil.isNull(flowableCategory)) {
            throw new ServiceException(FlowableCategoryExceptionEnum.CATEGORY_NOT_EXIST);
        }
        return flowableCategory;
    }

}
