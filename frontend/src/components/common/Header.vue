<template>
  <div id="header" class="p-0" v-if="isHeader">
    <nav class="navbar navbar-expand-lg navbar-light header-nav col-md-12">
      <!-- <router-link class="nav-title m-0" v-bind:to="{name:constants.URL_TYPE.POST.MAIN}"> -->
      <h5 class="my-auto font-weight-bold nav-title" @click="rendering">𝑳𝒊𝒏𝒌𝒊𝒗𝒊𝒕𝒚</h5>
      <!-- </router-link> -->

      <div class="d-md-block d-lg-none">
        <b-button
          v-b-toggle.sidebar-1
          class="btn btn-light"
          style="background-color:rgba(255,255,255,0.1); border: 1px solid rgb(134, 165, 212)"
        >
          <span class="navbar-toggler-icon"></span>
        </b-button>
        <b-sidebar id="sidebar-1" title="Menu" shadow width="250px">
          <div class="ml-4" style="text-align:left">
            <ul class="navbar-nav nav-sub ml-auto">
              <li class="nav-item">
                <a class="nav-link mt-3" @click="goPost">
                  <i class="fas fa-stream mr-2"></i>
                  액티비티
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link mt-3" @click="goNotice">
                  <i class="fas fa-flag mr-2"></i>
                  공지사항
                </a>
              </li>
              <li class="nav-item">
                <a v-if="this.$cookies.isKey('Auth-Token') && this.usertype == 'business'" class="nav-link mt-3" @click="gocreate">
                  <i class="fas fa-pen mr-2"></i>
                  액티비티 등록
                </a>
              </li>
              <li class="nav-item">
                <a v-if="this.$cookies.isKey('Auth-Token') && this.usertype == 'normal'" class="nav-link mt-3" @click="goBasket">
                  <i class="fas fa-shopping-basket mr-2"></i>
                  장바구니
                </a>
              </li>

              <li class="nav-item">
                <a v-if="this.$cookies.isKey('Auth-Token') && this.usertype != 'admin'" @click="info" class="nav-link mt-3">
                  <i class="far fa-user mr-2"></i>
                  마이페이지
                </a>
              </li>
              <li class="nav-item">
                <a v-if="this.$cookies.isKey('Auth-Token') && this.usertype == 'admin'" @click="goadmin" class="nav-link mt-3">
                  <i class="fas fa-users-cog mr-2"></i>
                  관리 페이지
                </a>
              </li>
              <li class="nav-item">
                <a v-if="this.$cookies.isKey('Auth-Token')" @click="logout" class="nav-link mt-3">
                  <i class="fas fa-sign-out-alt mr-2"></i>
                  로그아웃
                </a>
              </li>
              <li class="nav-item">
                <a v-if="!this.$cookies.isKey('Auth-Token')" data-toggle="modal" data-target="#LoginModal" class="nav-link mt-3">
                  <i class="fas fa-sign-in-alt mr-2"></i>
                  로그인
                </a>
              </li>
            </ul>
          </div>
        </b-sidebar>
      </div>

      <!-- <button
        class="navbar-toggler navbar-toggler-right text-black"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button> -->

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav nav-sub ml-auto">
          <li class="nav-item">
            <a class="nav-link mt-3 mr-2" @click="goPost">
              <i class="fas fa-stream mr-1"></i>
              <br />Post
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link mt-3 mr-2" @click="goNotice">
              <i class="fas fa-flag"></i>
              <br />Notice
            </a>
          </li>
          <li class="nav-item">
            <a v-if="this.$cookies.isKey('Auth-Token') && this.usertype == 'business'" class="nav-link mt-3 mr-2" @click="gocreate">
              <i class="fas fa-pen mr-1"></i>
              <br />Write
            </a>
          </li>
          <li class="nav-item">
            <a v-if="this.$cookies.isKey('Auth-Token') && this.usertype == 'normal'" class="nav-link mt-3 mr-2" @click="goBasket">
              <i class="fas fa-shopping-basket"></i>
              <br />Basket
            </a>
          </li>

          <li class="nav-item">
            <a v-if="this.$cookies.isKey('Auth-Token') && this.usertype != 'admin'" @click="info" class="nav-link mt-3 mr-2">
              <i class="far fa-user mr-1"></i>
              <br />MyPage
            </a>
          </li>
          <li class="nav-item">
            <a v-if="this.$cookies.isKey('Auth-Token') && this.usertype == 'admin'" @click="goadmin" class="nav-link mt-3 mr-2">
              <i class="fas fa-users-cog mr-1"></i>
              <br />Admin
            </a>
          </li>
          <li class="nav-item">
            <a v-if="this.$cookies.isKey('Auth-Token')" @click="logout" class="nav-link mt-3 pl-1">
              <i class="fas fa-sign-out-alt mr-1"></i>
              <br />Logout
            </a>
          </li>
          <li class="nav-item">
            <a v-if="!this.$cookies.isKey('Auth-Token')" data-toggle="modal" data-target="#LoginModal" class="nav-link mt-3 pl-1">
              <i class="fas fa-sign-in-alt mr-1"></i>
              <br />Login
            </a>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
import '../../assets/css/header.css';
import constants from '../../lib/constants';

import axios from 'axios';
const baseURL = process.env.VUE_APP_BACKURL;

export default {
  name: 'Header',
  components: {},
  props: {
    isHeader: Boolean,
  },
  computed: {},
  watch: {},
  created() {
    if (this.$cookies.get('Auth-Token') != null) {
      this.authUser();
    }
  },
  methods: {
    rendering() {
      this.$router.push('/').catch((err) => {
        this.$router.go();
      });
    },
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get('Auth-Token')}`)
        .then((response) => {
          this.usertype = response.data.checkType;
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    goadmin() {
      this.$router.push('/admin');
    },
    gocreate() {
      this.$router.push({
        name: 'PostCreate',
      });
      this.$router.go();
    },
    logout: function() {
      this.$cookies.remove('Auth-Token');
      const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 1000,
        timerProgressBar: true,
        onOpen: (toast) => {
          toast.addEventListener('mouseenter', Swal.stopTimer);
          toast.addEventListener('mouseleave', Swal.resumeTimer);
        },
      });

      Toast.fire({
        icon: 'success',
        title: '로그아웃 완료',
      });
      setTimeout(() => {
        this.$router.push('/').catch((err) => {
          console.log(err);
        });
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
  },
  data() {
    return {
      constants,
      keyword: '',
      usertype: '',
    };
  },
};
</script>

<style>
/* .pageclick {
  cursor: pointer;
} */
</style>
