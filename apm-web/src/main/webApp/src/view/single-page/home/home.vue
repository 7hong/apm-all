<template>
  <div>
    <Row :gutter="20">
      <!-- <i-col :xs="12" :md="8" :lg="4" v-for="(infor, i) in inforCardData" :key="`infor-${i}`" style="height: 120px;padding-bottom: 10px;">
        <infor-card shadow :color="infor.color" :icon="infor.icon" :icon-size="36">
          <count-to :end="infor.count" count-class="count-style"/>
          <p>{{ infor.title }}</p>
        </infor-card>
      </i-col> -->
      <Col span="6" v-for="(infor, i) in inforCardData" :key="`infor-${i}`" style="height: 120px;padding-bottom: 10px;">
        <infor-card shadow :color="infor.color" :icon="infor.icon" :icon-size="36">
          <count-to :end="infor.count" count-class="count-style"/>
          <p>{{ infor.title }}</p>
        </infor-card>
      </Col>
    </Row>
    <Alert show-icon closable>
        欢迎使用APM控制台 V1.0版本
        <Icon type="ios-bulb-outline" slot="icon"></Icon>
        <template slot="desc">2019年4月18日 V1.0 本次更新主要修复了版本号不太专业的问题!</template>
    </Alert>
    <Row :gutter="20" style="margin-top: 10px;">
      <i-col span="8">
        <Card shadow>
          <chart-pie style="height: 500px;" :value="pieData" text="TPS占比(TOP10)"></chart-pie>
        </Card>
      </i-col>
      <i-col span="16">
        <Card shadow>
          <chart-bar style="height: 500px;" :value="barData" text="上周系统吞吐量"/>
        </Card>
      </i-col>
    </Row>
    <!-- <Row>
      <Card shadow>
        <example style="height: 310px;"/>
      </Card>
    </Row> -->
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
        { title: '总应用数', icon: 'md-locate', count: 3, color: '#19be6b' },
        { title: '我的应用', icon: 'md-share', count: 3, color: '#ed3f14' },
        { title: '总方法数', icon: 'md-help-circle', count: 98, color: '#ff9900' },
        { title: '总TPS', icon: 'md-map', count: 120, color: '#9A66E4' }
      ],
      pieData: [
        { value: 335, name: 'mall' },
        { value: 20, name: 'apm-server'}
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
