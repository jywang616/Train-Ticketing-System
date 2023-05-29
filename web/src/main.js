import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

/*'#app'是跟index.html里div标签绑定的，内容会渲染到div中*/
createApp(App).use(store).use(router).mount('#app')
