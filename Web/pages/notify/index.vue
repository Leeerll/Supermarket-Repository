<template>
    <div class="container-scroller">
        <!--      顶栏-->
        <titlebar></titlebar>
        <cli-title class="titleClass"></cli-title>
        <!--      侧边栏-->
        <cli-menu class="menuClass" :pageIndex="pageIndex"></cli-menu>
        <div class="bodyDiv">
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <span>仓库1实时状态</span>
                </div>
                <div style="display: flex">
                    <div>
                        <div v-for="o in this.warehouseData" :key="o" style="font-size: 18px;min-width: 120px;margin-right: 20px;margin-top: 25px">
                            {{ o }}
                        </div>
                    </div>
                    <div style="min-width: 950px">
                        <!--新的属性在这里加-->
                        <el-progress :percentage="this.takerate" style="margin-top: 25px"></el-progress>
                        <el-progress :percentage="this.machine_health_rate" style="margin-top: 33px"></el-progress>
                    </div>
                </div>

                <div style="margin-top: 80px">
                    <!-- curve -->
                    <div id="echarts-curve" ref="echarts-curve" :style="{ width: '800px', height: '400px' }">

                    </div>
<!--                    <div id="echarts-pie" ref="echarts-pie" :style="{ width: '800px', height: '400px' }">-->

<!--                    </div>-->
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
import cliTitle from "../../components/base/cliTitle.vue";
import cliMenu from "../../components/base/cliMenu.vue";
import cliMenu2 from "../../components/base/cliMenu3.vue";
import Titlebar from "../../components/base/titlebar.vue";
import axios from "axios";
import Cookies from "js-cookie";
import "../../plugins/echarts.min.js";

export default {
    name: "notify_list",
    //通知列表
    components: {Titlebar,cliTitle, cliMenu,cliMenu2},
    data(){
        return{
            uid:'',
            myInput: 'test',
            pageIndex: "2",
            warehouseData:[
                '仓库占有率',
                '硬件健康程度'
                /*'仓库磨损率',
                '仓库收益',
                '仓库维护支出'*/
            ],
            wareData:[0,0,0],
            temp:[],

            timer:'',
            value:0,

            takerate:0,//占有率
            machine_health_rate:0,//硬件健康程度
        }
    },
    created() {
        this.uid = String(this.$route.query.uid);
    },
    mounted(){
        this.timer=setInterval(this.get,1000);
        this.initCurveEcharts();
/*        this.initPieCharts();*/
    },
    beforeDestroy() {
        clearInterval(this.timer);
    },

    methods:{
        get(){
            this.value++;
            let data={
                token:Cookies.get('token'),
                rid:this.uid
            }
            axios.post("http://localhost:8181/repository/selectRateByRid",data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    this.temp = response.data;
                    this.takerate = parseInt(this.temp.takerate);
                    this.machine_health_rate=parseInt(this.temp.machine_health_rate);
                })
        },
        // 异步处理图表数据
        async initCurveEcharts(){
            const dom = document.getElementById('echarts-curve');
            const myChart = echarts.init(dom);
            // 请求数据
            let data={
                rid:this.uid
            }
            let option_xAxis =[];
            let option_serious = [];
            // options
            const option = {
                // legend: {
                //     tooltip: {              //在legend里的tooltip，在鼠标滑到图例上时，展示自定义的内容
                //         show: true
                //     },
                //     top: 18,
                //     right: 10,
                //     data: ["月收益/元"],
                //     textStyle: {
                //         color: 'rgba(255, 255, 255, 0.8)',
                //         fontSize: 15
                //     },
                //     itemWidth: 10,
                //     itemHeight: 10,
                //     selectedMode: false,
                // },

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
                        fontSize:25,     //大小
                        align:'center'   //水平对齐
                    },
                },
                xAxis:{
                    type: 'category',
                    data: []
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'月收益/元',
                        data: [],
                        type: 'line',
                        smooth: false,
                        label: {
                            normal: {
                                show: true,
                                formatter: '{c}'  // 显示data的文字
                            }
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    }
                ]
            };
            await axios.post("http://localhost:8181/repository/getMonthIncome",data,
                {
                    headers:{
                        token:JSON.parse(localStorage.getItem('token'))
                    }
                })
                .then(response => {
                    response.data.forEach(item => {
                            option_xAxis.push(item.yearMonth)
                            option_serious.push(parseFloat(item.income))
                        },
                        option.xAxis.data = option_xAxis,
                        option.series.at(0).data=option_serious
                    )
                })
            myChart.setOption(option);
        },

        // async initPieCharts(){
        //     const dom2 = document.getElementById('echarts-pie');
        //     const myChart2 = echarts.init(dom2);
        //     const option = {
        //         title: {
        //             text: '收益组成',
        //             left: 'center',
        //             top: 'center',
        //             textStyle:{
        //                 color:'#0DB9F2',        //颜色
        //                 fontStyle:'normal',     //风格
        //                 fontWeight:'normal',    //粗细
        //                 fontFamily:'Microsoft yahei',   //字体
        //                 fontSize:14,     //大小
        //                 align:'center'   //水平对齐
        //             },},
        //         series: [
        //             {
        //                 type: 'pie',
        //                 data: [
        //                     {
        //                         value: 335,
        //                         name: 'A'
        //                     },
        //                     {
        //                         value: 234,
        //                         name: 'B'
        //                     },
        //                     {
        //                         value: 1548,
        //                         name: 'C'
        //                     }
        //                 ],
        //                 radius: ['40%', '70%']
        //             }
        //         ]
        //     }
        //     myChart2.setOption(option);
        // },
    }
}
</script>

<style>
.outdiv{
    min-width: 1000px;
    overflow: hidden;

}

.titleClass {
    position: fixed;
    z-index: 1;
    margin-bottom: 2rem;
}

.menuClass {
    position: fixed;
    top: 70px;
    width: 190px;
    z-index: 1;
}


.bodyDiv {
    position: fixed;
    top: 70px;
    left: 190px;
    width: calc(100% - 190px);
    min-height: calc(100% - 70px);
    padding: 1.5rem 1.5rem 5rem;
    height: 90%;
    overflow-y: auto;
    margin-bottom:-100px;
}
</style>
