<template>
    <div class="container-scroller">
        <!--      顶栏-->
        <titlebar></titlebar>
        <cli-title class="titleClass"></cli-title>
        <!--      侧边栏-->
        <cli-menu class="menuClass"  :pageIndex="pageIndex"></cli-menu>

        <div class="bodyDiv">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="用户id"  :disabled="true">
                    <el-input v-model="form.id" style="width: 500px"  :disabled="true"></el-input>
                </el-form-item>
                <el-form-item v-if="isdisplay2" label="原密码">
                    <el-input v-model="form.oldpas"  style="width: 500px"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input id="ps"  v-model="form.pas"  style="width: 500px" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">确认修改</el-button>
                    <el-button @click="doSubmit">保存</el-button>
                </el-form-item>
            </el-form>

            <el-button style="margin-top: 50px" type="primary" size="large" @click="display">创建超市账户 </el-button>

            <el-form style="margin-top: 20px" v-if="isdisplay" ref="form" :model="form2" label-width="80px">
                <el-form-item label="超市id">
                    <el-input v-model="form2.suid" aria-required="true" style="width: 500px" maxlength="10" minlength="4"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form2.password" style="width: 500px" show-password maxlength="10" minlength="6"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addUser">立即创建</el-button>
                </el-form-item>
            </el-form>
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
import el from "element-ui/src/locale/lang/el";

export default {
    name: "admin",
    components: {
        Titlebar,Sidebar, Notify_list_table, Message_detail, DataEdit, Position, CliMenu, CliTitle, List},
    mounted() {
        this.uid =String(this.$route.query.uid);
        this.form.id = String(this.$route.query.uid);
    },
    data() {
        return {
            uid:'',
            pageIndex: "5",

            form: {
                id:this.uid,
                pas:'',
                oldpas:'',
            },
            form2: {
                suid:'',
                password: '',
            },
            isdisplay:0,
            isdisplay2:0,
        }
    },
    methods: {
        display(){
            this.isdisplay = 1;
        },
        addUser(){
            let data = {
                suid: this.form2.suid,
                password: this.form2.password
            }
            axios.post("http://localhost:8181/user/addSupermarket", data,
                {
                    headers:{
                        token:JSON.parse(localStorage.getItem('token'))
                    }
                })
                .then(response => {
                    if(response.data.code==1){
                        alert("超市账户添加成功");
                    }else {
                        alert("账户添加失败！")
                    }
                    this.isdisplay=0;
                })
        },
        onSubmit(){
            const inputElement = document.getElementById("ps");
            inputElement.disabled = false;
            this.isdisplay2=1;
        },
        doSubmit(){
            if(this.form.pas==""||this.form.oldpas==""){
                alert("密码不可为空！")
            }else{
                let data={
                    uid:this.form.id,
                    password:this.form.pas,
                    old_password:this.form.oldpas,
                }
                axios.post("http://localhost:8181/user/modifyPassword",data,
                    {
                        headers:{
                            token:JSON.parse(localStorage.getItem('token'))
                        }
                    })
                    .then(response => {
                        if(response.data.code==1){
                            alert(response.data.msg);
                        }else {
                            alert(response.data.msg);
                        }
                    })
            }
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