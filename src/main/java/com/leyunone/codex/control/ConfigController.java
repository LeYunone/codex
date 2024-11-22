package com.leyunone.codex.control;

import com.leyunone.codex.dao.entry.Storage;
import com.leyunone.codex.dao.entry.User;
import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.query.StorageQuery;
import com.leyunone.codex.model.vo.GroupVO;
import com.leyunone.codex.model.vo.ProjectVO;
import com.leyunone.codex.model.vo.UserVO;
import com.leyunone.codex.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * :)
 * 配置接口
 *
 * @Author LeYunone
 * @Date 2024/8/14 14:48
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/storages")
    public DataResponse<List<Storage>> getStorage() {
        List<Storage> storage = configService.getStorages();
        return DataResponse.of(storage);
    }

    @GetMapping("/groups")
    public DataResponse<List<GroupVO>> groups() {
        List<GroupVO> groups = configService.getGroups();
        return DataResponse.of(groups);
    }

    @GetMapping("/projects")
    public DataResponse<List<ProjectVO>> projects() {
        List<ProjectVO> projects = configService.getProjects();
        return DataResponse.of(projects);
    }

    @GetMapping("/users")
    public DataResponse<List<UserVO>> getALlUser() {
        List<UserVO> allUser = configService.getAllUser();
        return DataResponse.of(allUser);
    }

}
