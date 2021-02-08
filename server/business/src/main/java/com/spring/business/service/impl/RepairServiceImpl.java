package com.spring.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.dto.CommunityDto;
import com.spring.base.entity.buiness.RepairCommentEntity;
import com.spring.base.entity.buiness.RepairEntity;
import com.spring.base.entity.buiness.RepairImageEntity;
import com.spring.base.entity.buiness.RepairProcessEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.business.dao.*;
import com.spring.business.dto.*;
import com.spring.business.service.IRepairService;
import com.spring.business.vo.RepairAddVo;
import com.spring.business.vo.RepairCloseVo;
import com.spring.business.vo.RepairQueryVo;
import com.spring.business.vo.WebRepairQueryVo;
import com.spring.common.constants.RepairConstants;
import com.spring.common.feign.client.BaseInfoFeignClient;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.SnowflakeIdWorker;
import com.spring.common.util.date.LocalDateTimeUtil;
import com.spring.common.util.list.ListUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 作者：熊锋
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报事报修业务接口实现类
 */

@Slf4j
@Service("repairService")
public class RepairServiceImpl extends BaseServiceImpl<RepairEntity, Long> implements IRepairService {
	
	@Autowired
	private IRepairDao repairDao;

	@Autowired
	private IRepairImageDao repairImageDao;

	@Autowired
	private IRepairProcessDao repairProcessDao;

	@Autowired
	private IHousingCertificationDao housingCertificationDao;

	@Autowired
	private IRepairCommentDao repairCommentDao;

	@Autowired
	private BaseInfoFeignClient baseInfoFeignClient;

	@Override
	public BaseDao getBaseMapper() {
		return repairDao;
	}
	
