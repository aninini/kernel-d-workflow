package cn.stylefeng.roses.kernel.workflow.business.modular.button.factory;


import cn.stylefeng.roses.kernel.rule.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.workflow.business.modular.button.result.FlowableButtonResult;

/**
 * 流程按钮工厂类
 *
 * @author fengshuonan
 * @date 2020/6/8 11:56
 */
public class FlowableButtonFactory {

    /**
     * 构造默认的按钮结果集，用于某些节点没配置按钮信息时，默认拥有提交、保存和退回按钮
     *
     * @author fengshuonan
     * @date 2020/6/8 11:57
     */
    public static FlowableButtonResult genFlowableButtonResult() {
        FlowableButtonResult flowableButtonResult = new FlowableButtonResult();
        flowableButtonResult.setSubmitFlag(YesOrNotEnum.Y.getCode());
        flowableButtonResult.setSaveFlag(YesOrNotEnum.Y.getCode());
        flowableButtonResult.setBackFlag(YesOrNotEnum.Y.getCode());
        flowableButtonResult.setTurnFlag(YesOrNotEnum.N.getCode());
        flowableButtonResult.setNextFlag(YesOrNotEnum.N.getCode());
        flowableButtonResult.setEntrustFlag(YesOrNotEnum.N.getCode());
        flowableButtonResult.setEndFlag(YesOrNotEnum.N.getCode());
        flowableButtonResult.setTraceFlag(YesOrNotEnum.Y.getCode());
        flowableButtonResult.setSuspendFlag(YesOrNotEnum.N.getCode());
        flowableButtonResult.setJumpFlag(YesOrNotEnum.N.getCode());
        flowableButtonResult.setAddSignFlag(YesOrNotEnum.N.getCode());
        flowableButtonResult.setDeleteSignFlag(YesOrNotEnum.N.getCode());
        return flowableButtonResult;
    }
}
