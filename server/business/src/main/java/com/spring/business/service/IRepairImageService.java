package com.spring.business.service;

import com.spring.base.entity.buiness.RepairImageEntity;
import com.spring.base.service.IBaseService;
import com.spring.common.response.ApiResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2021-01-05 14:57:47
 * @Desc类说明: 报修图片业务接口类
 */
public interface IRepairImageService extends IBaseService<RepairImageEntity,Long>{

	/**
	 * 图片上传
	 * @param files
	 * @return
	 * @throws Exception
	 * @author 作者：ZhaoJinHua
	 * @version 创建时间：2021-01-05 14:57:47
	 */
	 ApiResponseResult uploadBatch(MultipartFile[] files) throws Exception;

	/**
	 * @description:删除文件
	 * @return:
	 * @author: 熊锋
	 * @time: 2020/4/3 17:23
	 */
	ApiResponseResult deleteFile(String picUrl) throws Exception;
}
