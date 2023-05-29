import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import store from './store'
import * as Icons from '@ant-design/icons-vue';

/*'#app'是跟index.html里div标签绑定的，内容会渲染到div中*/
const app=createApp(App);
app.use(Antd).use(store).use(router).mount('#app');

const icons= Icons;
for(const i in icons){
    app.component(i,icons[i]);
}
