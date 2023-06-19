<template>
    <div class="container-scroller">
        <!--      顶栏-->
        <titlebar></titlebar>
        <cli-title class="titleClass"></cli-title>
        <!--      侧边栏-->
        <cli-menu class="menuClass" ></cli-menu>

        <div class="bodyDiv">
            <!--货物审查信息表格展示-->
            <h3>入库失败表格</h3>
            <el-table
                    :data="tableData"
                    style="width: 100%"
                    border
            >
<!--                <el-table-column prop="id" label="ID" width="80" />-->
<!--                <el-table-column prop="name" label="名称" width="180" />-->
<!--                <el-table-column prop="suid" label="超市id" width="180" />-->
<!--                <el-table-column prop="reason" label="理由" width="380" />-->
<!--                <el-table-column prop="num" label="数量" width="120"/>-->
<!--                <el-table-column prop="type" label="货物种类" />-->
<!--                <el-table-column prop="shelf_life" label="保质期" width="200" />-->
<!--                <el-table-column prop="production_date" label="生产日期" width="180" />-->
<!--                <el-table-column prop="orderID" label="订单编号" width="180" />-->
                <el-table-column prop="id" label="ID"/>
                <el-table-column prop="name" label="名称" />
                <el-table-column prop="suid" label="超市id"  />
                <el-table-column prop="reason" label="理由"/>
                <el-table-column prop="num" label="数量"/>
                <el-table-column prop="type" label="货物种类" />
                <el-table-column prop="shelf_life" label="保质期" />
                <el-table-column prop="production_date" label="生产日期" />
                <el-table-column prop="orderID" label="订单编号" />
            </el-table>


            <h3 style="margin-top: 40px">出库失败表格</h3>
            <el-table
                :data="outtableData"
                style="width: 100%"
                border
            >
                <el-table-column prop="sid" label="货物品类ID"/>
                <el-table-column prop="name" label="货物名称"/>
                <el-table-column prop="suid" label="超市id" />
                <el-table-column prop="reason" label="理由"/>
                <el-table-column prop="num" label="数量" />
                <el-table-column prop="orderID" label="订单编号" />
            </el-table>

            <!--文件上传按钮-->
<!--            <div class="upload">
                <input class="input_button" formenctype="multipart/form-data" type="file" ref="fileInput" @change="handleFileUpload">
                <button class="upload_button" @click="submitFile">上传文件</button>
            </div>-->

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
import Cookies from "js-cookie";

export default {
    name: "admin",
    components: {
        Titlebar,Sidebar, Notify_list_table, Message_detail, DataEdit, Position, CliMenu, CliTitle, List},
    mounted() {
        this.uid = String(this.$route.query.uid);
        this.getFailInfo();
        this.getFailOutInfo();
    },
    data() {
        return {
            uid:'',
            file:null,
            tableData:[
                {
                    date: '2016-05-03 14:00:00',
                    id: '1',
                    status: 'success',
                    info: 'import success!',
                }],
            outtableData:[],
        }
    },
    methods: {
        getFailInfo(){
            let aData = new Date()
            let dateValue = aData.getFullYear() + "-" + (aData.getMonth() + 1) + "-" + aData.getDate()
            axios({
                method:'get',
                url:'http://localhost:8181/cargo/show_notInput',
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then((response)=>{
                // 处理货物入库失败信息
                this.tableData = []
                response.data.forEach(item => {
                    let temp_data = {
                        name:item.name,
                        suid:item.suid,
                        info:item.info,
                        num:item.num,
                        type:item.type,
                        shelf_life:item.shelf_life,
                        id: item.id,
                        reason: item.reason,
                        production_date:item.production_date,
                        orderID:item.orderID
                    }
                    this.tableData.push(temp_data)
                })
            })
        },
        getFailOutInfo(){
            console.log("token是",JSON.parse(localStorage.getItem('token')))
            axios({
                method:'get',
                url:'http://localhost:8181/cargo/show_notOutput',
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then((response)=>{
                // 处理货物入库失败信息
                this.outableData = []
                response.data.forEach(item => {
                    let temp_data = {
                        suid:item.suid,
                        num:item.num,
                        sid: item.sid,
                        reason: item.reason,
                        name:item.name,
                        orderID:item.orderID
                    }
                    this.outtableData.push(temp_data)
                })
            })
        },
        // handleFileUpload(event) {
        //     this.file = event.target.files[0];
        // },
        // submitFile() {
        //     let formData = new FormData();
        //     formData.append('file', this.file);
        //     axios.post('http://localhost:8181/file/importFileInput', formData,{
        //         headers:{
        //             token:Cookies.get('token')
        //         }
        //     })
        //         .then(response => {
        //             // 处理返回信息
        //             console.log(response.data);
        //             alert(response.data.msg);
        //         })
        //         .catch(error => {
        //             console.log(error);
        //         });
        // },
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