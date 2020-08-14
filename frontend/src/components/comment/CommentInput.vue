<template>
  <div class="d-flex justify-content-between m-0 mt-2 mb-2 p-0">
    <!-- label -->
    <!-- <div class="input-group-prepend my-auto">
      
      <strong class="text-dark my-auto" style="height: 2.5rem; border: none;">{{nickname}}</strong>
    </div>-->
    <!-- content -->
    <textarea
      class="form-control my-auto p-1"
      style="height: 3.5rem; border-radius : 0px; width:90%;!important"
      aria-label="With textarea"
      v-model="commentData.content"
      placeholder="질문을 남겨주세요."
    ></textarea>
    <!-- input button -->
    <div
      class="buttonComment d-flex align-items-center buttonComment"
      style="height:3.5rem; width:10%;"
    >
      <span
        class="bg-white mx-auto"
        style="height: 2.5rem; border:none; border-top-right-radius: 5px; border-bottom-right-radius: 5px;"
        @click="createComment"
      >
        <i class="far fa-keyboard" style="font-size:25px"></i>
      </span>
    </div>
  </div>
</template>

<script>
import axios from "axios";
const baseURL = process.env.VUE_APP_BACKURL;
export default {
  data: function () {
    return {
      commentData: {
        pid: "",
        nickname: "",
        content: "",
      },
      email: "",
      nickname: "",
    };
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
          this.email = response.data.email;
          this.nickname = response.data.nickname;
          this.fetchNickName();
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    fetchNickName() {
      axios
        .get(`${baseURL}/account/getNickname/${this.email}`)
        .then((response) => {
          this.commentData.nickname = response.data;
        })
        .catch((err) => {
          console.log(err.response.data);
        });
    },
    createComment: function () {
      this.$emit("create-comment", this.commentData);
    },
  },
  created() {
    this.commentData.pid = this.$route.params.ID;
    this.authUser();
  },
};
</script>

<style>
.buttonComment {
  cursor: pointer;
}
</style>