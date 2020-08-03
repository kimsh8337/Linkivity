<template>
  <div class="container col-md-6">
    <p class="shopping-list">Shopping List</p>
    <div class="input-group mb-3" v-for="(post, index) in posts" :key="index">
      <div class="input-group-prepend">
        <div class="input-group-text">
          <input type="checkbox" aria-label="Checkbox for following text input" />
        </div>
      </div>
      <p type="text" class="form-control" aria-label="Text input with checkbox">
        {{ post.title }}</p>
    </div>
      <button class="btn btn-primary">구매하기</button>
  </div>
</template>

<script>
import axios from 'axios';
import "../../assets/css/basket.css";

const baseURL = "http://localhost:8080";

export default {
  created(){
    this.init();
  },
  data(){
    return{
      posts: {
        pid: "",
        email: "",
        activity: "",
        title: "",
        location: "",
        imgurl: "",
        price: "",
        sdate:"",
        edate:"",
        likecnt:""
      },
    }
  },
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
  }
};
</script>

<style>
</style>