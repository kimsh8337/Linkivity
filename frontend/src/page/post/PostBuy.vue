<template>
  <div class="container">
    <table class="table" v-for="(item, index) in items" :key="index">
      <thead class="thead-dark">
        <tr>
          <th>No</th>
          <td>{{ index + 1 }}</td>
        </tr>
        <tr>
          <th>Image</th>
          <th>Activity</th>
          <th>Date</th>
          <th>Price</th>
          <th>Code</th>
          <th>Use</th>
        </tr>
      </thead>
      <tbody v-for="(itm, idx) in item" :key="idx">
        <tr id="tt" @click="goDetail(itm.pid)">
          <td><img :src="itm.img" style="width: 100px; height: 100px;" /></td>
          <td>{{ itm.title }}</td>
          <td>{{ itm.sdate }} ~ {{ itm.edate }}</td>
          <td>{{ itm.price }}</td>
          <td>{{ itm.serialno }}</td>
          <td>{{ itm.puse }}</td>
        </tr>
      </tbody>
    </table>
    <br />
  </div>
</template>

<script>
import axios from 'axios';

const baseURL = 'http://localhost:8080';

export default {
  data() {
    return {
      num: 2,
      items: [],
    };
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get('Auth-Token')}`)
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
        .get(`${baseURL}/purchase/list/${this.email}`)
        .then((res) => {
          console.log(res.data);
          this.items = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goDetail(pid) {
      scroll(0, 0);
      this.$router.push({
        name: 'PostListDetail',
        params: { ID: pid },
      });
    },
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
#tt:hover {
  background-color: rgba(127, 172, 255, 0.25);
}
</style>
