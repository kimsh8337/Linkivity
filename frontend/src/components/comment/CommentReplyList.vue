<template>
  <div>
      <div>
        <div class="card mt-4">
        <div class="card-header d-flex bg-white pl-0 pr-0 pb-1 pt-3">
            <!-- nickname 칸 -->
            <div class="mr-3 d-flex my-auto" style="width:15%">
            <strong class="text-dark my-auto" style="width:8rem; text-align:left;">{{reply.nickname}}</strong>
            </div>
            <!-- content 칸 -->
            <div class="ml-3 my-auto" style="width:50%">
            <div>
                <span class="comment d-flex">{{reply.content}}</span>
            </div>
            </div>
            <!-- 날짜와 대댓버튼 -->
            <div class="ml-auto my-auto datereply d-flex justify-content-end row" style="width:30%;">
            <div class="d-flex justify-content-end pl-0 col-12 col-xs-12">
                <small class="text-dark d-flex align-items-start">{{commentdate(reply.createDate)}}</small>
            </div>
            <!-- 댓글 수정 삭제 버튼 -->
            <div class="d-flex justify-content-end pl-0 my-auto col-12 questionbtn" style="word-break:nowrap;">
                <small v-if="nickNameCheck" @click="commentReplyModify" class="">
                    <span v-if="isUpdate" style="color:red">취소</span>
                    <span v-else style="color:gray;">수정</span>
                </small>
                <small @click="replyDelete(reply.rrid)" v-if="nickNameCheck" class="ml-2" style="color:gray">삭제</small>
                <small class="ml-2" style="color:gray">신고</small>
            </div>
            </div>
        </div>
        <!-- 댓글 수정 -->
        <div>
            <ul class="list-group list-group-flush">
            <li class="list-group-item d-flex p-1">
                <CommentReplyUpdate v-if="isUpdate" :reply="reply"/>
            </li>
            </ul>
        </div>
        <!--  -->
        </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import CommentReplyUpdate from './CommentReplyUpdate.vue'

const baseURL = "http://localhost:8080";

export default {
    components: {
        CommentReplyUpdate,
    },
    props: {
        reply: Object,
    },
    data() {
        return {
            nickName: "",
            nickNameCheck: false,
            isUpdate: false,
        }
    },
    methods: {
      commentdate(createDate){
        var cd = createDate+''
        return cd.substring(0,10)+'  '+ cd.substring(11,16)
      },
      authUser() {
        axios
            .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
            .then((response) => {
                this.nickName = response.data.nickname
                if (this.nickName == this.reply.nickname) {
                    this.nickNameCheck = true
                } else {
                    this.nickNameCheck = false
                }
            })
            .catch((err) => {
            console.log(err.response);
            });
        },
      replyDelete(rrid){
          this.$emit('reply-delete',rrid)
      },
      commentReplyModify() {
          this.isUpdate = !this.isUpdate
      },
    },
    created() {
        this.authUser()
    },
}
</script>

<style>

</style>