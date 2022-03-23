package cn.stylefeng.roses.kernel.workflow.api.enums;

import lombok.Getter;

/**
 * 事件节点类型枚举
 *
 * @author fengshuonan
 * @date 2020/6/10 16:02
 */
@Getter
public enum EventNodeTypeEnum {

    /**
     * 全局
     */
    GLOBAL(1, "全局"),

    /**
     * 节点
     */
    NODE(2, "节点");

    private final Integer code;

    private final String message;

    EventNodeTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
