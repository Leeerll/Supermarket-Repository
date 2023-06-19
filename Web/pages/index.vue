<template style="overflow-y: hidden">
  <div class="loginWrapper">
    <div class="back-ground" style="overflow-y: hidden; z-index: -1">
      <img src="../assets/image/index-background.jpg">
    </div>

    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <el-tabs  class="bd" type="border-card">
      <el-tab-pane label="仓库管理员登录">
        <p class="title">
          自动化仓库管理系统
          <span style="display: inline-block; padding-left: 100%"></span>
        </p>
          <el-form :model="loginForm" :rules="loginRule" ref="loginForm" :label-position="labelPosition">
              <el-form-item  prop="userName" class="login-item" style="margin: 0">
                  <el-input type="userName" v-model="userName" placeholder="账号"></el-input>
              </el-form-item>
              <el-form-item prop="pwd" style="margin-top: 20px;margin-bottom: 0px">
                  <el-input v-model="pwd" placeholder="密码" type="password"></el-input>
              </el-form-item>
              <el-form-item style="margin-top: 20px">
                  <el-button type="primary" @click="submitForm('loginForm')" class="submitBtn" round >登录</el-button>
              </el-form-item>
          </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import upload from '~/components/base/cliUpload.vue'
import axios from 'axios'
import Cookies from 'js-cookie';
import Pro from '../api/API_PRO'
import API from '../api'
import { getBrowser } from "../components/util";

export default {
  components: {},

  data () {
    return {
      vartype:'',
      loadingLogin: false,
      labelPosition: 'left',
      loginForm: {
        userName: '',
        pwd: '',
      },
      userName: '',
      pwd: '',
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

  mounted() {

  },

  methods: {
      submitForm(loginForm) {
          let data = {
              token:Cookies.get('token'),
              uid: this.userName,
              password: this.pwd,
          }
          axios.post("http://localhost:8181/user/login", data,{
              headers:{
                  token:JSON.parse(localStorage.getItem('token'))
              }
          })
              .then(response => {
                  console.log("类型",this.vartype)
                  console.log("data",response.data)
                  if (response.data.code != "0") {
                      this.vartype = response.data.data.type;
                      if (this.vartype == "super") {
                          console.log("总管理员登录");
                          this.$router.push({path: `/manager`});
                      } else {
                          console.log("仓库管理者账号登录");
                          localStorage.setItem('token', JSON.stringify(response.data.data.token))
                          this.$router.push({
                              path: `/main/admin/`,
                              query:{uid:response.data.data.user.uid}
                          });
                      }
                  } else {
                      axios.post("http://localhost:8181/supermarket/login", data,{
                          headers:{
                              token:JSON.parse(localStorage.getItem('token'))
                          }
                      })
                          .then(response2 => {
                              if (response2.data.code !=="0") {
                                  console.log("超市账号登录")
                                  //console.log("response2.data.data",response2.data.data.token)
                                  // 获取token token:Cookies.get('token'),
                                  // 设置token
                                  localStorage.setItem('token', JSON.stringify(response2.data.data.token))
                                  this.$router.push({
                                      path: `/supermarket`,
                                      query:{suid:response2.data.data.supermarket.suid}
                                  });
                              } else {
                                  alert("账号不存在，请重新输入！")
                              }
                          })
                  }
              })
      }
  }
}
</script>

<style scoped>
  .back-ground{
    position: fixed;
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
    width: 30rem;
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
  @media only screen and (max-width: 1200px) {
      .bd{
          width: 30rem;
          margin-left: 40%;
          margin-top: 15%;
          min-height: 15rem;
          background-color: white;
          border-radius: 5px;
      }
  }

  @media only screen and (max-width: 900px) {
      .bd{
          width: 20rem;
          margin-left: 30%;
          margin-top: 15%;
          min-height: 15rem;
          background-color: white;
          border-radius: 5px;
      }
  }

  @media only screen and (max-width: 800px) {
      .bd{
          width: 20rem;
          margin-left: 20%;
          margin-top: 15%;
          min-height: 15rem;
          background-color: white;
          border-radius: 5px;
      }
  }


</style>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
</head>
