

<template>
  <div>
     <!-- <a href="https://kauth.kakao.com/oauth/authorize
            ?client_id=8940d7dfdaa8e8db4c0f4d73dd782a21
            &redirect_uri=http://localhost:3000/logink
            &response_type=code">
            <img src="../../assets/img/kakao_login.png">
            
     </a>  -->
         <img src="../../assets/img/kakao_login.png" @click="test()">
  </div>
</template>

<script>
import axios from "axios";
const baseURL = "http://localhost:8080";
export default {
    data() {
      return {
        url: "",
      }
    },
  methods: {
    test(){
        axios.get(`${baseURL}/kko/kakao/login`)
        .then(res => {
          console.log(res.data);
          this.url = res.data;
          window.open(this.url);
          this.getInfo();
        })
        .catch(err => {
          console.log(err);
        })
    },
    // getInfo() {
    //   axios.get(`${baseURL}/kko/kakao/login/getInfo`)
    //   .then( res => {
    //     console.log(res.data);
    //   })
    //   .catch(err => {
    //     console.log(err);
    //   })
    // }
  }

}
</script>