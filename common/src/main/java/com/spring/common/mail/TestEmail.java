package com.spring.common.mail;

/**
 * @author dell
 */
public class TestEmail {
    public static void main(String[] args) throws Exception {
        String mailHost="smtp.exmail.qq.com";
        String fromAddress="jinhua.zhao@farben.com.cn";
        String mailUser="jinhua.zhao@farben.com.cn";
        String mailPassword="Zjh198138!";
        String email="jinhua.zhao@farben.com.cn";
        String projectNo="A001";
        String systemAddress="http://14.29.241.30:9082/";

        Mail mail = new Mail();
        // 设置邮件服务器
        mail.setHost(mailHost);
        // 发件人邮件地址
        mail.setSender(fromAddress);
        // 发件人名称
        mail.setName("格力智能制造项目管理系统");
        // 登录账号,一般都是和邮箱名一样吧
        mail.setUsername(mailUser);
        // 发件人邮箱的登录密码
        mail.setPassword(mailPassword);
        // 接收人
        mail.setReceiver(email);
        mail.setReceiverName(email);
        mail.setSubject("格力智能制造系统提醒");
        String html = "<!DOCTYPE html>";
        html += "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
        html += "<title>格力智能制造项目管理系统提醒</title>";
        html += "</head><body>";
        html += "<div style=\"width:1000px;height:400px;margin:50px auto;\">";
        html += "<p>尊敬的用户:</p>";
        html += "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好 !</p><br/>";
        html += "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你在格力智能制造项目管理系统中，有一份驳回,单号:"+projectNo+", 待你处理，谢谢！</p>";
        html += "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系统地址:<a href=\""+systemAddress+"\">"+systemAddress+"</a></p>";
        html += "</div>";
        html += "</body></html>";
        mail.setMessage(html);
        new MailUtil().send(mail);
    }
}
