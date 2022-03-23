package cn.stylefeng.roses.kernel.workflow.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 流程定义相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/4/14 20:01
 */
@Getter
public enum FlowableDefinitionExceptionEnum implements AbstractExceptionEnum {

    /**
     * 流程模型不存在
     */
    MODEL_NOT_EXIST("1", "流程模型不存在"),

    /**
     * 流程模型无资源
     */
    MODEL_HAS_NO_RESOURCE("2", "流程模型无资源"),

    /**
     * 流程定义不存在
     */
    DEFINITION_NOT_EXIST("3", "流程定义不存在"),

    /**
     * 流程部署失败
     */
    DEPLOYMENT_ERROR("4", "流程部署失败"),

    /**
     * 流程部署不存在
     */
    DEPLOYMENT_NOT_EXIST("5", "流程部署不存在"),

    /**
     * 流程文件导出失败
     */
    RESOURCE_EXPORT_ERROR("6", "流程导出失败，请联系管理员"),

    /**
     * 该流程定义已处于挂起状态
     */
    DEFINITION_SUSPEND("7", "该流程定义已处于挂起状态"),

    /**
     * 该流程定义已处于激活状态
     */
    DEFINITION_ACTIVE("8", "该流程定义已处于激活状态");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableDefinitionExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }


}
