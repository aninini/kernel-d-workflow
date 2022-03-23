package cn.stylefeng.roses.kernel.workflow.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 流程分类相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/4/13 14:26
 */
@Getter
public enum FlowableCategoryExceptionEnum implements AbstractExceptionEnum {

    /**
     * 流程分类不存在
     */
    CATEGORY_NOT_EXIST("1", "流程分类不存在"),

    /**
     * 流程分类编码重复
     */
    CATEGORY_CODE_REPEAT("2", "流程分类编码重复，请检查code参数"),

    /**
     * 流程分类名称重复
     */
    CATEGORY_NAME_REPEAT("3", "流程分类名称重复，请检查name参数"),

    /**
     * 该分类下有流程定义
     */
    CATEGORY_CANNOT_DELETE("4", "该分类下有流程定义，无法删除");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableCategoryExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
