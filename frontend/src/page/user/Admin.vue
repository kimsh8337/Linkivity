<template>
<div class="container col-sm-12 col-md-12 col-lg-12 p-0">
  <!-- background image -->
  <div class="admin-img" style="display:block;">
  </div>
<div class="container col-md-8 mt-5">
  <b-tabs content-class="mt-3" fill>
    <b-tab class="admin-info" title="회원관리" active>
    <table class="table mt-5">
      <thead class="thead-dark">
        <tr>
          <th class="admin-info-num">No</th>
          <th class="admin-info-email">이메일</th>
          <th class="admin-info-nickname">닉네임</th>
          <th class="admin-info-name">이름</th>
          <th class="admin-info-type">사용자 유형</th>
          <th class="admin-info-out">탈퇴</th>
        </tr>
      </thead>
      <tbody>
          <tr v-for="(user,index) in users" :key="index">
                <td class="admin-info-data" v-if="user.uid!=1">{{index}}</td>
                <td class="admin-info-data" v-if="user.uid!=1">{{user.email}}</td>
                <td class="admin-info-data" v-if="user.uid!=1">{{user.nickname}}</td>
                <td class="admin-info-data" v-if="user.uid!=1">{{user.name}}</td>
                <td class="admin-info-data" v-if="user.uid!=1">{{user.checkType}}</td>
                <td v-if="user.uid!=1"><button class="admin-info-button">탈퇴</button></td>
          </tr>
      </tbody>
    </table>
    </b-tab>
    <b-tab class="admin-post" title="포스트"><p>승인요청 거절 완료</p>
    <table class="table">
      <thead class="thead-dark">
        <tr>
          <th>No</th>
          <th>제목</th>
          <th>업체정보</th>
          <th>작성자</th>
          <th>상태</th>
          <th>승인 및 거절</th>
        </tr>
      </thead>
      <tbody>
          <tr>
              <td>1</td>
              <td>글 제목</td>
              <td>업체</td>
              <td>admin@linkivity.com</td>
              <td>대기중/거절/승인</td>
              <td><button>승인</button><button>거절</button></td>
          </tr>
      </tbody>
    </table>
    </b-tab>
  </b-tabs>
</div>
</div>
</template>

<script>
import axios from 'axios';
import '../../assets/css/admin.css';

const baseURL = process.env.VUE_APP_BACKURL;

export default {
    data(){
        return{
            users:{},
        }
    },
    created(){
        this.authUser()
    },
    methods:{
        authUser(){
            axios
                .get(`${baseURL}/account/viewAllUser`)
                .then((res)=>{
                    this.users = res.data
                    console.log(this.users)
                })
                .catch((err)=>{
                    console.log(err)
                })
        },
    },

}
</script>

<style>

</style>