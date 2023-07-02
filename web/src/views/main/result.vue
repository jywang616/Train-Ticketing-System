<template>
    <a-result
            status="success"
            title="恭喜您已成功订票"
            sub-title="购票详情可在个人中心中查看"
    >
        <template #extra>
            <a-button key="console" type="primary" @click="back">完成</a-button>
            <a-button key="buy" @click="again">再买一张</a-button>
        </template>
    </a-result>
</template>
<script>
import { defineComponent } from 'vue';
import router from "@/router";

export default defineComponent({
  setup() {
      const back=()=>{
          router.push('/welcome');
      };
      const  again=()=>{
          router.push('/nonstopticket');
      }

    return {
        back,again,
    };
  },
});
</script>
<style>
</style>

