package com.spring.base.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description:FastDFS上传文件业务对象
 * @return:
 * @author: 赵进华
 * @time: 2020/4/2 15:40
 */
@Data
@ToString
public class FastFileVO implements Serializable{

    private static final long serialVersionUID = 2637755431406080379L;
    /**
     * 文件二进制
     */
    private byte[] content;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件长度
     */
    private Long size;

    public FastFileVO(){

    }
    public FastFileVO(byte[] content, String name, Long size){
        this.content = content;
        this.name = name;
        this.size = size;
    }
}

