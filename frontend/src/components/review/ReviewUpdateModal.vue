<template>
  <div
    class="modal fade"
    id="reviewUpdate"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">
            <i class="fas fa-user-edit">
              <small
                class="ml-2"
                style="font-weight:bold"
              >{{this.reviewUpdate.nickname}}님! 수정 내용을 입력해주세요.</small>
            </i>
          </h5>
          <button
            @click="reviewClose"
            type="button"
            class="close"
            data-dismiss="modal"
            aria-label="Close"
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <!-- img upload -->
          <div class="form-group">
            <div class align="left">
              <img
                class="card-img mb-2"
                v-if="this.reviewUpdate.img && !tempcheck"
                :src="makeimgurl(this.reviewUpdate.img)"
                style="height: 16rem; width:100%;"
              />
              <img
                class="card-img mb-2"
                v-if="this.reviewUpdate.img && tempcheck"
                :src="tempimg"
                style="height: 16rem; width:100%;"
              />
              <button
                type="button"
                class="btn btn-primary btn-sm"
                @click="onClickImageUpload"
              >이미지 업로드</button>
            </div>
            <input ref="file" type="file" hidden @change="onChangeImages" />
            <small
              v-if="!this.reviewUpdate.img"
              class="form-text text-muted d-flex"
            >원하는 사진을 업로드해주세요.</small>
            <small
              v-if="this.reviewUpdate.img"
              class="form-text text-muted d-flex"
            >이미지 수정을 원하시면 업로드 버튼을 눌러주세요.</small>
          </div>

          <!-- 제목 -->
          <div class="form-group">
            <label for="title" class="d-flex">제목을 입력해주세요.</label>
            <input type="text" class="form-control" id="title" v-model="reviewUpdate.title" />
            <small id="titlehelp" class="form-text text-muted d-flex">수정하실 제목을 입력해주세요.</small>
          </div>

          <!-- content -->
          <div class="form-group">
            <label for="content" class="d-flex">내용을 입력해주세요.</label>
            <textarea type="email" class="form-control" id="content" v-model="reviewUpdate.content"></textarea>
            <small id="titlehelp" class="form-text text-muted d-flex">수정하실 내용을 남겨주세요.</small>
          </div>

          <!-- rating -->
          <div>
            <b-form-rating v-model="reviewUpdate.star" variant="primary"></b-form-rating>
            <small class="d-flex">수정하실 별점을 남겨주세요.</small>
          </div>
        </div>
        <div class="modal-footer">
          <button
            @click="reviewClose"
            type="button"
            class="btn btn-secondary"
            data-dismiss="modal"
          >취소</button>
          <button @click="reviewModify" type="button" class="btn btn-primary">수정</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const baseURL = process.env.VUE_APP_BACKURL;

export default {
  data() {
    return {
      reviewUpdate: [],
      tempimg:'',
      tempcheck:false,
    };
  },
  props: {
    reviewInfo: Object,
  },
  methods: {
    fetchReviewDetail() {
      axios
        .get(
          `${baseURL}/review/reviewDetail/${this.reviewInfo.pid}/${this.reviewInfo.rvid}`
        )
        .then((response) => {
          this.reviewUpdate = response.data;

        })
        .catch((error) => {
          console.log(error);
        });
    },
    makeimgurl(imgurl) {
      var url = "../../../contents/"+imgurl;
      return url;
    },
    fileUpload(rvid) {
    var formData = new FormData();
    const file = this.$refs.file.files[0];
    if(file != null) {
      formData.append("file", file);
      axios.post(`${baseURL}/review/file/${rvid}`
          ,formData
          , {
            headers: {
              'Content-Type': 'multipart/form-data'
              }
          }
          )
        .then(function (response) {
          
          })
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
    },
    reviewClose() {
      this.$emit("review-close");
    },
    reviewModify() {
      Swal.fire({
        width: 300,
        text: "후기를 수정하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: '<a style="font-size:1rem; color:black">Modify</a>',
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

          axios
            .put(`${baseURL}/review/modify`, this.reviewUpdate)
            .then((response) => {
              this.fileUpload(response.data.rvid);

              setTimeout(() => {
                this.$router.go();
              }, 1000);
            })
            .catch((error) => {
              console.log(error);
            });
          Toast.fire({
            icon: "success",
            title: "후기 수정 완료!",
          });
        }
      });
    },
  },
  created() {
    this.fetchReviewDetail();
  },
};
</script>

<style>
</style>