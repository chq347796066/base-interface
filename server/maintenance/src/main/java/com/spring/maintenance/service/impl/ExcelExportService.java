package com.spring.maintenance.service.impl;

import com.spring.base.entity.baseinfo.ExcelExportEntity;
import com.spring.base.entity.buiness.ComplaintProposalEntity;
import com.spring.base.entity.buiness.ReportingRepairEntity;
import com.spring.base.vo.FastFileVO;
import com.spring.common.constants.Constants;
import com.spring.common.fastdfs.CommonFastDfsUtil;
import com.spring.common.util.excel.ExportExcel2;
import com.spring.common.util.excel.ExportExcel3;
import com.spring.common.util.file.FileUtils;
import com.spring.common.util.id.UUIDFactory;
import com.spring.common.util.string.StringHelper;
import com.spring.maintenance.dao.ExcelExportDao;
import lombok.extern.slf4j.Slf4j;
import org.nutz.aop.interceptor.async.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Desc: 导出
 * @Author:邓磊
 * @UpdateDate:2020/4/20 16:24
 * @return: 返回
 */
@Slf4j
@Service
public class ExcelExportService {

    @Autowired
    private ExcelExportDao sqlDao;

    // excel路径
    @Value("${application.data}")
    String applicationData;

    @Value("${application.fileserver}")
    private String fileServer;

    @Autowired
    private CommonFastDfsUtil commonFastDfsUtil;

    /**
     * @description:导出投诉建议信息
     * @return:
     * @author: 邓磊
     * @time: 2020/3/28 21:49
     */
    @Async
    public void exportComplaintProposal(ComplaintProposalEntity vo) throws Exception {
        try {
            // 组装where sql
            StringBuffer where = new StringBuffer();
            where.append(Constants.WHERE);
            if (vo != null) {
                if (vo.getProposalStatus() != null && !StringHelper.isEmpty(vo.getProposalStatus())) {
                    where.append(" and proposal_status='"+ vo.getProposalStatus()+"'");
                }
                if (vo.getComplaintAddress()!= null && !StringHelper.isEmpty(vo.getComplaintAddress())) {
                    where.append(" and complaint_address='"+ vo.getComplaintAddress()+"'");
                }
                if (vo.getStartDate() != null && !StringHelper.isEmpty(vo.getStartDate()) && vo.getEndDate() != null &&StringHelper.isEmpty(vo.getEndDate()) ) {
                    where.append(" and start_date='"+ vo.getStartDate()+"'");
                    where.append(" and end_date='"+ vo.getEndDate()+"'");
                }
                if (vo.getSubmitUser()!= null && !StringHelper.isEmpty(vo.getSubmitUser())) {
                    where.append(" and submit_user='"+ vo.getSubmitUser()+"'");
                }
                if (vo.getMobile()!= null && !StringHelper.isEmpty(vo.getMobile())) {
                    where.append(" and mobile='"+ vo.getMobile()+"'");
                }
            }
            // 没有查询条件,默认查询20000条数据
            if (where.toString().equals(Constants.WHERE)) {
                where.append(" order by create_date desc limit 20000 ");
            } else {
                where.append(" order by create_date desc");
            }
            // 根据类型查询导出的sql
            String sql ="SELECT\n" +
                    "\tIFNULL(complaint_address, '') AS 联系地址,\n" +
                    "\tIFNULL(complaint_describe, '') AS 投诉内容,\n" +
                    "\tIFNULL(submit_user, '') AS 联系人,\n" +
                    "\tIFNULL(mobile, '') AS 联系电话,\n" +

                    "\tIFNULL(proposal_status, '') AS 状态,\n" +
                    "\tIFNULL(start_date, '') AS 报修建议时间,\n" +
                    "\tIFNULL(end_date '') AS 完成时间\n" +
                    "FROM\n" +
                    "\tb_complaint_proposal\n"+where.toString();
            String title = "投诉建议处理";
            log.info("exportCustomer where:"+where.toString());
            // 执行sql语句得到记录集
            List<LinkedHashMap<String, Object>> map = sqlDao.execSqlGetRecordSet(sql);
            log.info("exportComplaintProposal get data where:"+where.toString()+",size:"+map.size());
            if (map.size() > 0) {
                // 标题
                List<String> titleList = new ArrayList<>();
                titleList.add("联系地址");
                titleList.add("投诉内容");
                titleList.add("联系人");
                titleList.add("联系电话");
                titleList.add("状态");
                titleList.add("报修建议时间");
                titleList.add("完成时间");
                String info1="exportComplaintProposal";
                String info2="投诉建议数据";
                saveExcelExportEntity(titleList,  map,info1,info2) ;
            }
        } catch (Exception e) {
            log.error("导出错误", e);
        }
    }

