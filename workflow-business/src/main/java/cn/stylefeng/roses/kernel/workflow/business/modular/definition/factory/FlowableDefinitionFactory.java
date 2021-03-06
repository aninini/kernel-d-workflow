package cn.stylefeng.roses.kernel.workflow.business.modular.definition.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.category.service.FlowableCategoryService;
import cn.stylefeng.roses.kernel.workflow.business.modular.definition.result.FlowableDefinitionResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.flowable.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * 流程定义工厂类，用于把flowable返回的实体转换为自定义实体
 *
 * @author fengshuonan
 * @date 2020/4/14 20:43
 */
public class FlowableDefinitionFactory {

    private static final FlowableCategoryService flowableCategoryService = SpringUtil.getBean(FlowableCategoryService.class);

    /**
     * 根据流程定义集合和默认分页，返回自定义PageResult
     *
     * @author fengshuonan
     * @date 2020/4/14 20:46
     */
    public static PageResult<FlowableDefinitionResult> pageResult(List<ProcessDefinition> processDefinitionList, Page<FlowableDefinitionResult> defaultPage) {
        List<FlowableDefinitionResult> flowableDefinitionResultList = convertToFlowableDefinitionResultList(processDefinitionList);
        defaultPage.setRecords(flowableDefinitionResultList);
        return PageResultFactory.createPageResult(defaultPage);
    }

    /**
     * 将流程定义结果转换为自定义结果
     *
     * @author fengshuonan
     * @date 2020/4/26 17:27
     */
    private static List<FlowableDefinitionResult> convertToFlowableDefinitionResultList(List<ProcessDefinition> processDefinitionList) {
        List<FlowableDefinitionResult> flowableDefinitionResultList = CollectionUtil.newArrayList();
        processDefinitionList.forEach(processDefinition -> {
            FlowableDefinitionResult flowableDefinitionResult = convertToFlowableDefinitionResult(processDefinition);
            flowableDefinitionResultList.add(flowableDefinitionResult);
        });
        return flowableDefinitionResultList;
    }

    /**
     * 将单个流程定义结果转换为自定义结果
     *
     * @author fengshuonan
     * @date 2020/4/14 20:49
     */
    public static FlowableDefinitionResult convertToFlowableDefinitionResult(ProcessDefinition processDefinition) {
        FlowableDefinitionResult flowableDefinitionResult = new FlowableDefinitionResult();
        flowableDefinitionResult.setId(processDefinition.getId());
        flowableDefinitionResult.setKey(processDefinition.getKey());
        flowableDefinitionResult.setDeploymentId(processDefinition.getDeploymentId());
        flowableDefinitionResult.setCategory(processDefinition.getCategory());
        flowableDefinitionResult.setCategoryName(flowableCategoryService.getNameByCode(processDefinition.getCategory()));
        flowableDefinitionResult.setDescription(processDefinition.getDescription());
        flowableDefinitionResult.setName(processDefinition.getName());
        flowableDefinitionResult.setVersion(processDefinition.getVersion());
        flowableDefinitionResult.setSuspended(processDefinition.isSuspended());
        return flowableDefinitionResult;
    }
}
