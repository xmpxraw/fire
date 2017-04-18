<template>
	<div class="page-xTestresult">
		<header-nav title="考试结果" :backshow="backshow"></header-nav>
    <div class="content">
        <p>这是第一个p</p>
        <p>这是第er个p</p>
        <div class="score">
          <span>222</span>
        </div>

        <p class="timeConsuming wastTime"><img src="../assets/images/icon_time01.png" alt="">耗时： 11111</p>
        <p class="wastTime"><img src="../assets/images/icon_time02.png" alt="">考试时间： 11111</p>

        <table class="scoreTable">
          <tr>
            <td>判断题</td>
            <td>得分</td>
          </tr>
          <tr>
            <td>判断题</td>
            <td>得分</td>
          </tr>
          <tr>
            <td>判断题</td>
            <td>得分</td>
          </tr>
        </table>
    </div>
    
    <footer>
      <span>查看试卷</span>
      <span>只看错题</span>
    </footer>
    
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
  name: 'xTestresult',
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

<style lang="scss">
.page-xTestresult{
  .content{
    padding: 1rem .5rem 1.5rem .5rem;
    background: #fff;

    p{
      margin-bottom: 1rem;
    }

    .score{
       -webkit-justify-content: center;
      justify-content: center;
      display: -webkit-box;
      -webkit-box-pack: center;
      width: 100%;
      height: 3rem;
      margin-bottom: 1.8rem;

      span{
        display: inline-block;
        width: 4rem;
        height: 3rem;
        line-height: 3rem;
        background: blue;
        text-align: center;
        font-size: 1.8rem;
        color: #fff;
        border-radius: .4rem;
        background: #53b6ef;
      }

    }
    
    .wastTime{
      text-align: center;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      word-break: break-word;
      img{
        width: 1rem;
        height: 1rem;
        vertical-align: bottom;
        margin-right: .2rem;
      }
    }

    .scoreTable{
      width:80%;
      margin-left: 10%;
      border-left:#ccc solid 1px;
      border-top:#ccc solid 1px;
      border-radius: .5rem;
      border-collapse: collapse;
      tr{
        td: first-child{
          border-bottom-right-radius: 1rem;
        }
        td{
          width:25%;
          border-right:#ccc solid 1px;
          border-bottom:#ccc solid 1px;
          padding:10px 10px 6px;
          vertical-align: center;
          text-align: center;
          font-size: .8rem;
        }
      }
    }
  }
  footer{
    width: 100%;
    height: 2.2rem;
    background: #fff;
    margin-top: .5rem;
    padding: .5rem 0;

    span:first-child{
      border-right: 1px solid #ccc;
      background: url(../assets/images/icon_book.png) 20% center no-repeat;
      background-size: .8rem .8rem;
    }

    span{
      width: 48%;
      display: inline-block;
      height: 1.2rem;
      line-height: 1.2rem;
      text-align: center;
      box-sizing: border-box;
      font-size: .8rem;
      background: url(../assets/images/icon_wrong.png) 20% center no-repeat;
      background-size: .8rem .8rem;

    }
  }
}
</style>


























