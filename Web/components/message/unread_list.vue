<template>
    <div>
        <div  style="display: table; width: 100%; padding-left: 10px; padding-top: 20px; padding-bottom:15px; padding-right: 10px">
            <div style="display: table-row">
                <div class="tableTitle" style="width: 30px">
                    <input type="checkbox" :key="'checkallpro'" :id="'checkallpro'" @change="changeAllChecked" v-model="checkall" style="cursor:pointer">
                </div>
                <div class="tableTitle" style="width: 40px">
                    序号
                </div>
                <div class="tableTitle">
                    姓名
                </div>
                <div class="tableTitle">
                    工号
                </div>
                <div class="tableTitle">
                    单位
                </div>
                <div class="tableTitle">
                    职务
                </div>
                <div class="tableTitle">
                    职称
                </div>
                <div v-if="showType" class="tableTitle">
                    审批状态
                </div>
<!--                <div v-if="showType" class="tableTitle">-->
<!--                    操作-->
<!--                </div>-->
            </div>
            <div :class="{'tablerow':true, 'grayLine':
            (index % 2 === 0)}" style="display: table-row;" v-if="dataList.length > 0" v-for="(item,index) in dataList" :key="item.id">
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
                <div class="tableCell" v-if="item.review_status === -1">
                    审核未通过
                </div>
                <div class="tableCell" v-if="item.review_status === 0">
                    未审核
                </div>
                <div class="tableCell" v-if="item.review_status === 1">
                    审核通过
                </div>
<!--                <div v-if="showType" class="tableCell">-->
<!--                    <el-button size="mini">详情</el-button>-->
<!--                </div>-->

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

            <div style="display: flex; flex-direction: row-reverse">
                <el-button v-if="showType" class="bottomButton" size="mini" type="primary" @click="downloadBatch">批量下载</el-button>
                <el-button v-if="showType" class="bottomButton" size="mini" type="primary" @click="downloadMessage">信息导出</el-button>
                <el-button v-if="!showType" class="bottomButton" size="mini" type="primary" @click="sendEmail">邮件提醒</el-button>
                <el-button v-if="!showType" class="bottomButton" size="mini" type="primary" @click="sendMessage">短信提醒</el-button>
                <el-button v-if="showType" class="bottomButton" size="mini" type="primary" @click="getcheckAnswer(-1)">不通过</el-button>
                <el-button v-if="showType" class="bottomButton" size="mini" type="primary" @click="getcheckAnswer(1)">通过</el-button>
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
        props:{
            /**
             *  true： 已读列表
             *  false：未读列表
             * */
            showType:{
                type:Boolean,
            }
        },

        data(){
            return{
                dataList:{},
                inMain:false,
                total:0,
                currentPage:1,
                pageSize:30,
                token:Cookies.get('token'),
                surveyID: parseInt($nuxt.$route.path.split('/')[2]),
                checkall:false,
                checkedids:[],
            }
        },

        mounted:function () {
            if(this.showType) {
                this.getFinishedList()
            }else {
                this.getUnfinishedList()
            }
        },

        methods:{
            currentChange(val){
                this.currentPage = val;
            },
            /**
             * 获取完成名单
             * */
            getFinishedList() {
                this.inMain = this.$route.path === '/main/admin';
                let data = {
                    token:this.token,
                    page:this.currentPage,
                    size:this.pageSize,
                    survey_id:this.surveyID,
                    // condition: {}
                };

                API.FinishedList(data).then(res => {
                    if(res.code){
                        alert(res.message);
                        return;
                    }

                    res.list.forEach(item => {
                        item.create_time = item.userInfo.create_time;
                        item.email = item.userInfo.email;
                        item.entry_time = item.userInfo.entry_time;
                        // item.id = item.userInfo.id;
                        item.job = item.userInfo.job;
                        item.job_number = item.userInfo.job_number;
                        item.name = item.userInfo.name;
                        item.phone = item.userInfo.phone;
                        item.positionInfo = item.userInfo.positionInfo;
                        item.jobtitleInfo = item.userInfo.jobtitleInfo;
                        item.position_id = item.userInfo.position_id;
                        item.status = item.userInfo.status;
                        item.unit_id = item.userInfo.unit_id;
                        item.update_time = item.userInfo.update_time;
                        // item.user_id = item.userInfo.user_id;
                        item.unitInfo = item.userInfo
                    });
                    this.dataList = res.list;

                }).catch(msg => {
                    alert('操作失败')
                })
            },

            /**
             * 获取未完成名单
             * */
            getUnfinishedList() {
                let data = {
                    token:this.token,
                    survey_id:this.surveyID,
                };

                API.UnfinishedList(data).then(res => {
                    if(res.code){
                        alert(res.message);
                        return;
                    }

                    this.dataList = res.list;
                    console.log(this.dataList);

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
                        this.checkedids.push(item.user_id);
                        i++;
                    })
                } else {
                    this.checkedids = []
                }
            },

            /**
             * 是否通过
             */
            getcheckAnswer(result) {
                let data = {
                    token:this.token,
                    survey_id:this.surveyID,
                    is_pass:result,
                    answer_ids:this.checkedids,
                }

                API.checkAnswer(data).then(res => {
                    if(res.code){
                        alert(res.message);
                        return;
                    }

                    if (res.status) {
                        this.getFinishedList();
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
                console.log(data);
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
    .checkboxClass{

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
