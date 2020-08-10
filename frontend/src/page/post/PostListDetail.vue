<template>
<div class="container col-sm-12 col-md-12 col-lg-12 p-0">
  <!-- background image -->
  <div class="post-img" style="display:block;">
  </div>
  <div class="container col-md-7" style="margin-top: 100px">
    <div class="column">
      <div class="card mt-5 mb-3" style="max-width: 100%;">
        <div class="row no-gutters">
          <div class="col-md-5">
            <img :src="post.imgurl" class="card-img" style="height: 16rem; box-shadow:5px 5px 5px rgba(0,0,0,.15)" alt />
          </div>
          <div class="col-md-7">
            <div class="card-body" style="padding: 0 0 0 20px">
              <div class="text">
                <div class="d-flex justify-content-start">
                  <!-- hashTag -->
                  <small
                    class="text-primary mr-1"
                    style="text-align:left; font-size:1rem; text-overflow:ellipsis; overflow: hidden; white-space: nowrap;"
                    v-for="hash in hashTag"
                    :key="hash.id"
                  >#{{ hash }}</small>
                  <!-- 카카오톡 공유하기 -->
                  <a
                    href="javascript:;"
                    @click="test()"
                    id="kakao-link-btn"
                    style="margin-left:auto"
                  >
                    <img
                      src="//developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_small.png"
                      width="28px"
                    />
                  </a>
                </div>
                <div class="d-flex justify-content-start">
                  <!-- 업체 위치 -->
                  <p
                    class="card-text"
                    style="font-size: 1rem; color: rgb(168, 168, 168); text-align: left; text-overflow:ellipsis; overflow: hidden; white-space: nowrap;"
                  >[{{ post.location }}]</p>
                </div>
                <!-- 제목 -->
                <p
                  class="card-text font-weight-bold"
                  style="font-size: 1.2rem; text-align: left;"
                >[{{ post.activity }}]{{ post.title }}</p>
                <!-- season, place check badge -->
                <div
                  style="text-align: left; text-overflow:ellipsis; overflow: hidden; white-space: nowrap;"
                >
                  <b-badge
                    v-if="this.springCheck == 1"
                    pill
                    variant
                    style="background-color: #F699CD;"
                  >Spring</b-badge>
                  <b-badge
                    v-if="this.summerCheck == 1"
                    pill
                    variant
                    style="background-color: #32a852;"
                  >Summer</b-badge>
                  <b-badge
                    v-if="this.autumnCheck == 1"
                    pill
                    variant
                    style="background-color: #CCA38D"
                  >Autumm</b-badge>
                  <b-badge
                    v-if="this.winterCheck == 1"
                    pill
                    variant
                    style="background-color: #D3D3D3"
                  >Winter</b-badge>
                  <b-badge
                    v-if="this.placeCheck == 'ground'"
                    pill
                    variant
                    style="background-color: #501B00"
                  >Ground</b-badge>
                  <b-badge
                    v-if="this.placeCheck == 'water'"
                    pill
                    variant
                    style="background-color: #003399"
                  >Water</b-badge>
                  <b-badge
                    v-if="this.placeCheck == 'sky'"
                    pill
                    variant
                    style="background-color: #8DCCE7"
                  >Sky</b-badge>
                </div>
                <!-- 사용 기간 -->
                <p
                  class="card-text d-flex mt-3"
                  style="font-size: 1rem; text-overflow:ellipsis; overflow: hidden; white-space:nowrap;"
                >가용 기간 : {{ post.sdate }} ~ {{ post.edate }}</p>
                <!-- 이용 가격 -->
                <div class="d-flex justify-content-end mt-3">
                  <p
                    class="card-text mt-2 mr-4 mb-0"
                    style="font-size: 1rem; color: rgb(168, 168, 168); text-decoration:line-through;
                  "
                  >\{{ post.price }}</p>
                  <p
                    class="card-text font-weight-bold mb-0"
                    style="font-size: 1.5rem; text-align: left; margin-bottom: 5px;
                  "
                  >{{ post.price * 0.95 }} 원</p>
                </div>
                <hr class="mt-0" />
                <!-- like heart -->
                <div class="d-flex justify-content-end mr-0 mt-3 mb-3">
                  <div class="d-flex justify-content-start">
                    <i
                      class="fas fa-heart select-button mr-2"
                      style="text-align: right; font-size: 20px; color:crimson"
                    ></i>
                    {{ post.likecnt }}명이 좋아요를 눌렀습니다.
                  </div>
                </div>
                <!-- 장바구니, 구매 -->
                <div class="d-flex justify-content-end" v-if="this.checkType == 'normal'">
                  <button type="button" class="btn btn-primary mr-1" @click="alertbasket(post)">
                    <i class="fas fa-shopping-basket mr-2"></i>장바구니
                  </button>
                  <button class="btn btn-danger">
                    <i class="far fa-hand-point-up mr-2"></i>바로구매
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    

    <!-- Scrollspy  -->
    <nav id="navbar-example2" class="navbar nav-info">
      <ul class="nav justify-content-between" style="width:100%;">
        <li class="nav-item">
          <a class="nav-link info-link" href="#item" @click="scroll">상세 정보</a>
        </li>
        <li class="nav-item">
          <a class="nav-link info-link" href="#corp" @click="scroll">업체 정보</a>
        </li>
        <li class="nav-item">
          <a class="nav-link info-link" href="#review" @click="scroll">후기</a>
        </li>
        <li class="nav-item">
          <a class="nav-link info-link" href="#qna" @click="scroll">Q&A</a>
        </li>
      </ul>
    </nav>
    <br />
    <div data-spy="scroll" data-target="#navbar-example2" data-offset="0">
      <!-- 상세 정봉 -->
      <h4 id="item" class="d-flex mb-3" style="font-weight:bold">상세 정보</h4>
      <p class="d-flex" style="font-align:left">{{ post.detail }}</p>
      <hr>
      <!-- 업체 정보 -->
      <h4 id="corp" class="d-flex mb-3" style="font-weight:bold">업체 정보</h4>
      <p class="d-flex">{{ post.companyInfo }}</p>
      <hr>
      <!-- 지도 -->
      <p class="d-flex" style="font-size:1.5rem; font-weight:bold;">진행 장소</p>
      <div id="map" style="max-width: 100%; height:300px;"></div>
      <small class="d-flex mt-2" style="font-weight:bold;">{{ post.location }}</small>
      <hr class="mt-2" />
      <!-- 후기 -->
      <div class="d-flex justify-content-between mb-2">
        <div>
          <h4 id="review" class="" style="font-weight:bold">후기</h4>
        </div>
        <div v-if="this.checkType == 'normal'" class="review-button">
          <i data-toggle="modal" data-target="#reviewWrite" class="fas fa-pen mr-1"><small>후기 작성</small></i>
        </div>
      </div>
      <ReviewWrite :pid="pid" :email="email"/>
      <ReviewSlide :pid="pid" @review-delete="reviewDelete"/>

      <hr>
      <!-- Q & A -->
      <h4 id="qna" class="d-flex mb-3" style="font-weight:bold">Q&A</h4>
      <!-- 댓글 List -->
      <div class="d-flex bg-white">Comment : {{ receiveComment.length }}</div>
      <CommentList v-for="comment in receiveComment" :key="comment.rid" :comment="comment" @comment-delete="commentDelete" />
      <!-- 댓글 작성 -->
      <CommentInput class="mt-3" v-if="this.email" @create-comment="createcomment" />
      
    </div>

    <hr class="mt-0" />

    <!-- 글 수정 삭제 -->
    <div class="d-flex justify-content-end mt-3 mb-3" v-if="this.email == this.post.email">
      <button class="btn btn-success" @click="goModify">
        <i class="far fa-edit mr-2"></i>수정하기
      </button>
      <button class="btn btn-danger" @click="goDelete">
        <i class="far fa-trash-alt mr-2"></i>삭제하기
      </button>
    </div>
  </div>
