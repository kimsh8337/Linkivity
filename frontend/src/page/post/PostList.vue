<template>
  <div class="post">
    <button class="btn btn-all btn-circle btn-xl mb-5 mr-5" @click="settype('all')">
      <br />
      <br />
      <br />All
    </button>
    <button class="btn btn-spring btn-circle btn-xl mb-5 mr-5" @click="settype('spring')">
      <br />
      <br />
      <br />Spring
    </button>
    <button class="btn btn-summer btn-circle btn-xl mb-5 mr-5" @click="settype('summer')">
      <br />
      <br />
      <br />Summer
    </button>
    <button class="btn btn-fall btn-circle btn-xl mb-5 mr-5" @click="settype('autumn')">
      <br />
      <br />
      <br />Fall
    </button>
    <button class="btn btn-winter btn-circle btn-xl mb-5 mr-5" @click="settype('winter')">
      <br />
      <br />
      <br />Winter
    </button>
    <button class="btn btn-ground btn-circle btn-xl mb-5 mr-5" @click="settype('ground')">
      <br />
      <br />
      <br />Ground
    </button>
    <button class="btn btn-water btn-circle btn-xl mb-5 mr-5" @click="settype('water')">
      <br />
      <br />
      <br />Water
    </button>
    <button class="btn btn-sky btn-circle btn-xl mb-5" @click="settype('sky')">
      <br />
      <br />
      <br />Sky
    </button>
    <div class="container col-md-8">
      <div class="input-group mb-5">
        <div class="input-group-prepend">
          <select
            class="btn dropdown-toggle text-black"
            style="border: 1px solid gray; z-index: 1;"
            aria-haspopup="true"
            aria-expanded="false"
            v-model="key"
          >
            <div role="separator" class="dropdown-divider"></div>
            <!-- <option value>All</option> -->
            <!-- <option value="all">All</option> -->
            <option value disabled>검색조건</option>
            <option value="title">Title</option>
            <option value="activity">Activity</option>
            <option value="price">Price</option>
          </select>
        </div>
        <input
          type="text"
          class="form-control"
          placeholder="Search"
          v-model="word"
          @keypress.enter="search"
        />
      </div>

      <!-- <div class="d-flex justify-content-end">
        <a type="button" class="btn btn-outline form-check mb-2" @click="gocreate">
          <i class="fas fa-pen"></i> 상품 등록
        </a>
      </div>-->
      <div class="row justify-content-left" v-if="posts.length > 0">
        <div
          class="col-12 col-sm-12 col-md-3 card-deck"
          style="margin:auto 0; padding:0 20px ;"
          v-for="(post, index) in posts"
          :key="index"
        >
          <div class="card mb-3 profile-post mr-0 ml-0">
            <div class="card-body" style="padding: 0;">
              <img :src="post.imgurl" class="card-img" style="height:10rem" />
              <div
                class="card-img-overlay pt-0 pr-2"
                @click="getdetail(post.pid)"
                style="text-align:right; font-size:0.7rem; font-weight:400; color: white; "
              >
                <!-- <button class="location-button">{{post.location}}</button> -->
                <!-- <p>{{ post.location.substring(0,2) }}</p> -->
                <span class="pr-2 pl-2 pb-1" style="background-color:rgba(0,0,0,0.3);z-index:34;">
                  <i class="fa fa-map-marker" style="font-size:0.7rem;"></i>
                  {{localarea(post.location)}}
                </span>
              </div>
              <div class="col-md-12 p-0">
                <div class="card-body" style="padding: 5px; height:10rem;">
                  <!-- tag -->
                  <!-- <div v-for="tagg in tag" :key="tagg.pid">
                  <div v-if="tagg.pid == post.pid" >
                    {{tagg.tag}}
                  </div>
                  </div>-->
                  <!-- </div> -->
                  <div
                    v-for="tagg in tag"
                    :key="tagg.pid"
                    style="text-align: left; text-overflow:ellipsis;overflow: hidden;white-space: nowrap;"
                  >
                    <div v-if="tagg.pid == post.pid">
                      <span
                        class="card-text mb-2 text-primary"
                        v-for="tagname in tagg.tag"
                        :key="tagname"
                        style="font-size: 0.8rem; font-weight:bold;"
                      >#{{ tagname }}</span>
                    </div>
                  </div>
                  <h5
                    class="card-title"
                    @click="getdetail(post.pid)"
                    style="font-size: 1rem; text-align: left; margin-bottom: 1rem; text-overflow:ellipsis;overflow: hidden;white-space: nowrap;"
                  >{{ post.title }}</h5>
                  <!-- pre-line; -->
                  <p
                    class="card-text mb-2"
                    style="font-size: 1rem; text-align: left; text-overflow:ellipsis;overflow: hidden;white-space: nowrap; color:gray"
                  >{{ post.sdate }}~{{ post.edate }}</p>

                  <div class="text d-flex justify-content-between">
                    <p
                      class="card-text"
                      style="font-size: 1rem; font-weight:bold; text-align: left; text-overflow:ellipsis;overflow: hidden;white-space: nowrap;"
                    >{{ addComma(post.price) }}원</p>
                    <!-- heart like -->
                    <div id="heart" @click="registlike(post.pid)">
                      {{ post.likecnt }}
                      <i
                        v-if="check(post.pid)"
                        class="fas fa-heart select-button like-button"
                        style="text-align: right; font-size: 20px; color:crimson;"
                      ></i>
                      <i
                        v-if="!check(post.pid)"
                        class="far fa-heart"
                        style="text-align: right; font-size: 20px;"
                      ></i>
                    </div>
                    <!--  -->
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- top button -->
      <i class="fas fa-2x fa-angle-double-up upBtn" @click="toTop" style="cursor:pointer;"></i>
      <!-- infinite loading -->
      <infinite-loading :identifier="infiniteId" @infinite="infiniteHandler" spinner="waveDots">
        <div slot="no-more">
          <a @click="toTop"></a>
        </div>
        <div slot="no-results"></div>
      </infinite-loading>
    </div>
  </div>
