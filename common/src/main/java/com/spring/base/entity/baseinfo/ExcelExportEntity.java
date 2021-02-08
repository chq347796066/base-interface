package com.spring.base.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.base.entity.BaseEntity;
import com.spring.base.vo.Vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description:excel导出实体
 * @author: 赵进华
 * @time: 2020/3/28 19:50
 */
@ApiModel
@Data
@ToString(callSuper = true)
@TableName("b_excel_export")
public class ExcelExportEntity extends BaseEntity implements Vo<String>, Serializable {
    private static final long serialVersionUID = 1L;
    //主键
    @ApiModelProperty(value = "主键")
    private String id;

    // 用户id
    @ApiModelProperty(value = " 用户id")
    private String userId;

    //导出类型
    @ApiModelProperty(value = "导出类型")
    private String excelType;

    //文件地址
    @ApiModelProperty(value = "文件地址")
    private String filePath;

    //文件名
    @ApiModelProperty(value = "文件名")
    private String fileName;

    //状态(1启动,2成功,3失败)
    @ApiModelProperty(value = "状态(1启动,2成功,3失败)")
    private Integer status;

}
