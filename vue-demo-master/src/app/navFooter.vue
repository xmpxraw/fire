<template>
	<div class="nav-footer">
	<tabbar :index.sync="selectIndex">
      <tabbar-item v-for="item in navList" @click="select($index)" v-bind:link="item.path">
        <img slot="icon" v-bind:src="item.img">
        <span slot="label">{{item.text}}</span>
      </tabbar-item>
    </tabbar>

	</div>
</template>
<script>
import XHeader from 'vux-components/x-header'
import {Tabbar,TabbarItem} from 'vux-components/tabbar'
export default {
		name: 'navfooter',
		components: {
			XHeader,
			Tabbar,
			TabbarItem
		},
		 props: {
		 	navList:Array,
		 	selectIndex:Number
        //   navList:{
        //   	 type:Array,
        //   },
        //   selectIndex: {
		      // type: Number,
		      // default: 1
        //  },
        },
		data () {
		    return {
		  
	    }
  },
	}
</script>
<style lang="scss">
  .nav-footer{
    background: red;
  }
</style>


