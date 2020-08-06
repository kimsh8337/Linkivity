<template>
  <div class="container">
    <table class="table" v-for="(item, index) in items" :key="index">
      <thead class="thead-dark">
        <tr>
          <th>No</th>
          <td>{{ index + 1 }}</td>
        </tr>
        <tr>
          <th>사진</th>
          <th>상품명</th>
          <th>기간</th>
          <th>가격</th>
          <th>일련번호</th>
          <th>사용여부</th>
        </tr>
      </thead>
      <tbody v-for="(itm, idx) in item" :key="idx">
        <tr id="tt">
          <td><img :src="itm.img" style="width: 100px; height: 100px;" /></td>
          <td>{{ itm.title }}</td>
          <td>{{ itm.sdate }} ~ {{ itm.edate }}</td>
          <td>{{ itm.price }}</td>
          <td>{{ itm.serialno }}</td>
          <td>
              <b-badge
                    v-if="itm.puse == 0"
                    pill
                    variant
                    style="background-color: #003399"
                  >미사용</b-badge>
              <b-badge
                    v-if="itm.puse == 1"
                    pill
                    variant
                    style="background-color: #C4302B"
                  >사용완료</b-badge>



          </td>
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
