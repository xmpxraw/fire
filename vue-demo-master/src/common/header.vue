<template>
	<div class="page-header">
      <header><span v-show="backshow" class="back" @click="back()"></span>{{title}}</header>
	</div>
</template>
<script>
import store from '../store.js'
const commit = store.commit || store.dispatch;
export default {
		name: 'headerNav',
		components: {
		
		},
		props: {
		 	title:String,
		 	backshow:Boolean,
        },

		data () {
		    return {
	        }
       },
       methods: {
       	 back:function(){
            // this.$emit('on-back')
            commit('UPDATE_ISNIGHT',false);//返回消除夜间模式
			      window.history.back();
			   },
       }
	}
</script>
<style lang="scss">
.page-header{
	header{
    width: 100%;
    height: 2.3rem;
    background: #438ac9;
    color: #fff;
    font-size: .85rem;
    text-align: center;
    line-height: 2.3rem;
    position: relative;
    .back{
      height: 2.3rem;
      width: 1.75rem;
      display: inline-block;
      position: absolute;
      left: 0;
      top: 0;
      background: url(../assets/images/icon_back.png) center no-repeat;
      background-size: .575rem .95rem;

    }
  }
 }
</style>