package com.leyunone.codex.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.leyunone.codex.dao.ProjectDao;
import com.leyunone.codex.dao.entry.Project;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProjectUtils {
    
    public static Map<String, String> getProjectName() {
        ProjectDao bean = ApplicationContextProvider.getBean(ProjectDao.class);
        List<Project> projects = bean.selectByCon(null);
        Map<String, String> userMap = projects.stream().filter((t) -> StringUtils.isNotBlank(t.getProjectName())).
                collect(Collectors.toMap(Project::getProjectId, Project::getProjectName));
        return userMap;
    }
}
