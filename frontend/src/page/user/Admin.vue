<template>
<div class="container col-sm-12 col-md-12 col-lg-12 p-0">
  <!-- background image -->
  <div class="admin-img" style="display:block;">
  </div>
<div class="container col-md-8 mt-5">
  <b-tabs content-class="mt-3" fill>
    <!-- 포스트 관리 -->
    <b-tab class="admin-post" title="포스트 관리" active><p>승인요청 거절 완료</p>
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
              <td>ssafy@ssafy.com</td>
              <td>대기중/거절/승인</td>
              <td><button>승인</button><button>거절</button></td>
          </tr>
      </tbody>
    </table>
    </b-tab>

    <!-- 회원 관리 -->
    <b-tab class="admin-info" title="회원관리">
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
                <td v-if="user.uid!=1"><button class="admin-info-button" @click="dropuser(user.uid)">탈퇴</button></td>
          </tr>
      </tbody>
    </table>
    </b-tab>

    <!-- 신고 관리 -->
    <b-tab title="신고 관리" ><p></p>
    <table class="table mt-5">
      <thead class="thead-dark">
        <tr>
          <th>No</th>
          <th>신고한사람</th>
          <th>신고당한사람</th>
          <th>내용</th>
          <th>경고횟수</th>
          <th>경고/탈퇴/취소</th>
        </tr>
      </thead>
      <tbody>
          <tr v-for="(blacklist, index) in blacklists" :key="index">
              <td>{{index+1}}</td>
              <td>{{blacklist.email}}</td>
              <td>{{blacklist.remail}}</td>
              <td>{{blacklist.reason}}</td>
              <td>3</td>
              <td><button>경고</button><button>탈퇴</button><button>취소</button></td>
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
            blacklists:{},
        }
    },
    created(){
        this.authUser();
        this.blackuser();
    },
    methods:{
        authUser(){
            axios
                .get(`${baseURL}/account/viewAllUser`)
                .then((res)=>{
                    this.users = res.data
                    // console.log(this.users)
                })
                .catch((err)=>{
                    console.log(err)
                })
        },
        blackuser(){
          axios
            .get(`${baseURL}/report/list`)
            .then((res)=>{
              this.blacklists = res.data
              console.log(this.blacklists)
            }).catch((err)=>{
              console.log(err)
            })
        },
        dropuser(uid){
          axios
            .delete(`${baseURL}/report/dropUser`)
            .then(()=>{
              this.$router.push('/admin')
            }).catch((err)=>{
              console.log(err)
            })
        },
    },

}
</script>

<style>

</style>