    /**
     * @Desc: 导出报事报修
     * @param vo
     * @Author:邓磊
     * @UpdateDate:2020/4/24 19:39
     * @return: 返回
     */
    @Async
    public void asynExportReportingRepair(ReportingRepairEntity vo) throws Exception {
        try {
            // 组装where sql
            StringBuffer where = new StringBuffer();
            where.append(Constants.WHERE);
            if (vo != null) {
                if (vo.getRepairType() != null && !StringHelper.isEmpty(vo.getRepairType())) {
                    where.append(" and repair_type='"+ vo.getRepairType()+"'");
                }
                if (vo.getReportingStatus()!= null && !StringHelper.isEmpty(vo.getReportingStatus())) {
                    where.append(" and reporting_status='"+ vo.getReportingStatus()+"'");
                }
                if (vo.getStartDate() != null && !StringHelper.isEmpty(vo.getStartDate()) && vo.getEndDate() != null &&StringHelper.isEmpty(vo.getEndDate()) ) {
                    where.append(" and start_date='"+ vo.getStartDate()+"'");
                    where.append(" and end_date='"+ vo.getEndDate()+"'");
                }
                if (vo.getSubmitUser()!= null && !StringHelper.isEmpty(vo.getSubmitUser())) {
                    where.append(" and submit_user='"+ vo.getSubmitUser()+"'");
                }
                if (vo.getUserName()!= null && !StringHelper.isEmpty(vo.getUserName())) {
                    where.append(" and user_name='"+ vo.getUserName()+"'");
                }
                if (vo.getMobile()!= null && !StringHelper.isEmpty(vo.getMobile())) {
                    where.append(" and mobile='"+ vo.getMobile()+"'");
                }
            }
            // 没有查询条件,默认查询20000条数据
            if (where.toString().equals(Constants.WHERE)) {
                where.append(" order by create_date desc limit 20000 ");
            } else {
                where.append(" order by create_date desc");
            }
            // 根据类型查询导出的sql
            String sql ="SELECT\n" +
                    "\tIFNULL(complaint_address, '') AS 报修地址,\n" +
                    "\tIFNULL(reporting_describe, '') AS 报修内容,\n" +
                    "\tIFNULL(user_name, '') AS 报修人,\n" +
                    "\tIFNULL(mobile, '') AS 联系电话,\n" +

                    "\tIFNULL(repair_type, '') AS 报修类型,\n" +
                    "\tIFNULL(reporting_status, '') AS 状态,\n" +
                    "\tIFNULL(outlay '') AS 维修金额\n" +

                    "\tIFNULL(start_date, '') AS 报修时间,\n" +
                    "\tIFNULL(dispatch_date, '') AS 派工时间,\n" +
                    "\tIFNULL(end_date, '') AS 完成时间,\n" +
                    "FROM\n" +
                    "\tb_reporting_repair\n"+where.toString();
            String title = "报事报修处理";
            log.info("exportCustomer where:"+where.toString());
            // 执行sql语句得到记录集
            List<LinkedHashMap<String, Object>> map = sqlDao.execSqlGetRecordSet(sql);
            log.info("asynExportReportingRepair get data where:"+where.toString()+",size:"+map.size());
            if (map.size() > 0) {
                // 标题
                List<String> titleList = new ArrayList<>();
                titleList.add("报修地址");
                titleList.add("报修内容");
                titleList.add("报修人");
                titleList.add("联系电话");
                titleList.add("报修类型");
                titleList.add("状态");
                titleList.add("维修金额");
                titleList.add("报修时间");
                titleList.add("派工时间");
                titleList.add("完成时间");
                String info1="exportReportingRepair";
                String info2="报事报修数据";
                saveExcelExportEntity(titleList,  map,info1,info2) ;
            }
        } catch (Exception e) {
            log.error("导出错误", e);
        }
    }


    //保存到导出文件列表
    private void saveExcelExportEntity(List<String> titleList, List<LinkedHashMap<String, Object>> map,String info1,String info2) throws Exception {
        // 标题 list转array
        String[] heads = titleList.toArray(new String[titleList.size()]);
        List<Object[]> list = ExportExcel2.getDataList(titleList, map);
        // 导出Excel到本地
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        //文件名
        String fileName = sdf.format(dt) + ".xls";
        //导出路径
        //String filePath = applicationData + fileName;
        String filePath = "d:\\" + fileName;
        log.info(info1+"filePath:" + filePath);
        ExportExcel3 ex = new ExportExcel3(info2, heads, list);
        ExportExcel3.exportExcelMy(map,info2, filePath);
        //判断文件是否存在
        File file = new File(filePath);
        if (file.exists()) {
            log.info(info1+"file exist filePath:" + filePath);
            //文件转字节数组
            byte[] content = FileUtils.fileConvertToByteArray(file);
            //上传到文件服务器
            FastFileVO fastDfsFile = new FastFileVO(content, fileName, file.length());
            String path = commonFastDfsUtil.uploadFile((MultipartFile) fastDfsFile);
            String uploadFileName = path.substring(path.lastIndexOf("/") + 1);
            String fallPath = fileServer + "/" + path;
            log.info(info1+"upload fastdfs  fallPath:" + fallPath + ",uploadFileName:" + uploadFileName);
            //保存到导出文件列表
            ExcelExportEntity excelExportEntity = new ExcelExportEntity();
            excelExportEntity.setId(UUIDFactory.createId());
            excelExportEntity.setUserId("admin");
            excelExportEntity.setExcelType("ProjectCreate");
            excelExportEntity.setFilePath(fallPath);
            excelExportEntity.setFileName(uploadFileName);
            excelExportEntity.setStatus(1);
            excelExportEntity.setCreateUser("admin");
            excelExportEntity.setCreateDate(new Date());
            sqlDao.insert(excelExportEntity);
            log.info(info1+"save data  fallPath:" + fallPath);
        }
    }
}
