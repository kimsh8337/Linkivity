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
                  v-if="PostUpdate.imgurl"
                  :src="PostUpdate.imgurl"
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
                v-if="!this.PostUpdate.imgurl"
                class="form-text text-muted d-flex"
              >원하는 사진을 업로드해주세요.</small>
              <small
                v-if="this.PostUpdate.imgurl"
                class="form-text text-muted d-flex"
              >이미지 수정을 원하시면 업로드 버튼을 눌러주세요.</small>
            </div>

            <div class="col-md-7">
              <div class="card-body" style="padding: 0 0 0 20px">
                <div class="text">
                  <!-- 제목 -->
                  <div class="form-group">
                    <label class="d-flex">Title</label>
                    <input type="text" class="form-control" id="title" v-model="PostUpdate.title" />
                    <label class="d-flex justify-content-start">Activity</label>
                    <input
                      type="text"
                      class="form-control"
                      id="activity"
                      v-model="PostUpdate.activity"
                    />
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
                        v-model="PostUpdate.sdate"
                        class="col-md-6 mr-1"
                      ></b-form-datepicker>
                      <b-form-datepicker id="edate" v-model="PostUpdate.edate" class="col-md-6"></b-form-datepicker>
                    </div>
                    <small class="form-text text-muted d-flex">상품 유효기간을 지정해주세요.</small>
                  </div>

                  <!-- 이용 가격 -->
                  <div class="form-group mb-0">
                    <label class="d-flex justify-content-start">Price</label>
                    <input type="text" class="form-control" id="price" v-model="PostUpdate.price" />
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
          <select class="form-control" id="place" v-model="PostUpdate.place">
            <option value="ground">Ground</option>
            <option value="water">Water</option>
            <option value="sky">Sky</option>
          </select>
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
                v-model="PostUpdate.spring"
              />
              <label class="form-check-label" for="spring">Spring</label>
            </div>
            <div class="form-check form-check-inline ml-3">
              <input
                class="form-check-input"
                type="checkbox"
                id="summer"
                value="summer"
                v-model="PostUpdate.summer"
              />
              <label class="form-check-label" for="summer">Summer</label>
            </div>
            <div class="form-check form-check-inline ml-3">
              <input
                class="form-check-input"
                type="checkbox"
                id="autumn"
                value="autumn"
                v-model="PostUpdate.autumn"
              />
              <label class="form-check-label" for="autumn">Autumn</label>
            </div>
            <div class="form-check form-check-inline ml-3">
              <input
                class="form-check-input"
                type="checkbox"
                id="winter"
                value="winter"
                v-model="PostUpdate.winter"
              />
              <label class="form-check-label" for="winter">Winter</label>
            </div>
          </div>
        </div>
      </div>
      <div>
        <!-- 업체 정보 -->
        <!-- <h4 class="d-flex mb-2" style="font-weight:bold">업체정보</h4> -->
        <div class="form-group">
          <label class="d-flex justify-content-start">Corporation-Detail</label>
          <textarea class="form-control" id="company-information" v-model="PostUpdate.companyInfo"></textarea>
        </div>

        <hr />

        <!-- 상세 정보 -->
        <!-- <h4 class="d-flex mb-2" style="font-weight:bold">상세정보</h4> -->
        <div class="form-group">
          <label class="d-flex justify-content-start">Detail-Info</label>
          <Editor
            ref="toastuiEditor"
            v-if="PostUpdate.detail != null"
            :initialValue="PostUpdate.detail"
          />
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
          v-model="hashTag"
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
          class="btn btn-primary d-flex justify-content-start"
          @click="modify"
        >제출</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
import "codemirror/lib/codemirror.css";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";

const baseURL = process.env.VUE_APP_BACKURL;

export default {
  components: {
    Editor,
  },
  data() {
    return {
      PostUpdate: [],
      pid: "",
      seasons: [],
      hashTag: [],
      addr1:"",
      addr2:"",
      addr3:"",
      // Instance_Date: []
    };
  },
  methods: {
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
    fetchDetail: function () {
      axios
        .get(`${baseURL}/post/detail/${this.pid}`)
        .then((response) => {
          this.PostUpdate = response.data;
        })
        .catch((error) => {
          console.log(error.response.data);
        });
    },
    taglist() {
      axios
        .get(`${baseURL}/tag/list/${this.pid}`)
        .then((res) => {
          this.hashTag = res.data;
          console.log(this.hashTag)
        })
        .catch((err) => {
          console.log(err);
        });
    },
    modify: function () {
      this.PostUpdate.detail = this.$refs.toastuiEditor.invoke("getMarkdown");
      this.PostUpdate.location = this.addr2 + " " + this.addr3;
      if(this.PostUpdate.spring == true){
        this.PostUpdate.spring = 1;
      }
      if(this.PostUpdate.summer == true){
        this.PostUpdate.summer = 1;
      }
      if(this.PostUpdate.autumn == true){
        this.PostUpdate.autumn = 1;
      }
      if(this.PostUpdate.winter == true){
        this.PostUpdate.winter = 1;
      }

      Swal.fire({
        width: 350,
        text: "수정하시겠습니까?",
        icon: "question",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: '<a style="font-size:1rem; color:black">Update</a>',
        cancelButtonText: '<a style="font-size:1rem; color:black">Cancel</a>',
      }).then((result) => {
        if (result.value) {
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
          Toast.fire({
            icon: "success",
            title: "Update Completed!",
          });
          axios
            .put(`${baseURL}/post/modify`, this.PostUpdate)
            .then(() => {
              this.$router.push({
                name: "PostListDetail",
                params: { ID: this.pid },
              });
            })
            .catch((error) => {
              console.log(error.response.data);
            });
        }
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
      this.PostUpdate.imgurl = new Image();
      var reader = new FileReader();
      reader.onload = (e) => {
        this.PostUpdate.imgurl = e.target.result;
      };
      reader.readAsDataURL(file);
    },
  },
  created() {
    this.pid = this.$route.params.ID;
    this.fetchDetail();
    this.taglist();
  },
};
</script>

<style>
</style>