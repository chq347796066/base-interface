package com.spring.baseinfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.NoticeEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.notice.NoticeAddVo;
import com.spring.base.vo.baseinfo.notice.NoticeUpdateVo;
import com.spring.baseinfo.dao.INoticeDao;
import com.spring.baseinfo.service.INoticeService;
import com.spring.common.constants.MessageCode;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-04-07 17:49:59
 * @Desc类说明: 通知公告业务接口实现类
 */

@Service("noticeService")
public class NoticeServiceImpl extends BaseServiceImpl<NoticeEntity, String> implements INoticeService {
	
	@Autowired
	private INoticeDao noticeDao;

	@Override
	public BaseDao getBaseMapper() {
		return noticeDao;
	}
	
	/**
	 * 新增通知公告
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2020-04-07 17:49:59
	 */
	@Override
	public ApiResponseResult addNotice(NoticeAddVo vo) throws Exception {
		// 返回的对象
		ApiResponseResult result = new ApiResponseResult();
		NoticeEntity entity = new NoticeEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setId(UUIDFactory.createId());
		entity.setCreateUser(RequestUtils.getUserId());
		Integer releaseStatus = entity.getReleaseStatus();
		if(releaseStatus == 2){
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			entity.setReleaseDate(dateString);
		}
		//拆分租户公司数组
		if (vo.getTenantCompanyArray() != null&&vo.getTenantCompanyArray().length>0) {
			entity.setTenantId(RequestUtils.getTenantId());
			entity.setCompanyId(vo.getTenantCompanyArray()[vo.getTenantCompanyArray().length-1]);
		}
		entity.setDelFlag(0);
		entity.setCreateDate(new Date());
		// 新增
		int no = noticeDao.insert(entity);
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
	 * 更新通知公告
	 * @param vo
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua 
	 * @version 创建时间：2020-04-07 17:49:59
	 */
	@Override
	public ApiResponseResult updateNotice(NoticeUpdateVo vo) throws Exception {
		NoticeEntity entity = new NoticeEntity();
		BeanUtils.copyProperties(vo, entity);
		entity.setModifyUser(RequestUtils.getUserId());
		entity.setModifyDate(new Date());
		if(entity.getReleaseStatus() == 3){
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			entity.setOfflineDate(dateString);
		}
		if(entity.getReleaseStatus() == 2){
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			entity.setReleaseDate(dateString);
		}
		//拆分租户公司数组
		if (vo.getTenantCompanyArray() != null) {
			entity.setTenantId(RequestUtils.getTenantId());
			entity.setCompanyId(vo.getTenantCompanyArray()[1]);
		}
		// 更新
		int no = noticeDao.updateById(entity);
		if (no > 0) {
			return createSuccessResult(null);
		}
		return createFailResult();
	}

	 /**
	  * @description:通知分页
	  * @return:
	  * @author: 赵进华
	  * @time: 2020/6/29 10:02
	  */
	 @Override
	 public ApiResponseResult queryPageNotice(RequestPageVO<NoticeEntity> requestPageVO) throws Exception {
		 ApiResponseResult result = new ApiResponseResult();
		 PageHelper.startPage(requestPageVO.getCurrentPage(), requestPageVO.getPageSize(), true);
		 List<NoticeEntity> list = getBaseMapper().queryList(requestPageVO.getEntity());
		 if(list!=null && list.size()>0)
		 {
			 list.forEach(item->{
				 //拼接租户公司数组
				 if(StringUtils.isEmpty(item.getCompanyId()))
				 {
					 String[] tenantCompanyArray = new String[1];
					 tenantCompanyArray[0] = item.getTenantId();
					 item.setTenantCompanyArray(tenantCompanyArray);
				 }else
				 {
					 String[] tenantCompanyArray = new String[2];
					 tenantCompanyArray[0] = item.getTenantId();
					 tenantCompanyArray[1] = item.getCompanyId();
					 item.setTenantCompanyArray(tenantCompanyArray);
				 }
			 });
		 }
		 PageInfo<NoticeEntity> pageInfo = new PageInfo<NoticeEntity>(list);
		 return createSuccessResult(pageInfo);
	 }
 }
