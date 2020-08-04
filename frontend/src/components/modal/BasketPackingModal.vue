<template>
  <!-- Modal -->
<div class="modal fade" id="BasketPackingModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content" style="margin:auto;height:40rem; width:45rem;">
      <div class="modal-header pl-0">
        <h5 class="modal-title w-100 text-center font-weight-bold position-absolute" id="exampleModalLabel">Packing List</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <!-- {{checked}} -->
          <!-- <div v-if="this.checked.changetf"> -->
          <div v-for="(post, index) in checked" :key="index">
              <img :src="post.imgurl" alt="" @click="getdetail(post.pid)" style="width:4rem; height:3rem;">
              <div type="text" class="basket-list col-md-8" aria-label="Text input with checkbox" @click="getdetail(post.pid)">
                <p class="mb-0">제목 : {{post.title}}</p>
                <p class="mb-0">기간 : {{post.sdate}}~{{post.edate}}</p>
                <p class="mb-0">위치 : {{post.location}}</p>
                <p class="mb-0">가격 : {{post.price}}</p>
              </div>

          </div>
        <!-- </div> -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import axios from "axios";

const baseURL = "http://localhost:8080";

export default {
  created(){
    this.email = this.$cookies.get("User");
    this.init();
  },
  // props:{
  //   checked:Array,
  // },
  data(){
    return{
      checked:[],
      sum:0,
    }
  },
  methods: {
    init() {
      axios
        .get(`${baseURL}/cart/list/${this.email}`)
        .then((res) => {
          this.checked = res.data;
          this.no = res.data.index;
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

}
};
</script>

<style>

</style>