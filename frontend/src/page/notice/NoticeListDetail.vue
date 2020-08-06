<template>
<div class="container col-md-8">
    <!-- Title -->
    <div class="form-group mb-4">
        <div class="d-flex justify-content-between" style="width:100%">
            <label class="d-flex notice-title-main mb-0 mr-3">{{notice.title}}</label>
                <div class="d-flex justify-content-end">
                    <small class="form-text notice-title-sub text-muted d-flex mr-2">작성일 : {{writeDate(this.notice.createDate)}}</small>
                    <small class="notice-title-sub">|</small>
                    <small class="form-text notice-title-sub text-muted d-flex ml-2">조회수 : {{notice.visit}}</small>
                </div>
        </div>
    <hr>
    </div>

    <!-- Detail-Info -->
    <div class="form-group">
        <label class="d-flex notice-content-main mr-3">Contents</label>
        <small class="form-text notice-content-sub text-muted d-flex">{{notice.content}}</small>
    </div>

    <!-- Button -->
    <div class="d-flex justify-content-end mb-5 mt-5">
      <button type="submit" class="btn btn-outline pr-0" style="font-size: 1.1rem;" @click="goNotice">
        <i class="fas fa-th-list mr-2"></i>목록으로
      </button>
    </div>
  </div>
<!-- <div class="container col-md-8">
    <div class="card col-md-8" style="max-width: 100%;">
    <div class="card-body col-md-8" style="max-width: 100%;">
        <span class="card-title">{{notice.title}}</span>
        <h6 class="card-subtitle mb-2 text-muted">조회수 : {{notice.visit}}</h6>
        <span>{{writeDate(notice.createDate)}}</span>
        <p class="card-text">{{notice.content}}</p>
    </div>
    </div>
</div> -->
</template>

<script>
import axios from 'axios';

import Swal from 'sweetalert2';

const baseURL = 'http://localhost:8080';

export default {
    created(){
        this.init();
    },
    data(){
        return{
            notice:[],
        }
    },
    methods:{
        init() {
          axios
            .get(`${baseURL}/notice/detail/${this.$route.params.ID}`)
            .then((res) => {
                this.notice = res.data;
            })
            .catch((err) => {
            console.log(err);
            });
        },
        writeDate(createDate){
            var wd = this.notice.createDate+"";
            return wd.substring(0,10);
        },
        goNotice: function() {
            this.$router.push('/notice/');
        },
    }

}
</script>

<style scoped>
.notice-title-main{
    text-align: left;
    font-size: 1.7rem;
    font-weight: bold;
    max-width: 70%;
}

.notice-title-sub{
    font-size: 1rem;
    max-width: 100%;
    margin-top: auto;
}

.notice-content-main{
    font-size: 1.5rem;
    font-weight: bold;
}

.notice-content-sub{
    text-align: left;
    font-size: 1.2rem;
}
</style>