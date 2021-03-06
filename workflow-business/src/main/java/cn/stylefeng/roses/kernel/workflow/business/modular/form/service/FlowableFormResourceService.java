package cn.stylefeng.roses.kernel.workflow.business.modular.form.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.entity.FlowableFormResource;
import cn.stylefeng.roses.kernel.workflow.business.modular.form.param.FlowableFormResourceParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 表单管理service接口
 *
 * @author fengshuonan
 * @date 2020/8/14 15:06
 **/
public interface FlowableFormResourceService extends IService<FlowableFormResource> {

    /**
     * 查询表单
     *
     * @param flowableFormResourceParam 查询参数
     * @return 查询结果集
     * @author fengshuonan
     * @date 2020/8/14 15:06
     */
    PageResult<FlowableFormResource> page(FlowableFormResourceParam flowableFormResourceParam);

    /**
     * 查询列表
     *
     * @param flowableFormResourceParam 查询参数
     * @return 查询结果集
     * @author fengshuonan
     * @date 2020/8/14 15:06
     */
    List<FlowableFormResource> list(FlowableFormResourceParam flowableFormResourceParam);

    /**
     * 添加表单
     *
     * @param flowableFormResourceParam 添加参数
     * @author fengshuonan
     * @date 2020/8/14 15:06
     */
    void add(FlowableFormResourceParam flowableFormResourceParam);

    /**
     * 删除表单
     *
     * @param flowableFormResourceParam 删除参数
     * @author fengshuonan
     * @date 2020/8/14 15:06
     */
    void delete(FlowableFormResourceParam flowableFormResourceParam);

    /**
     * 编辑表单
     *
     * @param flowableFormResourceParam 编辑参数
     * @author fengshuonan
     * @date 2020/8/14 15:06
     */
    void edit(FlowableFormResourceParam flowableFormResourceParam);

    /**
     * 设计表单
     *
     * @param flowableFormResourceParam 设计参数
     * @author fengshuonan
     * @date 2020/8/14 15:06
     */
    void design(FlowableFormResourceParam flowableFormResourceParam);

    /**
     * 查看表单
     *
     * @param flowableFormResourceParam 查看参数
     * @return 表单结果
     * @author fengshuonan
     * @date 2020/8/14 15:06
     */
    FlowableFormResource detail(FlowableFormResourceParam flowableFormResourceParam);


}
