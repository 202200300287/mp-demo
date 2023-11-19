package com.itheima.mp.service;


import com.itheima.mp.payload.request.DataRequest;
import com.itheima.mp.payload.response.DataResponse;
import com.itheima.mp.service.iservice.MailService;
import com.itheima.mp.util.CommomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Configuration
@ComponentScan(basePackages = "com.itheima.mp")
public class AuthService {

    @Autowired(required = false)
    private MailService mailService;

    public String getCodeVer(){
        Random random = new Random();
        StringBuilder s= new StringBuilder();
        for(int i=0;i<6;i++){
            s.append(random.nextInt(10));
        }
        return s.toString();
    }

    public DataResponse sendEmail(DataRequest dataRequest) {
        String to = dataRequest.getString("email");
        String code = getCodeVer();
        String title = "邮箱验证";
        String content = "<html>"
                + "<head>"
                + "    <style>"
                + "        body {"
                + "            font-family: Arial, sans-serif;"
                + "            background-color: #f5f5f5;"
                + "            padding: 20px;"
                + "        }"
                + "        h1 {"
                + "            color: #333;"
                + "            text-align: center;"
                + "        }"
                + "        .container {"
                + "            text-align:center;"
                + "            width: 90%;"
                + "            height: 50%"
                + "            margin: 0 auto;"
                + "            background-color: #fff;"
                + "            padding: 20px;"
                + "            border-radius: 5px;"
                + "            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);"
                + "        }"
                + "        code {"
                + "            background-color: #f2f2f2;"
                + "            padding: 2px 5px;"
                + "            border-radius: 3px;"
                + "        }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class='container'>"
                + "        <h1>欢迎来到 CutePony 的系统</h1>"
                + "        <p><code>" + code + "</code></p>"
                + "        <p>祝您使用愉快！</p>"
                + "    </div>"
                + "</body>"
                + "</html>";
        mailService.sendSimpleText(to, title, content);
        return CommomMethod.getReturnData(code);
    }

}
