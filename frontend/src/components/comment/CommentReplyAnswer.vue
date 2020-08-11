<template>
  <div style="padding-left:20%">
    <!-- 대댓글 Input -->
    <CommentReplyInput :comment="comment" @creply-create="CommentReplyCreate"/>
    <!-- 대댓글 List -->
    <CommentReplyList />
  </div>
</template>

<script>
import CommentReplyInput from './CommentReplyInput.vue'
import CommentReplyList from './CommentReplyList.vue'

import axios from 'axios'
const baseURL = "http://localhost:8080";

export default {
    data: function() {
      return {
        nickname: "",
        commentrid: ""
      }
    },
    props: {
        comment: Object,
    },
    components: {
      CommentReplyInput,
      CommentReplyList,
    },
    methods: {
      authUser() {
        axios
          .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
          .then((response) => {
            this.fetchCommentReply()
            this.nickname = response.data.nickname
          })
          .catch((err) => {
            console.log(err.response);
          });
      },
      CommentReplyCreate(commentReplyData) {
        axios.post(`${baseURL}/reply/reRegist`,commentReplyData)
          .then((response) => {
            console.log(response.data)
          }).catch((err) => {
            console.log(err)
          })
      },
      fetchCommentReply() {
        axios.get(`${baseURL}/reply/reList/${this.commentrid}`)
          .then((response) => {
            console.log(response.data)
          }).catch((error) => {
            console.log(error)
          })
      },
    },
    created() {
      this.authUser()
      this.commentrid = this.comment.rid
    },
}
</script>

<style>

</style>