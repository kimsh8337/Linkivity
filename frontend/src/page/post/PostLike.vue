<template>
  <div class="row justify-content-left">
    <div class="col-12 col-sm-12 col-md-3 card-deck" style="margin:auto 0;" v-for="(post, index) in posts" :key="index">
      <div class="card mb-3 profile-post mr-0 ml-0">
        <div class="card-body" style="padding: 0;">
          <img :src="post.imgurl" class="card-img" style="height:10rem" />
          <div
            class="card-img-overlay"
            @click="getdetail(post.pid)"
            style="padding:4rem 0; text-align:center; font-size:1.3rem; font-weight:bold; color: white;"
          >
            <p>{{ post.location }}</p>
          </div>
          <div class="col-md-12 p-0">
            <div class="card-body" style="padding: 5px;">
              <p
                class="card-text mb-2"
                style="font-size: 1rem; text-align: left; text-overflow:ellipsis;overflow: hidden;white-space: nowrap; color:gray"
              >
                {{ post.sdate }}~{{ post.edate }}
              </p>
              <h5
                class="card-title"
                @click="getdetail(post.pid)"
                style="font-size: 1rem; text-align: left; margin-bottom: 1rem; text-overflow:ellipsis;overflow: hidden;white-space: nowrap;"
              >
                {{ post.title }}
              </h5>
              <div class="text d-flex justify-content-between">
                <p
                  class="card-text"
                  style="font-size: 1rem; text-align: left; text-overflow:ellipsis;overflow: hidden;white-space: nowrap;"
                >
                  가격 : {{ post.price }}
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
// import '../../assets/css/postlist.css';
import axios from 'axios';

const baseURL = 'http://localhost:8080';

export default {
  data() {
    return {
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
        .get(`${baseURL}/like/list/${this.email}`)
        .then((res) => {
          console.log(res);
          this.posts = res.data;
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
  },
  created() {
    this.authUser();
  },
};
</script>

<style>
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
.card-title,
.card-img-overlay {
  cursor: pointer;
}
</style>
