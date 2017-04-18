<template>
	<div class="page-xTestrecord">
		<header-nav title="考试记录" :backshow="backshow"></header-nav>	
    <no-message tip="暂无考试记录" v-show="hasExam"></no-message>
	  <div class="contain">
	    <ul>
	      <li v-for="item in items" @click="goDetail(item.id)">
	         <dl>
	          <dt>
	          	<span class="pass">随机卷</span>
	          	<!-- <span class="pass nopass" v-if="(item.pass == '随机卷')">{{item.pass}}</span> -->
	          </dt>
	          <dd class="firstDl">
              <p>{{item.examinationPaperName}}</p>
	            <p>用时: {{item.useTime}}</p>
	            <p>得分: {{item.score}}</p>
	          </dd>
	          <dd><span class="special total-time">{{item.createTime}}</span></dd>
	        </dl>
	      </li>
	    </ul>
	  </div>
    <loading :show="loading"></loading>
    <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
	</div>
</template>

<script>
import headerNav from '../common/header.vue'
import loading from 'vux-components/loading'
import apiService from '../apiservice.js'
import toast from 'vux-components/toast'
import noMessage from '../common/noMessage.vue'
export default {
  name: 'xTestrecord',
  components: {
    headerNav,loading,toast,noMessage
  },
  ready(){
    this.renderData();
  },
  data () {
    return {
      backshow:true,
      loading:false,//控制是否出现loading
      show2:false,//控制toast的出现
      warntext:"",
      items:[],
      hasExam:false,
    }
  },
  methods: {
   renderData:function(){
      this.loading = true;
      apiService.examResultList({
        code:window.localStorage.getItem("codeStatus"),
        sysCode:window.localStorage.getItem("sysCode"),
        studentNo:window.localStorage.getItem("studentNo"),
        // studentNo:"170405002",
        // sysCode:"94a1533f-85a2-4c6a-8850-74a39b2b5ba9",
      }).then((data) =>{
               this.loading = false;
               if(data.data.code == 200){
                 this.items = data.data.data;
                 if(this.items.length < 1){
                   this.hasExam = true;
                 }
                 this.items.forEach((item,index) => {
                       item.useTime = this.formatSeconds(item.useTime);  
                    })
               }else{
                this.show2 = true;
                this.warntext =data.data.msg;
               }
            },(err) =>{
              this.loading = false;
              this.show2 = true;
              this.warntext ="网络不给力,请稍后再试...";
              console.log(err);
            })
   },
   formatSeconds:function(value) {
    var theTime = parseInt(value);// 秒
    var theTime1 = 0;// 分
    var theTime2 = 0;// 小时
    if(theTime > 60) {
        theTime1 = parseInt(theTime/60);
        theTime = parseInt(theTime%60);
            if(theTime1 > 60) {
            theTime2 = parseInt(theTime1/60);
            theTime1 = parseInt(theTime1%60);
            }
    }
        var result = ""+parseInt(theTime)+"秒";
        if(theTime1 > 0) {
        result = ""+parseInt(theTime1)+"分"+result;
        }
        if(theTime2 > 0) {
        result = ""+parseInt(theTime2)+"小时"+result;
        }
    return result;
},
   goDetail:function(val){
      this.$router.go({name:'examResult',params:{id:val}});
   },
   popState:function(){
    var state = { 
    title: "title", 
    url: "#"
    }; 
    window.history.pushState(state, "title", "#"); 
   },
  },

}

</script>
<style lang="scss">
.page-xTestrecord{
  .contain{
    width:100%;
    height:auto;
    background:#fff;
    margin-top:.5rem;
    ul{
      width:100%;
      height:auto;
      li{
        width:100%;
        border-top:1px solid #dddddd;
        list-style: none;
        padding-bottom: 1.2rem;
        dl{
          overflow: hidden;
          margin: 0;
          background: url(../assets/images/icon_arrow.png) 96% no-repeat;
          background-size: .55rem 1rem; 
          dt{
            overflow: hidden;
            height:2rem;
            line-height: 2rem;
            margin-bottom: .6rem;
            .totalScore{
              float: right;
              background: #f5f3f4;
              padding:.2rem .5rem;
              margin: .2rem .5rem 0 0;
              line-height: 1rem;
            }
            .pass{
              color: #fff;
              width: 50%;
              background:url(../assets/images/bg_title_yellow.png) left no-repeat;
              background-size: 5.5rem;
              padding:0 .5rem;
              display: inline-block;
              height:2rem;
              line-height: 2rem;
              position: relative;
            }
            .nopass{
              width: 50%;
              background:url(../assets/images/bg_title_yellow.png) left no-repeat;
              background-size: 5.5rem;
            }
            
          }
          dd{
            text-align: left;
            overflow: hidden;
            margin: 0;
            padding: 0 .4rem;
            box-sizing:border-box; 
            p{
              overflow: hidden;
              text-overflow: ellipsis;
              display: -webkit-box;
              -webkit-line-clamp: 1;
              -webkit-box-orient: vertical;
              word-break: break-word;
              font-size: .9rem;
              line-height: 1.2rem;
            }
            .special{
              color: #666;
              font-size: .7rem;
              line-height: 1.2rem;
            }
          }
          .firstDl{
            margin-bottom: .5rem;
          }
        }
        
      }
    }
  }
 
}
</style>


