	/**
	 * 新增报事报修
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：熊锋
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult createRepair(RepairAddVo vo) throws Exception {
		log.info("创建报修报修参数: {}",vo.toString());
		// 返回的对象
		List<String> picList=vo.getPicUrlList();
		RepairEntity entity = new RepairEntity();
		RepairProcessEntity repairProcessEntity=new RepairProcessEntity();
		List<RepairImageEntity> list=new ArrayList<>();
		BeanUtils.copyProperties(vo, entity);
		entity.setRepairOrder("BX"+System.currentTimeMillis());
		entity.setId(SnowflakeIdWorker.generateId());
		entity.setRepairStatus(RepairConstants.COMMIT);
		entity.setOwnerId(RequestUtils.getUserId());
		//新增报修工单
		int no = repairDao.insert(entity);
		if(CollectionUtils.isNotEmpty(picList)){
			//组装数据
			picList.stream().forEach(pic->{
				RepairImageEntity repairImageEntity=new RepairImageEntity();
				repairImageEntity.setRepairId(Long.valueOf(entity.getId()));
				repairImageEntity.setId(SnowflakeIdWorker.generateId());
				repairImageEntity.setDocType(RepairConstants.REPAIR_PIC);
				repairImageEntity.setPicPath(pic);
				repairImageEntity.setTenantId(RequestUtils.getTenantId());
				list.add(repairImageEntity);
			});
			//往图片表插入报修图片
			repairImageDao.addList(list);
		}
		//往报修流程表插入数据
		repairProcessEntity.setRepairId(entity.getId());
		repairProcessEntity.setProcessType(RepairConstants.PROCESS_SUBMIT);
		repairProcessEntity.setHandleJobId(RequestUtils.getJobId());
		repairProcessEntity.setHandleJobName(RequestUtils.getJobName());
		repairProcessEntity.setHandlePhone(RequestUtils.getUserCode());
		if (StringUtils.isBlank(RequestUtils.getUserName())){
			repairProcessEntity.setHandleUser(vo.getOwnerName());
		}else{
			repairProcessEntity.setHandleUser(RequestUtils.getUserName());
		}
		Integer num=saveRepairProcess(repairProcessEntity);
		if (no > 0 && num>0) {
			return createSuccessResult(entity.getRepairOrder());
		} else {
			return createFailResult("新增工单失败");
		}
	}

	/**
	 * 取消报事报修
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 作者：熊锋
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult cancelRepair(Long id,String remark){
		log.info("报修单id: {}",id);
		log.info("报修取消原因: {}",remark);
		RepairProcessEntity repairProcessEntity=new RepairProcessEntity();
		QueryWrapper<RepairEntity> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("id",id).eq("del_flag",0);
		RepairEntity repairEntity=repairDao.selectOne(queryWrapper);
		if (!repairEntity.getRepairStatus().equals(RepairConstants.COMMIT)){
			return createFailResult("工单已被处理，不可取消");
		}
		//设置报修状态为用户取消报修
		repairEntity.setRepairStatus(RepairConstants.CANCEL);
		Integer flag=repairDao.updateById(repairEntity);
		//往报修流程表插入一条取消报修数据
		repairProcessEntity.setRepairId(id);
		repairProcessEntity.setProcessType(RepairConstants.PROCESS_CANCEL);
		repairProcessEntity.setHandleDate(new Date());
		repairProcessEntity.setRemark(remark);
		repairProcessEntity.setHandleUser(RequestUtils.getUserName());
		repairProcessEntity.setHandlePhone(RequestUtils.getUserCode());
		Integer num=saveRepairProcess(repairProcessEntity);
		if (flag> 0 && num>0) {
			return createSuccessResult(null);
		} else {
			return createFailResult("工单取消失败");
		}
	}

	/**
	 * 查询报修列表
	 * @param repairQueryVo
	 * @return
	 * @throws Exception
	 * @author 作者：熊锋
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	@Override
	public ApiResponseResult queryRepairList(RepairQueryVo repairQueryVo) throws Exception {
		log.info("查询报修列表入参为: {}",repairQueryVo.toString());
		PageHelper.startPage(repairQueryVo.getCurrentPage(), repairQueryVo.getPageSize(), true);
		ApiResponseResult result=new ApiResponseResult();
		if (repairQueryVo.getRepairStatus()==null){
			return createFailResult("工单报修状态有误");
		}

		//查询工单数据
		ApiResponseResult apiResponseResult = getRepairList(repairQueryVo, result);
		if (apiResponseResult != null){
			return apiResponseResult;
		}
		return result;
	}

	/**
	 * 查询报修列表（待处理，处理中，已完成）
	 * @param repairQueryVo
	 * @return
	 * @throws Exception
	 * @author 作者：熊锋
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	private ApiResponseResult getRepairList(RepairQueryVo repairQueryVo, ApiResponseResult result) throws Exception {
		QueryWrapper<RepairEntity> queryWrapper=new QueryWrapper<>();
		//获取项目id
		String communityId=RequestUtils.getCommunityId();
		log.info("当前登录人所属项目id为 :{}",communityId);
		List<String> communityIds=new ArrayList<>();
		if (StringUtils.isBlank(communityId)){
			List<CommunityDto> resultInfo=baseInfoFeignClient.getCommunityInfo();
			for (CommunityDto dto :resultInfo){
				communityIds.add(dto.getCommunityId());
			}
		}else {
			communityIds.add(communityId);
		}
		//查询待处理工单列表
		if (repairQueryVo.getRepairStatus().equals(RepairConstants.PENDING)){
			//物管端查询
			if (repairQueryVo.getChannelSource()==1){
				queryWrapper.eq("del_flag",0).eq("repair_status",1).
						in("community_id",communityIds).orderByDesc("create_date");
			}
			//业主端查询
			if (repairQueryVo.getChannelSource()==0){
				queryWrapper.eq("del_flag",0).in("repair_status",1).
						eq("owner_id",RequestUtils.getUserId()).orderByDesc("create_date");
			}
			return queryRepair(queryWrapper);
		}
		//查询处理中工单列表
		if (repairQueryVo.getRepairStatus().equals(RepairConstants.BEGIN_PROCESS)){
			//物管端查询
			if (repairQueryVo.getChannelSource()==1){
				queryWrapper.eq("del_flag",0).in("repair_status",4,5,6,9).
						in("community_id",communityIds).orderByDesc("create_date");
			}
			//业主端查询
			if (repairQueryVo.getChannelSource()==0){
				queryWrapper.eq("del_flag",0).in("repair_status",4,5,6,9).
						eq("owner_id",RequestUtils.getUserId()).orderByDesc("create_date");
			}
			return queryRepair(queryWrapper);
		}
		//查询已完成工单列表
		if (repairQueryVo.getRepairStatus().equals(RepairConstants.COMPLETED)){
			//物管端查询
			if (repairQueryVo.getChannelSource()==1){
				queryWrapper.eq("del_flag",0).in("repair_status",2,3,7,8).
						in("community_id",communityIds).orderByDesc("create_date");
			}
			//业主端查询
			if (repairQueryVo.getChannelSource()==0){
				queryWrapper.eq("del_flag",0).in("repair_status",2,3,7,8).
						eq("owner_id",RequestUtils.getUserId()).orderByDesc("create_date");
			}
			return queryRepair(queryWrapper);
		}
		return result;
	}

	/**
	 * 抽取函数
	 * @return
	 * @throws Exception
	 * @author 作者：熊锋
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	private ApiResponseResult queryRepair(QueryWrapper<RepairEntity> queryWrapper) {
		List<RepairEntity> repairEntityList = repairDao.selectList(queryWrapper);
		PageInfo<RepairEntity> pageInfo = new PageInfo<>(repairEntityList);
		return createSuccessResult(pageInfo);
	}

	/**
	 * 查询报修工单详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 作者：熊锋
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	@Override
	public ApiResponseResult queryRepairDetail(String id){
		log.info("当前工单详情id为: {}",id);
		RepairDetailDto repairDetailDto=new RepairDetailDto();
		List<RepairProcessDto> repairProcessDtoList=new ArrayList<>();
		List<RepairCommentDto> repairCommentList=new ArrayList<>();
		QueryWrapper<RepairEntity> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("del_flag",0).eq("id",id);
		//查询报修详情
		RepairEntity repairEntity=repairDao.selectOne(queryWrapper);
		if (repairEntity==null){
			return createSuccessResult(null);
		}
		BeanUtils.copyProperties(repairEntity,repairDetailDto);
		//用户报修图片
		QueryWrapper<RepairImageEntity> wrapper=new QueryWrapper<>();
		wrapper.eq("del_flag",0).eq("repair_id",id).eq("doc_type",1);
		repairDetailDto.setPicPath(getPicList(wrapper));

		//作业完成图片
		QueryWrapper<RepairImageEntity> workWrapper=new QueryWrapper<>();
		workWrapper.eq("del_flag",0).eq("repair_id",id).eq("doc_type",2);
		List<String> workPic=getPicList(workWrapper);

		//查询报修流程
		QueryWrapper<RepairProcessEntity> processWrapper=new QueryWrapper<>();
		processWrapper.eq("repair_id",id).eq("del_flag",0).orderByDesc("create_date");
		List<RepairProcessEntity> repairProcessEntityList=repairProcessDao.selectList(processWrapper);
		ListUtil.copy(repairProcessEntityList,RepairProcessDto.class);
		CollectionUtils.addAll(repairProcessDtoList,ListUtil.copy(repairProcessEntityList,RepairProcessDto.class));
		repairProcessDtoList.stream().forEach(processDto->{
			if (processDto.getProcessType().equals(RepairConstants.PROCESS_FINISH) || processDto.getProcessType().equals(RepairConstants.PROCESS_PAY)){
				processDto.setPicPath(workPic);
			}
			if (processDto.getProcessType().equals(RepairConstants.PROCESS_FINISH)){
				processDto.setCost(repairEntity.getCost());
			}
		});
		//查询评论信息
		QueryWrapper<RepairCommentEntity> commentWrapper=new QueryWrapper<>();
		commentWrapper.eq("del_flag",0).eq("repair_id",id);
		List<RepairCommentEntity> commentList=repairCommentDao.selectList(commentWrapper);
		ListUtil.copy(commentList,RepairCommentDto.class);
		CollectionUtils.addAll(repairCommentList,ListUtil.copy(commentList,RepairCommentDto.class));

		repairDetailDto.setRepairCommentList(repairCommentList);
		repairDetailDto.setRepairProcessDtoList(repairProcessDtoList);
		return createSuccessResult(repairDetailDto);
	}

	/**
	 * 管家关闭工单
	 * @param repairCloseVo
	 * @return
	 * @throws Exception
	 * @author 作者：熊锋
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ApiResponseResult closeRepair(RepairCloseVo repairCloseVo) {
		RepairProcessEntity repairProcessEntity=new RepairProcessEntity();
		QueryWrapper<RepairEntity> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("id",repairCloseVo.getId()).eq("del_flag",0);
		RepairEntity repairEntity=repairDao.selectOne(queryWrapper);
		if (repairEntity==null ||!repairEntity.getRepairStatus().equals(RepairConstants.COMMIT)){
			return createFailResult("工单已被操作，不可关闭");
		}
		UpdateWrapper<RepairEntity> wrapper=new UpdateWrapper<>();
		wrapper.set("repair_status",RepairConstants.CLOSE);
		wrapper.eq("id",repairCloseVo.getId());
		//修改报修状态为管家关闭
		Integer flag=repairDao.update(repairEntity,wrapper);
		//汪流程表插入数据
		repairProcessEntity.setId(SnowflakeIdWorker.generateId());
		repairProcessEntity.setRepairId(repairCloseVo.getId());
		repairProcessEntity.setProcessType(RepairConstants.PROCESS_CLOSE);
		repairProcessEntity.setRemark(repairCloseVo.getRemark());
		repairProcessEntity.setHandleDate(new Date());
		repairProcessEntity.setHandleUser(RequestUtils.getUserName());
		repairProcessEntity.setHandlePhone(RequestUtils.getUserCode());
		repairProcessEntity.setHandleJobId(RequestUtils.getJobId());
		repairProcessEntity.setHandleJobName(RequestUtils.getJobName());
		Integer no = saveRepairProcess(repairProcessEntity);
		if (flag> 0 && no>0) {
			return createSuccessResult(null);
		} else {
			return createFailResult("关闭工单失败");
		}
	}

	/**
	 * @Author 熊锋
	 * @param repairProcessEntity
	 * @Description 汪流程表插入数据
	 * @author 作者：熊锋
	 * @Date 2021/1/6 16:39
	 */
	private Integer saveRepairProcess(RepairProcessEntity repairProcessEntity) {
		repairProcessEntity.setId(SnowflakeIdWorker.generateId());
		return repairProcessDao.insert(repairProcessEntity);
	}

