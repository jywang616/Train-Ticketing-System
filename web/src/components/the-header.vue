<template>
  <a-layout-header class="header">
    <div class="logo" />
    <div style="float:right; color:white;">
    您好: 手机用户 {{member.mobile}} &nbsp;&nbsp;
      <router-link to="/login">
        退出登录
      </router-link>
    </div>
    <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <coffee-outlined />&nbsp;欢迎
        </router-link>
      </a-menu-item>
      <a-menu-item key="/passenger">
        <router-link to="/passenger">
          <coffee-outlined />&nbsp;乘车人管理
        </router-link>
      </a-menu-item>
    </a-menu>

  </a-layout-header>
</template>

<script>
import {defineComponent, ref,watch} from 'vue';
import store from "@/store"
import router from "@/router"

export default defineComponent({
  name: "the-header-view",
  setup() {
    //header只是显示，不修改member，就不用响应变量
    let member=store.state.member;
    const selectedKeys = ref([]);

    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch', newValue);
      selectedKeys.value = [];
      selectedKeys.value.push(newValue);
    }, {immediate: true});
    return {
      selectedKeys,
      member
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
