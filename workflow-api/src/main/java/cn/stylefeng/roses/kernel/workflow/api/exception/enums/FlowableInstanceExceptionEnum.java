package cn.stylefeng.roses.kernel.workflow.api.exception.enums;


import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 流程实例相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/4/28 10:45
 */
@Getter
public enum FlowableInstanceExceptionEnum implements AbstractExceptionEnum {

    /**
     * 流程实例不存在
     */
    INSTANCE_NOT_EXIST("1", "流程实例不存在"),

    /**
     * 流程实例无定义
     */
    INSTANCE_HAS_NO_DEFINITION("2", "流程实例无定义"),

    /**
     * 流程实例已处于挂起状态
     */
    INSTANCE_SUSPEND("3", "流程实例已处于挂起状态"),

    /**
     * 流程实例已处于激活状态
     */
    INSTANCE_ACTIVE("4", "流程实例已处于激活状态"),

    /**
     * 已挂起的流程实例无法结束
     */
    INSTANCE_SUSPEND_CAN_NOT_END("5", "已挂起的流程实例无法结束，请激活后再结束");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableInstanceExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
