<template>
  <div class="container p-0">
    <table class="table">
      <thead class="thead-dark">
        <tr>
          <th style="width:10%; white-space: nowrap;">No</th>
          <th style="width:20%; white-space: nowrap;">이미지</th>
          <th style="width:40%; white-space: nowrap;">제목</th>
          <th style="width:15%; white-space: nowrap;">작성일</th>
          <th style="width:15%; white-space: nowrap;">바로가기</th>
        </tr>
      </thead>
      <tr id="tt" v-for="(post, index) in wposts" :key="index">
        <td v-if="email == post.email">{{((wpage - 1) * 8 ) + index + 1}}</td>
        <td v-if="email == post.email">
          <img
            :src="makeimgurl(post.imgurl)"
            v-if="post.imgurl"
            style="width: 100px; height: 100px;"
          />
        </td>
        <td
          v-if="email == post.email"
          style="text-overflow:ellipsis; overflow: hidden; white-space: nowrap;"
        >{{ post.title }}</td>
        <td
          v-if="email == post.email"
          style="text-overflow:ellipsis; overflow: hidden; white-space: nowrap;"
        >{{ createdate(post.createDate) }}</td>
        <td v-if="email == post.email">
          <button class="postwrite-button" @click="getdetail(post.pid)">바로가기</button>
        </td>
      </tr>
    </table>
    <br />

    <!-- paging -->
    <b-pagination
      class="mt-5 mb-0"
      v-if="wtotalPage > 10"
      v-model="wpage"
      :total-rows="wtotalPage"
      pills
      :per-page="10"
    ></b-pagination>
  </div>
</template>

<script>
import axios from "axios";

const baseURL = process.env.VUE_APP_BACKURL;

export default {
  data() {
    return {
      wposts: [],
      type: "all",
      wtotalPage: 0,
      wpage: 1,
      email: "",
    };
  },
  created() {
    this.authUser();
  },
  methods: {
    postCount() {
      axios
        .get(`${baseURL}/post/count/mypost/${this.email}`)
        .then((res) => {
          this.wtotalPage = res.data;
          this.checkPage();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((res) => {
          this.email = res.data.email;
          this.postCount();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    makeimgurl(imgurl) {
      return require("@/assets/file/" + imgurl);
    },
    checkPage() {
      axios
        .get(`${baseURL}/post/mypost/${this.email}/${this.wpage}`)
        .then((res) => {
          this.wposts = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    createdate(date) {
      var cd = date + "";
      return cd.substring(0, 10);
    },
    getdetail(pid) {
      scroll(0, 0);
      this.$router.push(`/posts/${pid}`);
    },
  },
  watch: {
    wpage: function (v) {
      this.checkPage();
    },
  },
};
</script>

<style scoped>
.postwrite-button {
  border: none;
  background: none;
  color: blue;
}

.postwrite-button:focus {
  outline: none;
}

.postwrite-button:hover {
  color: red;
}
</style>