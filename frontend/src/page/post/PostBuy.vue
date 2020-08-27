<template>
  <div>
    <!-- 구매목록이 없을 때 -->
    <div class="mt-5 mb-5" v-if="bitems.length <= 0">
      <i class="fas fa-surprise mt-5 mr-2"></i>
      <span style="font-weight:bold">구매한 상품이 없습니다.</span>
      <i class="fas fa-surprise ml-2"></i>
      <br />
      <button
        @click="goPost"
        class="btn mt-2 mb-4"
        style="font-weight:bold; color:white; background-color:RGB(134, 165, 212); border-radius:7px"
      >
        <i class="fas fa-shopping-cart mr-2"></i>상품 구매하러 가기
      </button>
    </div>

    <!-- 구매목록이 있을때 -->

    <!-- 웹버전 -->
    <div class="Webtable d-none d-sm-block">
      <div class="" v-if="bitems.length > 0">
        <table class="table" v-for="(item, index) in bitems" :key="index">
          <thead class="thead" style="background:RGB(134, 165, 212); color:white; font-weight:bold;">
            <tr>
              <td>No {{ (bpage - 1) * 5 + index + 1 }}</td>
              <td>수량 : {{ item[0].amount }}</td>
              <td><button class="btn btn-danger btn-sm pt-0 pb-0" @click="packDelete(item[0].packno)" v-if="checkpuse(item)" style="height:25px;font-weight:bold;">구매 취소</button></td>
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
              <td>
                <img
                  v-if="itm.img"
                  :src="makeimgurl(itm.img)"
                  @click="goDetail(itm.pid)"
                  style="width: 100px; height: 100px; cursor:pointer;"
                />
              </td>
              <td>
                <div @click="goDetail(itm.pid)" style="cursor:pointer;font-weight:bold;">{{ itm.title }}</div>
              </td>
              <td style="font-weight:bold;">{{ itm.sdate }} ~ {{ itm.edate }}</td>
              <td style="font-weight:bold;">{{ addComma(itm.price) }}</td>
              <td style="font-weight:bold;">{{ itm.serialno }}</td>
              <td>
                <b-badge v-if="itm.puse == 0" pill variant style="background-color: #003399">미사용</b-badge>
                <b-badge v-if="itm.puse == 1" pill variant style="background-color: #C4302B">사용완료</b-badge>
              </td>
            </tr>
          </tbody>
        </table>
        <br />
        <!-- paging -->
        <b-pagination class="mt-5 mb-0" v-if="btotalPage > 5" v-model="bpage" :total-rows="btotalPage" pills :per-page="5"></b-pagination>
      </div>
    </div>

    <!-- 모바일버전 -->
    <div class="MoblieCard d-block d-sm-none d-md-none">
      <table class="table" v-for="(item, index) in bitems" :key="index">
        <thead class="thead" style="background:RGB(134, 165, 212); color:white;">
          <tr>
            <th>No {{ (bpage - 1) * 5 + index + 1 }}</th>
            <td>수량 : {{ item[0].amount }}</td>
          </tr>
        </thead>
        <div class="container row p-2" @click="goDetail(itm.pid)" style="width:210%" v-for="(itm, idx) in item" :key="idx">
          <div class="col-6 p-2 d-flex align-items-center">
            <img v-if="itm.img" :src="makeimgurl(itm.img)" style="width:100%; heigh:100%;" />
            <img v-if="!itm.img" src="../../assets/img/noimage.jpg" style="width:100%; heigh:100%;" />
          </div>
          <div class="col-6 p-2 d-flex align-items-center">
            <div class="" style="text-align:left">
              <b-badge v-if="itm.puse == 0" pill variant style="background-color: #003399">미사용</b-badge>
              <b-badge v-if="itm.puse == 1" pill variant style="background-color: #C4302B">사용완료</b-badge>
              <br />
              <div class="mt-1 mb-1">
                <span style="font-size:1rem; font-weight:bold;" class="white-space:pre;">상품명 : [{{ itm.title }}]</span>
              </div>
              <div class="mt-1 mb-1">
                <span style="font-weight:bold;">가격 : {{ addComma(itm.price) }}원</span>
              </div>
              <div class="mt-1 mb-1">
                <span style="font-weight:bold;">일련번호 : {{ itm.serialno }}</span>
              </div>
              <div class="mt-1 mb-1">
                <small style="font-weight:bold;">시작일 : {{ itm.sdate }}</small>
              </div>
              <div class="mt-1 mb-1">
                <small style="font-weight:bold;">종료일 : {{ itm.edate }}</small>
              </div>
            </div>
          </div>
        <div class="col-12 d-flex justify-content-end p-0">
          <button class="btn btn-danger btn-sm ml-2" @click="packDelete(item[0].packno)" v-if="checkpuse(item)" style="height:25px;font-weight:bold;">구매 취소</button>
        </div>
        </div>
      </table>
      <!-- paging -->
        <b-pagination class="mt-5 mb-0" v-if="btotalPage > 5" v-model="bpage" :total-rows="btotalPage" pills :per-page="5"></b-pagination>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

