<template>
  <div class="container col-md-6">
    <!-- 이름 입력칸  -->
    <div class="form-group">
      <label for="exampleInputEmail1" class="d-flex">
        <i class="fas fa-user"> Name</i>
      </label>
      <input v-model="name" type="text" class="form-control" id="name" aria-describedby="emailHelp" />
      <small id="emailHelp" class="form-text text-muted d-flex" v-if="!error.name">이름을 입력하세요.</small>
      <span class="error-text d-flex mt-1" v-if="error.name" style="color:crimson;">{{error.name}}</span>
    </div>

    <!-- 닉네임 입력칸 -->
    <div class="form-group">
      <label for="exampleInputPassword1" class="d-flex">
        <i class="fas fa-smile"> Nickname</i>
      </label>
      <input v-model="nickname" type="text" class="form-control" id="nickname" />
      <small id="emailHelp" class="form-text text-muted d-flex" v-if="!error.nickname">닉네임을 입력하세요.</small>
      <span
        class="error-text d-flex mt-1"
        v-if="error.nickname"
        style="color:crimson;"
      >{{error.nickname}}</span>
    </div>

    <!-- email 입력칸 -->
    <div class="form-group">
      <label for="exampleInputPassword1" class="d-flex">
        <i class="fas fa-at"> E-mail</i>
      </label>
      <input
        v-model="email"
        v-bind:class="{error : error.email, complete:!error.email&&email.length!==0}"
        type="email"
        class="form-control"
        id="email"
      />
      <small id="emailHelp" class="form-text text-muted d-flex" v-if="!error.email">이메일을 입력하세요.</small>
      <small
        class="error-text d-flex mt-1"
        v-if="error.email"
        style="color:crimson;"
      >{{error.email}}</small>
    </div>

    <!-- 비밀번호 입력칸 -->
    <div class="form-group">
      <label for="exampleInputPassword1" class="d-flex">
        <i class="fas fa-eye"> Password</i>
      </label>
      <input
        v-model="password"
        v-bind:class="{error : error.password, complete:!error.password&&password.length!==0}"
        :type="passwordType"
        class="form-control"
        id="password"
      />
      <small id="emailHelp" class="form-text text-muted d-flex" v-if="!error.password">비밀번호를 입력하세요.</small>
      <span :class="{active : passwordType==='text'}">
        <span
          class="error-text d-flex mt-1"
          v-if="error.password"
          style="color:crimson;"
        >{{error.password}}</span>
      </span>
    </div>

    <!-- 비밀번호 확인 입력칸 -->
    <div class="form-group">
      <label for="exampleInputPassword1" class="d-flex">
        <i class="fas fa-eye"> Password Confirm</i>
      </label>
      <input
        v-model="passwordconfirm"
        v-bind:class="{error : error.passwordconfirm, complete:!error.passwordconfirm&&passwordconfirm.length!==0}"
        :type="passwordConfirmType"
        class="form-control"
        id="passwordconfirm"
      />
      <small
        id="emailHelp"
        class="form-text text-muted d-flex"
        v-if="!error.passwordconfirm"
      >비밀번호를 다시 입력하세요.</small>
      <span :class="{active : passwordConfirmType==='text'}">
        <span
          class="error-text d-flex mt-1"
          v-if="error.passwordconfirm"
          style="color:crimson;"
        >{{error.passwordconfirm}}</span>
      </span>
    </div>

    <!-- 주소 입력칸 -->
    <div class="form-group">
      <label for="exampleInputEmail1" class="d-flex">
        <i class="fas fa-map-marker-alt"> Address</i>
      </label>
      <div>
        <div class="d-flex mb-1">
          <input
            type="text"
            class="form-control"
            v-model="addr1"
            style="width:200px;"
            placeholder="우편번호"
          />
          <button type="button" class="btn btn-primary btn-sm ml-1" @click="Search">우편번호 찾기</button>
        </div>
        <input type="text" class="form-control mb-1" v-model="addr2" placeholder="주소" readonly />
        <input type="text" class="form-control mb-1" v-model="addr3" placeholder="상세주소" />
      </div>
      <span
        class="error-text d-flex mt-1"
        v-if="error.clocation"
        style="color:crimson;"
      >{{error.clocation}}</span>
    </div>
    <!-- 핸드폰 번호 입력칸 -->
    <div class="form-group">
      <label for="exampleInputEmail1" class="d-flex">
        <i class="fas fa-phone-square-alt"> Phone Number</i>
      </label>
      <input
        v-model="cphone"
        type="text"
        class="form-control"
        id="phone"
        aria-describedby="emailHelp"
      />
      <small id="emailHelp" class="form-text text-muted d-flex" v-if="!error.cphone">연락처를 입력하세요.</small>
      <span
        class="error-text d-flex mt-1"
        v-if="error.cphone"
        style="color:crimson;"
      >{{error.cphone}}</span>
    </div>

    <!-- img upload -->
    <label class="d-flex" v-if="!this.imgurl"><i class="fas fa-images"> 프로필 사진을 등록해주세요.</i></label>
    <button type="button" class="btn btn-primary d-flex" @click="onClickImageUpload">이미지 업로드</button>
    <div class="col-md-8 p-0" align="left">
      <input ref="imageInput" type="file" hidden @change="onChangeImages" />
      <img
        class="card-img mb-2"
        style="height: 15rem; width: 15rem;"
        v-if="this.imgurl"
        :src="this.imgurl"
      />
    </div>

    <!-- 제출 버튼 -->
    <button @click="join" class="btn d-flex ml-auto">
      <i class="fas fa-pen mr-1"></i>
      <span>작성완료</span>
    </button>
  </div>
