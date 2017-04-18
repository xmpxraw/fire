<template>
  <div class="page-xprtTj">
    <header-nav title="练习统计" :backshow="backshow"></header-nav>
    <div class="contain">
      <div class="contain-top">
        <p>练习统计情况</p>
        <ul>
          <li>
            <p>
              <span>{{allData.prtProgress}}%</span><br />
              <span>练习进度</span>
            </p>
          </li>
          <li>
            <p>
              <span>{{allData.prtTrue}}%</span><br />
              <span>答题正确率</span>
            </p>
          </li>
        </ul>
      </div>
      <div class="contain-center">
        <p>练习进度</p>
        <div class="choose">
          <span>目录：</span>
          <select name="choose" id="Screening" @change="getChapterDel"  v-model="selectValue">
            <option data-id="-1" value="-1">全部</option>
            <option v-for="item in successData" data-id={{item.id}} value="{{item.id}}">{{item.name}}</option>
          </select>
        </div>

        <div class="wrongSay">
          <ul>
            <li v-for="item in chapterData" data-id={{item.id}}>
              <p class="wrongTitle"><span>{{$index+1}}</span>{{item.chapterName}}</p>
              <p>进度: {{item.progress}}%({{item.finishedQuestions}}/{{item.totalQuestions}})</p>
              <div class="progress">
                <div class="progress-detail" style="width:{{item.progress}}%"></div>
              </div>

              <p class="trueAns">正确率: {{item.tureProgress}}%({{item.rightQuestions}}/{{item.totalQuestions}})</p>
              <div class="progress">
                <div class="progress-true" style="width:{{item.tureProgress}}%"></div>
              </div>


            </li>
          </ul>
        </div>

      </div>
    </div>


    <loading :show="loading"></loading>
    <no-message tip="暂无数据" v-show="showNoMessage"></no-message>
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
    name: 'xprtTj',
    components: {
      headerNav,
      loading,
      toast,
      noMessage,
    },
    ready(){
      this.renderData();
    },
    data () {
      return {
        //    studentNo: "170405002",
//      sysCode: "94a1533f-85a2-4c6a-8850-74a39b2b5ba9",
        studentNo: localStorage.getItem("studentNo"),
        sysCode: localStorage.getItem("sysCode"),
        specialty: localStorage.getItem("specialty"),
        backshow:true,
        loading:false,//控制是否出现loading
        show2:false,//控制toast的出现
        warntext:"",
        successData:[],//目录select值 === data
        chapterData:[],//每章节的进度
        allData:[], //所有章节的统计
        showNoMessage:false  // 暂无信息提示语
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

        }).then((data) =>{
          this.loading = false;
          this.successData = data.data.data;

        // 设置select框第一个
        // this.selectValue = data.data.data[0].id;
        this.selectValue = -1;
        this.getChapterDel();

        console.log("后台获取到的目录data===>",data);
        this.classSelect = 0;
      },(err) =>{
          this.loading = false;
          this.show2 = true;
          this.warntext ="网络不给力,请稍后再试...";
          console.log(err);
        })
      },

      //获取筛选目录对应的章节列表
      getChapterDel: function(){
        // var obj = selectValue ? selectValue : this.selectValue;
        console.log("this.selectValue=======>",this.selectValue);
        this.loading = true;
        apiService.getChapterProgress({
          parentId:this.selectValue,
          tags:"章节练习目录",
          sysCode: this.sysCode,
          studentNo: this.studentNo,
          specialty:this.specialty,

        }).then((data) =>{
          this.loading = false;
        //总的统计（总的进度）
        this.allData = data.data.data.total;
        if(this.allData.totalQuestions!==0){
          this.allData.prtProgress = (this.allData.finishedQuestions/this.allData.totalQuestions)*100;
          this.allData.prtProgress = this.allData.prtProgress.toFixed(2);
          if(this.allData.prtProgress == 0){
            this.allData.prtProgress = 0;
          }
        }else{
          this.allData.prtProgress = 0;
        }

        if(this.allData.totalQuestions!==0){
          this.allData.prtTrue = (this.allData.rightQuestions/this.allData.totalQuestions)*100;
          this.allData.prtTrue = this.allData.prtTrue.toFixed(2);
          if(this.allData.prtTrue == 0){
            this.allData.prtTrue = 0;
          }
        }else{
          this.allData.prtTrue = 0;
        }
          if(data.data.data.chapterStatis != 0){
          this.chapterData = data.data.data.chapterStatis;
            console.log("----------chapterData-------");
            this.logJSON(this.chapterData);
            this.showNoMessage = false;
            //计算每章节的进度
            for(var i= 0,length=this.chapterData.length;i<length;i++){
              if(this.chapterData[i].totalQuestions!==0){
//               //进度
                this.chapterData[i].progress = (this.chapterData[i].finishedQuestions/this.chapterData[i].totalQuestions)*100;
                this.chapterData[i].progress = this.chapterData[i].progress.toFixed(2);

                //正确率
                this.chapterData[i].tureProgress = (this.chapterData[i].rightQuestions/this.chapterData[i].totalQuestions)*100;
                this.chapterData[i].tureProgress = this.chapterData[i].tureProgress.toFixed(2);
              }else{
                this.chapterData[i].progress = 0;
                this.chapterData[i].tureProgress = 0;
              }

            }
        }else{
          this.show2 = true;
          this.warntext = "暂无数据";
          this.chapterData = null;
          this.showNoMessage = true;
        };
        console.log("isdata");
        this.logJSON(data);

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

      // 输出专用
      logJSON: function (data) {
        function innerLog(data) {
          var temp = {};
          for (var p in data) {
            //console.log(p + "    " + data[p] + "    " + typeof data[p]);
            if (typeof data[p] == "object") temp[p] = innerLog(data[p]);
            else temp[p] = data[p];
          }
          return temp;
        }

        console.log(innerLog(data));
      },


    },



  }
</script>
<style lang="scss">
  .page-xprtTj{
  .contain{
  .contain-top{
    padding: .5rem;
    background: #fff;
    font-size: .8rem;

  ul{
    margin-top: .75rem;
    margin-bottom: .75rem;
    overflow: hidden;
  li: first-child{
    margin-left: 0;
  }
  li{
    height: 4.3rem;
    line-height: .8rem;
    width: 4.3rem;
    margin-left: 3rem;
    display: inline-block;
    border-radius: 50%;
    background: #efefef;
    list-style: none;
    float: left;
    color: #4489ca;

  p{
    text-align: center;
    padding-top: 1.4rem;
  }

  span{
    font-size: .6rem;
  }

  span:first-child{
    font-size: .8rem;
    letter-spacing: -1px;
  }
  }
  }
  }

  .contain-center{
    padding: .5rem;
    margin-top: .5rem;
    background: #fff;
    font-size: .8rem;
    margin-bottom: 4rem;

  .choose{

  #Screening{
    width: 7.5rem;
    height: 1.5rem;
    line-height: 1.5rem;
    font-size: .8rem;
    color: #999;
    border:1px solid #999;
    margin: 1rem 0;
    background:url(../assets/images/icon_arrow_m.png) 96% no-repeat;
    background-size: .6rem .6rem;
  }
  }

  .wrongSay{
    font-size: .8rem;

  ul{
  li{
    list-style: none;
    border-top: 1px solid #ddd;
    padding: 1rem 0 1.5rem 0;

  .wrongTitle{
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
    word-break: break-word;

  span{
    width: 1rem;
    height: 1rem;
    line-height: 1rem;
    border-radius: 50%;
    background: #4489ca;
    display: inline-block;
    text-align: center;
    color: #fff;
    margin-right: .25rem;

  }
  }

  p:nth-child(2),.trueAns{
    color: #a0a0a0;
    text-align: center;
    margin-top: 1rem;
    font-size: .6rem;
  }

  .progress{
    width: 100%;
    height: 1rem;
    background: #d9d9d9;
    border-radius: .5rem;
    padding: .15rem;

  .progress-detail,.progress-true{
    height: .75rem;
    border-radius: .5rem;
    margin: 0;
    background: repeating-linear-gradient(140deg, #ea9810, #ea9810 3px, #f3a51d 0px, #f3a51d 6px) left center;
  }

  .progress-true{
    background: repeating-linear-gradient(140deg, #86be75, #86be75 3px, #88c575 0px, #88c575 6px) left center;
  }

  }
  }
  }
  }

  }

  }



  }
</style>




























