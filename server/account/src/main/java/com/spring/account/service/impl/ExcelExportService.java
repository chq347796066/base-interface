package com.spring.account.service.impl;

import com.spring.account.dao.ISqlDao;
import com.spring.base.entity.account.SubAccountEntity;
import com.spring.base.entity.baseinfo.*;
import com.spring.base.vo.FastFileVO;
import com.spring.common.constants.Constants;
import com.spring.common.fastdfs.CommonFastDfsUtil;
import com.spring.common.request.RequestUtils;
import com.spring.common.util.excel.ExportExcel2;
import com.spring.common.util.excel.ExportExcel3;
import com.spring.common.util.file.FileUtils;
import com.spring.common.util.id.UUIDFactory;
import com.spring.common.util.string.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @description:excel导出服务
 * @author: 赵进华
 * @time: 2020/3/28 21:47
 */
@Slf4j
@Service
public class ExcelExportService {

    @Autowired
    private ISqlDao sqlDao;

    // excel路径
    @Value("${application.data}")
    String applicationData;

    @Value("${application.fileserver}")
    private String fileServer;

    @Autowired
    private CommonFastDfsUtil commonFastDfsUtil;



    /**
     * @description:导出小区信息
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:49
     */
    @Async
    public void exportPrepay(SubAccountEntity vo) throws Exception {
        try {
            // 组装where sql
            StringBuffer where = new StringBuffer();
            where.append(Constants.WHERE);
            //小区id
            if (StringUtils.isNotEmpty(vo.getOpenBranch())) {
                where.append(" and main.open_branch = '" + vo.getOpenBranch() + "' ");
            }
            //楼栋id
            if (StringUtils.isNotEmpty(vo.getBuildId())) {
                where.append(" and main.build_id = '" + vo.getBuildId() + "' ");
            }
                //房屋编码
            if (StringUtils.isNotEmpty(vo.getHouseCode())) {
                where.append(" and co.house_code like '%" + vo.getHouseCode() + "%' ");
            }

            // 没有查询条件,默认查询20000条数据
            if (where.toString().equals(Constants.WHERE)) {
                where.append(" order by main.create_date desc limit 20000 ");
            } else {
                where.append(" order by main.create_date desc");
            }
            // 根据类型查询导出的sql
            String sql ="SELECT\n" +
                    "\t ifnull(main.community_name,'') as 小区名称,\n" +
                    "\t ifnull(main.build_name,'') as 楼栋,\n" +
                    "\t ifnull(main.cell_name,'') as 单元,\n" +
                    "\t ifnull(main.house_code,'') as 房屋编码,\n" +
                    "\t ifnull(main.pname,'')as 客户名称,\n" +
                    "\t ifnull(main.current_balance,'')as 账户余额\n" +
                    "FROM\n" +
                    "\t a_sub_account AS main \n" + where.toString();

            String title = "小区管理";
            log.info("exportPrepay where:"+where.toString());
            // 执行sql语句得到记录集
            List<LinkedHashMap<String, Object>> map = sqlDao.execSqlGetRecordSet(sql);
            log.info("exportPrepay get data where:"+where.toString()+",size:"+map.size());
            if (map.size() > 0) {
                // 标题
                List<String> titleList = new ArrayList<>();
                titleList.add("小区名称");
                titleList.add("楼栋");
                titleList.add("单元");
                titleList.add("房屋编码");
                titleList.add("客户名称");
                titleList.add("账户余额");
                String info1="exportPrepay";
                String info2="小区数据";
                saveExcelExportEntity(titleList,map,info1,info2,vo.getExcelType()) ;
            }
        } catch (Exception e) {
            log.error("导出错误", e);
        }
    }



    //保存到导出文件列表
    private void saveExcelExportEntity(List<String> titleList, List<LinkedHashMap<String, Object>> map,String info1,String info2,String excelType) throws Exception {
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
            FastFileVO fastFile = new FastFileVO(content, fileName, file.length());
            String path = commonFastDfsUtil.uploadFile((MultipartFile) fastFile);
            String uploadFileName = path.substring(path.lastIndexOf("/") + 1);
            String fallPath = fileServer + "/" + path;
            log.info(info1+"upload fastdfs  fallPath:" + fallPath + ",uploadFileName:" + uploadFileName);
            //保存到导出文件列表
            ExcelExportEntity excelExportEntity = new ExcelExportEntity();
            excelExportEntity.setId(UUIDFactory.createId());
            excelExportEntity.setUserId(RequestUtils.getUserId());
            excelExportEntity.setExcelType(excelType);
            excelExportEntity.setFilePath(fallPath);
            excelExportEntity.setFileName(uploadFileName);
            excelExportEntity.setStatus(1);
            excelExportEntity.setCreateUser(RequestUtils.getUserId());
            excelExportEntity.setCreateDate(new Date());
            sqlDao.insert(excelExportEntity);
            log.info(info1+"save data  fallPath:" + fallPath);
        }
    }
}
