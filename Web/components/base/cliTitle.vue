<template >
    <div id="out_div" style="display: flex; box-shadow: 0 0 5px rgba(158,158,158,0.7); text-align: center">
      <p  style="font-weight: bold; font-family:'FZYaSongS-B-GB'; font-size: 28px; color: rgb(37, 144, 226);position: absolute;left: 90px">自动化立体仓库管理系统</p>
      <p style="font-family:'FZYaSongS-B-GB';font-size: 16px;right: 190px;position: absolute">{{this.identity}}   id: {{this.uid}}</p>
      <div @click="logout" style="font-family: '黑体';font-size: 16px;position: absolute;right:50px"><i class="el-icon-d-arrow-right"></i>退出
      </div>
    </div>
</template>

<script>
  import API from '../../api'
  import Cookies from 'js-cookie'
  import PRO from '../../api/API_PRO.js'

    export default {
        name: "cliTitle",

      data () {
        return {
          state:'',

          /**
           * 0是普通用户
           * 1是管理员
           * 2是评委
           * **/
          roleType:Cookies.get('type'),
          uid:0,
          identity:"仓库管理者"
        }
      },
      mounted: function () {
            if(this.$route.query.uid!=null){
                this.uid= String(this.$route.query.uid);
                this.identity = "系统管理者";
            }else{
                this.uid= String(this.$route.query.suid);
                this.identity = "超市";
            }

      },
      methods: {
        querySearchAsync (queryString, cb) {
          let token = Cookies.get('token')
          let data = {
            token: token,
            keyword: queryString
          }
          API.searchProject(data).then(res => {
            this.restaurants = res.list
            clearTimeout(this.timeout)
            this.timeout = setTimeout(() => {
              //let results = queryString ? this.restaurants.filter(this.createStateFilter(queryString)) : this.restaurants
              let results = queryString ?this.createStateFilter(this.restaurants): this.restaurants
              console.log(results)
              cb(results)
            }, 3000 * Math.random())
          }).catch(msg => {
            let results = queryString ?this.createStateFilter(this.restaurants): this.restaurants
            cb(results)
          })
        },
        createStateFilter (restaurants) {
          let results = []
          restaurants.forEach(item=>{
            results.push({'content':((item.projectName) + '-' + (item.principalInfo.name  )+ '-' + (item.principalInfo.phone)), 'projectId':item.projectId})
          })
          return results
        },
        //查看详情
        gocliNoticeDetail(item) {
          if (Cookies.get('type') === '0') {
            //普通用户跳转到管理页面
            //this.$router.push({path: '/processManagement/_paperId/userProcess?projectId='+item.projectId})
            this.$router.push({path:'/refreshPage?url='+'/processManagement/_paperId/userProcess?projectId='+item.projectId})
          }else if (Cookies.get('type') === '1') {
            //管理员
            //this.$router.push({path: '/project/cliProDetailPage?projectId='+item.projectId})
            this.$router.push({path:'/refreshPage?url='+'/project/cliProDetailPage?projectId='+item.projectId})
          }
        },
        // 退出
        logout() {
            this.$confirm('是否确认退出系统?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                const url = "/"
                window.location.replace(url);
            }).catch(() => {
            });
        }
      }
    }
</script>

<style scoped>
  #out_div{
    width: 100%;
    height: 70px;
    top:0;
    background-color: rgb(255,255,255);
    z-index: 9999;
      line-height: 70px;
  }
</style>
