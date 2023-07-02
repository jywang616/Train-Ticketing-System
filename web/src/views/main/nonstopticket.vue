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

        <a-layout-content class="foot">
            <a-table
                :columns="columns"
                :data-source="traininfos"
                :pagination="pagination"
                :loading="loading"
                @change="handleTableChange">
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
                    <template v-if="column.dataIndex=='traincode'">
                        <a @click="showmore(record)">{{record.traincode}}</a>
                        <a-modal :visible="visible" @cancel="handleMouseLeave" @ok="handleMouseLeave">
                                <a-table :columns="station_col" :data-source="stations" >
                                </a-table>
                        </a-modal>

                    </template>
                </template>
            </a-table>
        </a-layout-content>

    </a-layout>
</template>

<script>
import {defineComponent, onMounted, ref} from 'vue';
import { SearchOutlined } from '@ant-design/icons-vue';
import axios from 'axios';
import locale from 'ant-design-vue/es/date-picker/locale/zh_CN';
import { notification } from 'ant-design-vue';
import router from "@/router";

export default defineComponent({
    name: 'traininfo-view',
    components: {
        SearchOutlined,
    },
    setup() {
        const city = ref([]);
        const selectstartcity = ref('');
        const selectendcity = ref('');

        const time_val=ref([]);
        const traininfos = ref([]);
        const loading = ref(false);

        const visible = ref(false);
        const stations=ref([]);

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
            },
        ];
        const ticket_info=ref([]);
        const visible1 = ref(false);
        const option_seat=ref([]);

        const selectedSeat = ref('');

        const passengers=ref([]);
        const passenger_option=ref([]);

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
        const gobuyticket=(record)=>{
            ticket_info.value=[record];
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

        const nobuyticket=()=>{
            //save
          visible1.value= false;
            router.push('/nonstopticket');
        }
        const buyticket=()=>{
            router.push('/result');
            //买票

        };
        const getcity = () => {
            axios.get('/ticket/getCity').then((res) => {
                if (res.data && res.data.content) {
                    let data = res.data.content;
                    city.value = data.map((x) => ({
                        value: x,
                        label: x,
                    }));
                } else {
                    notification.error({ description: res.message });
                }
            });
        };

        const station_col=[
            {
                title: '站序',
                dataIndex: 'station_NO',
            },
            {
                title: '站名',
                dataIndex: 'name',
            },
            {
                title: '到站时间',
                dataIndex: 'start_time',
            },
            {
                title: '出发时间',
                dataIndex: 'out_time',
            },
            {
                title: '停留时间',
                dataIndex: 'duration',
            },
        ];
        const columns = [
            {
                title: '车次',
                dataIndex: 'traincode',
                sorter: true,
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
                sorter: true,
            },{
                title: '到达时间',
                dataIndex: 'end_time',
                sorter: true,
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
            {
                title: '操作',
                dataIndex: 'operation',
            },
        ];
        const pagination = ref({
            total: 0,
            current: 1,
            pageSize: 10,
        });

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
                .get('/ticket/NonStopQuery', {
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
                    let data = response.data;
                    if (data.success) {
                        traininfos.value = data.content.list;
                        pagination.value.current = param.page;
                        pagination.value.total = data.content.total;
                    } else {
                        notification.error({ description: data.message });
                    }
                });
        };

        const handleMouseLeave=()=> {
            visible.value= false;
        }
        const handleMouseLeave1=()=> {
            visible1.value= false;
        }

        const showmore= (param) =>  {
            axios.get('/ticket/getTrainStationInfo',{
                params:{
                    traincode:param.traincode,
                },
            }).then((res)=>{
                let data=res.data;
                if(data.success){
                    stations.value=data.content;
                    visible.value= true;
                }else {
                    notification.error({ description: data.message });
                }
            });
        };

        onMounted(() => {
            getcity();

        });

        return {
            city,
            getcity,
            selectstartcity,
            selectendcity,
            time_val,
            locale,
            columns,
            pagination,
            loading,
            traininfos,
            search,
            showmore,
            station_col,
            visible,
            stations,
            handleMouseLeave,
            router,
            buyticket,
            ticket_columns,
            handleMouseLeave1,
            gobuyticket,
            passenger_option,
            option_seat,
            showpassenger,
            selectedSeat,
            passengers,
            visible1,
            ticket_info,
            nobuyticket,
        };
    },
});
</script>

<style>
.selectedinfo {
    background-color: white;
}
.foot {
    background-color: white;
}
</style>
