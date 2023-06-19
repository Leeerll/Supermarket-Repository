<template>
    <div id="scrollHid"
         style="align-items: center; background-color:rgb(244, 249, 255);padding-right: 0;height:1200px;max-height: 1500px;overflow-y:scroll;">

        <div style="display: flex">
            <el-menu
                :collapse="isCollapse"
                :default-active="activeIndex"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose"
                background-color="rgb(222, 242, 253)"
                text-color="rgb(64, 84, 119)"
                active-text-color="rgb(68, 117, 223)"
            >
                <el-submenu index="8">
                    <template slot="title">
                        <i class="el-icon-menu"></i>
                        <span class="menuItem" style="font-weight: bold">工作台</span>
                    </template>
                    <el-menu-item-group>
                        <el-menu-item class="menuItem" index="1" @click=toCLiHomePage style="padding: 0">
                            <div v-bind:class="{ 'menuclick': (pageIndex === '1') }"
                                 style="font-family: '黑体'; width:100%;font-size: 16px;">入\出库管理
                            </div>
                        </el-menu-item>
                        <el-menu-item class="menuItem" index="10" @click=toCheckPage style="padding: 0">
                            <div v-bind:class="{ 'menuclick': (pageIndex === '10') }"
                                 style="font-family: '黑体'; width:100%;font-size: 16px;">审核订单
                            </div>
                        </el-menu-item>
                        <el-menu-item class="menuItem" index="2" @click=toCLiAuditorPage style="padding: 0; ">
                            <div v-bind:class="{ 'menuclick': (pageIndex === '2') }"
                                 style="font-family: '黑体'; width:100%;font-size: 16px">仓库实时管理
                            </div>
                        </el-menu-item>
<!--                        <el-menu-item class="menuItem" index="3" @click=toDialogPage style="padding: 0; ">
                            <div v-bind:class="{ 'menuclick': (pageIndex === '3') }"
                                 style="font-family: '黑体'; width:100%;font-size: 16px">日志管理
                            </div>
                        </el-menu-item>-->

                        <el-menu-item class="menuItem" index="4" style="padding: 0; " @click="toProcessManagementPage">
                            <div v-bind:class="{ 'menuclick': (pageIndex === '4') }"
                                 style="font-family: '黑体'; width:100%;font-size: 16px">仓库查询
                            </div>
                        </el-menu-item>

                        <el-menu-item class="menuItem" index="7" style="padding: 0; " @click="toSettingPage">
                            <div v-bind:class="{ 'menuclick': (pageIndex === '7') }"
                                 style="font-family: '黑体'; width:100%;font-size: 16px">规则管理
                            </div>
                        </el-menu-item>

                        <el-menu-item v-if="type === '0'" class="menuItem" index="9" style="padding: 0; " @click="toSpacePage">
                            <div v-bind:class="{ 'menuclick': (pageIndex === '9') }"
                                 style="font-family: '黑体'; width:100%;font-size: 16px">状态更新
                            </div>
                            <!--              状态更新是要写什么？-->
                        </el-menu-item>
                    </el-menu-item-group>
                </el-submenu>

                <el-submenu index="9">
                    <template slot="title">
                        <i class="el-icon-service"></i>
                        <span class="menuItem" style="font-weight: bold">用户管理</span>
                    </template>
                    <el-menu-item v-if="this.uid== 1" class="menuItem" index="5" style="padding: 0; " @click="toNewPage">
                        <div v-bind:class="{ 'menuclick': (pageIndex === '5') }"
                             style="font-family: '黑体'; width:100%;font-size: 16px">账号管理
                        </div>
                    </el-menu-item>
                </el-submenu>

            </el-menu>
            <div @click="changeState" style="width: 10px;background-color:gainsboro"> <div style="margin-top: 300px">&lt;</div>  </div>
        </div>

    </div>
</template>

<script>
import Cookies from 'js-cookie'
import Pro from '../../api/API_PRO'

export default {
    name: "cliMenu",
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
            isCollapse: false
        }
    },
    mounted: function () {
        this.uid= String(this.$route.query.uid);
        this.activeIndex = this.pageIndex
    },
    methods: {
        changeState(){
            if(this.isCollapse==true){
                this.isCollapse=false;
            }else {
                this.isCollapse=true;
            }
        },
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        toCLiHomePage() {
            this.$router.push({
                path: `/main/admin`,
                query:{uid:this.uid}
            })
            this.activeIndex = "1"
        },
        toNewPage() {
            this.$router.push({
                path: `/new`,
                query:{uid:this.uid}
            })
            this.activeIndex = "5"
        },
        toCLiAuditorPage() {
            this.$router.push({
                path: `/notify`,
                query:{uid:this.uid}
            })
            this.activeIndex = "2"
        },
        toDialogPage() {
            this.$router.push({
                path: `/message`,
                query:{uid:this.uid}
            })
            this.activeIndex = "3"
        },
        toProcessManagementPage() {
            this.$router.push({
                path: '/person',
                query:{uid:this.uid}
            })
            this.activeIndex = "4"
        },
        toSpacePage() {
            this.$router.push({
                path: `/space`,
                query:{uid:this.uid}
            })
            this.activeIndex = "9"
        },
        toCheckPage(){
            this.$router.push({
                path: `/main/check`,
                query:{uid:this.uid}
            })
            this.activeIndex = "10"
        },

        toSettingPage() {
            this.$router.push({
                path: '/person/user',
                query:{uid:this.uid}
            })
        },
    }
}
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
}
.el-menu-vertical-demo {
    text-align: center;
    height: 2850px;
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
    width: 170px;
    padding-left: 15px;
}


</style>
