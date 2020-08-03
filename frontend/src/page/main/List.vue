<template>
<div>
  <div class="container col-md-7">
    <button class="btn btn-spring btn-circle btn-xl mb-5 mr-5">
      <br />
      <br />
      <br />Spring
    </button>
    <button class="btn btn-summer btn-circle btn-xl mb-5 mr-5">
      <br />
      <br />
      <br />Summer
    </button>
    <button class="btn btn-fall btn-circle btn-xl mb-5 mr-5">
      <br />
      <br />
      <br />Fall
    </button>
    <button class="btn btn-winter btn-circle btn-xl mb-5 mr-5">
      <br />
      <br />
      <br />Winter
    </button>
    <button class="btn btn-ground btn-circle btn-xl mb-5 mr-5">
      <br />
      <br />
      <br />Ground
    </button>
    <button class="btn btn-water btn-circle btn-xl mb-5 mr-5">
      <br />
      <br />
      <br />Water
    </button>
    <button class="btn btn-sky btn-circle btn-xl mb-5">
      <br />
      <br />
      <br />Sky
    </button>
    <div
      id="carouselExampleControls"
      class="carousel slide"
      style="margin: auto;"
      data-ride="carousel"
    >
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img
            src="https://t2.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/2OhT/image/O8cC0T4PchZkbqSc09Fvv8kVHOc.jpg"
            class="d-block w-100"
            alt="paragliding"
          />
        </div>
        <div class="carousel-item">
          <img
            src="https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F997560335A1C150A27"
            class="d-block w-100"
            alt="snowboarding"
          />
        </div>
        <div class="carousel-item">
          <img
            src="https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F997560335A1C150A27"
            class="d-block w-100"
            alt="wingsuit"
          />
        </div>
      </div>
      <a
        class="carousel-control-prev"
        href="#carouselExampleControls"
        role="button"
        data-slide="prev"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a
        class="carousel-control-next"
        href="#carouselExampleControls"
        role="button"
        data-slide="next"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>
    <div class="d-flex justify-content-between">
      <p class="hot-item mt-3 mb-0">HOT ITEM</p>
      <span class="more mt-4" @click="goPost">+ more</span>
    </div>
    <div class="row">
      <div
        class="card col-12 col-sm-12 col-md-3 p-3"
        v-for="(post, index) in posts"
        :key="index"
        style="width: 18rem; border: none;"
      >
        <div v-if="index < 4">
          <img
            :src="post.imgurl"
            class="card-img-top"
            style="height:11rem; cursor: pointer;"
            @click="getdetail(post.pid)"
          />
          <div class="card-body p-0">
            <p
              class="card-text mb-1"
              style="text-overflow:ellipsis;overflow: hidden;white-space: nowrap; font-weight: bold; color: gray;"
            >{{post.title}}</p>
            <p class="card-text d-flex justify-content-start" style="text-overflow:ellipsis; overflow: hidden; white-space: nowrap;">
              <i
                class="fas fa-heart select-button like-button mr-2 mt-1"
                style="text-align: left; font-size: 18px; color: crimson; "
              ></i>
              {{post.likecnt}}명이 좋아합니다.
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
    <footer class="main-footer bg-dark">
      <div class="container m-0 row">
        <div class="m-flex col-md-8" style="font-size:0.8rem">
          <span class="text-white text-muted d-flex" >상호명 (주)Linkivity | 대표이사: 이재인</span>
          <span class="text-white text-muted d-flex">사업자등록번호 : 010-2497-8055</span>
          <span class="text-white d-flex">통신판매업신고번호 : 제 2020-대전유성구-0077</span>
          <span class="text-white text-muted d-flex">본사 : 대전광역시 유성구 학하서로 121번길 55-9</span>
          <span class="text-white text-muted d-flex"><span class="text-white">고객지원 : </span><span class="text-white ml-1 mr-1"> 문의 게시판</span> 또는 <span class="text-white ml-1">실시간 채팅</span></span>
          <span class="text-white text-muted d-flex">(운영시간 09:00 ~ 18:00, 점심 : 12:00 ~ 13:00, 주말/공휴일은 제외)</span>
        </div>
        <div class="col-md-4 social d-flex">
          <span class="fa-stack fa-lg text-white facebook">
            <i class="fa fa-circle-o fa-stack-2x"></i>
            <i class="fa fa-facebook fa-stack-1x"></i>
          </span>
          <!-- <i class="text-white">𝙛𝙖𝙘𝙚𝙗𝙤𝙤𝙠</i> -->
          <span class="fa-stack fa-lg text-white twitter">
            <i class="fa fa-square-o fa-stack-2x"></i>
            <i class="fa fa-twitter fa-stack-1x"></i>
          </span>
          <!-- <i class="text-white">𝒕𝒘𝒊𝒕𝒆𝒓</i> -->
          <span class="fa-stack fa-lg text-white instagram">
            <i class="fa fa-square-o fa-stack-2x"></i>
            <i class="fa fa-instagram fa-stack-1x"></i>
          </span>
          <!-- <i class="text-white">𝒊𝒏𝒔𝒕𝒂𝒈𝒓𝒂𝒎</i> -->
        </div>
        <div class="d-flex col-md-8 mt-2">
          <span class="text-white"> 이용약관</span>
          <span class="text-white text-muted ml-2 mr-2"> | </span>
          <span class="text-white"> 개인정보처리방침</span>
          <span class="text-white text-muted ml-2 mr-2"> | </span>
          <span class="text-white"> 회사소개 </span>
        </div>
        <div class="d-flex col-md-8 mt-2" style="font-size:0.8rem">
          <span class="text-white">Made by : <i class="fas fa-heart" style="font-size:0.5rem"></i>𝕁𝕒𝕟𝕖ℝ𝕦𝕟</span>
        </div>

      </div>
    </footer>
  </div>
</template>
 
<script>
import axios from "axios";
import "../../assets/css/list.css";

const baseURL = "http://localhost:8080";

export default {
  name: "Post",
  components: {},
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
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goPost: function () {
      this.$router.push("/posts/");
      this.$router.go();
    },
    getdetail(pid) {
      this.$router.push({
        name: "PostListDetail",
        params: { ID: pid },
      });
    },
  },
  data: () => {
    return {
      email: "",
      password: "",
      itemcount: 0,
      posts: {
        pid: "",
        email: "",
        activity: "",
        title: "",
        location: "",
        imgurl: "",
        price: "",
        sdate: "",
        edate: "",
        likecnt: "",
      },
    };
  },
};
</script>

<style>
.main-footer {
  height: 280px;
  padding-top: 50px;
  padding-bottom: 30px;
}
.facebook {
  cursor: pointer;
}
.instagram {
  cursor: pointer;
}
.twitter {
  cursor: pointer;
}
</style>