	/**
	 * @Author 熊锋
	 * @param queryWrapper
	 * @Description 获取图片信息
	 * @author 作者：熊锋
	 * @Date 2021/1/6 16:39
	 */
	private List<String> getPicList(QueryWrapper<RepairImageEntity> queryWrapper) {
		//获取图片
		List<RepairImageEntity> list=repairImageDao.selectList(queryWrapper);
		if(CollectionUtils.isEmpty(list)){
			return new ArrayList<>();
		}
		List<String> picPathList=list.stream().map(repairImageEntity->repairImageEntity.getPicPath()).collect(Collectors.toList());
		return picPathList;
	}

	/**
	 * 查询报修人信息
	 * @return
	 * @throws Exception
	 * @author 作者：熊锋
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	@Override
	public ApiResponseResult queryRepairInfo(String userId) {
		List<HouseInfoDto> list=housingCertificationDao.queryRepairHouseInfo(userId);
		if (CollectionUtils.isNotEmpty(list)){
			return createSuccessResult(list);
		}
		return createSuccessResult(null);
	}

	/**
	 * web查询报事报修列表
	 * @return
	 * @throws Exception
	 * @author 作者：熊锋
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	@Override
	public ApiResponseResult queryWebRepairList(WebRepairQueryVo webRepairQueryVo) throws Exception {
		log.info("当前登录用户名为: {}",RequestUtils.getUserName());
		//查询今日报事报修
		if (webRepairQueryVo.getPeriodFlag()!=null && webRepairQueryVo.getPeriodFlag()==0){
			webRepairQueryVo.setPeriodStartTime(LocalDate.now().toString()+" 00:00:00");
			webRepairQueryVo.setPeriodEndTime(LocalDate.now().toString()+" 23:59:59");
		}
		//查询昨日报事报修
		if (webRepairQueryVo.getPeriodFlag()!=null && webRepairQueryVo.getPeriodFlag()==1){
			webRepairQueryVo.setPeriodStartTime(LocalDate.now().plusDays(-1).toString()+" 00:00:00");
			webRepairQueryVo.setPeriodEndTime(LocalDate.now().plusDays(-1).toString()+" 23:59:59");

		}
		//查询本周报事报修
		if (webRepairQueryVo.getPeriodFlag()!=null && webRepairQueryVo.getPeriodFlag()==2){
			webRepairQueryVo.setPeriodStartTime(LocalDateTimeUtil.getStartOrEndDayOfWeek(LocalDate.now(),true)+" 00:00:00");
			webRepairQueryVo.setPeriodEndTime(LocalDateTimeUtil.getStartOrEndDayOfWeek(LocalDate.now(),false)+" 23:59:59");
		}

		//根据登录者用户id查询项目id
		String companyId=RequestUtils.getCompanyId();
		log.error("当前登陆者公司id: {}"+companyId);
		List<CommunityDto> resultInfo=baseInfoFeignClient.getCommunityInfo();
		log.info("当前登录者所属项目信息: {}"+resultInfo);
		List<String> communityIds=new ArrayList<>();
		for (CommunityDto dto:resultInfo){
			communityIds.add(dto.getCommunityId());
		}
		log.info("当前登录者所属项目id为: {}"+String.valueOf(communityIds));
		webRepairQueryVo.setCommunityIds(communityIds);
		//查询报事报修列表
		List<WebRepairDto> list=repairDao.queryWebRepairList(webRepairQueryVo);

		if (CollectionUtils.isEmpty(list)){
			return createSuccessResult(new ArrayList<>());
		}
		PageHelper.startPage(webRepairQueryVo.getCurrentPage(), webRepairQueryVo.getPageSize(), true);
		PageInfo<WebRepairDto> pageInfo = new PageInfo<>(list);
		return createSuccessResult(pageInfo);
	}
}
