<template>
  <div class="container">
    <table class="table">
      <tr v-if="temps.length > 0">
        <td>제목</td>
        <td>작성일시</td>
      </tr>
      <tr v-for="(temp, index) in temps" :key="index">
        <td>
          <router-link v-if="temp.pid" v-bind:to="{ name: 'PostTempDetail', params: { ID: temp.pid } }" class="btn--text">{{
            temp.title
          }}</router-link>
        </td>
        <td>{{ temp.createDate }}</td>
      </tr>
    </table>

    <br />
    <br />
    <br />
  </div>
</template>

<script>
import '../../assets/css/postlist.css';
import axios from 'axios';

const baseURL = 'http://localhost:8080';

export default {
  data() {
    return {
      temps: {
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
        createDate: '',
      },
      email: '',
    };
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get('Auth-Token')}`)
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
  },
  created() {
    this.authUser();
  },
};
</script>
