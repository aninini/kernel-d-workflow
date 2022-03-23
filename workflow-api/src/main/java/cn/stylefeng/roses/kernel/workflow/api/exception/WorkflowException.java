package cn.stylefeng.roses.kernel.workflow.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import cn.stylefeng.roses.kernel.workflow.api.constants.WorkflowConstants;

/**
 * 工作流模块的异常
 *
 * @author fengshuonan
 * @date 2021/2/9 10:26
 */
public class WorkflowException extends ServiceException {

    public WorkflowException(AbstractExceptionEnum exception) {
        super(WorkflowConstants.WORKFLOW_MODULE_NAME, exception);
    }

    public WorkflowException(AbstractExceptionEnum exception, Object... params) {
        super(WorkflowConstants.WORKFLOW_MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

}
