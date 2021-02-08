package com.spring.baseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.base.dao.BaseDao;
import com.spring.base.entity.baseinfo.*;
import com.spring.base.service.impl.BaseServiceImpl;
import com.spring.baseinfo.dao.IExcelExportDao;
import com.spring.baseinfo.dao.ISqlDao;
import com.spring.baseinfo.service.IExcelService;
import com.spring.common.constants.Constants;
import com.spring.common.constants.MessageCode;
import com.spring.common.request.RequestUtils;
import com.spring.common.response.ApiResponseResult;
import com.spring.common.util.excel.ExportExcel2;
import com.spring.common.util.string.StringHelper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @description:excel实现类
 * @author: 赵进华
 * @time: 2020/4/2 13:27
 */
@Slf4j
@Service("excelService")
public class ExcelServiceImpl extends BaseServiceImpl<ExcelExportEntity, String> implements IExcelService {
    @Autowired
    private ISqlDao sqlDao;

    @Override
    public BaseDao getBaseMapper() {
        return sqlDao;
    }

    @Autowired
    private ExcelExportService excelExportService;

    @Autowired
    private IExcelExportDao excelExportDao;

    /**
     * @description:同步导出excel
     * @return:
     * @author: 赵进华
     * @time: 2020/4/2 13:40
     */
    @Override
    public void exportExcelData(HttpServletResponse response, CompanyEntity vo) throws Exception {
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
        // 没有查询条件,默认查询1000条数据
        if (where.toString().equals(Constants.WHERE)) {
            where.append(" order by create_date desc limit 1000 ");
        } else {
            where.append(" order by create_date desc");
        }
        // 根据类型查询导出的sql
        String sql = "select ifnull(company_code,'') as 公司编号,ifnull(company_name,'') as 公司名称,ifnull(mobile,'') as 公司电话,\n" +
                "ifnull(address,'') as 公司地址,ifnull(legal_name,'') as 法人姓名,ifnull(legal_mobile,'') as 法人电话,\n" +
                " from b_company" + where.toString();
        String title = "公司管理";

        // 执行sql语句得到记录集
        List<LinkedHashMap<String, Object>> map = sqlDao.execSqlGetRecordSet(sql);

        if (map.size() > 0) {
            // 标题
            List<String> titleList = new ArrayList<>();
            titleList.add("公司编号");
            titleList.add("公司名称");
            titleList.add("公司电话");
            titleList.add("公司地址");
            titleList.add("法人姓名");
            titleList.add("法人电话");
            // 标题 list转array
            String[] heads = titleList.toArray(new String[titleList.size()]);
            List<Object[]> list = ExportExcel2.getDataList(titleList,  map);
            // 导出
            ExportExcel2 ex = new ExportExcel2(title, heads, list, response);
            ex.exportData();
        }
    }

    /**
     * @description:异步导出excel
     * @return:
     * @author: 赵进华
     * @time: 2020/3/28 21:57
     */
    @Override
    public ApiResponseResult exportExcelAsync(CompanyEntity vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        log.info("exportExcelAsync userId:" + RequestUtils.getUserId() + ",vo:" + vo.toString());
        excelExportService.exportCompany(vo);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("异步导出excel中,请耐心等待！");
        return result;
    }

    @Override
    public ApiResponseResult exportCommunityExcelAsync(CommunityEntity vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        log.info("exportExcelAsync userId:" + RequestUtils.getUserId() + ",vo:" + vo.toString());
        excelExportService.exportCommunity(vo);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("异步导出excel中,请耐心等待！");
        return result;
    }

    @Override
    public ApiResponseResult exportUserExcelAsync(UserEntity vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        log.info("exportExcelAsync userId:" + RequestUtils.getUserId() + ",vo:" + vo.toString());
        excelExportService.exportUser(vo);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("异步导出excel中,请耐心等待！");
        return result;
    }

