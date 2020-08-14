<template>
  <div
    class="modal fade"
    id="LoginModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content" style="width:80%; border:0">
        <div class="modal-header pl-0" style="background-color:RGB(134, 165, 212); width:100%">
          <h4
            class="modal-title font-weight-bold my-auto ml-2"
            id="exampleModalLabel"
            style="width:60%;"
          ><i class="fas fa-file-signature ml-5">ＬＯＧＩＮ</i></h4>
          <button type="button" class="close my-auto ml-0" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <!-- email 입력칸 -->
        <div class="modal-body d-flex justify-content-center mt-2">
          <i v-if="!error.email" class="fas fa-envelope my-auto mr-2" style="font-size:25px"></i>
          <i v-if="error.email" class="fas fa-envelope my-auto mr-2" style="font-size:25px; color:red" title="이메일 형식을 아닙니다!"></i>
          <input
            class="text-center"
            style="width:60%; border-radius:5px;"
            type="text"
            v-model="email"
            id="email"
            placeholder="email@example.com"
          />
        </div>
        <!-- <small class="d-flex justify-content-end mt-0 mb-0" style="width:82%" v-if="error.email"><i class="fas fa-exclamation mr-1 my-auto"></i>{{error.email}}</small> -->
        
        <!-- password 입력칸 -->
        <div class="modal-body d-flex justify-content-center mt-2">
          <i class="fas fa-lock my-auto mr-2" style="font-size:25px"></i>
          <input
            class="text-center"
            style="width:60%; border-radius:5px;"
            type="password"
            v-model="password"
            id="password"
            placeholder="Input your password"
            @keypress.enter="login"
          />
          </div>

        <div style="margin-left:10%; margin-right:10%">
          <hr>
        </div>

        <!-- 로그인 -->
        <div @click="login" data-dismiss="modal" class="mt-2 row" style="cursor: pointer; margin-left:18%; margin-right:18%;">
          <div class="col-12 d-flex justify-content-center align-items-center" style="border:1px solid RGB(134, 165, 212); height:2rem; border-radius:5px">
            <i class="fas fa-sign-in-alt"><span class="my-auto ml-2">로그인</span></i>
          </div>
        </div>

        <!-- 회원가입 -->
        <div @click="join" data-dismiss="modal" class="mt-2 row" style="cursor: pointer; margin-left:18%; margin-right:18%;">
          <div class="col-12 d-flex justify-content-center align-items-center" style="border:1px solid RGB(134, 165, 212); height:2rem; border-radius:5px">
            <i class="far fa-user mr-1"><span class="my-auto ml-2" style="font-weight:bold">회원가입</span></i>
          </div>
        </div>

        <!-- 비밀번호 찾기 -->
        <div data-dismiss="modal" class="mt-2 row" style="cursor: pointer; margin-left:18%; margin-right:18%;">
          <div class="col-12 d-flex justify-content-center align-items-center" style="border:1px solid RGB(134, 165, 212); height:2rem; border-radius:5px">
            <i class="fas fa-unlock"><span class="my-auto ml-2">비밀번호 찾기</span></i>
          </div>
        </div>

        <!-- 카카오톡 로그인 -->
        <div id="kakao-login-btn" @click="kakaoLogin" class="mt-2 row d-flex justify-content-center" style="cursor: pointer; margin-left:18%; margin-right:18%;">
          <div class="col-12 d-flex justify-content-center align-items-center" style="border:1px solid RGB(134, 165, 212); height:2rem; border-radius:5px">
            <img  src="../../assets/img/kakaologin2.jpg" style="width:20px"><span class="my-auto ml-2" style="font-weight:bold">카카오 로그인</span>
          </div>
        </div>
        
        <div class="mt-2" style="margin-left:10%; margin-right:10%;">
          <hr>
        </div>

        <div class="modal-footer border-0" style="">

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import axios from "axios";
import Swal from "sweetalert2";

const baseURL = process.env.VUE_APP_BACKURL;
export default {
  name: "Post",
  components: {},
  created() {
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();
  },
  watch: {
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    },
  },

  methods: {
    checkForm() {
      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else if (this.email.length > 0 && EmailValidator.validate(this.email)) {
        axios
          .get(`${baseURL}/account/checkEmail/${this.email}`)
          .then((response) => {
            this.error.email = "";
          })
          .catch(() => {});
      } else this.error.email = false;
    },
    login() {
      axios
        .get(`${baseURL}/account/login/${this.email}/${this.password}`)
        .then((response) => {
          if (response.data == 0) {
            alert(
              "해당 아이디는 신고 누적으로 차후에 이용이 제한될 수 있습니다."
            );
            this.$cookies.set("Auth-Token", response.data);
          } else if (response.data == 1) {
            alert("해당 아이디는 신고 누적으로 이용이 제한되었습니다.");
          } else {
            this.$cookies.set("Auth-Token", response.data);
          }
          this.$router.push("/");
          this.$router.go();
        })
        .catch((err) => {
          console.log(err.response);
          alert("아이디 및 비밀번호를 확인해주세요.");
          this.email = "";
          this.password = "";
        });
    },
    join: function() {
      this.$router.push("/user/join/");
      this.$router.go();
    },
    kakaoLogin() {
      let x = this;
      Kakao.Auth.createLoginButton({
        container: "#kakao-login-btn",
        success: function(authObj) {
          Kakao.API.request({
            url: "/v2/user/me",
            success: function (res) {
              //  console.log(res.kakao_account.email);//<---- 콘솔 로그에 email 정보 출력 (어딨는지 알겠죠?)
              //  console.log(res.properties.nickname);//<---- 콘솔 로그에 닉네임 출력(properties에 있는 nickname 접근
              x.kakao.email = res.kakao_account.email;
              x.kakao.nickname = res.properties.nickname;

              axios
                .post(`${baseURL}/account/kakaologin`, x.kakao)
                .then((response) => {
                  console.log(response.data);
                  x.$cookies.set("Auth-Token", response.data);
                  x.$router.push("/");
                  x.$router.go();
                })
                .catch((err) => {
                  console.log(err);
                });
            },
          });
        },

        fail: function(error) {
          alert(JSON.stringify(error));
        },
      });
    },
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        password: false,
      },
      passwordType: "password",
      kakao: {
        email: "",
        nickname: "",
      },
    };
  },
};
</script>

<style>
.container {
  margin-left: 10%;
  margin-right: 10%;
}
</style>
