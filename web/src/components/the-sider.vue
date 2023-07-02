<template>
  <a-layout-sider width="200" style="background: #fff">
    <a-menu
        v-model:selectedKeys="selectedKeys"
        mode="inline"
        :style="{ height: '100%', borderRight: 0 }"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <coffee-outlined />&nbsp;欢迎
        </router-link>
      </a-menu-item>
      <a-sub-menu key="sub1">
        <template #title>
            <span>
              <user-outlined />
              购票中心
            </span>
        </template>
        <a-menu-item key="/nonstopticket">
          <router-link to="/nonstopticket">
            <smile-outlined />&nbsp;直达购票
          </router-link>
        </a-menu-item>
        <a-menu-item key="/transferticket">
          <router-link to="/transferticket">
            <smile-outlined />&nbsp;中转换乘
          </router-link>
        </a-menu-item>
      </a-sub-menu>
      <a-menu-item key="/passenger">
        <router-link to="/passenger">
          <smile-outlined />&nbsp;乘车人管理
        </router-link>
      </a-menu-item>
      <a-menu-item key="/confirm-order">
        <router-link to="/confirm-order">
          <smile-outlined />&nbsp;订单查询
        </router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-sider>
</template>

<script>
import {defineComponent, ref, watch} from 'vue';
import router from "@/router";
export default defineComponent({
  name: "the-sider-view",
  setup() {
    const selectedKeys = ref([]);

    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch', newValue);
      selectedKeys.value = [];
      selectedKeys.value.push(newValue);
    }, {immediate: true});
    return {
      selectedKeys,
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
