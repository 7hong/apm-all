<template>
    <Select v-model="appSelect" :style="{width: selectWidth}" :placeholder="placeholder" @on-change="appSelectChange" filterable clearable>
        <Option v-for="(key,index) in appList" :value="key" :key="index">{{key}}</Option>
    </Select>
</template>

<script>
  import {apps} from '@/api/data.js'

  export default {
    name: "AppSelect",
    props: {
      selectedValue: String,
      selectWidth: String,
      placeholder: String
    },
    data() {
      return {
        appList:[],
        appSelect: ""
      }
    },
    created() {
      this.bindData();
    },
    methods: {
      bindData() {
        apps().then((response) => {
          const result = response.data;
          if (result) {
            this.appList = result;
            this.appSelect = this.selectedValue;
          }
        });
      },
      appSelectChange() {
        this.$emit("appSelect",this.appSelect);//传递给父组件
      }
    }
  }
</script>

<style scoped>

</style>
