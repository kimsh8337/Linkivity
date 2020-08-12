<template>
  <div class="input-group">
    <!-- label -->
    <div class="input-group-prepend my-auto">
      <span class="input-group-text bg-white" style="height: 2.5rem; border: none;">Question</span>
    </div>
    <!-- content -->
    <textarea
      class="form-control my-auto"
      style="height: 2.5rem; border:none !important; "
      aria-label="With textarea"
      v-model="commentData.content"
      placeholder="질문을 남겨주세요!"
    ></textarea>
    <!-- input button -->
    <div class="input-group-prepend buttonComment my-auto" style="border:none;"> 
      <span
        class="input-group-text bg-white pr-3"
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
    };
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
            this.email = response.data.email;
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