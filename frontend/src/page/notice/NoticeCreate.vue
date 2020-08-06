<template>
  <div class="container col-md-8">
    <!-- Title -->
    <div class="form-group mb-4">
        <div class="d-flex justify-content-start">
            <label class="d-flex notice-title-font mr-3">Title</label>
            <small class="form-text notice-content-font text-muted d-flex" v-if="!error.title">제목을 입력하세요.</small>
        </div>
      <input type="text" class="form-control" id="title" v-model="NoticeCreate.title" />
      <small class="form-text d-flex" style="color:red;" v-if="error.title">{{error.title}}</small>
    </div>

    <!-- Detail-Info -->
    <div class="form-group">
        <div class="d-flex justify-content-start">
            <label class="d-flex notice-title-font mr-3">Detail-Info</label>
            <small class="form-text notice-content-font text-muted d-flex" v-if="!error.content">내용을 입력하세요.</small>
        </div>
      <textarea class="form-control" style="height:30rem;" id="detail" v-model="NoticeCreate.content"></textarea>
      <small class="form-text d-flex" style="color:red;" v-if="error.detail">{{error.detail}}</small>
    </div>

    <!-- Button -->
    <div class="d-flex justify-content-end mb-5">
      <button type="submit" class="btn btn-outline pr-0" style="font-size: 1.1rem;" @click="noticeregist">
        <i class="fas fa-pen mr-1"></i>등록
      </button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

import Swal from "sweetalert2";

const baseURL = "http://localhost:8080";

export default {
    data(){
        return{
            page:1,
            NoticeCreate:{
                title:'',
                content:'',
            },
            error:{
                title:false,
                content:false,
            },
        }
    },
    created(){
        this.superadviser()
    },
    methods:{
        superadviser(){
            axios
              .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
              .then((response) => {
                this.PostCreate.email = response.data.email;
            })
              .catch((err) => {
                console.log(err.response);
            });
            },
        noticeregist(){
            var flag = 0;
            if (this.NoticeCreate.title == "") {
                this.error.title = "제목은 빈칸일 수 없습니다.";
                flag = 1;
            } else {
                this.error.title = false;
            }
            if (this.NoticeCreate.content == "") {
                this.error.content = "상품 세부정보는 빈칸일 수 없습니다.";
                flag = 1;
            } else {
                this.error.content = false;
            }
            if (flag == 1) {
                alert("정보를 모두 입력해주세요.");
                return;
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
                .post(`${baseURL}/notice/regist/`, this.NoticeCreate)
                .then((response) => {
                console.log(response.data);
                Toast.fire({
                    icon: "success",
                    title: "작성이 완료되었습니다.",
                });
                this.$router.push(`/notice`);
                })
                .catch((error) => {
                console.log("error입니다.");
                });
        },
    },

}
</script>

<style scoped>
.notice-title-font{
    font-size: 1.5rem;
}

.notice-content-font{
    margin-top: 0.8rem;
}
</style>