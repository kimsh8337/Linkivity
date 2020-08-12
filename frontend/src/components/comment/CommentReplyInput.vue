<template>
    <div class="form-row align-items-center d-flex justify-content-end">
        <div class="col-auto" style="width:100%">
        <label class="sr-only" for="inlineFormInputGroup"></label>
        <div class="input-group mb-2">
            <div class="input-group-prepend">
            <div class="input-group-text my-auto">Anwser</div>
            </div>
            <textarea v-model="commentReplyData.content" style="height:2.5rem; border:none !important" type="text" class="form-control my-auto" id="inlineFormInputGroup" placeholder="답글을 남겨주세요!"></textarea>
            <!-- button -->
            <div class="input-group-prepend buttonComment my-auto" style="border:none;"> 
            <span
                class="input-group-text bg-white pr-3 my-auto"
                style="height: 2.5rem; border:none; border-top-right-radius: 5px; border-bottom-right-radius: 5px;"
                @click="CommentReplyCreate"
            >
                <i class="far fa-keyboard" style="font-size:25px"></i>
            </span>
            </div>
        </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
const baseURL = process.env.VUE_APP_BACKURL;
export default {
    data() {
        return {
            commentReplyData: {
                rid:"",
                nickname: "",
                content: "",
            },
        }
    },
    props: {
        comment:Object,
    },
    methods: {
        authUser() {
            axios
        .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
        .then((response) => {
            this.commentReplyData.nickname = response.data.nickname
        })
        .catch((err) => {
            console.log(err.response);
        });
      },
      CommentReplyCreate() {
          if (this.commentReplyData.content == "") {
            Swal.fire({
                width: 250,
                icon: 'error',
                text: '답글을 남겨주세요!',
                confirmButtonText: '<a style="font-size:1rem; color:black; width:0.5rem">확인</a>',
            })
          } else {
            this.$emit('creply-create',this.commentReplyData)
          }
      },
    },
    created() {
        this.commentReplyData.rid = this.comment.rid
        this.authUser()
    },
}
</script>

<style>

</style>