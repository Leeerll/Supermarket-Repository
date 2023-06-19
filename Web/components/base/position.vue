<template>
  <div style="height: 40px; padding-top:10px; padding-bottom: 10px; display: flex; justify-content: space-between">
    <div class="positionClass"><strong>{{positionName}}</strong></div>
    <div v-if="haveBack" class="returnClass" @click="clickBack">{{rightName}}</div>
  </div>
</template>

<script>
    export default {
      name: "position",
      props:{
        positionName: { // 所在位置
          type: String
        },
        rightName: { // 右边小字
            type: String,
            default: '返回'
        },
        haveBack: { // 是否有返回键
          type: Boolean,
          default: false,
        }
      },
      data() {
        return {
          showtime:''
        }
      },
      mounted:function(){
        this.getCurrentTime();
      },
      methods: {
          // 点击返回
        clickBack(){
          this.$emit('positionBack', {})
        },
        format(data) {
          if(data>0 && data <=9){
            return "0"+data;
          }else{
            return data;
          }
        },
        getCurrentTime() {
          let now = new Date();
          let year = now.getFullYear(); //得到年份
          let month = now.getMonth();//得到月份
          month = this.format(month + 1)
          let date = this.format(now.getDate());//得到日期
          let day = now.getDay();//得到周几
          let hour = this.format(now.getHours());//得到小时
          let minu = this.format(now.getMinutes());//得到分钟
          let day_s = ""
          switch (day) {
            case 1:
              day_s = "一";
              break;
            case 2:
              day_s = "二";
              break;
            case 3:
              day_s = "三";
              break;
            case 4:
              day_s = "四";
              break;
            case 5:
              day_s = "五";
              break;
            case 6:
              day_s = "六";
              break;
            case 0:
              day_s = "日";
              break;
          }
          this.showtime = year + "年" + month + "月" + date + "日" + "  " + hour + ":" + minu + " 星期" + day_s
        },
      }
    }
</script>

<style scoped>
  .positionClass {
  }

  .returnClass{
    color: #409eff;
    cursor: pointer;
  }
  .returnClass::after{
    content: '>>';
  }
</style>
