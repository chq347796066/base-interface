package com.spring.baseinfo.service.impl;

import com.spring.base.entity.baseinfo.*;
import com.spring.base.vo.FastFileVO;
import com.spring.baseinfo.dao.ISqlDao;
import com.spring.common.constants.Constants;
import com.spring.common.fastdfs.CommonFastDfsUtil;
import com.spring.common.request.RequestUtils;
import com.spring.common.util.excel.ExportExcel2;
import com.spring.common.util.excel.ExportExcel3;
import com.spring.common.util.file.FileUtils;
import com.spring.common.util.id.UUIDFactory;
import com.spring.common.util.string.StringHelper;
import lombok.extern.slf4j.Slf4j;
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
     * @description:导出公司信息
     * @return:
     * @author: 赵进华
     * @time: 2020/3/28 21:49
     */
    @Async
    public void exportCompany(CompanyEntity vo) throws Exception {
        try {
            // 组装where sql
            StringBuffer where = new StringBuffer();
            where.append(Constants.WHERE);
            if (vo != null) {
                if (vo.getCompanyName() != null && !StringHelper.isEmpty(vo.getCompanyName())) {
                    where.append(" and company_name like '%" + vo.getCompanyName() + "%' ");
                }
                if (vo.getLegalName() != null && !StringHelper.isEmpty(vo.getLegalName())) {
                    where.append(" and legal_name like '%" + vo.getLegalName() + "%' ");
                }

            }
            // 没有查询条件,默认查询20000条数据
            if (where.toString().equals(Constants.WHERE)) {
                where.append(" order by create_date desc limit 20000 ");
            } else {
                where.append(" order by create_date desc");
            }
            // 根据类型查询导出的sql
            String sql = "select ifnull(company_code,'') as 公司编号,ifnull(company_name,'') as 公司名称,ifnull(mobile,'') as 公司电话,\n" +
                    "ifnull(address,'') as 公司地址,ifnull(legal_name,'') as 法人姓名,ifnull(legal_mobile,'') as 法人电话\n" +
                    "from b_company" + where.toString();
            String title = "公司管理";
            log.info("exportCompany where:"+where.toString());
            // 执行sql语句得到记录集
            List<LinkedHashMap<String, Object>> map = sqlDao.execSqlGetRecordSet(sql);
            log.info("exportCompany get data where:"+where.toString()+",size:"+map.size());
            String excelType=vo.getExcelType();
            if (map.size() > 0) {
                // 标题
                List<String> titleList = new ArrayList<>();
                titleList.add("公司编号");
                titleList.add("公司名称");
                titleList.add("公司电话");
                titleList.add("公司地址");
                titleList.add("法人姓名");
                titleList.add("法人电话");
                String info1="exportCompany";
                String info2="公司数据";
                saveExcelExportEntity(titleList,map,info1,info2,excelType) ;
            }
        } catch (Exception e) {
            log.error("导出错误", e);
        }
    }

    /**
     * @description:导出小区信息
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:49
     */
    @Async
    public void exportCommunity(CommunityEntity vo) throws Exception {
        try {
            // 组装where sql
            StringBuffer where = new StringBuffer();
            where.append(Constants.WHERE);
            if (vo != null) {
                if (vo.getCompanyName() != null && !StringHelper.isEmpty(vo.getCompanyName())) {
                    where.append(" and main.status = '1' and co.company_name like '%" + vo.getCompanyName() + "%' ");
                }
                if (vo.getCommunityName() != null && !StringHelper.isEmpty(vo.getCommunityName())) {
                    where.append(" and main.community_name like '%" + vo.getCommunityName() + "%' ");
                }

            }
            // 没有查询条件,默认查询20000条数据
            if (where.toString().equals(Constants.WHERE)) {
                where.append(" order by main.create_date desc limit 20000 ");
            } else {
                where.append(" order by main.create_date desc");
            }
            // 根据类型查询导出的sql
            String sql ="SELECT\n" +
                    "\t ifnull (main.community_code,'') as 小区编号,\n" +
                    "\t ifnull(main.community_name,'') as 小区名称,\n" +
                    "\t ifnull(main.community_address,'') as 小区地址,\n" +
                    "\t ifnull(main.community_mobile,'') as 小区电话,\n" +
                    "\t ifnull(main.all_households,'') as 总户数,\n" +
                    "\t ifnull(co.company_name,'')as 所属公司\n" +
                    "FROM\n" +
                    "\tb_community AS main\n" +
                    "LEFT JOIN b_company co ON main.company_id = co.id"+where.toString();

            String title = "小区管理";
            log.info("exportCommunity where:"+where.toString());
            // 执行sql语句得到记录集
            List<LinkedHashMap<String, Object>> map = sqlDao.execSqlGetRecordSet(sql);
            log.info("exportCommunity get data where:"+where.toString()+",size:"+map.size());
            if (map.size() > 0) {
                // 标题
                List<String> titleList = new ArrayList<>();
                titleList.add("小区编号");
                titleList.add("小区名称");
                titleList.add("所属公司");
                titleList.add("小区电话");
                titleList.add("小区地址");
                titleList.add("总户数");
                String info1="exportCommunity";
                String info2="小区数据";
                saveExcelExportEntity(titleList,map,info1,info2,vo.getExcelType()) ;
            }
        } catch (Exception e) {
            log.error("导出错误", e);
        }
    }

    /**
     * @description:导出员工信息
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:49
     */
    @Async
    public void exportUser(UserEntity vo) throws Exception {
//        try {
//            // 组装where sql
//            StringBuffer where = new StringBuffer();
//            where.append(Constants.WHERE);
//            if (vo != null) {
//                if (vo.getCommunityName() != null && !StringHelper.isEmpty(vo.getCommunityName())) {
//                    where.append(" and  comm.community_name ='"+ vo.getCommunityName()+"'");
//                }
//                if (vo.getUserName() != null && !StringHelper.isEmpty(vo.getUserName())) {
//                    where.append(" and main.user_name like '%" + vo.getUserName() + "%' ");
//                }
//                if (vo.getCompanyName() != null && !StringHelper.isEmpty(vo.getCompanyName())) {
//                    where.append(" and om.company_name ='" + vo.getCompanyName()+"'");
//                }
//            }
//            // 没有查询条件,默认查询20000条数据
//            if (where.toString().equals(Constants.WHERE)) {
//                where.append(" order by main.create_date desc limit 20000 ");
//            } else {
//                where.append(" order by main.create_date desc");
//            }
//            // 根据类型查询导出的sql
//            String sql ="SELECT\n" +
//                    "\t ifnull(main.user_name,'') AS 员工名字,\n" +
//                    "\t ifnull(main.mobile,'') AS 员工电话,\n" +
//                    "\t ifnull(IF (main.sex = 1, \"男\", \"女\"),\t'') AS 员工性别,\n" +
//                    "\t ifnull(main.id_card,'') AS 身份证号码,\n" +
//                    "\t ifnull(role.`status`,'') AS 员工状态,\n" +
//                    "\t ifnull(com.company_name,'') AS 公司名称,\n" +
//                    "\t ifnull(comm.community_name,'') AS 小区名称\n" +
//                    "FROM\n" +
//                    "\tb_user AS main\n" +
//                    "LEFT JOIN b_company AS com ON main.company_id = com.id\n" +
//                    "LEFT JOIN b_community AS comm ON main.community_id = comm.id\n" +
//                    "LEFT JOIN b_user_role AS role ON role.user_id = main.id"+where.toString();
//
//            String title = "员工管理";
//            log.info("exportCommunity where:"+where.toString());
//            // 执行sql语句得到记录集
//            List<LinkedHashMap<String, Object>> map = sqlDao.execSqlGetRecordSet(sql);
//            log.info("exportCommunity get data where:"+where.toString()+",size:"+map.size());
//            if (map.size() > 0) {
//                // 标题
//                List<String> titleList = new ArrayList<>();
//                titleList.add("员工名字");
//                titleList.add("员工电话");
//                titleList.add("员工性别");
//                titleList.add("身份证号码");
//                titleList.add("员工状态");
//                titleList.add("公司名称");
//                titleList.add("小区名称");
//                String info1="exportUser";
//                String info2="员工数据";
//                saveExcelExportEntity(titleList,  map,info1,info2) ;
//            }
//        } catch (Exception e) {
//            log.error("导出错误", e);
//        }
    }

    /**
     * @description:导出房产信息
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:49
     */
    @Async
    public void exportHouse(HouseEntity vo) throws Exception {
        try {
            // 组装where sql
            StringBuffer where = new StringBuffer();
            where.append(Constants.WHERE);
            if (vo != null) {
                if (vo.getCommunityName() != null && !StringHelper.isEmpty(vo.getCommunityName())) {
                    where.append(" and  c.community_name ='"+ vo.getCommunityName()+"'");
                }
                if (vo.getBuildName() != null && !StringHelper.isEmpty(vo.getBuildName())) {
                    where.append(" and d.build_name='"+ vo.getBuildName()+"'");
                }
                if (vo.getHouseCode() != null && !StringHelper.isEmpty(vo.getHouseCode())) {
                    where.append(" and main.house_code ='" + vo.getHouseCode()+"'");
                }
            }
            // 没有查询条件,默认查询20000条数据
            if (where.toString().equals(Constants.WHERE)) {
                where.append(" order by main.create_date desc limit 20000 ");
            } else {
                where.append(" order by main.create_date desc");
            }
            // 根据类型查询导出的sql
            String sql ="SELECT\n" +
                    "\tifnull(main.floor, '') AS 楼层,\n" +
                    "\tifnull(main.house_code, '') AS 房屋编码,\n" +
                    "\tifnull(main.house_type, '') AS 房屋类型,\n" +
                    "\tifnull(main.house_status, '') AS 房屋状态,\n" +
                    "\tifnull(main.architecture_area, '') AS 建筑面积,\n" +
                    "\tifnull(main.owner_name, '') AS 业主名字,\n" +
                    "\tifnull(c.community_name, '') AS 小区名称,\n" +
                    "\tifnull(d.build_name, '') AS 楼栋名称,\n" +
                    "\tifnull(l.cell_name, '') AS 单元\n" +
                    "FROM\n" +
                    "\tb_house AS main\n" +
                    "LEFT JOIN b_community c ON main.community_id = c.id\n" +
                    "LEFT JOIN b_build d ON main.build_id = d.id\n" +
                    "LEFT JOIN b_cell l ON main.cell_id = l.id\n" +
                    "LEFT JOIN b_customer cu ON main.customer_id = cu.id"+where.toString();
            String title = "房产管理";
            log.info("exportHouse where:"+where.toString());
            // 执行sql语句得到记录集
            List<LinkedHashMap<String, Object>> map = sqlDao.execSqlGetRecordSet(sql);
            log.info("exportCommunity get data where:"+where.toString()+",size:"+map.size());
            if (map.size() > 0) {
                // 标题
                List<String> titleList = new ArrayList<>();
                titleList.add("楼层");
                titleList.add("房屋编码");
                titleList.add("房屋类型");
                titleList.add("房屋状态");
                titleList.add("建筑面积");
                titleList.add("业主名字");
                titleList.add("小区名称");
                titleList.add("楼栋名称");
                titleList.add("单元");
                String info1="exportHouse";
                String info2="房产数据";
                saveExcelExportEntity(titleList,  map,info1,info2,vo.getExcelType()) ;
            }
        } catch (Exception e) {
            log.error("导出错误", e);
        }
    }

    /**
     * @description:导出车位信息
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:49
     */
    @Async
    public void exportCar(CarEntity vo) throws Exception {
        try {
            // 组装where sql
            StringBuffer where = new StringBuffer();
            where.append(Constants.WHERE);
            if (vo != null) {
                if (vo.getCommunityName() != null && !StringHelper.isEmpty(vo.getCarCode())) {
                    where.append(" and  co.community_name ='"+ vo.getCommunityName()+"'");
                }
                if (vo.getCarCode() != null && !StringHelper.isEmpty(vo.getCarCode())) {
                    where.append(" and main.car_code='"+ vo.getCarCode()+"'");
                }
            }
            // 没有查询条件,默认查询20000条数据
            if (where.toString().equals(Constants.WHERE)) {
                where.append(" order by main.create_date desc limit 20000 ");
            } else {
                where.append(" order by main.create_date desc");
            }
            // 根据类型查询导出的sql
            String sql ="\tselect\n" +
                    "\t\tifnull(main.car_code,'') as 车位编码,\n" +
                    "\t\tifnull(main.car_no,'') as 车牌号,\n" +
                    "\t\tifnull(main.area,'') as 车位面积,\n" +
                    "\t\tifnull(main.car_type,'') as 车位类型,\n" +
                    "\t\tifnull(main.car_status,'') as 车位状态,\n" +
                    "\t\tifnull(co.community_name,'') as 小区名称,\n" +
                    "\t\tifnull(cu.customer_name,'') as 业主名字,\n" +
                    "\t\tifnull(cu.phone,'') as 业主电话\n" +
                    "\t\tfrom b_car as main\n" +
                    "\t\tleft join b_community co on main.community_id = co.id\n" +
                    "\t\tleft join b_customer cu  on main.customer_id = cu.id"+where.toString();
            String title = "车位管理";
            log.info("exportCar where:"+where.toString());
            // 执行sql语句得到记录集
            List<LinkedHashMap<String, Object>> map = sqlDao.execSqlGetRecordSet(sql);
            log.info("exportCommunity get data where:"+where.toString()+",size:"+map.size());
            if (map.size() > 0) {
                // 标题
                List<String> titleList = new ArrayList<>();
                titleList.add("车位编码");
                titleList.add("车牌号");
                titleList.add("车位面积");
                titleList.add("车位类型");
                titleList.add("车位状态");
                titleList.add("小区名称");
                titleList.add("业主名字");
                titleList.add("业主电话");
                String info1="exportCar";
                String info2="车位数据";
                saveExcelExportEntity(titleList,map,info1,info2,vo.getExcelType()) ;
            }
        } catch (Exception e) {
            log.error("导出错误", e);
        }
    }

    /**
     * @description:导出客户信息
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:49
     */
    @Async
    public void exportCustomer(CustomerEntity vo) throws Exception {
        try {
            // 组装where sql
            StringBuffer where = new StringBuffer();
            where.append(Constants.WHERE);
            if (vo != null) {
                if (vo.getCommunityName() != null && !StringHelper.isEmpty(vo.getCommunityName())) {
                    where.append(" and  co.community_name ='"+ vo.getCommunityName()+"'");
                }
                if (vo.getCustomerName() != null && !StringHelper.isEmpty(vo.getCustomerName())) {
                    where.append(" and main.customer_name like '%" + vo.getCustomerName() + "%' ");
                }
                if (vo.getCustomerType() != null && !StringHelper.isEmpty(vo.getCustomerType())) {
                    where.append(" and main.customer_type='"+ vo.getCustomerType()+"'");
                }
            }
            // 没有查询条件,默认查询20000条数据
            if (where.toString().equals(Constants.WHERE)) {
                where.append(" order by main.create_date desc limit 20000 ");
            } else {
                where.append(" order by main.create_date desc");
            }
            // 根据类型查询导出的sql
            String sql ="SELECT\n" +
                    "\tIFNULL(main.customer_type, '') AS 客户类别,\n" +
                    "\tIFNULL(main.customer_name, '') AS 客户名称,\n" +
                    "\tIFNULL(main.sex, '') AS 客户性别,\n" +
                    "\tIFNULL(main.certificates_type, '') AS 证件类型,\n" +
                    "\tIFNULL(\n" +
                    "\t\tmain.certificates_number,\n" +
                    "\t\t''\n" +
                    "\t) AS 证件号码,\n" +
                    "\tIFNULL(main.phone, '') AS 客户电话,\n" +
                    "\tIFNULL(main.community_address, '') AS 客户地址,\n" +
                    "\tIFNULL(co.community_name, '') AS 小区名称\n" +
                    "FROM\n" +
                    "\tb_customer AS main\n" +
                    "LEFT JOIN b_community AS co ON main.community_id = co.id"+where.toString();
            String title = "客户管理";
            log.info("exportCustomer where:"+where.toString());
            // 执行sql语句得到记录集
            List<LinkedHashMap<String, Object>> map = sqlDao.execSqlGetRecordSet(sql);
            log.info("exportCommunity get data where:"+where.toString()+",size:"+map.size());
            if (map.size() > 0) {
                // 标题
                List<String> titleList = new ArrayList<>();
                titleList.add("客户类别");
                titleList.add("客户名称");
                titleList.add("客户性别");
                titleList.add("证件类型");
                titleList.add("证件号码");
                titleList.add("客户电话");
                titleList.add("客户地址");
                titleList.add("小区名称");
                String info1="exportCustomer";
                String info2="客户数据";
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
