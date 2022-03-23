package cn.stylefeng.roses.kernel.workflow.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

/**
 * 申请草稿管理相关异常枚举
 *
 * @author fengshuonan
 * @date 2020/8/14 15:06
 */
@Getter
public enum FlowableDraftExceptionEnum implements AbstractExceptionEnum {

    /**
     * 申请草稿不存在
     */
    DRAFT_NOT_EXIST("1", "表单不存在");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    FlowableDraftExceptionEnum(String errorCode, String userTip) {
        this.errorCode = errorCode;
        this.userTip = userTip;
    }

}
