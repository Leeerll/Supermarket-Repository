<template>
<!--    <div class="outDiv">-->
<!--        <titlebar></titlebar>-->
<!--        <cli-title class="titleClass"></cli-title>-->
<!--        <cli-menu class="menuClass" :pageIndex="pageIndex"></cli-menu>-->

<!--        <div class="bodyDiv">-->
<!--            &lt;!&ndash; <position :positionName="position.name" :have-back="false" @positionBack="clickBack"></position> &ndash;&gt;-->
<!--            &lt;!&ndash;选择框&ndash;&gt;-->
<!--            <div class="ruleDiv">-->
<!--                &lt;!&ndash; <div class="ruleset">规则设置</div> &ndash;&gt;-->
<!--                <div class="exportDiv">-->
<!--                    <p>出库规则</p> -->
<!--                    <el-select-->
<!--                    v-model="export_value"-->
<!--                    multiple-->
<!--                    placeholder="Select"-->
<!--                    style="width: 200px"-->
<!--                    >-->
<!--                    <el-option-->
<!--                        v-for="item in export_options"-->
<!--                        :key="item.value"-->
<!--                        :label="item.label"-->
<!--                        :value="item.value"-->
<!--                    />-->
<!--                    </el-select>-->
<!--                </div>-->

<!--                <div class="dispatchDiv">-->
<!--                    <p>调度规则</p> -->
<!--                    <el-select-->
<!--                    v-model="dispatch_value"-->
<!--                    multiple-->
<!--                    placeholder="Select"-->
<!--                    style="width: 200px"-->
<!--                    >-->
<!--                    <el-option-->
<!--                        v-for="item in dispatch_options"-->
<!--                        :key="item.value"-->
<!--                        :label="item.label"-->
<!--                        :value="item.value"-->
<!--                    />-->
<!--                    </el-select>-->
<!--                </div>-->
<!--                <div class="confirm-button">-->
<!--                    <el-button @click="upload_ruleCheck">提交</el-button>-->
<!--                </div>-->
<!--            </div>-->
<!--            &lt;!&ndash;展示已经选择的规则&ndash;&gt;-->
<!--            <div class="selectDisplayDiv">-->
<!--                <div class="collapsetitle">-->
<!--                    当前已经选择如下规则:-->
<!--                </div>-->
<!--                <div class="collapse">-->
<!--                    <el-collapse -->
<!--                    v-model="activeNames" accordion-->
<!--                    v-for="item in selected_rule"-->
<!--                    :key="item"-->
<!--                    >-->
<!--                        <el-collapse-item :title="item" :name="item">-->
<!--                            <div>{{item}}</div>-->
<!--                        </el-collapse-item>-->
<!--                    </el-collapse>-->
<!--                </div>-->
<!--            </div>-->
<!--        </div>-->
<!--        &lt;!&ndash; <cli-footer style="position: absolute; bottom: 0;"></cli-footer> &ndash;&gt;-->
<!--    </div>-->
    <div class="outDiv">
        <titlebar></titlebar>
        <cli-title class="titleClass"></cli-title>
        <cli-menu class="menuClass" :pageIndex="pageIndex"></cli-menu>

        <div class="bodyDiv">
            <!-- <position :positionName="position.name" :have-back="false" @positionBack="clickBack"></position> -->
            <!--选择框-->
            <div class="ruleDiv">
                <!-- <div class="ruleset">规则设置</div> -->
                <div class="exportDiv">
                    <p>出库规则</p>
                    <el-select
                        v-model="export_value"
                        multiple
                        placeholder="Select"
                        style="width: 200px"
                    >
                        <el-option
                            v-for="item in export_options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        />
                    </el-select>
                </div>

                <div class="dispatchDiv">
                    <p>调度规则</p>
                    <el-select
                        v-model="dispatch_value"
                        multiple
                        placeholder="Select"
                        style="width: 200px"
                    >
                        <el-option
                            v-for="item in dispatch_options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        />
                    </el-select>
                </div>
                <div class="confirm-button">
                    <el-button @click="upload_ruleCheck">提交</el-button>
                </div>
            </div>
            <!--展示已经选择的规则-->
            <div class="selectDisplayDiv">
                <div class="collapsetitle">
                    当前已经选择如下规则:
                </div>
                <div class="collapse">
                    <el-collapse
                        v-model="activeNames" accordion
                        v-for="item in selected_rule"
                        :key="item"
                    >
                        <el-collapse-item :title="item" :name="item">
                            <div>{{item}}</div>
                        </el-collapse-item>
                    </el-collapse>
                </div>
            </div>
        </div>
        <!-- <cli-footer style="position: absolute; bottom: 0;"></cli-footer> -->
    </div>
