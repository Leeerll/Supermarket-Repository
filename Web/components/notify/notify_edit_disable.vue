<template>
  <div style="display: block;padding-left: 30px;padding-right: 30px;padding-top: 20px" v-if="isshow">
      <div style="margin-top: 15px;width: 100%;" class="display-row">
          <div class="filelabel row-left"><p>名称:</p></div>
          <div class="titleinput row-right">
              <p>{{notifytitle}}</p>
          </div>
      </div>

      <div v-if="type==0&&showType==1" style="margin-top: 15px" class="display-row">
          <div class="filelabel row-left"><p>对象:</p></div>
          <div class="titleinput row-right">
              <p>{{notifyRange}}</p>
          </div>

      </div>

      <div style="margin-top: 15px;width: 100%;" class="display-row" v-if="notitype === 3">
          <div class="filelabel row-left"><p>时间范围:</p></div>
          <div class="titleinput row-right">
              {{getdate(timeRange[0])}} - {{getdate(timeRange[1])}}
          </div>
      </div>

          <!--富文本编辑器组件-->
          <el-row class="display-row" style="margin-top: 20px" v-if="isPutongNotify">
              <div class="filelabel row-left"><p>内容:</p></div>
              <div class="row-right">
            <quill-editor
              v-model="detailContent"
              ref="myQuillEditor"
              :options="editorOption"
              style="height: 400px;"
              @focus="editorFocus($event)"
            >
            </quill-editor>
              </div>
          </el-row>
      <!--个性通知表格-->
      <div style="display: table; width: 100%; padding-left: 10px; padding-top: 20px; padding-bottom:15px; padding-right: 10px" v-if="!isPutongNotify&&type==0">
          <div style="display: table-row">
              <div class="tableTitle" style="width: 50px; text-align: center;padding-left: 10px">
                  序号
              </div>
              <div class="tableTitle" style="text-align: center">
                  通知对象
              </div>
              <div class="tableTitle" style=" text-align: center">
                  通知内容
              </div>
          </div>
          <div :class="{'tablerow':true, 'grayLine': (index % 2 === 0)}" style="display: table-row;"
               v-if="notifyList.length > 0" v-for="(item,index) in notifyList" :key="item.id">
              <div class="tableCell" style="text-align: center;">
                  {{index+1}}
              </div>
              <div class="tableCell" style="text-align: center">
                  {{item.range[0].userInfo.name}}
              </div>
              <quill-editor
                      v-model="item.content"
                      :options="editorOption"
                      class="tableCell" style="text-align: center">
                  {{item.content}}
              </quill-editor>
          </div>
      </div>
      <div class="fujian display-row" style="margin-top: 80px;">
          <div class="filelabel row-left"><p>附件:</p></div>
        <el-table
          :data="attachment"
          @row-click="clickrow"
          :row-style="{cursor:'pointer'}"
          style="width: 100%">
          <el-table-column
            prop="filename"
            label="附件（点击可下载）">
          </el-table-column>
        </el-table>
      </div>
  </div>
</template>

<script>
  import cliUpload from '../base/cliUpload.vue'
  import * as Quill from 'quill'  //引入编辑器
  import { quillEditor } from 'vue-quill-editor'

  import API from '../../api'
  import Cookies from 'js-cookie'
  import PRO from '../../api/API_PRO.js'

  let quill;

  export default {
    name: "notify_edit_disable",
    props:{
        /**
         *  3   信息征集
         *  2   基本信息
         *  1   通知
         */
        notitype: {
            type: Number, //
            default: 1
        },
      infoid:''//查看详情或者是编辑普通通知时传入的id,如果id是空的话
    },
    components:{
      cliUpload,
    },

    data(){
        return {
            token: Cookies.get('token'),
            notifyRange: '',
            isshow: true,
            attachment: [],//该条详情的附件
            notifytitle: '',//标题
            detailContent: '', // 富文本内容
            type: Cookies.get('type'),
            showType:1,
            editorOption: {
                placeholder: '',
                theme: 'snow',  // or 'bubble'
                modules: {
                    toolbar: null
                }
            },
            updateTime:'',
            notifyList:[],
            timeRange:[],
            isPutongNotify:true,

        }
    },
    mounted:function () {


      //
      //   this.getinf(this.infoid)
      // this.isshow = true
      //   $('.ql-container.ql-snow').css('border', '0');
        console.log(this.notitype)
        if(this.notitype !== 3){
            let data ={
                token: this.token,
                query_id : this.infoid
            }
            API.readNotify(data).then(res => {
                if(res.code){
                    alert(res.message);
                    return;
                }
                console.log('isthis');
                console.log(res);
                if(res.type===1){
                    this.notifytitle = res.title
                    this.detailContent = res.content
                    this.attachment = res.attachment
                    res.range.forEach(item=>{
                        this.notifyRange = this.notifyRange + item.userInfo.name + ' '
                    })
                }else{
                    if(this.type==0)
                    {this.notifytitle=res[0].title
                        this.notifyList=res
                        this.showType=0
                        this.isPutongNotify=false
                    }
                    else{
                        this.notifytitle = res.title
                        this.detailContent = res.content
                    }

                }
            }).catch(msg => {
                if(res.code){
                    alert(res.message);
                    return;
                }
                alert(msg)
            })

        }

    },
    methods:{

        editorFocus(qevent){
            qevent.enable(false)
        },

        getdate(timestamp) {
            var now = new Date(timestamp),
                y = now.getFullYear(),
                m = now.getMonth() + 1,
                d = now.getDate();
            return y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d) + " "
        },

        setData(data){
            this.attachment = data.attachment;
            this.timeRange = [data.start_timestamp, data.stop_timestamp];
            this.detailContent = data.requirement;
            this.notifytitle = data.name;
            console.log(data.surveyRange);
            if (data.surveyRange){

                data.surveyRange.forEach(item=>{
                    this.notifyRange = this.notifyRange + item.userInfo.name + ' '
                })
            }

        },

      /**
       * 点击下载附件
       * */
      clickrow(row, event, column){
        window.open(row.url)
      },
      /**
       * 请求详情信息
       * */
      getinf(notifyid){
        let data = {
          token: this.token,
            notify_id:notifyid,
        }
        API.notifyRead(data).then(res => {
          this.notifytitle = res.info.title;
          this.detailContent=res.info.content
            this.attachment = res.info.attachment;
          this.updateTime = res.info.update_time
            let quill = this.$refs.myQuillEditor.quill
            quill.enable(false);
          return res
        }).catch(msg => {
          alert('请求失败，请稍后再试!')
        })

      },
    },
  }
</script>

<style scoped>
  .div_inline{
    display:inline
  }
  .margin_top{
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
      text-align: left;
      min-width: 100px;
  }

  .row-right{
      width: 92%;
  }

  .titleinput {
      height: 40px;
      margin-left: 5px;
      font-size: 16px;
  }


  .titleinput p {
      height: 40px;
      line-height: 30px;
      font-size: 16px;
      border: 1px solid #c0c4cc;
      background: #ffffff;
      outline: 0;
  }


  .filelabel{
    float: left;
    height: 30px;
  }

  .filelabel p{
    line-height: 30px;
    max-lines: 1;
    font-size: 16px;
    color:#5EBBDA ;
    text-align: center;
  }


  .tableTitle{
      display: table-cell;
      font-size: 14px;
      padding: 5px;
      font-family: 黑体;
      font-weight: bold;
      color: gray;

  }
  .tableCell{
      display: table-cell;
      font-size: 15px;
      padding: 5px;
  }

  .grayLine{
      background-color: #e4efff;
  }
</style>
