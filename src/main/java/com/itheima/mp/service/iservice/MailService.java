package com.itheima.mp.service.iservice;

/**
 * @author xyp
 * @create 2022-10-07 12:57
 * @describe 邮件发送接口，定义发送不同类型邮件的方法
 */
public interface MailService {
    /**
     * 发送简单文本的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @return
     */
    boolean sendSimpleText(String to, String subject, String content);

    /**
     * 发送 html 的邮件
     *
     * @param to
     * @param subject
     * @param html
     * @return
     */
    boolean sendWithHtml(String to, String subject, String html);

    /**
     * 发送带有图片的 html 的邮件
     *
     * @param to
     * @param subject
     * @param html
     * @param cids
     * @param filePaths
     * @return
     */
    boolean sendWithImageHtml(String to, String subject, String html, String[] cids, String[] filePaths);


    /**
     * 发送带有附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePaths
     * @return
     */
    boolean sendWithWithEnclosure(String to, String subject, String content, String[] filePaths);
}
