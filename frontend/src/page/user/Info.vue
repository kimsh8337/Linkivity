<template>
  <div class="container col-sm-12 col-md-12 col-lg-12 p-0">
    <!-- background image -->
    <div class="info-img" style="display:block;">
      <div class="info-bg"></div>
    </div>
    <div class="container col-md-11 col-sm-11 mt-5" id="join">
      <div class="wrapC table">
        <div class="middle">
          <!-- <h1 v-if="validated == 1">회원정보 조회</h1> -->
          <!-- <h1 v-if="validated == 0">회원정보 수정</h1> -->
          <!-- img -->
          <div class="card col-sm-12 mt-1">
            <div class="d-flex justify-content-center">
              <!-- <input ref="imageInput" type="file" hidden @change="onChangeImages" />
              <img class="infoimg" v-if="this.imgurl" :src="this.imgurl" style="box-shadow:5px 5px 5px rgba(0,0,0,.15)" />
              <button type="button" class="btn btn-outline" @click="onClickImageUpload" v-if="validated == 0">-->
              <div class="col-12 d-flex justify-content-between row">
                <div class="inputimg col-12 col-md-6">
                  <div>
                    <input ref="file" type="file" hidden @change="onChangeImages" />
                    <img
                      class="infoimg"
                      v-if="imgurl && !tempcheck"
                      :src="makeimgurl(imgurl)"
                      style="box-shadow:5px 5px 5px rgba(0,0,0,.15)"
                    />
                    <img
                      class="infoimg"
                      v-if="tempimg && tempcheck"
                      :src="tempimg"
                      style="box-shadow:5px 5px 5px rgba(0,0,0,.15)"
                    />
                    <img
                      v-if="!imgurl && !tempimg"
                      class="infoimg"
                      src="../../assets/img/noimage.jpg"
                      style="box-shadow:5px 5px 5px rgba(0,0,0,.15)"
                    />
                  </div>
                  <button
                    type="button"
                    class="btn btn-default btn-sm col-md-5 mt-2"
                    style="border-radius:12px; font-size:13px; border:1.5px solid"
                    @click="onClickImageUpload"
                    v-if="validated == 0"
                  >
                    <i class="fas fa-image mr-2"></i>프로필사진 수정
                  </button>
                </div>

                <!-- <input type="file" id="file" name="file" ref="file" />
            <button v-on:click="fileUpload" >fileUpload</button>
                <img v-bind:src="this.imgurl" width="200"/>-->
                <!-- <img v-bind:src="path" width="200"/> -->

                <div class="col-12 col-md-5 pr-0">
                  <div class="form-group-info d-flex justify-content-between">
                    <!-- <label for="nickname">닉네임</label> -->
                    <input
                      v-if="validated==1"
                      class="form-control mb-1 infofont"
                      disabled
                      v-model="nickname"
                      id="nickname"
                      type="text"
                      style="font-size: 30px; font-weight:bold;"
                    />
                    <input
                      class="form-control mb-1"
                      v-if="validated == 0"
                      v-model="nickname"
                      id="nickname"
                      type="text"
                      style="font-size: 30px; font-weight:bold; border:1px solid lightgray !important"
                    />
                    <button v-if="validated == 1" @click="gomodify" class="btn">
                      <i class="fas fa-wrench my-auto" style="font-size:1.5rem; color:gray;"></i>
                    </button>
                    <button v-if="validated == 0" @click="modify" class="btn p-0">
                      <div class="row mx-auto d-flex">
                        <i class="fas fa-save my-auto mx-auto" style="font-size:1.5rem;"></i>
                        <small class="mx-auto">완료</small>
                      </div>
                    </button>
                    <button v-if="validated == 0" @click="modifyCancel" class="btn p-0">
                      <div class="row mx-auto d-flex">
                        <i class="fas fa-cut my-auto mx-auto" style="font-size:1.5rem;"></i>
                        <small class="mx-auto">취소</small>
                      </div>
                    </button>

                    <!-- <span class="nickname-edit" v-if="validated == 0"> -->
                    <!-- <i class="fas fa-arrow-up mr-2"></i> -->
                    <!-- 닉네임 클릭하여 변경 -->
                    <!-- <i class="fas fa-arrow-up"></i> -->
                    <!-- </span> -->
                  </div>
                  <div class="form-group-info infofont">
                    <!-- <label for="email">이메일</label> -->
                    <input
                      class="form-control mt-2"
                      v-if="pwvalidated == 0"
                      disabled="false"
                      v-model="email"
                      type="text"
                      style="font-size: 20px; "
                    />
                  </div>
                  <div class="form-group-info infofont">
                    <!-- <label for="name">이름</label> -->
                    <input
                      class="form-control mb-3"
                      v-if="pwvalidated == 0"
                      disabled="false"
                      v-model="name"
                      id="name"
                      type="text"
                      style="font-size: 20px;"
                    />
                  </div>
                  <div class="d-flex p-0">
                  <button
                    @click="passwordModify"
                    v-if="pwvalidated == 0 && validated == 1"
                    class="btn btn-sm p-0"
                    style="color:#86a5d4; font-size:1rem; font-weight:bold;"
                  >비밀번호 변경</button>
                  </div>
                  <div
                    class="d-flex justify-content-between"
                    style="margin-top:0.6rem;"
                    v-if="pwvalidated == 0"
                  >
                    <div class="d-flex justify-content-start" v-if="this.checkType=='business'">
                      <i class="fas fa-clipboard mr-2" style="font-size:2rem;"></i>
                      <span style="font-size:1.5rem;font-weight:bold;">{{this.myposts}}</span>
                    </div>
                    <div class="d-flex justify-content-start" v-if="this.checkType=='normal'">
                      <i class="fas fa-shopping-basket mr-2" style="font-size:2rem;"></i>
                      <span style="font-size:1.5rem;font-weight:bold;">{{this.mycarts}}</span>
                    </div>
                    <div class="d-flex justify-content-start">
                      <i class="fas fa-heart mr-2" style="font-size:2rem;"></i>
                      <span style="font-size:1.5rem;font-weight:bold;">{{this.mylikes}}</span>
                    </div>
                    <div class="d-flex justify-content-start" v-if="this.checkType=='normal'">
                      <i class="fas fa-money-check mr-2" style="font-size:2rem;"></i>
                      <span style="font-size:1.5rem;font-weight:bold;">{{this.buycounts}}</span>
                    </div>
                    <div class="d-flex justify-content-start" v-if="this.checkType=='normal'">
                      <i class="fas fa-comments mr-2" style="font-size:2rem;"></i>
                      <span style="font-size:1.5rem;font-weight:bold;">{{this.myreviews}}</span>
                    </div>
                    <div class="d-flex justify-content-start" v-if="this.checkType=='business'">
                      <i class="fas fa-money-check mr-2" style="font-size:2rem;"></i>
                      <span style="font-size:1.5rem;font-weight:bold;">{{this.sellcounts}}</span>
                    </div>
                    <div class="d-flex justify-content-start" v-if="this.checkType=='business'">
                      <i class="fas fa-save mr-2" style="font-size:2rem;"></i>
                      <span style="font-size:1.5rem;font-weight:bold;">{{this.tempcounts}}</span>
                    </div>
                  </div>

                

                  <div class="form-group-pw mt-1" align="left" v-if="pwvalidated == 1" style="width:70%;">
                    <label class="mt-2" for="pw">비밀번호</label>
                    <input
                      class="form-control mb-1"
                      v-model="password"
                      v-bind:class="{ error: error.password, complete: !error.password && password.length !== 0 }"
                      id="password"
                      :type="passwordType"
                    />
                    <div class="d-flex justify-contetn-between">
                      <span v-if="error.password" :class="{ active: passwordType === 'text' }">
                        <i class="fas fa-eye mr-2"></i>
                      </span>
                      <div class="error-text mt-1" v-if="error.password">{{ error.password }}</div>
                    </div>
                  </div>

                  <div class="form-group-pw" align="left" v-if="pwvalidated == 1" style="width:70%;">
                    <label for="name">비밀번호 확인</label>
                    <input
                      class="form-control mb-1"
                      v-model="passwordconfirm"
                      v-bind:class="{ error: error.passwordconfirm, complete: !error.passwordconfirm && passwordconfirm.length !== 0 }"
                      id="passwordconfirm"
                      :type="passwordConfirmType"
                    />
                    <div class="d-flex justify-contetn-between">
                      <span
                        v-if="error.passwordconfirm"
                        :class="{ active: passwordConfirmType === 'text' }"
                      >
                        <i class="fas fa-eye mr-2"></i>
                      </span>
                      <div
                        class="error-text mt-1"
                        v-if="error.passwordconfirm"
                      >{{ error.passwordconfirm }}</div>
                    </div>
                  </div>
                  <div class="d-flex justify-content-end">
                    <button
                      v-if="pwvalidated == 1"
                      @click="modify"
                      class="btn btn-link btn-sm mb-2"
                    >
                      <i class="fas fa-check mr-2"></i>완료
                    </button>
                    <button
                      @click="cancel"
                      v-if="pwvalidated == 1"
                      class="btn btn-link btn-sm mb-2 ml-2 p-0"
                    >
                      <i class="fas fa-times mr-2"></i>취소
                    </button>
                  </div>
                </div>
                <div class="col-sm-0 col-md-1"></div>
              </div>
            </div>
          </div>

          <div class="mt-5">
            <b-tabs content-class="mt-3" fill>
              <b-tab title="상품목록" active v-if="this.checkType=='business'">
                <Mypost />
              </b-tab>
              <b-tab title="장바구니" active v-if="this.checkType=='normal'">
                <Cart />
              </b-tab>
              <b-tab title="좋아요">
                <Like />
              </b-tab>
              <b-tab title="구매목록" v-if="this.checkType=='normal'">
                <Buy />
              </b-tab>
              <b-tab title="후기" v-if="this.checkType=='normal'">
                <Review />
              </b-tab>
              <b-tab title="판매목록" v-if="this.checkType=='business'">
                <Sell />
              </b-tab>
              <b-tab title="임시저장" v-if="this.checkType=='business'">
                <Temp />
              </b-tab>
            </b-tabs>
          </div>

          <!-- <hr class="border-bottom-1 border-black mt-1" />
          <div class="card col-sm-12 mt-1"></div>-->
          <!-- <hr /> -->
          <div class="d-flex justify-content-end">
          <button @click="deluser" class="btn btn-default btn-sm" style="border-radius:7px; font-weight:bold; border:1.2px solid #86a5d4;">
            탈퇴하기
          </button>
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
import "../../assets/css/info.css";
import Like from "../post/PostLike.vue";
import Temp from "../post/PostTemp.vue";
import Cart from "../post/PostCart.vue";
import Buy from "../post/PostBuy.vue";
import Sell from "../post/PostSell.vue";
import Review from "../post/PostReview.vue";
import Mypost from "../post/PostWrite.vue";
import Swal from "sweetalert2";

