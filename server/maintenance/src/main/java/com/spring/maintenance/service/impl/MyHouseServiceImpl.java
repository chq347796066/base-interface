package com.spring.maintenance.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.CommunityEntity;
import com.spring.base.entity.baseinfo.CompanyEntity;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.user.UserUpdateLogo;
import com.spring.base.vo.buiness.myhouse.MyHouseAddVo;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.base.vo.buiness.myhouse.MyHouseVo;
import com.spring.common.constants.MessageCode;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import com.spring.maintenance.dao.IMyHouseDao;
import com.spring.maintenance.service.IMyHouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：denglei
 * @date : 创建时间：2020-04-17 09:46:08
 * @Desc类说明: 我的房屋信息业务接口实现类
 */
@Slf4j
@Service("myHouseService")
public class MyHouseServiceImpl extends BaseServiceImpl<MyHouseEntity, String> implements IMyHouseService {
	
	@Autowired
	private IMyHouseDao myHouseDao;
	@Autowired
	private BaseInfoFeignClient communityBuildCellHouseFeignCilnet;
	@Autowired
	private BaseInfoFeignClient userFeignCilnet;

	@Override
	public BaseDao getBaseMapper() {
		return myHouseDao;
	}
	
	/**
	 * 新增我的房屋信息
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-17 09:46:08
	 */
	@Override
	public ApiResponseResult addMyHouse(MyHouseAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		MyHouseEntity entity = new MyHouseEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		entity.setDelFlag(0);
		entity.setCompanyId(RequestUtils.getCompanyId());
		entity.setTenantId(RequestUtils.getTenantId());

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		entity.setProcessCode("FB"+dateString);
		String processName =entity.getUserName()+entity.getBuildName()+entity.getCellName()+entity.getHouseCode()+"认证";
		entity.setProcessName(processName);
		//'审核状态(0 待审核 1已通过 2未通过)'
		entity.setAuditStatus(0);
		//认证类型(1 房屋认证 2公司审核)
		entity.setAuthType(1);
		//客户业主姓名手机号
		HouseEntity houseEntity = new HouseEntity();
		houseEntity.setOwnerMobile(entity.getOwnerPhone());
		houseEntity.setOwnerName(entity.getOwnerName());
		houseEntity.setId(entity.getHouseId());
		houseEntity.setHouseCode(entity.getHouseCode());
		ApiResponseResult apiResponseResult = communityBuildCellHouseFeignCilnet.queryList(houseEntity);
		if(null != apiResponseResult.getData()){
			List<HouseEntity> houseEntities= (List<HouseEntity>)apiResponseResult.getData();
			if(houseEntities.size() == 0){
				result.setCode(MessageCode.FAIL);
				result.setMsg("输入的信息与业主预留信息不一致,请核实后输入");
				return result;
			}
		}

		// 新增
		int no = myHouseDao.insertMyHouse(entity);
		if (no > 0) {
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		} else {
			result.setCode(MessageCode.FAIL);
			result.setMsg("新增失败");
		}
		return result;
	}

	/**
	 * 更新我的房屋信息
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-17 09:46:08
	 */
	@Override
	public ApiResponseResult updateMyHouse(MyHouseUpdateVo vo) throws Exception {
		MyHouseEntity entity = new MyHouseEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		entity.setTenantId(RequestUtils.getTenantId());
		//审核状态(0 待审核 1已通过 2未通过)
		if(1 == entity.getAuditStatus() || 2==entity.getAuditStatus()){
			Date currentTime = new Date();
			entity.setAuditDate(currentTime);
		}
		if(1 == entity.getAuditStatus()){
			CommunityEntity communityEntity = communityBuildCellHouseFeignCilnet.queryCommunityEntity(vo.getCommunityId());
			if(null != communityEntity){
				CompanyEntity companyEntity = communityBuildCellHouseFeignCilnet.queryCompanyEntity(communityEntity.getCompanyId());
				if(companyEntity!=null){
					entity.setCompanyName(companyEntity.getCompanyName());
				}
			}
			UserUpdateLogo updateLogo = new UserUpdateLogo();
			updateLogo.setId(vo.getUserId());
			updateLogo.setCommunityId(vo.getCommunityId());
			updateLogo.setCompanyId(communityEntity.getCompanyId());
			userFeignCilnet.updateLogoUser(updateLogo);
			entity.setCompanyId(communityEntity.getCompanyId());
		}
		// 更新
		int no = myHouseDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	/**
	 * @Desc:    删除我的房屋
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/18 10:06
	 * @return: 返回
	 */
	 @Override
	 public ApiResponseResult deleteMyHouse(MyHouseEntity vo) throws Exception {
         ApiResponseResult result = new ApiResponseResult();
		 MyHouseEntity myHouseEntity = myHouseDao.queryMyHouseInfo(vo);
		 if(null!=myHouseEntity && myHouseEntity.getIdentityType() !=null){
			 //身份类型(1业主　２家庭成员　３租客)
			 if(myHouseEntity.getIdentityType() == 1){
				 result.setCode(MessageCode.FAIL);
				 result.setMsg("业主房屋不能删除，只允许删除租客或者家庭成员");
				 return result;
			 }
		 }
		 vo.setDelFlag(1);
		 int no = myHouseDao.deleteMyHouse(vo);
		 if (no > 0) {
			 return createSuccessResult(null);
		 }else{
			 return createFailResult();
		 }
	 }

	 /**
	  * @Desc:    我的租客列表
	  * @param vo
	  * @Author:邓磊
	  * @UpdateDate:2020/4/22 14:36
	  * @return: 返回
	  */
	@Override
	public ApiResponseResult queryRenterList(MyHouseVo vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		List<MyHouseEntity> myHouseEntities = myHouseDao.queryRenterList(vo);
		result.setData(myHouseEntities);
		result.setCode(MessageCode.SUCCESS);
		result.setMsg("成功");
		return result;
	}

	/**
	 * @Desc:    根据条件系统管理用户管理业主APP用户查询列表
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/5/7 16:31
	 * @return: 返回
	 */
	@Override
	public List<MyHouseEntity> queryUserAppHouseList(MyHouseEntity vo) throws Exception {
		return myHouseDao.queryList(vo);
	}
}
