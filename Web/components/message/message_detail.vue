<template>
    <div style="padding-left: 20px; padding-right: 20px; padding-bottom: 20px;">
        <div v-if="showType === 1" style="width: 100%; text-align: center" >
            <strong style="font-size: 1.2rem">待填字段</strong>
        </div>

<!--      test-->
        <template v-for="(item, index) in dataList">
            <div :key="'title' +index" style="padding: 8px; display: flex; justify-content: space-between; align-items: center">
                <div style="color: #258fe2">
                    {{index + 1}}、{{item.content}}<span v-if="item.type === 3" style="color: gray; font-size: 0.7rem">(多选)</span>
                </div>
            </div>
            <div v-if="item.type === 1" :key="'answer'+ index">
                <el-input
                        type="textarea"
                        v-model="item.answer.content"
                        :rows="2"
                        placeholder="请输入内容"
                        :disabled="isView">
                </el-input>
            </div>
            <div v-if="item.type === 2" :key="'radio'+ index">
                <el-radio-group v-model="item.answer.option_id">
                    <el-radio v-for="(choice, index2) in item.options"
                              class="choiceClass"
                              :key="'radio2'+index+index2"
                              :label="choice.id"
                              :disabled="isView">{{choice.content}}</el-radio>
                </el-radio-group>
            </div>
            <div v-if="item.type === 3" :key="'checkbox'+index">
                <el-checkbox-group v-model="item.answer.option_ids">
                    <el-checkbox v-for="(choice, index2) in item.options"
                                 class="choiceClass"
                                 :key="'checkbox2'+index+index2"
                                 :label="choice.id"
                                 :disabled="isView">{{choice.content}}</el-checkbox>
                </el-checkbox-group>
            </div>
        </template>

        <div v-if="showType === 1">
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
            <div style="width: 100%;margin-top: 15px" v-if="!isView">
                <cli-upload v-on:notifyurl="urlget" v-bind:urltype="uploadType" style="margin-top: 0"></cli-upload>
            </div>
        </div>

    </div>
</template>

<script>
    import CliUpload from "../base/cliUpload";
    export default {
        name: "message_detail",
        components: {CliUpload},
        props:{
            /**
             * false： 填写
             * true： 查看
             */
            isView:{
                type:Boolean,
            },

            /**
             * 1：电脑端
             * 2：手机端
             * */
            showType:{
                type: Number,
                default: 1,
            }

        },
        data(){
            return{

                /**
                 *  1: 填空题
                 *  2: 单选题
                 *  3: 多选题
                 */
                dataList:[],
                uploadType:'zhengji',
                attachment: [],
                answerList:[],
                beizhu:'',
            }
        },

        methods:{
            setQuestion(data, attachment){
                this.dataList = data;
                this.attachment = attachment;
                console.log(this.attachment)
            },

            getAnswer(){

                return {
                    dataList: this.dataList,
                    attachment: this.attachment.map(d => d.url),
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

            urlget(notifyurl) {
                console.log(notifyurl)
                this.attachment.push(notifyurl)
            },


        }
    }
</script>

<style scoped>
    .el-radio + .el-radio {
        margin-left: 0;
    }
    .el-checkbox + .el-checkbox {
        margin-left: 0;
    }
</style>
