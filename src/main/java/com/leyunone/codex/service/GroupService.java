package com.leyunone.codex.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.leyunone.codex.dao.GroupDao;
import com.leyunone.codex.dao.GroupUserDao;
import com.leyunone.codex.dao.entry.Group;
import com.leyunone.codex.model.ResponseCode;
import com.leyunone.codex.model.bo.GroupBO;
import com.leyunone.codex.model.vo.GroupUserVO;
import com.leyunone.codex.util.AssertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/8/14 16:28
 */
@Service
public class GroupService {

    private final GroupUserDao groupUserDao;
    private final GroupDao groupDao;

    public GroupService(GroupUserDao groupUserDao, GroupDao groupDao) {
        this.groupUserDao = groupUserDao;
        this.groupDao = groupDao;
    }

    /**
     * 保存分组
     *
     * @param groupBO
     */
    public void saveGroup(GroupBO groupBO) {
        Group group = groupDao.selectByGroupName(groupBO.getGroupName());
        AssertUtil.isFalse(ObjectUtil.isNotNull(group)
                && group.getGroupId().equals(groupBO.getGroupId()), ResponseCode.GROUP_NAME_REPEAT);
        AssertUtil.isFalse(ObjectUtil.isNull(group) && ObjectUtil.isNotNull(group), ResponseCode.GROUP_NAME_REPEAT);
        groupDao.insertOrUpdate(groupBO);
    }


    /**
     * 删除分组
     *
     * @param groupId
     */
    public void deleteGroup(Integer groupId) {
        groupDao.deleteById(groupId);
        groupUserDao.deleteByGroupId(groupId);
    }

}
