<template>
<div>
  <div v-if="slides.length == 0">
    <i class="far fa-surprise mr-1 mb-3"></i>등록된 후기가 없습니다. 처음으로 후기를 남겨보세요!<i class="far fa-surprise ml-1"></i>
  </div>
  <div v-if="slides.length > 0" id="slider" class="slider" @mousemove="mouseMoving" @mouseout="stopDrag" @mouseup="stopDrag" @mouseLeave="stopDrag">
  <div class="slider-cards" :style="`transform: translate3d(${cardsX}px,0,0)`">
    <div @mousedown="startDrag"
      @mouseup="stopDrag"
      v-for="(slide, rvid) in slides" 
      :key="rvid"
      class="slider-card">
      <!-- img 보여주기 -->
      <img class="review-img" style="width:100%" :src="slide.img" :alt="slide.title" draggable="false">
      <!-- 프로필 보여주기 -->
      <div class="d-flex justify-content-between">
        <img class="profile-img d-flex m-1" :src="slide.proimg">
        <div class="mt-2 mr-4">
            <div class="d-flex">
                <small class="d-flex align-items-center" style="font-weight:bold">{{slide.nickname}}</small><br>
            </div>
            <div class="d-flex">
                <i class="fas fa-star" style="color:Salmon;" v-for="i in slide.star" :key="i.id"></i>
            </div>
        </div>
        <!-- 날짜 및 수정 삭제 -->
        <div v-if="email == slide.email" class="mt-2">
          <div class="d-flex justify-content-end mr-3">
            <small @click="update(slide)" data-toggle="modal" data-target="#reviewUpdate" style="color:blue;"><i class="fas fa-wrench" title="수정"></i></small>
            <small @click="reviewDelete(slide.rvid)" style="color:red"><i class="fas fa-trash-alt ml-2" title="삭제"></i></small>
          </div>
          <div class="d-flex align-items-end mt-1 mr-3">
            <small style="font-weight:bold">{{datecut(slide.createDate)}}</small>
          </div>
        </div>
        <div v-if="email != slide.email" class="d-flex align-items-center mt-4 mr-3">
            <small style="font-weight:bold">{{datecut(slide.createDate)}}</small>
        </div>
      </div>
      <!-- 제목 -->
      <div class="d-flex mt-2 ml-2 p-2">
        <span style="font-weight:bold;">{{slide.title}}</span>
      </div>
      <!-- 내용 -->
      <div class="d-flex ml-2 p-2">
        <small>{{slide.content}}</small>
      </div>
    </div>
  </div>
      <ReviewUpdate v-if="this.isUpdated" :reviewInfo="reviewInfo" @review-close="reviewClose"/>
</div>
</div>
</template>

<script>
import axios from 'axios'

const baseURL = process.env.VUE_APP_BACKURL;

import ReviewUpdate from './ReviewUpdateModal.vue'

export default {
  components: {
    ReviewUpdate,
  },
  props: {
    pid: [Number,String],
  },
  data() {
    return {
        slides: [],
        selectedIndex: 0,
        dragging: false,
        initialMouseX: 0,
        initialCardsX: 0,
        cardsX: 0,
        email: "",
        reviewInfo: {},
        isUpdated: false,
      }
  },
  methods: {
      authUser() {
          axios
              .get(`${baseURL}/account/authuser/${this.$cookies.get("Auth-Token")}`)
              .then((response) => {
                  this.email = response.data.email
                  this.name = response.data.name
                  this.fetchReview()
              })
              .catch((err) => {
              console.log(err.response);
              });
      },
      fetchReview() {
          axios.get(`${baseURL}/review/list/${this.pid}`)
              .then((response) => {
                  this.slides = response.data
              }).catch((error) => {
                  console.log(error)
              })
      },
      startDrag(e) {
          this.dragging = true
          this.initialMouseX = e.pageX
          this.initialCardsX = this.cardsX
      },
      stopDrag() {
          this.dragging = false
          const cardWidth = 290
          const nearestSlide = -Math.round(this.cardsX / cardWidth)
          this.selectedIndex = Math.min(Math.max(0, nearestSlide), this.slides.length -1)
          TweenLite.to(this, 0.3, {cardsX: -this.selectedIndex * cardWidth})
      },
      mouseMoving (e) {
          if (this.dragging) {
              const dragAmount = e.pageX - this.initialMouseX
              const targetX = this.initialCardsX + dragAmount
              this.cardsX = targetX
          }
      },
      datecut(date) {
          var tempdatecut = date+""
          return tempdatecut.substring(0,10)
      },
      reviewDelete(rvid) {
        this.$emit('review-delete',rvid)
      },
      update(slide) {
        this.reviewInfo = slide
        this.isUpdated = true
      },
      reviewClose() {
        this.isUpdated = false
      },
  },
  created() {
    if(this.$cookies.get("Auth-Token")!=null)
      this.authUser()
  },
}
</script>

<style>
.slider {
  overflow: hidden;
  background-color: white;
  width: 100%;
  height: 430px;
}
.slider-cards {
  cursor: pointer;
  position: relative;
  display: inline-flex;
  margin: 20px;
  z-index: 1;
}
.slider-card {
  display: inline-block;
  background-color: white;
  border: 0.1px solid gray;
  overflow: hidden;
  width: 260px;
  height: 360px;
  margin-right: 20px;
  border-radius: 12px;
  box-shadow:40px 50px 20px -20px rgba(0, 0, 0, 0.3)
}
.review-img {
    width: 100%;
    height: 50%;
}
.profile-img {
    width: 50px;
    height: 50px;
    border-radius: 35px;
    font-size: 1rem;
    line-height: 1.33;
}
</style>