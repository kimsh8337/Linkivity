<template>
  <div class="container col-md-6">
    <p class="shopping-list"><i class="fas fa-shopping-basket mr-2"></i>Shopping List</p>   
     <!-- shooping list  -->
    <div class="input-group mb-5" v-for="(post, index) in carts" :key="index">
      <div class="input-group-prepend">
        <div class="input-group-text">
          <input type="checkbox" aria-label="Checkbox for following text input" :value="post.price"  v-model="checked" />
        </div>
      </div>
      <img :src="post.imgurl" alt="" @click="getdetail(post.pid)">
      <div type="text" class="basket-list col-md-8" aria-label="Text input with checkbox" @click="getdetail(post.pid)">
        <p class="mb-0">제목 : {{post.title}}</p>
        <p class="mb-0">기간 : {{post.sdate}}~{{post.edate}}</p>
        <p class="mb-0">위치 : {{post.location}}</p>
        <p class="mb-0">가격 : {{post.price}}</p>
        <!-- <p>{{checked}}</p> -->
        </div>
    </div>

    <!-- price -->
    <div>
        <p class="checked-price">Total : {{checkedprice}}</p>
    </div>

    <!-- paging -->
    <paginate
      :page-count="this.totalPage"
      :click-handler="pageClick"
      :prev-text="'Prev'"
      :next-text="'Next'"
      :container-class="'pagination'"
      :page-class="'page-item'"
      >
    </paginate>

    <!-- 구매하기 button -->
    <div class="d-flex justify-content-end mb-5">
      <button class="btn btn-danger"><i class="far fa-hand-point-up mr-2"></i>구매하기</button>

    </div>
  </div>
</template>

<script>
import axios from 'axios';
import "../../assets/css/basket.css";
import Paginate from 'vuejs-paginate';

const baseURL = "http://localhost:8080";

export default {
  components: {
    Paginate
  },
  created(){
    this.email = this.$cookies.get("User");
    this.init();
  },
  data(){
    return{
      page: 0,
      totalPage: 0,
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
        likecnt:""
      },
      checked:[],
      sum:0,
    }
  },
  methods: {
    init() {
      axios
        .get(`${baseURL}/cart/list/${this.email}/${this.page}`)
        .then((res) => {
          this.carts = res.data;
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });

      // count
      axios
        .get(`${baseURL}/cart/count/${this.email}`)
        .then((res) => {
          this.totalPage = res.data / 10;
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });  
    },
    getdetail(pid) {
      this.$router.push({
        name: "PostListDetail",
        params: { ID: pid },
      });
    },
    pageClick: function(pageNum) {
      this.page = pageNum - 1;
      console.log(this.page);
    },
    checkPage() {
      axios
        .get(`${baseURL}/cart/list/${this.email}/${this.page}`)
        .then((res) => {
          this.carts = res.data;
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  },
  computed: {
    checkedprice(price){
      this.sum = 0;
      for(var i = 0; i < this.checked.length; i++ ){
        this.sum += this.checked[i];
      }
      return this.sum;
    }
  },
  watch: {
    page: function(v) {
      this.checkPage();
    }
  }
};
</script>

<style>
</style>