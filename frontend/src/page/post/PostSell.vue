<template>
  <div>
    <!-- 판매된 상품이 없을 때 -->
    <div class="mt-5 mb-5" v-if="sitems.length <= 0">
      <i class="fas fa-surprise mt-5 mr-2 mb-5"></i
      ><span style="font-weight:bold">판매된 상품이 없습니다.</span
      ><i class="fas fa-surprise ml-2"></i>
    </div>
    <!-- 판매된 상품이 있을때 -->
    <div class="container" v-if="sitems.length > 0">
      <table class="table">
        <thead class="thead" style="background:RGB(134, 165, 212); color:white;">
          <tr>
            <th>구매자</th>
            <th>사진</th>
            <th>상품명</th>
            <th>기간</th>
            <th>가격</th>
            <th>사용여부</th>
          </tr>
        </thead>
        <tr id="tt" v-for="(itm, index) in sitems" :key="index">
          <td>{{ itm.email }}</td>
          <td>
            <img
              :src="makeimgurl(itm.img)"
              v-if="itm.img"
              style="width: 100px; height: 100px; cursor:pointer;"
              @click="getdetail(itm.pid)"
            />
          </td>
          <td>
            <div style="cursor:pointer;" @click="getdetail(itm.pid)">
              {{ itm.title }}
            </div>
          </td>
          <td>{{ itm.sdate }} ~ {{ itm.edate }}</td>
          <td>{{ itm.price }}원</td>
          <td>
            <button
              class="btn btn-outline-danger btn-sm pt-0 pb-0"
              style="height:20px; font-size:12px;"
              v-if="itm.puse == 0"
              data-toggle="modal"
              data-target="#confirmmodal"
              @click="open(itm.purid)"
            >
              구매확정
            </button>
            <b-badge
              v-if="itm.puse == 1"
              pill
              variant
              style="background-color: #C4302B"
              >사용완료</b-badge
            >
          </td>
        </tr>
      </table>

      <ConfirmModal :purid="this.id" @make-use="makeuse" />
      <br />
    </div>
    <!-- paging -->
    <b-pagination class="mt-5 mb-0" v-if="stotalPage > 10" v-model="spage" :total-rows="stotalPage" pills :per-page="10"></b-pagination>
  </div>
</template>

<script>
import axios from "axios";
import ConfirmModal from "../../components/modal/ConfirmModal.vue";
const baseURL = process.env.VUE_APP_BACKURL;

export default {
  components: { ConfirmModal },
  data() {
    return {
      sitems: [],
      id: "",
      spage: 1,
      stotalPage: 0
    };
  },

  methods: {
    open(purid) {
      this.id = purid;
    },
     makeimgurl(imgurl){
      return require("@/assets/file/"+imgurl);
    },
    getdetail(pid) {
      this.$router.push({
        name: "PostListDetail",
        params: { ID: pid },
      });
    },
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((res) => {
          this.email = res.data.email;
          this.countPage();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    countPage() {
      axios
        .get(`${baseURL}/purchase/count/sellList/${this.email}`)
        .then((res) => {
          this.stotalPage = res.data;
          this.checkPage();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkPage() {
      axios
        .get(`${baseURL}/purchase/sellList/${this.email}/${this.spage}`)
        .then((res) => {
          this.sitems = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    makeuse(purid, serialno) {
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
          axios
            .get(`${baseURL}/purchase/checkuse/${purid}/${serialno}`)
            .then((response) => {
              if (response.data != "") {
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
              } else {
                serialno = "";
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
                  icon: "error",
                  title: "일련번호가 일치하지 않습니다.",
                });
              }
              this.init();
            })
            .catch((error) => {
              console.log(error);
            });
        }
      });
    },
  },
  created() {
    this.authUser();
  },
  watch: {
    spage: function(v) {
      this.checkPage();
    }
  }
};
</script>

<style>
#tt td {
  text-align: center;
  vertical-align: middle;
}
</style>