</div>
</template>

<script>
import axios from "axios";
import "../../assets/css/postlistdetail.css";
import PostUpdateVue from "./PostUpdate.vue";

import CommentInput from "../../components/comment/CommentInput.vue";
import CommentList from "../../components/comment/CommentList.vue";

import ReviewSlide from "../../components/review/ReviewSlide.vue"
import ReviewWrite from "../../components/review/ReviewModal.vue"

import Swal from "sweetalert2";

const baseURL = "http://localhost:8080";

export default {
  components: {
    CommentInput,
    CommentList,
    ReviewSlide,
    ReviewWrite,
  },
  data() {
    return {
      post: [],
      pid: 0,
      email: "",
      receiveComment: [],
      hashTag: [],
      checkType: "",
      springCheck: "",
      summerCheck: "",
      autumnCheck: "",
      winterCheck: "",
      placeCheck: "",
    };
  },
  created() {
    (this.pid = this.$route.params.ID), this.authUser();
    this.fetchHashTag();
    // Kakao.init('765ed14c0d508f8aa48c6d173446acba');
  },
  methods: {
    scroll(evt) {
      evt.preventDefault();
      const href = evt.target.getAttribute('href');
      var location = document.querySelector(href).offsetTop;
      window.scrollTo({top:location + 400, behavior:'smooth'})
    },
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
          this.checkType = response.data.checkType;
          this.email = response.data.email;
          this.getPost();
          this.fetchComment();
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    test() {
      Kakao.Link.createDefaultButton({
        container: "#kakao-link-btn",
        objectType: "feed",
        content: {
          title: this.post.title, // 콘텐츠의 타이틀
          description: this.post.activity, // 콘텐츠 상세설명
          imageUrl: document.images[0].src, // 썸네일 이미지
          link: {
            webUrl: "http://localhost:3000/#/posts/" + this.pid,
            mobileWebUrl: "https://developers.kakao.com",
          },
        },
        social: {
          likeCount: 286, // LIKE 개수
          commentCount: 45, // 댓글 개수
          sharedCount: 845,
        },
        buttons: [
          {
            title: "Open!", // 버튼 제목
            link: {
              mobileWebUrl: "https://developers.kakao.com",
              webUrl: "http://localhost:3000/#/posts/" + this.pid,
            },
          },
        ],
      });
    },
    // goinfo() {
    //   this.$router.go();
    // },
    getPost() {
      axios
        .get(`${baseURL}/post/detail/${this.$route.params.ID}`)
        .then((res) => {
          this.springCheck = res.data.spring;
          this.summerCheck = res.data.summer;
          this.autumnCheck = res.data.autumn;
          this.winterCheck = res.data.winter;
          this.placeCheck = res.data.place;
          this.post = res.data;
          this.mapView(this.post.location);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goModify() {
      this.$router.push({
        name: "PostUpdate",
        params: { ID: this.pid },
      });
    },
    goDelete() {
      Swal.fire({
        width: 350,
        text: "삭제하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: '<a style="font-size:1rem; color:black">Delete</a>',
        cancelButtonText: '<a style="font-size:1rem; color:black">Cancel</a>',
      }).then((result) => {
        if (result.value) {
          const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener("mouseenter", Swal.stopTimer);
              toast.addEventListener("mouseleave", Swal.resumeTimer);
            },
          });
          Toast.fire({
            icon: "success",
            title: "글이 삭제되었습니다.",
          });
          axios
            .delete(`${baseURL}/post/delete/${this.$route.params.ID}`)
            .then(() => {
              this.$router.push(`/posts`);
            })
            .catch((error) => {
              console.log(error.response.data);
            });
        }
      });
    },
    createcomment(commentData) {
      const Toast = Swal.mixin({
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 1500,
        timerProgressBar: true,
        onOpen: (toast) => {
          toast.addEventListener("mouseenter", Swal.stopTimer);
          toast.addEventListener("mouseleave", Swal.resumeTimer);
        },
      });
      axios
        .post(`${baseURL}/reply/register`, commentData)
        .then((response) => {
          commentData.content = "";
          this.fetchComment();
          Toast.fire({
            icon: "success",
            title: "댓글 작성 완료!",
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    fetchComment() {
      axios
        .get(`${baseURL}/reply/list/${this.$route.params.ID}`)
        .then((response) => {
          this.receiveComment = response.data;
        })
        .catch((error) => {
          console.log(error.response.data);
        });
    },
    commentDelete(comment) {
      Swal.fire({
        width: 350,
        text: "댓글을 삭제하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: '<a style="font-size:1rem; color:black">Delete</a>',
        cancelButtonText: '<a style="font-size:1rem; color:black">Cancel</a>',
      }).then((result) => {
        if (result.value) {
          const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 1000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener("mouseenter", Swal.stopTimer);
              toast.addEventListener("mouseleave", Swal.resumeTimer);
            },
          });

          Toast.fire({
            icon: "success",
            title: "댓글이 삭제되었습니다.",
          });
          axios
            .delete(`${baseURL}/reply/delete/${comment.rid}`)
            .then((response) => {
              this.fetchComment();
            })
            .catch((error) => {
              console.log(error.response.data);
            });
        }
      });
    },
    alertbasket(post) {
      let isin = 0;
      axios
        .get(`${baseURL}/cart/check/${this.email}/${this.pid}`)
        .then((res) => {
          if (res.data) {
            alert("동일한 상품이 장바구니에 있습니다.");
          } else {
            Swal.fire({
              title: `${post.title}`,
              text: "장바구니에 담겼습니다.",
              imageUrl: `${post.imgurl}`,
              imageWidth: 400,
              imageHeight: 200,
              imageAlt: "Custom image",
            }),
              axios
                .get(`${baseURL}/cart/regist/${this.email}/${this.pid}`)
                .then((res) => {
                  this.posts = this.res;
                })
                .catch((err) => {
                  console.log(err);
                });
          }
        })
        .catch((err) => {
          console.log(err);
        });
      //장바구니에 있는지 확인

      // alert(`'${title}'상품을 장바구니에 담았습니다!`)
    },
    mapView(loc) {
      var mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
          level: 5, // 지도의 확대 레벨
        };

      // 지도를 생성합니다
      var map = new kakao.maps.Map(mapContainer, mapOption);

      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();

      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(loc, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          // console.log(result);
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

          // 결과값으로 받은 위치를 마커로 표시합니다
          var marker = new kakao.maps.Marker({
            map: map,
            position: coords,
          });
          var test = loc;

          // 인포윈도우로 장소에 대한 설명을 표시합니다
          var infowindow = new kakao.maps.InfoWindow({
            content: test,
          });
          infowindow.open(map, marker);

          // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
          map.setCenter(coords);
        }
      });
    },
    fetchHashTag() {
      axios
        .get(`${baseURL}/tag/list/${this.pid}`)
        .then((response) => {
          this.hashTag = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    reviewDelete(rvid) {
      Swal.fire({
      width: 300,
      text: "후기를 삭제하시겠습니까?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: '<a style="font-size:1rem; color:black">Delete</a>',
      cancelButtonText: '<a style="font-size:1rem; color:black">Cancel</a>',
    }).then((result) => {
      if (result.value) {
        const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 1000,
        timerProgressBar: true,
        onOpen: (toast) => {
          toast.addEventListener('mouseenter', Swal.stopTimer)
          toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
      })

      Toast.fire({
        icon: 'success',
        title: '후기 삭제 완료!'
      })
      axios.delete(`${baseURL}/review/delete/${rvid}`)
        .then(() => {
          setTimeout(() => {
            this.$router.go()
          },1000)
        }).catch((error) => {
          console.log(error)
        }) 
      }
    })
    },
  },
};
</script>

<style>

</style>
