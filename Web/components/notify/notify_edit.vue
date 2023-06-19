<template>

    <div style="display: block;padding-left: 30px;padding-right: 30px;width: 100%" v-if="isshow">

        <div style="margin-top: 15px;width: 100%;" class="display-row">
            <div class="filelabel row-left"><p>名称:</p></div>
            <div class="titleinput row-right">
                <input v-model="notifytitle" placeholder="请输入标题"/>
            </div>
        </div>

        <div style="margin-top: 15px" class="display-row">
                <div class="filelabel row-left"><p>对象:</p></div>
                <div class="titleinput row-right">
                    <input style="width: 100%" v-model="notifyRange" @click="dialogFormVisible = true"
                           readonly="readonly" placeholder="点击选择发布范围"/>
                </div>
        </div>

        <el-dialog title="发布范围" :visible.sync="dialogFormVisible" width="70%" :modal="dialogModal">
            <div>
                <div style="width: 100%">
                    <el-radio-group v-model="rangeType" @change="selectChange" style="margin-left: 10px">
                        <el-radio :label="1" >单位</el-radio>
                        <el-radio :label="2" >职称</el-radio>
                        <el-radio :label="3" >自定义</el-radio>
                    </el-radio-group>
                </div>
                <div style="display: inline-block;width: 45%;margin-top: 20px">
                    <div style="border: 1px solid #5EBBDA;width: 100%;height: 250px;overflow-y: auto;border-radius: 5px;padding-left: 10px">
                        <div style="display: table-row;">
                            <div class="tableTitle">
<!--                                <input type="checkbox" :key="'checkallpro'" :id="'checkallpro'"-->
<!--                                       @change="changeAllChecked1" v-model="checkall1" style="cursor:pointer">-->
                            </div>
                            <el-button class="tableTitle" @click="getAllPerson">
                                {{tableTitle1}}
                            </el-button>
                        </div>

                        <div class="tablerow" style="display: table-row;cursor: pointer;" v-if="list1.length > 0"
                             v-for="(item) in list1" :key="item.id" @click.stop="getPersonList(item)">
                            <div class="tableCell">
