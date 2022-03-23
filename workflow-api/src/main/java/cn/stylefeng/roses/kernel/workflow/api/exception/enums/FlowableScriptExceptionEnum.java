package cn.stylefeng.roses.kernel.workflow.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 流程脚本相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/4/17 17:20
 */
@Getter
public enum FlowableScriptExceptionEnum implements AbstractExceptionEnum {

    /**
     * 脚本不存在
     */
    SCRIPT_NOT_EXIST("1", "脚本不存在");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableScriptExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
