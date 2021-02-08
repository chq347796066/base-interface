package com.spring.maintenance.service.impl;
import com.spring.base.entity.baseinfo.PicEntity;
import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalAddVo;
import com.spring.base.vo.buiness.complaintproposal.ComplaintProposalUpdateVo;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.request.RequestUtils;
import com.spring.maintenance.dao.IComplaintProposalDao;
import com.spring.maintenance.service.IComplaintProposalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.base.dao.BaseDao;
import com.spring.base.service.impl.BaseServiceImpl;
import org.springframework.beans.BeanUtils;
import com.spring.common.constants.MessageCode;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：denglei
 * @date : 创建时间：2020-04-11 15:49:31
 * @Desc类说明: 业主投诉建议业务接口实现类
 */
@Slf4j
@Service("complaintProposalService")
public class ComplaintProposalServiceImpl extends BaseServiceImpl<ComplaintProposalEntity, String> implements IComplaintProposalService {
	
	@Autowired
	private IComplaintProposalDao complaintProposalDao;
	@Autowired
	private BaseInfoFeignClient picFeignCilnet;


	@Override
	public BaseDao getBaseMapper() {
		return complaintProposalDao;
	}
	
	/**
	 * 新增业主投诉建议
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-11 15:49:31
	 */
	@Override
	public ApiResponseResult addComplaintProposal(ComplaintProposalAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		ComplaintProposalEntity entity = new ComplaintProposalEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCompanyId(RequestUtils.getCompanyId());
		entity.setTenantId(RequestUtils.getTenantId());
		entity.setDelFlag(0);
		entity.setCreateUser(RequestUtils.getUserId());
		entity.setCreateDate(new Date());
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		entity.setStartDate(dateString);
		entity.setSubmitUser(RequestUtils.getUserId());
		//投诉状态（1 待处理 2 已完成 3 已评价　４已取消）
		entity.setProposalStatus(1);
		//删除标志（0 未删除 1 已删除）
		entity.setDelFlag(0);
		//投诉建议上传图片
		List<PicEntity> picEntityList = entity.getPicEntityList();
		List<PicEntity> list =new ArrayList<PicEntity>();
		if(CollectionUtils.isNotEmpty(picEntityList)){
			final String pictureId = UUIDFactory.createId();
			entity.setPictureId(pictureId);
			picEntityList.stream().forEach(picEntity -> {
				PicEntity picVo = new PicEntity();
				picVo.setId(UUIDFactory.createId());
				picVo.setTableId(pictureId);//投诉上传的图片
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
				log.info("添加业主投诉上传的图片"+list);
				picFeignCilnet.addPicList(list);
			}
		}
		int no = complaintProposalDao.insertComplaintProposal(entity);
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
	 * 更新业主投诉建议
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-11 15:49:31
	 */
	@Override
	public ApiResponseResult updateComplaintProposal(ComplaintProposalUpdateVo vo) throws Exception {
		ComplaintProposalEntity entity = new ComplaintProposalEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		Integer proposalStatus = entity.getProposalStatus();
		//投诉状态（1 待处理 2 已完成 3 已评价　４已取消）
		if(2  == proposalStatus || 4 == proposalStatus){
			//投诉完成日期
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			entity.setEndDate(dateString);
		}
		//审核处理结果图片
		List<PicEntity> picEntityList = entity.getPicEntityList();
		List<PicEntity> list =new ArrayList<PicEntity>();
		if(CollectionUtils.isNotEmpty(picEntityList)){
			final  String resultId = UUIDFactory.createId();
			entity.setResultId(resultId);
			picEntityList.stream().forEach(picEntity -> {
				PicEntity picVo = new PicEntity();
				picVo.setId(UUIDFactory.createId());
				picVo.setTableId(resultId);//审核完成处理结果图片ID
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
				log.info("添加物业审核上传的图片"+list);
				picFeignCilnet.addPicList(list);
			}
		}
		// 更新
		int no = complaintProposalDao.updateComplaintProposal(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	/**
	 * 根据条件查询列表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryListComplaintProposal(ComplaintProposalEntity vo) throws Exception{
		ApiResponseResult result = new ApiResponseResult();
		List<ComplaintProposalEntity> complaintProposalEntities = complaintProposalDao.queryListComplaintProposal(vo);
		if(CollectionUtils.isNotEmpty(complaintProposalEntities)){
			complaintProposalEntities.stream().forEach(complaintProposalEntity -> {
				if(StringUtils.isNotEmpty(complaintProposalEntity.getPictureId())){
					PicEntity picEntity = new PicEntity();//投诉上传的图片
					picEntity.setTableId(complaintProposalEntity.getPictureId());
					ApiResponseResult apiResponseResult = null;
					try {
						apiResponseResult = picFeignCilnet.queryPicEntityVoList(picEntity);
						log.info("获取业主上传的投诉图片"+apiResponseResult.getData());
					} catch (Exception e) {
						log.error("获取业主上传的投诉图片"+e);
						e.printStackTrace();
					}
					if(null !=  apiResponseResult.getData()){
						List<PicEntity> picEntityList= (List<PicEntity>) apiResponseResult.getData();
						complaintProposalEntity.setPicEntityList(picEntityList);
					}
				}
				if(StringUtils.isNotEmpty(complaintProposalEntity.getResultId())){
					PicEntity picEntity = new PicEntity();//审核完成处理结果图片ID
					picEntity.setTableId(complaintProposalEntity.getResultId());
					ApiResponseResult apiResponseResult1 = null;
					try {
						 apiResponseResult1 = picFeignCilnet.queryPicEntityVoList(picEntity);
						log.info("获取物业审核操作者上传的图片"+apiResponseResult1.getData());
					} catch (Exception e) {
						log.error("获取物业审核操作者上传的图片"+e);
						e.printStackTrace();
					}
					if(null !=  apiResponseResult1.getData()){
						List<PicEntity> picPictureList = (List<PicEntity>) apiResponseResult1.getData();
						complaintProposalEntity.setPicPictureList(picPictureList);
					}
				}
			});
		}
		result.setData(complaintProposalEntities);
		result.setCode(MessageCode.SUCCESS);
		result.setMsg("成功");
		return result;
	}

	/**
	 * 查询对象详情
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@Override
	public ApiResponseResult queryComplaintProposal(ComplaintProposalEntity vo) throws Exception {
		ApiResponseResult result = new ApiResponseResult();
		ComplaintProposalEntity complaintProposalEntity = complaintProposalDao.queryComplaintProposal(vo);
		if(null != complaintProposalEntity) {
			if (StringUtils.isNotEmpty(complaintProposalEntity.getPictureId())) {
				PicEntity picEntity = new PicEntity();
				picEntity.setTableId(complaintProposalEntity.getPictureId());
				ApiResponseResult apiResponseResult = null;
				try {
					apiResponseResult = picFeignCilnet.queryPicEntityVoList(picEntity);
					log.info("获取业主上传的投诉图片"+apiResponseResult.getData());
				} catch (Exception e) {
					log.error("获取业主上传的投诉图片"+e);
					e.printStackTrace();
				}
				if (null != apiResponseResult.getData()) {
					List<PicEntity> picEntityList = (List<PicEntity>) apiResponseResult.getData();
					complaintProposalEntity.setPicEntityList(picEntityList);
				}
			}
			if (StringUtils.isNotEmpty(complaintProposalEntity.getResultId())) {
				PicEntity picEntity = new PicEntity();
				picEntity.setTableId(complaintProposalEntity.getResultId());
				ApiResponseResult apiResponseResult1 = null;
				try {
					apiResponseResult1 = picFeignCilnet.queryPicEntityVoList(picEntity);
					log.info("获取物业审核操作者上传的图片"+apiResponseResult1.getData());
				} catch (Exception e) {
					log.error("获取物业审核操作者上传的图片"+e);
					e.printStackTrace();
				}
				if (null != apiResponseResult1.getData()) {
					List<PicEntity> picPictureList = (List<PicEntity>) apiResponseResult1.getData();
					complaintProposalEntity.setPicPictureList(picPictureList);
				}
			}
		}
		result.setData(complaintProposalEntity);
		result.setCode(MessageCode.SUCCESS);
		result.setMsg("成功");
		return result;
	}

	/**
	 * @Desc:物业管理系统-物业服务-投诉建议导出调用列表
	 * @param requestPageVO
	 * @Author:邓磊
	 * @UpdateDate:2020/5/15 17:08
	 * @return: 返回
	 */
	@Override
	public List<ComplaintProposalEntity> queryProposalList(ComplaintProposalEntity requestPageVO) throws Exception {
		return complaintProposalDao.queryListComplaintProposal(requestPageVO);
	}
}
