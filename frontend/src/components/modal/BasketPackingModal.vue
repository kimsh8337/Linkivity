<template>
  <!-- Modal -->
  <div
    class="modal fade"
    id="BasketPackingModal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header border-0 pl-0">
          <h5
            class="modal-title w-100 text-center font-weight-bold position-absolute"
            id="exampleModalLabel"
          >Packing List</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <div class="d-flex justify-content-start mb-4" v-for="(post, index) in prePosts" :key="index">
            <img
              :src="post.imgurl"
              alt
              @click="getdetail(post.pid)"
              data-dismiss="modal"
            />
            <div
              type="text"
              class="basket-list col-md-8"
              aria-label="Text input with checkbox"
              data-dismiss="modal"
              @click="getdetail(post.pid)"
            >
              <p class="mb-0">제목 : {{ post.title }}</p>
              <p class="mb-0">기간 : {{ post.sdate }}~{{ post.edate }}</p>
              <p class="mb-0">위치 : {{ post.location }}</p>
              <p class="mb-0">가격 : {{ post.price }}</p>
            </div>
          </div>
            <!-- <p class="packaging-price mb-1">Singled Price : {{ Singleprice }}</p> -->
            <p class="packaging-price mb-1">Packaging Price : {{ Packagingprice }}</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-outline" data-dismiss="modal"><i class="fas fa-times mr-2"></i>
            취소
          </button>
          <button type="button" class="btn btn-danger" @click="purchase"><i class="far fa-hand-point-up mr-2"></i>구매하기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import '../../assets/css/basketmodal.css';

const baseURL = "http://localhost:8080";

export default {
  created() {
    this.authUser();
  },
  props: {
    prePosts: Array,
  },
  methods:{
    getdetail(pid) {
      this.$router.push({
        name: "PostListDetail",
        params: { ID: pid },
      });
    },
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
          this.email = response.data.email;
          this.Singleprice();
          this.Packagingprice();
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    purchase() {
      for (var i = 0; i < this.prePosts.length; i++) {
        this.packPost.push(this.prePosts[i].pid);
      }
      axios
        .get(`${baseURL}/purchase/regist/${this.packPost}/${this.email}/${this.sum}`)
        .then((response) => {
          alert("구매 완료");
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  computed: {
    // Singleprice(price) {
    //   this.sum = 0;
    //   this.sum = this.prePosts.price;
    //   return this.sum;
    // },
    Packagingprice(price) {
      this.sum = 0;
      this.subsum = 0;
      if(this.prePosts.length == 1){
        for (var i = 0; i < this.prePosts.length; i++) {
        this.sum += this.prePosts[i].price;
      }
      }else if(this.prePosts.length == 2){
        for (var i = 0; i < this.prePosts.length; i++) {
          this.subsum += this.prePosts[i].price;
        }
        this.sum = this.subsum * 0.95;
      }else if(this.prePosts.length == 3){
        for (var i = 0; i < this.prePosts.length; i++) {
          this.subsum += this.prePosts[i].price;
        }
          this.sum = this.subsum * 0.90;
      }else if(this.prePosts.length > 3){
        for (var i = 0; i < this.prePosts.length; i++) {
          this.subsum += this.prePosts[i].price;
        }
          this.sum = this.subsum * 0.85;
      }
      return this.sum;
    },
  },
  data() {
    return {
      sum: 0,
      packPost: [],
    };
  },
};
</script>

<style>
</style>
