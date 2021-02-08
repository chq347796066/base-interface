package com.spring.maintenance.service.impl;
import com.spring.base.entity.baseinfo.PicEntity;
import com.spring.base.entity.buiness.RepairPersonnelEntity;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.buiness.repairpersonnel.RepairPersonnelAddVo;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairAddVo;
import com.spring.base.vo.buiness.reportingrepai.ReportingRepairUpdateVo;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.request.RequestUtils;
import com.spring.maintenance.dao.IRepairPersonnelDao;
import com.spring.maintenance.dao.IReportingRepairDao;
import com.spring.maintenance.service.IReportingRepairService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-11 16:03:31
 * @Desc类说明: 业主报事报修业务接口实现类
 */
@Slf4j
@Service("reportingRepairService")
public class ReportingRepairServiceImpl extends BaseServiceImpl<ReportingRepairEntity, String> implements IReportingRepairService {
	
	@Autowired
	private IReportingRepairDao reportingRepairDao;

	@Autowired
	private IRepairPersonnelDao repairPersonnelDao;

	@Autowired
	private BaseInfoFeignClient picFeignCilnet;


	@Override
	public BaseDao getBaseMapper() {
		return reportingRepairDao;
	}
	
	/**
	 * 新增业主报事报修
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-11 16:03:31
	 */
	@Override
	public ApiResponseResult addReportingRepair(ReportingRepairAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		ReportingRepairEntity entity = new ReportingRepairEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setDelFlag(0);
		entity.setCompanyId(RequestUtils.getCompanyId());
		entity.setTenantId(RequestUtils.getTenantId());
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		entity.setStartDate(dateString);
		//报修状态（1 待派单 2 待接单 3 处理中　４待确认 5 已完成 6 已评价 7 已取消）
		entity.setReportingStatus(1);
		List<PicEntity> picEntityList = vo.getPicEntityList();
		List<PicEntity> list =new ArrayList<PicEntity>();
		if(CollectionUtils.isNotEmpty(picEntityList)){
			final String repairId = UUIDFactory.createId();
			entity.setRepairId(repairId);
			picEntityList.stream().forEach(picEntity -> {
				PicEntity picVo = new PicEntity();
				picVo.setId(UUIDFactory.createId());
				picVo.setTableId(repairId);//业主报修图片ID
				picVo.setDataId(picEntity.getDataId());
				picVo.setName(picEntity.getName());
				picVo.setUrl(picEntity.getUrl());
				picVo.setTenantId(RequestUtils.getUserId());
				picVo.setDelFlag(0);//删除标志（0 未删除 1 已删除）")
				picVo.setCreateUser(RequestUtils.getUserId());
				picVo.setCreateDate(new Date());
				list.add(picVo);
			});
			if(CollectionUtils.isNotEmpty(list)){
				log.info("添加业主报修上传的图片"+list);
				picFeignCilnet.addPicList(list);
			}
		}
		log.info("addReportingRepair,tenantId:"+RequestUtils.getTenantId()+",companyId:"+RequestUtils.getCompanyId()+",communityId:"+RequestUtils.getCommunityId());
		// 新增
		int no = reportingRepairDao.addReportingRepair(entity);
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
	 * 更新业主报事报修
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-11 16:03:31
	 */
	@Override
	public ApiResponseResult updateReportingRepair(ReportingRepairUpdateVo vo) throws Exception {
		ReportingRepairEntity entity = new ReportingRepairEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		//报修状态（1 待派单 2 待接单 3 处理中　４待确认 5 已完成 6 已评价 7 已取消）
		if(5 == vo.getReportingStatus()  || 7 == vo.getReportingStatus()){
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String endDate = formatter.format(currentTime);
			entity.setEndDate(endDate);
		}
		if(3 == vo.getReportingStatus()){
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String repairerDate = formatter.format(currentTime);
			entity.setRepairerReacceptDate(repairerDate);
		}
		if(4  == vo.getReportingStatus()){
			List<PicEntity> picEntityList = vo.getPicEntityList();
			List<PicEntity> list =new ArrayList<PicEntity>();
			if(CollectionUtils.isNotEmpty(picEntityList)){
				final String outlayId = UUIDFactory.createId();
				entity.setOutlayId(outlayId);
				picEntityList.stream().forEach(picEntity -> {
					PicEntity picVo = new PicEntity();
					picVo.setId(UUIDFactory.createId());
					picVo.setTableId(outlayId);
					picVo.setDataId(picEntity.getDataId());
					picVo.setName(picEntity.getName());
					picVo.setUrl(picEntity.getUrl());
					picVo.setTenantId(RequestUtils.getUserId());
					picVo.setDelFlag(0);//删除标志（0 未删除 1 已删除）")
					picVo.setCreateUser(RequestUtils.getUserId());
					picVo.setCreateDate(new Date());
					list.add(picVo);
				});
				if(CollectionUtils.isNotEmpty(list)){
					log.info("添加业主报修上传的图片"+list);
					picFeignCilnet.addPicList(list);
				}
			}
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String finiDate = formatter.format(currentTime);
			entity.setFinishDate(finiDate);
		}
		if(2 == vo.getReportingStatus() && CollectionUtils.isNotEmpty(vo.getPersonnelAddVoList())){
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String endDate = formatter.format(currentTime);
			entity.setDispatchDate(endDate);
			RepairPersonnelEntity per= new RepairPersonnelEntity();
			per.setReportingRepairId(vo.getId());
			per.setDelFlag(1);
			repairPersonnelDao.updateBrepairPersonnel(per);
			List<RepairPersonnelEntity> repairList = new ArrayList<RepairPersonnelEntity>();
			vo.getPersonnelAddVoList().stream().forEach(repairPersonnelAddVo -> {
				RepairPersonnelEntity personnelEntity= new RepairPersonnelEntity();
				BeanUtils.copyProperties(repairPersonnelAddVo, personnelEntity);
				personnelEntity.setId(UUIDFactory.createId());
				personnelEntity.setReportingRepairId(vo.getId());//主报事报修ID
				personnelEntity.setDelFlag(0);
				personnelEntity.setTenantId(RequestUtils.getTenantId());
				personnelEntity.setCreateUser(RequestUtils.getUserId());
				personnelEntity.setCreateDate(new Date());
				repairList.add(personnelEntity);
			});
			if(CollectionUtils.isNotEmpty(repairList)){
				repairPersonnelDao.addList(repairList);
			}
		}
		// 更新
		int no = reportingRepairDao.updateReportingRepair(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	/**
	 * id 查询对象
	 * @param reportingRepairEntity
	 * @return
	 */
	@Override
	public ApiResponseResult getReportingRepair(ReportingRepairEntity reportingRepairEntity) {
		ApiResponseResult result = new ApiResponseResult();
		if(null != reportingRepairEntity) {
			ReportingRepairEntity reportingRepair = reportingRepairDao.getReportingRepair(reportingRepairEntity);
			//图片
			if(null != reportingRepair) {
				if (StringUtils.isNotEmpty(reportingRepair.getRepairId())) {
					PicEntity picEntity = new PicEntity();
					picEntity.setTableId(reportingRepair.getRepairId());
					ApiResponseResult apiResponseResult = null;
					try {
						apiResponseResult = picFeignCilnet.queryPicEntityVoList(picEntity);
						log.info("获取业主报修图片"+apiResponseResult.getData());
					} catch (Exception e) {
						log.error("获取业主报修图片"+e);
						e.printStackTrace();
					}
					if (null != apiResponseResult.getData()) {
						List<PicEntity> picEntityList = (List<PicEntity>) apiResponseResult.getData();
						reportingRepair.setPicEntityList(picEntityList);
					}
				}
				if (StringUtils.isNotEmpty(reportingRepair.getOutlayId())) {
					PicEntity picEntity = new PicEntity();
					picEntity.setTableId(reportingRepair.getOutlayId());
					ApiResponseResult apiResponseResult1 = null;
					try {
						apiResponseResult1 = picFeignCilnet.queryPicEntityVoList(picEntity);
						log.info("获取维修人员费用的图片"+apiResponseResult1.getData());
					} catch (Exception e) {
						log.error("获取维修人员费用的图片"+e);
						e.printStackTrace();
					}
					if (null != apiResponseResult1.getData()) {
						List<PicEntity> picPictureList = (List<PicEntity>) apiResponseResult1.getData();
						reportingRepair.setRepairPersonnelList(picPictureList);
					}
				}
			}
			//查询维修人员
			RepairPersonnelEntity per= new RepairPersonnelEntity();
			per.setReportingRepairId(reportingRepair.getId());
			if(null != per){
				List<RepairPersonnelEntity> repairPersonnelEntities = repairPersonnelDao.queryRepairPersonnelEntityList(per);
				List<RepairPersonnelAddVo> list = new ArrayList<RepairPersonnelAddVo>();
				if(CollectionUtils.isNotEmpty(repairPersonnelEntities)){
					repairPersonnelEntities.stream().forEach(repairPersonnelEntity -> {
						RepairPersonnelAddVo addVo = new RepairPersonnelAddVo();
						addVo.setReportingRepairId(repairPersonnelEntity.getReportingRepairId());
						addVo.setUserId(repairPersonnelEntity.getUserId());
						addVo.setUserName(repairPersonnelEntity.getUserName());
						addVo.setMobile(repairPersonnelEntity.getMobile());
						list.add(addVo);
					});
				}
				if(CollectionUtils.isNotEmpty(list)){
					reportingRepair.setPersonnelAddVoList(list);
				}
			}
			result.setData(reportingRepair);
			result.setCode(MessageCode.SUCCESS);
			result.setMsg("成功");
		}
		return result;
	}

	/**
	 * @Desc: APP端列表不分页
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/22 15:38
	 */
	@Override
	public ApiResponseResult queryReportingRepairList(ReportingRepairEntity vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		List<ReportingRepairEntity> reportingRepairList = reportingRepairDao.queryList(vo);
		if(CollectionUtils.isNotEmpty(reportingRepairList)){
			reportingRepairList.stream().forEach(reportingRepairEntity -> {
				if(StringUtils.isNotEmpty(reportingRepairEntity.getRepairId())){
					PicEntity picEntity = new PicEntity();//业主报修的图片ID
					picEntity.setTableId(reportingRepairEntity.getRepairId());
					ApiResponseResult apiResponseResult = null;
					try {
						apiResponseResult = picFeignCilnet.queryPicEntityVoList(picEntity);
						log.info("获取业主报修的图片"+apiResponseResult.getData());
					} catch (Exception e) {
						log.error("获取业主报修的图片"+e);
						e.printStackTrace();
					}
					if(null !=  apiResponseResult.getData()){
						List<PicEntity> picEntityList= (List<PicEntity>) apiResponseResult.getData();
						reportingRepairEntity.setPicEntityList(picEntityList);
					}
				}
				if(StringUtils.isNotEmpty(reportingRepairEntity.getOutlayId())){
					PicEntity picEntity = new PicEntity();//获取维修人员费用的图片ID
					picEntity.setTableId(reportingRepairEntity.getOutlayId());
					ApiResponseResult apiResponseResult1 = null;
					try {
						apiResponseResult1 = picFeignCilnet.queryPicEntityVoList(picEntity);
						log.info("获取维修人员费用的图片"+apiResponseResult1.getData());
					} catch (Exception e) {
						log.error("获取维修人员费用的图片"+e);
						e.printStackTrace();
					}
					if(null !=  apiResponseResult1.getData()){
						List<PicEntity> picPictureList = (List<PicEntity>) apiResponseResult1.getData();
						reportingRepairEntity.setRepairPersonnelList(picPictureList);
					}
				}
			});
		}
		result.setData(reportingRepairList);
		result.setCode(MessageCode.SUCCESS);
		result.setMsg("成功");
		return result;
	}

	
	/**
	 * @Desc:    java类作用描述
	 * @param vo
	 * @Author:邓磊
	 * @UpdateDate:2020/4/29 14:47
	 * @return: 返回
	 */
	@Override
	public List<ReportingRepairEntity> queryReporList(ReportingRepairEntity vo) throws Exception {
		List<ReportingRepairEntity> reportingRepairEntities = reportingRepairDao.queryReportingRepairList(vo);
		return reportingRepairEntities;
	}
}
