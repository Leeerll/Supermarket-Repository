<template>
    <div style="padding-left: 10px; padding-right: 10px">
        <div class="titleClass">
            可选字段
        </div>

        <div style="margin-top: 10px;">
            <el-checkbox v-model="fieldShow.showIndex">序号</el-checkbox>
            <el-checkbox v-model="fieldShow.showName">姓名</el-checkbox>
            <el-checkbox v-model="fieldShow.showJobNumber">工号</el-checkbox>
            <el-checkbox v-model="fieldShow.showUnit">单位</el-checkbox>
            <el-checkbox v-model="fieldShow.showZhiWu">职务</el-checkbox>
            <el-checkbox v-model="fieldShow.showZhiChent">职称</el-checkbox>
        </div>

        <div class="titleClass" style="margin-top: 5px">
            征集字段  <el-checkbox @change="selectAll">全选</el-checkbox>
        </div>

        <div style="margin-top: 10px;">
            <el-checkbox v-for="(item) in field"
                         class="choiceClass"
                         :key="item.id"
                         v-model="item.isShow">{{item.content}}</el-checkbox>
        </div>

        <div  style="display: table; width: 100%; padding-top: 20px; padding-bottom:15px;">
            <div style="display: table-row">
                <div class="tableTitle" style="width: 30px">
                    <input type="checkbox" :key="'checkallpro'" :id="'checkallpro'" @change="changeAllChecked" v-model="checkall" style="cursor:pointer">
                </div>
                <div v-show="fieldShow.showIndex" class="tableTitle" style="min-width: 40px">
                    序号
                </div>
                <div v-show="fieldShow.showName" class="tableTitle" style="min-width: 60px">
                    姓名
                </div>
                <div v-show="fieldShow.showJobNumber" class="tableTitle">
                    工号
                </div>
                <div v-show="fieldShow.showUnit" class="tableTitle" style="min-width: 40px">
                    单位
                </div>
                <div v-show="fieldShow.showZhiWu" class="tableTitle" style="min-width: 40px">
                    职务
                </div>
                <div v-show="fieldShow.showZhiChent" class="tableTitle" style="min-width: 40px">
                    职称
                </div>

                <template v-for="item in field">
                    <div v-show="item.isShow" class="tableTitle" :key="item.id" style="min-width: 10rem;">
                        {{getContent(item.content)}}
                    </div>
                </template>

                <div class="tableTitle"  style="min-width: 5rem;">
                    审批状态
                </div>
                <div class="tableTitle"  style="min-width: 10rem;">
                    备注
                </div>
            </div>
            <div :class="{'tablerow':true, 'grayLine':
            (index % 2 === 0)}" style="display: table-row;" v-if="dataList.length > 0" v-for="(item,index) in dataList" :key="item.id">
                <div class="tableCell">
                    <input class="checkboxClass" type="checkbox" :key="index + 'pro'" :id="index+ 'pro'" :value="item" v-model="checkedids" style="cursor:pointer">
                </div>
                <div v-show="fieldShow.showIndex" class="tableCell">
                    {{index + 1}}
                </div>
                <div v-show="fieldShow.showName" class="tableCell">
                    {{item.userInfo.name}}
                </div>
                <div v-show="fieldShow.showJobNumber" class="tableCell">
                    {{item.userInfo.job_number}}
                </div>
                <div v-show="fieldShow.showUnit" class="tableCell">
                    {{item.userInfo.unitInfo.name}}
                </div>
                <div v-show="fieldShow.showZhiWu" class="tableCell">
                    {{item.userInfo.positionInfo.name}}
                </div>
                <div v-show="fieldShow.showZhiChent" class="tableCell">
                    {{item.userInfo.jobtitleInfo.name}}
                </div>

                <template v-for="(res, index2) in item.answer">
                    <div v-show="field[index2].isShow" class="tableTitle" :key="index2">
                        {{getContent(res)}}
                    </div>
                </template>

                <div class="tableCell" v-if="item.review_status === -1">
                    审核未通过
                </div>
                <div class="tableCell" v-if="item.review_status === 0">
                    未审核
                </div>
                <div class="tableCell" v-if="item.review_status === 1">
                    审核通过
                </div>
                <div class="tableCell" style="width: 150px">
                    <el-input v-model="item.beizhu" size="mini" v-if="item.review_status === 0"></el-input>
                    <p v-if="item.review_status !== 0">{{item.beizhu}}</p>
                </div>
            </div>
        </div>

        <div v-if="!inMain" class="pagePosition">
            <el-pagination
                    @current-change=currentChange
                    :current-page.sync=currentPage
                    :page-size="pageSize"
                    layout="prev, pager, next, jumper"
                    :total=total
                    style="margin-right: 40px">
            </el-pagination>

            <div style="padding-right: 15px">
                <div>
                    <el-button class="bottomButton" size="mini" type="primary" @click="downloadBatch">批量下载</el-button>
                    <el-button class="bottomButton" size="mini" type="primary" @click="downloadMessage">信息导出</el-button>
                    <el-button class="bottomButton" size="mini" type="primary" @click="getcheckAnswer(-1)">不通过</el-button>
                    <el-button class="bottomButton" size="mini" type="primary" @click="getcheckAnswer(1)">通过</el-button>
                </div>
            </div>
        </div>

    </div>

