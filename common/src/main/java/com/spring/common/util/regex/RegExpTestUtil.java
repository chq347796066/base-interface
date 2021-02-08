package com.spring.common.util.regex;

import java.util.regex.Pattern;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:正则匹配工具类
*/
public class RegExpTestUtil
{
  public static boolean serviceNameRegExpTest(String serviceName)
  {
    String serviceNameReg = "^zte-([a-z]){2,}-([a-z]){2,}-([a-z]){2,}$";
    return Pattern.matches(serviceNameReg, serviceName);
  }

  public static boolean hostRegExpTest(String host)
  {
    String hostReg = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d):(\\d{1,5})$";

    return Pattern.matches(hostReg, host);
  }

  public static boolean ipRegExpTest(String ip)
  {
    String hostReg = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    return Pattern.matches(hostReg, ip);
  }

  public static boolean portRegExpTest(String port)
  {
    String hostReg = "^\\d{1,5}$";
    return Pattern.matches(hostReg, port);
  }

  public static boolean versionRegExpTest(String version)
  {
    String versionReg = "^v\\d+(\\.\\d+)?$";
    return Pattern.matches(versionReg, version);
  }

  public static boolean urlRegExpTest(String url)
  {
    if ("/".equals(url)) {
      return true;
    }
    String urlReg = "^\\/.*((?!\\/).)$";
    return Pattern.matches(urlReg, url);
  }

  public static void main(String[] args)
  {
    System.out.println(serviceNameRegExpTest("zte-crm-prm-authentication"));
  }
}