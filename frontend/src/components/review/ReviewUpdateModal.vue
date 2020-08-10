<template>
  <div class="modal fade" id="reviewUpdate" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        
        {{this.reviewUpdate.pid}}
        {{this.reviewUpdate.rvid}}
         <!-- img upload -->
        <div class="form-group">
          <div class="" align="left">
            <img
              class="card-img mb-2"
              v-if="this.reviewInfo.img"
              :src="this.reviewInfo.img"
              style="height: 16rem; width:100%;"
            />
            <button type="button" class="btn btn-primary btn-sm" @click="onClickImageUpload">이미지 업로드</button>
          </div>
          <input ref="imageInput" type="file" hidden @change="onChangeImages" />
          <small v-if="!this.reviewInfo.img" class="form-text text-muted d-flex">원하는 사진을 업로드해주세요.</small>
          <small
            v-if="this.reviewInfo.img"
            class="form-text text-muted d-flex"
          >이미지 수정을 원하시면 업로드 버튼을 눌러주세요.</small>
        </div>

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
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
            // reviewUpdate: {
            //     pid: "",
            //     email: "",
            //     img: "",
            //     title: "",
            //     content: "",
            //     star: 0,
            //     proimg: "",
            //     nickname: "",
            // },
            reviewUpdate: [],
        }
    },
    props: {
      reviewInfo: Object,
    },
    methods: {
      // authUser() {
      //   axios
      //       .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
      //       .then((response) => {
      //         this.reviewUpdate.email = response.data.email;
      //         this.reviewUpdate.nickname = response.data.nickname;
      //       })
      //       .catch((err) => {
      //         console.log(err.response);
      //       });
      // },
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
        this.reviewUpdate.img = new Image();
        var reader = new FileReader();
        reader.onload = (e) => {
          this.reviewUpdate.img = e.target.result;
        };
        reader.readAsDataURL(file);
      },
    },
    created() {
      // this.authUser()
      this.reviewUpdate = this.reviewInfo
    },
}
</script>

<style>

</style>