package cn.stylefeng.roses.kernel.workflow.business.modular.user.controller;

import cn.hutool.core.lang.Dict;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.rule.pojo.dict.SimpleDict;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.system.api.pojo.role.request.SysRoleRequest;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.request.SysUserRequest;
import cn.stylefeng.roses.kernel.system.modular.role.service.SysRoleService;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import cn.stylefeng.roses.kernel.workflow.api.constants.CommonConstant;
import cn.stylefeng.roses.kernel.workflow.business.modular.user.result.DesignerSelectResult;
import cn.stylefeng.roses.kernel.workflow.business.modular.user.result.FlowableUserResult;
import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.security.SecurityUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * flowable账户控制器，用于进入设计器时获取用户信息
 * flowable用户控制器，用于流程设计时选人，选角色
 * </p>
 *
 * @author fengshuonan
 * @date 2020/4/14 9:04
 */
@RestController
@ApiResource(name = "工作流用户")
public class FlowableUserController {

    @Resource
    private SessionManagerApi sessionManagerApi;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 流程设计器获取账户信息
     *
     * @author fengshuonan
     * @date 2020/4/14 9:10
     */
    @GetResource(name = "流程设计器获取账户信息", path = "/app/rest/account")
    public ResponseData getAccount(String token) {

        LoginUser session = sessionManagerApi.getSession(token);
        RemoteUser remoteUser = new RemoteUser();
        remoteUser.setFirstName(session.getSimpleUserInfo().getRealName());
        remoteUser.setId(session.getAccount());
        //构建flowable用户结果集
        FlowableUserResult flowableUserResult = new FlowableUserResult(remoteUser);
        //保证创建流程可用，在保存model等情况
        SecurityUtils.assumeUser(remoteUser);
        return new SuccessResponseData(flowableUserResult);
    }

    /**
     * 流程设计器获取用户列表
     *
     * @param filter 用户的账号
     * @author fengshuonan
     * @date 2020/4/14 9:13
     */
    @GetResource(name = "流程设计器获取用户列表", path = {"/app/rest/editor-users"})
    public DesignerSelectResult getUsers(@RequestParam(value = "filter", required = false) String filter) {

        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setAccount(filter);
        List<SysUserDTO> userList = sysUserService.getUserList(sysUserRequest);

        ArrayList<Dict> dicts = new ArrayList<>();
        if (userList != null) {
            for (SysUserDTO sysUserDTO : userList) {
                Dict dict = new Dict();
                dict.set(CommonConstant.ID, sysUserDTO.getUserId());
                dict.set(CommonConstant.FIRST_NAME, sysUserDTO.getRealName() + "[" + sysUserDTO.getAccount() + "]");
                dicts.add(dict);
            }
        }

        return new DesignerSelectResult(dicts, dicts.size());
    }

    /**
     * 流程设计器获取用户组（角色）列表
     *
     * @param filter 角色名
     * @author fengshuonan
     * @date 2020/4/14 9:13
     */
    @GetResource(name = "流程设计器获取用户组（角色）列表", path = {"/app/rest/editor-groups"})
    public DesignerSelectResult getGroups(@RequestParam(value = "filter", required = false) String filter) {
        SysRoleRequest sysRoleRequest = new SysRoleRequest();
        sysRoleRequest.setRoleName(filter);
        List<SimpleDict> roleList = sysRoleService.findList(sysRoleRequest);
        return new DesignerSelectResult(roleList, roleList.size());
    }

}
