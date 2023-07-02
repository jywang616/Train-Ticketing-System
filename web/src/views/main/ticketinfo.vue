<template>
<!--    <a-descriptions bordered title="订单详情" column="6" layout="vertical">-->
<!--&lt;!&ndash;        <template #extra>&ndash;&gt;-->
<!--&lt;!&ndash;            <a-button type="primary">Edit</a-button>&ndash;&gt;-->
<!--&lt;!&ndash;        </template>&ndash;&gt;-->
<!--        <a-descriptions-item label="车次">traininfo.traincode</a-descriptions-item>-->
<!--        <a-descriptions-item label="出发站">traininfo.start_station</a-descriptions-item>-->
<!--        <a-descriptions-item label="出发时间">traininfo.start_time</a-descriptions-item>-->
<!--        <a-descriptions-item label="到达站">traininfo.end_station</a-descriptions-item>-->
<!--        <a-descriptions-item label="到达时间">traininfo.end_time</a-descriptions-item>-->
<!--        <a-descriptions-item label="历时">traininfo.duration</a-descriptions-item>-->
<!--    </a-descriptions>-->
    <p>订单详情</p>
    <a-table :columns="columns" :data-source="traininfo"></a-table>
    <p>座位选择</p>
    <a-radio-group  @change="getSeat">
        <a-radio value="first">一等座</a-radio>
        <a-radio value="second">二等座</a-radio>
        <a-radio value="rw">软卧</a-radio>
        <a-radio value="yw">硬卧</a-radio>
    </a-radio-group>
    <p>乘坐人选择</p>
    <a-radio-group v-model:value="value1" :options="passenger_option" />
    <template>
        <a-button @click="backwelcome">再看看</a-button>
        <a-button type="primary" @click="submit">提交订单</a-button>

    </template>


</template>
<script>
import {defineComponent, onMounted} from 'vue';
import {useRoute} from "vue-router";
import axios from "axios";
import {notification} from "ant-design-vue";
import router from "@/router";

export default defineComponent({
  setup() {
      const route=useRoute();
      const traininfo=route.props.record;
      const passengers=ref([]);
      const passenger_option=ref([]);
      const showpassenger=()=>{
          axios.get("/member/passenger/query-list", {
              params: {
                  page: param.page,
                  size: param.size
              }
          }).then((response) => {
              let data = response.data;
              if (data.success) {
                  passengers.value = data.content.list;
                  passenger_option.value=response.data.content.map((x)=>({
                      value:x.name,
                      label:x.name,
                  }));
              } else {
                  notification.error({description: data.message});
              }
          });

      };
      const backwelcome=()=>{
          router.push('/welcome');
      };
      const submit=()=>{
          //订单提交
           router.push('/result');
      }
      // traincode,start_station,end_station,start_time,end_time,duration
      // type:(0:一等座 1：二等座 2：软卧 4：硬卧)，乘车人信息
      // const ticket_columns = [
      //     {
      //         title: '车次',
      //         dataIndex: 'traincode',
      //         sorter: true,
      //     },
      //     {
      //         title: '出发站',
      //         dataIndex: 'start_station',
      //     },
      //     {
      //         title: '到达站',
      //         dataIndex: 'end_station',
      //     },
      //     {
      //         title: '出发时间',
      //         dataIndex: 'start_time',
      //         sorter: true,
      //     },{
      //         title: '到达时间',
      //         dataIndex: 'end_time',
      //         sorter: true,
      //     },
      //     {
      //         title: '历时',
      //         dataIndex: 'duration',
      //     },
      //     {
      //         title: '一等座',
      //         dataIndex: 'firstClass',
      //     },
      //     {
      //         title: '二等座',
      //         dataIndex: 'secondClass',
      //     },
      //     {
      //         title: '软卧',
      //         dataIndex: 'rw',
      //     },{
      //         title: '硬卧',
      //         dataIndex: 'yw',
      //     },
      //     {
      //         title: '操作',
      //         dataIndex: 'operation',
      //     },
      // ];

      onMounted(()=>{
          showpassenger();
      });



    return {
        traininfo,showpassenger,backwelcome,submit,columns,
    };
  },
});
</script>
<style>
</style>