</template>

<script>
import CliTitle from "../../components/base/cliTitle";
import CliMenu from "../../components/base/cliMenu";
import Position from "../../components/base/position";
import Cli_sub_title from "../../components/base/cli_sub_title";
import Notify_edit from "../../components/notify/notify_edit";
import DataEdit from "../../components/message/message_edit";
import Read_list from "../../components/message/unread_list";
import MyTable from "../../components/message/my-table";
import axios from "axios";
// import res from "express/lib/response";
// import API from "../../api";
// import * as ElementPlusIconsVue from '@element-plus/icons-vue'

export default {
    name: "admin_detail",
    components: {MyTable, Read_list, DataEdit, Notify_edit, Cli_sub_title, Position, CliMenu, CliTitle},
    data(){
        return {
            pageIndex: "7",
            export_options:[
                {
                    value:'FIFO', // first in first out
                    label:'先入先出'
                },
                {
                    value:'FDFO', // first date-out first out
                    label:'先到期先出'
                }],
            export_value:[],

            dispatch_options:[
                {
                    value:'first dispatch', // first in first out
                    label:'调度规则设置'
                }],
            dispatch_value:[],

            // collapse默认选择项
            activeNames:[''],
            selected_rule:[],
        }
    },
    mounted() {
        this.getRule()
    },

    methods:{
        // 选择规则更新
        upload_ruleCheck(){
            axios({
                method: 'post',
                url:'http://localhost:8181/rule/setRule',
                data:{
                    export: this.export_value,
                    dispatch: this.dispatch_value
                },
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then(function (response){
                console.log(response.msg)
                alert("规则已成功提交")
            })
        },

        // collapse更新触发
        handleChange(){
        },

        // 获取当前数据库规则
        getRule(){
            axios({
                method:'get',
                url:'http://localhost:8181/rule/sendRule',
                headers:{
                    token:JSON.parse(localStorage.getItem('token'))
                }
            }).then((response)=>{
                // this.selected_rule = null
                // console.log(response.data.data.dispatch)
                // this.selected_rule = []
                // // let export  =
                this.selected_rule.push(response.data.data.export)
                this.selected_rule.push(response.data.data.dispatch)
            })
        },
    },
    // 页面刷新时读取当前选中的规则



}
</script>
<!--<script>-->
<!--    import CliTitle from "../../components/base/cliTitle";-->
<!--    import CliMenu from "../../components/base/cliMenu";-->
<!--    import Position from "../../components/base/position";-->
<!--    import Cli_sub_title from "../../components/base/cli_sub_title";-->
<!--    import Notify_edit from "../../components/notify/notify_edit";-->
<!--    import DataEdit from "../../components/message/message_edit";-->
<!--    import Read_list from "../../components/message/unread_list";-->
<!--    import MyTable from "../../components/message/my-table";-->
<!--    import axios from "axios";-->
<!--    // import res from "express/lib/response";-->

<!--    export default {-->
<!--        name: "admin_detail",-->
<!--        components: {MyTable, Read_list, DataEdit, Notify_edit, Cli_sub_title, Position, CliMenu, CliTitle},-->
<!--        data(){-->
<!--            return {-->
<!--                pageIndex: "7",-->
<!--                export_options:[-->
<!--                {-->
<!--                    value:'FIFO', // first in first out-->
<!--                    label:'先入先出'-->
<!--                },-->
<!--                {-->
<!--                    value:'FDFO', // first date-out first out -->
<!--                    label:'先到期先出'-->
<!--                }],-->
<!--                export_value:[],-->

<!--                dispatch_options:[-->
<!--                {-->
<!--                    value:'first dispatch', // first in first out-->
<!--                    label:'调度规则设置'-->
<!--                }],-->
<!--                dispatch_value:[],-->

<!--                // collapse默认选择项-->
<!--                activeNames:[''],-->
<!--                selected_rule:[],-->
<!--            }-->
<!--        },-->
<!--        mounted() {-->
<!--        },-->

<!--        methods:{-->
<!--            // 选择规则更新-->
<!--            upload_ruleCheck(){-->
<!--                axios({-->
<!--                    method: 'post',-->
<!--                    url:'',-->
<!--                    data:{-->
<!--                        export: this.export_value,-->
<!--                        dispatch: this.dispatch_value-->
<!--                    }-->
<!--                }).then(function (response){-->
<!--                    console.log(response.msg)-->
<!--                })-->
<!--            },-->

<!--            // collapse更新触发-->
<!--            handleChange(){-->
<!--            },-->

<!--            // 获取当前数据库规则-->
<!--            getRule(){-->
<!--                axios({-->
<!--                    method:'get',-->
<!--                    url:'http://localhost:8181/rule/sendRule'-->
<!--                }).then((response)=>{-->
<!--                    // this.selected_rule = null-->
<!--                    // console.log(response.data.data.dispatch)-->
<!--                    // this.selected_rule = []-->
<!--                    // // let export  = -->
<!--                    this.selected_rule.push(response.data.data.export)-->
<!--                    this.selected_rule.push(response.data.data.dispatch)-->
<!--                })-->
<!--            },-->
<!--        },-->
<!--        // 页面刷新时读取当前选中的规则-->
<!--        created(){-->
<!--            this.getRule()-->
<!--        }-->

<!--    }-->
<!--</script>-->

<style scoped>
.outDiv {
    min-width: 1000px;
}

.titleClass {
    position: fixed;
    min-width: 1000px;
    margin-bottom: 2rem;
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
.ruleDiv{
    display: flex;
    /* width: auto; */
    flex-wrap: wrap;
    /* justify-content: space-around; */
}
/* .ruleset{
    width:100%;
    height: 50px;
} */
.exportDiv{
    display: flex;
    width:300px;
    align-items: center;
    justify-content: space-around;
}
.dispatchDiv{
    display: flex;
    width:300px;
    align-items: center;
    justify-content: space-around;
}
.selectDisplayDiv{
    margin-top: 50px;
    margin-left: 10px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    /* align-items: flex-start; */
}
.collapse{
    width: 300px;
}
.collapsetitle{
    margin-bottom: 10px;
}
</style>
<!--<style scoped>-->
<!--    .outDiv {-->
<!--        min-width: 1000px;-->
<!--    }-->

<!--    .titleClass {-->
<!--        position: fixed;-->
<!--        min-width: 1000px;-->
<!--        margin-bottom: 2rem;-->
<!--    }-->

<!--    .menuClass {-->
<!--        position: fixed;-->
<!--        top: 70px;-->
<!--        width: 150px;-->
<!--        z-index: 1;-->
<!--    }-->

<!--    .bodyDiv {-->
<!--        position: fixed;-->
<!--        top: 70px;-->
<!--        left: 150px;-->
<!--        width: calc(100% - 150px);-->
<!--        min-height: calc(100% - 70px);-->
<!--        height: 100%;-->
<!--        background-color: #f4f7fc;-->
<!--        overflow-y: auto;-->
<!--        padding: 1.5rem 1.5rem 10rem;-->
<!--    }-->
<!--    .ruleDiv{-->
<!--        display: flex;-->
<!--        /* width: auto; */-->
<!--        flex-wrap: wrap;-->
<!--        /* justify-content: space-around; */-->
<!--    }-->
<!--    /* .ruleset{-->
<!--        width:100%;-->
<!--        height: 50px;-->
<!--    } */-->
<!--    .exportDiv{-->
<!--        display: flex;-->
<!--        width:300px;-->
<!--        align-items: center;-->
<!--        justify-content: space-around;-->
<!--    }-->
<!--    .dispatchDiv{-->
<!--        display: flex;-->
<!--        width:300px;-->
<!--        align-items: center;-->
<!--        justify-content: space-around;-->
<!--    }-->
<!--    .selectDisplayDiv{-->
<!--        margin-top: 50px;-->
<!--        margin-left: 10px;-->
<!--        display: flex;-->
<!--        flex-direction: column;-->
<!--        justify-content: flex-start;-->
<!--        /* align-items: flex-start; */-->
<!--    }-->
<!--    .collapse{-->
<!--        width: 300px;-->
<!--    }-->
<!--    .collapsetitle{-->
<!--        margin-bottom: 10px;-->
<!--    }-->
<!--</style>-->
