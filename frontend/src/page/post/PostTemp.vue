<template>
  <div>
    <!-- 임시저장 List가 없을 때 -->
    <div class="mt-5 mb-5" v-if="temps.length <= 0">
      <i class="fas fa-surprise mt-5 mr-2 mb-5"></i
      ><span style="font-weight:bold">임시저장한 게시글이 없습니다.</span
      ><i class="fas fa-surprise ml-2"></i>
    </div>
    <!-- 임시저장 List가 있을 때 -->
    <div class="container" v-if="temps.length > 0">
      <table class="table">
        <tr v-if="temps.length > 0">
          <td>제목</td>
          <td>작성일시</td>
          <td></td>
        </tr>
        <tr v-for="(temp, index) in temps" :key="index">
          <td>
            <router-link
              v-if="temp.pid"
              v-bind:to="{ name: 'PostTempDetail', params: { ID: temp.pid } }"
              class="btn--text"
              >{{ temp.title }}</router-link
            >
          </td>
          <td>{{ tempDate(temp.createDate) }}</td>
          <td><small>수정 /</small><small class="ml-1">삭제</small></td>
        </tr>
      </table>

      <!-- <br />
    <br />
    <br /> -->
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
    };
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
          this.email = response.data.email;
          this.init();
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    init() {
      axios
        .get(`${baseURL}/temp/list/${this.email}`)
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
  },
  created() {
    this.authUser();
  },
};
</script>
