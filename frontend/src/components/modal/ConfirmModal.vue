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
        <!-- body -->
        <div class="modal-body pb-0 d-flex justify-content-center align-items-center row">
          <div class="col-12">
            <span>일련번호 입력</span>
            <div class="mt-3 input-group input-group-lg pl-5 pr-5">
              <input type="text" class="form-control" aria-label="Large" aria-describedby="inputGroup-sizing-sm">
            </div>
          </div>
        </div>

        <!-- footer -->
        <div class="modal-footer border-0 pt-0">
            <button @click="makeuse(purid,serialno)" type="button" class="btn" data-dismiss="modal"><i class="fas fa-plus-circle"><span class="ml-1">구매 확정</span></i></button>
 
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

const baseURL = process.env.VUE_APP_BACKURL;

export default {
  data() {
    return {
      serialno: "",
      detailData: "",
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
