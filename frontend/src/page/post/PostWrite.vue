<template>
  <div class="container">
    <table class="table">
      <thead class="thead-dark">
        <tr>
          <th style="width:10%;">No</th>
          <th style="width:20%">이미지</th>
          <th style="width:40%">제목</th>
          <th style="width:15%">작성일</th>
          <th style="width:15%">바로가기</th>
        </tr>
      </thead>
      <tr id="tt" v-for="(post, index) in posts" :key="index">
        <td v-if="email == post.email">{{index+1}}</td>
        <td v-if="email == post.email">
          <img :src="post.imgurl" style="width: 100px; height: 100px;" />
        </td>
        <td v-if="email == post.email" style="text-overflow:ellipsis; overflow: hidden; white-space: nowrap;">{{ post.title }}</td>
        <td v-if="email == post.email" style="text-overflow:ellipsis; overflow: hidden; white-space: nowrap;">{{ createdate(post.createDate) }}</td>
        <td v-if="email == post.email"><button class="postwrite-button" @click="getdetail(post.pid)">바로가기</button></td>
      </tr>
    </table>
    <br />
  </div>
</template>

<script>
import axios from "axios";

const baseURL = process.env.VUE_APP_BACKURL;

export default {
    data(){
        return{
            posts:{},
            type:'all',
            page:0,
            email:'',
        }
    },
    created(){
        this.authUser();
    },
    methods:{
        authUser(){
            axios
                .get(`${baseURL}/account/authuser/${this.$cookies.get('Auth-Token')}`)
                .then((res)=>{
                    this.email = res.data.email
                    console.log(this.email)
                    this.init()
                }).catch((err)=>{
                    console.log(err)
                })
        },
        init(){
            axios
                .get(`${baseURL}/post/getList/${this.type}/${this.page}`)
                .then((res)=>{
                    this.posts = res.data
                }).catch((err)=>{
                    console.log(err)
                })
              
        },
        createdate(date){
            var cd = date+""
            return cd.substring(0,10)
        },
        getdetail(pid){
            scroll(0,0);
            this.$router.push(`/posts/${pid}`)
        }
    },

}
</script>

<style scoped>
.postwrite-button{
    border: none;
    background: none;
    color: blue;
}

.postwrite-button:focus{
    outline: none;
}

.postwrite-button:hover{
    color: red;
}
</style>