<template>
  <div>
    <Row :gutter="20">
      <i-col :xs="12" :md="8" :lg="4" v-for="(infor, i) in inforCardData" :key="`infor-${i}`" style="height: 120px;padding-bottom: 10px;">
        <infor-card shadow :color="infor.color" :icon="infor.icon" :icon-size="36">
          <count-to :end="infor.count" count-class="count-style"/>
          <p>{{ infor.title }}</p>
        </infor-card>
      </i-col>
    </Row>
    <Row :gutter="20" style="margin-top: 10px;">
      <i-col :md="24" :lg="8" style="margin-bottom: 20px;">
        <Card shadow>
          <chart-pie style="height: 300px;" :value="pieData" text="用户访问来源"></chart-pie>
        </Card>
      </i-col>
      <i-col :md="24" :lg="16" style="margin-bottom: 20px;">
        <Card shadow>
          <chart-bar style="height: 300px;" :value="barData" text="最近十分钟系统吞吐量"/>
        </Card>
      </i-col>
    </Row>
    <Row>
      <Card shadow>
        <example style="height: 310px;"/>
      </Card>
    </Row>
  </div>
</template>

<script>
import InforCard from '_c/info-card'
import CountTo from '_c/count-to'
import { ChartPie, ChartBar } from '_c/charts'
import Example from './example.vue'
import {topData} from '@/api/data.js'
export default {
  name: 'home',
  components: {
    InforCard,
    CountTo,
    ChartPie,
    ChartBar,
    Example
  },
  data () {
    return {
      inforCardData: [
        { title: '总应用数', icon: 'md-locate', count: 0, color: '#19be6b' },
        { title: '总方法数', icon: 'md-help-circle', count: 0, color: '#ff9900' },
        { title: 'Kafka吞吐量', icon: 'md-map', count: 0, color: '#9A66E4' },
        { title: '总吞吐量', icon: 'md-share', count: 0, color: '#ed3f14' }
      ],
      pieData: [
        { value: 335, name: 'mall' }
      ],
      barData: {
        Mon: 13253,
        Tue: 34235,
        Wed: 26321,
        Thu: 12340,
        Fri: 24643,
        Sat: 1322,
        Sun: 1324
      }
    }
  },
  created () {

  },
  mounted () {
    //
  },
  methods:{
    loadData(){
      topData().then(res => {

      })
    }
  }
}
</script>

<style lang="less">
.count-style{
  font-size: 50px;
}
</style>
