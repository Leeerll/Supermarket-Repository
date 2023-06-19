<template>
    <div class="container-scroller">
        <!--              顶栏-->
        <titlebar></titlebar>
        <cli-title class="titleClass"> </cli-title>
        <!--      侧边栏-->
        <cli-menu class="menuClass"> </cli-menu>

        <div class="bodyDiv">
            <div class="container-info">

                <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick(activeName)">
                    <el-tab-pane class="table-panel" label="我的" name="first">
                        <!-- 超市信息展示区域 -->
                        <div class="object-table">
                            <!-- <div>仓库基本信息</div> -->
                            <!-- <el-text class="mx-1" type="primary">超市货物剩余量信息</el-text> -->
                            <p class="mx-1">超市货物剩余量信息</p>
                            <el-table :data="tableData_Object" border style="width: 100%">
                                <el-table-column prop="sname" label="货物名称"  />
                                <el-table-column prop="storageNum" label="货物数量"  />
                            </el-table>
                        </div>
                        <div class="out-date-table">
                            <!-- <el-text class="mx-2" type="primary">过期产品信息</el-text> -->
                            <p class="mx-2">过期产品信息</p>
                            <el-table :data="tableData_supmarket_outdata" border style="width: 100%">
                                <el-table-column prop="sid" label="商品ID"  />
                                <el-table-column prop="sname" label="商品名称"  />
                                <el-table-column prop="cid" label="货物编号"  />
                                <el-table-column prop="productionDate" label="生产日期"  />
                                <el-table-column prop="shelfLife" label="保质期"  />
                                <el-table-column prop="inputTime" label="入库时间"  />
                                <el-table-column prop="outputTime" label="出库时间"  />
                                <el-table-column prop="state" label="货物状态"  />
                                <el-table-column prop="suid" label="超市ID"  />
                            </el-table>
                        </div>
                    </el-tab-pane>

                    <el-tab-pane label="订单通知" name="second">
                        <!-- 展示消息区域 -->
                        <el-table :data="tableData_Info" style="width: 100%">
                            <el-table-column type="expand">

                                <template slot-scope="props">

                                    <div m="4">
                                        <!-- 订单的所有历史状态 -->
                                        <el-table :data="props.row.history" >
                                            <el-table-column label="历史状态" prop="action" />
                                            <el-table-column label="时间" prop="time" />
                                        </el-table>
                                        <!-- 该订单失败的原因 -->

                                        <el-table v-if="props.row.flag" :data="props.row.reasonTable">
                                            <el-table-column label="货物编号" prop="id"/>
                                            <el-table-column label="超市" prop="suid"/>
                                            <el-table-column label="货物名称" prop="name"/>
                                            <el-table-column label="货物类型" prop="type"/>
                                            <el-table-column label="失败原因" prop="reason"/>
                                            <el-table-column label="生产日期" prop="productionDate"/>
                                            <el-table-column label="数量" prop="num"/>
                                            <el-table-column label="货架尺寸" prop="shelfLife"/>
                                        </el-table>
                                    </div>

                                </template>
                            </el-table-column>

                            <!-- <el-table-column label="日期" prop="time" /> -->
                            <!-- <el-table-column label="仓库" prop="rid" /> -->
                            <el-table-column label="订单编号" prop="orderID" />
                            <el-table-column label="当前状态" prop="state" />
                            <el-table-column label="操作时间" prop="time" />
                            <el-table-column label="说明" prop="others" />

                        </el-table>
                    </el-tab-pane>

                    <el-tab-pane label="缴费" name="third">
                        <el-table
                            :data="paymentTable"
                            style="width: 100%"
                        >
                            <el-table-column prop="orderID" label="订单编号"/>
                            <el-table-column prop="cost" label="花费"/>
                            <el-table-column prop="time" label="时间"/>

                            <el-table-column label="" >
                                <template slot-scope="scope">
                                    <!-- <el-button @click="payHandleClick(scope.row)" type=primary size="small">缴费</el-button> -->
                                    <!-- <el-button @click="open(scope.row)" type=primary size="small">缴费</el-button> -->
                                    <!-- 进入缴费界面 -->
                                    <!-- <el-button type="primary" size='small' @click="dialogFormVisible=true">缴费</el-button> -->
                                    <el-button type="primary" size='small' @click="openPayUI(scope.row)">缴费</el-button>
                                    <!-- diaglog -->
                                    <el-dialog title="缴费"
                                               :visible.sync="dialogFormVisible"
                                               :modal = false
                                               width="25%"  top="18%" center>
                                        <el-form class="dialog-el-form">
                                            <el-form-item label="缴费方式 :" label-width="100px">
                                                <el-select v-model="paytype"  :disabled="disabled" placeholder="请选择缴费方式" width="50%">
                                                    <el-option label="全部缴费" value=1></el-option>
                                                    <el-option label="缴费10%" value=0.1></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-form>

                                        <div slot="footer" class="dialog-footer">
                                            <el-button size='small' @click="dialogFormVisible = false">取 消</el-button>
                                            <el-button type="primary" size='small' @click="payHandleClick(scope.row)">确 定</el-button>
                                        </div>
                                    </el-dialog>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-tab-pane>
                    <!-- 订单缴费日志 -->
                    <el-tab-pane label="缴费日志" name="forth">
                        <el-table
                            :data="paymentTableLog"
                            style="width: 100%"
                        >
                            <el-table-column prop="orderID" label="订单编号"/>
                            <el-table-column prop="time" label="时间"/>
                            <el-table-column prop="money" label="花费"/>
                            <el-table-column prop="statement" label="说明"/>

                            <!-- <el-table-column  label="" >
                                <template slot-scope="scope">
                                    <el-button @click="payHandleClick(scope.row)" type=primary size="small">缴费</el-button>
                                </template>
                            </el-table-column> -->

                        </el-table>
                    </el-tab-pane>
                    <!-- 确认表单 -->
                    <el-tab-pane label="确认审核单" name="fifth">
                        <div>
                            <el-table
                                :data="confirmTable"
                                style="width: 100%;">
                                <!-- <el-table-column
                                            type="selection"
                                            width="55">
                                </el-table-column> -->
                                <el-table-column type="expand">
                                    <template slot-scope="props">
                                        <el-table
                                            :data="props.row.confirm"
                                            style="width: 100%"
                                            @selection-change="handleSelectionChange"
                                            ref="multipleTable">
                                            <el-table-column
                                                type="selection"
                                                width="55">
                                            </el-table-column>
                                            <el-table-column
                                                prop="orderID"
                                                label="订单编号"
                                                sortable>
                                            </el-table-column>
                                            <el-table-column
                                                prop="sid"
                                                label="货物编号"
                                                sortable>
                                            </el-table-column>
                                            <el-table-column
                                                prop="num"
                                                label="数量"
                                                sortable>
                                            </el-table-column>
                                            <el-table-column
                                                prop="statement"
                                                label="说明">
                                            </el-table-column>
                                        </el-table>
                                    </template>

                                </el-table-column>
                                <el-table-column
                                    prop="orderID"
                                    label="订单编号"
                                    sortable>
                                </el-table-column>
                            </el-table>

                            <!-- <el-table
                                :data="confirmTable"
                                style="width: 100%"
                                row-key="orderID"
                                @selection-change="handleSelectionChange"
                                ref="multipleTable"
                                :tree-props="{children:'children', hasChildren:'hasChildren'}">
                                        <el-table-column
                                            type="selection"
                                            width="55">
                                        </el-table-column>
                                        <el-table-column
                                            prop="orderID"
                                            label="订单编号"
                                            sortable>
                                        </el-table-column>
                                        <el-table-column
                                            prop="sid"
                                            label="货物编号"
                                            sortable>
                                        </el-table-column>
                                        <el-table-column
                                            prop="num"
                                            label="数量"
                                            sortable>
                                        </el-table-column>
                                        <el-table-column
                                            prop="statement"
                                            label="说明">
                                        </el-table-column>
                            </el-table> -->
                        </div>

                        <div style="margin-top: 20px;" >
                            <el-button type=primary size="small" @click="confirm" :disabled="disabledBtu">确 认</el-button>
                        </div>
                    </el-tab-pane>

                </el-tabs>

            </div>
        </div>
    </div>
