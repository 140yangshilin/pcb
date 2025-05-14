<template>
    <div>
        <HeaderBar />
        <div><!--class="pt-16">--> <!-- 避免被遮住 -->
            <OrderFormLayout />
        </div>
    </div>
</template>

<script setup>
import OrderFormLayout from '../components/layout/OrderFormLayout.vue'
import HeaderBar from '../components/sidebar/HeaderBar.vue'
</script>
