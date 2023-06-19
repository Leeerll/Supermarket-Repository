<template>
    <div class="outDiv">
        <cli-title class="titleClass"></cli-title>
        <cli-menu class="menuClass" :pageIndex="pageIndex"></cli-menu>

        <div class="bodyDiv">
            <div class="orderInfo">
                <!-- <el-alert
                    title="Success"
                    type="success"
                    description="文字说明文字说明文字说明文字说明文字说明文字说明"
                    show-icon>
                </el-alert> -->
                <el-alert v-for="item in infoTable"
                          :key="item.id"
                          :title=item.title
                          :description=item.description
                          effect="dark"
                          type="info">
                </el-alert>

            </div>
        </div>

    </div>
</template>

<script>
import Cookies from 'js-cookie'
import List from "../../components/message/list";
import CliTitle from "../../components/base/cliTitle";
import CliMenu from "../../components/base/cliMenu";
import Position from "../../components/base/position";
import DataEdit from "../../components/message/message_edit";
import Message_detail from "../../components/message/message_detail";
export default {
    name: "index",
    components: { Message_detail, DataEdit, Position, CliMenu, CliTitle,List},
    data(){
        return{
            pageIndex: "10",
            roleType: Cookies.get('type'),
            position: {
                haveBack: true,
            },
            infoTable:[{
                title:"2023-02-12",
                description:"用户发出订单",
            }],
        };
    },

    methods:{
        // 获取超市订单
        getOrderFromSup(){

        }
    }

}
</script>

<style scoped>
.outDiv {
    min-width: 1000px;
}

.titleClass {
    position: fixed;
    min-width: 1000px;
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
    padding: 1.5rem 1.5rem 10rem;
    height: 100%;
    overflow-y: auto;
}
.orderInfo{
    display: flex;
    flex-direction: column;
}

</style>
