<template>
  <div class="loginWrapper">
    <div class="back-ground" style="overflow-y: hidden; z-index: -1">
      <img src="../assets/image/index-background.jpg">
    </div>

    <el-tabs  class="bd" type="border-card">
      <el-tab-pane label="仓库管理员登录">
        <p class="title">
          电信学院科研项目服务平台
          <span style="display: inline-block; padding-left: 100%"></span>
        </p>
        <el-form :model="loginForm" :rules="loginRule" ref="loginForm" :label-position="labelPosition">
          <el-form-item  prop="userName" class="login-item" style="margin: 0">
            <el-input type="userName" v-model="loginForm.userName" placeholder="账号" @keyup.enter.native="submitForm('loginForm')"></el-input>
          </el-form-item>
          <el-form-item prop="pwd" style="margin-top: 20px;margin-bottom: 0px">
            <el-input v-model="loginForm.pwd" placeholder="密码" type="password" @keyup.enter.native="submitForm('loginForm')"></el-input>
          </el-form-item>
          <el-form-item style="margin-top: 20px">
            <el-button type="primary" @click="submitForm('loginForm')" class="submitBtn" round >登录</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script type="text/javascript">
  import API from '../api'
  import Pro from '../api/API_PRO'
  import Cookies from 'js-cookie';
  import axios from 'axios'

  export default {
    name: 'Login',
    data () {
      return {
        loadingLogin: false,
        labelPosition: 'left',
        loginForm: {
          userName: '',
          pwd: ''
        },
        loginRule: {
          userName: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
            //{ min: 3, max: 10, message: '长度在 3 到 16 个字符', trigger: 'blur' }
          ],
          pwd: [
            { required: true, message: '请输入密码', trigger: 'blur' }
            //{ min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
          ]
        },
      }
    },
    methods: {
      async submitForm (loginForm) {
        this.$refs[loginForm].validate((valid) => {
          if (!valid) {
            return
          }
          this.loadingLogin = true
        })
        let result = await this.$store.dispatch('login', {
          account: this.loginForm.userName,
          password: this.loginForm.pwd
        })
        if (result.code) {
          if (result.code === 100310) {
            this.loadingLogin = false
            alert('密码／用户名不能为空')
            console.log('密码／用户名不能为空')
            return
          } else if (result.code === 300101) {
            this.loadingLogin = false
            alert('密码不正确')
            console.log('密码不正确')
            return
          } else if (result.code === 300100) {
            console.log(result)
            this.loadingLogin = false
            alert('用户名不存在！')
            return
          } else if(result.code === 300104){
            console.log(result)
            this.loadingLogin = false
            alert('账户无效！')
            return
          }else {
              console.log(result)
              return
          }
        }

        let type = Cookies.get('type');

        if (type === '0') {
          this.$router.push({path: `/main/admin`})
        } else if (type === '1') {
          this.$router.push({path: `/main/user`})
        }
      },

      userLogin(){
          const client_id = 'FJXPslUxKPuB16fGRWSzVusxwvNadiWLG3sTJvyX';
          const redirect_uri = Pro.qianduanURL;
          const url = `https://cas.bjtu.edu.cn/o/authorize?client_id=${client_id}&response_type=code&redirect_uri=${redirect_uri}`
          window.location.replace(url);
      },

    }
  }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .back-ground{
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
  }
  .back-ground>img{
    width: 100%;
    height: 100%;
  }

  .bd{
      width: 20rem;
      margin-left: 60%;
      margin-top: 15%;
      min-height: 15rem;
      background-color: white;
      border-radius: 5px;
  }

  .title{
      width: 100%;
      text-align: justify;
      height: 2.5rem;
  }
  .title:after{
      display: inline-block;
      width: 100%;
      content: '';
  }

  .submitBtn{
      width: 100%;
      border-radius: 0;
      margin-bottom: 0px;
      background-color: #1965d9;
      border-width: 0;
      transition: 1s;
  }


</style>
