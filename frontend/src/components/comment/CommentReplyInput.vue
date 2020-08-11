<template>
    <div class="form-row align-items-center d-flex justify-content-end">
        <div class="col-auto" style="width:100%">
        <label class="sr-only" for="inlineFormInputGroup"></label>
        <div class="input-group mb-2">
            <div class="input-group-prepend">
            <div class="input-group-text">Anwser</div>
            </div>
            <textarea v-model="commentReplyData.content" style="height:2.5rem;" type="text" class="form-control" id="inlineFormInputGroup" placeholder="답글을 남겨주세요!"></textarea>
            <!-- button -->
            <div class="input-group-prepend buttonComment my-auto" style="border:none;"> 
            <span
                class="input-group-text bg-white pr-3"
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
const baseURL = "http://localhost:8080";
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
          this.$emit('creply-create',this.commentReplyData)
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