export default {
  /**
  * 测试
  * */
   baseURL: 'http://localhost:8181/',

  method: 'post',

  /**
   * 基本信息
   */
  getUnitList: {url: 'common/setting/get-unit'}, //获得单位
  getPositionList: {url: 'common/setting/get-position'}, //获得职务列表
  getJobList: {url: 'common/setting/get-jobtitle'}, // 获取职称信息

  getRenlingList: {url: 'renling/renling/readlist'}, //读取认领列表


  /**
   * 账户
   */
  loginApi: {url: 'user/account/login'}, // 用户登录
  register: {url: 'user/account/register'}, // 用户注册
  Findpw: {url: 'user/account/findpw'}, // 找回密码
  Changepw: {url: 'user/account/changepw'}, // 修改密码
  Getinfo: {url: 'user/information/get-info'}, // 获取用户信息
  Updateinfo: {url: 'user/information/update-info'}, // 更改用户信息


}
