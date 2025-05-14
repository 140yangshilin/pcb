<template>
    <div class="p-8 text-center">
        <h1 class="text-xl font-bold mb-4">欢迎登录</h1>
        <button @click="handleLogin" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
            登录
        </button>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()

function handleLogin() {
    // 模拟登录
    localStorage.setItem('token', 'dummy_token')
    router.push('/order')
}
</script>
