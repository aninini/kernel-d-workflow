package cn.stylefeng.roses.kernel.workflow.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 表单管理相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/8/14 15:06
 */
@Getter
public enum FlowableFormResourceExceptionEnum implements AbstractExceptionEnum {

    /**
     * 表单不存在
     */
    FORM_RESOURCE_NOT_EXIST("1", "表单不存在"),

    /**
     * 表单编码重复
     */
    FORM_RESOURCE_CODE_REPEAT("2", "表单编码重复，请检查code参数"),

    /**
     * 表单名称重复
     */
    FORM_RESOURCE_NAME_REPEAT("3", "表单名称重复，请检查name参数"),

    /**
     * 该表单已关联流程定义
     */
    FORM_CANNOT_DELETE("4", "该表单已关联流程定义，无法删除");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableFormResourceExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