    @Override
    public ApiResponseResult exportHouseExcelAsync(HouseEntity vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        log.info("exportExcelAsync userId:" + RequestUtils.getUserId() + ",vo:" + vo.toString());
        excelExportService.exportHouse(vo);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("异步导出excel中,请耐心等待！");
        return result;
    }

    @Override
    public ApiResponseResult exportCarExcelAsync(CarEntity vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        log.info("exportExcelAsync userId:" + RequestUtils.getUserId() + ",vo:" + vo.toString());
        excelExportService.exportCar(vo);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("异步导出excel中,请耐心等待！");
        return result;
    }

    @Override
    public ApiResponseResult exportCustomerExcelAsync(CustomerEntity vo) throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        log.info("exportExcelAsync userId:" + RequestUtils.getUserId() + ",vo:" + vo.toString());
        excelExportService.exportCustomer(vo);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg("异步导出excel中,请耐心等待！");
        return result;
    }

    /**
     * @description:查询excel文件导出列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/29 10:09
     */
    @Override
    public ApiResponseResult queryExcelFile() throws Exception {
        ApiResponseResult result = new ApiResponseResult();
        //根据用户名和类型查询用户导出列表
        QueryWrapper<ExcelExportEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", "admin").eq("excel_type", "ProjectCreate");
        List<ExcelExportEntity> excelList = sqlDao.selectList(queryWrapper);
        result.setCode(MessageCode.SUCCESS);
        result.setMsg(MessageCode.SUCCESS_TEXT);
        result.setData(excelList);
        return result;
    }

    /**
     * @description:导入数据
     * @return:
     * @author: 赵进华
     * @time: 2020/4/2 17:46
     */
//    @Override
//    public ApiResponseResult importExcel(HttpServletRequest req, MultipartFile file) throws Exception {
//        List<List<String[]>> list = ExceUtils.importExcel(req, file);// excelExportService.importExcel(req, file);
//        List<CompanyEntity> importList = new ArrayList<>();
//        try {
//            for (List<String[]> listRow : list) {
//                int i = 0;
//                for (String[] strings : listRow) {
//                    // 过滤掉表格第一行标题
//                    if (i > 0) {
//                        // 构建对象
//                        CompanyEntity entity = new CompanyEntity();
//                        entity.setId(UUIDFactory.createId());
//                        entity.setProNo("P001");
//                        // 项目名称
//                        entity.setProName(strings[0]);
//                        // 项目分类
//                        entity.setClassify(strings[1]);
//                        // 板块
//                        entity.setPlate(strings[2]);
//                        // 项目来源
//                        entity.setSource(strings[3]);
//                        // 来源单号
//                        entity.setSourceNo(strings[4]);
//                        // 项目组成员
//                        entity.setMemberNo(strings[5]);
//                        // 项目管理员
//                        entity.setKeeperNo(strings[6]);
//                        // 项目负责单位
//                        entity.setCompany(strings[7]);
//                        // 项目负责部门
//                        entity.setDepartment(strings[8]);
//                        // 项目负责人
//                        entity.setCharger(strings[9]);
//                        entity.setStatus(1);
//                        entity.setCreateUser(RequestUtils.getUserId());
//                        entity.setCreateDate(new Date());
//                        entity.setModifyUser(RequestUtils.getUserId());
//                        entity.setModifyDate(new Date());
//                        // 对象加入列表
//                        importList.add(entity);
//                    }
//                    i = i + 1;
//                }
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return createFailResult("系统错误，请联系管理员");
//        }
//
//        if (importList.size() > 0) {
//            try {
//                // 批量插入数据
//                sqlDao.addListProject(importList);
//                return createSuccessResult(importList);
//            } catch (Exception e) {
//                log.error("插入数据库失败", e);
//            }
//            return createFailResult("导入失败");
//        } else {
//            return createSuccessResult(0);
//        }
//    }

    /**
     * @description:查询excel文件导出列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/29 10:09
     */
    @Override
    public ApiResponseResult queryExcelFile(String userId, String excelType) throws Exception {
        return createSuccessResult(excelExportDao.queryExcelFile(userId,excelType));
    }

}
