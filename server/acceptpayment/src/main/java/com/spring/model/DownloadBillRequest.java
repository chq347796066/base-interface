package com.spring.model;

import lombok.Data;

/**
 * 下载对账文件请求
 * @author dell
 */
@Data
public class DownloadBillRequest {

    //对账日期
    private String billDate;

}