</template>

<script>
// import Cookies from 'js-cookie'
import CliTitle from "../../components/base/cliTitle";
import Titlebar from "../../components/base/titlebar";
import axios from "axios";
import Cookies, { get } from "js-cookie";
// 更改
import cliMenu from "../../components/base/cliMenu3";

export default {
    name: "index",
    components: {
        Titlebar,
        CliTitle,
        cliMenu,
    },


    data(){
        return {
            suid:'',
            activeName:'first',
            // // 定时器
            // timer:'',
            confirmTable:[
                {
                    orderID:1,
                    confirm:[{
                        orderID:1,
                        num:'1',
                        statement:'1',
                        state:'1',
                        sid:'1',
                    },
                        {
                            orderID:1,
                            num:'2',
                            statement:'2',
                            state:'2',
                            sid:'2',
                        }]
                },
                {
                    orderID:2,
                    confirm:[{
                        orderID:2,
                        num:'1',
                        statement:'1',
                        state:'1',
                        sid:'3',
                    },
                        {
                            orderID:2,
                            num:'2',
                            statement:'2',
                            state:'2',
                            sid:'4',
                        }]
                }
            ],
            tableData_Info:[
                {
                    orderID:'',
                    state:'',
                    time:'',
                    others:'',
                    flag:false,
                    history:[
                        {
                            orderID:'',
                            action:'',
                            time:''
                        }
                    ],
                    reasonTable:[
                        {
                            id:'',
                            suid:'',
                            name:'',
                            type:'',
                            reason:'',
                            productionDate:'',
                            num:'',
                            shelfLife:'',
                        }
                    ]
                }
            ],
            // 超市存储货物信息表
            tableData_Object:[
                {
                    sname:'农夫山泉',
                    storageNum:'100'
                }
            ],

            // 过期信息表
            tableData_supmarket_outdata:[
                {
                    sid:'1',
                    sname:'农夫山泉',
                    cid:'00000063',
                    productionDate:'2023-03-23',
                    shelfLife:'1',
                    inputTime:'2023-04-20',
                    outputTime:'2023-04-20',
                    state:'not expire',
                    suid:'101'
                }
            ],
            paymentTable: [
                {
                    orderID:'111',
                    cost:'123',
                    time:"111",
                    paytype:''
                }
            ],
            paymentTableLog:[
                {
                    orderID:'111',
                    time:'111',
                    money:'111',
                    statement:'111',
                }
            ],
            dialogFormVisible: false,
            paytype:'',
            disabled:false,
            multipleSelection: [],
            disabledBtu: false
        }
    },

    methods:{
        logout() {
            const url = "/"
            window.location.replace(url);
        },
        // 获取多选数据
        handleSelectionChange(val){
            this.multipleSelection = val
            console.log(this.multipleSelection)
        },

        // 确认审核单
        confirm(){

            let data ={
                token:Cookies.get('token'),
                array:[]
            }

            this.multipleSelection.forEach(element=>{
                data.array.push(element)
            })

            axios.post('http://localhost:8181/supermarket/show_Confirm',data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then(res=>{
                if(res.data){
                    // console.log(res)
                    const h = this.$createElement;
                    this.$notify({
                        title: '通知',
                        type:'success',
                        message: h('i', { style: 'color: teal'}, '确认审核单成功'),
                        offset: 55
                    });
                    this.getConfirm()
                }else{
                    const h = this.$createElement;
                    this.$notify({
                        title: '通知',
                        type:'error',
                        message: h('i', { style: 'color: red'}, '确认审核失败'),
                        offset: 55
                    });
                }
            })
        },

        // 获取审核单
        getConfirm(){
            let data = {
                token:Cookies.get('token'),
                suid:this.suid,
            }
            let temp_confirmTable=
                {
                    orderID:'',
                    confirm:[]
                }
            axios.post('http://localhost:8181/supermarket/show_notConfirm',data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then(res=>{
                console.log(res.data)
                this.confirmTable = []
                let idTable = []
                let num = 0
                res.data.forEach(element=>{
                    if(!idTable.includes(element.orderID)){
                        // 当前元素的ID从未出现

                        temp_confirmTable.orderID = element.orderID
                        temp_confirmTable.confirm.push(element)
                        num+=1
                    }else{
                        // 当前元素已经出现
                        temp_confirmTable.confirm.push(element)
                        num+=1
                    }

                    if(num==res.data.length){
                        this.confirmTable.push(temp_confirmTable)
                        console.log(this.confirmTable)
                    }

                })

                if(this.confirmTable.length===0){
                    // 没选择数据
                    this.disabledBtu = true
                }

            })
        },

        // tab切换
        handleClick(activeName){
            // if(activeName="first"){
            //     // 获取超市货物信息
            //     this.getSupermarketData()
            //     // 获取超市过期商品
            //     this.getExpiredCargo()
            // }else if(activeName="second"){
            //     // 获取超市订单通知
            //     this.getSupermarketInfo()
            // }else if(activeName="third"){
            //     // 获取待缴费订单
            //     this.getPaymentOrder()
            // }else if(activeName="forth"){
            //     // 获取缴费日志
            //     this.getPaymentOrderLog()
            // }else if(activeName="fifth"){
            //     // 获取审核单
            //     this.getConfirm()
            // }
        },

        // 打开缴费UI
        openPayUI(row){
            this.dialogFormVisible = true
            if(row.payMethod != 0){
                this.paytype = row.payMethod
                this.disabled=true
            }

        },

        // 获取缴费日志
        getPaymentOrderLog(){

            let data = {
                token:Cookies.get('token'),
                suid:this.suid,
            }

            axios.post('http://localhost:8181/state/getPaymentOrderLog',data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then(res=>{
                this.paymentTableLog = []
                res.data.forEach(element=>{
                    this.paymentTableLog.push(element)
                })
            })

        },

        // 获取待缴费订单
        getPaymentOrder(){

            let data = {
                token:Cookies.get('token'),
                suid:this.suid
            }

            axios.post('http://localhost:8181/state/show_choose_payMethod',data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then(res=>{
                this.paymentTable = []
                res.data.forEach(element=>{
                    this.paymentTable.push(element)
                })
            })

        },

        // 表格缴费
        payHandleClick(row){
            this.dialogFormVisible = false
            let data = {
                token:Cookies.get('token'),
                orderID:row.orderID,
                payMethod:this.paytype
            }

            console.log(row.payMethod)
            console.log(data.payMethod)
            if(row.payMethod != 0){

                // 已经选择了支付方式
                axios.post('http://localhost:8181/state/finish_payment',data,{
                    headers:{
                        token:JSON.parse(localStorage.getItem('token'))
                    }
                }).then(res=>{
                    if(res.data){
                        const h = this.$createElement;
                        this.$notify({
                            title: '通知',
                            type:'success',
                            message: h('i', { style: 'color: teal'}, '缴费成功'),
                            offset: 55
                        });
                        this.getPaymentOrder()

                    }else{
                        const h = this.$createElement;
                        this.$notify({
                            title: '通知',
                            type:'error',
                            message: h('i', { style: 'color: red'}, '缴费失败'),
                            offset: 55
                        });
                    }
                })

            }else{

                // 还没选择支付方式
                axios.post('http://localhost:8181/state/choose_payMethod',data,{
                    headers:{
                        token:JSON.parse(localStorage.getItem('token'))
                    }
                }).then(res=>{
                    if(res.data){
                        const h = this.$createElement;
                        this.$notify({
                            title: '通知',
                            type:'success',
                            message: h('i', { style: 'color: teal'}, '缴费成功'),
                            offset: 55
                        });
                        this.getPaymentOrder()

                    }else{
                        const h = this.$createElement;
                        this.$notify({
                            title: '通知',
                            type:'error',
                            message: h('i', { style: 'color: red'}, '缴费失败'),
                            offset: 55
                        });
                    }
                })
            }

        },

        // 获取当前超市的存储信息
        getSupermarketData(){
            let data = {
                token:Cookies.get('token'),
                suid:this.suid
            }
            this.tableData_Object = []
            axios.post('http://localhost:8181/supermarket/get_storage',data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then((res) =>{
                // 添加数据
                res.data.forEach(element => {
                    this.tableData_Object.push(element)
                });
            })
        },

        // 获取过期产品信息
        getExpiredCargo(){
            let data = {
                token:Cookies.get('token'),
                suid:this.suid
            }
            this.tableData_supmarket_outdata = []
            axios.post('http://localhost:8181/cargo/showExpire',data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then((res)=>{
                // console.log(res)
                // 添加数据
                res.data.forEach(element => {
                    this.tableData_supmarket_outdata.push(element)
                });
            })
        },

        // 获取某个商品的所有历史信息
        getHistory(orderID,callback){
            // 获取历史信息
            let data = {
                token:Cookies.get('token'),
                orderID: orderID,
            }

            let history=[]
            axios.post("http://localhost:8181/state/show_supermarket_order_allState", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(res =>{
                    res.data.forEach(element=>{
                        history.push(element)
                    })
                    callback(history)
                })
        },

        // 获取失败商品的消息
        getFailInfo(orderID,callback){
            let data = {
                token:Cookies.get('token'),
                orderID: orderID,
            }
            let reasonTable = []
            axios.post("http://localhost:8181/state/show_supermarket_order_notInput",data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(res=>{
                    res.data.forEach(element=>{
                        reasonTable.push(element)
                    })
                    callback(reasonTable)
                })
        },


        // 获取消息
        getSupermarketInfo(){
            let headers={
                token:Cookies.get('token'),
            }
            let data = {
                suid: this.suid,
            }
            this.tableData_Info = []
            axios.post("http://localhost:8181/state/show_supermarket_allOrder", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    // 循环订单添加
                    // 每次获取一个订单
                    response.data.forEach( element => {
                        let temp_table = {
                            orderID:element.orderID,
                            state:element.state,
                            time:element.time,
                            others:element.others,
                            flag:false,
                            history: [],
                            reasonTable: [],
                        }

                        // 获取订单的历史状态
                        this.getHistory(element.orderID,function(history){
                            temp_table.history = history
                        })

                        // 获取失败订单的原因
                        this.getFailInfo(element.orderID,function(reasonTable){
                            if(reasonTable.length != 0){
                                temp_table.reasonTable = reasonTable
                                temp_table.flag = true
                            }
                        })
                        this.tableData_Info.push(temp_table)
                    })
                })
        },
        // 打开缴费窗口

    },
    mounted(){
        this.suid=String(this.$route.query.suid);
        // 获取超市订单通知
        this.getSupermarketInfo()
        // 获取超市货物信息
        this.getSupermarketData()
        // 获取超市过期商品
        this.getExpiredCargo()
        // 获取待缴费订单
        this.getPaymentOrder()
        // 获取补差价订单
        // this.getOrderPayment()
        // 获取缴费日志
        this.getPaymentOrderLog()
        // 获取审核单
        this.getConfirm()
        // 定时发送请求,
        //this.timer=setInterval(this.getSupermarketInfo,1000);
    }

}

</script>

<style scoped>
.titleClass {
    position: fixed;
    min-width: 1000px;
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
    /*width: 100%;*/
    min-height: calc(100% - 70px);
    padding: 1.5rem 1.5rem 10rem;
    height: 90%;
    overflow-y: auto;
}
.demo-tabs{
    /* padding-top: 0px; */
    color: #6b778c;
    /* font-size: 25px; */
    /* font-weight: 600; */
}
/* .mx-1{
  position: fixed;
} */
.object-table{
    display: flex;
    flex-direction: column;
    margin-bottom: 100px;
}
.mx-1{
    text-align: center;
    margin-bottom: 10px;
}

.out-date-table{
    display: flex;
    flex-direction: column;
}
.mx-2{
    text-align: center;
    margin-bottom: 10px;
}
.dialog-el-form{
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>
