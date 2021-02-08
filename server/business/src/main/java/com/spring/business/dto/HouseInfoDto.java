package com.spring.business.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @author 熊锋
 * @date 2021-01-07 9:39
 * @Describe:报修人房屋信息
 */
@ApiModel
@Data
@ToString
public class HouseInfoDto {

    /**
     * 报修人小区名称
     */
    private String communityName;

    /**
     * 报修人楼栋
     */
    private String buildName;

    /**
     * 报修人单元
     */
    private String cellName;

    /**
     * 报修人房间号名称
     */
    private String houseName;
}
