package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("x_branches")
public class Branches {
    
    @TableId("branches_id")
    private String branchesId;
    
    private String projectId;
    
    private String branchesName;
}