const baseURL = process.env.VUE_APP_BACKURL;

export default {
  data() {
    return {
      bitems: [],
      bpage: 1,
      btotalPage: 0,
    };
  },
  methods: {
    addComma(num) {
      num = num + "";
      var regexp = /\B(?=(\d{3})+(?!\d))/g;
      return num.toString().replace(regexp, ',');
    },
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get('Auth-Token')}`)
        .then((res) => {
          this.email = res.data.email;
          this.pageCount();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    makeimgurl(imgurl) {
      var url = '../../../contents/' + imgurl;
      return url;
    },
    packDelete(packno) {
      Swal.fire({
        width: 350,
        text: '패키지를 환불하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#fff',
        cancelButtonColor: '#fff',
        confirmButtonText: '<a style="font-size:1rem; color:black">환불</a>',
        cancelButtonText: '<a style="font-size:1rem; color:black">취소</a>',
      }).then((result) => {
        if (result.value) {
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer);
              toast.addEventListener('mouseleave', Swal.resumeTimer);
            },
          });
          Toast.fire({
            icon: 'success',
            title: '환불되었습니다.',
          });
          axios
            .delete(`${baseURL}/purchase/delete/` + packno)
            .then((res) => {
              this.pageCount();
            })
            .catch((err) => {
              console.log(err);
            });
        }
      });
    },
    pageCount() {
      axios
        .get(`${baseURL}/purchase/count/list/${this.email}`)
        .then((res) => {
          this.btotalPage = res.data;
          this.pageCheck();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    pageCheck() {
      axios
        .get(`${baseURL}/purchase/list/${this.email}/${this.bpage}`)
        .then((res) => {
          this.bitems = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goDetail(pid) {
      axios
        .get(`${baseURL}/post/detail/${pid}`)
        .then((res) => {
          scroll(0, 0);
          this.$router.push({
            name: 'PostListDetail',
            params: { ID: pid },
          });
        })
        .catch((err) => {
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 1000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer);
              toast.addEventListener('mouseleave', Swal.resumeTimer);
            },
          });

          Toast.fire({
            icon: 'error',
            title: '해당 상품은 삭제된 상품입니다.',
          });
          setTimeout(() => {
            th.$router.go();
          }, 1000);
        });
    },
    goPost() {
      this.$router.push('/posts');
    },
    checkpuse(item){
      var i = 0;
      for(i=0;i<item.length;i++){
        if(item[i].puse == 1){
          return false
        }
    }
      return true
  },
  },
  created() {
    this.authUser();
  },
  watch: {
    bpage: function(v) {
      this.pageCheck();
    },
  },
};
</script>

<style>
#tt td {
  text-align: center;
  vertical-align: middle;
}
</style>