<!--                                <input type="checkbox" :key="index + 'list1'" :value="item.id" v-model="checkedids1"-->
<!--                                       style="cursor:pointer">-->
                            </div>
                            <el-button class="tableCell">
                                {{item.name}}
                            </el-button>
                        </div>

                        <!-- <el-radio-group class="tablerow" style="display: table-row;height: 20px" v-model="typeId" v-for="item in list1" :key="item.id" @change="getPersonList" size="mini">
                            <el-radio :label="item.id">
                                {{item.name}}
                            </el-radio>
                        </el-radio-group>
                        -->
                    </div>
                    <div style="margin-top:10px;border: 1px solid #5EBBDA;width: 100%;height: 250px;overflow-y: auto;border-radius: 5px">
                        <div style="display: table-row">
                            <div class="tableTitle">
                                <input type="checkbox" :key="'checkallpro'" :id="'checkallpro'"
                                       @change="changeAllChecked2" v-model="checkall2" style="cursor:pointer">
                            </div>
                            <div class="tableTitle">
                                姓名
                            </div>
                        </div>
                        <div class="tablerow" style="display: table-row;" v-if="list2.length > 0"
                             v-for="(item, index) in list2" :key="item.id">
                            <div class="tableCell">
                                <input type="checkbox" :key="index + 'list1'" :id="index+ 'list1'" :value="item.id"
                                       v-model="checkedids2" style="cursor:pointer">
                            </div>
                            <div class="tableCell">
                                {{item.name}}
                            </div>
                        </div>
                    </div>
                </div>
                <div style="display: inline-block;overflow: hidden;height: 510px;margin-left: 10px">
                    <div style="margin-top: 220px">
                        <el-button icon="el-icon-arrow-right" circle size="mini" @click="addRange"></el-button>
                    </div>
                    <div style="margin-top: 10px">
                        <el-button icon="el-icon-arrow-left" circle size="mini" @click="removeRange"></el-button>
                    </div>
                </div>
                <div style="display: inline-block;margin-left:10px;border: 1px solid #5EBBDA;width: 45%;height: 510px;overflow-y: auto;border-radius: 5px">
                    <div style="display: table-row">
                        <div class="tableTitle">
                            <input type="checkbox" :key="'checkallpro'" :id="'checkallpro'" @change="changeAllChecked3"
                                   v-model="checkall3" style="cursor:pointer">
                        </div>
                        <div class="tableTitle">
                            姓名
                        </div>
                    </div>
                    <div class="tablerow" style="display: table-row;" v-if="list3.length > 0"
                         v-for="(item, index) in list3" :key="index+'list3'">
                        <div class="tableCell">
                            <input type="checkbox" :key="index + 'list3'" :value="item.id" v-model="checkedids3"
                                   style="cursor:pointer">
                        </div>
                        <div class="tableCell">
                            {{item.name}}
                        </div>
                    </div>
                </div>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="dialogFormVisible = false,freshRange">确 定</el-button>
            </div>
        </el-dialog>


        <div style="margin-top: 15px" class="display-row"  v-if="edittype === 3">
            <div class="filelabel row-left"><p>时间范围:</p></div>
            <div class="titleinput row-right">
                <el-date-picker
                        v-model="timeRange"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="timestamp">
                </el-date-picker>
            </div>

        </div>


        <div style="margin-top: 30px;width:100%">

            <!-- 图片上传组件辅助-->
            <div v-show="false">
                <el-upload
                        class="avatar-uploader"
                        :action="serverUrl"
                        :data=upload
                        :headers="header"
                        :show-file-list="false"
                        :on-success="imguploadSuccess"
                        :on-error="imguploadError"
                        :before-upload="imgbeforeUpload">
                </el-upload>
            </div>

            <!--富文本编辑器组件-->
            <el-row class="display-row" v-loading="quillUpdateImg">
                <div class="filelabel row-left"><p>内容:</p></div>
                <div class="row-right">
                <quill-editor
                        v-model="detailContent"
                        ref="myQuillEditor"
                        :options="editorOption"
                        style="height: 300px;"
                >
                </quill-editor>
                </div>
            </el-row>
        </div>
        <div class="fujian display-row" style="margin-top: 80px;">
            <div class="filelabel row-left"><p>附件:</p></div>
            <div class="row-right">
                <el-table
                        :data="attachment"
                        @row-click="clickrow"
                        :row-style="{cursor:'pointer'}"
                        style="width: 100%">
                    <el-table-column
                            prop="filename"
                            min-width="80%"
                            label="附件（点击可下载）">
                    </el-table-column>
                    <el-table-column
                            label="操作" min-width="20%" algin="left">
                        <template slot-scope="scope">
                            <el-button @click="handleDelete(scope.$index)" type="text" size="small">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div style="width: 100%;margin-top: 15px">
                    <cli-upload v-on:notifyurl="urlget" v-bind:urltype="notify" style="margin-top: 0"></cli-upload>
                </div>
            </div>

        </div>

        <div class="row-button" v-if="edittype !== 3">
            <el-button type="success" style="width: 100px;margin-right: 30px" v-if="edittype !== 3"  @click="submitInfo" >
                发布
            </el-button>
            <el-button type="success"  style="width: 100px;margin-right: 30px"  v-if="edittype === 1"  @click="saveInfo" >
                暂存
            </el-button>
        </div>
    </div>
</template>

