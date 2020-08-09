package com.web.blog.controller.account;

import java.util.Optional;


import com.web.blog.dao.user.UserDao;
import com.web.blog.model.user.User;
import com.web.blog.service.FindUtil;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pwsearch")
public class MailUtil {

    
    // @Autowired
    // FindUtil findUtil;
    
    @Autowired
    UserDao userDao;

    @PutMapping("/pwd")
    public void sendMail(String email, String name) throws Exception {
        
        // String keyCode = FindUtil.createKey();
    

        //Mail Server 설정

        String charSet = "utf-8";
        String hostSMTP = "smtp.naver.com";
        // SMTP 서버명

        String hostSMTPid = "eagleeye0117@naver.com";
        String hostSMTPpwd = "mine0117tjdrhd12";


        email = "mine011776@gmail.com";
       
        // 보내는 사람
        String fromEmail = hostSMTPid;
        String fromName = "링키비티";
       
        String subject = "링키비티 임시 비밀번호 찾기";
       
        String newPwd = FindUtil.getNewPwd();
        Optional<User> userOpt = userDao.findUserByEmail(email);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            user.setPassword(newPwd);
            userDao.save(user);
        }


        // email 전송
        try {
            HtmlEmail mail = new HtmlEmail();
            mail.setDebug(true);
            mail.setCharset(charSet);
            mail.setSSLOnConnect(true);

        //SSL 사용(TLS가 없는 경우 SSL 사용)
            mail.setHostName(hostSMTP);
            mail.setSmtpPort(587);
            mail.setAuthentication(hostSMTPid, hostSMTPpwd);
            mail.setStartTLSEnabled(true);
            mail.addTo(email);
            mail.setFrom(fromEmail, fromName, charSet);
            mail.setSubject(subject);
            // 내용
            mail.setHtmlMsg(""+newPwd);
            mail.send();
            System.out.println("성공");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}