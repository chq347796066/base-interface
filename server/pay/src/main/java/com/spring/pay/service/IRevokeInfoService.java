package com.spring.pay.service;

import com.spring.base.entity.pay.RevokeInfoEntity;
import com.spring.base.service.IBaseService;
import com.spring.base.vo.pay.revoke.RevokeInfoAddVo;
import com.spring.base.vo.pay.revoke.RevokeInfoUpdateVo;
import com.spring.base.vo.pay.revoke.RevokeVo;
import com.spring.common.response.ApiResponseResult;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-05-20 10:37:32
 * @Desc类说明: 撤销记录业务接口类
 */
public interface IRevokeInfoService extends IBaseService<RevokeInfoEntity,String>{
	
	/**
	 * 新增撤销记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-05-20 10:37:32
	 */
    ApiResponseResult addRevokeInfo(RevokeInfoAddVo vo) throws Exception;
	
	/**
	 * 更撤销记录
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-05-20 10:37:32
	 */
    ApiResponseResult updateRevokeInfo(RevokeInfoUpdateVo vo) throws Exception;



	/**
	 * 撤销收费
	 * @param vo
	 * @return
	 * @throws Exception
	 */
    ApiResponseResult payRevoke(RevokeVo revokeVo) throws Exception;

}
