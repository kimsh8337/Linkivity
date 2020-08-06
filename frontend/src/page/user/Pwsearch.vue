<template>
  <div class="user" id="login">
    <div class="wrapC table">
      <div class="middle">
        <h1>비밀번호 찾기</h1>
        <div class="input-wrap">
          <input v-model="email" id="email" placeholder="이메일을 입력해주세요" type="text" />
        </div>
        <div class="input-wrap">
          <input v-model="name" id="name" placeholder="이름을 입력하세요" type="text" />
        </div>
        <button @click="test()" class="btn btn--back btn--login">비밀번호 찾기</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
const baseURL = "http://localhost:8080/account";

export default {
    
     methods: {
          test() {
              axios
              .get(`${baseURL}/pwd/${this.email}/${this.name}`)
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