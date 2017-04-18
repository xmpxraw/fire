<template>
	<div class="page-noMessage">
      <div class="noMessage">{{tip}}</div>
	</div>
</template>
<script>
export default {
		name: 'noMessage',
		components: {
		
		},
		props: {
		 	tip:String,
		 	
        },

		data () {
		    return {
	        }
       },

       methods: {

       }
	}
</script>
<style lang="scss">
.page-noMessage{
	.noMessage{
    width: 100%;
    height: 4rem;
    line-height: 4rem;
    text-align:center;
    color: black;
    font-size: .8rem;
   }
 }
</style>