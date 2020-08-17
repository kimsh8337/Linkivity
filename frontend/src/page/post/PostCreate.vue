<template>
  <div class="container col-sm-12 col-md-12 col-lg-12 p-0">
    <!-- background image -->
    <div class="post-img" style="display:block;"></div>

    <div class="container col-md-7" style="margin-top: 100px">
      <div class="column">
        <div class="card mt-5 mb-3" style="max-width: 100%;">
          <div class="row no-gutters">
            <!-- 이미지 삽입 -->
            <div class="col-md-5">
              <div class="col-md-8 p-0" align="left">
                <img
                  class="card-img mb-2"
                  v-if="this.PostCreate.imgurl"
                  :src="this.PostCreate.imgurl"
                  style="height: 16rem; width:100%"
                />
                <button
                  type="button"
                  class="btn btn-primary btn-sm"
                  @click="onClickImageUpload"
                >이미지 업로드</button>
              </div>
              <input ref="imageInput" type="file" hidden @change="onChangeImages" />
              <small
                v-if="!this.PostCreate.imgurl"
                class="form-text text-muted d-flex"
              >원하는 사진을 업로드해주세요.</small>
              <small
                v-if="this.PostCreate.imgurl"
                class="form-text text-muted d-flex"
              >이미지 수정을 원하시면 업로드 버튼을 눌러주세요.</small>
            </div>

            <div class="col-md-7">
              <div class="card-body" style="padding: 0 0 0 20px">
                <div class="text">
                  <!-- 제목 -->
                  <div class="form-group">
                    <label class="d-flex">Title</label>
                    <input type="text" class="form-control" id="title" v-model="PostCreate.title" />
                    <small class="form-text text-muted d-flex" v-if="!error.title">상품명을 입력하세요.</small>
                    <small
                      class="form-text d-flex"
                      style="color:red;"
                      v-if="error.title"
                    >{{error.title}}</small>
                    <label class="d-flex justify-content-start">Activity</label>
                    <input
                      type="text"
                      class="form-control"
                      id="activity"
                      v-model="PostCreate.activity"
                    />
                    <small class="form-text text-muted d-flex" v-if="!error.activity">활동명을 입력하세요.</small>
                    <small
                      class="form-text d-flex"
                      style="color:red;"
                      v-if="error.activity"
                    >{{error.activity}}</small>
                  </div>

                  <!-- 사용 기간 -->
                  <div class="form-group">
                    <label class="d-flex justify-content-start mb-0">Expiration-Date</label>
                    <div class="d-flex justify-content-between">
                      <small class="form-text text-muted" style="margin-right:auto;">시작일</small>
                      <br />
                      <small class="form-text text-muted" style="margin-right:auto;">마감일</small>
                    </div>
                    <div class="d-flex justify-content-between">
                      <b-form-datepicker
                        id="sdate"
                        v-model="PostCreate.sdate"
                        class="col-md-6 mr-1"
                      ></b-form-datepicker>
                      <b-form-datepicker id="edate" v-model="PostCreate.edate" class="col-md-6"></b-form-datepicker>
                    </div>
                    <small class="form-text text-muted d-flex">상품 유효기간을 지정해주세요.</small>
                  </div>

                  <!-- 이용 가격 -->
                  <div class="form-group mb-0">
                    <label class="d-flex justify-content-start">Price</label>
                    <input type="text" class="form-control" id="price" v-model="PostCreate.price" />
                    <small
                      class="form-text text-muted d-flex"
                      v-if="!error.price && !error.priceint"
                    >가격을 입력하세요.</small>
                    <small
                      class="form-text d-flex"
                      style="color:red;"
                      v-if="error.price"
                    >{{error.price}}</small>
                    <small
                      class="form-text d-flex"
                      style="color:red;"
                      v-if="error.priceint"
                    >{{error.priceint}}</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <hr class="mt-0" />
      <!-- season, place check badge -->
      <div class="d-flex justify-content-between">
        <div class="form-group" style="width:23rem; ">
          <label class="d-flex justify-content-start">Field</label>
          <select class="form-control" id="place" v-model="PostCreate.place">
            <option value="ground">Ground</option>
            <option value="water">Water</option>
            <option value="sky">Sky</option>
          </select>
          <small class="form-text text-muted d-flex" v-if="!error.place">필드를 선택하세요.</small>
          <small class="form-text d-flex" style="color:red;" v-if="error.place">{{error.place}}</small>
        </div>
        <div class="form-group">
          <label class="d-flex justify-content-start">Seasons</label>
          <div class="d-flex">
            <div class="form-check form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="spring"
                value="spring"
                v-model="seasons"
              />
              <label class="form-check-label" for="spring">Spring</label>
            </div>
            <div class="form-check form-check-inline ml-3">
              <input
                class="form-check-input"
                type="checkbox"
                id="summer"
                value="summer"
                v-model="seasons"
              />
              <label class="form-check-label" for="summer">Summer</label>
            </div>
            <div class="form-check form-check-inline ml-3">
              <input
                class="form-check-input"
                type="checkbox"
                id="autumn"
                value="autumn"
                v-model="seasons"
              />
              <label class="form-check-label" for="autumn">Autumn</label>
            </div>
            <div class="form-check form-check-inline ml-3">
              <input
                class="form-check-input"
                type="checkbox"
                id="winter"
                value="winter"
                v-model="seasons"
              />
              <label class="form-check-label" for="winter">Winter</label>
            </div>
          </div>
          <small class="form-text text-muted d-flex" v-if="!error.seasons">상품 이용 계절을 선택하세요.(중복가능)</small>
          <small class="form-text d-flex" style="color:red;" v-if="error.seasons">{{error.seasons}}</small>
        </div>
      </div>
      <div>
        <!-- 업체 정보 -->
        <!-- <h4 class="d-flex mb-2" style="font-weight:bold">업체정보</h4> -->
        <div class="form-group">
          <label class="d-flex justify-content-start">Corporation-Detail</label>
          <textarea class="form-control" id="company-information" v-model="PostCreate.companyInfo"></textarea>
          <small class="form-text text-muted d-flex" v-if="!error.companyInfo">업체 정보를 입력하세요.</small>
          <small
            class="form-text d-flex"
            style="color:red;"
            v-if="error.companyInfo"
          >{{error.companyInfo}}</small>
        </div>

        <hr />

        <!-- 상세 정보 -->
        <!-- <h4 class="d-flex mb-2" style="font-weight:bold">상세정보</h4> -->
        <div class="form-group">
          <label class="d-flex justify-content-start">Detail-Info</label>
          <Editor ref="toastuiEditor" />
          <small class="form-text text-muted d-flex" v-if="!error.detail">상품 상세정보를 입력하세요.</small>
          <small class="form-text d-flex" style="color:red;" v-if="error.detail">{{error.detail}}</small>
        </div>
        <hr />
        <!-- 지도 -->
        <!-- <p class="d-flex" style="font-size:1.5rem; font-weight:bold;">위치</p>
        <div id="map" style="max-width: 100%; height:300px;"></div>-->
        <div>
          <!-- <h4 class="d-flex mb-2" style="font-weight:bold">위치</h4> -->
          <label class="d-flex justify-content-start">Address</label>
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

        <small class="form-text text-muted d-flex">주소를 입력하세요.</small>

        <hr />

        <!-- HasTag -->
        <label for="tags-basic" class="d-flex mt-3"># HASHTAG</label>
        <b-form-tags
          input-id="tags-pills"
          v-model="tagValue"
          tag-variant="primary"
          tag-pills
          size="md"
          separator=" "
          placeholder="원하는 태그를 입력해주세요."
          class="mb-2"
        ></b-form-tags>
        <small class="form-text text-muted d-flex">해시태그를 입력해주세요.</small>
      </div>

      <hr class="mt-2" />
      <div class="d-flex justify-content-end mb-5">
        <button
          type="submit"
          class="btn btn-outline mr-1"
          style="font-size: 1rem; color: gray;"
          @click="tempSave"
        >임시저장</button>
        <button
          type="submit"
          class="btn btn-outline pr-0"
          style="font-size: 1.1rem;"
          @click="regist"
        >
          <i class="fas fa-pen mr-1"></i>등록
        </button>
      </div>
    </div>
  </div>