const baseURL = process.env.VUE_APP_BACKURL;

export default {
  components: {
    Cart,
    Like,
    Temp,
    Buy,
    Sell,
    Review,
    Mypost,
  },
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

    this.authUser();
  },
  watch: {
    password: function (v) {
      this.checkForm();
    },
    passwordconfirm: function (v) {
      this.checkForm();
    },
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
          this.email = response.data.email;
          this.checkType = response.data.checkType;
          this.getuser();
          this.postCount();
          this.likeCount();
          this.cartCount();
          this.countReview();
          this.countBuy();
          this.countSell();
          this.countTemp();
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    postCount() {
      axios
        .get(`${baseURL}/post/count/mypost/${this.email}`)
        .then((res) => {
          this.myposts = res.data;
          // this.checkPage();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    likeCount() {
      axios
        .get(`${baseURL}/like/count/${this.email}`)
        .then((res) => {
          this.mylikes = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    cartCount() {
      axios
        .get(`${baseURL}/cart/count/${this.email}`)
        .then((res) => {
          this.mycarts = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    countReview() {
      axios
        .get(`${baseURL}/review/count/listbyemail/${this.email}`)
        .then((response) => {
          this.myreviews = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    countBuy() {
      axios
        .get(`${baseURL}/purchase/count/list/${this.email}`)
        .then((res) => {
          this.buycounts = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    countSell() {
      axios
        .get(`${baseURL}/purchase/count/sellList/${this.email}`)
        .then((res) => {
          this.sellcounts = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    countTemp() {
      axios
        .get(`${baseURL}/temp/count/list/${this.email}`)
        .then((res) => {
          this.tempcounts = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    makeimgurl(imgurl) {
      var url = "../../../contents/" + imgurl;
      return url;
    },
    getuser() {
      axios
        .get(`${baseURL}/account/viewInfo/${this.email}`)
        .then((response) => {
          this.name = response.data.name;
          this.nickname = response.data.nickname;
          this.imgurl = response.data.imgurl;
        })
        .catch((err) => {
          this.$router.push({
            name: "Params",
            params: { name: err.response.status },
          });
        });
    },
    passwordModify() {
      this.pwvalidated = 1;
    },
    cancel() {
      this.pwvalidated = 0;
    },
    checkForm() {
      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문,숫자 포함 8 자리이상이어야 합니다.";
      else this.error.password = false;
      if (
        this.passwordconfirm.length > 0 &&
        this.passwordconfirm != this.password
      )
        this.error.passwordconfirm = "비밀번호를 다시 확인해주세요.";
      else this.error.passwordconfirm = false;
    },
    gomodify() {
      this.validated = 0;
      console.log(this.validated);
    },
    deluser() {
      Swal.fire({
        width: 350,
        text: "삭제하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: '<a style="font-size:1rem; color:black">Delete</a>',
        cancelButtonText: '<a style="font-size:1rem; color:black">Cancel</a>',
      }).then((result) => {
        if (result.value) {
          const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 1000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener("mouseenter", Swal.stopTimer);
              toast.addEventListener("mouseleave", Swal.resumeTimer);
            },
          });
          Toast.fire({
            icon: "success",
            title: "계정 탈퇴가 완료되었습니다.",
          });
          axios
            .delete(`${baseURL}/account/delete/${this.email}`)
            .then((response) => {
              this.$cookies.remove("Auth-Token");
              this.$router.push("/");
              this.$router.go();
            })
            .catch((err) => {
              console.log(err);
            });
        }
      });
    },
    modify() {
      let { email, nickname, password, name, imgurl } = this;
      let data = {
        email,
        nickname,
        password,
        name,
        imgurl,
      };
      this.fileUpload();
      axios
        .put(`${baseURL}/account/modify/${this.pwvalidated}`, data)
        .then((response) => {
          const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 1000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener("mouseenter", Swal.stopTimer);
              toast.addEventListener("mouseleave", Swal.resumeTimer);
            },
          });
          Toast.fire({
            icon: "success",
            title: "수정되었습니다.",
          });

          this.$router.push("/user/info");
          this.$router.go();
        })
        .catch(() => {
          alert("정보를 입력하세요.");
          // this.$router.push({name: 'Params', params: {name: err.response.status}});
        });
    },

    fileUpload: function () {
      var formData = new FormData();
      const file = this.$refs.file.files[0];
      if(file.size >= 1048576) {
        Swal.fire({
          width:350,
          icon: 'error',
          text: '업로드 파일 크기를 초과하였습니다!',
        })
      }
      if(file != null) {
        formData.append("file", file);
        axios
          .post(`${baseURL}/account/file/${this.email}`, formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then(function (response) {})
          .catch(function (error) {
            console.log(error);
          });
      }
    },

    onClickImageUpload() {
      this.$refs.file.click();
    },
    onChangeImages(e) {
      const file = e.target.files[0];
      this.tempimg = URL.createObjectURL(file);
      this.tempcheck = true;
      // var img = new Image(file);
      // img = e.target.files[0];
      // this.createImage(img);
    },
    // createImage(file) {
    //   this.imgurl = new Image();
    //   var reader = new FileReader();
    //   reader.onload = (e) => {
    //     this.imgurl = e.target.result;
    //   };
    // },
    modifyCancel() {
      this.validated = !this.validated;
    },
  },
  data: () => {
    return {
      email: "",
      name: "",
      nickname: "",
      password: "",
      passwordconfirm: "",
      passwordSchema: new PV(),
      error: {
        password: false,
        passwordconfirm: false,
      },
      isTerm: false,
      passwordType: "password",
      passwordConfirmType: "password",
      imgurl: null,
      validated: 1,
      pwvalidated: 0,
      checkType: "",
      tempimg: "",
      tempcheck: false,
      myposts: "",
      mylikes: "",
      mycarts: "",
      myreviews: "",
      buycounts: "",
      sellcounts: "",
      tempcounts: "",
    };
  },
};
</script>

<style scoped>
</style>