package cn.stylefeng.roses.kernel.workflow.business.modular.definition.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.param.FlowableDefinitionParam;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.result.FlowableDefinitionResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.result.FlowableUserTaskResult;
import com.fasterxml.jackson.databind.JsonNode;
import org.flowable.engine.repository.ProcessDefinition;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 流程定义service接口
 *
 * @author fengshuonan
 * @date 2020/4/14 19:26
 */
public interface FlowableDefinitionService {

    /**
     * 查询流程定义
     *
     * @param flowableDefinitionParam 查询参数
     * @return 查询结果
     * @author fengshuonan
     * @date 2020/4/14 19:30
     */
    PageResult<FlowableDefinitionResult> page(FlowableDefinitionParam flowableDefinitionParam);

    /**
     * 部署流程
     *
     * @param flowableDefinitionParam 部署参数
     * @author fengshuonan
     * @date 2020/4/14 19:41
     */
    void deploy(FlowableDefinitionParam flowableDefinitionParam);

    /**
     * 导出流程文件
     *
     * @param flowableDefinitionParam 导出参数
     * @param response                响应response
     * @author fengshuonan
     * @date 2020/4/15 9:48
     */
    void export(FlowableDefinitionParam flowableDefinitionParam, HttpServletResponse response);

    /**
     * 映射流程定义，将已部署的流程映射到模型
     *
     * @param flowableDefinitionParam 流程定义参数
     * @author fengshuonan
     * @date 2020/4/15 16:57
     */
    void mapping(FlowableDefinitionParam flowableDefinitionParam);

    /**
     * 激活/挂起流程定义
     *
     * @param flowableDefinitionParam 流程定义参数
     * @param isSuspend               是否挂起，true挂起，false激活
     * @author fengshuonan
     * @date 2020/4/15 17:19
     */
    void activeOrSuspend(FlowableDefinitionParam flowableDefinitionParam, boolean isSuspend);

    /**
     * 流程定义的流程图
     *
     * @param flowableDefinitionParam 流程定义参数
     * @return 流程图数据
     * @author fengshuonan
     * @date 2020/4/15 17:27
     */
    JsonNode trace(FlowableDefinitionParam flowableDefinitionParam);

    /**
     * 删除流程定义，根据版本删除，级联删除流程实例和相关任务
     *
     * @param flowableDefinitionParam 删除参数
     * @author fengshuonan
     * @date 2020/4/24 15:06
     */
    void delete(FlowableDefinitionParam flowableDefinitionParam);

    /**
     * 流程定义的用户任务节点，用于跳转时选择节点
     *
     * @param flowableDefinitionParam 流程定义参数
     * @return 用户任务节点集合
     * @author fengshuonan
     * @date 2020/4/26 11:20
     */
    List<FlowableUserTaskResult> userTasks(FlowableDefinitionParam flowableDefinitionParam);

    /**
     * 根据流程定义id获取流程定义详情
     *
     * @param processDefinitionId 流程定义id
     * @return 流程定义结果
     * @author fengshuonan
     * @date 2020/4/26 17:31
     */
    FlowableDefinitionResult detail(String processDefinitionId);

    /**
     * 根据流程id获取流程定义
     *
     * @param processDefinitionId 流程定义id
     * @return 流程定义结果
     * @author fengshuonan
     * @date 2020/4/30 17:18
     */
    ProcessDefinition queryProcessDefinition(String processDefinitionId);

    /**
     * 根据流程id获取流程定义并校验状态
     *
     * @param processDefinitionId 流程定义id
     * @return 流程定义结果
     * @author fengshuonan
     * @date 2020/8/12 10:00
     **/
    ProcessDefinition queryProcessDefinitionWithValidStatus(String processDefinitionId);

    /**
     * 多实例用户任务节点的元素变量名
     *
     * @param processDefinitionId 流程定义id
     * @param actId               节点id
     * @return 元素变量名
     * @author fengshuonan
     * @date 2020/6/2 16:06
     */
    String getMultiInstanceActAssigneeParam(String processDefinitionId, String actId);

    /**
     * 根据分类编码判断该分类下是否有流程定义
     *
     * @param category 分类编码
     * @return 是否有定义，true是，false否
     * @author fengshuonan
     * @date 2020/6/29 17:52
     */
    boolean hasDefinition(String category);

}
