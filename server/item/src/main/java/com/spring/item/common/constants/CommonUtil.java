package com.spring.item.common.constants;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author dell
 */
public class CommonUtil {

    /**
     * 格式化时间
     * @param date
     * @return
     */
    public static String getFormatDate(Date date){
        String str = "yyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        return sdf.format(date);
    }
}
