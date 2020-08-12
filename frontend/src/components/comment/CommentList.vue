<template>
  <div>
    <div class="card mt-4">
      <div class="card-header d-flex bg-white pl-0 pr-0 pb-1 pt-3">
        <!-- nickname 칸 -->
        <div class="mr-3 d-flex my-auto" style="width:15%">
          <strong class="text-dark my-auto" style="width:8rem; text-align:left;">{{comment.nickname}}</strong>
        </div>
        <!-- content 칸 -->
        <div class="ml-3 my-auto" style="width:50%">
          <div>
            <span class="comment d-flex">{{comment.content}}</span>
          </div>
        </div>
        <!-- 날짜와 대댓버튼 -->
        <div class="ml-auto my-auto datereply d-flex justify-content-end row" style="width:30%;">
          <div class="d-flex justify-content-end pl-0 col-12">
            <small class="text-dark d-flex align-items-start">{{commentdate(comment.createDate)}}</small>
            <small @click="replyInputCheck"><i class="fas fa-reply-all rereply ml-2 my-auto" title="답글 펼치기"></i></small>
          </div>
          <!-- 댓글 수정 삭제 버튼 -->
          <div class="d-flex justify-content-end pl-0 my-auto col-12 questionbtn" style="word-break:nowrap;">
            <small v-if="NickNameCheck" @click="commentModify">
              <span v-if="isUpdated" style="color:red">취소</span>
              <span v-else style="color:LimeGreen">수정</span>
            </small>
            <small v-if="NickNameCheck" class="ml-2" style="color:Crimson" @click="commentDelete">삭제</small>
            <small class="ml-2" style="color:Maroon">신고</small>
          </div>
        </div>
      </div>
      <!-- 댓글 수정 -->
      <div>
        <ul class="list-group list-group-flush">
          <li class="list-group-item d-flex p-1">
            <CommentUpdate v-if="isUpdated" :comment="comment"/>
          </li>
        </ul>
      </div>
      <!--  -->
    </div>
    <CommentReplyAnswer v-if="replyCheck" :comment="comment"/>
  </div>
</template>

<script>
import axios from 'axios'
import CommentUpdate from './CommentUpdate'
import CommentReplyAnswer from './CommentReplyAnswer.vue'

const baseURL = "http://localhost:8080";

export default {
  components: {
    CommentUpdate,
    CommentReplyAnswer,
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
      replyCheck: false,
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
    },
    replyInputCheck() {
      this.replyCheck = !this.replyCheck
    },
  },
  created() {
    if(this.$cookies.get("Auth-Token")!=null)
    this.authUser();
  },
}
</script>

<style>
.rereply {
  cursor: pointer;
  transform: rotateX(180deg);
  -webkit-transform:rotateX(180deg);
  -moz-transform:rotateX(180deg);
}
.comment {
  word-break: break-all;
}
.questionbtn {
  cursor: pointer;
}
</style>