package cn.stylefeng.roses.kernel.workflow.business.modular.task.handletask.factory;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.stylefeng.roses.kernel.system.api.pojo.role.dto.SysRoleDTO;
import cn.stylefeng.roses.kernel.system.api.pojo.user.SysUserDTO;
import cn.stylefeng.roses.kernel.system.modular.role.entity.SysRole;
import cn.stylefeng.roses.kernel.system.modular.role.service.SysRoleService;
import cn.stylefeng.roses.kernel.system.modular.user.entity.SysUser;
import cn.stylefeng.roses.kernel.system.modular.user.service.SysUserService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.TaskService;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.task.api.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 流程任务处理人工厂类，用于生成处理人信息
 *
 * @author fengshuonan
 * @date 2020/5/22 10:46
 */
public class FlowableAssigneeFactory {

    private static final TaskService taskService = SpringUtil.getBean(TaskService.class);

    private static final HistoryService historyService = SpringUtil.getBean(HistoryService.class);

    private static final SysUserService userServiceApi = SpringUtil.getBean(SysUserService.class);

    private static final SysRoleService roleServiceApi = SpringUtil.getBean(SysRoleService.class);

    /**
     * 根据处理人id获取处理人名称
     *
     * @author fengshuonan
     * @date 2020/5/22 10:47
     */
    public static String getAssigneeNameByUserId(String assignee) {
        Long userId = Convert.toLong(assignee);
        SysUserDTO userInfoByUserId = userServiceApi.getUserInfoByUserId(userId);
        if (userInfoByUserId != null) {
            return userInfoByUserId.getRealName();
        } else {
            return "";
        }
    }

    /**
     * 根据处理角色id获取处理角色名称
     *
     * @author fengshuonan
     * @date 2020/5/22 15:50
     */
    private static String getGroupNameByGroupId(String groupId) {
        Long roleId = Convert.toLong(groupId);
        ArrayList<Long> longs = ListUtil.toList(roleId);
        List<SysRoleDTO> rolesByIds = roleServiceApi.getRolesByIds(longs);
        if (rolesByIds != null && rolesByIds.size() > 0) {
            return rolesByIds.get(0).getRoleName();
        } else {
            return "";
        }
    }

    /**
     * 根据处理人id获取处理人信息
     *
     * @author fengshuonan
     * @date 2020/5/22 10:49
     */
    public static String getAssigneeInfoByUserId(String assignee) {
        return "处理人：" + getAssigneeNameByUserId(assignee);
    }

    /**
     * 根据任务id获取候选人、候选组名称
     *
     * @author fengshuonan
     * @date 2020/5/22 15:36
     */
    public static String getAssigneeNameByTaskId(String taskId) {
        Set<String> groupNameSet = CollectionUtil.newHashSet();
        Set<String> userNameSet = CollectionUtil.newHashSet();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //如果任务处于运行中
        if (ObjectUtil.isNotNull(task)) {
            List<IdentityLink> identityLinksForTask = taskService.getIdentityLinksForTask(taskId);
            identityLinksForTask.forEach(identityLink -> {
                String groupId = identityLink.getGroupId();
                String userId = identityLink.getUserId();
                if (ObjectUtil.isNotEmpty(groupId)) {
                    String groupName = getGroupNameByGroupId(groupId);
                    groupNameSet.add(groupName);
                }
                if (ObjectUtil.isNotEmpty(userId)) {
                    String userName = getAssigneeNameByUserId(userId);
                    userNameSet.add(userName);
                }
            });

        } else {
            List<HistoricIdentityLink> historicIdentityLinksForTask = historyService.getHistoricIdentityLinksForTask(taskId);
            historicIdentityLinksForTask.forEach(identityLink -> {
                String groupId = identityLink.getGroupId();
                String userId = identityLink.getUserId();
                if (ObjectUtil.isNotEmpty(groupId)) {
                    String groupName = getGroupNameByGroupId(groupId);
                    groupNameSet.add(groupName);
                }
                if (ObjectUtil.isNotEmpty(userId)) {
                    String userName = getAssigneeNameByUserId(userId);
                    userNameSet.add(userName);
                }
            });
        }

        groupNameSet.addAll(userNameSet);
        if (ObjectUtil.isNotEmpty(groupNameSet)) {
            return Convert.toStr(groupNameSet);
        }
        return null;
    }

    /**
     * 根据任务id获取候选人、候选组信息
     *
     * @author fengshuonan
     * @date 2020/5/22 15:36
     */
    public static String getAssigneeInfoByTaskId(String taskId) {
        String assigneeNameStr = getAssigneeNameByTaskId(taskId);
        if (ObjectUtil.isNotEmpty(assigneeNameStr)) {
            return "候选操作人：" + assigneeNameStr;
        }
        return "无";
    }

    /**
     * 根据办理人或角色id判断是否是用户
     *
     * @author fengshuonan
     * @date 2020/8/4 20:55
     */
    public static boolean isUser(String assignee) {
        SysUser sysUser = userServiceApi.getById(Convert.toLong(assignee));
        return sysUser != null;
    }

    /**
     * 根据办理人或角色id判断是否是角色
     *
     * @author fengshuonan
     * @date 2020/8/4 20:55
     */
    public static boolean isRole(String assignee) {
        SysRole sysRole = roleServiceApi.getById(Convert.toLong(assignee));
        return sysRole != null;
    }
}
