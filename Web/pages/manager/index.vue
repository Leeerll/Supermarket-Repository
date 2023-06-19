<template>
    <div class="container-scroller">
        <!--      顶栏-->
        <titlebar></titlebar>
        <cli-title class="titleClass"></cli-title>

        <div class="bodyDiv">
            <div class="echarts-div">
                <!-- curve -->
                <div id="echarts-curve" ref="echarts-curve" :style="{ width: '800px', height: '400px' }">

                </div>
                <div id="echarts-pie" ref="echarts-pie" :style="{ width: '800px', height: '400px' }">

                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Cookies from 'js-cookie'
import List from "../../components/message/list";
import CliTitle from "../../components/base/cliTitle";
import CliMenu from "../../components/base/cliMenu";
import Position from "../../components/base/position";
import DataEdit from "../../components/message/message_edit";
import Message_detail from "../../components/message/message_detail";
import Notify_list_table from "../../components/notify/notify_list_table";
import Sidebar from "../../components/base/sidebar";
import Titlebar from "../../components/base/titlebar";
import "../../plugins/echarts.min.js";
import axios from "axios";
/*import { getCurrentInstance } from '@vue/runtime-core';*/
export default {
    name: "index",
    components: {
        Titlebar,Sidebar, Notify_list_table, Message_detail, DataEdit, Position, CliMenu, CliTitle, List},
    data() {
        return {
           option:{
                // 图表标题
                title: {
                    text: '月度收益',
                    left: 'center',
                    top: 'top',
                    textStyle:{
                        color:'#0DB9F2',        //颜色
                        fontStyle:'normal',     //风格
                        fontWeight:'normal',    //粗细
                        fontFamily:'Microsoft yahei',   //字体
                        fontSize:14,     //大小
                        align:'center'   //水平对齐
                    },
                },
                xAxis:{
                    type: 'category',
                    data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月','9月','10月','11月','12月']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: [820, 932, 901, 934, 1290, 1330, 1320, 123, 456, 789, 345, 1456],
                        type: 'line',
                        smooth: true
                    }
                ]
            },
           option2:{
               title: {
                    text: '收益组成',
                    left: 'center',
                    top: 'center',
                    textStyle:{
                        color:'#0DB9F2',        //颜色
                        fontStyle:'normal',     //风格
                        fontWeight:'normal',    //粗细
                        fontFamily:'Microsoft yahei',   //字体
                        fontSize:14,     //大小
                        align:'center'   //水平对齐
                    },},
               series: [
                    {
                        type: 'pie',
                        data: [
                            {
                                value: 335,
                                name: 'A'
                            },
                            {
                                value: 234,
                                name: 'B'
                            },
                            {
                                value: 1548,
                                name: 'C'
                            }
                        ],
                        radius: ['40%', '70%']
                    }
                ]
           }
        }
    },

    methods: {
        // 异步处理图表数据
        initCurveEcharts(){
            // let internalInstance = getCurrentInstance();
            let internalInstance = this.$parent;
            let echarts = this.$parent.appContext.config.globalProperties.$echarts;
            const dom = document.getElementById('echarts-curve');
            const myChart = echarts.init(dom)
            myChart.setOption(this.option);
        },
        initPieCharts(){
            let internalInstance = this.$parent;
            let echarts = this.$parent.$ssrContext.config.globalProperties.$echarts;
            const dom = document.getElementById('echarts-pie');
            const myChart = echarts.init(dom)
            myChart.setOption(this.option2);
        }
    },
    mounted(){
        this.initCurveEcharts();
        this.initPieCharts();
    },
}

</script>

<style scoped>

.titleClass {
    position: fixed;
    min-width: 1000px;
}

.bodyDiv {
    position: fixed;
    top: 70px;
    left: 190px;
    width: calc(100% - 150px);
    min-height: calc(100% - 70px);
    padding: 1.5rem 1.5rem 10rem;
    height: 90%;
    overflow-y: auto;
}

.container{
    display: flex;
    flex-direction: column;
    align-items: center;
}
:global(h2#card-usage ~ .example .example-showcase) {
    background-color: red;
}

.statistic-footer .footer-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.statistic-footer .footer-item span:last-child {
    display: inline-flex;
    align-items: center;
    margin-left: 4px;
}

</style>
