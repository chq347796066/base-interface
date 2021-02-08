package com.spring.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Saas Base Entity
 *
 * @author WuJiaQuan
 */
public class SaasBaseEntity implements Serializable {

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @JsonIgnore
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    private Date createDate;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    @JsonIgnore
    private String modifyUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonIgnore
    private Date modifyDate;

    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志（0 未删除 1 已删除）")
    @JsonIgnore
    private Integer delFlag;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
