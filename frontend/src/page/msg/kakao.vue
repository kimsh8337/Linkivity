
<template>
  <div>
<img id="kakao-login-btn" @click="test()" src="https://kauth.kakao.com/public/widget/login/kr/kr_02_medium.png" style="cursor: pointer" onmouseover="this.src='https://kauth.kakao.com/public/widget/login/kr/kr_02_medium_press.png'" onmouseout="this.src='https://kauth.kakao.com/public/widget/login/kr/kr_02_medium.png'">
         <!-- <button id="kakao-login-btn" @click="test()"></button> -->

   <!-- <img src="../../assets/img/kakaologin.png"> -->
  </div>

</template>``

<script>
import axios from "axios";
const baseURL = "http://localhost:8080";
export default {
  created(){
      Kakao.init('765ed14c0d508f8aa48c6d173446acba')
  },
  data:() => {
    return {
      kakao: {
        email: "",
        nickname: "",
      },
    }
  },
  methods: {
       test(){
         let x = this;
             Kakao.Auth.createLoginButton({
            container: '#kakao-login-btn',
            success: function(authObj) {
             Kakao.API.request({
                url: '/v2/user/me',
            success: function(res) {
        //  alert(JSON.stringify(res)); //<---- kakao.api.request 에서 불러온 결과값 json형태로 출력

        //  alert(JSON.stringify(authObj)); //<----Kakao.Auth.createLoginButton에서 불러온 결과값 json형태로 출력
           
           console.log(res);//<---- 콘솔 로그에 id 정보 출력(id는 res안에 있기 때문에  res.id 로 불러온다)
          //  console.log(res.kakao_account.email);//<---- 콘솔 로그에 email 정보 출력 (어딨는지 알겠죠?)
          //  console.log(res.properties.nickname);//<---- 콘솔 로그에 닉네임 출력(properties에 있는 nickname 접근 

          x.kakao.email = res.kakao_account.email;
           x.kakao.nickname = res.properties.nickname;

           axios
        .post(`${baseURL}/account/kakaologin`,x.kakao)
        .then(response => {
          alert("로그인 성공");
          console.log(response.data);
          x.$cookies.set("Auth-Token", response.data);
              x.$router.push("/");
              x.$router.go();
        })
        .catch(err => {
          console.log(err);
          // this.$router.push({name: 'Params', params: {name: err.response.status}});
        });

      // res.properties.nickname으로도 접근 가능 )

          //console.log(authObj.access_token);//<---- 콘솔 로그에 토큰값 출력

        }

      })

    },

    fail: function(error) {

      alert(JSON.stringify(error));

    },

  });     
   },
   fetch: function() {
     axios
     .post(`${baseURL}/kko/kakao/login`, this.kakao)
     .then(res => {
       alert("테스트입니다")
        this.$router.push("/main");
     })
     .catch(() => {
       alert("테스트 실패ㅠ")
     })

   }
   
   
 }
}
</script>