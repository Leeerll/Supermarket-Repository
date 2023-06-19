<template>
  <div>
    <el-table
        ref="filterTable"
        :data="Data.slice(pageSize * (currentPage - 1),pageSize * currentPage)"
        stripe
        style="width: 100%">
      <el-table-column
          label="序号"
          type="index"
          :index="indexMethod"
          sortable
          width="180"
          column-key="index"
          :default-sort = "{prop: 'index', order: 'descending'}">
      </el-table-column>
      <el-table-column
          prop="name"
          label="姓名"
          sortable
          width="180"
          :default-sort = "{prop: 'name', order: 'descending'}">
      </el-table-column>

      <el-table-column
          prop="JobNumber"
          label="工号"
          sortable
          :default-sort = "{prop: 'JobNumber', order: 'descending'}">
      </el-table-column>

      <el-table-column
          align="right"
          prop="college"
          label="二级单位"
          width="150"
      >
        <template slot="header" slot-scope="scope">

          <el-select
              v-model="valuex"
              size="mini"
              style="width: 150px;margin-right: 100px;"
              placeholder="请选择">
            <el-option
                v-for="item in unitList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>

        </template>
      </el-table-column>

      <el-table-column
          prop="college"
          label="二级单位"
          width="150"
          :filters="[{text: '电子信息工程学院', value: '电子信息工程学院'}, {text: '其他', value: '#'}]"
          :filter-method="filterHandler">
      </el-table-column>
      <el-table-column
          prop="unit"
          label="一级单位"
          width="150"
          :filters="[{text: '团委', value: '团委'}, {text: '光波所', value: '光波所'}, {text: '电子电工教学基地', value: '电子电工教学基地'}, {text: '其他', value: '#'}]"
          :filter-method="filterHandler">
      </el-table-column>
      <el-table-column
          prop="log_time"
          label="登录天数"
          sortable
          width="150"
          :default-sort = "{prop: 'log_time', order: 'descending'}">
      </el-table-column>
      <el-table-column
          prop="operate_time"
          label="督办次数"
          sortable
          width="150"
          :default-sort = "{prop: 'operate_time', order: 'descending'}">
      </el-table-column>
      <el-table-column
          prop="score"
          label="积分"
          sortable
          width="100"
          :default-sort = "{prop: 'score', order: 'descending'}">
      </el-table-column>
    </el-table>

    <div class="block">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[1, 2, 3, 4, 5]"
          :page-size="5"
          layout="total, sizes, prev, pager, next, jumper"
          :total="Data.length"
          hide-on-single-page>
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: "my-table",
  methods: {
    // index 自适应序号
    indexMethod(index) {
      if(this.currentPage!==1){
        return index+1 + this.pageSize * (this.currentPage-1)
      }
      else{
        return index + 1;
      }
    },
    // 页数变化触发
    handleSizeChange(pageSize) {
      this.pageSize=pageSize
    },
    // 页码变化触发
    handleCurrentChange(currentPage) {
      this.currentPage=currentPage
    },
    //筛选功能
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    //计算score
    caculate(log,oper) {
      return ((log*0.4+oper*0.6)/(log+oper))
    },
    open() {
      this.$notify({
        title: '警告',
        message: '没有更多了',
        type: 'warning'
      });
    }
  },
  data() {
    return {
      Data: [{
        index: '1',
        name: 'test1',
        JobNumber: '9301',
        college: '电子信息工程学院',
        unit: '团委',
        log_time: 22,
        operate_time: 32,
        score: 11
      },{
        index: '2',
        name: 'test2',
        JobNumber: '9302',
        college: '电子信息工程学院',
        unit: '团委',
        log_time: 56,
        operate_time: 41,
        score: 11
      },{
        index: '3',
        name: 'test3',
        JobNumber: '9303',
        college: '电子信息工程学院',
        unit: '团委',
        log_time: 35,
        operate_time: 23,
        score: 9
      },{
        index: '4',
        name: 'test4',
        JobNumber: '9401',
        college: '电子信息工程学院',
        unit: '光波所',
        log_time: 60,
        operate_time: 19,
        score: 12
      },{
        index: '5',
        name: 'test5',
        JobNumber: '9510',
        college: '软件学院',
        unit: '光波所',
        log_time: 11,
        operate_time: 21,
        score: 8
      },{
        index: '6',
        name: 'test6',
        JobNumber: '9511',
        college: '软件学院',
        unit: '电子电工教学基地',
        log_time: 10,
        operate_time: 25,
        score: 5
      }],
      currentPage: 1,
      pageSize: 5,
      collegeList: [{id: 1, name: '电子信息工程学院'}, {id: 2, name: '软件学院'}],
      unitList: [{id: 1, name: '团委'}, {id: 2, name: '光波所'},{id: 4, name: '电子电工教学基地'}],
      valuex: []
    }
  }
}

</script>

<style scoped>

</style>