<template>
  <div>
    <footer class="main-footer" style="background-color:#ccdced;">
      <div class="container">
        <div class="p-0 text-center" style="font-size:0.9rem; text-align:center;">
          <p class="mb-1">상호명 (주)Linkivity | 대표이사: 이재인</p>
          <p class="mb-1">전화번호 : 010-2497-8055</p>
          <!-- <span class="d-flex">통신판매업신고번호 : 제 2020-대전유성구-0077</span> -->
          <p class="mb-1">본사 : 대전광역시 유성구 학하서로 121번길 55-9</p>
            <p class="mb-1">고객지원 : 공지게시판</p>
          <p class="mb-1">(운영시간 09:00 ~ 18:00, 점심 : 12:00 ~ 13:00, 주말/공휴일은 제외)</p>
        </div>
        
        <div class="mb-2">
          <span class style="cursor:pointer;" @click="goagreement">이용약관</span>
          <span class="ml-2 mr-2">|</span>
          <span class>개인정보처리방침</span>
          <span class="ml-2 mr-2">|</span>
          <span class>회사소개</span>
        </div>
        <div style="font-size:0.8rem">
          <p class="mb-3">Made by 길민규 김석환 노휘종 이동현 이재인</p>
        </div>
        <div class="social justify-content-center align-items-center">
          <!-- <i class="text-white">𝙛𝙖𝙘𝙚𝙗𝙤𝙤𝙠</i> -->
          <a href="http://facebook.com">
            <span class="fa-stack fa-lg facebook" style="color:white;">
              <i class="fa fa-circle-o fa-stack-2x"></i>
              <i class="fa fa-facebook fa-stack-1x"></i>
            </span>
          </a>
          <!-- <i class="text-white">𝒕𝒘𝒊𝒕𝒆𝒓</i> -->
          <a href="https://twitter.com/?lang=ko">
            <span class="fa-stack fa-lg twitter" style="color:white;">
              <i class="fa fa-square-o fa-stack-2x"></i>
              <i class="fa fa-twitter fa-stack-1x"></i>
            </span>
          </a>
          <!-- <i class="text-white">𝒊𝒏𝒔𝒕𝒂𝒈𝒓𝒂𝒎</i> -->
          <a href="https://www.instagram.com/?hl=ko">
            <span class="fa-stack fa-lg instagram" style="color:white;">
              <i class="fa fa-square-o fa-stack-2x"></i>
              <i class="fa fa-instagram fa-stack-1x"></i>
            </span>
          </a>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  methods: {
    goagreement() {
      this.$router.push("/agreement");
    },
  },
};
</script>

<style>
.main-footer {
  height: auto;
  padding-top: 30px;
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
