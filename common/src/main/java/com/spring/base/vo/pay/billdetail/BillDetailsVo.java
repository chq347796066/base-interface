package com.spring.base.vo.pay.billdetail;

import com.spring.base.entity.pay.BillDetailsEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-04-27 16:43:07
* @Desc类说明:
*/
@ApiModel
@Data
public class BillDetailsVo  {
   List<BillDetailsEntity> list;
}
