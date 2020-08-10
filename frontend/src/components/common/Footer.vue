<template>
  <div>
    <footer class="main-footer bg-dark">
      <div class="container mr-auto ml-auto row pl-1">
        <div class="m-flex col-md-8 p-0" style="font-size:0.8rem">
          <span class="text-white text-muted d-flex">상호명 (주)Linkivity | 대표이사: 이재인</span>
          <span class="text-white text-muted d-flex">사업자등록번호 : 010-2497-8055</span>
          <span class="text-white d-flex">통신판매업신고번호 : 제 2020-대전유성구-0077</span>
          <span class="text-white text-muted d-flex">본사 : 대전광역시 유성구 학하서로 121번길 55-9</span>
          <span class="text-white text-muted d-flex"
            ><span class="text-white">고객지원 : </span><span class="text-white ml-1 mr-1"> 문의 게시판</span> 또는
            <span class="text-white ml-1">실시간 채팅</span></span
          >
          <span class="text-white text-muted d-flex">(운영시간 09:00 ~ 18:00, 점심 : 12:00 ~ 13:00, 주말/공휴일은 제외)</span>
        </div>
        <div class="col-md-4 social d-flex pl-1 justify-content-center align-items-center">
          <!-- <i class="text-white">𝙛𝙖𝙘𝙚𝙗𝙤𝙤𝙠</i> -->
          <a href="http://facebook.com">
            <span class="fa-stack fa-lg text-white facebook">
              <i class="fa fa-circle-o fa-stack-2x"></i>
              <i class="fa fa-facebook fa-stack-1x"></i>
            </span>
          </a>
          <!-- <i class="text-white">𝒕𝒘𝒊𝒕𝒆𝒓</i> -->
          <a href="https://twitter.com/?lang=ko">
            <span class="fa-stack fa-lg text-white twitter">
              <i class="fa fa-square-o fa-stack-2x"></i>
              <i class="fa fa-twitter fa-stack-1x"></i>
            </span>
          </a>
          <!-- <i class="text-white">𝒊𝒏𝒔𝒕𝒂𝒈𝒓𝒂𝒎</i> -->
          <a href="https://www.instagram.com/?hl=ko">
            <span class="fa-stack fa-lg text-white instagram">
              <i class="fa fa-square-o fa-stack-2x"></i>
              <i class="fa fa-instagram fa-stack-1x"></i>
            </span>
          </a>
        </div>
        <div class="d-flex col-md-8 mt-2 pl-1">
          <span class="text-white" style="cursor:pointer;" @click="goagreement"> 이용약관</span>
          <span class="text-white text-muted ml-2 mr-2"> | </span>
          <span class="text-white"> 개인정보처리방침</span>
          <span class="text-white text-muted ml-2 mr-2"> | </span>
          <span class="text-white"> 회사소개 </span>
        </div>
        <div class="d-flex col-md-8 mt-2 pl-1" style="font-size:0.8rem">
          <span class="text-white">Made by : <i class="fas fa-heart" style="font-size:0.5rem"></i>𝕁𝕒𝕟𝕖ℝ𝕦𝕟</span>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  methods:{
    goagreement(){
      this.$router.push('/agreement')
    },
  },
};
</script>

<style>
.main-footer {
  height: 280px;
  padding-top: 40px;
  padding-bottom: 30px;
}
.facebook {
  cursor: pointer;
}
.instagram {
  cursor: pointer;
}
.twitter {
  cursor: pointer;
}
</style>
