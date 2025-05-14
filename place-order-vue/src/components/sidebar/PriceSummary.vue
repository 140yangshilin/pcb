<template>
    <div class="bg-white p-6 rounded shadow sticky top-6 h-fit">
        <h2 class="text-lg font-semibold mb-4">价格明细</h2>
        <p class="text-xl font-bold text-gray-800 mb-4">总价：¥{{ totalPrice }}</p>
        <button class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded w-full">
            提交订单
        </button>
    </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
    lang: String,
    formData: Object
})

// ✅ 使用 props.formData 而不是直接 formData
const totalPrice = computed(() => {
    const base = 10
    const multi = props.formData?.layers === '4' ? 1.5 : 1
    return (base * multi).toFixed(2)
})
</script>