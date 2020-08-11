<template>
  <div class="input-group mb-3">
      {{reply}}
    <textarea
      type="text"
      class="form-control"
      placeholder="Please write modify reply"
      aria-label="Recipient's username"
      aria-describedby="button-addon2"
      style="height:2.5rem"
    />
    <div class="input-group-append">
      <button class="btn btn-outline-success" type="button" id="button-addon2" @click="replyModify">수정</button>
    </div>
  </div>
</template>

<script>
export default {
    props: {
        reply: Object,
    },
    methods: {
        replyModify() {
            this.$emit('reply-modify')
        },
    },
}
</script>

<style>

</style>