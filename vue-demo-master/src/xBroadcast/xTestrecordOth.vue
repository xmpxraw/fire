<template>
  <div class="page-xTestrecordOth">
    <header-nav title="播放记录" :backshow="backshow"></header-nav>
    <div class="content">
    <no-message tip="暂无播放记录" v-if="showNoMessage"></no-message>
    <ul v-if="!showNoMessage">
      <li v-for="item in successData" @click="goDetail(item.directory)">
        <p>{{item.chapter}}</p>
        <p>{{item.updateTime}} 观看过</p>
      </li>
    </ul> 
    <loading :show="loading"></loading>
    <toast :show.sync="show2" type="text" :time="1000">{{warntext}}</toast>
  </div>
</template>

<style lang="scss">
  .page-xTestrecordOth{
    .content{
      background: #fff;
      ul{
        li{
          width: 100%;
          padding: 0rem 8% 1rem .8rem;
          background: url(../assets/images/btn_play.png) 96% no-repeat;
          background-size: 1.2rem 1.2rem;
          border-bottom: 1px solid #ccc;
          word-break: break-all;
          
          p:first-child{
            font-weight: 800;
            font-size: 1rem;
            overflow: hidden;
            text-overflow: ellipsis;
            word-break: break-word;
            display: -webkit-box;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
          }
          p{
             margin-bottom: .5rem;
             font-weight: 400;
             font-size: .8rem;
          }
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
import noMessage from '../common/noMessage.vue'
export default {
  name: 'xBroadcastRecord',
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
      successData:[],
      showNoMessage:false,
    }
  },
  methods: {
    goDetail:function(chapterId){
     this.$router.go({name:'videoDetail',params:{chapterId:chapterId}});
   },
   renderData:function(){
      this.loading = true;
      apiService.getVideo({
              code:window.localStorage.getItem("codeStatus"),
              JSESSIONID:window.localStorage.getItem("JSESSIONID"),
              studentNo:window.localStorage.getItem("studentNo"),
              sysCode:window.localStorage.getItem("sysCode"),
              // studentNo:"170401009",
              // sysCode:"94a1533f-85a2-4c6a-8850-74a39b2b5ba9",
         }).then((res) =>{
              this.loading = false;
              if(res.data.code == 200){
                  if(res.data.data.length > 0 ){
                      this.showNoMessage = false;
                      this.successData = res.data.data;
                    }else{
                      this.showNoMessage = true;
                  }
                }else{
                  this.show2 = true;
                  this.warntext =res.data.msg;
                }

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




























