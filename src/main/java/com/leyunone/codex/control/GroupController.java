package com.leyunone.codex.control;

import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.bo.GroupBO;
import com.leyunone.codex.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/saveGroupUser")
    public DataResponse<?> saveGroupUser(@RequestBody GroupBO groupBO) {
        groupService.saveGroup(groupBO);
        return DataResponse.buildSuccess();
    }

    @GetMapping("/delete")
    public DataResponse<?> delete(Integer groupId) {
        groupService.deleteGroup(groupId);
        return DataResponse.buildSuccess();
    }
}