</template>

<script>
const baseURL = process.env.VUE_APP_BACKURL;

import axios from "axios";
import "../../assets/css/postcreate.css";
import Swal from "sweetalert2";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";

export default {
  components: {
    Editor,
  },
  data() {
    return {
      //
      PostCreate: {
        email: "",
        title: "",
        location: "",
        imgurl: "",
        price: "",
        sdate: "",
        edate: "",
        companyInfo: "",
        detail: "",
        activity: "",
        spring: 0,
        summer: 0,
        autumn: 0,
        winter: 0,
        place: "",
      },
      error: {
        activity: false,
        detail: false,
        price: false,
        companyInfo: false,
        title: false,
        priceint: false,
        location: false,
        seasons: false,
        place: false,
      },
      addr1: "",
      addr2: "",
      addr3: "",
      seasons: [],
      tagValue: [],
    };
  },
  watch: {
    PostCreate: {
      handler: function (val) {
        if (!/^[0-9]+$/g.test(val.price) && val.price.length > 0) {
          this.error.priceint = "가격은 숫자만 입력 가능합니다.";
        } else {
          this.error.priceint = false;
        }
      },
      deep: true,
    },
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
          this.PostCreate.email = response.data.email;
        })
        .catch((err) => {
          console.log(err.response);
        });
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
    regist: function () {
      var content = this.$refs.toastuiEditor.invoke("getMarkdown");
      this.PostCreate.detail = content;
      var flag = 0;
      if (this.PostCreate.activity == "") {
        this.error.activity = "활동명은 빈칸일 수 없습니다.";
        flag = 1;
      } else {
        this.error.activity = false;
      }
      if (this.PostCreate.detail == "") {
        this.error.detail = "상품 세부정보는 빈칸일 수 없습니다.";
        flag = 1;
      } else {
        this.error.detail = false;
      }
      if (this.PostCreate.companyInfo == "") {
        this.error.companyInfo = "업체 정보는 빈칸일 수 없습니다.";
        flag = 1;
      } else {
        this.error.companyInfo = false;
      }
      if (this.PostCreate.price == "") {
        this.error.price = "가격은 빈칸일 수 없습니다.";
        flag = 1;
      } else {
        this.error.price = false;
      }
      if (this.PostCreate.title == "") {
        this.error.title = "상품명은 빈칸일 수 없습니다.";
        flag = 1;
      } else {
        this.error.title = false;
      }
      if (this.seasons.length == 0) {
        this.error.seasons = "계절은 하나 이상 선택해야합니다.";
        flag = 1;
      } else {
        this.error.seasons = false;
      }
      if (this.PostCreate.place == "") {
        this.error.place = "필드는 빈칸일 수 없습니다.";
        flag = 1;
      } else {
        this.error.place = false;
      }
      if (flag == 1) {
        alert("정보를 모두 입력해주세요.");
        return;
      }
      this.PostCreate.location = this.addr2 + " " + this.addr3;

      for (var i = 0; i < this.seasons.length; i++) {
        if (this.seasons[i] == "spring") {
          this.PostCreate.spring = 1;
        } else if (this.seasons[i] == "summer") {
          this.PostCreate.summer = 1;
        } else if (this.seasons[i] == "autumn") {
          this.PostCreate.autumn = 1;
        } else if (this.seasons[i] == "winter") {
          this.PostCreate.winter = 1;
        }
      }
      const Toast = Swal.mixin({
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        onOpen: (toast) => {
          toast.addEventListener("mouseenter", Swal.stopTimer);
          toast.addEventListener("mouseleave", Swal.resumeTimer);
        },
      });

      axios
        .post(`${baseURL}/post/regist/${this.tagValue}`, this.PostCreate)
        .then((response) => {
          Toast.fire({
            icon: "success",
            title: "게시물 승인 요청이 완료되었습니다.",
          });
          this.$router.push("/posts");
        })
        .catch((error) => {
          console.log(error);
        });
    },
    tempSave() {
      //임시저장 메소드
      if (this.PostCreate.title == "") {
        this.error.title = "상품명은 빈칸일 수 없습니다.";
        alert("정보를 확인해주세요");
        return;
      }
      axios
        .post(`${baseURL}/temp/regist/${this.tagValue}`, this.PostCreate)
        .then((response) => {
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            // onOpen: (toast) => {
            //   toast.addEventListener('mouseenter', Swal.stopTimer)
            //   toast.addEventListener('mouseleave', Swal.resumeTimer)
            // }
          })

          Toast.fire({
            icon: 'success',
            title: '임시저장 완료!'
          })
          this.$router.push("/posts");
        })
        .catch((error) => {
          console.log(error);
        });
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
      this.PostCreate.imgurl = new Image();
      var reader = new FileReader();
      reader.onload = (e) => {
        this.PostCreate.imgurl = e.target.result;
      };
      reader.readAsDataURL(file);
    },
  },
  created() {
    this.authUser();
  },
};
</script>
<style>
</style>