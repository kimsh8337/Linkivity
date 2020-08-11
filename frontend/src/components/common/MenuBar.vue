<template>
  <div v-if="this.scrollposition > 200" class="side-menu">
        <ul v-if="this.showbar == true" class="side-menu-bar">
            <li><i class="fas fa-stream mx-2 "></i>post</li>
            <li><i class="fas fa-flag mx-2"></i>notice</li>
            <li><i class="fas fa-pen mx-2"></i>Write</li>
            <li><i class="fas fa-shopping-basket mx-2"></i>basket</li>
            <li><i class="far fa-user mx-2"></i>mypage</li>
            <li><i class="fas fa-angle-double-up upBtn" @click="toTop" style="cursor:pointer;"></i>Top</li>
          </ul>
        <button class="side-main-button" @click="changeshowbar"><i class="fas fa-bars"></i></button>
      </div>
</template>

<script>
import '../../assets/css/menubar.css';

export default {
    props:{
        scrollposition: Number,
        showbar:Boolean,
    },
    deta(){
        return{
        }
    },
    mounted(){
    },
    methods:{
        changeshowbar(){
            this.$emit('change-showbar',this.showbar)
        },
        toTop() {
            scroll(0, 0);
        },
    }

}
</script>

<style>

</style>