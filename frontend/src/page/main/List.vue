<template>
  <div>
    <div class="container col-sm-12 col-md-12 col-lg-12 p-0">
      <!-- background image -->
      <div class="main-img" style="display:block; height: 40rem;">
      </div>

      <div class="col-md-10" style="margin: 3rem auto;">
        <!-- main3button -->
        <div class="d-flex justify-content-between mb-5">
          <a class="main3button main-btn-intro"><i class="far fa-handshake mr-2"></i>액티비티 소개</a>
          <a class="main3button main-btn-item"><i class="fas fa-gift mr-2"></i>상품 둘러보기</a>
          <a class="main3button main-btn-notice"><i class="fas fa-flag mr-2"></i>공지사항</a>
        </div>

      <!-- carousel -->
      <div id="carouselExampleControls" class="carousel slide" style="margin: auto;" data-ride="carousel">
        <div class="carousel-inner" style="height:18rem">
          <div class="carousel-item active">
            <img
              src="https://www.10wallpaper.com/wallpaper/1366x768/1703/Flowers_branch_bloom_spring-2017_High_Quality_Wallpaper_1366x768.jpg"
              class="d-block w-100"
              style="height:18rem;"
              alt="paragliding"
            />
          </div>
          <div class="carousel-item">
            <img
              src="https://t1.daumcdn.net/cfile/tistory/9991CD365E49EF9D05"
              class="d-block w-100"
              style="height:18rem;"
              alt="snowboarding"
            />
          </div>
          <div class="carousel-item">
            <img
              src="https://t1.daumcdn.net/cfile/tistory/206D80344EACF2ED04"
              class="d-block w-100"
              style="height:18rem;"
              alt="wingsuit"
            />
          </div>
          <div class="carousel-item">
            <img
              src="https://t1.daumcdn.net/cfile/tistory/182F304D4EF06F1C30"
              class="d-block w-100"
              style="height:18rem;"
              alt="wingsuit"
            />
          </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev" style="opacity: 0;">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next" style="opacity: 0;">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="sr-only">Next</span>
        </a>
      </div>

      <!-- HOt item -->
      <div class="d-flex justify-content-between">
        <p class="hot-item mb-0" @click="goPost">HOT ITEM</p>
        <span class="more" @click="goPost">+ more</span>
      </div>
      <div class="row">
        <div class="card col-12 col-sm-12 col-md-2 p-3" v-for="(post, index) in posts" :key="index" style="width: 18rem; border: none;">
          <div v-if="index < 6">
            <img :src="post.imgurl" class="card-img-top" style="height:11rem; cursor: pointer;" @click="getdetail(post.pid)" />
            <div class="card-body p-0">
              <p
                class="card-text mt-2  mb-0"
                style="text-overflow:ellipsis;overflow: hidden;white-space: nowrap; font-weight: bold; color: black; text-align:left;"
              >
                {{ post.title }}
              </p>
              <p class="card-text d-flex justify-content-start" style="text-overflow:ellipsis; overflow: hidden; white-space: nowrap;">
                <i class="fas fa-heart select-button like-button mr-2 mt-1" style="text-align: left; font-size: 18px; color: crimson; "></i>
                {{ post.likecnt }}명이 좋아합니다.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import '../../assets/css/list.css';
import Footer from '../../components/common/Footer.vue';

const baseURL = 'http://localhost:8080';

export default {
  name: 'Post',
  components: { Footer },
  created() {
    this.init();
  },
  watch: {},

  methods: {
    init() {
      axios
        .get(`${baseURL}/post/listbylike/`)
        .then((res) => {
          this.posts = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goPost: function() {
      this.$router.push('/posts/');
      this.$router.go();
    },
    getdetail(pid) {
      this.$router.push({
        name: 'PostListDetail',
        params: { ID: pid },
      });
    },
    spring() {
      this.$router.push({
        name: 'PostList',
        params: { TYPE: 'spring' },
      });
    },
    summer() {
      this.$router.push({
        name: 'PostList',
        params: { TYPE: 'summer' },
      });
    },
    autumn() {
      this.$router.push({
        name: 'PostList',
        params: { TYPE: 'autumn' },
      });
    },
    winter() {
      this.$router.push({
        name: 'PostList',
        params: { TYPE: 'winter' },
      });
    },
    ground() {
      this.$router.push({
        name: 'PostList',
        params: { TYPE: 'ground' },
      });
    },
    water() {
      this.$router.push({
        name: 'PostList',
        params: { TYPE: 'water' },
      });
    },
    sky() {
      this.$router.push({
        name: 'PostList',
        params: { TYPE: 'sky' },
      });
    },
  },
  data: () => {
    return {
      email: '',
      password: '',
      itemcount: 0,
      posts: {
        pid: '',
        email: '',
        activity: '',
        title: '',
        location: '',
        imgurl: '',
        price: '',
        sdate: '',
        edate: '',
        likecnt: '',
      },
    };
  },
};
</script>

<style></style>
