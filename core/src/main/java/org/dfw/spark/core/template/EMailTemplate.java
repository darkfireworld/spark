package org.dfw.spark.core.template;

import org.dfw.spark.core.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Async;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EMailTemplate implements InitializingBean {
    Logger logger = LoggerFactory.getLogger(EMailTemplate.class);
    //邮箱名称
    String email;
    //邮箱密码
    @Nullable
    String password;
    //smtp地址
    String smtp;
    //会话
    Session session;
    // 是否为调试模式
    boolean debug;

    /**
     * 构造函数
     *
     * @param email    邮箱地址
     * @param password 密码，可空
     * @param smtp     邮件服务器地址
     * @param debug    是否为调试模式
     */
    public EMailTemplate(String email, String password, String smtp, boolean debug) {
        this.email = email;
        this.password = password;
        this.smtp = smtp;
        this.debug = debug;
    }

    /**
     * 发送邮件，注意：如果在测试环境下，则所有邮件都会发送都指定的测试邮箱。
     *
     * @param debugEmail 测试环境发送邮箱，可空
     * @param tos        收件人邮箱地址
     * @param ccs        抄送邮箱地址，可空
     * @param subject    邮件主题
     * @param content    邮件内容
     */
    @Async
    public void send(String debugEmail, String[] tos, @Nullable String[] ccs, String subject, String content) {
        // 测试环境，所有的邮件都定向到指定邮箱中
        if (debug) {
            // 重定向到测试邮箱
            tos = new String[]{debugEmail};
            // 关闭抄送
            ccs = null;
            // 修改邮件标题
            subject = String.format("%s-测试邮件", subject);
        }
        try {
            // 创建mime类型邮件
            final MimeMessage message = new MimeMessage(session);
            // 设置发信人
            message.setFrom(new InternetAddress(email));
            //TO
            {
                InternetAddress[] ids = new InternetAddress[tos.length];
                for (int i = 0; i < tos.length; ++i) {
                    ids[i] = new InternetAddress(tos[i]);
                }
                // 设置收件人
                message.setRecipients(Message.RecipientType.TO, ids);
            }
            //CC
            {
                if (ccs != null && ccs.length > 0) {
                    InternetAddress[] ids = new InternetAddress[ccs.length];
                    for (int i = 0; i < ccs.length; ++i) {
                        ids[i] = new InternetAddress(ccs[i]);
                    }
                    // 设置收件人
                    message.setRecipients(Message.RecipientType.CC, ids);
                }
            }
            // 设置主题
            message.setSubject(subject);
            // 设置邮件内容
            message.setContent(content, "text/html;charset=utf-8");
            // 发送
            Transport.send(message);
        } catch (Exception e) {
            logger.error("发送邮件失败：{}-{}\n{}", tos, subject, content, e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化props
        Properties props = new Properties();
        Authenticator authenticator = null;
        if (email != null && password != null) {
            authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            };
        }
        props.put("mail.smtp.auth", authenticator == null ? "false" : "true");
        props.put("mail.smtp.host", smtp);
        // 创建session
        session = Session.getInstance(props, authenticator);
    }
}
