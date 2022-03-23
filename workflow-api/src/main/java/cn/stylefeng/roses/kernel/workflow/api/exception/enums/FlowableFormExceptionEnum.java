package cn.stylefeng.roses.kernel.workflow.api.exception.enums;


import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 流程表单相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/4/26 16:59
 */
@Getter
public enum FlowableFormExceptionEnum implements AbstractExceptionEnum {

    /**
     * 表单不存在
     */
    FORM_NOT_EXIST("1", "表单不存在"),

    /**
     * 该流程定义无启动表单
     */
    FORM_START_NOT_EXIST("2", "该流程定义无启动表单"),

    /**
     * 该流程定义无全局表单
     */
    FORM_GLOBAL_NOT_EXIST("3", "该流程定义无全局表单"),

    /**
     * 活动节点名称为空
     */
    FORM_ACT_NAME_EMPTY("4", "活动节点名称为空"),

    /**
     * 活动节点名称重复
     */
    FORM_ACT_NAME_REPEAT("5", "活动节点名称重复"),

    /**
     * 活动节点Id为空
     */
    FORM_ACT_ID_EMPTY("6", "活动节点Id为空"),

    /**
     * 活动节点Id重复
     */
    FORM_ACT_ID_REPEAT("7", "活动节点Id重复");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableFormExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
