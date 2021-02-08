package com.spring.common.constants;

/**
 * @Description //房屋认证常量定义
 * @Author shangshanshan
 * @Date 2021/1/6 10:19
 */
public interface CertificationHouseConstants {

    Integer CONSTANT_ZERO = 0;

    //身份类型（业主）
    String OWNER = "1";
    //身份类型（家属）
    String RELATION = "2";
    //身份类型（租客）
    String TENANT = "3";

    //认证方式（业主认证）
    String OWNER_CERTIFICATION = "1";
    //认证方式（物业认证）
    String PROPERTY_CERTIFICATION = "2";

    //证件类型（身份证）
    String IDENTITY_CARD = "1";
    //证件类型（港澳通行证）
    String HONGKONG_AND_MACAO_PASS = "2";

    //审核状态（待审核）
    String WAIT = "0";
    //审核状态（已通过）
    String PASS = "1";
    //审核状态（未通过）
    String NO_PASS = "2";
    //审核状态（撤回）
    String WAITDRAW = "3";
    //审核状态（驳回）
    String REJECT = "4";
}
