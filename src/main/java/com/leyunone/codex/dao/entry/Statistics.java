package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("x_statistics")
public class Statistics {

    @TableId(type = IdType.AUTO)
    private Integer statisticsId;
    
    private String userId;
    
    private Integer codeAdditions;
    
    private Integer codeDeletions;
    
    private Integer codeTotal;
}
