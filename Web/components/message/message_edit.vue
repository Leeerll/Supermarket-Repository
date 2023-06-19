<template>
    <div style="padding-left: 20px; padding-right: 20px; padding-bottom: 20px">
        <template v-for="(item, index) in dataList">
            <div :key="'title' + index" style="padding: 8px; display: flex; justify-content: space-between; align-items: center">
                <div style="color: #258fe2">
                    {{index + 1}}、{{item.content}} <span v-if="item.type === 3" style="color: gray; font-size: 0.6rem">(多选)</span>
                </div>
                <div v-if="!isView" style="display: flex; flex-direction: row-reverse">
                    <el-button size="mini" style="margin-left: 20px" type="danger" @click="delQuestion(index)">删除</el-button>
                    <el-button size="mini" @click="editQuestion(index)">编辑</el-button>
                </div>
            </div>
            <div v-if="item.type === 1" :key="'answer' + index">
                <el-input
                    type="textarea"
                    :rows="2"
                    disabled="true"
                    placeholder="待用户输入内容">
                </el-input>
            </div>
            <div v-if="item.type === 2" :key="'radio'+index">
                <el-radio-group>
                    <el-radio v-for="(choice, index2) in item.options"
                              class="choiceClass"
                              :key="'radio'+index+index2"
                              :label="index2"
                              disabled="true">{{choice.content}}</el-radio>
                </el-radio-group>
            </div>
            <div v-if="item.type === 3" :key="'checkbox'+index">
                <el-checkbox-group>
                    <el-checkbox v-for="(choice, index2) in item.options"
                                 class="choiceClass"
                                 :key="'checkbox'+index+index2"
                                 :label="index2"
                                 disabled="true">{{choice.content}}</el-checkbox>
                </el-checkbox-group>
            </div>
        </template>

        <div v-if="!isView" style="width: 100%; display: flex; justify-content: center; padding-top: 30px">
            <el-button type="primary" round style="width: 120px" size="mini" @click="addQuestion">添加字段</el-button>
        </div>

        <el-dialog
            title="添加字段"
            v-if="dialogVisible && !isView"
            :visible.sync="dialogVisible"
            width="60%"
            :modal="dialogModal">
            <div class="dialogMsgClass">
                <p>字段名称:</p>
                <el-input v-model="dialogMsg.content" placeholder="字段名称" style="max-width: 80%"></el-input>
            </div>
            <div class="dialogMsgClass">
                <p>字段类型:</p>
                <el-radio-group v-model="dialogMsg.type">
                    <el-radio :label="1">填空型</el-radio>
                    <el-radio :label="2">单选型</el-radio>
                    <el-radio :label="3">多选型</el-radio>
                </el-radio-group>
            </div>
            <div class="dialogMsgClass" v-show="dialogMsg.type === 2 || dialogMsg.type === 3" >
                <p>字段类型:</p>
                <el-button v-show="dialogMsg.type === 2 || dialogMsg.type === 3" size="mini"
                           @click="dialogMsg.options.push({order: dialogMsg.options.length, content: ''})">添加选项</el-button>
                <el-button v-show="dialogMsg.type === 2 || dialogMsg.type === 3" type="danger" size="mini" @click="dialogMsg.options.pop('')">删除选项</el-button>
            </div>
            <div class="dialogMsgClass" v-if="dialogMsg.type === 2">
                <el-radio-group v-model="dialogMsg.type">
                    <el-radio v-for="(item , index) in dialogMsg.options" disabled  :key="'radio2'+index"><el-input v-model="dialogMsg.options[index].content" size="mini" placeholder="选项内容"></el-input></el-radio>
                </el-radio-group>
            </div>
            <div class="dialogMsgClass" v-if="dialogMsg.type === 3">
                <el-checkbox-group v-model="dialogMsg.type">
                    <el-checkbox v-for="(item , index) in dialogMsg.options" disabled  :key="'checkbox2'+index"><el-input v-model="dialogMsg.options[index].content" size="mini" placeholder="选项内容"></el-input></el-checkbox>
                </el-checkbox-group>
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button v-show="dialogMsg.type === 1 || ( ( dialogMsg.type === 2 || dialogMsg.type === 3) && dialogMsg.options.length > 0 )" type="primary" @click="addQuestionok">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "dataEdit",
        props:{
            /**
             * false： 填写
             * true： 查看
             */
            isView:{
                type: Boolean,
                default: false,
            },

        },
        data () {
            return {
                /**
                 *  1: 填空题
                 *  2: 单选题
                 *  3: 多选题
                 */
                dataList:[
                    {
                        type: 1,
                        content:'【示例】填空题目一',
                        options: [],
                    },
                    {
                        type: 2,
                        content:'【示例】单选题目二',
                        options:[
                            {
                                order: 1,
                                content:'选项一',
                            },
                            {
                                order: 2,
                                content:'选项二',
                            },
                            {
                                order: 3,
                                content:'选项三',
                            },
                        ]
                    },
                    {
                        type: 3,
                        content:'【示例】多选题目三',
                        options:[
                            {
                                order: 1,
                                content:'选项一',
                            },
                            {
                                order: 2,
                                content:'选项二',
                            },
                            {
                                order: 3,
                                content:'选项三',
                            },
                        ]
                    }
                ],

                dialogModal: false,
                dialogVisible: false,
                // 悬浮框中的数据
                dialogMsg:{
                    // 悬浮框显示的问题索引，-1为新建问题
                    selectIndex: -1,
                    type: -1,
                    content: '',
                    options: [],
                },
            }
        },

        methods:{
            // 添加问题
            addQuestion(){
                this.dialogMsg.selectIndex = -1;
                this.dialogMsg.type = -1;
                this.dialogMsg.content = '';
                this.dialogMsg.options = [];

                this.dialogVisible = true;
            },
            // 编辑完问题
            addQuestionok(){
                if(this.dialogMsg.selectIndex === -1){
                    this.dataList.push({
                        type: this.dialogMsg.type,
                        content: this.dialogMsg.content,
                        options: this.dialogMsg.options,
                    })
                } else {
                    this.dataList[this.dialogMsg.selectIndex].type = this.dialogMsg.type;
                    this.dataList[this.dialogMsg.selectIndex].content = this.dialogMsg.content;
                    this.dataList[this.dialogMsg.selectIndex].options = this.dialogMsg.options;
                }

                this.dialogVisible = false;
            },
            // 编辑问题
            editQuestion(index) {
                this.dialogMsg.selectIndex = index;
                this.dialogMsg.type = this.dataList[index].type;
                this.dialogMsg.content = this.dataList[index].content;
                this.dialogMsg.options = this.dataList[index].options;

                this.dialogVisible = true;
            },
            // 删除问题
            delQuestion(index) {

                this.$confirm('确定要删除吗', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.dataList.splice(index, 1);
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            // 输出数据的接口
            getData(){
                return this.dataList;
            },

            setData(data){
                this.dataList = data;
            },
        },
    }
</script>

<style scoped>
    .choiceClass {
        min-width: 10rem;
    }
    .el-radio + .el-radio {
        margin-left: 0;
    }
    .el-checkbox + .el-checkbox {
        margin-left: 0;
    }
    .dialogMsgClass {
        display: flex;
        align-items: center;
        padding-bottom: 1rem;
    }
    .dialogMsgClass p {
        color: #258fe2;
        margin-right: 1rem;
        font-size: 1.1rem;
    }
</style>