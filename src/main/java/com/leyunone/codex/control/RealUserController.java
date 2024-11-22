package com.leyunone.codex.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyunone.codex.model.DataResponse;
import com.leyunone.codex.model.dto.RealUserDTO;
import com.leyunone.codex.model.query.RealUserQuery;
import com.leyunone.codex.model.vo.RealUserVO;
import com.leyunone.codex.service.RealUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/3 14:19
 */
@RestController
@RequestMapping("/realuser")
public class RealUserController {

    @Autowired
    private RealUserService realUserService;
    
    @PostMapping("/save")
    public DataResponse<?> saveRealUser(@RequestBody RealUserDTO realUserDTO) {
        realUserService.saveRealUser(realUserDTO);
        return DataResponse.of();
    }
    
    @PostMapping("/delete")
    public DataResponse<?> deleteRealUser(@RequestBody RealUserDTO realUserDTO){
        realUserService.deleteRealUser(realUserDTO);
        return DataResponse.of();
    }
    
    @GetMapping("/query")
    public DataResponse<Page<RealUserVO>> queryRealUser(RealUserQuery query){
        Page<RealUserVO> realUserVOPage = realUserService.queryRealUser(query);
        return DataResponse.of(realUserVOPage);
    }
    
}
