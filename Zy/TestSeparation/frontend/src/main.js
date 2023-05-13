import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
Vue.config.productionTip = false
//引入elementUI组件库
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI)
//引入axios
import axios from "axios";
axios.defaults.baseURL = "http://localhost:9090/"
Vue.prototype.$axios = axios;
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
