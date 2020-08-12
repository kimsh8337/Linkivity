<template>
  <div class="modal fade" id="reviewWrite" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel"><i class="fas fa-user-edit"><small class="ml-2" style="font-weight:bold">{{this.reviewCreate.nickname}}님!! 솔직한 리뷰를 남겨주세요.</small></i></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">

        <!-- img upload -->
        <div class="form-group">
          <!-- <label class="d-flex">Image</label> -->
          <div class="" align="left">
            <img
              class="card-img mb-2"
              v-if="this.reviewCreate.img"
              :src="this.reviewCreate.img"
              style="height: 16rem; width:100%;"
            />
            <button type="button" class="btn btn-primary btn-sm" @click="onClickImageUpload">이미지 업로드</button>
          </div>
          <input ref="imageInput" type="file" hidden @change="onChangeImages" />
          <small v-if="!this.reviewCreate.img" class="form-text text-muted d-flex">원하는 사진을 업로드해주세요.</small>
          <small
            v-if="this.reviewCreate.img"
            class="form-text text-muted d-flex"
          >이미지 수정을 원하시면 업로드 버튼을 눌러주세요.</small>
        </div>

        <!-- 제목 -->
        <div class="form-group">
          <label for="title" class="d-flex">제목을 입력해주세요.</label>
          <input type="text" class="form-control" id="title" v-model="reviewCreate.title">
          <small id="titlehelp" class="form-text text-muted d-flex">남길 후기의 제목을 입력해주세요.</small>
        </div>

        <!-- content -->
        <div class="form-group">
          <label for="content" class="d-flex">내용을 입력해주세요.</label>
          <textarea type="email" class="form-control" id="content" v-model="reviewCreate.content"></textarea>
          <small id="titlehelp" class="form-text text-muted d-flex">액티비티를 즐긴 소감을 남겨주세요.</small>
        </div>

        <!-- rating -->
        <div>
          <b-form-rating v-model="reviewCreate.star" variant="danger"></b-form-rating>
          <small class="d-flex">액티비티에 대한 별점을 남겨주세요.</small>
        </div>

      </div>
      <div class="modal-footer">
        <button @click="reviewRegist" type="button" class="btn btn-success" data-dismiss="modal"><i class="fas fa-plus-circle">후기 등록</i></button>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'

const baseURL = "http://localhost:8080";

export default {
  data() {
    return {
      reviewCreate: {
        pid: 0,
        email: "",
        img: "",
        title: "",
        content: "",
        star: 0,
        proimg: "",
        nickname: "",
      },
    }
  },
  props: {
    pid: [Number,String],
    email: String,
  },
  created() {
    if(this.$cookies.get("Auth-Token")!=null)
    this.authUser()
  },
  methods:{
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
          this.reviewCreate.email = response.data.email;
          this.reviewCreate.pid = this.pid
          this.reviewCreate.nickname = response.data.nickname
          this.getProfileImg()
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    reviewRegist() {
      axios.post(`${baseURL}/review/regist`,this.reviewCreate)
        .then((response) => {
          const Toast = Swal.mixin({
            toast: true,
          position: 'top-end',
          showConfirmButton: false,
          timer: 1000,
          timerProgressBar: true,
          onOpen: (toast) => {
            toast.addEventListener('mouseenter', Swal.stopTimer)
            toast.addEventListener('mouseleave', Swal.resumeTimer)
          }
        })
        setTimeout(() => {
          this.$router.go()
        },1000)
        Toast.fire({
          icon: 'success',
          title: '후기 등록이 완료되었습니다!!'
        })
        }).catch((error) => {
          console.log(error)
        })
    },
    getProfileImg() {
        axios.get(`${baseURL}/account/getImg/${this.reviewCreate.email}`)
            .then((response) => {
              this.reviewCreate.proimg = response.data
            }).catch((error) => {
                console.log(error)
            })
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
      this.reviewCreate.img = new Image();
      var reader = new FileReader();
      reader.onload = (e) => {
        this.reviewCreate.img = e.target.result;
      };
      reader.readAsDataURL(file);
    },
  }
}
</script>

<style>

</style>