package com.leyunone.codex.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.GroupUserDao;
import com.leyunone.codex.dao.RealUserDao;
import com.leyunone.codex.dao.RealUserRelationDao;
import com.leyunone.codex.dao.UserDao;
import com.leyunone.codex.dao.entry.GroupUser;
import com.leyunone.codex.dao.entry.RealUser;
import com.leyunone.codex.dao.entry.RealUserRelation;
import com.leyunone.codex.model.ResponseCode;
import com.leyunone.codex.model.dto.RealUserDTO;
import com.leyunone.codex.model.query.RealUserQuery;
import com.leyunone.codex.model.vo.GroupUserVO;
import com.leyunone.codex.model.vo.RealUserRelationVO;
import com.leyunone.codex.model.vo.RealUserVO;
import com.leyunone.codex.util.AssertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 14:47
 */
@Service
public class RealUserService {

    private final RealUserDao realUserDao;
    private final RealUserRelationDao realUserRelationDao;
    private final UserDao userDao;
    private final GroupUserDao groupUserDao;

    public RealUserService(RealUserDao realUserDao, RealUserRelationDao realUserRelationDao, UserDao userDao, GroupUserDao groupUserDao) {
        this.realUserDao = realUserDao;
        this.realUserRelationDao = realUserRelationDao;
        this.userDao = userDao;
        this.groupUserDao = groupUserDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveRealUser(RealUserDTO realUserDTO) {
        //校验名称唯一
        RealUser existUser = realUserDao.selectByName(realUserDTO.getRealUserName());
        AssertUtil.isFalse(ObjectUtil.isNotNull(existUser) && !ObjectUtil.equal(existUser.getRealUserId(), realUserDTO.getRealUserId()), ResponseCode.USER_NAME_REPEAT);
        AssertUtil.isFalse(ObjectUtil.isNotNull(existUser) && ObjectUtil.isNull(realUserDTO.getRealUserId()), ResponseCode.GROUP_NAME_REPEAT);

        RealUser realUser = new RealUser();
        BeanUtil.copyProperties(realUserDTO, realUser);

        realUserDao.insertOrUpdate(realUser);
        //删除 旧关系
        realUserRelationDao.deleteByRealUserId(realUserDTO.getRealUserId());
        //清空分组
        groupUserDao.deleteByUserId(realUserDTO.getRealUserId());

        //更新 人员与gitlab账号关系
        List<String> userIds = realUserRelationDao.selectUserIdByRealUserId(realUserDTO.getRealUserId());
        if (CollectionUtil.isNotEmpty(userIds)) {
            userDao.updateRealNameByUserIds(userIds, null);
        }

        if (CollectionUtil.isNotEmpty(realUserDTO.getUserIds())) {
            List<RealUserRelation> gs = new ArrayList<>();
            for (String userId : realUserDTO.getUserIds()) {
                RealUserRelation realUserRelation = new RealUserRelation();
                realUserRelation.setUserId(userId);
                realUserRelation.setRealUserId(realUserDTO.getRealUserId());
                realUserRelation.setRealUserName(realUserDTO.getRealUserName());
                gs.add(realUserRelation);
            }
            realUserRelationDao.saveBatch(gs);
            //更新 user表真实姓名
            userDao.updateRealNameByUserIds(realUserDTO.getUserIds(), realUserDTO.getRealUserName());
        }

        //小组关系
        List<Integer> groups = realUserDTO.getGroups();
        List<GroupUser> gus = new ArrayList<>();
        for (Integer groupId : groups) {
            GroupUser groupUser = new GroupUser();
            groupUser.setGroupId(groupId);
            groupUser.setRealUserId(realUser.getRealUserId());
            gus.add(groupUser);
        }
        if (CollectionUtil.isNotEmpty(gus)) {
            groupUserDao.saveBatch(gus);
        }
    }

    public boolean deleteRealUser(RealUserDTO realUserDTO) {
        if (ObjectUtil.isNull(realUserDTO.getRealUserId())) return false;
        //删除 user表上真实姓名
        List<String> userIds = realUserRelationDao.selectUserIdByRealUserId(realUserDTO.getRealUserId());
        if (CollectionUtil.isNotEmpty(userIds)) {
            userDao.updateRealNameByUserIds(userIds, null);
        }

        //解除部门关系
        groupUserDao.deleteByUserId(realUserDTO.getRealUserId());
        realUserRelationDao.deleteByRealUserId(realUserDTO.getRealUserId());
        return realUserDao.deleteById(realUserDTO.getRealUserId());
    }

    public Page<RealUserVO> queryRealUser(RealUserQuery query) {
        Integer groupId = query.getGroupId();
        if (ObjectUtil.isNotNull(groupId)) {
            List<GroupUser> groupUsers = groupUserDao.selectRealUserByGroupId(groupId);
            if (CollectionUtil.isNotEmpty(groupUsers)) {
                query.setRealUserIds(groupUsers.stream().map(GroupUser::getRealUserId).collect(Collectors.toList()));
            } else {
                query.setRealUserIds(CollectionUtil.newArrayList(-1));
            }
        }
        Page<RealUser> realUserIPage = realUserDao.selectPage(query);
        List<RealUser> records = realUserIPage.getRecords();
        Page<RealUserVO> result = new Page<>(realUserIPage.getCurrent(), realUserIPage.getSize());
        result.setTotal(realUserIPage.getTotal());
        List<RealUserVO> ls = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(records)) {
            List<Integer> realUserIds = records.stream().map(RealUser::getRealUserId).collect(Collectors.toList());
            //关联Gitlab用户
            List<RealUserRelationVO> realUserRelations = realUserRelationDao.selectByRealUserIds(realUserIds);
            Map<Integer, List<RealUserRelationVO>> userMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(realUserRelations)) {
                userMap = realUserRelations.stream().collect(Collectors.groupingBy(RealUserRelationVO::getRealUserId));
            }
            //关联分组
            List<GroupUserVO> groupUserVOS = groupUserDao.selectByUserIds(realUserIds);
            Map<Integer, List<GroupUserVO>> groupMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(groupUserVOS)) {
                groupMap = groupUserVOS.stream().collect(Collectors.groupingBy(GroupUserVO::getRealUserId));
            }
            for (RealUser realUser : records) {
                RealUserVO realUserVO = new RealUserVO();
                realUserVO.setRealUserId(realUser.getRealUserId());
                realUserVO.setRealUserName(realUser.getRealUserName());
                realUserVO.setRealEmail(realUser.getRealEmail());
                if (userMap.containsKey(realUserVO.getRealUserId())) {
                    realUserVO.setGitLabUser(userMap.get(realUserVO.getRealUserId()));
                }
                if (groupMap.containsKey(realUserVO.getRealUserId())) {
                    realUserVO.setGroupList(groupMap.get(realUserVO.getRealUserId()));
                }
                ls.add(realUserVO);
            }
        }
        result.setRecords(ls);

        return result;
    }
}
