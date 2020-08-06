<template>
  <div class="container col-md-8">
    <div class="d-flex justify-content-between">
      <p class="notice"><i class="fas fa-flag mr-2"></i>Notice</p>
      <button class="btn btn-regist" @click="gonoticecreate"><i class="fas fa-pen mr-2"></i>공지사항 등록</button>
    </div>

    <table class="table">
      <thead class="thead-light">
        <tr>
          <th scope="col" class="table-num">No</th>
          <th scope="col" class="table-title">Title</th>
          <th scope="col" class="table-date">Date</th>
          <th scope="col" class="table-date">Visit</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(notice, index) in notices" :key="index">
          <th scope="row">{{ index + 1 }}</th>
          <td class="notice-title" @click="gonoticedetail(notice.nid)">{{ notice.title }}</td>
          <td>{{ writeDate(notice.createDate) }}</td>
          <td>{{ notice.visit }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

import Swal from 'sweetalert2';

const baseURL = 'http://localhost:8080';

export default {
  data() {
    return {
      page: 1,
      notices: {
        nid: '',
        title: '',
        content: '',
        visit: '',
        createDate: '',
      },
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      axios
        .get(`${baseURL}/notice/list/${this.page}`)
        .then((res) => {
          this.notices = res.data;
          console.log(this.notices);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    gonoticecreate() {
      this.$router.push('/noticecreate');
    },
    gonoticedetail(nid) {
      axios
        .put(`${baseURL}/notice/visitPlus/${nid}`)
        .then((res) => {
          this.notices.visit = res.data;
        })
        .catch((err) => {
          console.log(err);
        });

      this.$router.push({
        name: 'NoticeListDetail',
        params: { ID: nid },
      });
    },
    writeDate(createDate) {
      var wd = createDate + '';
      return wd.substring(0, 10);
    },
  },
};
</script>

<style scoped>
.notice {
  font-size: 1.5rem;
  font-weight: bold;
  text-align: left;
}

.btn-regist {
  color: crimson;
  font-size: 0.9rem;
}

.table {
  margin-bottom: 3rem;
}

.table-num {
  width: 20%;
}

.table-title {
  width: 60%;
}

.table-date {
  width: 20%;
}

.notice-title {
  text-align: left;
  cursor: pointer;
}
</style>
