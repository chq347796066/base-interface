package com.spring.base.vo.baseinfo.houseinstrument;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-03 09:35:57
* @Desc类说明: 新增房屋仪管理vo
*/

@ApiModel
@Data
public class HouseInstrumentAddVo {

   //小区名称
   @Excel(name = "小区名称")
   private String communityName;

   //楼栋
   @Excel(name = "楼栋")
   private String buildName;

   @Excel(name = "所属房屋")
   private String houseCode;

   //仪表名称
   @Excel(name = "仪表名称")
   private String instrumentName;

   //仪表id
   @Excel(name = "仪表类型")
   private String instrumentType;

   //最小读数
   @Excel(name = "初始度数")
   private String minReading;

   //最大读数
   @Excel(name = "最大度数")
   private String maxReading;

   //仪表倍率
   @Excel(name = "倍率")
   private String ratio;

   //初始日期
   @Excel(name = "初始日期")
   private String initialDate;

}
