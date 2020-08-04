<template>
  <div class="container col-md-6">
    <!-- shooping list  -->
    <div class="d-flex justify-content-between">
      <p class="shopping-list">
        <i class="fas fa-shopping-basket mr-2"></i>Shopping List
      </p>
      <button class="btn btn-delete" @click="checkdelete(no)">
        <i class="fas fa-trash-alt mr-2"></i>선택항목 삭제하기
      </button>
    </div>
    <div class="input-group mb-5" v-for="(post, index) in carts" :key="index">
      <div class="input-group-prepend">
        <div class="input-group-text">
          <input
            type="checkbox"
            aria-label="Checkbox for following text input"
            :value="post.price"
            v-model="checked"
            @click="changetf"
          />
        </div>
      </div>
      <img :src="post.imgurl" alt="" @click="getdetail(post.pid)" />
      <div
        type="text"
        class="basket-list col-md-8"
        aria-label="Text input with checkbox"
        @click="getdetail(post.pid)"
      >
        <p class="mb-0">제목 : {{ post.title }}</p>
        <p class="mb-0">기간 : {{ post.sdate }}~{{ post.edate }}</p>
        <p class="mb-0">위치 : {{ post.location }}</p>
        <p class="mb-0">가격 : {{ post.price }}</p>
        <!-- <p>{{checked}}</p> -->
      </div>
    </div>

    <!-- price -->
    <div>
      <p class="checked-price">Total : {{ checkedprice }}</p>
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
      <button
        class="btn btn-danger"
        data-toggle="modal"
        data-target="#BasketPackingModal"
      >
        <i class="far fa-hand-point-up mr-2"></i>구매하기
      </button>
      <BasketPackingModal />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import "../../assets/css/basket.css";
import Paginate from "vuejs-paginate";
import BasketPackingModal from "../../components/modal/BasketPackingModal.vue";

import Swal from "sweetalert2";

const baseURL = "http://localhost:8080";

export default {
  components: {
    Paginate,
    BasketPackingModal,
  },
  created() {
    this.email = this.$cookies.get("User");
    this.init();
  },
  data() {
    return {
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
        sdate: "",
        edate: "",
        likecnt: "",
        checktf: 0,
      },
      checked: [],
      sum: 0,
      likes: {
        no: "",
        pid: "",
        email: "",
        cart: "",
      },
      no: [],
      clicknum: 0,
    };
  },
  methods: {
    changetf() {
      this.clicknum += 1;
      this.checktf = 0;
      if (this.clicknum % 2 != 0) {
        this.checktf = 1;
      } else {
        this.checktf = 0;
      }
    },
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
        })
        .catch((err) => {
          console.log(err);
        });

      // likelist
      axios
        .get(`${baseURL}/cart/likelist/${this.email}`)
        .then((res) => {
          this.likes = res.data;
          console.log(this.likes);
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
    },
    checkdelete(no) {
      Swal.fire({
        width: 350,
        text: "선택항목을 삭제하시겠습니까?",
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
            title: "장바구니에서 삭제되었습니다.",
          });
          axios
            .delete(`${baseURL}/cart/delete/${this.no}`)
            .then(() => {
              this.$router.push(`/user/basket`);
            })
            .catch((error) => {
              console.log(error.response.data);
            });
        }
      });
    },
  },
  computed: {
    checkedprice(price) {
      this.sum = 0;
      for (var i = 0; i < this.checked.length; i++) {
        this.sum += this.checked[i];
      }
      return this.sum;
    },
  },
  watch: {
    page: function(v) {
      this.checkPage();
    },
  },
};
</script>

<style></style>
