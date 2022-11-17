<template>
    <div class="total">
      <allHeader/>
      <div class="main">
          <router-view name="table">
          </router-view>
      </div>
    </div>
</template>

<script>
import allHeader from "@/components/allHeader";
export default {
  components: {allHeader},
  data(){
    return{

    }
  },
  methods:{

  }
}
</script>
<style scoped>
.main{
  height: 600px;
}
</style>