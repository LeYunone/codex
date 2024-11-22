package com.leyunone.codex.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.dao.*;
import com.leyunone.codex.dao.entry.ProjectUser;
import com.leyunone.codex.dao.entry.RealUserRelation;
import com.leyunone.codex.dao.entry.User;
import com.leyunone.codex.model.query.ProjectUserQuery;
import com.leyunone.codex.model.query.UserQuery;
import com.leyunone.codex.model.vo.ProjectUserVO;
import com.leyunone.codex.model.vo.UserVO;
import com.leyunone.codex.util.AssertUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 16:39
 */
@Service
public class UserService {

    private final ProjectUserDao projectUserDao;
    private final ProjectDao projectDao;
    private final RealUserRelationDao realUserRelationDao;
    private final RealUserDao realUserDao;
    private final UserDao userDao;

    public UserService(ProjectUserDao projectUserDao, ProjectDao projectDao, RealUserRelationDao realUserRelationDao, RealUserDao realUserDao, UserDao userDao) {
        this.projectUserDao = projectUserDao;
        this.projectDao = projectDao;
        this.realUserRelationDao = realUserRelationDao;
        this.realUserDao = realUserDao;
        this.userDao = userDao;
    }


    /**
     * 选择项目，得到项目下人员；
     * 选择人员，得到人员有的项目；
     *
     * @param query
     */
    public List<ProjectUserVO> selectProjectOrUser(ProjectUserQuery query) {
        List<ProjectUserVO> projectUserVOS = new ArrayList<>();
        AssertUtil.isFalse(ObjectUtil.isNull(query.getType()), "type is empty");
        if (query.getType() == 0) {
            //查询用户
            if (StringUtils.isNotBlank(query.getProjectId())) {
                List<ProjectUser> projectUsers = projectUserDao.selectUserIdByProjectId(query.getProjectId());
                if (CollectionUtil.isNotEmpty(projectUsers)) {
                    List<String> userIds = projectUsers.stream().map(ProjectUser::getUserId).collect(Collectors.toList());
                    List<RealUserRelation> realUserRelations = realUserRelationDao.selectByUserIds(userIds);
                    projectUserVOS = BeanUtil.copyToList(realUserRelations, ProjectUserVO.class);
                }
            } else {
                projectUserVOS = realUserDao.selectByCon(null, ProjectUserVO.class);
            }
        }
        if (query.getType() == 1) {
            //查询项目
            if (ObjectUtil.isNotNull(query.getRealUserId())) {
                List<String> userIds = realUserRelationDao.selectUserIdByRealUserId(query.getRealUserId());
                query.setUserIds(userIds);
            }
            //项目搜索
            projectUserVOS = projectDao.selectProjectUserList(query);
        }
        return projectUserVOS;
    }


    public Page<UserVO> queryUserCodex(UserQuery query) {
        IPage<User> userIPage = userDao.selectByConPage(null, query.getPageIndex(), query.getPageSize());
        Page<UserVO> userVOPage = new Page<>();
        userVOPage.setRecords(userIPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtil.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList()));
        userVOPage.setTotal(userIPage.getTotal());
        userVOPage.setSize(userIPage.getSize());
        userVOPage.setCurrent(userIPage.getCurrent());
        return userVOPage;
    }

}
