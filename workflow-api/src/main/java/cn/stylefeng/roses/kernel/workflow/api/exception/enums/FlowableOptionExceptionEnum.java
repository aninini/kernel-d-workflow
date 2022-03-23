package cn.stylefeng.roses.kernel.workflow.api.exception.enums;


import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 流程选项相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/4/24 14:21
 */
@Getter
public enum FlowableOptionExceptionEnum implements AbstractExceptionEnum {

    /**
     * 流程选项不存在
     */
    OPTION_NOT_EXIST("1", "流程选项不存在"),

    /**
     * 一条流程定义只能有一条选项设置
     */
    PROCESS_DEFINITION_OPTION_REPEAT("2", "一条流程定义只能有一条选项设置");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableOptionExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
