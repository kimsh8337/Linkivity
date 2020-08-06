<template>
  <div class="container col-md-8">
    <div class="d-flex justify-content-between">
      <p class="notice">
        <i class="fas fa-flag mr-2"></i>Notice
      </p>
      <button class="btn btn-regist" @click="gonoticecreate"><i class="fas fa-pen mr-2"></i>공지사항 등록</button>
    </div>

    <table class="table">
        <thead class="thead-light">
            <tr>
            <th scope="col" class="table-num">Number</th>
            <th scope="col" class="table-title">Title</th>
            <th scope="col" class="table-date">Date</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(notice,index) in notices" :key="index">
            <th scope="row">{{index+1}}</th>
            <td class="notice-title" @click="gonoticedetail(notice.nid)">{{notice.title}}</td>
            <td>{{writeDate(notice.createDate)}}</td>
            </tr>
        </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";

import Swal from "sweetalert2";

const baseURL = "http://localhost:8080";

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
            // console.log(this.notices)
        })
        .catch((err) => {
          console.log(err);
        });
    },
    gonoticecreate(){
        this.$router.push('/noticecreate')
    },
    gonoticedetail(nid){
        this.$router.push({
            name: 'NoticeListDetail',
            params: { ID:nid },
        })
    },
    writeDate(createDate){
        var wd = createDate+"";
        return wd.substring(0,10);
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

.btn-regist{
    color: crimson;
    font-size: 0.9rem;
}

.table{
    margin-bottom:3rem;
}

.table-num{
    width: 15%;
}

.table-title{
    width: 70%;
}

.table-date{
    width: 15%;
}

.notice-title{
    text-align: left;
    cursor: pointer;
}
</style>