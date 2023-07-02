
<template>
    <a-layout>
        <a-layout-header class="selectedinfo">
            <a-row type="flex">
                <a-col :flex="0.5">
                    <span :style="{ marginRight: '6px' }">出发地</span>
                </a-col>
                <a-col :flex="2">
                    <a-select
                        v-model:value="selectstartcity"
                        placeholder="请选择出发城市"
                        style="width: 120px"
                        :options="city"
                        @click="getcity"
                    ></a-select>
                </a-col>
                <a-col :flex="0.5">
                    <span :style="{ marginRight: '6px' }">目的地</span>
                </a-col>
                <a-col :flex="2">
                    <a-select
                        v-model:value="selectendcity"
                        placeholder="请选择到达城市"
                        style="width: 120px"
                        :options="city"
                        @click="getcity()"
                    ></a-select>
                </a-col>
                <a-col :flex="0.5">
                    <span :style="{ marginRight: '6px' }">日期</span>
                </a-col>
                <a-col :flex="2">
                    <a-range-picker
                        v-model:value="time_val"
                        :format="dateFormat"
                        :showToday="true"
                        :locale="locale"
                    ></a-range-picker>
                </a-col>
                <a-col :flex="auto">
                    <a-button type="primary" @click="search">
                        <template #icon><SearchOutlined /></template>
                        Search
                    </a-button>
                </a-col>
            </a-row>
        </a-layout-header>

        <a-layout-content class="info">
            <a-table
                    :columns="columns"
                    :data-source="data"
                    :pagination="pagination"
                    :loading="loading"
                    :expandedRowKeys="expandedRowKeys"
                    @expand="getInnerData"
                    class="components-table-demo-nested">

                <template #bodyCell="{ column, record }">
                    <template v-if="column.dataIndex === 'operation'">
                        <a-button type="primary" @click="gobuyticket(record)">购票</a-button>
                        <a-modal :visible="visible1" :width=800 cancel-text="暂存订单" ok-text="确认购买" @cancel="nobuyticket" @ok="buyticket">
                            <h3 :style="{ margin: '16px 0' }">订单详情</h3>
                            <a-table :columns="ticket_columns" :data-source="ticket_info" :pagination="false"></a-table>
                            <h3 :style="{ margin: '16px 0' }">座位选择</h3>
                            <a-radio-group  v-model:value="selectedSeat" :options="option_seat" ></a-radio-group>
                            <h3 :style="{ margin: '16px 0' }">乘坐人选择</h3>
                            <a-radio-group v-model:value="passengers" :options="passenger_option" />
                        </a-modal>
                    </template>
                </template>
                <template #expandedRowRender>
                    <a-table
                        :columns="innerColumns"
                        :data-source="innerdata"
                    ></a-table>
                </template>
            </a-table>
        </a-layout-content>

    </a-layout>
</template>

<script>
import {defineComponent, onMounted, ref} from 'vue';
import { SearchOutlined } from '@ant-design/icons-vue';
import axios from 'axios';
import { notification } from 'ant-design-vue';
import router from "@/router";

