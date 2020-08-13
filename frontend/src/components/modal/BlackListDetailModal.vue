<template>
  <div
    class="modal fade"
    id="blacklist"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header border-0 pl-0">
          <h5
            class="modal-title w-100 text-center font-weight-bold position-absolute"
            id="exampleModalLabel"
          ><span style="color:crimson;">{{blacklist.remail}} </span>님의 상세 정보</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <div class="text-left mx-3">
            <p>
                <span style="font-size:1.2rem; font-weight:bold; margin-right:1rem;">신고자</span>
                <span>{{blacklist.email}}</span>
                <span style="font-size:1.2rem; font-weight:bold; margin-right:1rem; margin-left:3rem;">신고당한 횟수</span>
                <span>{{blacklist.cnt}}</span>
            </p>
            <p style="font-size:1.2rem; font-weight:bold;">신고내용</p>
            <p class="ml-3">{{blacklist.reason}}</p>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" style="border:none; background:none; font-weight:bold; font-size:1.1rem;" data-dismiss="modal" @click="cancelblack">거절</button>
          <button type="button" style="border:none; background:none; font-weight:bold; font-size:1.1rem; color:orange" data-dismiss="modal" @click="warnblack">경고</button>
          <button type="button" style="border:none; background:none; font-weight:bold; font-size:1.1rem; color:crimson" data-dismiss="modal" @click="dropblack">탈퇴</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const baseURL = process.env.VUE_APP_BACKURL;

export default {
  props: {
    blacklist: Object,
  },
  methods: {
    warnblack() {
      this.$emit("warn-black", this.blacklist.rpid);
    },
    dropblack() {
      this.$emit("drop-black", this.blacklist.rpid);
    },
    cancelblack() {
      this.$emit("cancel-black", this.blacklist.rpid);
    },
  },
};
</script>

<style>
</style>