<template>
  <!-- Modal -->
  <div
    class="modal fade"
    id="confirmmodal"
    tabindex="-1"
    role="dialog"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content" style="margin:auto;height:20rem; width:25rem;">
        <div class="modal-header border-0 pl-0">
          <h4
            class="modal-title w-100 text-center font-weight-bold position-absolute"
            id="exampleModalLabel"
          >구매확정</h4>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body pb-0">
          일련번호 입력
          <br />
          <input type="text" v-model="serialno" />
        </div>
        <div class="modal-footer border-0 pt-0">
            <button @click="makeuse(purid,serialno)" type="button" class="btn" data-dismiss="modal"><i class="fas fa-plus-circle">구매 확정</i></button>
 
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const baseURL = "http://localhost:8080";

export default {
  data() {
    return {
      serialno: "",
    };
  },
  props: {
    purid: [Number, String],
  },
  methods: {
    makeuse(purid,serialno) {
      this.$emit('make-use',purid,serialno)
      this.serialno=""
    },
    confirmSell() {
      axios
        .get(`${baseURL}/login/${this.email}/${this.password}`)
        .then((response) => {
          this.$cookies.set("Auth-Token", response.data);
          this.$router.push("/");
          this.$router.go();
        })
        .catch((err) => {
          console.log(err.response);
          alert("아이디 및 비밀번호를 확인해주세요.");
          this.email = "";
          this.password = "";
        });
    },
  },
};
</script>

<style></style>