</template>

<script>
import "../../assets/css/postlist.css";
import axios from "axios";
import InfiniteLoading from "vue-infinite-loading";
import Swal from "sweetalert2";

// const Swal = require('sweetalert2')

const baseURL = "http://localhost:8080";

export default {
  components: {
    InfiniteLoading,
  },
  data() {
    return {
      page: 1,
      infiniteId: 0,
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
      key: "",
      word: "",
      type: "all",
      email: "",
      postLike: [],
      cntLike: [],
      tag: [],
      // filter: "",
    };
  },
  methods: {
    settype(typename) {
      this.type = typename;
      this.init();
    },
    toTop() {
      scroll(0, 0);
    },
    infiniteHandler($state) {
      if (this.key == "") {
        axios
          .get(`${baseURL}/post/getList/${this.type}/${this.page}`)
          .then((res) => {
            setTimeout(() => {
              if (res.data.length) {
                this.posts = this.posts.concat(res.data);
                $state.loaded();
                this.page += 1;
                if (this.posts.length / 9 < 1) {
                  $state.complete();
                }
              } else {
                $state.complete();
              }
            }, 1000);
          })
          .catch((err) => {
            console.log(err);
          });
      } else {
        axios
          .get(
            `${baseURL}/post/search/${this.type}/${this.key}/${this.word}/${this.page}`
          )
          .then((res) => {
            setTimeout(() => {
              if (res.data.length) {
                this.posts = this.posts.concat(res.data);
                $state.loaded();
                this.page += 1;
                if (this.posts.length / 9 < 1) {
                  $state.complete();
                }
              } else {
                $state.complete();
              }
            }, 1000);
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    getdetail(pid) {
      scroll(0, 0);
      this.$router.push({
        name: "PostListDetail",
        params: { ID: pid },
      });
    },
    search() {
      this.page = 1;
      this.infiniteId += 1;

      if (this.key == "") {
        this.word = "";
      } else {
        if (this.word == "") {
          alert("검색어를 입력하세요.");
        } else {
          this.page = 1;
          // this.init();
          axios
            .get(
              `${baseURL}/post/search/${this.type}/${this.key}/${this.word}/0`
            )
            .then((res) => {
              this.posts = res.data;
            })
            .catch((err) => {
              console.log(err);
            });
        }
      }
    },
    registlike(pid) {
      if (this.email != null) {
        axios
          .get(`${baseURL}/like/registDelete/${this.email}/${pid}`)
          .then((res) => {
            this.checklike();
            this.init();
            if (this.check(pid) == false) {
              this.$toasted.show("좋아좋아요", {
                theme: "bubble",
                position: "top-right",
                duration: 1000,
              });
            } else {
              this.$toasted.show("좋아요 취소", {
                theme: "bubble",
                position: "top-right",
                duration: 1000,
              });
            }
          })
          .catch((err) => {
            alert(err);
          });
      } else {
        Swal.fire({
          icon: "error",
          text: "로그인 후 이용해주세요...",
          confirmButtonColor: "#fff",
          width: 350,
          confirmButtonText:
            '<a data-toggle="modal" data-target="#LoginModal" style="font-size:1rem; color:black" >Login</a>',
          showCancelButton: true,
          cancelButtonText: '<a style="font-size:1rem; color:black">Cancel</a>',
          cancelButtonColor: "#fff",
        }).then((result) => {
          Swal.close();
        });
      }
    },
    check(pid) {
      for (var i = 0; i < this.postLike.length; i++) {
        if (this.postLike[i] == pid) {
          return true;
        }
      }
      return false;
    },
    checklike() {
      axios
        .get(`${baseURL}/like/check/${this.email}`)
        .then((res) => {
          this.postLike = res.data;
        })
        .catch((err) => {
          alert(err);
        });
    },
    cntlike() {
      axios
        .get(`${baseURL}/like/cnt`)
        .then((res) => {
          this.cntLike = res.data;
        })
        .catch((err) => {
          alert(err);
        });
    },
    init() {
      axios
        .get(`${baseURL}/post/getList/${this.type}/0`)
        .then((res) => {
          this.posts = res.data;
          this.nextTag();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    nextTag() {
      this.tag = [];
      for (let i = 0; i < this.posts.length; i++) {
        axios
          .get(`${baseURL}/tag/list/${this.posts[i].pid}`)
          .then((res) => {
            let a = { tag: res.data, pid: this.posts[i].pid };
            this.tag.push(a);
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    localarea(location) {
      var la = location + "";
      return la.substring(0, 2);
    },
    addComma(num) {
      var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return num.toString().replace(regexp, ",");
    },
  },
  created() {
    this.filter = this.$route.params.TYPE;
    if (this.$cookies.get("Auth-Token") == null) {
      this.init();
      return;
    }
    axios
      .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
      .then((response) => {
        this.email = response.data.email;
        this.init();
        this.checklike();
      })
      .catch((err) => {
        console.log(err.response);
      });
  },
  computed: {},
};
</script>

<style>
.carousel-inner {
  width: 100%;
  height: 25rem;
}
.carousel-inner img {
  height: 25rem;
}

.card-img-left {
  width: 15rem;
}
.post-title {
  font-size: 3rem;
  text-align: center;
}
.postlist {
  cursor: pointer;
}

.card-img-overlay {
  cursor: pointer;
}
.upBtn {
  position: fixed;
  right: 5%;
  top: 90%;
  color: red;
}
</style>