</template>

<script>
import PV from "password-validator";
import * as EmailValidator from "email-validator";

import axios from "axios";

const baseURL = process.env.VUE_APP_BACKURL;

export default {
  props: {
    checkType: String,
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
    this.checkForm();
  },
  watch: {
    password: function (v) {
      this.checkForm();
    },
    email: function (v) {
      this.checkForm();
    },
    passwordconfirm: function (v) {
      this.checkForm();
    },
    nickname: function (v) {
      this.checkForm();
    },
    addr2: function (v) {
      this.addrSum();
    },
  },
  methods: {
    addrSum() {
      this.clocation = this.addr2 + " " + this.addr3;
    },
    checkForm() {
      if (this.nickname.length > 0) {
        axios
          .get(`${baseURL}/account/checkNickname/${this.nickname}`)
          .then((response) => {
            this.error.nickname = response.data;
          })
          .catch(() => {
            alert("에러");
          });
      } else if (this.nickname.length != 0) this.error.nickname = false;
      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else if (this.email.length > 0 && EmailValidator.validate(this.email)) {
        axios
          .get(`${baseURL}/account/checkEmail/${this.email}`)
          .then((response) => {
            this.error.email = response.data;
          })
          .catch(() => {
            alert("에러");
          });
      } else this.error.email = false;

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
    join() {
      let check = 0;
      if (this.name.length == 0) {
        this.error.name = "이름은 빈칸일 수 없습니다.";
        check = 1;
      } else this.error.name = false;
      if (this.nickname.length == 0) {
        this.error.nickname = "닉네임은 빈칸일 수 없습니다.";
        check = 1;
      } else this.error.nickname = false;
      if (this.email.length == 0) {
        this.error.email = "이메일은 빈칸일 수 없습니다.";
        check = 1;
      } else this.error.email = false;
      if (this.password.length == 0) {
        this.error.password = "비밀번호는 빈칸일 수 없습니다.";
        check = 1;
      } else this.error.password = false;
      if (this.passwordconfirm.length == 0) {
        this.error.passwordconfirm = "비밀번호 확인은 빈칸일 수 없습니다.";
        check = 1;
      } else this.error.passwordconfirm = false;
      if (this.addr2.length == 0 || this.addr3.length == 0) {
        this.error.clocation = "주소는 빈칸일 수 없습니다.";
        check = 1;
      } else this.error.clocation = false;
      if (this.cphone.length == 0) {
        this.error.cphone = "연락처는 빈칸일 수 없습니다.";
        check = 1;
      } else this.error.cphone = false;
      if (check == 1) {
        alert("정보를 모두 입력해주세요.");
        return;
      }
      this.$emit(
        "join-create-business",
        this.email,
        this.nickname,
        this.password,
        this.name,
        this.checkType,
        this.imgurl,
        this.clocation,
        this.cphone
      );
    },
    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    onChangeImages(e) {
      const file = e.target.files[0];
      var img = new Image(file);
      img = e.target.files[0];
      this.createImage(img);
      // this.imgurl = URL.createObjectURL(file);
    },
    createImage(file) {
      this.imgurl = new Image();
      var reader = new FileReader();
      reader.onload = (e) => {
        this.imgurl = e.target.result;
      };
      reader.readAsDataURL(file);
    },
    Search() {
      let x = this;
      new daum.Postcode({
        oncomplete: function (data) {
          x.addr1 = data.zonecode;
          x.addr2 = data.address;
          x.addr3 = data.buildingName;
        },
      }).open();
    },
  },
  data() {
    return {
      email: "",
      name: "",
      nickname: "",
      password: "",
      passwordconfirm: "",
      imgurl: "",
      clocation: "",
      cphone: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        password: false,
        nickname: false,
        passwordconfirm: false,
        clocation: false,
        cphone: false,
      },
      addr1: "",
      addr2: "",
      addr3: "",
      isTerm: false,
      passwordType: "password",
      passwordConfirmType: "password",
    };
  },
};
</script>

<style></style>
