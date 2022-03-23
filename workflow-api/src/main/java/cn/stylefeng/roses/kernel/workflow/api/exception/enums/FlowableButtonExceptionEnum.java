package cn.stylefeng.roses.kernel.workflow.api.exception.enums;


import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 流程按钮相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/4/26 16:26
 */
@Getter
public enum FlowableButtonExceptionEnum implements AbstractExceptionEnum {

    /**
     * 流程按钮不存在
     */
    BUTTON_NOT_EXIST("1", "流程按钮不存在"),
    /**
     * 同一节点按钮配置只能有一条
     */
    ACT_FORM_REPEAT("2", "同一节点按钮配置只能有一条");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableButtonExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
