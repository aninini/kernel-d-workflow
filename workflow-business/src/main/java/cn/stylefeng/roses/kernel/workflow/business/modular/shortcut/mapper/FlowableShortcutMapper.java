package cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.mapper;

import cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.entity.FlowableShortcut;
import cn.stylefeng.roses.kernel.workflow.business.modular.shortcut.node.FlowableShortcutTreeNode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程申请入口mapper接口
 *
 * @author fengshuonan
 * @date 2020/6/30 10:48
 */
public interface FlowableShortcutMapper extends BaseMapper<FlowableShortcut> {

    /**
     * 流程申请入口列表
     *
     * @param queryWrapper 查询参数
     * @return 申请入口列表
     * @author fengshuonan
     * @date 2020/6/30 14:05
     */
    List<FlowableShortcutTreeNode> list(@Param("ew") QueryWrapper<FlowableShortcutTreeNode> queryWrapper);
}
