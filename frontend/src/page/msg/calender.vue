<template>
    <div>
        <Editor ref="toastuiEditor" />
        <b-button @click="createAction">저장하기</b-button>
        {{this.text}} 
    </div>
</template>

<script> 
import "codemirror/lib/codemirror.css"; 
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/vue-editor";
export default { components: { Editor }, 
    methods: { 
        createAction() { 
            var content = this.$refs.toastuiEditor.invoke("getMarkdown"); // content를 저장하는 액션 처리
            this.text = content
        } 
    },
    data: function() {
        return {
            text: ""
        }
    } ,
}; 
</script>
