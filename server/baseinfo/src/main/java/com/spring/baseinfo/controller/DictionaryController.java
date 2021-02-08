package com.spring.baseinfo.controller;

import com.spring.base.controller.BaseController;
import com.spring.base.entity.baseinfo.DictionaryEntity;
import com.spring.base.vo.baseinfo.dictionary.DictionaryAddVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryDataVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryNodeVo;
import com.spring.base.vo.baseinfo.dictionary.DictionaryUpdateVo;
import com.spring.common.annotation.TenantPagePower;
import com.spring.common.exception.ValidationException;
import com.spring.common.page.RequestPageVO;
import com.spring.common.redis.RedisCacheUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.baseinfo.service.IDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
* @author 作者：ZhaoJinHua
* @date : 创建时间：2020-03-18 17:32:18
* @Desc类说明: 字典控制器
*/
@RestController
@Api(value = "字典", tags = "字典接口")
@RequestMapping("dictionary")
public class DictionaryController extends BaseController {
   @Autowired
   private IDictionaryService dictionaryService;

    @Autowired
    private RedisCacheUtils redisUtils;

   /**
    * 新增
    *
    * @param vo
    * @param result
    * @return
    * @throws Exception
    */
   @ApiOperation(value = "新增")
   @PostMapping(value = "/add")
   public ApiResponseResult add(@RequestBody @Validated DictionaryAddVo vo, BindingResult result) throws Exception {
       if (result != null && result.hasErrors()) {
           throw new ValidationException(result);
       }
       return dictionaryService.addDictionary(vo);
   }

   /**
    * 更新
    *
    * @param vo
    * @param result
    * @return
    * @throws Exception
    */
   @ApiOperation(value = "更新")
   @PostMapping(value = "/update")
   public ApiResponseResult update(@RequestBody @Validated DictionaryUpdateVo vo,
                                   BindingResult result) throws Exception {
       if (result != null && result.hasErrors()) {
           throw new ValidationException(result);
       }
       return dictionaryService.updateDictionary(vo);
   }

   /**
    * 删除
    *
    * @param id
    * @return
    */
   @ApiOperation(value = "删除", notes = "删除")
   @GetMapping(value = "/delete")
   public ApiResponseResult delete(
           @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
           throws Exception {
       return dictionaryService.deleteDictionary(id);
   }

   /**
    * 根据主键id查询对象
    *
    * @param id
    * @return
    */
   @ApiOperation(value = "根据主键id查询对象", response = DictionaryEntity.class)
   @GetMapping(value = "/queryObject")
   public ApiResponseResult queryObject(
           @ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id)
           throws Exception {
       return dictionaryService.queryObject(id);
   }

   /**
    * 根据条件分页查询
    *
    * @param requestPageVO
    * @return
    */
   @TenantPagePower
   @ApiOperation(value = "根据条件分页查询", response = DictionaryNodeVo.class)
   @PostMapping(value = "/queryPage")
   public ApiResponseResult queryPage(@RequestBody RequestPageVO<DictionaryEntity> requestPageVO) throws Exception {
       return dictionaryService.queryPageDictionary(requestPageVO);
   }

   /**
    * @description:根据单个code查询字典列表
    * @return:
    * @author: 赵进华
    * @time: 2020/3/23 14:43
    */
    @ApiOperation(value = "根据单个code查询字典列表", response = DictionaryDataVo.class)
    @GetMapping(value = "/getDictionaryByOne")
    public ApiResponseResult getDictionaryByOne(
            @ApiParam(value = "父字典CODE", required = true) @RequestParam(value = "parentCode", required = true) String parentCode)
            throws Exception {
        return dictionaryService.getDictionaryByOne(parentCode);
    }

    /**
     * @description:根据多个code查询字典列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/23 14:43
     */
    @ApiOperation(value = "根据多个code查询字典列表")
    @GetMapping(value = "/getDictionaryByMulti")
    public ApiResponseResult getDictionaryByMulti(@ApiParam(value = "父字典CODE数组", required = true) @RequestParam("parentCodes") List<String> parentCodes)
            throws Exception {
        return dictionaryService.getDictionaryByMulti(parentCodes);
    }

}
