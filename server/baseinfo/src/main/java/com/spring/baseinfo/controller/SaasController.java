package com.spring.baseinfo.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.baseinfo.UserEntity;
import com.spring.base.entity.buiness.MyHouseEntity;
import com.spring.base.vo.baseinfo.user.*;
import com.spring.base.vo.buiness.myhouse.MyHouseUpdateVo;
import com.spring.base.vo.saas.BuyConfirmVo;
import com.spring.base.vo.saas.UpgradeVo;
import com.spring.baseinfo.service.ICompanyService;
import com.spring.baseinfo.service.IExcelService;
import com.spring.baseinfo.service.ISaasService;
import com.spring.baseinfo.service.IUserService;
import com.spring.common.annotation.CommunityPagePower;
import com.spring.common.annotation.TenantPagePower;
import com.spring.common.constants.MessageCode;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.excel.EasyExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author 作者：ZhaoJinHua
 * @date : 创建时间：2020-03-31 19:02:26
 * @Desc类说明: saas控制器
 */
@Slf4j
@RestController
@Api(value = "saas接口", tags = "saas接口")
@RequestMapping("saas")
public class SaasController extends BaseController{
	@Autowired
	private ISaasService saasService;

    /**
     * @description:购买正式应用
     * @return:
     * @author: 赵进华
     * @time: 2020/7/15 9:53
     */
	@ApiOperation(value = "购买正式应用")
	@PostMapping(value = "/buyApp")
	public ApiResponseResult buyApp(@RequestBody @Validated BuyConfirmVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return saasService.buyApp(vo);
	}

	/**
	 * @description:应用时间续费或者扩增小区数
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/7/15 11:41
	 */
	@ApiOperation(value = "应用时间续费或者扩增小区数")
	@PostMapping(value = "/extendApp")
	public ApiResponseResult extendApp(@RequestBody @Validated BuyConfirmVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return saasService.extendApp(vo);
	}

	/**
	 * @description:升级新应用
	 * @return:
	 * @author: 赵进华
	 * @time: 2020/7/15 11:42
	 */
	@ApiOperation(value = "升级新应用")
	@PostMapping(value = "/upgradeApp")
	public ApiResponseResult upgradeApp(@RequestBody @Validated UpgradeVo vo, BindingResult result) throws Exception {
		if (result != null && result.hasErrors()) {
			throw new ValidationException(result);
		}
		return saasService.upgradeApp(vo);
	}
}
