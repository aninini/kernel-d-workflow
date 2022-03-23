package cn.stylefeng.roses.kernel.workflow.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 待办任务相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/4/29 15:39
 */
@Getter
public enum FlowableHandleTaskExceptionEnum implements AbstractExceptionEnum {

    /**
     * 任务不存在
     */
    TASK_NOT_EXIST("1", "任务不存在"),

    /**
     * 签收失败，任务已被其他人办理
     */
    TASK_HAS_BEEN_HANDLE("2", "签收失败，任务已被其他人办理"),

    /**
     * 任务委托状态异常
     */
    TASK_DELEGATE_STATUS_ERROR("3", "任务委托状态异常"),

    /**
     * 活动节点id与任务id不匹配
     */
    TASK_ACT_ID_ERROR("4", "活动节点id与任务id不匹配，请检查targetActId参数"),

    /**
     * 加签失败，该任务非会签（或签）任务
     */
    TASK_ADD_SIGN_ERROR("5", "加签失败，该任务不是会签（或签）任务或节点配置错误"),

    /**
     * 减签失败，该任务非会签（或签）任务
     */
    TASK_DELETE_SIGN_ERROR("6", "减签失败，该任务不是会签（或签）任务或节点配置错误"),

    /**
     * 任务id为空
     */
    TASK_ID_EMPTY("7", "任务id为空，请检查actId参数"),

    /**
     * 流程定义id为空
     */
    PROCESS_DEFINITION_ID_EMPTY("8", "流程定义id为空，请检查processDefinitionId参数"),

    /**
     * 流程状态不正确
     */
    CAN_NOT_START("9", "流程状态不正确，无法发起申请");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableHandleTaskExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
