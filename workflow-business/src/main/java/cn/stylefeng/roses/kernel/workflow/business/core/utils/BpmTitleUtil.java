package cn.stylefeng.roses.kernel.workflow.business.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.roses.kernel.auth.api.context.LoginContext;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;

/**
 * 流程标题工具类
 *
 * @author fengshuonan
 * @date 2020/5/26 16:46
 */
public class BpmTitleUtil {

    /**
     * 处理流程标题规则
     *
     * @author fengshuonan
     * @date 2020/5/26 16:55
     */
    public static String formatTitle(String titleReg, String processTitle) {
        LoginUser loginUser = LoginContext.me().getLoginUser();
        return titleReg.replace("user", loginUser.getSimpleUserInfo().getRealName()).replace("time", DateUtil.now()).replace("title", processTitle);
    }
}
