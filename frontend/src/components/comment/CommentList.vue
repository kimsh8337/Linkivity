<template>
  <div>
    <div class="card mt-4">
      <div class="card-header d-flex bg-white">
        <strong class="mr-3 text-dark" style="width:8rem; text-align:left;">{{comment.nickname}}</strong>
        <span>{{comment.content}}</span>
        <small class="my-auto ml-auto text-dark">{{commentdate(comment.createDate)}}</small>
      </div>
      <ul class="list-group list-group-flush">
        <li class="list-group-item d-flex p-1">
          <CommentUpdate v-if="isUpdated" :comment="comment" @update-comment="commentModify" />
          <span v-else class="text-dark"></span>
        </li>
      </ul>
    </div>
    <div v-if="NickNameCheck" class="d-flex">
    <small
        class="ml-auto mr-2 badge commentModify btn btn-outline-success"
        @click="commentModify"
    >
        <span v-if="isUpdated">취소</span>
        <span v-else @click="fetchCommentRID">수정</span>
    </small>
    <small class="badge commentDelete btn btn-outline-danger" @click="commentDelete">삭제</small>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import CommentUpdate from './CommentUpdate'

const baseURL = "http://localhost:8080";

export default {
  components: {
    CommentUpdate,
  },
  props: {
    comment: Object,
  },
  data: function(){
    return {
      NickName: "",
      NickNameCheck: false,
      isUpdated: false,
      email: '',
      rid:'',
    }
  },
  methods: {
    authUser() {
      axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
            this.email = response.data.email;
            this.fetchNickName()
        })
        .catch((err) => {
          console.log(err.response);
        });
    },
    commentDelete: function() {
      this.$emit('comment-delete',this.comment)
    },
    commentModify() {
      this.isUpdated = !this.isUpdated
    },
    fetchNickName(){
        axios.get(`${baseURL}/account/getNickname/${this.email}`)
            .then((response) => {
              this.NickName = response.data
              if (this.NickName == this.comment.nickname) {
                this.NickNameCheck = true
              } else {
                this.NickNameCheck = false
              }
            })
            .catch((err) => {
              console.log(err.response.data)
            });
    },
    fetchCommentRID() {
      this.rid = this.comment.rid
    },
    commentdate(createDate){
      var cd = createDate+''
      return cd.substring(0,10)+'  '+ cd.substring(11,16)
    }
  },
  created() {
    this.authUser();
    
  },
}
</script>

<style>

</style>