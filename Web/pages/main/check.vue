<template>
    <div class="container-scroller">
        <!--      顶栏-->
        <titlebar></titlebar>
        <cli-title class="titleClass"></cli-title>
        <!--      侧边栏-->
        <cli-menu class="menuClass" ></cli-menu>

        <div class="bodyDiv">
            <el-table
                :data="toCheckData"
                style="width: 100%"
                border
                @expand-change="searchTransport"
            >
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-table
                            :data="detailinfo"
                            style="width: 100%"
                            border
                        >
                            <el-table-column prop="sw" label="sw"/>
                            <el-table-column prop="orderID" label="订单ID"/>
                            <el-table-column prop="stype" label="类别"/>
                            <el-table-column prop="num" label="数量"/>
                            <el-table-column prop="weight" label="重量"/>
                            <el-table-column prop="inputTime" label="入库时间"/>
                            <el-table-column prop="outputTime" label="出库时间"/>
                            <el-table-column prop="suid" label="超市ID"/>
                            <el-table-column prop="sid" label="类别"/>
                            <el-table-column prop="productionDate" label="生产日期"/>
                            <el-table-column prop="sname" label="货物品名"/>
                            <el-table-column prop="shelfLife" label="保质期"/>
                        </el-table>
                        <div style="margin-top: 20px; padding-left: 60%">
                            <el-button id="sucCheck"  type="success" @click="sucCheck()">通过审查</el-button>
<!--                            <el-button id="failCheck" type="danger" @click="failCheck()">不通过审查</el-button>-->
                            <el-popover
                                placement="right"
                                width="400"
                                trigger="click">
                                <el-input
                                    type="textarea"
                                    placeholder="审查未通过的理由"
                                    v-model="reason"
                                    maxlength="30"
                                    show-word-limit
                                >
                                </el-input>
                                <el-button id="failCheck" type="danger" style="margin-top: 20px" @click="failCheck()">不通过审查</el-button>

                                <el-button slot="reference" style="margin: 30px">不通过审查</el-button>
                            </el-popover>
                        </div>
                    </template>
                </el-table-column>

                <el-table-column prop="suid" label="超市ID"/>
                <el-table-column prop="orderID" label="货物名称"/>
                <el-table-column prop="time" label="待审核 时间" />
            </el-table>

        </div>
    </div>
</template>

<script>
import List from "../../components/message/list";
import CliTitle from "../../components/base/cliTitle";
import CliMenu from "../../components/base/cliMenu";
import Position from "../../components/base/position";
import DataEdit from "../../components/message/message_edit";
import Message_detail from "../../components/message/message_detail";
import Notify_list_table from "../../components/notify/notify_list_table";
import Sidebar from "../../components/base/sidebar";
import Titlebar from "../../components/base/titlebar";
import axios from "axios";

export default {
    name: "check",
    components: {
        Titlebar,Sidebar, Notify_list_table, Message_detail, DataEdit, Position, CliMenu, CliTitle, List},
    mounted() {
        this.uid = String(this.$route.query.uid);
        this.getTocheckInfo();
    },
    data() {
        return {
            uid:'',
            file:null,
            toCheckData:[],
            detailinfo:[],
            reason:" ",
        }
    },
    methods: {
        searchTransport(row,expandedRows){
            if(expandedRows.length>0){
                let data = {
                    orderID: row.orderID
                };
                axios.post('http://localhost:8181/state/manual_review_detail', data, {
                    headers:{
                        token:JSON.parse(localStorage.getItem('token'))
                    }
                })
                .then(response => {
                    this.detailinfo=response.data;
                    /*for(var i=0;i<this.detailTransport.length;i++){
                        this.detailTransport[i].fromDate= this.detailTransport[i].fromDate.slice(0, 10);
                    }*/
                })
                .catch(error => {
                    console.log(error);
                });
            }else{
                this.detailinfo=[];
            }
        },
        getTocheckInfo(){
            axios({
                method:'get',
                url:'http://localhost:8181/state/manual_review',
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then((response)=>{
                this.toCheckData = [],
                response.data.forEach(item => {
                    let temp_data = {
                        suid:item.suid,
                        orderID:item.orderID,
                        time:item.time,
                    }
                    this.toCheckData.push(temp_data)
                })
            })
        },
        // 通过人工审查
        sucCheck(){
            document.getElementById("sucCheck").disabled=true;
            console.log("33333333333333333333")
            console.log(this.detailinfo[0].orderID)
            let data = {
                orderID: this.detailinfo[0].orderID
            };
            axios.post('http://localhost:8181/state/manual_review_passed', data, {
                headers: {'token': this.token}
            })
            .then(response => {
                console.log("通过审查")
                alert('审查通过')
                this.getTocheckInfo();
            })
            .catch(error => {
                console.log(error);
            });
            document.getElementById("sucCheck").disabled=false;
        },
        // 不通过审查
        failCheck(){
            document.getElementById("failCheck").disabled=true;
            let data = {
                orderID: this.detailinfo[0].orderID,
                reason:this.reason
            };
            axios.post('http://localhost:8181/state/manual_review_failed', data, {
                headers: {'token': this.token}
            })
                .then(response => {
                    alert("成功提交审查不通过")
                    this.getTocheckInfo();
                })
                .catch(error => {
                    console.log(error);
                });

            document.getElementById("failCheck").disabled=false;
        }
    },
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
    min-height: calc(100% - 70px);
    padding: 1.5rem 1.5rem 10rem;
    height: 90%;
    overflow-y: auto;
}

.upload{
    position: fixed;
    top: 700px;
    left: 1200px;
}
.input_button{
    margin-bottom: 5px;
}
</style>