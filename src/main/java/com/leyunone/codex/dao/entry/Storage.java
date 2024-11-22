package com.leyunone.codex.dao.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("x_storage")
public class Storage {
    
    @TableId(type = IdType.AUTO)
    private Integer storageId;
    
    private String storageUrl;
    
    private String storageName;
    
    private String token;
}
