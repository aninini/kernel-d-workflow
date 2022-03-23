package cn.stylefeng.roses.kernel.workflow.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 流程事件相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/4/26 16:40
 */
@Getter
public enum FlowableEventExceptionEnum implements AbstractExceptionEnum {

    /**
     * 流程事件不存在
     */
    EVENT_NOT_EXIST("1", "流程事件不存在"),

    /**
     * 活动节点id为空
     */
    ACT_ID_EMPTY("2", "活动节点id为空，请检查actId参数"),

    /**
     * 活动节点名称为空
     */
    ACT_NAME_EMPTY("3", "活动节点名称为空，请检查actName参数");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableEventExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
