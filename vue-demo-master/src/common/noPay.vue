<template>
	<div class="noPay-main">
	<header-nav title="温馨提示" :backshow="backshow"></header-nav>
   <div class="noPay-contain">
   	   您未付费，不能使用该功能！
   </div>
   <div class="j-cancle" @click='goIndex()'>点击回到首页</div>
	</div>
</template>
<script>
import headerNav from '../common/header.vue'
import Dialog from 'vux-components/dialog'
import XButton from 'vux-components/x-button'
import loading from 'vux-components/loading'
import toast from 'vux-components/toast'
import Client from '../client.js'
export default {
	name: 'noPay',
	components: {
	    Dialog,
	    XButton,
	    loading,
      headerNav
	  },
   ready(){
    // this.powerState(); //监听返回键
  },
	data () {
	    return {
	      showNoScroll:false,
        backshow:false,
	      showitem:true,
	      noPayData:[],
	      loading:true,
	    }
  },
  methods: {
  	goIndex:function(){
      this.$router.go('/schoolIndex');
   },
   powerState:function(){
    var state = { 
    title: "title", 
    url: "#"
    }; 
    window.history.pushState(state, "title", "#"); 
    window.addEventListener("popstate", function(e) {
                wx.closeWindow();
            }, false);
   },
  },
  }
</script>
<style lang="scss">
.noPay-main{
  background: #ebebeb;
  height:auto;
  padding-bottom: 2.3rem;
  
}
.noPay-header{
  width: 100%;
  padding: 0 .5rem;
  height: 2.95rem;
  background: #f6f5f5;
  -webkit-box-shadow: 0px 3px 5px #ccc;
   box-shadow: 0px 3px 5px #ccc;

  .noPayDescribe{
  width:100%;
  height:100%;
  line-height: 2.95rem;
  background: url(../assets/images/line.png) repeat-x  center;
}
.describe{
  width:6rem;
  height:100%;
  line-height: 2.95rem;
  background:#f6f5f5;
  font-size: .9rem;
  color: #438ac9;
  font-weight: bold;
}
.describe span{
  display: inline-block;
  width:100%;
  height:2.95rem;
  line-height: 2.95rem;
  background: url(../assets/images/icon_face.png) no-repeat  .5rem .85rem;
  background-size: 1.1rem;
  padding-left:1.9rem;
}
}
.noPay-contain{
	width:100%;
    font-size: .85rem;
    padding:.5rem;
    line-height: 1.5rem;
    color:#333;
}
.noPay-main .j-cancle{
    width:90%;
    height:2.5rem;
    line-height: 2.5rem;
    text-align: center;
    color: #fff;
    font-size: .9rem;
    background:#4389ca;
    border-radius: .25rem;
    position: absolute;
    bottom:10rem;
    left: 50%;
    margin-left: -45%;
  }
</style>