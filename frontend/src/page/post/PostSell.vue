<template>
  <div class="container">
    <table class="table" >
      <thead class="thead-dark">
        <tr>
          <th>구매자</th>
          <th>사진</th>
          <th>상품명</th>
          <th>기간</th>
          <th>가격</th>
          <th>사용여부</th>
        </tr>
      </thead>
      <tr id="tt" v-for="(itm, index) in items" :key="index">
        <td>{{itm.email}}</td>
        <td>
          <img :src="itm.img" style="width: 100px; height: 100px;" />
        </td>
        <td>{{ itm.title }}</td>
        <td>{{ itm.sdate }} ~ {{ itm.edate }}</td>
        <td>{{ itm.price }}원</td>
        <td>
            <button class="btn btn-outline-danger btn-sm pt-0 pb-0" style="height:20px; font-size:12px;" v-if="itm.puse==0" @click="makeuse(itm.purid)">구매확정</button>   
        <!-- <b-badge
                    v-if="itm.puse == 0"
                    pill
                    variant
                    style="background-color: #003399"
                  >미사용</b-badge>-->
        <b-badge 
                    v-if="itm.puse == 1"
                    pill
                    variant
                    style="background-color: #C4302B"
                  >사용완료</b-badge>
        </td>
      </tr>
    </table>
    <br />
  </div>
</template>

<script>
import axios from "axios";
const baseURL = "http://localhost:8080";

export default {
  data() {
    return {
      items: [],
    };
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
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
        .get(`${baseURL}/purchase/sellList/${this.email}`)
        .then((res) => {
          this.items = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    makeuse(purid){
        Swal.fire({
        width: 350,
        text: "사용확정 처리하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "black",
        cancelButtonColor: "black",
        confirmButtonText: '<a style="font-size:1rem; color:white;">확정</a>',
        cancelButtonText: '<a style="font-size:1rem; color:white;">취소</a>',
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
            title: "사용확정 처리되었습니다.",
          });
          axios
            .get(`${baseURL}/purchase/checkuse/${purid}`)
            .then((response) => {
              this.init();
            })
            .catch((error) => {
              console.log(error);
            });
        }
      });


    }
  },
  created() {
    this.authUser();
  },
};
</script>

<style>
#tt td {
  text-align: center;
  vertical-align: middle;
}
</style>
