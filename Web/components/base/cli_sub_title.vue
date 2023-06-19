<template>
    <div>
        <div style="display: flex">

            <div v-for="(item, index) in tabList"
                 :key="index"
                 class="tabClass"
                 :style="{backgroundColor: index === selectIndex ? '#3b76f4': 'rgba(59,118,244,0.25)', color: index === selectIndex ? '#fff': '#3b76f4'}"
                 @click="clickTab(index)">
                {{item}}
            </div>

        </div>
        <div style="width: 100%; height: 0.3rem; background: #2c57b4"></div>
    </div>
</template>

<script>
    export default {
        name: "cli_sub_title",
        props:{
            // 标签数组
            tabList:{
                type:Array
            },
            // 选中的标签的索引
            selectIndex:{
                type:Number
            },
        },
        methods:{
            clickTab(index) {
                this.$emit('selectTab', { index: index })
            }
        }


    }
</script>

<style scoped>
    .tabClass {
        width: 8rem;
        height: 2rem;
        line-height: 2rem;
        text-align: center;
        margin-right: 1rem;
        cursor: pointer;

        transition: 1s;
    }


</style>