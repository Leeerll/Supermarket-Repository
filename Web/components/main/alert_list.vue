<template>
    <div>
        <div style="display: flex; flex-direction: row; justify-content: space-between; padding: 5px">
            <strong>{{titleList[showType]}}</strong>
            <div class="more" @click="moreInfo(showType)">更多</div>
        </div>

        <el-card :body-style="{ padding: '0px' }" style="min-height: 250px;">
            <div  style="display: table; width: 100%; padding-left: 10px; padding-top: 20px; padding-bottom:15px; padding-right: 10px">
                <div style="display: table-row">
                    <div class="tableTitle" style="width: 40px">
                        序号
                    </div>
                    <div class="tableTitle" style="width: 180px">
                        名称
                    </div>
                    <div v-if="showType === 1" class="tableTitle">
                        开展周期
                    </div>
                    <div v-if="showType === 2" class="tableTitle">
                        状态
                    </div>
                    <div class="tableTitle" style="width: 50px">
                        操作
                    </div>
                </div>

                <div style="display: table-row;" v-if="dataList.length > 0" v-for="(item,index) in dataList" :key="item.id">

                    <div class="tableCell">
                        {{index + 1}}
                    </div>
                    <strong  v-if="showType === 0"  class="tableCell">
                        {{item.title}}
                    </strong>
                    <strong  v-if="showType === 1"  class="tableCell">
                        {{item.name}}
                    </strong>



                    <div  v-if="showType === 1"  class="tableCell">
                        {{item.range}}
                    </div>
                    <div v-if="showType === 2"  class="tableCell">
                        单位
                    </div>
                    <div class="tableCell">
                        <el-button size="mini"  @click="readNotify(item)">详情</el-button>
                    </div>
                </div>
            </div>
            <div v-if="dataList.length == 0&&showType === 0" class="notify">暂无未读通知</div>
            <p v-if="dataList.length == 0&&showType === 1" class="notify">暂无待交征集</p>
        </el-card>

    </div>
</template>

<script>
    export default {
        name: "alert_list",

        props: {
            /**
             * 0： 未读通知
             * 1： 待交征集
             * 2： 项目管理
             */
            showType: {
                type: Number,
            },
            dataList: {
                type: Array,
            }
        },
        data() {
            return {
                titleList: ['未读通知', '待交征集', '项目管理']
            }
        },
        methods: {
            readNotify(item) {
                if (this.showType === 0) {
                    this.$router.push({path: `/notify/` + item.query_id + `/detail`})
                }else if (this.showType === 1) {
                    this.$router.push({path: `/message/` + item.id + `/user_detail`})
                }
            },
            /**
             * ‘更多’按钮
             * @param type showType类型
             */
            moreInfo (type) {
                if (type===0)
                    this.$router.push({path: '/notify' });
                else if (type===1)
                    this.$router.push({path: '/message' });
                else
                    this.$router.push({path: '' });
            }
        }
    }
</script>

<style scoped>
    .more{
        cursor: pointer;
        color: #409eff;
    }
    .more::after{
        content: '>>';
    }

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
    .notify{
        margin:0 auto;
        text-align: center;
        margin-top: 60px;
        color: #7f828b;
    }
</style>
