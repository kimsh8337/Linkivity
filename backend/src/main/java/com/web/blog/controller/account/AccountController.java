package com.web.blog.controller.account;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.web.blog.jwt.JwtService;
import com.web.blog.dao.user.UserDao;
import com.web.blog.model.BasicResponse;
import com.web.blog.model.user.User;
import com.web.blog.service.FindUtil;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 400, message = "Bad Request", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserDao userDao;

    @Autowired
    JwtService jwtService;

    @GetMapping("/login/{email}/{password}")
    @ApiOperation(value = "로그인")
    public Object login(@PathVariable String email, @PathVariable String password) throws SQLException, IOException {
        try {
            Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);
            if (userOpt.isPresent()) {
                User tokenuser = new User();
                tokenuser.setEmail(userOpt.get().getEmail());
                tokenuser.setPassword(userOpt.get().getPassword());
                String token = jwtService.createLoginToken(tokenuser);
                return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/authuser/{token}")
    @ApiOperation(value = "토큰으로 유저정보 가져오기")
    public Object authUser(@PathVariable String token) throws SQLException, IOException {
        User tokenuser = jwtService.getUser(token);
        Optional<User> userinfo = userDao.findUserByEmail(tokenuser.getEmail());
        try {
            if (userinfo.isPresent()) {
                return new ResponseEntity<>(userinfo.get(), HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@RequestBody User request)
            throws MessagingException, SQLException, IOException {
                System.out.println(request.toString());
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setNickname(request.getNickname());
        user.setCheckType(request.getCheckType());
        user.setImgurl(request.getImgurl());
        if (request.getCheckType().equals("business")) {
            user.setClocation(request.getClocation());
            user.setCphone(request.getCphone());
        }
        userDao.save(user);

        String charSet = "utf-8";
        String hostSMTP = "smtp.naver.com";
        // SMTP 서버명

        String hostSMTPid = "eagleeye0117@naver.com";
        String hostSMTPpwd = "mine0117tjdrhd12";
       
        // 보내는 사람
        String fromEmail = hostSMTPid;
        String fromName = "링키비티";
       
        String subject = "링키비티 회원가입을 축하합니다!!!";
        
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
            mail.addTo(request.getEmail());
            mail.setFrom(fromEmail, fromName, charSet);
            mail.setSubject(subject);
            // 내용
            mail.setHtmlMsg("링키비티에 가입해주셔서 진심으로 감사합니다.");
            mail.send();
            System.out.println("성공");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


    @PostMapping("/kakaologin")
    @ApiOperation(value = "카카오 로그인")
    public Object viewInfo(@RequestBody User request) throws SQLException, IOException {
        String token = null;
        try {
            Optional<User> userOpt = userDao.findUserByEmail(request.getEmail());
            if(userOpt.isPresent()){
                User tokenuser = new User();
                tokenuser.setEmail(userOpt.get().getEmail());
                tokenuser.setPassword(userOpt.get().getPassword());
                token = jwtService.createLoginToken(tokenuser);
            }else{
                User user = new User();
                user.setEmail(request.getEmail());
                user.setNickname(request.getNickname());
                user.setName(request.getNickname());
                user.setCheckType("normal");
                user.setPassword("kakaopassword123");
                userDao.save(user);

                User tokenuser = new User();
                tokenuser.setEmail(user.getEmail());
                tokenuser.setPassword(user.getPassword());
                token = jwtService.createLoginToken(tokenuser);
            }
            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/viewInfo/{email}")
    @ApiOperation(value = "회원정보조회")
    public Object viewInfo(@PathVariable String email) throws SQLException, IOException {
        try {
            Optional<User> userOpt = userDao.findUserByEmail(email);
            return userOpt.get();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/viewAllUser")
    @ApiOperation(value = "모든 회원정보")
    public Object viewAllUser() throws SQLException, IOException {
        try {
            return new ResponseEntity<>(userDao.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/checkEmail/{email}")
    @ApiOperation(value = "이메일확인")
    public String checkEmail(@PathVariable String email) {
        String result="";
        Optional<User> userOpt = userDao.findUserByEmail(email);
        if (userOpt.isPresent()) {
            result = "이미 존재하는 이메일입니다.";
        }
        return result;
    }

    @GetMapping("/checkNickname/{nickname}")
    @ApiOperation(value = "닉네임확인")
    public String checkNickname(@PathVariable String nickname) {
        String result="";
        Optional<User> userOpt = userDao.findUserByNickname(nickname);
        if (userOpt.isPresent()) {
            result = "이미 존재하는 닉네임입니다.";
        }
        System.out.println(result);
        return result;
    }

    @GetMapping("/getNickname/{email}")
    @ApiOperation(value = "닉네임가져오기")
    public String getNickname(@PathVariable String email) {
        User user = userDao.getUserByEmail(email);
        return user.getNickname();
    }

    @PutMapping("/modify/{pwvalidated}")
    @ApiOperation(value = "회원정보수정")
    public Object modify(@Valid @RequestBody User request, @PathVariable int pwvalidated)
            throws SQLException, IOException {
        try {
            Optional<User> userOpt = userDao.findUserByEmail(request.getEmail());
            if (userOpt.isPresent()) {
                User newUser = userOpt.get();
                if (pwvalidated == 1) {
                    newUser.setPassword(request.getPassword());
                }
                newUser.setNickname(request.getNickname());
                newUser.setImgurl(request.getImgurl());
                userDao.save(newUser);
                return newUser;
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{email}")
    @ApiOperation(value = "탈퇴하기")
    public Object delete(@PathVariable String email) {
        Optional<User> userOpt = userDao.findUserByEmail(email);
        if (userOpt.isPresent()) {
            userDao.delete(userOpt.get());
            return "탈퇴 완료";
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getImg/{email}")
    @ApiOperation(value = "그림가져오기")
    public String getImg(@PathVariable String email) {
        Optional<User> user = userDao.findUserByEmail(email);
        return user.get().getImgurl();
    }

    @GetMapping("/pwd/{email}/{name}")
    @ApiOperation(value = "임시비밀번호 발급")
    public void sendMail(@PathVariable String email, @PathVariable String name) throws Exception {
        
        //Mail Server 설정

        String charSet = "utf-8";
        String hostSMTP = "smtp.naver.com";
        // SMTP 서버명

        String hostSMTPid = "eagleeye0117@naver.com";
        String hostSMTPpwd = "mine0117tjdrhd12";
       
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