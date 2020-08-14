<template>
  <div class="modal fade" id="postReviewModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">
                <div class="d-flex">
                    <!-- profile img 보여주기 -->
                    <img v-if="reviewDetail.proimg" class="user-img d-flex m-1 mr-2" :src="reviewDetail.proimg" style="border-radius:70px;" />
                    <img v-if="!reviewDetail.proimg" class="user-img d-flex m-1 mr-2" src="../../assets/img/noimage.jpg" style="border-radius:70px;" />
                    <span class="my-auto mr-1" style="font-weight:bold">[{{this.reviewDetail.nickname}}]</span>
                    <small style="margin-top:6%">님이 남긴 후기 상세 정보</small>
                 </div>
            </h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <!-- modal body -->
        <div class="modal-body">
            <span class="d-flex mb-3" style="font-weight:bold">[후기 정보]</span>
            <div class="row mb-3">
                <!-- review에 남긴 img -->
                <img class="col-6" v-if="reviewDetail.img" :src="reviewDetail.img" style="width:40%; height:40%"/>
                <img class="col-6" v-if="!reviewDetail.img" src="../../assets/img/noimage.jpg" style="width:40%; height:40%"/>
                <div class="col-6">
                    <!-- review 제목 -->
                    <small class="d-flex mb-2" style="font-weight:bold">[후기 제목]</small>
                    <p class="d-flex mb-4" style="height:25%">{{this.reviewDetail.title}}</p>
                    <small class="d-flex mb-2" style="font-weight:bold">[내가 준 평점]</small>
                    <div class="d-flex">
                        <i class="fas fa-star" style="color:Salmon;" v-for="i in this.reviewDetail.star" :key="i.id"></i>
                    </div>
                </div>
            </div>
            <!-- 후기 내용 -->
            <small class="d-flex mb-2" style="font-weight:bold">[후기 내용]</small>
            <small class="d-flex">{{this.reviewDetail.content}}</small>

            <div class="width:80%">
                <hr>
            </div>

            <!-- 후기 남긴 Post 정보 -->
            <span class="d-flex mb-3" style="font-weight:bold">[후기를 남긴 게시글 정보]</span>
            <div class="row">
                <img v-if="this.postDetail.imgurl" class="col-6" :src="this.postDetail.imgurl" style="width:40%; height:40%">
                <img v-if="!this.postDetail.imgurl" class="col-6" src="../../assets/img/noimage.jpg" style="width:40%; height:40%">
                <div class="col-6">
                    <small class="d-flex mb-2" style="font-weight:bold">[게시글 제목]</small>
                    <small class="d-flex" style="font-size:0.9rem text-overflow:ellipsis; overflow: hidden; white-space: nowrap;">{{this.postDetail.title}}</small>
                </div>
            </div>
            <!-- {{this.postDetail}} -->


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
import axios from 'axios'

const baseURL = process.env.VUE_APP_BACKURL;

export default {
    data() {
        return {
            pid: "",
            postDetail: [],
        }
    },
    props: {
        reviewDetail: [Object, Array],
    },
    methods: {
        bringPostDetail() {
            axios.get(`${baseURL}/post/detail/${this.pid}`)
                .then((response) => {
                    this.postDetail = response.data
                }).catch((error) => {
                    console.log(error)
                })
        },
    },
    created() {

    },
    updated() {
        this.pid = this.reviewDetail.pid
        this.bringPostDetail()
    },
}
</script>

<style>
.user-img {
  width: 50px;
  height: 50px;
}
</style>