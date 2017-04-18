<template>
	<div>
	<!-- <x-header :left-options="{showBack: false}">首页</x-header> -->

	<navfooter></navfooter>
	</div>
</template>
<script>
import navfooter from './navFooter.vue'
export default {
		name: 'index',
		components: {
			navfooter
		}
	}
</script>
<style lang="scss"></style>