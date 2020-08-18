<template>
  <div>
    <!-- 임시저장 List가 없을 때 -->
    <div class="mt-5 mb-5" v-if="temps.length <= 0">
      <i class="fas fa-surprise mt-5 mr-2 mb-5"></i
      ><span style="font-weight:bold">임시저장한 게시글이 없습니다.</span
      ><i class="fas fa-surprise ml-2"></i>
    </div>
    <!-- 임시저장 List가 있을 때 -->
    <div class="container p-0" v-if="temps.length > 0">
      <table class="table">
        <tr class="bg-dark text-white" v-if="temps.length > 0">
          <td>제목</td>
          <td>작성일시</td>
          <td>수정 / 삭제</td>
        </tr>
        <tr v-for="(temp, index) in temps" :key="index">
          <td>
            <router-link
              v-if="temp.pid"
              style="color:black"
              v-bind:to="{ name: 'PostTempDetail', params: { ID: temp.pid } }"
              class="btn--text"
              >{{ temp.title }}</router-link
            >
          </td>
          <td>{{ tempDate(temp.createDate) }}</td>
          <td>
            <span class="mr-2 badge" style="color:lightblue; cursor: pointer; font-size:0.9rem;" @click="tempUpdate(temp.pid)"
              >수정</span
            >
            <span>/</span>
            <span
              style="color:pink; cursor: pointer; font-size:0.9rem;"
              class="ml-2 badge"
              @click="tempDelete(temp.pid)"
              >삭제</span
            >
          </td>
        </tr>
      </table>

      <!-- paging -->
      <b-pagination class="mt-5 mb-0" v-if="ttotalPage > 10" v-model="tpage" :total-rows="ttotalPage" pills :per-page="10"></b-pagination>
    </div>
  </div>
</template>

<script>
import "../../assets/css/postlist.css";
import axios from "axios";

const baseURL = process.env.VUE_APP_BACKURL;

export default {
  data() {
    return {
      temps: {
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
        createDate: "",
      },
      email: "",
      tpage: 1,
      ttotalPage: 0
    };
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
          this.email = response.data.email;
          this.countPage();
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    countPage() {
      axios
        .get(`${baseURL}/temp/count/list/${this.email}`)
        .then((res) => {
          this.ttotalPage = res.data;
          this.checkPage();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    checkPage() {
      axios
        .get(`${baseURL}/temp/list/${this.email}/${this.tpage}`)
        .then((res) => {
          this.temps = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    tempDate(date) {
      var datecut = date + "";
      return datecut.substring(0, 10);
    },
    tempUpdate(pid) {
      scroll(0, 0);
      this.$router.push({
        name: "PostTempDetail",
        params: { ID: pid },
      });
    },
    tempDelete(pid) {
      Swal.fire({
        width: 350,
        text: "삭제하시겠습니까?",
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
          });
          Toast.fire({
            icon: "success",
            title: "임시저장 글이 삭제되었습니다.",
          });
          axios
            .delete(`${baseURL}/post/delete/${pid}`)
            .then(() => {
              this.checkPage();
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
    tpage: function(v) {
      this.checkPage();
    }
  },
};
</script>
