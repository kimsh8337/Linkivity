<template>
<div class="container col-sm-12 col-md-12 col-lg-12 p-0">
  <!-- background image -->
  <div class="pwsearch-img" style="display:block;">
  </div>
  <div class="user mt-5" id="login">
    <div style="height:50px;"></div>
    <div class="container wrapC table col-md-3">
      <div class="middle">
        <h1>비밀번호 찾기</h1><br>
        <div class="form-group">
      <label for="example2" style="float:left">이메일</label>
      <input v-model="email" id="email" placeholder="이메일을 입력해주세요" type="text" class="form-control form-control-md">
         </div>

      <div class="form-group">
      <label for="example2" style="float:left">이름</label>
     <input v-model="name" id="name" placeholder="이름을 입력하세요" type="text" class="form-control form-control-md">
        </div><br>
      
        <button @click="test()" class="btn btn--back btn--login">비밀번호 찾기</button><br>
      </div>
    </div>
    <div style="height:190px;">

    </div>
  </div>
</div>
</template>

<script>
import axios from 'axios';
import '../../assets/css/pwsearch.css'
const baseURL = process.env.VUE_APP_BACKURL;

export default {
    
     methods: {
          test() {
              axios
              .get(`${baseURL}/account/pwd/${this.email}/${this.name}`)
              .then((res) => {
                  console.log(res.data);
                  alert("임시 비밀번호 전송을 하였습니다!")
                  this.$router.push(`/`)
              })
              .catch((err) => {
                 this.$router.push({
                  name: 'Params',
                // params: { name: err.response.status },
               });
            });
         }
     }
    }
</script>
