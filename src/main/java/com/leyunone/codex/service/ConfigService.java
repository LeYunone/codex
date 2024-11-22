package com.leyunone.codex.service;

import com.leyunone.codex.dao.*;
import com.leyunone.codex.dao.entry.Storage;
import com.leyunone.codex.dao.entry.User;
import com.leyunone.codex.model.vo.GroupUserVO;
import com.leyunone.codex.model.vo.GroupVO;
import com.leyunone.codex.model.vo.ProjectVO;
import com.leyunone.codex.model.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2024/8/14 14:50
 */
@Service
public class ConfigService {

    private final StorageDao storageDao;
    private final GroupDao groupDao;
    private final GroupUserDao groupUserDao;
    private final ProjectDao projectDao;
    private final UserDao userDao;

    public ConfigService(StorageDao storageDao, GroupDao groupDao, GroupUserDao groupUserDao, ProjectDao projectDao, UserDao userDao) {
        this.storageDao = storageDao;
        this.groupDao = groupDao;
        this.groupUserDao = groupUserDao;
        this.projectDao = projectDao;
        this.userDao = userDao;
    }

    /**
     * 获得仓库
     *
     * @return
     */
    public List<Storage> getStorages() {
        return storageDao.selectByCon(null);
    }


    /**
     * 获得分组
     *
     * @return
     */
    public List<GroupVO> getGroups() {
        List<GroupVO> groups = groupDao.selectByCon(null, GroupVO.class);
        List<GroupUserVO> groupUsers = groupUserDao.selectByCon(null, GroupUserVO.class);
        Map<Integer, List<GroupUserVO>> groupUserMap = groupUsers.stream().collect(Collectors.groupingBy(GroupUserVO::getGroupId));
        for (GroupVO group : groups) {
            if (groupUserMap.containsKey(group.getGroupId())) {
                group.setGroupUsers(groupUserMap.get(group.getGroupId()));
            }
        }
        return groups;
    }

    /**
     * 获得所有项目
     *
     * @return
     */
    public List<ProjectVO> getProjects() {
        List<ProjectVO> projects = projectDao.selectByCon(null, ProjectVO.class);
        return projects;
    }

    /**
     * 获得所有git用户
     *
     * @return
     */
    public List<UserVO> getAllUser() {
        return userDao.selectByCon(null, UserVO.class);
    }

}
