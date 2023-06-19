<template>
  <div class="outdiv">
    <cli-title class="titleClass"></cli-title>
    <cli-menu class="menuClass" :pageIndex="pageIndex"></cli-menu>
    <div class="bodyDiv">
<!--      第一行-->
      <el-row class="demo-autocomplete">
        <el-col :span="6">
          <div class="sub-title">货物种类</div>
            <el-select v-model="value" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
            </el-select>
        </el-col>

        <el-col :span="6">
          <div class="sub-title">货物名称</div>
            <el-select v-model="sid" placeholder="请选择">
                <el-option
                        v-for="item in options2"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
        </el-col>

        <el-col :span="6">
          <div class="sub-title">货物编号</div>
          <el-input style="width: 200px" v-model="cid" placeholder="请输入内容"></el-input>
        </el-col>

        <el-col :span="6">
          <div class="sub-title">仓库柜ID</div>
          <el-input style="width: 200px" v-model="ceid" placeholder="请输入内容"></el-input>
        </el-col>
      </el-row>

<!--      第二行-->
      <el-row style="margin-top: 20px" class="demo-autocomplete">
        <el-col :span="6">
          <div class="sub-title">入库时间</div>
            <el-date-picker
                    v-model="input_time"
                    type="date"
                    placeholder="选择日期"
                    value-format="yyyy-MM-dd"
                    :picker-options="pickerOptions1">
            </el-date-picker>
        </el-col>

        <el-col :span="6">
          <div class="sub-title">出库时间</div>
            <el-date-picker
                    v-model="output_time"
                    type="date"
                    placeholder="选择日期"
                    value-format="yyyy-MM-dd"
                    :picker-options="pickerOptions1">
            </el-date-picker>
        </el-col>

        <el-col :span="6">
          <div class="sub-title">超市id</div>
          <el-input style="width: 200px" v-model="suid" placeholder="请输入内容"></el-input>
        </el-col>

        <el-col :span="6">
          <el-button style="margin-top: 20px" type="primary" @click="search">查询</el-button>
        </el-col>
      </el-row>

      <div>
        <el-table
            :data="tableData"
            style="width: 100%;margin-top: 30px">
          <el-table-column
                  prop="stype"
                  label="货物种类"
                  width="180">
          </el-table-column>
          <el-table-column
              prop="sid"
              label="货物品类id"
              width="180">
          </el-table-column>
          <el-table-column
                  prop="sname"
                  label="货物名称"
                  width="180">
          </el-table-column>
          <el-table-column
              prop="cid"
              label="货物编号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="ceid"
              label="仓库柜id">
          </el-table-column>
          <el-table-column
              prop="input_time"
              label="入库时间">
          </el-table-column>
          <el-table-column
              prop="output_time"
              label="出库时间">
          </el-table-column>
          <el-table-column
              prop="suid"
              label="超市id">
          </el-table-column>
        </el-table>
      </div>
    </div>

  </div>


</template>

