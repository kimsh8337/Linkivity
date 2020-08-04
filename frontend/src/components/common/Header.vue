<template>
  <div id="header" class="p-0" v-if="isHeader">
    <nav class="navbar navbar-expand-lg navbar-light fixed-top header-nav col-md-7" >
      <router-link class="nav-title" v-bind:to="{name:constants.URL_TYPE.POST.MAIN}">
        <h5 class="mt-3 font-weight-bold nav-title">𝑳𝒊𝒏𝒌𝒊𝒗𝒊𝒕𝒚</h5>
      </router-link>
      <button
        class="navbar-toggler navbar-toggler-right text-black"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link mt-3 mr-2" @click="goPost"><i class="fas fa-stream mr-1"></i><br>Post</a>
          </li>
          <li class="nav-item">
            <a v-if="this.$cookies.isKey('Auth-Token')" class="nav-link mt-3 mr-2"  @click="gocreate"><i class="fas fa-pen mr-1"></i><br>Write</a>
          </li>
          <li class="nav-item dropdown">
            <a v-if="this.$cookies.isKey('Auth-Token')" class="nav-link dropdown-toggle mt-3 mr-2" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" @mouseover="visi" @mouseleave="hide"><i class="fas fa-shopping-basket"></i><br>Basket</a>
            <div v-if="upup" class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" href="#">Action</a>
              <a class="dropdown-item" href="#">Another action</a>
              <div class="dropdown-divider"></div>
              <a class="dropdown-item" href="#">Something else here</a>
            </div>
            <!-- Preview -->
            <!-- <div v-if="upup" class="card border-1" style="border:1px solid black">
              <div class="card border-1">

              <li v-for="(post, index) in carts" :key="index"><img v-if="index < 3" :src="post.imgurl" style="width:7rem; height:5rem" alt=""></li>
              </div>
            </div> -->
          </li>
          

          <li class="nav-item">
            <a v-if="this.$cookies.isKey('Auth-Token')" @click="info" class="nav-link mt-3 mr-2"><i class="far fa-user mr-1"></i><br>MyPage</a>
          </li>
          <li class="nav-item">
            <a v-if="this.$cookies.isKey('Auth-Token')" @click="logout" class="nav-link mt-3 pl-1"><i class="fas fa-sign-out-alt mr-1"></i><br>Logout</a>
          </li>
          <li class="nav-item">
            <a v-if="!this.$cookies.isKey('Auth-Token')" data-toggle="modal" data-target="#LoginModal" class="nav-link mt-3 pl-1"><i class="fas fa-sign-in-alt mr-1"></i><br>Login</a>
          </li>
        </ul>
      </div>
    </nav>

  </div>
</template>   

<script>
import '../../assets/css/header.css'
import constants from "../../lib/constants";

import axios from "axios";
const baseURL = "http://localhost:8080";

export default {
  name: "Header",
  components: {
  },
  props: {
    isHeader: Boolean,
  },
  computed: {},
  watch: {},
  created() {
    this.email = this.$cookies.get("User");
    this.init()
  },
  methods: {
    init() {
      axios
        .get(`${baseURL}/cart/list/${this.email}`)
        .then((res) => {
          this.carts = res.data;
          // this.checked = res.data;
          // this.no = res.data.index;
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    gocreate() {
      this.$router.push({
        name: "PostCreate",
      })
      this.$router.go();
    },
    logout: function() {
      this.$cookies.remove("Auth-Token");
      this.$cookies.remove("User");
      this.$router.push("/");
      Swal.fire({
        width:250,
        position: 'top-right',
        icon: 'success',
        title: '로그아웃 완료!!!',
        showConfirmButton: false,
      })
      setTimeout(() => {
        this.$router.go();
      },1000)
    },
    info: function() {
      this.$router.push("/user/info/");
      this.$router.go();
    },
    goPost: function() {
      this.$router.push("/posts/");
      this.$router.go();
    },
    goBasket: function() {
      this.$router.push("/user/basket/");
      this.$router.go();
    },
    visi(){
      alert('보여라')
      this.upup = true
    },
    hide(){
      this.upup = false
    }
  },
  data() {
    return {
      constants,
      keyword: "",
      carts: {
        pid: "",
        email: "",
        activity: "",
        title: "",
        location: "",
        imgurl: "",
        price: "",
        sdate:"",
        edate:"",
        likecnt:"",
        checktf:0,
      },
      upup: false,
    }
  }
};
</script>

<style>
/* .pageclick {
  cursor: pointer;
} */
</style>