import Vue from 'vue';
import uploader from 'vue-simple-uploader';
import App from './App.vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
Vue.prototype.$http = axios;
Vue.use(uploader);
Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
  render(createElement) {
    return createElement(App);
  }
}).$mount('#app');
