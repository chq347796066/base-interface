package com.spring.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 租户属性
 * @author zwb
 * @Date 2020-11-6
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "ocean.mysql")
public class TenantProperties {

    /**
     * 是否开启租户模式
     */
    private Boolean enable;

    /**
     * 需要排除的多租户的表
     */
    private List<String> ignoreTables = Arrays.asList("b_user","b_role_menu","b_menu","b_role","b_user_role","b_bus_dict","b_item_charge",
            "b_charge_config","b_bus_dict","b_charge_rule","b_house_charge","p_bill_details","p_pay_bills","b_dictionary","r_repair","r_repair_process",
            "r_repair_image","r_repair_comment");

    /**
     * 多租户字段名称
     */
    private String column ;

    /**
     * 排除不进行租户隔离的sql
     * 样例全路径：vip.mate.system.mapper.UserMapper.findList
     */
    private List<String> ignoreSqls = new ArrayList<>();
}
