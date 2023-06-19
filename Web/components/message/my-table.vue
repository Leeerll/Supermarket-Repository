<template>
  <div class="outdiv">
    <div class="bodyDiv">
      <el-card   shadow="hover" :body-style="{ padding: '20px'} " >
        <div class="display-row">

          <div  class="row-right">
            <div  style="display: table; width: 100%; padding: 0 10px 20px 20px;">
              <div style="display: table-row">
                <div class="tableTitle" style="width: 20px">
                  <input type="checkbox" :key="'checkallpro'" :id="'checkallpro'" @change="changeAllChecked" v-model="checkall" style="cursor:pointer">
                </div>
                <div class="tableTitle" style="width: 40px">
                  序号
                </div>
                <div class="tableTitle" style="width: 90px;">
                  姓名
                </div>
                <div class="tableTitle">
                  工号
                </div>
                <div class="tableTitle" style="width: 300px;">
                  <el-select
                      size="mini"
                      v-model="unitSelected"
                      placeholder="单位"
                      multiple value="string"
                      @change="changeFunc">
                    <el-option
                        v-for="item in unitList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                  </el-select>
                </div>
                <div class="tableTitle" style="width: 200px;">
                  <el-select
                      size="mini"
                      v-model="positionSelected"
                      placeholder="职务"
                      multiple value="string"
                      @change="changeFunc">
                    <el-option
                        v-for="item in positionList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                  </el-select>
                </div>
                <div class="tableTitle" style="width: 100px;">
                  <el-select
                      size="mini"
                      v-model="jobSelected"
                      placeholder="职称"
                      multiple value="string"
                      @change="changeFunc">
                    <el-option
                        v-for="item in jobList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                    </el-option>
                  </el-select>
                </div>
                <div class="tableTitle">
                  入职年份
                </div>
                <div class="tableTitle">
                  邮箱
                </div>
                <div class="tableTitle">
                  手机号
                </div>
              </div>
              <div :class="{'tablerow':true, 'grayLine': (index % 2 === 0)}" style="display: table-row;" v-if="dataList.length > 0" v-for="(item,index) in dataList" :key="item.id">
                <div class="tableCell">
                  <input class="checkboxClass" type="checkbox" :key="index + 'pro'" :id="index+ 'pro'" :value="item.user_id" v-model="checkedids" style="cursor:pointer">
                </div>
                <div class="tableCell">
                  {{index + 1}}
                </div>
                <div class="tableCell">
                  {{item.name}}
                </div>
                <div class="tableCell">
                  {{item.job_number}}
                </div>
                <div class="tableCell">
                  {{item.unitInfo.name}}
                </div>
                <div class="tableCell">
                  {{item.positionInfo.name}}
                </div>
                <div class="tableCell">
                  {{item.jobtitleInfo.name}}
                </div>
                <div class="tableCell">
                  {{item.entry_time}}
                </div>
                <div class="tableCell">
                  {{item.email}}
                </div>
                <div class="tableCell">
                  {{item.phone}}
                </div>
              </div>

            </div>
            <el-pagination style="margin-left: 60px ;float: left"
                           @size-change="handleSizeChange"
                           @current-change="handlePageChange"
                           :current-page="listForm.page"
                           :page-sizes="[20, 30, 40, 50]"
                           :page-size="listForm.size"
                           layout="total, sizes, prev, pager, next, jumper"
                           :total="listForm.total">
            </el-pagination>

            <div style="float:right;margin-right: 10px;float: left;margin-left: 80px;margin-top: 5px">
              <el-button plain v-if="isAll===false" class="bottomButton" size="mini" type="primary" @click="deletDialogVisible = true">删除成员</el-button>
              <el-button plain v-if="isAll" class="bottomButton" size="mini" type="primary" @click="downloadMuBan">模板下载</el-button>
              <el-button plain v-if="isAll" class="bottomButton" size="mini" type="primary" @click="uploadMsg=true">人员导入</el-button>
              <el-button plain v-if="isAll" class="bottomButton" size="mini" type="primary" @click="joindialogVisible=true">加入小组</el-button>
            </div>
          </div>
        </div>

      </el-card>
    </div>

    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <span>该操作将会删除该组别即所有人员，是否继续？</span>
      <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="deGroup">确 定</el-button>
              </span>
    </el-dialog>
    <el-dialog
        title="提示"
        :visible.sync="deletDialogVisible"
        width="30%"
        center>
      <span>该操作将会删除所有所选人员，是否继续？</span>
      <span slot="footer" class="dialog-footer">
                <el-button @click="deletDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="deletPerson">确 定</el-button>
              </span>
    </el-dialog>
    <el-dialog title="可将所勾选人员加入新小组或已有小组"
               :visible.sync="joindialogVisible"
               width="50%"
               center>
      <el-form >
        <el-form-item label="输入新小组或已有小组名称">
          <el-input  v-model="joinGroupName" style="float: left ;width: 50% " ></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="joindialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="joinGroup">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="信息导入"
        :visible.sync="uploadMsg"
        width="50%"
    >
      <cli-upload style="margin-bottom: 40px" v-bind:urltype="person"></cli-upload>
      <span slot="footer" class="dialog-footer">
                    <el-button @click="uploadMsg = false">取 消</el-button>
                    <el-button type="primary" @click="uploadMsg = false">确 定</el-button>
                </span>
    </el-dialog>

  </div>
