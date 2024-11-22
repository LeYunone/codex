package com.leyunone.codex.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.query.ProjectUserQuery;
import com.leyunone.codex.model.query.UserQuery;
import com.leyunone.codex.model.vo.ProjectUserVO;
import com.leyunone.codex.model.vo.UserVO;
import com.leyunone.codex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/alluser")
    public DataResponse<Page<UserVO>> getUser(UserQuery query) {
        Page<UserVO> userVOS = userService.queryUserCodex(query);
        return DataResponse.of(userVOS);
    }

    @RequestMapping("/login")
    public DataResponse<?> login(String account, String password) {
        //TODO 登录调试
        if (account.equals("admin") && password.equals("admin")) {
            return DataResponse.buildSuccess();
        } else {
            return DataResponse.buildFailure("账号或密码错误");
        }
    }

    @GetMapping("/selectuserproject")
    public DataResponse<List<ProjectUserVO>> selectProjectOrUser(ProjectUserQuery query) {
        List<ProjectUserVO> projectUserVOS = userService.selectProjectOrUser(query);
        return DataResponse.of(projectUserVOS);
    }

}