<script>
    import cliUpload from '../base/cliUpload.vue'
    import _ from 'lodash'
    import * as Quill from 'quill'  //引入编辑器
    import {quillEditor} from 'vue-quill-editor'

    import API from '../../api'
    import Cookies from 'js-cookie'
    import PRO from '../../api/API_PRO.js'

    let quill;

    // 工具栏配置
    const toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
        ['blockquote', 'code-block'],

        [{'header': 1}, {'header': 2}],               // custom button values
        [{'list': 'ordered'}, {'list': 'bullet'}],
        [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
        [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
        [{'direction': 'rtl'}],                         // text direction

        [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
        [{'header': [1, 2, 3, 4, 5, 6, false]}],

        [{'color': []}, {'background': []}],
        [{'align': []}],
        ['link', 'image', 'video'],
        ['clean']                                         // remove formatting button
    ]

    export default {
        name: "notify_edit",
        props: {
            /**
             *  3   信息征集
             *  2   修改通知
             *  1   新建通知
             */
            edittype: {
                type: Number //
            },
            infoid: ''//查看详情或者是编辑普通通知时传入的id,如果id是空的话


        },
        components: {
            cliUpload,
        },

        data() {
            return {
                dialogModal: false,
                notifyRange: '',
                dialogFormVisible: false,//通知范围弹出框
                rangeType: 1,//选择的类别 1,学院 2,职务
                unitList: [],//单位列表
                teaPosList: [],//职称列表
                freeList: [],//自定义列表
                checkList: [],
                list1: [],//左上方的列表数据
                checkall1: '',
                checkedids1: [],//左上方选中的
                tableTitle1: '学院列表',
                typeId: '',//左上方所选的学院或者职务类别
                list2: [],//左下方的数据
                checkedids2: [],//左下方选中的
                checkall2: '',
                list3: [],//右的数据
                checkedids3: [],//右边选中的
                checkall3: '',
                loading:false,


                notify: 'notify',
                token: Cookies.get('token'),
                quillUpdateImg: false, // 根据图片上传状态来确定是否显示loading动画，刚开始是false,不显示
                upload: {asset: "notify"},
                serverUrl: PRO.baseURL + 'common/upload/file',  // 这里写你要上传的图片服务器地址*/
                header: {token: Cookies.get('token')},  // 有的图片服务器要求请求头需要有token之类的参数，写在这里
                arr:[1, 2, 3, 3, 3, '0', '1', '2', '测试', '重复', '重复', NaN, NaN, false, false],
                timeRange:[],


                isshow: false,
                attachment: [],//该条详情的附件
                notifytitle: '',//标题
                detailContent: '', // 富文本内容
                editorOption: {
                    placeholder: '',
                    theme: 'snow',  // or 'bubble'
                    modules: {
                        toolbar: {
                            container: toolbarOptions,  // 工具栏
                            handlers: {
                                'image': function (value) {
                                    if (value) {
                                        // 触发input框选择图片文件
                                        document.querySelector('.avatar-uploader input').click()
                                    } else {
                                        this.quill.format('image', false);
                                    }
                                }
                            }
                        }
                    }
                },

            }
        },
        mounted: function () {

            let data1 = {
                token: this.token
            }
            API.groupList(data1).then(res=>{
                if(res.code){
                    alert(res.message);
                    return;
                }
                this.freeList = res.list
            })

            API.getUnit(data1).then(res=>{
                if(res.code){
                    alert(res.message);
                    return;
                }
                this.list1 = res.unit_list
                this.unitList = res.unit_list
            })
            API.getJobList(data1).then(res=>{
                if(res.code){
                    alert(res.message);
                    return;
                }
                this.teaPosList = res.jobtitle_list
            })

            if(this.edittype !== 3){

                let data = {
                    type : 1,
                    token : this.token
                }
                if(this.infoid){
                    let data ={
                        token: this.token,
                        query_id : this.infoid
                    }
                    API.readNotify(data).then(res=>{
                        if(res.code){
                            alert(res.message);
                            return;
                        }
                        this.notifytitle = res.title
                        this.detailContent = res.content
                        this.attachment = res.attachment
                        res.range.forEach(item=>{
                            this.list3.push(item.userInfo)
                        })
                        this.freshRange()

                    }).catch(msg => {
                        if(res.code){
                            alert(res.message);
                            return;
                        }
                        alert(msg)
                    })
                }else{
                    API.readTemporary(data).then(res=>{
                        if(res.code){
                            alert(res.message);
                            return;
                        }
                        this.notifytitle = res.title
                        this.detailContent = res.content
                        this.attachment = res.attachment
                        res.range.forEach(item=>{
                            this.list3.push(item.userInfo)
                        })
                        this.addRange()

                    })
                }
                this.isshow = true
            } else {
                this.isshow = true
            }
            this.arr = [...new Set(this.arr)]
            console.log("yyy?")
            console.log(this.arr)



        },
        methods: {
            urlget(notifyurl) {
                console.log(notifyurl)
                this.attachment.push(notifyurl)
            },

            /**
             *  信息征集部分调用以获取数据
             * */
            getData(){
                return {
                    timeRange: this.timeRange,
                    selectId: this.list3.map(d => d.user_id),
                    attachment: this.attachment.map(d => d.url),
                    title: this.notifytitle,
                    content: this.detailContent,
                }
            },

            setZhengjiData(data){
                this.timeRange = [data.start_timestamp, data.stop_timestamp];
                this.list3 = data.surveyRange.map(d => d.userInfo);
                this.list3.forEach(item => {
                    this.notifyRange = this.notifyRange + item.name + ' '
                })
                this.notifyRange = this.notifyRange + '等人'
                this.attachment = data.attachment;
                this.notifytitle = data.name;
                this.detailContent = data.requirement;
            },

            /**
             * 对象类别选择
             * */
            selectChange() {
                if (this.rangeType == 1) {
                    this.checkall1 = false
                    this.list1 = this.unitList
                    this.checkedids1 = []
                    this.checkedids2 = []
                    this.typeId = ''
                    this.tableTitle1 = '学院列表'
                } else if(this.rangeType ==2){
                    this.checkall1 = false
                    this.list1 = this.teaPosList
                    this.checkedids1 = []
                    this.checkedids2 = []
                    this.typeId = ''
                    this.tableTitle1 = '职称列表'
                }else{
                    this.checkall1 = false
                    this.list1 = this.freeList
                    this.checkedids1 = []
                    this.checkedids2 = []
                    this.typeId = ''
                    this.tableTitle1 = '全部'
                }
            },
            /**
             * 获得所有人员
             * */
            getAllPerson(){
                this.checkedids2 = []
                let data = {
                    token: Cookies.get('token'),
                    size:9999
                }
                API.Request_schema(data).then(res =>{
                    this.list2 = res.list;
                    return res
                }).catch(msg => {
                    alert('加载学院列表失败!')
                })
            },

            /**
             * 我太难了，数组去重
             * */

            unique(arr) {
                const res = new Map();
                return arr.filter((arr) => !res.has(arr.user_id) && res.set(arr.user_id, 1))
            },
            /**
             * 根据选择的类型 请求该学院或者该职务下的所有人员
             * */
            getPersonList(item) {
                this.checkedids2 = []
                let chooseId
                chooseId = item.id
                if (this.rangeType == 1){
                    let data ={
                        token:Cookies.get('token'),
                        condition:{'unit_id':chooseId},
                        size:9999
                    }
                    API.Request_schema(data).then(res => {
                        this.list2 = res.list;
                        return res
                    }).catch(msg => {
                        alert('加载单位列表失败!')
                    })
                } else if (this.rangeType == 2){
                    let data ={
                        token:Cookies.get('token'),
                        condition:{'job':chooseId},
                        size:9999
                    }
                    API.Request_schema(data).then(res => {
                        this.list2 = res.list;
                        return res
                    }).catch(msg => {
                        alert('加载职务列表失败!')
                    })
                }else if (this.rangeType == 3){
                    let data ={
                        token:Cookies.get('token'),
                        group_id:chooseId
                    }
                    API.peopleList(data).then(res => {
                        this.list2 = res.info.range;
                        return res.list
                    }).catch(msg => {
                        alert('加载自定义列表失败!')
                    })
                }

            },
            /**
             * 实现全选
             * */
            changeAllChecked1() {

                if (this.checkall1 === true) {

                    this.checkedids1 = []
                    this.list1.forEach(item => {
                        this.checkedids1.push(item.id)
                    })
                    console.log('sss')
                } else {
                    this.checkedids1 = []
                }
                this.getAllPerson()
            },
            /**
             * 实现全选
             * */
            changeAllChecked2() {
                if (this.checkall2 === true) {

                    this.checkedids2 = []
                    this.list2.forEach(item => {
                        this.checkedids2.push(item.id)
                    })
                } else {
                    this.checkedids2 = []
                }
            },
            /**
             * 实现全选
             * */
            changeAllChecked3() {
                if (this.checkall3 === true) {

                    this.checkedids3 = []
                    this.list3.forEach(item => {
                        this.checkedids3.push(item.id)
                    })
                } else {
                    this.checkedids3 = []
                }
            },
            /**
             * 加入到右边的框中 checkid2加到list3
             * */
            addRange() {
                //一个一个添加
                this.checkedids2.forEach(item => {
                    let result1 = this.list2.findIndex(item2 => item2.id === item)
                    let result2 = this.list3.findIndex(item3 => item3.id === item)
                    console.log(result2)
                    if (result1 >= 0 && result2 < 0) {
                        console.log(result1)
                        this.list3.push(this.list2[result1])
                    }
                })

                this.checkedids2 = []
                // if (this.rangeType == 1 && this.checkedids1.length > 0) {
                //             let data = {
                //                 token: Cookies.get('token'),
                //                 condition: {'unit_id': this.checkedids1}
                //             }
                //             API.Request_schema(data).then(res => {
                //                 res.list.forEach(item => {
                //                     let result3 = this.list3.findIndex(item3 => item3.id === item.id)
                //                     if (result3 < 0) {
                //                         this.list3.push(item)
                //                     }
                //                 })
                //                 this.freshRange()
                //                 return res
                //             }).catch(msg => {
                //                 console.log(msg)
                //                 alert('加载单位列表失败!')
                //             })
                //
                //
                // }else if(this.rangeType == 2 && this.checkedids1.length > 0) {
                //             let data = {
                //                 token: Cookies.get('token'),
                //                 condition: {'job': this.checkedids1}
                //             }
                //             API.Request_schema(data).then(res => {
                //                 res.list.forEach(item => {
                //                     let result3 = this.list3.findIndex(item3 => item3.id === item.id)
                //                     if (result3 < 0) {
                //                         this.list3.push(item)
                //                     }
                //                 })
                //                 this.freshRange()
                //                 return res
                //             }).catch(msg => {
                //                 console.log(msg)
                //                 alert('加载学院列表失败!')
                //             })
                //
                // }else if(this.rangeType == 3 && this.checkedids1.length > 0) {
                //     for (let i=0;i<this.checkedids1.length;i++) {
                //         {
                //             let data = {
                //                 token: Cookies.get('token'),
                //                 group_id:this.checkedids1[i],
                //             }
                //             API.peopleList(data).then(res => {
                //                 res.info.range.forEach(item => {
                //                     let result3 = this.list3.findIndex(item3 => item3.id === item.id)
                //                     if (result3 < 0) {
                //                         this.list3.push(item)
                //                     }
                //                 })
                //                 this.freshRange()
                //                 return res
                //             }).catch(msg => {
                //                 console.log(msg)
                //                 alert('加载学院列表失败!')
                //             })
                //         }
                //     }
                // }

                //显示行。。用户1。等人的方法

                this.checkedids1 = []
                this.list3 = this.unique(this.list3)
                this.freshRange()
                console.log("fuckit")
                console.log(this.list3)
            },
            /**
             * 移除右边框中的数据list3移除checkid3
             * */
            removeRange() {
                this.checkedids3.forEach(item => {
                    let result = this.list3.findIndex(item2 => item2.id === item)
                    if (result >= 0) {
                        this.list3.splice(result, 1)
                    }
                })
                if (this.list3.length > 0) {
                    if (this.list3.length >= 4) {
                        this.notifyRange = this.list3[0].name + ',' + this.list3[1].name + ',' + this.list3[2].name + ',' + this.list3[3].name + '等人'
                    } else {
                        this.notifyRange = ''
                        this.list3.forEach(item => {
                            this.notifyRange = this.notifyRange + item.name + ' '
                        })
                        this.notifyRange = this.notifyRange + '等人'
                    }

                } else {
                    this.notifyRange = ''
                }
                this.checkedids3 = []
            },

            /**
             * 更新range
             * */
            freshRange(){
                if (this.list3.length > 0) {
                    if (this.list3.length >= 4) {
                        this.notifyRange = this.list3[0].name + ',' + this.list3[1].name + ',' + this.list3[2].name + ',' + this.list3[3].name + '等人'
                    } else {
                        this.notifyRange = ''
                        this.list3.forEach(item => {
                            this.notifyRange = this.notifyRange + item.name + ' '
                        })
                        this.notifyRange = this.notifyRange + '等人'
                    }

                } else {
                    this.notifyRange = ''
                }
            },

            /**
             * 删除附件
             * */
            handleDelete(index) {
                this.attachment.splice(index, 1)
            },
            /**
             * 点击下载附件
             * */
            clickrow(row, event, column) {
                window.open(row.url)
            },
            /**
             * 修改通知信息
             * */
            edittinfo() {
                let range_ids = []
                this.list3.forEach(item => {
                    range_ids.push(item.id)
                })
                let attachment_id = []
                this.attachment.forEach(item1 => {
                    attachment_id.push(item1.url)
                })
                let data = {
                    token: this.token,
                    notify_id: this.infoid,
                    title: this.notifytitle,
                    content: this.detailContent,
                    attachment: attachment_id,
                    range_ids: range_ids,
                }
                API.notifyUpdate(data).then(res => {
                    if (res.status == true) {
                        alert("发布成功！")
                        this.$router.push({path: `/notify/`})
                    }
                    else
                        alert("发布失败!")

                    return res
                }).catch(msg => {
                    alert('Failed!')
                })
            },
            /**
             * 发布通知信息
             * */
            submitInfo() {
                let range_ids = []
                this.list3.forEach(item => {
                    range_ids.push(item.user_id)
                })
                let attachment_id = []
                this.attachment.forEach(item1 => {
                    attachment_id.push(item1.url)
                })


                if(this.infoid){
                    let data = {
                        query_id:this.infoid,
                        token: this.token,
                        title: this.notifytitle,
                        content: this.detailContent,
                        user_ids: range_ids,
                    }

                    API.updateCommon(data).then(res => {
                        if (res == true) {
                            alert("发布成功！")
                            this.$router.push({path: `/notify/`})
                        }
                        else
                            alert("发布失败!")

                        return res
                    }).catch(msg => {
                        alert('Failed!')
                    })


                }
                else {
                    let data = {
                        is_temporary: 0,
                        token: this.token,
                        title: this.notifytitle,
                        content: this.detailContent,
                        attachment_urls: attachment_id,
                        user_ids: range_ids,
                    }
                    API.putCommon(data).then(res => {
                        if (res == true) {
                            alert("发布成功！")
                            this.$router.push({path: `/notify/`})
                        }
                        else
                            alert("发布失败!")

                        return res
                    }).catch(msg => {
                        alert('Failed!')
                    })
                }

            },
            saveInfo(){
                let range_ids = []
                this.list3.forEach(item => {
                    range_ids.push(item.user_id)
                })
                let attachment_id = []
                this.attachment.forEach(item1 => {
                    attachment_id.push(item1.url)
                })
                let data = {
                    is_temporary: 1,
                    token: this.token,
                    title: this.notifytitle,
                    content: this.detailContent,
                    attachment_urls: attachment_id,
                    user_ids: range_ids
                }
                API.putCommon(data).then(res=>{
                    if (res == true) {
                        alert("暂存成功！")
                        this.$router.push({path: `/notify/`})
                    }
                    else
                        alert("暂存失败!")

                    return res
                }).catch(msg => {
                    alert('Failed!')
                })
            },

            /**
             * 请求详情信息
             * */
            getinf(notifyid) {
                let data = {
                    token: this.token,
                    notify_id: notifyid,
                }
                API.notifyRead(data).then(res => {
                    this.notifytitle = res.info.title;
                    this.detailContent = res.info.content
                    this.attachment = res.info.attachment;
                    this.list3 = res.info.range
                    if (this.list3.length > 0) {
                        if (this.list3.length >= 4) {
                            this.notifyRange = this.list3[0].name + ',' + this.list3[1].name + ',' + this.list3[2].name + ',' + this.list3[3].name + '等人'
                        } else {
                            this.notifyRange = ''
                            this.list3.forEach(item => {
                                this.notifyRange = this.notifyRange + item.name + ' '
                            })
                            this.notifyRange = this.notifyRange + '等人'
                        }

                    } else {
                        this.notifyRange = ''
                    }

                    return res
                }).catch(msg => {
                    alert('请求失败，请稍后再试!')
                })

            },
            // 富文本图片上传前
            imgbeforeUpload() {
                // 显示loading动画
                this.quillUpdateImg = true
            },
            /**
             * 图片上传
             * */
            imguploadSuccess(res, file) {
                // res为图片服务器返回的数据
                // 获取富文本组件实例
                let quill = this.$refs.myQuillEditor.quill
                // 如果上传成功
                console.log('成功 code' + res.code)
                console.log('成功 ' + res.data.url)
                if (res.code === 0 && res.data !== null) {
                    // 获取光标所在位置
                    let length = quill.getSelection().paperClaim;
                    // 插入图片  res.info为服务器返回的图片地址
                    quill.insertEmbed(length, 'image', res.data.url)
                    // 调整光标到最后
                    quill.setSelection(length + 1)
                } else {
                    this.$message.error('图片插入失败')
                }
                // loading动画消失
                this.quillUpdateImg = false
            },

            // 富文本图片上传失败
            imguploadError() {
                // loading动画消失
                this.quillUpdateImg = false
                this.$message.error('图片插入失败')
            }
        },
    }
</script>

<style scoped>
    .div_inline {
        display: inline
    }

    .margin_top {
        margin-top: 20px;
    }

    .display-row {
        display: -webkit-flex; /* Safari */
        -webkit-justify-content: flex-start; /* Safari 6.1+ */
        display: flex;
        justify-content: flex-start;
        width: 100%;
    }

    .row-left {
        width: 8%;
    }

    .row-right{
        width: 92%;
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
        width: 100%;
        border: 1px solid #c0c4cc;
        outline: 0;
    }

    .titleinput p {

        line-height: 30px;
        font-size: 16px;
        border: 1px solid #c0c4cc;
        background: #ffffff;
        outline: 0;
    }

    .filelabel {
        height: 30px;
        min-width: 100px
    }

    .filelabel p {
        line-height: 30px;
        max-lines: 1;
        font-size: 16px;
        color: rgba(59, 118, 244, 0.82);
        text-align: left;
    }

    .tableTitle {
        display: table-cell;
        font-size: 14px;
        padding: 5px;
        font-family: 黑体;
        font-weight: bold;

    }

    .tableCell {
        display: table-cell;
        font-size: 13px;
        padding: 2px;
        font-family: 黑体;
        font-weight: bold;
    }

    .row-button {
        margin-top: 30px;
        display: -webkit-flex; /* Safari */
        -webkit-justify-content: flex-end; /* Safari 6.1+ */
        display: flex;
        justify-content: flex-end;
        width: 100%;
        margin-bottom: 20px;
    }
</style>
