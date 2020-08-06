<template>
    <h1>공지사항 상세정보</h1>
  
</template>

<script>
import axios from 'axios';

import Swal from 'sweetalert2';

const baseURL = 'http://localhost:8080';

export default {
    created(){
        (this.nid = this.$route.params.ID), this.init();
    },
    methods:{
        init() {
          axios
            .get()
            .then((res) => {
                this.notices = res.data;
                console.log(this.notices)
            })
            .catch((err) => {
            console.log(err);
            });
    },
    }

}
</script>

<style>

</style>