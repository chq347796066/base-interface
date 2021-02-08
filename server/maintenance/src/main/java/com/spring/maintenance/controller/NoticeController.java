package com.spring.maintenance.controller;

import com.spring.base.entity.baseinfo.NoticeEntity;
import com.spring.common.response.ApiResponseResult;
import com.spring.maintenance.service.INoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dell
 */
@RestController
@Api(value = "通知公告", tags = "通知公告接口")
@RequestMapping("/inner/notice")
public class NoticeController {

    @Autowired
    private INoticeService noticeService;

    /**
     * 根据条件查询列表
     * @param vo
     * @return
     */
    @ApiOperation(value = "根据条件查询列表", response = NoticeEntity.class)
    @PostMapping(value = "/queryList")
    public ApiResponseResult queryList(@RequestBody NoticeEntity vo) throws Exception {
        return noticeService.queryNoticeList(vo);
    }

    /**
     * 根据主键id查询对象
     * @param id
     * @return
     */
    @ApiOperation(value = "根据主键id查询对象", response = NoticeEntity.class)
    @GetMapping(value = "/queryObject")
    public ApiResponseResult queryObject(@ApiParam(value = "主键id", required = true) @RequestParam(value = "id", required = true) String id) throws Exception {
        return noticeService.queryNotice(id);
    }


}
