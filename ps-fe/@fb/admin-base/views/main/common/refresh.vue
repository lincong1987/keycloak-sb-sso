<template>
	<div></div>
</template>

<script>
	export default {
		name: 'refresh',
		beforeRouteEnter (to, from, next) {
 

			next(vm => {
				if (Object.keys(to.params).length) {
					vm.$router.replace({
						name: from.name,
						params: to.params
					})
				} else {
					vm.$router.replace({
						path: from.path,
						query: to.query
					})
				}
			})
		}
	}
</script>
