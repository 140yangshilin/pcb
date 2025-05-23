import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import './assets/main.css'
import router from './router'

// createApp(App).mount('#app')
createApp(App).use(router).mount('#app')