export default defineComponent({
    name: 'traininfo-view',
    components: {
        SearchOutlined,
    },
    setup() {
        const visible1 = ref(false);
        const ticket_columns = [
            {
                title: '车次',
                dataIndex: 'traincode',
            },
            {
                title: '出发站',
                dataIndex: 'start_station',
            },
            {
                title: '到达站',
                dataIndex: 'end_station',
            },
            {
                title: '出发时间',
                dataIndex: 'start_time',
            },{
                title: '到达时间',
                dataIndex: 'end_time',
            },
            {
                title: '历时',
                dataIndex: 'duration',
            },
            {
                title: '一等座',
                dataIndex: 'firstClass',
            },
            {
                title: '二等座',
                dataIndex: 'secondClass',
            },
            {
                title: '软卧',
                dataIndex: 'rw',
            },{
                title: '硬卧',
                dataIndex: 'yw',
            }
        ];
        const ticket_info=ref([]);
        const option_seat=ref([]);
        const selectedSeat = ref('')
        const passengers=ref([]);
        const passenger_option=ref([]);

        const gobuyticket=(record)=>{
            ticket_info.value=record.infos;
            option_seat.value = [
                {
                    label: '一等座',
                    value: 'firstclass',
                    disabled: false,
                },
                {
                    label: '二等座',
                    value: 'secondclass',
                    disabled: false,
                },
                {
                    label: '软卧',
                    value: 'rw',
                    disabled: false,
                },
                {
                    label: '硬卧',
                    value: 'yw',
                    disabled: false,
                }];

            if (record.firstClass === 0) {
                option_seat.value[0].disabled = true;
            }

            if (record.secondClass === 0) {
                option_seat.value[1].disabled = true;
            }

            if (record.rw === 0) {
                option_seat.value[2].disabled = true;
            }

            if (record.yw === 0) {
                option_seat.value[3].disabled = true;
            }
            showpassenger();
            visible1.value= true;
        }
        const showpassenger=()=>{
            axios.get("/member/passenger/query-list", {
                params: {
                    page: 1,
                    size: 1
                }
            }).then((response) => {
                let data = response.data;
                if (data.success) {
                    passengers.value = data.content.list;
                    passenger_option.value = passengers.value.map((x) => ({
                        value: x.name,
                        label: x.name
                    }));
                } else {
                    notification.error({description: data.message});
                }
            });

        };
        const nobuyticket=()=>{
            //save
          visible1.value= false;
            router.push('/nonstopticket');
        }
        const buyticket=()=>{
            router.push('/result');
            //买票

        };

        const city = ref([]);
        const selectstartcity = ref('');
        const selectendcity = ref('');
        const time_val=ref([]);
        const loading = ref(false);
        let expandedRowKeys=[];
        const getcity = () => {
            axios.get('/ticket/getCity').then((res) => {
                if (res.data && res.data.content) {
                    let data = res.data.content;
                    city.value = data.map((x) => ({
                        value: x,
                        label: x,
                    }));
                } else {
                    notification.error({ description: res.data.message });
                }
            });
        };

        const innerColumns = [
            {
                title: '车次',
                dataIndex: 'traincode',
            },
            {
                title: '出发站',
                dataIndex: 'start_station',
            },
            {
                title: '到达站',
                dataIndex: 'end_station',
            },
            {
                title: '出发时间',
                dataIndex: 'start_time',
            },{
                title: '到达时间',
                dataIndex: 'end_time',
            },
            {
                title: '历时',
                dataIndex: 'duration',
            },
            {
                title: '一等座',
                dataIndex: 'firstClass',
            },
            {
                title: '二等座',
                dataIndex: 'secondClass',
            },
            {
                title: '软卧',
                dataIndex: 'rw',
            },{
                title: '硬卧',
                dataIndex: 'yw',
            },
        ];
        const columns = [
            {
                title: '出发站',
                dataIndex: 'start_station',
            },
            {
                title: '出发时间',
                dataIndex: 'start_time',
            },
            {
                title: '到达站',
                dataIndex: 'end_station',
            },
            {
                title: '到达时间',
                dataIndex: 'end_time',
            },{
                title: '到达时间',
                dataIndex: 'end_time',
            },  {
                title: '操作',
                dataIndex: 'operation',
            },
        ];
        const pagination = ref({
            total: 0,
            current: 1,
            pageSize: 10,
        });
        const data=ref([]);
        let innerdata=ref([]);


        const getInnerData = (expanded, record) => {
            expandedRowKeys=[];
            if (expanded) {
                expandedRowKeys.push(record.id);
                //innerdata=[];
                innerdata.value=record.infos; // 获取内表格的数据
            }
        };



        const search = () => {
            const [startDate, endDate] = time_val.value.map((date) =>
                date.format('YYYY/MM/DD')
            );
            const params = {
                selectstartcity: selectstartcity.value,
                selectendcity: selectendcity.value,
                start_date: startDate,
                end_date: endDate,
            };
            handleQuery(params);
        };
        const handleQuery = (param) => {
            if (!param) {
                param = {
                    page: 1,
                    size: pagination.value.pageSize,
                };
            }
            loading.value = true;
            axios
                .get('/ticket/StopQuery', {
                    params: {
                        page: 1,
                        size: pagination.value.pageSize,
                        start_city: param.selectstartcity,
                        end_city: param.selectendcity,
                        start_date: param.start_date,
                        end_date: param.end_date,
                    },
                })
                .then((response) => {
                    loading.value = false;
                    if (response.data.success) {
                        let result = response.data.content.list;
                        //traininfos.value = data.content.list;
                        data.value=result;
                        // outerdata.value=data;//映射不上的可以不映射？
                        // innerdata.value = data.map((item) => ({
                        //     id: item.id, // Assuming each data object has a unique 'id' property
                        //     infos: item.infos,
                        // }));
                        pagination.value.current = param.page;
                        pagination.value.total = response.data.content.total;
                    } else {
                        notification.error({ description: response.data.message });
                    }
                });
        };

        onMounted(() => {
            getcity();
        });

        return {
            city,selectstartcity,selectendcity,time_val,loading,getcity,innerColumns,columns,pagination,search,handleQuery,
            data,getInnerData,innerdata,visible1,ticket_columns,ticket_info,option_seat,
            selectedSeat,passengers,passenger_option,gobuyticket,showpassenger,nobuyticket,buyticket
        };
    },
});
</script>

<style>
.selectedinfo {
    background-color: white;
}
.info {
    background-color: white;
}
</style>
