<template>
    <div class="bg-white p-6 rounded shadow">
        <!-- 折叠头部 -->
        <div class="flex justify-between items-center mb-4 cursor-pointer" @click="collapsed = !collapsed">
            <div class="flex items-center space-x-2">
                <h2 class="text-lg font-semibold">基本信息</h2>
            </div>
            <span class="text-sm text-blue-500">{{ collapsed ? '展开' : '收起' }}</span>
        </div>

        <!-- 字段区域 -->
        <div v-show="!collapsed" class="space-y-4">
            <div v-for="field in fields" :key="field.id" class="flex items-start gap-4">
                <!-- 字段标签 -->
                <div class="w-24 text-sm font-medium text-gray-700 pt-2">
                    {{ t(field.label) }}
                    <span v-if="field.tooltip" class="text-gray-400 ml-1 cursor-help" :title="t(field.tooltip)">?</span>
                </div>

                <!-- 不同字段类型渲染 -->
                <!-- 按钮类型：每个字段内单选 -->
                <div v-if="field.type === 'button'" class="flex flex-wrap gap-3">
                    <div v-for="opt in field.options" :key="opt.value"
                        @click="!isDisabled(field, opt.value) && updateField(field.id, opt.value)"
                        class="relative w-20 h-10 flex items-center justify-center border rounded text-sm cursor-pointer"
                        :class="[
                            model[field.id] === opt.value
                                ? 'border-blue-600 ring-2 ring-blue-300'
                                : 'border-gray-300 bg-white hover:border-blue-400',
                            isDisabled(field, opt.value) ? 'opacity-50 cursor-not-allowed' : ''
                        ]">
                        {{ t(opt.label) }}
                        <span v-if="model[field.id] === opt.value"
                            class="absolute bottom-0.5 right-1 text-blue-500 text-xs">✔</span>
                    </div>
                </div>

                <!-- 下拉选择框 -->
                <select v-else-if="field.type === 'dropdown'" v-model="model[field.id]"
                    class="border rounded px-3 py-1 text-sm">
                    <option disabled value="">{{ t(field.placeholder) || '请选择' }}</option>
                    <option v-for="opt in field.options" :key="opt.value" :value="opt.value">
                        {{ t(opt.label) }}
                    </option>
                </select>

                <!-- 尺寸输入：长宽带单位 -->
                <div v-else-if="field.type === 'input-unit'" class="flex items-center gap-2">
                    <input type="number" class="w-20 border rounded px-2 py-1" v-model="model[field.id + '_length']"
                        placeholder="长" />
                    <span>CM</span>
                    <input type="number" class="w-20 border rounded px-2 py-1" v-model="model[field.id + '_width']"
                        placeholder="宽" />
                    <span>CM</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, defineProps, defineEmits } from 'vue'

const props = defineProps({
    lang: String,
    modelValue: Object
})
const emit = defineEmits(['update:modelValue'])
const collapsed = ref(false)
const model = computed(() => props.modelValue)

const t = (labelObj) => typeof labelObj === 'string' ? labelObj : labelObj?.[props.lang] || ''

const updateField = (fieldId, value) => {
    emit('update:modelValue', {
        ...props.modelValue,
        [fieldId]: value
    })
}

// 判断选项是否禁用
const isDisabled = (field, value) => {
    if (field.id === 'quantity' && model.value.boardType !== 'fpc') {
        return true
    }
    return false
}
/*
const updateField = (fieldId, value) => {
    model.value = {
        ...model.value,
        [fieldId]: value
    }
}*/

/*const updateField = (key, value) => {
    emit('update:modelValue', { ...props.modelValue, [key]: value })
}*/

// 示例字段
const fields = [
    {
        id: 'boardType',
        label: { zh: '板材类别', en: 'Board Type' },
        type: 'button',
        options: [
            { value: 'fpc', label: { zh: 'FPC软板', en: 'FPC' } },
            { value: 'fr4', label: { zh: 'FR-4', en: 'FR-4' } },
            { value: 'aluminum', label: { zh: '铝基板', en: 'Aluminum' } },
            { value: 'copper', label: { zh: '铜基板', en: 'Copper' } }
        ]
    },
    {
        id: 'SpecialBoardType',
        label: { zh: '特殊基材', en: 'Special Board Type' },
        type: 'button',
        options: [
            { value: 'normal', label: { zh: '常规FPC', en: 'normalFPC' } },
            { value: 'thick', label: { zh: '超厚FPC', en: 'thickFPC' } },
            { value: 'transparent', label: { zh: '透明FPC', en: 'transparentFPC' } }
        ]
    },
    {
        id: 'quantity',
        label: { zh: '板子数量', en: 'Quantity' },
        type: 'dropdown',
        placeholder: { zh: '请选择数量', en: 'Select Quantity' },
        options: [
            { value: 5, label: { zh: '5片', en: '5 pcs' } },
            { value: 10, label: { zh: '10片', en: '10 pcs' } },
            { value: 20, label: { zh: '20片', en: '20 pcs' } }
        ]
    },
    {
        id: 'size',
        label: { zh: '板子尺寸', en: 'Board Size' },
        type: 'input-unit'
    },
    {
        id: 'BoardQuantity',
        label: { zh: '板子层数', en: 'Board Quantity' },
        type: 'button',
        options: [
            { value: '1', label: { zh: '1', en: '1' } },
            { value: '2', label: { zh: '2', en: '2' } },
            { value: '4', label: { zh: '4', en: '4' } }
        ]
    },
]


/*
const fields = [
    {
        id: 'layers',
        label: { zh: '层数', en: 'Layers' },
        type: 'select',
        options: [
            { value: '2', label: { zh: '双层', en: '2 Layers' } },
            { value: '4', label: { zh: '四层', en: '4 Layers' } }
        ]
    },
    {
        id: 'size',
        label: { zh: '尺寸(mm)', en: 'Size (mm)' },
        type: 'input'
    }
]*/
</script>
