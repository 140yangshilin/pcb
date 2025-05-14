<template>
    <div class="w-full flex pt-10">
        <!-- 左侧导航栏 -->
        <div class="w-[150px] flex-shrink-0">
            <LeftSidebar class="bg-white rounded-r shadow text-sm text-gray-700 p-4 sticky top-20 self-start" />
        </div>

        <!-- 中间+右侧区域 -->
        <div class="flex-1 max-w-6xl mx-auto flex gap-6 px-4 py-4">
            <!-- 中间表单模块 -->
            <div class="flex-1 min-w-[700px] space-y-6">
                <div id="section-basic">
                    <BasicInfoForm :lang="'zh'" v-model="formData" />
                    <!-- ✅ 实时预览当前表单数据 -->
                </div>
                <div id="section-craft">
                    <CraftForm />
                </div>
                <div id="section-service">
                    <PersonalServiceForm />
                </div>
                <div id="section-SMT">
                    <SMTInfoForm />
                </div>
                <div id="section-check">
                    <CheckForm />
                </div>
                <div id="section-delivery">
                    <DeliveryForm />
                </div>
            </div>

            <!-- 右侧价格卡片 -->
            <div class="w-[280px] self-start sticky top-20">
                <PriceSummary :lang="lang" :formData="formData" />
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import LeftSidebar from '../sidebar/LeftSidebar.vue'
import BasicInfoForm from '../form/BasicInfoForm.vue'
import CraftForm from '../form/CraftForm.vue'
import PriceSummary from '../sidebar/PriceSummary.vue'
import PersonalServiceForm from '../form/PersonalServiceForm.vue'
import CheckForm from '../form/CheckForm.vue'
import SMTInfoForm from '../form/SMTInfoForm.vue'
import DeliveryForm from '../form/DeliveryForm.vue'


const lang = ref('zh')
/*
const formData = reactive({
    layers: '2',
    size: ''
})*/
const formData = ref({
    boardType: '',
    copperWeight: ''
})
/*const formData = reactive({
    boardType: '',
    quantity: '',
    size_length: '',
    size_width: ''
})
/*
const formData = reactive({
    boardType: '',
    quantity: '',
    size_length: '',
    size_width: ''
})*/


</script>
