<template>
    <Select v-model="methodSelect" :style="{width: selectWidth}" :placeholder="placeholder"
            @on-change="methodSelectChange" filterable clearable>
        <Option v-for="(item,index) in serviceList" :value="item" :key="index">{{item}}</Option>
    </Select>
</template>

<script>
  import {methods} from '@/api/data.js'

  export default {
    name: "MethodSelect",
    props: {
      appValue: String,
      selectedValue: String,
      selectWidth: String,
      placeholder: String
    },
    data() {
      return {
        serviceList: [],
        methodSelect: "",
        currApp:""
      }
    },
    created() {
      this.bindData();
    },
    methods: {
      bindData() {
        this.methodSelect = ""
        if (this.currApp != "") {
          methods(this.currApp).then((response) => {
            const result = response.data;
            this.serviceList = result;
            this.methodSelect = this.selectedValue;
          });
        }
      },
      methodSelectChange() {
        this.$emit("methodSelect", this.methodSelect);//传递给父组件
      }
    },
    watch: {
      appValue: function () {
        console.log("appValue appValue")
        this.currApp = this.appValue;
        this.bindData();
      },
      serviceList: function (val) {
        // this.methodSelect = this.selectedValue;
      }
    }
  }
</script>

<style scoped>

</style>
