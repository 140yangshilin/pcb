<template>
    <nav class="space-y-4">
        <div v-for="step in steps" :key="step.id" :class="[
            'cursor-pointer flex items-center space-x-2 px-2 py-1 rounded transition',
            activeStep === step.id ? 'bg-blue-100 text-blue-600 font-semibold' : 'hover:bg-gray-100'
        ]" @click="scrollTo(step.id)">
            <span class="w-6 h-6 bg-blue-500 text-white rounded-full flex items-center justify-center text-xs">
                {{ step.index }}
            </span>
            <span>{{ step.label }}</span>
        </div>
    </nav>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'

const steps = [
    { id: 'section-basic', label: '基本信息', index: 1 },
    { id: 'section-craft', label: 'PCB工艺', index: 2 },
    { id: 'section-service', label: '个性化服务', index: 3 },
    { id: 'section-SMT', label: 'SMT贴片激光钢网', index: 4 },
    { id: 'section-check', label: '开票/发票', index: 5 },
    { id: 'section-delivery', label: '发货/快递', index: 6 },
    // 可继续添加更多步骤
]

const activeStep = ref('section-basic')

function scrollTo(id) {
    const el = document.getElementById(id)
    if (el) {
        el.scrollIntoView({ behavior: 'smooth', block: 'start' })
        activeStep.value = id
    }
}

function updateActiveStep() {
    for (const step of steps) {
        const el = document.getElementById(step.id)
        if (el) {
            const rect = el.getBoundingClientRect()
            if (rect.top <= 100 && rect.bottom >= 100) {
                activeStep.value = step.id
                break
            }
        }
    }
}

onMounted(() => {
    window.addEventListener('scroll', updateActiveStep)
})

onUnmounted(() => {
    window.removeEventListener('scroll', updateActiveStep)
})
</script>

<style scoped>
nav {
    min-width: 120px;
}
</style>