</template>

<script>
import CliTitle from "../../components/base/cliTitle";
import CliMenu from "../../components/base/cliMenu";
import Position from "../../components/base/position";
import Cookies from 'js-cookie'
import API from '../../api'
import Pro from '../../api/API_PRO'
import CliUpload from "../../components/base/cliUpload";
export default {
  name: "test",
  components: {CliUpload, Position, CliMenu, CliTitle},
  data(){
    return{
      uploadMsg:false,
      isAll: true,//当前群组状态是否在全部
      conditionCount:0,
      dialogVisible : false,
      deletDialogVisible :false,
      centerDialogVisible : false,
      joindialogVisible : false,
      peopleIndex:"1",
      currentGruop:0,
      delectGroup : "" ,
      group:[],
      joinGroupName:"",
      checkall:false,
      pageIndex: "5",
      groupForm:{name:"",user_ids:[]},
      checkedids:[],
      person:'person',
      searchInput: '', // 搜索输入框
      positionSelected: [],    // 已选择的职务
      /**
       * 1 党委副书记
       * 2
       * 3 普通辅导员
       * 4 无
       */
      positionList: [{id: 1, name:　'党委副书记'}, {id: 3,name: '普通辅导员'}, {id: 4,name: '无'}], // 职务列表
      unitSelected: [],   // 已选择的单位
      /**
       * 1 电信学院
       * 2 软件学院
       * 3
       * 4 北交大
       * 5 国重
       */
      unitList: [{id: 1, name: '电信学院'}, {id: 2, name: '软件学院'},{id: 4, name: '北交大'},{id: 5,name: '国重'}],   // 单位列表
      jobSelected: [], // 已选择的职称
      jobList: [{name: '学生'},{name: '讲师'},{name: '教授'},{name: '副教授'},{name: '无'}],    // 职称列表
      dataList:[],
      dataList2:[],
      position: {
        name: '用户管理',
        haveBack: true,

      },
      tableData3:[{index:1,id:"233",name:"蔡徐坤"},
        {index:2,id:"2333",name:"篮球"}
      ],
      listForm:{
        page:1,
        size:20,
        total:0,
        orderby:['id'],
      },
      condition :{
        unit_id: [],
        position_id: [],
        job: []
      },
    }
  },
  methods:{
    getUnitList () {

      let data = {
        token: this.token
      }

      API.getUnitList(data).then(res=>{
        if (res.code) {
          alert(res.message)
        }
        this.unitList = res.unit_list;
      }).catch(msg => {
        alert(msg)
      })


    },

    getJobList() {
      let data = { token: this.token};
      API.getJobList(data).then(res => {
        if (res.code) {
          alert(res.message)
        }
        this.jobList = res.jobtitle_list;
      }).catch(msg => alert(msg))

    },

    getPositionList(){
      let data = {
        token: this.token
      }

      API.getPositionList(data).then(res=>{
        if (res.code) {
          alert(res.message)
        }
        this.positionList = res.position_list;
      }).catch(msg => {
        alert(msg)
      })
    },
    initCondition(){
      this.condition ={
        unit_id: this.unitSelected,
        position_id: this.positionSelected,
        job: this.jobSelected
      };
      this.conditionCount = 3;

      if (this.unitSelected.length===0)
      {
        delete this.condition.unit_id;
        this.conditionCount--;
      }

      if (this.positionSelected.length===0)
      {
        delete this.condition.position_id;
        this.conditionCount--;
      }

      if (this.jobSelected.length===0)
      {
        delete this.condition.job;
        this.conditionCount--;
      }
      console.log('我也太难了'+this.conditionCount)


    },
    /**
     * 当你对单位、职务、职称进行筛选时
     */
    changeFunc () {
      this.initCondition()
      let data = {
        token: this.token,
        condition: this.condition,
        size:20
      };
      if(this.isAll){
        if(this.conditionCount!=0)
          data.size = 999
        API.Request_schema(data).then(res=>{
          if (res.code) {
            alert(res.message)
          }
          this.dataList = res.list
          this.listForm.total = res.sum
          this.listForm.size = 20
          if(this.conditionCount!=0)
            this.listForm.size=res.sum
        }).catch(msg => {
          alert(msg)
        })
      }else{
        this.clickMenu(this.currentGruop)

      }



    },
    querySearch (queryString, cb) {
      console.log(queryString);
      if(this.isAll){
        if (queryString) {
          let condition = {
            name: queryString,
            job_number: queryString
          };

          if (isNaN(parseInt(this.searchInput))) delete condition.job_number;
          else delete condition.name;

          let data = {
            token: this.token,
            condition: condition
          };
          API.Request_schema(data).then(res=>{
            if (res.code) {
              alert(res.message)
            }
            this.dataList2 = res.list;
            clearTimeout(this.timeout);
            this.timeout = setTimeout(() => {
              let result = queryString ? this.createStateFilter(this.dataList2): this.dataList;
              cb(result);
            }, 2000*Math.random())
          }).catch(msg => {
            let result = queryString ? this.createStateFilter(this.dataList2): this.dataList;
            cb(result)
          })
        } else {

        }
      }else{
        if (queryString) {
          let data ={
            token:this.token,
            keyword: queryString,
            group_id:this.currentGruop

          };
          API.searchGroup(data).then(res=>{
            if (res.code) {
              alert(res.message)
            }
            this.dataList2 = res.list;
            clearTimeout(this.timeout);
            this.timeout = setTimeout(() => {
              let result = queryString ? this.createStateFilter(this.dataList2): this.dataList;
              cb(result);
            }, 2000*Math.random())
          }).catch(msg => {
            let result = queryString ? this.createStateFilter(this.dataList2): this.dataList;
            cb(result)
          })


        }else {
        }
      }

    },
    choosePerson () {
      this.dataList = this.dataList2;
    },
    createStateFilter (returnQuesList) {
      let results = [];
      returnQuesList.forEach(item=>{
        results.push(item)
      });
      return results
    },
    changeAllChecked() {
      console.log('change')
      if (this.checkall === true) {

        this.checkedids = []
        let i =0
        this.dataList.forEach(item=>{
          this.checkedids.push(item.user_id)
          i++
        })
      } else {
        this.checkedids = []
      }
    },
    deletPerson(){
      this.deletDialogVisible = false
      let data ={
        token: this.token,
        group_id: this.currentGruop,
        is_add:0,
        user_ids:this.checkedids
      }
      API.updataGroup(data).then(res=>{
        if(res.code){
          alert(res.message);
          return;
        }
        alert('删除人员成功')
        this.clickMenu(this.currentGruop)
      }).catch(msg => {
        if(res.code){
          alert(res.message);
          return;
        }
        alert(msg)
      })

    },
    showAll(){
      this.isAll = true
      this.unitSelected=[]
      this.jobSelected=[]
      this.positionSelected=[]

      this.initCondition()

      let data ={
        token: this.token,
        size:this.listForm.size,
        condition: this.condition
      }

      API.Request_schema(data).then(res=>{
        this.dataList=res.list
        this.listForm.total = res.sum
      })

    },
    downloadMuBan(){
      window.open(Pro.baseURL+'user/information/export-template')

    },
    addNewGroup(){
      this.dialogVisible = false
      console.log(this.groupForm.user_ids)
      let data ={
        name:this.groupForm.name,
        user_ids:this.groupForm.user_ids,
        token:this.token
      }
      API.addGroup(data).then(res=>{
        if(res.code){
          alert(res.message);
          return;
        }

        alert('添加小组成功')

        API.groupList(data).then(res=>{
          this.group=res.list
        })
      }).catch(msg => {
        if(res.code){
          alert(res.message);
          return;
        }
        alert(msg)
      })
      this.groupForm.name=''
      this.groupForm.user_ids=[]
    },
    joinGroup(){
      console.log(this.checkedids)
      let data ={
        name:this.joinGroupName,
        user_ids:this.checkedids,
        token:this.token
      }
      API.addGroup(data).then(res=>{
        if(res.code){
          alert(res.message);
          return;
        }
        alert('添加小组成功')


        API.groupList(data).then(res=>{
          this.group=res.list
        })
      }).catch(msg => {
        if(res.code){
          alert(res.message);
          return;
        }
        alert(msg)
      })
      this.joinGroupName=""
      this.joindialogVisible = false

    },
    clickMenu(key){
      this.isAll = false
      this.initCondition()
      this.currentGruop = key
      let data={
        token:this.token,
        group_id:key,//18
        condition:this.condition,
        size:20
      }
      console.log(this.condition)
      if(this.conditionCount!=0)
        data.size = 999
      API.peopleList(data).then(res=>{
        this.dataList=res.info.range
        this.listForm.total = res.sum
        this.listForm.size = 20
        if(this.conditionCount!=0)
          this.listForm.size=res.sum
      })
    },
    Menuchange(data){
      this.isAll = false
      API.peopleList(data).then(res=>{
        this.dataList=res.info.range
        this.listForm.total = res.sum

      })

    },
    handleSelectionChange(val){
      this.groupForm.user_ids=val
      console.log(val)
    },
    handleSizeChange(val){
      this.listForm.size = val
      if(this.isAll){
        let data={
          token: this.token,
          page: this.listForm.page,
          size: this.listForm.size,

        }
        API.Request_schema(data).then(res=>{
          this.dataList=res.list
          this.listForm.total = res.sum
        })
      }else {
        let data={
          token: this.token,
          page: this.listForm.page,
          size: this.listForm.size,
          group_id:this.currentGruop
        }
        this.Menuchange(data)

      }

    },
    handlePageChange(val){
      this.listForm.page = val
      if(this.isAll){
        let data={
          token: this.token,
          page: this.listForm.page,
          size: this.listForm.size,
        }
        API.Request_schema(data).then(res=>{
          this.dataList=res.list
          this.listForm.total = res.sum
        })
      }else {
        let data={
          token: this.token,
          page: this.listForm.page,
          size: this.listForm.size,
          group_id:this.currentGruop
        }
        this.Menuchange(data)

      }

    },
    deleteGroup(key)
    {
      this.centerDialogVisible = true
      this.delectGroup = key
    },
    deGroup(){
      this.centerDialogVisible = false
      let data={
        token: this.token,
        group_id:this.delectGroup
      }
      API.deleteGroup(data).then(res=>{
        if(res.code){
          alert(res.message);
          return;
        }
        API.groupList(data).then(res=>{
          this.group=res.list
        })
      })
    }

  },
  mounted() {
    this.getPositionList();
    this.getUnitList();
    this.getJobList();

    let data ={
      token: this.token
    }
    API.Request_schema(data).then(res=>{
      this.dataList=res.list
      this.dataList2=res.list
      this.listForm.total=res.sum
    })

    API.groupList(data).then(res=>{
      this.group=res.list
    })
  }
}
</script>

<style scoped>

.elstyle{
  line-height: 40px;
  height: 40px;
}
.outdiv {
  min-width: 1000px;
  overflow: hidden;
}

.display-row {
  display: flex;
  justify-content: flex-start;
  width: 100%;
}

.row-left {
  width: 8%;
}

.row-right {
  width: 92%;
}

.right-title {
  display: flex;
  justify-content: space-around;
  margin: 10px 0 10px 20px;
}

.titleClass {
  position: fixed;
  z-index: 1;
  margin-bottom: 2rem;
}

.titleinput {
  height: 40px;
  margin-left: 5px;
  font-size: 16px;
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
  margin-top: 70px;
  width: 15=90px;
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

.tableTitle {
  display: table-cell;
  font-size: 14px;
  padding: 5px;
  font-family: 黑体;
  font-weight: bold;
  color: gray;

}

.tableCell {
  display: table-cell;
  font-size: 15px;
  padding: 5px;
}
.grayLine{
  background-color: #e4efff;
}
</style>