<template>
  <div>
    <div class="row justify-content-left" v-if="cartPosts.length > 0">
      <div class="col-12 col-sm-12 col-md-3 card-deck" style="margin:auto 0;" v-for="(cartPost, cindex) in cartPosts" :key="cindex">
        <div class="card mb-3 profile-post mr-0 ml-0" >
          <div class="card-body" style="padding: 0;">
            <img :src="cartPost.imgurl" class="card-img" style="height:10rem" />
            <div
              class="card-img-overlay"
              @click="getdetail(cartPost.pid)"
              style="padding:4rem 0; text-align:center; font-size:1.3rem; font-weight:bold; color: white;"
            ></div>
            <div class="col-md-12 p-0">
              <div class="card-body" style="padding: 5px;">
                <p
                  class="card-text mb-2"
                  style="font-size: 1rem; text-align: left; text-overflow:ellipsis;overflow: hidden;white-space: nowrap; color:gray"
                >
                  {{ cartPost.sdate }}~{{ cartPost.edate }}
                </p>
                <h5
                  class="card-title"
                  @click="getdetail(cartPost.pid)"
                  style="font-size: 1rem; text-align: left; margin-bottom: 1rem; text-overflow:ellipsis;overflow: hidden;white-space: nowrap;"
                >
                  {{ cartPost.title }}
                </h5>
                <div class="text d-flex justify-content-between">
                  <p
                    class="card-text"
                    style="font-size: 1rem; text-align: left; text-overflow:ellipsis;overflow: hidden;white-space: nowrap;"
                  >
                    가격 : {{ cartPost.price }}
                  </p>
                  <button type="button" class="btn btn-outline-danger btn-sm" style="height:30px;" @click="deleteCart(cartPost.pid)">삭제</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- paging -->
    <b-pagination v-if="ctotalPage > 8" v-model="cpage" :total-rows="ctotalPage" pills :per-page="8"></b-pagination>
    <br />
    <br />
    <br />
  </div>
</template>

<script>
// import '../../assets/css/postlist.css';
import axios from 'axios';
import BPagenation from 'bootstrap-vue';
import Swal from 'sweetalert2';

const baseURL = 'http://localhost:8080';

export default {
  components: {
    BPagenation,
  },
  data() {
    return {
      cartPosts: {
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
      },
      cpage: 1,
      ctotalPage: 0,
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
        .get(`${baseURL}/cart/list/${this.email}/${this.cpage}`)
        .then((res) => {
          this.cartPosts = res.data;
        })
        .catch((err) => {
          console.log(err);
        });

      axios
        .get(`${baseURL}/cart/count/${this.email}`)
        .then((res) => {
          this.ctotalPage = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getdetail(pid) {
      this.$router.push({
        name: 'PostListDetail',
        params: { ID: pid },
      });
    },
    pageClick(pageNum) {
      this.page = pageNum - 1;
    },
    checkPage() {
      axios
        .get(`${baseURL}/cart/list/${this.email}/${this.cpage}`)
        .then((res) => {
          this.cartPosts = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    deleteCart(pid) {
      Swal.fire({
        width: 350,
        text: '삭제하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '<a style="font-size:1rem; color:black">Delete</a>',
        cancelButtonText: '<a style="font-size:1rem; color:black">Cancel</a>',
      }).then((result) => {
        if (result.value) {
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer);
              toast.addEventListener('mouseleave', Swal.resumeTimer);
            },
          });
          Toast.fire({
            icon: 'success',
            title: '삭제되었습니다.',
          });
          axios
            .delete(`${baseURL}/cart/deleteCart/${pid}/${this.email}/1`)
            .then((res) => {
              this.init();
            })
            .catch((err) => {
              console.log(err);
            });
        }
      });
    },
  },
  watch: {
    cpage: function(v) {
      this.checkPage();
    },
  },
  created() {
    this.authUser();
  },
};
</script>

<style></style>
