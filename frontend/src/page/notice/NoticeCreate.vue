<template>
<div class="container col-sm-12 col-md-12 col-lg-12 p-0">
  <div class="notice-img" style="display:block;">
  </div>
  <div class="container col-md-8">
    <!-- Title -->
    <div class="form-group form-group mb-4 mt-5">
        <div class="d-flex justify-content-start">
            <label class="d-flex notice-title-font mr-3">Title</label>
            <small class="form-text notice-content-font text-muted d-flex" v-if="!error.title">제목을 입력하세요.</small>
        </div>
        <div>
          <select class="form-control col-md-2" id="imp" v-model="NoticeCreate.importance" style="float:right;">
            <option value="0">중요도</option>
            <option value="1">일반</option>
            <option value="2">중요</option>
            <option value="3">필독</option>
          </select>
        </div>
      </div>
      <input type="text" class="form-control" id="title" v-model="NoticeCreate.title" />
      <small class="form-text notice-content-font text-muted d-flex" v-if="!error.title">제목을 입력하세요.</small>
      <small class="form-text d-flex" style="color:red;" v-if="error.title">{{ error.title }}</small>
    </div>

    <!-- Detail-Info -->
    <div class="form-group">
      <div class="d-flex justify-content-start">
        <label class="d-flex notice-title-font mr-3">Detail-Info</label>
        <small class="form-text notice-content-font text-muted d-flex" v-if="!error.content">내용을 입력하세요.</small>
      </div>
      <textarea class="form-control" style="height:30rem;" id="detail" v-model="NoticeCreate.content"></textarea>
      <small class="form-text d-flex" style="color:red;" v-if="error.detail">{{ error.detail }}</small>
    </div>

    <!-- Button -->
    <div class="d-flex justify-content-end mb-5">
      <button type="submit" class="btn btn-outline pr-0" style="font-size: 1.1rem; color:red; font-weight:bold;" @click="noticeregist">
        <i class="fas fa-pen mr-1"></i>등록
      </button>
    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios'
import '../../assets/css/noticecreate.css'

import Swal from 'sweetalert2';

const baseURL = 'http://localhost:8080';

export default {
  data() {
    return {
      page: 1,
      NoticeCreate: {
        title: '',
        content: '',
        importance: '0',
      },
      error: {
        title: false,
        content: false,
      },
    };
  },
  created() {
    this.superadviser();
  },
  methods: {
    // 수정해야함
    superadviser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get('Auth-Token')}`)
        .then((response) => {
          this.PostCreate.email = response.data.email;
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    noticeregist() {
      var flag = 0;
      if (this.NoticeCreate.title == '') {
        this.error.title = '제목을 입력해주세요.';
        flag = 1;
      } else {
        this.error.title = false;
      }
      if (this.NoticeCreate.content == '') {
        this.error.content = '내용을 입력해주세요.';
        flag = 1;
      } else {
        this.error.content = false;
      }
      if (flag == 1) {
        alert('정보를 모두 입력해주세요.');
        return;
      }
      const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        onOpen: (toast) => {
          toast.addEventListener('mouseenter', Swal.stopTimer);
          toast.addEventListener('mouseleave', Swal.resumeTimer);
        },
      });

      axios
        .post(`${baseURL}/notice/regist/`, this.NoticeCreate)
        .then((response) => {
          console.log(response.data);
          Toast.fire({
            icon: 'success',
            title: '작성이 완료되었습니다.',
          });
          this.$router.push(`/notice`);
        })
        .catch((error) => {
          console.log('error입니다.');
        });
    },
  },
};
</script>

<style scoped>

</style>