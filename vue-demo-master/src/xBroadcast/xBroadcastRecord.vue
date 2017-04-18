<template>
	<div class="page-xBroadcastRecord">
		<header-nav title="播放记录" :backshow="backshow"></header-nav>
    <div class="content">
    <ul>
      <li>我好帅啊</li>
    </ul> 
    <loading :show="loading"></loading>
    <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
	</div>
</template>


<style lang="scss">
  .page-xBroadcastRecord{
    .content{
      background: #fff;
      ul{
        li{
          width: 100%;
          padding: 1rem 8% 1rem .8rem;
          background: url(../assets/images/icon_arrow.png) 96% no-repeat;
          background-size: .55rem 1rem;
          border-bottom: 1px solid #ccc;
          word-break: break-all;
        }

        li:last-child{
          border-bottom: none;
        }
      }

    }
  }
</style>


<script>
import headerNav from '../common/header.vue'
import loading from 'vux-components/loading'
import apiService from '../apiservice.js'
import toast from 'vux-components/toast'
export default {
  name: 'xBroadcastRecord',
  components: {
    headerNav,loading,toast
  },
  ready(){
    // this.renderData();
  },
  data () {
    return {
      backshow:true,
      loading:false,//控制是否出现loading
      show2:false,//控制toast的出现
      warntext:"",
      items:[{
          scoreTotal:210,
          pass:"人工卷",
          scoreLl:51,//理论成绩
          scoreSxt:51,//水系统成绩
          scoreKzs:51,//控制室成绩
          scoreFhxc:51,//防火巡查成绩
          scoreQtmh:51,//气体灭火成绩
          examTime:"2016年11月28日",
          examLocation:"深圳远泰消防学校1"
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




























