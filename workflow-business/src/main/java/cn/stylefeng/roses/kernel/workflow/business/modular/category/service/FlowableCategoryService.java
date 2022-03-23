package cn.stylefeng.roses.kernel.workflow.business.modular.category.service;


import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.entity.FlowableCategory;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.param.FlowableCategoryParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.result.FlowableCategoryResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 流程分类service接口
 *
 * @author fengshuonan
 * @date 2020/4/13 11:28
 */
public interface FlowableCategoryService extends IService<FlowableCategory> {

    /**
     * 流程分类查询
     *
     * @param flowableCategoryParam 查询参数
     * @return 查询结果
     * @author fengshuonan
     * @date 2020/4/13 11:29
     */
    PageResult<FlowableCategory> page(FlowableCategoryParam flowableCategoryParam);

    /**
     * 流程分类列表
     *
     * @param flowableCategoryParam 查询分类参数
     * @return 查询结果集
     * @author fengshuonan
     * @date 2020/6/22 16:37
     */
    List<FlowableCategory> list(FlowableCategoryParam flowableCategoryParam);

    /**
     * 添加流程分类
     *
     * @param flowableCategoryParam 添加参数
     * @author fengshuonan
     * @date 2020/4/13 11:29
     */
    void add(FlowableCategoryParam flowableCategoryParam);

    /**
     * 删除流程分类
     *
     * @param flowableCategoryParam 删除参数
     * @author fengshuonan
     * @date 2020/4/13 11:29
     */
    void delete(FlowableCategoryParam flowableCategoryParam);

    /**
     * 编辑流程分类
     *
     * @param flowableCategoryParam 编辑参数
     * @author fengshuonan
     * @date 2020/4/13 11:29
     */
    void edit(FlowableCategoryParam flowableCategoryParam);

    /**
     * 查看流程分类
     *
     * @param flowableCategoryParam 查看参数
     * @return 分类结果
     * @author fengshuonan
     * @date 2020/4/13 14:13
     */
    FlowableCategoryResult detail(FlowableCategoryParam flowableCategoryParam);

    /**
     * 根据流程分类编码获取名称
     *
     * @param categoryCode 分类编码
     * @return 分类名称
     * @author fengshuonan
     * @date 2020/6/29 17:47
     */
    String getNameByCode(String categoryCode);

}

