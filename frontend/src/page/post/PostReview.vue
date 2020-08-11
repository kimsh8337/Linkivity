<template>
  <div>
    <div class="row justify-content-left" v-if="reviews.length > 0">
      <div
        v-for="(slide, index) in reviews"
        :key="index"
        class="col-12 col-sm-12 col-md-3 p-3"
      >
        <!-- <div class="card mb-3 profile-post mr-0 ml-0"> -->
          <div class="card-body" style="padding: 0; cursor: pointer;" @click="getdetail(slide.pid)">
            <!-- img 보여주기 -->
            <img v-if="slide.img" class="review-img" :src="slide.img" :alt="slide.title" style="height:8rem;"/>
            <img v-if="!slide.img" class="review-img" src="../../assets/img/noimage.jpg" style="height:8rem;" />
              
            <!-- 프로필 보여주기 -->
            <div class="d-flex justify-content-between">
              <img class="user-img d-flex m-3" :src="slide.proimg" style="border-radius:70px;" />
              <div class="mt-2 mr-4">
                <div class="d-flex">
                  <small
                    class="d-flex align-items-center"
                    style="font-weight:bold"
                  >{{slide.nickname}}</small>
                  <br />
                </div>
                <div class="d-flex">
                  <i class="fas fa-star" style="color:Salmon" v-for="i in slide.star" :key="i.id"></i>
                </div>
                <div class="d-flex align-items-end mt-1 mr-3">
                  <small style="font-weight:bold">{{datecut(slide.createDate)}}</small>
                </div>
              </div>
            </div>
            <!-- 제목 -->
            <div class="d-flex mt-2 ml-2 p-2">
              <span style="font-weight:bold;">{{slide.title}}</span>
            </div>
          </div>
        <!-- </div> -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const baseURL = "http://localhost:8080";

export default {
  data() {
    return {
      reviews: [],
      email: "",
    };
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
          this.email = response.data.email;
          this.fetchReview();
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    fetchReview() {
      axios
        .get(`${baseURL}/review/listbyemail/${this.email}`)
        .then((response) => {
          this.reviews = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    datecut(date) {
      var tempdatecut = date + "";
      return tempdatecut.substring(0, 10);
    },
    getdetail(pid) {
      scroll(0, 0);
      this.$router.push({
        name: "PostListDetail",
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
.review-img {
  width: 100%;
  height: 8rem;
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.15);
}
.user-img {
  width: 50px;
  height: 50px;
}
</style>