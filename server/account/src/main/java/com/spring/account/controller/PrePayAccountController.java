package com.spring.account.controller;

import com.spring.account.service.IPrePayService;
import com.spring.base.entity.account.SubAccountEntity;
import com.spring.base.entity.baseinfo.HouseEntity;
import com.spring.base.entity.pay.BdHousePrepayInfoDto;
import com.spring.base.entity.pay.CommonResult;
import com.spring.base.vo.pay.payonekeypay.BillOffsetVo;
import com.spring.base.vo.pay.payonekeypay.PayOneVo;
import com.spring.common.page.RequestPageVO;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/prePay")
@Slf4j
@Api(value = "账号", tags = "预缴信息接口")
public class PrePayAccountController {

    @Autowired
    private IPrePayService iPrePayService;

    /**
     * 预缴
     */
    @PostMapping(value = "/income")
    public CommonResult createPrePay(@RequestBody PayOneVo vo) throws Exception{
        //String xid = RootContext.getXID();
        //log.error("xid:"+xid);
        iPrePayService.createPrePay(vo);
        return new CommonResult("综合预缴成功!", 200);
    }

    /**
     * 预缴导入
     * @param bdHousePrepayInfoDtos
     * @return
     */
    @PostMapping(value = "/importIncome")
    public CommonResult importPrePay(@RequestBody List<BdHousePrepayInfoDto> bdHousePrepayInfoDtos) throws Exception{

        iPrePayService.importPrePay(bdHousePrepayInfoDtos);
        return new CommonResult("综合预缴导入成功!", 200);
    }

    /**
     * 根据条件查询账户余额
     * @param vo
     * @return
     */
    @PostMapping(value = "/queryPrePayList")
    public List<SubAccountEntity> queryPrePayList(@RequestBody BillOffsetVo billOffsetVos) throws Exception{

        return iPrePayService.queryPrePayList(billOffsetVos);
    }


    /**
     * 预缴导入
     * @param communityId
     * @param resp
     * @throws Exception
     */
    @GetMapping("/exportExcelModel" )
    @ApiOperation(value = "预缴导入",httpMethod = "GET")
    public void exportExcelModel(@RequestParam(value = "communityId",required = false)String communityId, HttpServletResponse resp) throws Exception {
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotEmpty(communityId)){
            params.put("communityId",communityId);
        }else {
            params.put("communityId",RequestUtils.getCommunityId());
        }

        List<LinkedHashMap<String,String>> mapList = iPrePayService.queryExportList(params);

        String[] titles = {"公司名称","公司编号","小区名称","小区编号","楼栋名称","楼栋id","单元名称","单元id","房屋编码","房屋编号","业主编码","业主姓名","手机号",  "预缴金额", "预缴时间"};
        this.exportExcel(titles,mapList,resp);


    }


    private void exportExcel(String[] titles,  List<LinkedHashMap<String, String>> list, HttpServletResponse res)
            throws Exception {
        // 生成一个excel工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一页工作表
        HSSFSheet sheet = wb.createSheet();
        // 设置第一列的宽度
        sheet.setColumnWidth(1, 17 * 256);
        // 设置第二列的宽度
        sheet.setColumnWidth(2, 15 * 256);
        // 创建第一行，存放表头
        HSSFRow row = sheet.createRow(0);
        // title背景颜色样式
        CellStyle titileStyle = wb.createCellStyle();
        titileStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        titileStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 文本
        HSSFCellStyle cellStyle2 = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();
        cellStyle2.setDataFormat(format.getFormat("@"));
        // 日期格式样式
        HSSFCellStyle dateStyle = wb.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));

        Font font2 = wb.createFont();
        font2.setFontHeightInPoints((short) 10); // 字体高度
        font2.setFontName("宋");
        font2.setColor(HSSFColor.RED.index);  //颜色
        HSSFRichTextString ts= null;
        String titleValue = null;
        //设置头信息
        for (int i = 0; i < titles.length; i++) {
            HSSFCell cell = row.createCell(i);
            titleValue = titles[i];
            ts=new HSSFRichTextString(titleValue);
//            if(titleValue.contains(titleEnd)){
//                ts.applyFont(ts.length()-2,ts.length()-1,font2);
//            }
            cell.setCellValue(ts);
            cell.setCellStyle(titileStyle);
        }


        for (int i = 0; i < list.size(); i++) {
            LinkedHashMap<String, String> map = list.get(i);
            Set<String> keySets = map.keySet();
            // 数据每增加一行，表格就再生成一行
            row = sheet.createRow(i + 1);
            int k = 0;
            for (String s : keySets) {
                Cell cell = row.createCell(k);
                cell.setCellValue(map.get(s));
                k++;

            }
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
        } catch (IOException e) {
            throw e;
        } finally {
            if (wb != null)
                wb.close();
            if (os != null) {
                os.close();
            }
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        res.reset();
        res.setContentType("application/vnd.ms-excel;charset=utf-8");
        res.setHeader("Content-Disposition",
                "attachment;filename=" + new String(("综合预缴导入模板" + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = res.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }

    }


    /**
     * 预收账户查询
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    @PostMapping("/getPrepayList" )
    @ApiOperation(value = "预收账户查询",httpMethod = "POST" ,response = SubAccountEntity.class)
    public ApiResponseResult getPrepayList(@RequestBody @Validated RequestPageVO<SubAccountEntity> requestPageVO) throws Exception {

        return iPrePayService.getPrepayList(requestPageVO);

    }

    /**
     * 预收账户金额查询
     * @param requestPageVO
     * @return
     * @throws Exception
     */
    @PostMapping("/getPrepay" )
    @ApiOperation(value = "预收账户金额查询",httpMethod = "POST" ,response = HouseEntity.class)
    public ApiResponseResult getPrepayList(@RequestBody SubAccountEntity requestPageVO) throws Exception {

        return iPrePayService.getPrepayList(requestPageVO);

    }

    /**
     * @description:异步导出预收Excel
     * @return:
     * @author: 赵进华
     * @time: 2020/3/28 22:00
     */
    @ApiOperation(value = "异步导出预收Excel", httpMethod = "POST", response = Object.class)
    @RequestMapping(value = "/exportPrepayExcelAsync", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseResult exportPrepayExcelAsync(@RequestBody SubAccountEntity vo) throws Exception {
        return iPrePayService.exportPrepayExcelAsync(vo);
    }
}
