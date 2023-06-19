<template>
    <div id="scrollHid"
         style="align-items: center; background-color:rgb(244, 249, 255);padding-right: 0;height:1200px;max-height: 1500px;overflow-y:scroll;">
        <h1></h1>
        <p></p>
        <div class="cls1" style="position: absolute ;width: 200px; height: 100px">
            <p style="color: #1bb8ff;font-size: 16px"></p>
        </div>

        <el-menu
            :default-active="activeIndex"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
            background-color="rgb(222, 242, 253)"
            text-color="rgb(64, 84, 119)"
            active-text-color="rgb(68, 117, 223)"
        >
            <el-menu-item class="menuItem" index="1" @click=toPage style="padding: 0">
                <div v-bind:class="{ 'menuclick': (pageIndex === '1') }"
                     style="font-family: '黑体'; width:100%;font-size: 16px;"><i class="el-icon-menu"></i>超市
                </div>
            </el-menu-item>
            <!-- <el-menu-item class="menuItem" index="8" style="padding: 0; " @click="logout">
                <div v-bind:class="{ 'menuclick': (pageIndex === '8') }"
                     style="font-family: '黑体'; width:100%;font-size: 16px"><i class="el-icon-d-arrow-left"></i>消息
                </div>
            </el-menu-item> -->
            <!-- <el-menu-item class="menuItem" index="8" style="padding: 0; " @click="logout">
                <div v-bind:class="{ 'menuclick': (pageIndex === '8') }"
                     style="font-family: '黑体'; width:100%;font-size: 16px"><i class="el-icon-d-arrow-left"></i>出库信息
                </div>
            </el-menu-item> -->

            <!-- <el-menu-item class="menuItem" style="padding: 0;margin-top: 300px;font-family:'FZYaSongS-B-GB';font-size: 20px">
                仓库管理者
            </el-menu-item>
            <el-menu-item class="menuItem" style="padding: 0;font-family:'FZYaSongS-B-GB';font-size: 20px">
                id: {{this.uid}}
            </el-menu-item> -->

        </el-menu>

    </div>
</template>

<script>
import Cookies from 'js-cookie'
import Pro from '../../api/API_PRO'

export default {
    name: "cliMenu3",
    props: {
        pageIndex: {
            type: String
        }
    },
    data() {
        return {
            uid:'',
            activeIndex: "1",
            name: '',
            isadmin: false,
            centerDialogVisible: false,
            isShowdefMan: false,
            isShowJudge: false,
            id: '',

            /**
             *  '0': 管理员
             *  '1'：普通用户
             * */
            type: Cookies.get('type'),
        }
    },
    mounted: function () {
        this.uid= String(this.$route.query.uid);
        this.activeIndex = this.pageIndex
    },
    methods: {
        toPage(){

        },
        handleOpen(){

        },
        handleClose(){

        }
    }
}
</script>

<style scoped>
.el-menu-vertical-demo {
    text-align: center;
    height: 2850px;
    width: 100%;
}

.menuclick {
    background-color: rgb(244, 249, 255);
}

.pic {
    width: 80px;
    height: 80px;

}

.menuItem {
    height: 40px;
    line-height: 40px;
}

.pic img {
    width: 100%;
    border-radius: 80%;
}

#scrollHid::-webkit-scrollbar {
    display: none;
}

#scrollHid > ul {
    height: 1500px;
}

</style>
<style>
.el-submenu .el-menu-item {
    min-width: 0;

    padding: 0;
    padding-left: 0 !important;
}

.el-menu-item-group__title {
    padding: 0;
    padding-left: 0 !important;
}

.el-submenu__title {
    padding: 0;
    padding-left: 0 !important;

}

.el-menu-item div {
    text-align: left;
    padding-left: 20px;
}


</style>
