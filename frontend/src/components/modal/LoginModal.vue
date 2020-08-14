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
      <div class="modal-content" style="margin:auto;height:20rem; width:25rem;">
        <div class="modal-header border-0 pl-0">
          <h4
            class="modal-title w-100 text-center font-weight-bold position-absolute"
            id="exampleModalLabel"
          >
            Login
          </h4>
          <button
            type="button"
            class="close"
            data-dismiss="modal"
            aria-label="Close"
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body pb-0">
          <i class="fas fa-envelope" style="font-size:20px"></i>
          <input
            class="text-center ml-1 mt-4 bg-white"
            style="width:60%; border: none;"
            type="text"
            v-model="email"
            id="email"
            placeholder="이메일을 입력해주세요."
          />
          <div class="error-text" v-if="error.email">{{ error.email }}</div>
          <p class="mb-4"></p>
          <i class="fas fa-lock" style="font-size:20px"></i>
          <input
            class="text-center ml-1 mt-4"
            style="width:60%; border: none;"
            type="password"
            v-model="password"
            id="password"
            placeholder="비밀번호를 입력해주세요."
            @keypress.enter="login"
          />
        </div>
        <div class="modal-footer border-0 pt-0">
          <!-- 카카오 로그인 이미지 -->
          <!-- <img src="../../assets/img/kakaologin.png"> -->
          <!-- <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button> -->
          <div style>
            <button
              type="button"
              class="btn"
              @click="join"
              data-dismiss="modal"
            >
              <i class="far fa-user mr-1"></i>
              <br />회원가입
            </button>
            <button
              type="button"
              class="btn"
              @click="login"
              data-dismiss="modal"
            >
              <i class="fas fa-sign-in-alt mr-1"></i>
              <br />로그인
            </button>
            <button
              type="button"
              class="btn"
              @click="pwsearch"
              data-dismiss="modal"
            >
              <i class="fas fa-unlock"></i>
              <br />비밀번호 찾기
            </button>
            <img
              id="kakao-login-btn"
              @click="test()"
              src="../../assets/img/kakaologin.png"
              style="cursor: pointer; width :100px;"
            />카카오로그인
          </div>
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
    pwsearch: function() {
      this.$router.push("/user/Pwsearch/");
      this.$router.go();
    },
    report() {
      axios
        .get(`${baseURL}/report/reports/${this.email}`)
        .then((response) => {
          alert(response.data);
          if (response.data == 0) {
            alert("경고!");
          } else if (response.data == 1) {
            alert("신고 누적으로 탈퇴되셨습니다!");
            return;
          }
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    test() {
      let x = this;
      Kakao.Auth.createLoginButton({
        container: "#kakao-login-btn",
        success: function(authObj) {
          Kakao.API.request({
            url: "/v2/user/me",
            success: function(res) {
              console.log(res); //<---- 콘솔 로그에 id 정보 출력(id는 res안에 있기 때문에  res.id 로 불러온다)
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

<style></style>