<script>
import CliTitle from "../../components/base/cliTitle";
import CliMenu from "../../components/base/cliMenu";
import Position from "../../components/base/position";
import CliUpload from "../../components/base/cliUpload";
import axios from "axios";
import Cookies from "js-cookie";
import * as url from "url";
export default {
  name: "test",
  components: {CliUpload, Position, CliMenu, CliTitle},
  data(){
    return{
      options: [{
        value: '饮品',
        label: '(1)饮品类'
      }, {
        value: '零食',
        label: '(2)零食类'
      }, {
        value: '日用品',
        label: '(3)日用品类'
      }, {
        value: '易炸品',
        label: '(4)易燃易炸'
      }, {
          value: '',
          label: '空'
      }],
        options2: [{
            value: '1',
            label: '农夫山泉'
        }, {
            value: '2',
            label: '坚果饼干'
        }, {
            value: '3',
            label: '卷纸'
        }, {
            value: '4',
            label: '中桶矿泉水'
        }, {
            value: '6',
            label: '红牛'
        },{
            value: '7',
            label: '手套'
        },{
            value: '8',
            label: '红枣牛奶'
        },{
            value: '',
            label: '空'
        }],
        value: '',

      choice:0,//代表要发送哪个接口,0是不发送，1是stype单，2是sid单，3是cid单

      pickerOptions1: {
          disabledDate(time) {
              return time.getTime() > Date.now();
          },
          shortcuts: [{
              text: '今天',
              onClick(picker) {
                  picker.$emit('pick', new Date());
              }
          }, {
              text: '昨天',
              onClick(picker) {
                  const date = new Date();
                  date.setTime(date.getTime() - 3600 * 1000 * 24);
                  picker.$emit('pick', date);
              }
          }, {
              text: '一周前',
              onClick(picker) {
                  const date = new Date();
                  date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                  picker.$emit('pick', date);
              }
          }]
      },
      ceid:'',
      input_time:'',
      output_time:'',
      suid:'',//超市id
      cid:'',//货物编号
      stype:'',//货物种类
      sid:'',//货物品类id
      sname:'',//货物品名
      tableData: [],
      falsecargo:[],
      num:'',//货物存量

      conditionCount:0,
      peopleIndex:"1",
      currentGruop:0,
      delectGroup : "" ,
      group:[],
      joinGroupName:"",
      checkall:false,
      pageIndex: "4",
      groupForm:{name:"",user_ids:[]},
      checkedids:[],
      person:'person',
    }
  },
  methods:{
    search(){
        if(this.value!==null&&this.value!==""){
            this.choice=1;
            if(this.input_time!==null&&this.input_time!==""){
                this.choice=5;
            }else if(this.output_time!==null&&this.output_time!==""){
                this.choice=6;
            }else if(this.suid!==null&&this.suid!==""){
                this.choice=7;
            }
        }
        if(this.sid!==null&&this.sid!==""){
            this.choice=2;
            if(this.input_time!==null&&this.input_time!==""){
                this.choice=8;
            }else if(this.output_time!==null&&this.output_time!==""){
                this.choice=9;
            }else if(this.suid!==null&&this.suid!==""){
                this.choice=10;
            }
        }
        if(this.cid!==null&&this.cid!==""){
            this.choice=3;
        }
        if(this.ceid!==null&&this.ceid!==""){
            this.choice=4;
        }
        if(this.suid!==null&&this.suid!==""){
            this.choice=11;
        }
        if((this.value==null||this.value=="")&&(this.sid==null||this.sid=="")&&(this.cid==null||this.cid=="")){
            if((this.input_time==null||this.input_time=="")&&(this.output_time==null||this.output_time=="")&&(this.suid==null||this.suid=="")){
                    this.choice=0;
            }
        }
        if(this.choice==1){
            // 成功
            let data={
                token:Cookies.get('token'),
                stype:this.value
            }
            axios.post("http://localhost:8181/save/findByStype", data,
                {
                    headers:{
                        token:JSON.parse(localStorage.getItem('token'))
                    }
                })
                .then(response => {
                    this.tableData=response.data;
                })
        }else if(this.choice==2){
            let data={
                token:Cookies.get('token'),
                sid:this.sid
            }
            axios.post("http://localhost:8181/save/findBySid", data,
                {
                    headers:{
                        token:JSON.parse(localStorage.getItem('token'))
                    }
                })
                .then(response => {
                    this.tableData=response.data;
                })
        }else if(this.choice==3){
            // 成功
            let data={
                token:Cookies.get('token'),
                cid:this.cid
            }
            axios.post("http://localhost:8181/save/findByCid", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    console.log(response)
                    this.tableData=response.data;
                })
        }else if(this.choice==4){
            // 成功
            let data={
                token:Cookies.get('token'),
                ceid:this.ceid
            }
            axios.post("http://localhost:8181/save/findByCeid", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    console.log(response)
                    this.tableData=response.data;
                })
        }else if(this.choice==5){
            // 成功
            let data={
                token:Cookies.get('token'),
                stype:this.value,
                inputTime:this.input_time
            }
            axios.post("http://localhost:8181/save/findByStypeAndInputTime", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    console.log(response)
                    this.tableData=response.data;
                })
        }else if(this.choice==6){
            // 成功
            let data={
                token:Cookies.get('token'),
                stype:this.value,
                outputTime:this.output_time
            }
            axios.post("http://localhost:8181/save/findByStypeAndOutputTime", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    console.log(response)
                    this.tableData=response.data;
                })
        }else if(this.choice==7){
            // 成功
            let data={
                token:Cookies.get('token'),
                stype:this.value,
                suid:this.suid
            }
            axios.post("http://localhost:8181/save/findByStypeAndSuid", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    console.log(response)
                    this.tableData=response.data;
                })
        }else if(this.choice==8){
            // 成功
            let data={
                token:Cookies.get('token'),
                sid:this.sid,
                inputTime:this.input_time
            }
            axios.post("http://localhost:8181/save/findBySidAndInputTime", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    console.log(response)
                    this.tableData=response.data;
                })
        }else if(this.choice==9){
            // 成功
            let data={
                token:Cookies.get('token'),
                sid:this.sid,
                outputTime:this.output_time
            }
            axios.post("http://localhost:8181/save/findBySidAndOutputTime", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    console.log(response)
                    this.tableData=response.data;
                })
        }else if(this.choice==10){
            // 成功
            let data={
                token:Cookies.get('token'),
                sid:this.sid,
                suid:this.suid
            }
            axios.post("http://localhost:8181/save/findBySidAndSuid", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    console.log(response)
                    this.tableData=response.data;
                })
        }else if(this.choice==11){
            // 成功
            let data={
                token:Cookies.get('token'),
                suid:this.suid
            }
            axios.post("http://localhost:8181/save/findBySuid", data,{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    console.log(response)
                    this.tableData=response.data;
                })
        }else if(this.choice==0){
            axios.get('http://localhost:8181/save/findAll',{
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            })
                .then(response => {
                    this.tableData=response.data;
                })
                .catch(error => {
                    console.log(error)
                })
        }
    }
  },
  mounted() {
  }
}
</script>

<style scoped>
.outdiv {
  min-width: 1000px;
  overflow: hidden;
}

.titleClass {
  position: fixed;
  z-index: 1;
  margin-bottom: 2rem;
}

.titleinput input {
  -web-kit-appearance: none;
  -moz-appearance: none;
  height: 40px;
  font-size: 16px;
  width: 80%;
  border: 1px solid #c0c4cc;
  outline: 0;
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
  height: 100%;
  overflow-y: auto;
  padding: 1.5rem 1.5rem 10rem;
}

</style>
