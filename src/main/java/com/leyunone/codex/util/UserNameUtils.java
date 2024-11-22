package com.leyunone.codex.util;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.dao.UserDao;
import com.leyunone.codex.dao.entry.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserNameUtils {

    public static Map<String, String> getUserRealNames(List<String> userIds) {
        if(CollectionUtil.isEmpty(userIds)) return new HashMap<>();
        UserDao userDao = ApplicationContextProvider.getBean(UserDao.class);
        List<User> users = userDao.selectByIds(userIds);
        Map<String, String> userMap = users.stream().filter((t) -> StringUtils.isNotBlank(t.getRealUserName())).
                collect(Collectors.toMap(User::getUserId, User::getRealUserName));
        return userMap;
    }
}
