<template>
	<div class="page-xPractice">
		<header-nav title="章节练习" :backshow="backshow"></header-nav>
	<div class="contain">
	  <div class="contain-top">
	  	<div class="choose">
	  		<span>目录：</span>
	  		<select name="choose" id="Screening" @change="getChapterDel"  v-model="selectValue">
          <option data-id="" value="">全部</option>
	  			<option v-for="item in successData" data-id={{item.id}} value="{{item.id}}">{{item.name}}</option>
	  		</select>
	  	</div>
	  </div>

	  <div class="contain-center">
	  	<span class="allPro" @click="goAllprt">全部</span>
	  	<span class="wrongPro" @click="goprtTj">练习统计</span>
	  </div>

	  <div class="contain-foot">
	  	<ul>
	  		<li v-for="item in chapterData" data-id={{item.id}} @click="goDel(item.id)">
	  			<p class="firstp"><span>{{$index + 1}}</span>{{item.name}}</p>
	  			<p class="secondp">
	  				<span>{{item.finishedQuestions}}/{{item.totalQuestions}}</span>
	  				<span>难度</span>
	  				<rater :value.sync="item.significance" slot="value" active-color="#e15147" disabled></rater>
	  			</p>
	  		</li>
	  	</ul>
	  </div>
	</div>
  <no-message tip="暂无数据" v-show="showNoMessage"></no-message>
    <loading :show="loading"></loading>
    <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
	</div>
</template>

<script>
import Dialog from 'vux-components/dialog'
import headerNav from '../common/header.vue'
import noMessage from '../common/noMessage.vue'
import loading from 'vux-components/loading'
import rater from 'vux-components/rater'
import apiService from '../apiservice.js'
import toast from 'vux-components/toast'
export default {
  name: 'xPractice',
  components: {
    headerNav,
    noMessage,
    loading,
    rater,
    toast,
    Dialog
  },

  ready(){
    this.renderData();
  },

  data () {
    return {
//    studentNo: "170405002",
//    sysCode: "94a1533f-85a2-4c6a-8850-74a39b2b5ba9",
      studentNo: localStorage.getItem("studentNo"),
      sysCode: localStorage.getItem("sysCode"),
      specialty: localStorage.getItem("specialty"),
      backshow:true,
      show2:false,
      loading:false,
      selectValue:"",// 目录select 值的id
      successData:[],//目录select值 === data
      chapterData: [], //筛选目录对应的章节 === data
      warntext:"",  //alert框
      showNoMessage:true  // 暂无信息提示语
    }
  },


  methods: {
    // 获取筛选目录
   renderData:function(){
          this.loading = true;
          apiService.chapter({
              parentId:0,
              tags: "章节练习目录",
              sysCode: this.sysCode,
              studentNo: this.studentNo,
              specialty:this.specialty,
              type:"practice"
    }).then((data) =>{
              this.loading = false;
              this.successData = data.data.data;

              // 设置select框第一个
//              this.selectValue = data.data.data[0].id;
              this.selectValue = 0;
              this.getChapterDel();

              //console.log("后台获取到的目录data===>",data);
              this.classSelect = 0;
            },(err) =>{
              this.loading = false;
              this.show2 = true;
              this.warntext ="网络不给力,请稍后再试...";
              //console.log(err);
            })
   },

   //获取筛选目录对应的章节列表
    getChapterDel: function(){
      this.loading = true;
      // var obj = selectValue ? selectValue : this.selectValue;
      var params = {
        parentId:this.selectValue,
        tags: "",
        sysCode: this.sysCode,
        studentNo: this.studentNo,
        specialty:this.specialty,
        type:"practice"

      };
      if(this.selectValue == 0){
        params.dirType = "AllChapter";
      };
      //console.log("this.selectValue=======>",this.selectValue);
      //console.log(params);
            apiService.chapter(params).then((data) =>{
                  if(data.data.data.length != 0){
                    this.loading = false;
                    this.chapterData = data.data.data;
                    this.showNoMessage = false;

                  }else{
                    this.show2 = true;
                    this.loading = false;
                    this.warntext = "暂无数据";
                    this.chapterData = null;
                    this.showNoMessage = true;
                  };

                  },(err) =>{
                    this.loading = false;
                    this.show2 = true;
                    this.warntext ="网络不给力,请稍后再试...";
                    //console.log(err);
                })
        },

    goDel: function(id){
      // this.$router.go('/xPracticedel?id='+id);
      this.$router.go({name:'xPracticedel',params:{
        chapterId: id
      }});
    },

    goAllprt: function(id){
      // this.$router.go('/xPracticedel?id='+id);
      this.$router.go({name:'xPracticedel',params:{
        chapterId: ""
      }});
    },

    goprtTj: function(id){
       this.$router.go('/xprtTj');
    },

   popState:function(){
    var state = {
    title: "title",
    url: "#"
    };
    window.history.pushState(state, "title", "#");
   }

  }
}
</script>


<style lang="scss">
.page-xPractice{
  .contain{
    .contain-top{
      padding: .5rem;
      margin-top: .5rem;
      font-size: .8rem;
      margin-bottom: .5rem;
      .choose{
        #Screening{
          width: 7.5rem;
          height: 1.5rem;
          line-height: 1.5rem;
          font-size: .8rem;
          color: #999;
          border:1px solid #999;
          padding-left: .5rem;
          border-radius:.25rem;

          background: #fff url(../assets/images/icon_arrow_m.png) 96% no-repeat;
          background-size: .6rem .6rem;
          appearance: none;
          -webkit-appearance: none;
          -moz-appearance: none;
        }
      }
    }

    .contain-center{
      width: 100%;
      height: 3.25rem;
      background: #fff;

    .allPro{
      border-right: 1px solid #ddd;
    }

      span{
        background:url(../assets/images/icon_all.png) 30% center no-repeat;
        background-size: .8rem .8rem;
        width: 48%;
        margin: .5rem 0;
        height: 2.25rem;
        line-height: 2.25rem;
        display: inline-block;
        text-align: center;
        font-size: .8rem;
      }
      .wrongPro{
      background:url(../assets/images/icon_statistics.png) 18% center no-repeat;
      background-size: .8rem .8rem;
      }
    }

    .contain-foot{
      width: 100%;
      background: #fff;
      margin-top: .5rem;
      padding: 0 0 0 .5rem;

      ul{
        li:last-child{
          border: none;
        }
        li{
          list-style: none;
          font-size: .8rem;
          padding: .5rem 0;
          border-bottom: 1px solid #ddd;
          background:url(../assets/images/icon_arrow.png) 97% no-repeat;
          background-size: .4rem .6rem;

          .firstp{
            padding-right: .8rem;
            word-break: break-all;
            span{
              width: 1rem;
              height: 1rem;
              line-height: 1.1rem;
              text-align: center;
              color: #fff;
              background: #4489ca;
              border-radius: 50%;
              display: inline-block;
              margin-bottom: .1rem;
              margin-right: .5rem;
            }
          }

          .secondp{
            padding-left: 1rem;
            color: #b8b8b8;
            font-size: .6rem;

            .vux-rater .vux-rater-box{
              font-size: .8rem !important;
              width: .8rem !important;
              height: .8rem !important;
            }

            span:nth-child(2){
              margin-left: 2rem;
            }
          }


        }
      }

    }


  }

}
</style>




























