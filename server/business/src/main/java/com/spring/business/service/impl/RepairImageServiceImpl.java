package com.spring.business.service.impl;

import com.spring.base.dao.BaseDao;
import com.spring.base.entity.buiness.RepairImageEntity;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.base.vo.baseinfo.document.DocumentShowVo;
import com.spring.business.dao.IRepairImageDao;
import com.spring.business.service.IRepairImageService;
import com.spring.common.config.FdfsConfig;
import com.spring.common.constants.MessageCode;
import com.spring.common.fastdfs.CommonFastDfsUtil;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.id.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修图片业务接口实现类
 */

 @Slf4j
@Service("repairImageService")
public class RepairImageServiceImpl extends BaseServiceImpl<RepairImageEntity, Long> implements IRepairImageService {
	
	@Autowired
	private IRepairImageDao repairImageDao;

	@Autowired
	private CommonFastDfsUtil commonFastDfsUtil;

	@Autowired
	private FdfsConfig fdfsConfig;

	@Override
	public BaseDao getBaseMapper() {
		return repairImageDao;
	}

	 /**
	  * 上传图片
	  * @param files
	  * @return
	  * @throws Exception
	  * @author 作者：熊锋
	  * @version 创建时间：2021-01-05 14:57:47
	  */
	 @Override
	 public ApiResponseResult uploadBatch(MultipartFile[] files) throws Exception {
		 ApiResponseResult result=new ApiResponseResult();
		 List<DocumentShowVo> documentShowVoList=new ArrayList<>();
		 for (MultipartFile multipartFile:files) {
			 DocumentShowVo showVo = new DocumentShowVo();
			 //得到文件上传路径
			 String fastPath=commonFastDfsUtil.uploadFile(multipartFile);
			 //返回给前端路径带url
			 //返回给前端路径带url
			 String path=fdfsConfig.getResHost()+":"+fdfsConfig.getStoragePort()+"/"+fastPath;
			 showVo.setId(UUIDFactory.createId());
			 showVo.setName(multipartFile.getOriginalFilename());
			 showVo.setUrl(path);
			 documentShowVoList.add(showVo);
		 }
		 result.setCode(MessageCode.SUCCESS);
		 result.setMsg("文件上传成功！");
		 result.setData(documentShowVoList);
		 return result;
	 }

	/**
	 * @description:删除文件
	 * @return:
	 * @author: 熊锋
	 * @time: 2020/4/3 17:23
	 */
	@Override
	public ApiResponseResult deleteFile(String picUrl) throws Exception {
		ApiResponseResult result=new ApiResponseResult();
		commonFastDfsUtil.deleteFile(picUrl);
		result.setCode(MessageCode.SUCCESS);
		result.setMsg("文件删除成功！");
		return result;
	}
}
