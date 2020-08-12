<template>
  <div v-if="this.scrollposition > 200" class="side-menu">
        <ul v-if="this.showbar == true" class="side-menu-bar">
            <li @click="goPost"><i class="fas fa-stream mx-2 "></i>Post</li>
            <li @click="goNotice"><i class="fas fa-flag mx-2"></i>Notice</li>
            <li v-if="this.$cookies.isKey('Auth-Token') && this.usertype == 'business'" @click="gocreate"><i class="fas fa-pen mx-2"></i>Write</li>
            <li v-if="this.$cookies.isKey('Auth-Token') && this.usertype == 'normal'" @click="goBasket"><i class="fas fa-shopping-basket mx-2"></i>Basket</li>
            <li v-if="this.$cookies.isKey('Auth-Token')" @click="info"><i class="far fa-user mx-2"></i>Mypage</li>
            <li @click="toTop"><i class="fas fa-angle-double-up upBtn" style="cursor:pointer;"></i>Top</li>
          </ul>
        <button class="side-main-button" @click="changeshowbar"><i class="fas fa-bars"></i></button>
      </div>
</template>

<script>
import axios from 'axios';
import '../../assets/css/menubar.css';

const baseURL = process.env.VUE_APP_BACKURL;

export default {
    props:{
        scrollposition: Number,
        showbar:Boolean,
    },
    deta(){
        return{
            usertype: ''
        }
    },
    created() {
    if (this.$cookies.get('Auth-Token') != null) {
      this.authUser();
    }
  },
    methods:{
        authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
          this.usertype = response.data.checkType;
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    gocreate() {
      this.$router.push({
        name: 'PostCreate',
      });
      this.$router.go();
    },
    logout: function() {
      this.$cookies.remove('Auth-Token');
      this.$router.push('/');
      Swal.fire({
        width: 250,
        position: 'top-right',
        icon: 'success',
        title: '로그아웃 완료!!!',
        showConfirmButton: false,
      });
      setTimeout(() => {
        this.$router.go();
      }, 1000);
    },
    info: function() {
      this.$router.push('/user/info/');
      this.$router.go();
    },
    goPost: function() {
      this.$router.push('/posts/');
      this.$router.go();
    },
    goBasket: function() {
      this.$router.push('/user/basket/');
      this.$router.go();
    },
    goNotice: function() {
      this.$router.push('/notice/');
    },
    changeshowbar(){
        this.$emit('change-showbar',this.showbar)
    },
    toTop() {
        scroll(0, 0);
        this.changeshowbar();
    },
    }

}
</script>

<style>

</style>