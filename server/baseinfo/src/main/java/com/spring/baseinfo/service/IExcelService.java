package com.spring.baseinfo.service;

import com.spring.base.entity.baseinfo.*;
import com.spring.common.response.ApiResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


/**
 * @description:excel接口
 * @return:
 * @author: 赵进华
 * @time: 2020/4/2 14:26
 */
public interface IExcelService {

    /**
     * @description:同步导出excel
     * @return:
     * @author: 赵进华
     * @time: 2020/4/2 13:40
     */
    void exportExcelData(HttpServletResponse response, CompanyEntity vo)
            throws Exception;

    /**
     * @description:异步导出excel
     * @return:
     * @author: 赵进华
     * @time: 2020/3/28 21:57
     */
    ApiResponseResult exportExcelAsync(CompanyEntity vo) throws Exception;

    /**
     * @description:异步导出小区excel
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:57
     */
    ApiResponseResult exportCommunityExcelAsync(CommunityEntity vo) throws Exception;

    /**
     * @description:异步导出员工excel
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:57
     */
    ApiResponseResult exportUserExcelAsync(UserEntity vo) throws Exception;

    /**
     * @description:异步导出房产excel
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:57
     */
    ApiResponseResult exportHouseExcelAsync(HouseEntity vo) throws Exception;

    /**
     * @description:异步导出车位excel
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:57
     */
    ApiResponseResult exportCarExcelAsync(CarEntity vo) throws Exception;

    /**
     * @description:异步导出车位excel
     * @return:
     * @author: 吕文祥
     * @time: 2020/3/28 21:57
     */
    ApiResponseResult exportCustomerExcelAsync(CustomerEntity vo) throws Exception;


    /**
     * @description:查询excel文件导出列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/29 10:09
     */
    ApiResponseResult queryExcelFile() throws Exception;

    /**
     * @description:导入数据
     * @return:
     * @author: 赵进华
     * @time: 2020/4/2 17:46
     */
//    public ApiResponseResult importExcel(HttpServletRequest req, MultipartFile file) throws Exception;

    /**
     * @description:查询excel文件导出列表
     * @return:
     * @author: 赵进华
     * @time: 2020/3/29 10:09
     */
    ApiResponseResult queryExcelFile(String userId,String excelType) throws Exception;
}
