package com.web.blog.controller.account;

import java.sql.SQLException;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.web.blog.dao.user.UserDao;
import com.web.blog.model.user.User;
import com.web.blog.service.KakaoAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
// @RestController
@RestController
@RequestMapping("/kko")
public class kakaoController {
    
    @Autowired
    private KakaoAPI kakao;

    @Autowired
    UserDao userDao;

    @GetMapping("/kakao/login")
   public String login() throws SQLException {
      System.out.println("logger - " + "/kakao/login");
      String URL = "https://kauth.kakao.com/oauth/authorize?client_id=8940d7dfdaa8e8db4c0f4d73dd782a21"
            + "&redirect_uri=http://localhost:8080/kko/logink" + "&response_type=code";
      System.out.println(URL);
      
      return URL;
   }
/* map<String,Object> map = new HashMap<>();
   map.put("url", url);
   ResponseEntity<String>
*/
   @GetMapping("/logink")
   public HashMap<String, Object> login(@RequestParam("code") String code) throws SQLException {
      System.out.println("logger - kakao login 후에 getCode");
      System.out.println("code : " + code);
      System.out.println("logger - code를 기반으로 getAccessToken");
      String access_Token = "";
      try {
         access_Token = kakao.getAccessToken(code);
         
         System.out.println(kakao.getUserInfo(access_Token));
         
      } catch (Exception e) {
         e.printStackTrace();
      }
      System.out.println("controller access_token : " + access_Token);

      HashMap<String, Object> userInfo = null;
      userInfo = kakao.getUserInfo(access_Token);

      String userEmail = "a";
      String userNickname = "a";
      if(userEmail != null) {
         userEmail = userInfo.get("email").toString();
      }

      if(userNickname != null) {
         userNickname = userInfo.get("nickname").toString();
      }

      User user = new User();
      if(userDao.findUserByEmail(userEmail).isPresent()) {
         System.out.println("디비에 있는 아이디");
      } else {
         System.out.println("디비에 없는 아이디");
         user.setEmail(userEmail);
         user.setNickname(userNickname);
         user.setCheckType("normal");
         user.setName("카카오 사용자");
         user.setPassword("qwer1234");
         
         userDao.save(user);
      }

      return userInfo;

//      클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
//  if (userInfo.get("email") != null) {
//      session.setAttribute("userId", userInfo.get("email"));
//      session.setAttribute("access_Token", access_Token);
//  }

//   return "index";
   }
   // @GetMapping("/kakao/login/getInfo/{access_Token}")
   // public @ResponseBody HashMap<String, Object> getInfo(@PathVariable String access_Token) throws SQLException{
   //    HashMap<String, Object> userInfo = null;
   //    System.out.println(access_Token);
   //    try {
   //     userInfo = kakao.getUserInfo(access_Token);
   //     System.out.println("login Controller : " + userInfo);
   // } catch (Exception e) {
   //    e.printStackTrace();
   // }
   //    return userInfo;
      
   // }
   @GetMapping("/kakao/login/getInfo/{access_Token}")
   public Object getInfo(@PathVariable String access_Token) throws SQLException{
      HashMap<String, Object> userInfo = null;
      System.out.println("@@@" + access_Token);
      try {
       userInfo = kakao.getUserInfo(access_Token);
       System.out.println("login Controller : " + userInfo);
   } catch (Exception e) {
      e.printStackTrace();
   }
      return userInfo;
      
   }

    
}