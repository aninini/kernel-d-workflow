package cn.stylefeng.roses.kernel.workflow.business.modular.script.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.script.entity.FlowableScript;
import cn.stylefeng.roses.kernel.workflow.business.modular.script.param.FlowableScriptParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 流程脚本service接口
 *
 * @author fengshuonan
 * @date 2020/4/17 9:58
 */
public interface FlowableScriptService extends IService<FlowableScript> {

    /**
     * 查询流程脚本
     *
     * @param flowableScriptParam 查询参数
     * @return 查询结果
     * @author fengshuonan
     * @date 2020/4/17 17:14
     */
    PageResult<FlowableScript> page(FlowableScriptParam flowableScriptParam);

    /**
     * 流程脚本列表
     *
     * @param flowableScriptParam 查询参数
     * @return 脚本列表
     * @author fengshuonan
     * @date 2020/8/13 17:36
     **/
    List<FlowableScript> list(FlowableScriptParam flowableScriptParam);

    /**
     * 添加流程脚本
     *
     * @param flowableScriptParam 添加参数
     * @author fengshuonan
     * @date 2020/4/17 17:14
     */
    void add(FlowableScriptParam flowableScriptParam);

    /**
     * 删除流程脚本
     *
     * @param flowableScriptParam 删除参数
     * @author fengshuonan
     * @date 2020/4/17 17:15
     */
    void delete(FlowableScriptParam flowableScriptParam);

    /**
     * 编辑流程脚本
     *
     * @param flowableScriptParam 编辑参数
     * @author fengshuonan
     * @date 2020/4/17 17:15
     */
    void edit(FlowableScriptParam flowableScriptParam);

    /**
     * 查看流程脚本
     *
     * @param flowableScriptParam 查看参数
     * @return 流程脚本
     * @author fengshuonan
     * @date 2020/4/17 17:15
     */
    FlowableScript detail(FlowableScriptParam flowableScriptParam);
}
