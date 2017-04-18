<template>
	<div class="page-xReportrecord">
		<header-nav title="报班记录" :backshow="backshow"></header-nav>	
	  <div class="contain">
	    <ul>
	      <li v-for="item in items">
	         <dl>
	          <dt>
	          	<span class="wating passStyle" v-if="(item.pass == '待报班')">{{item.pass}}</span>
              <span class="pass passStyle" v-if="(item.pass == '考试通过')">{{item.pass}}</span>
	          	<span class="nopass passStyle" v-if="(item.pass == '考试不通过')">{{item.pass}}</span>
	          </dt>
	          <dd class="firstDl">
              <p>标题</p>
	            <p>用时: {{item.scoreLl}}</p>
              <p>得分: {{item.scoreSxt}}</p>
	            <p>得分: {{item.scoreSxt}}</p>
	          </dd>
	          <dd><span class="special total-time">{{item.examTime}}</span></dd>
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
export default {
  name: 'xReportrecord',
  components: {
    headerNav,loading,toast
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
      items:[{
          scoreTotal:210,
          pass:"待报班",
          scoreLl:51,//理论成绩
          scoreSxt:51,//水系统成绩
          scoreKzs:51,//控制室成绩
          scoreFhxc:51,//防火巡查成绩
          scoreQtmh:51,//气体灭火成绩
          examTime:"2016年11月28日",
          examLocation:"深圳远泰消防学校1"
      },{
         scoreTotal:200,
          pass:"考试不通过",
          scoreLl:50,//理论成绩
          scoreSxt:50,//水系统成绩
          scoreKzs:50,//控制室成绩
          scoreFhxc:50,//防火巡查成绩
          scoreQtmh:50,//气体灭火成绩
          examTime:"2016年12月28日",
          examLocation:"深圳远泰消防学校"
      },{
         scoreTotal:200,
          pass:"考试通过",
          scoreLl:50,//理论成绩
          scoreSxt:50,//水系统成绩
          scoreKzs:50,//控制室成绩
          scoreFhxc:50,//防火巡查成绩
          scoreQtmh:50,//气体灭火成绩
          examTime:"2016年12月28日",
          examLocation:"深圳远泰消防学校"
      }
      ]
    }
  },
  methods: {
   renderData:function(){
      this.loading = true;
      apiService.myExam({code:window.localStorage.getItem("codeStatus")}).then((data) =>{
               this.loading = false;
               this.items = data.data.exams[0];
            },(err) =>{
              this.loading = false;
              this.show2 = true;
              this.warntext ="网络不给力,请稍后再试...";
              console.log(err);
            })
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
.page-xReportrecord{
  .contain{
    width:100%;
    height:auto;
    background:#fff;
    margin-top:.5rem;
    margin-bottom: 3rem;
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
            .passStyle{
              color: #fff;
              background:url(../assets/images/bg_title_purple.png) left no-repeat;
              background-size: 100% 100%;
              padding: 0 1.5rem 0 1rem;
              display: inline-block;
              height:2rem;
              line-height: 2rem;
              position: relative;
            }
            .pass{
              background:url(../assets/images/bg_title_green.png) left no-repeat;
              background-size: 100% 100%;
            }
            .nopass{
              background:url(../assets/images/bg_title_red.png) left no-repeat;
              background-size: 100% 100%;
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


























