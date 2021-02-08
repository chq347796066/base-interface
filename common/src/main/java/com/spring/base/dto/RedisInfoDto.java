package com.spring.base.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author 熊锋
 * @date 2021-01-14 17:49
 * @Describe:
 */
@Data
public class RedisInfoDto {

    private static final long serialVersionUID = 1L;

    /**
     * token
     */
    private String token;

    /**
     * userId
     */
    private String userId;

    /**
     * userCode
     */
    private String userCode;

    /**
     * userName
     */
    private String userName;

    /**
     * 生成token的时间
     */
    private Date tokenDate;

    /**
     * 职位名称
     */
    private String jobName;

}
