package cn.stylefeng.roses.kernel.workflow.business.modular.button.result;

import lombok.Data;

/**
 * 活动节点按钮结果集
 *
 * @author fengshuonan
 * @date 2020/4/17 10:01
 */
@Data
public class FlowableButtonResult {

    /**
     * 提交（Y-是，N-否）
     */
    private String submitFlag;

    /**
     * 保存（Y-是，N-否）
     */
    private String saveFlag;

    /**
     * 退回（Y-是，N-否）
     */
    private String backFlag;

    /**
     * 转办（Y-是，N-否）
     */
    private String turnFlag;

    /**
     * 指定（Y-是，N-否）
     */
    private String nextFlag;

    /**
     * 委托（Y-是，N-否）
     */
    private String entrustFlag;

    /**
     * 终止（Y-是，N-否）
     */
    private String endFlag;

    /**
     * 追踪（Y-是，N-否）
     */
    private String traceFlag;

    /**
     * 挂起（Y-是，N-否）
     */
    private String suspendFlag;

    /**
     * 跳转（Y-是，N-否）
     */
    private String jumpFlag;

    /**
     * 加签（Y-是，N-否）
     */
    private String addSignFlag;

    /**
     * 减签（Y-是，N-否）
     */
    private String deleteSignFlag;

}
