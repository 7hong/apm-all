<template>
    <div>
        <Row :gutter="10" style="margin-top:10px">
            <Col span="4">
                <AppSelect :selected-value="app" select-width="100%" placeholder="请选择应用" @appSelect="appSelect"></AppSelect>
            </Col>
            <Col span="5">
                <DatePicker @on-ok="timeChange" :confirm="true" type="datetimerange" v-model="time" format="yyyy-MM-dd HH:mm:ss" placeholder="请选择时间" style="width: 100%"></DatePicker>
            </Col>
            <Col span="3">
                <i-Switch size="large" v-model="flush">
                    <span slot="open">实时</span>
                    <span slot="close">关闭</span>
                </i-Switch>
            </Col>
        </Row>
        <Row style="margin-top:10px">
            <div id="chart2" class="chart"></div>
        </Row>
        <Row style="margin-top:10px">
            <div id="chart1" class="chart"></div>
        </Row>
    </div>
</template>

<script>
import AppSelect from '../../components/asm/AppSelect.vue';
import {mem_metric} from '@/api/data.js';
import {formatDate} from '../../libs/formatDate.js';
import echarts from 'echarts';
export default {
    name:'memMetric',
    components:{AppSelect},
    data() {
        return {
            app:'',
            chart1:null,
            chart2:null,
            lastTime:0,
            flush:false,
            dur:11000,//时间间隔
            time:[new Date(new Date().getTime()-3600000),new Date(new Date().getTime()-60000)],
            names1:["nonHeapInit","nonHeapUsed","nonHeapCommitted","nonHeapMax"],
            names2:["heapInit","heapUsed","heapCommitted","heapMax"],
            option1:{
                title: {left: '1%',text: '非堆内存监控'},
                color: ["#d38e87","#76feaa","#E1E100","#fa89e9","#9400D3","#5cb4ff","#9393FF","#00EC00"],
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: [{
                    data: []
                }],
                yAxis: [{
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: '#999',
                            type: 'dashed'
                        }
                    },
                    type : 'value'
                }],
                grid: {left:'1%',right:'15px',top:'20%',bottom: '5%',containLabel:true},
                legend: {data: []},
                series: [
                    {name:"nonHeapInit", type:"line",data:[]},
                    {name:"nonHeapUsed", type:"line",data:[]},
                    {name:"nonHeapCommitted", type:"line",data:[]},
                    {name:"nonHeapMax", type:"line",data:[]}
                ]
            },
             option2:{
                title: {left: '1%',text: '堆内存监控'},
                color: ["#d38e87","#76feaa","#E1E100","#fa89e9","#9400D3","#5cb4ff","#9393FF","#00EC00"],
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: [{
                    data: []
                }],
                yAxis: [{
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: '#999',
                            type: 'dashed'
                        }
                    },
                    type : 'value'
                }],
                grid: {left:'1%',right:'15px',top:'20%',bottom: '5%',containLabel:true},
                legend: {data: []},
                series: [
                    {name:"heapInit", type:"line",data:[]},
                    {name:"heapUsed", type:"line",data:[]},
                    {name:"heapCommitted", type:"line",data:[]},
                    {name:"heapMax", type:"line",data:[]}
                ]
            }

        }
    },
    watch:{
        app:function(){
            this.loadData(this.getForm());
        },
        flush:function(fl){
            if (this.flush) {
                if (this.stop) {
                    clearInterval(this.stop);
                }
                this.stop = setInterval(() => {
                    const f = {
                        app:this.app,
                        startTime:this.lastTime+1,
                        endTime:this.lastTime+this.dur
                    }
                    this.loopData(f);
                },10000)
            } else {
                if (this.stop) {
                    clearInterval(this.stop);
                }
            }
        }
    },
    created() {

    },
    mounted(){
        this.chart1 = echarts.init(document.getElementById('chart1'),'dark');
        this.chart2 = echarts.init(document.getElementById('chart2'),'dark');
    },
    methods:{
        timeChange(){
            if (this.stop) {
                clearInterval(this.stop);
            }
            this.flush = true;
            this.loadData(this.getForm());
        },
        getForm(){
            const f = {
                app:this.app,
                startTime:this.time[0].getTime(),
                endTime:this.time[1].getTime()
            }
            return f;
        },
        loadData() {
             if (!this.checkForm()) {
                return ;
            }
            const f = {
                app:this.app,
                startTime:this.time[0].getTime(),
                endTime:this.time[1].getTime()
            }
            this.chart1.showLoading({text : '正在加载数据'});
            this.chart2.showLoading({text : '正在加载数据'});
            mem_metric(f).then(res => {
                const data = res.data;
                const x = data.time;
                let times = [];
                x.forEach(t => {
                    this.lastTime = t;
                    const temp = formatDate(t, "MM-dd hh:mm:ss");
                    times.push(temp);
                })

                this.option1.legend.data = this.names1;
                this.option2.legend.data = this.names2;
                this.option1.xAxis[0].data = times;
                this.option2.xAxis[0].data = times;
                for(var i=0;i<4;i++) {
                    this.option1.series[i].data = data[this.names1[i]];
                    this.option2.series[i].data = data[this.names2[i]];
                }
                this.chart1.setOption(this.option1);
                this.chart2.setOption(this.option2);
                this.chart1.hideLoading();
                this.chart2.hideLoading();
                this.chart1.resize();
                this.chart2.resize();
                if (this.flush) {
                    if (this.stop) {
                        clearInterval(this.stop);
                    }
                    this.stop = setInterval(() => {
                        const f = {
                            app:this.app,
                            startTime:this.lastTime+1,
                            endTime:this.lastTime+this.dur
                        }
                        this.loopData(f);
                    },10000)
                }

            })
        },
        loopData(f) {
            if (!this.checkForm()) {
                return ;
            }
            const curr = new Date().getTime();
            if (f['startTime'] < 10000) {
                f['startTime'] = new Date().getTime() - 3600000;
            }
            if (f.endTime > curr) {
                f['endTime'] = curr;
            }
            mem_metric(f).then(res => {
                const data = res.data;
                const x = data.time;
                const that = this;
                x.forEach(t => {
                    this.lastTime = t;
                    const temp = formatDate(t, "MM-dd hh:mm:ss");
                    if (this.option1.xAxis[0].data.length > 100) {
                        this.option1.xAxis[0].data.shift()
                        this.option1.xAxis[0].data.push(temp);
                        this.option2.xAxis[0].data.shift()
                        this.option2.xAxis[0].data.push(temp);
                        for(var i=0;i<4;i++) {
                            this.option1.series[i].data.shift()
                            this.option2.series[i].data.shift()
                            const ddr1 = data[that.names1[i]];
                            const ddr2 = data[that.names2[i]];
                            for(var i = 0; i<ddr1.length;i++) {
                                this.option1.series[i].data.push(ddr1[i]);
                                this.option2.series[i].data.push(ddr2[i]);
                            }
                        }
                    } else {
                        this.option1.xAxis[0].data.push(temp);
                        this.option2.xAxis[0].data.push(temp);
                        for(var i=0;i<4;i++) {
                            const ddr1 = data[that.names1[i]];
                            const ddr2 = data[that.names2[i]];
                            for(var i = 0; i<ddr1.length;i++) {
                                this.option1.series[i].data.push(ddr1[i]);
                                this.option2.series[i].data.push(ddr2[i]);
                            }
                        }
                    }
                });
                this.chart1.setOption(this.option1);
                this.chart2.setOption(this.option2);
                this.chart1.resize();
                this.chart2.resize();
            })
        },
        appSelect(app){
            this.app = app;
        },
        checkForm(){
            if (this.app != "") {
                return true;
            }
            return false;
        }
    },
    beforeDestroy(){
        if (this.stop) {
            clearInterval(this.stop);
        }
        if (this.chart1) {
            this.chart1.dispose();
        }
        if (this.chart2) {
            this.chart2.dispose();
        }
    }

}
</script>
<style scoped>
.chart{
    width: 100%;
    height: 400px;
}
</style>
