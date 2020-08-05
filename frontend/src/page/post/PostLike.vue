<template>
  <div>
    <div class="row justify-content-left">
      <div class="col-12 col-sm-12 col-md-3 card-deck" style="margin:auto 0;" v-for="(likePost, lindex) in likePosts" :key="lindex">
        <div class="card mb-3 profile-post mr-0 ml-0">
          <div class="card-body" style="padding: 0;">
            <img :src="likePost.imgurl" class="card-img" style="height:10rem" />
            <div
              class="card-img-overlay"
              @click="getdetail(likePost.pid)"
              style="padding:4rem 0; text-align:center; font-size:1.3rem; font-weight:bold; color: white;"
            >
              <p>{{ likePost.location }}</p>
            </div>
            <div class="col-md-12 p-0">
              <div class="card-body" style="padding: 5px;">
                <p
                  class="card-text mb-2"
                  style="font-size: 1rem; text-align: left; text-overflow:ellipsis;overflow: hidden;white-space: nowrap; color:gray"
                >
                  {{ likePost.sdate }}~{{ likePost.edate }}
                </p>
                <h5
                  class="card-title"
                  @click="getdetail(likePost.pid)"
                  style="font-size: 1rem; text-align: left; margin-bottom: 1rem; text-overflow:ellipsis;overflow: hidden;white-space: nowrap;"
                >
                  {{ likePost.title }}
                </h5>
                <div class="text d-flex justify-content-between">
                  <p
                    class="card-text"
                    style="font-size: 1rem; text-align: left; text-overflow:ellipsis;overflow: hidden;white-space: nowrap;"
                  >
                    가격 : {{ likePost.price }}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- paging -->
    <b-pagination v-if="ltotalPage > 8" v-model="lpage" :total-rows="ltotalPage" pills :per-page="8"></b-pagination>
  </div>
</template>

<script>
// import '../../assets/css/postlist.css';
import axios from 'axios';
import BPagenation from 'bootstrap-vue';

const baseURL = 'http://localhost:8080';

export default {
  components: {
    BPagenation,
  },
  data() {
    return {
      likePosts: {
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
      lpage: 1,
      ltotalPage: 0,
    };
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get('Auth-Token')}`)
        .then((res) => {
          this.email = res.data.email;
          this.init();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    init() {
      axios
        .get(`${baseURL}/like/list/${this.email}/${this.lpage}`)
        .then((res) => {
          this.likePosts = res.data;
        })
        .catch((err) => {
          console.log(err);
        });

      axios
        .get(`${baseURL}/like/count/${this.email}`)
        .then((res) => {
          this.ltotalPage = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getdetail(pid) {
      this.$router.push({
        name: 'PostListDetail',
        params: { ID: pid },
      });
    },
    checkPage() {
      axios
        .get(`${baseURL}/like/list/${this.email}/${this.lpage}`)
        .then((res) => {
          this.likePosts = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  watch: {
    lpage: function(v) {
      this.checkPage();
    },
  },
  created() {
    this.authUser();
  },
};
</script>

<style></style>