</template>

<script>
    import Cookies from 'js-cookie'
    import API from '../../api'
    import PRO from '~/api/API_PRO'

    export default {
        name: "read_list",

        data(){
            return{
                field: [],
                fieldShow: {
                    showIndex: true,
                    showName: true,
                    showJobNumber: false,
                    showUnit: false,
                    showZhiWu: false,
                    showZhiChent: false,
                },

                dataList:{},
                inMain:false,
                total:0,
                pageSize:30,
                currentPage:1,
                jobtitleInfo:{},
                token:Cookies.get('token'),
                surveyID: parseInt($nuxt.$route.path.split('/')[2]),
                checkall:false,
                checkedids:[],
                //beizhu:'',
            }
        },

        mounted:function () {
            this.getFinishedList(false);
        },

        methods:{
            currentChange(val){
                this.currentPage = val;
            },

            selectAll(isSelect) {
                this.field.forEach((item, i) => {
                    item.isShow = isSelect;
                })
            },

            getContent(content) {
                let result = content;
                if (content.length > 10) {
                    result = content.substring(0, 8) + '...';
                }
                return result;
            },

            /**
             * 获取完成名单
             * */
            getFinishedList(isFlesh) {
                this.inMain = this.$route.path === '/main/admin';
                let data = {
                    token:this.token,
                    page:this.currentPage,
                    size:this.pageSize,
                    survey_id:this.surveyID,
                    // condition: {}
                };

                API.adminReadList(data).then(res => {
                    console.log(res);
                    if(res.code){
                        alert(res.message);
                        return;
                    }
                    this.dataList = res.list;

                    //field数组中加一个属性
                    if (!isFlesh) {
                        res.field.map(((item, index)=> {
                            this.field.push(Object.assign({},item,{isShow:true}))
                        }))
                    }

                }).catch(msg => {
                    alert('操作失败')
                })
            },

            /**
             * 实现全选
             * */
            changeAllChecked() {
                console.log('change')
                if (this.checkall === true) {

                    this.checkedids = []
                    let i =0
                    this.dataList.forEach(item=>{
                        this.checkedids.push(item)
                        i++
                    })
                } else {
                    this.checkedids = []
                }
            },

            /**
             * 是否通过
             */
            getcheckAnswer(result) {
                const answerIds = this.checkedids.map(d => {
                    return {
                        'answer_id': d.id,
                        'beizhu': d.beizhu,
                    }
                })

                let data = {
                    token:this.token,
                    survey_id:this.surveyID,
                    is_pass:result,
                    answer_ids:answerIds,
                }

                API.checkAnswer(data).then(res => {
                    if(res.code){
                        alert(res.message);
                        return;
                    }

                    if (res.status) {
                        this.getFinishedList(true);
                        alert('操作成功');
                        // location.reload();
                    }

                }).catch(msg => {
                    alert('操作失败')
                })
            },

            /**
             * 短信提醒
             */
            sendMessage(){
                let data = {
                    token: this.token,
                    survey_id: this.surveyID,
                    user_ids: this.checkedids,
                };
                API.notifyUnFinishByMessage(data).then(res=>{

                    if(res.code){
                        alert(res.message);
                        return;
                    }
                    alert('短信发送成功！');
                }).catch(msg => {
                    alert('操作失败')
                })
            },

            /**
             * 邮箱提醒
             * */
            sendEmail(){
                let data = {
                    token: this.token,
                    survey_id: this.surveyID,
                    user_ids: this.checkedids,
                };
                API.notifyUnFinishByEmail(data).then(res=>{

                    if(res.code){
                        alert(res.message);
                        return;
                    }
                    alert('邮件发送成功！');
                }).catch(msg => {
                    alert('操作失败')
                })
            },

            /**
             * 信息导出
             */
            downloadMessage () {
                window.open(PRO.baseURL+'survey/answer/export?token='+this.token+'&survey_id='+this.surveyID);
            },

            /**
             * 批量下载
             */
            downloadBatch () {
                window.open(PRO.baseURL+'survey/answer/download-batch?token='+this.token+'&survey_id='+this.surveyID);
            }
        }

    }
</script>

<style scoped>
    .tableTitle{
        display: table-cell;
        text-align: center;
        font-size: 15px;
        padding: 5px;
        color: gray;
    }

    .titleClass {
        color: gray;
    }

    .tableCell{
        display: table-cell;
        text-align: center;
        font-size: 15px;
        padding: 5px;
    }
    .grayLine{
        background-color: #e4efff;
    }

    .el-button + .el-button{
        margin-left: 0;
    }
    .bottomButton{
        margin-right: 10px;
        min-width: 80px;
    }
    .el-checkbox{
        min-width: 100px;
    }

    .el-checkbox + .el-checkbox {
        margin-left: 0;
    }

    .pagePosition{
        display: flex;
        float: right;
        align-items: center;
        justify-content: space-between;
        margin-left: 5%;
        padding-bottom: 15px;
        padding-top: 10px
    }
</style>
