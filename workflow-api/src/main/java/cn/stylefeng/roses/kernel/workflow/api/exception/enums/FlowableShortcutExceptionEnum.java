package cn.stylefeng.roses.kernel.workflow.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 流程申请入口相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/6/30 12:05
 */
@Getter
public enum FlowableShortcutExceptionEnum implements AbstractExceptionEnum {

    /**
     * 流程申请入口不存在
     */
    SHORTCUT_NOT_EXIST("1", "流程申请入口不存在"),

    /**
     * 流程定义key重复
     */
    SHORTCUT_CODE_REPEAT("2", "流程定义key重复，请检查key参数"),

    /**
     * 流程申请入口名称重复
     */
    SHORTCUT_NAME_REPEAT("3", "流程申请入口名称重复，请检查name参数");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableShortcutExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
