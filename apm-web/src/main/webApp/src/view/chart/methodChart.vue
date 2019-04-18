<template>
    <div>
        <Row :gutter="10" style="margin-top:10px">
            <Col span="4">
                <AppSelect :selected-value="app" select-width="100%" placeholder="请选择应用" @appSelect="appSelect"></AppSelect>
            </Col>
            <Col span="6">
                <MethodSelect :app-value="app" :selected-value="method" selectWidth="100%" placeholder="请选择方法" @methodSelect="methodSelect"></MethodSelect>
            </Col>
            <Col span="6">
                <DatePicker @on-ok="timeChange" :confirm="true"  type="datetimerange" v-model="time" format="yyyy-MM-dd HH:mm:ss" placeholder="请选择时间" style="width: 100%"></DatePicker>
            </Col>
            <Col span="3">
                <i-Switch size="large" v-model="flush">
                    <span slot="open">实时</span>
                    <span slot="close">关闭</span>
                </i-Switch>
            </Col>
        </Row>
        <Row style="margin-top:10px">
            <div id="chart1" class="chart"></div>
        </Row>
        <Row style="margin-top:10px">
            <div id="chart2" class="chart"></div>
        </Row>

    </div>
</template>

<script>
import AppSelect from '../../components/asm/AppSelect.vue';
import MethodSelect from '../../components/asm/MethodSelect.vue';
import {method_metric} from '@/api/data.js';
import {formatDate} from '../../libs/formatDate.js';
import echarts from 'echarts';
export default {
    name:'methodMetric',
    components:{AppSelect,MethodSelect},
    data() {
        return {
            app:'',
            method:'',
            chart1:null,
            chart2:null,
            lastTime:0,
            flush:false,
            stop:null,
            time:[new Date(new Date().getTime()-3600000),new Date(new Date().getTime()-60000)],
            option1:{
                title: {left: '1%',text: '实时RPS曲线'},
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
                    {name:"rps", type:"line",data:[]}
                ]
            },
            option2:{
                title: {left: '1%',text: '实时TP曲线'},
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
                    {name:"tp90", type:"line", symbol: 'none', smooth: true, data:[]},
                    {name:"tp99", type:"line", symbol: 'none', smooth: true, data:[]},
                    {name:"avg", type:"line", symbol: 'none', smooth: true, data:[]}
                ]
            }

        }
    },
    watch:{
        method:function(){
            if (this.stop) {
                clearInterval(this.stop);
            }
            this.loadData(this.getForm());
        },
        flush:function(fl){
            if (fl) {
                if (this.stop) {
                    clearInterval(this.stop);
                }
                this.stop = setInterval(()=>{
                        const f = {
                            app:this.app,
                            method:this.method,
                            startTime:this.lastTime+1,
                            endTime:this.lastTime+1100
                        }
                        this.loopData(f);
                    },1000)
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
                method:this.method,
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
                method:this.method,
                startTime:this.time[0].getTime(),
                endTime:this.time[1].getTime()
            }
            this.chart1.showLoading({text : '正在加载数据'});
            this.chart2.showLoading({text : '正在加载数据'});
            method_metric(f).then(res => {
                const data = res.data;
                const x = data.x;
                let times = [];
                x.forEach(t => {
                    this.lastTime = t;
                    const temp = formatDate(t, "MM-dd hh:mm:ss");
                    times.push(temp);
                })
                const rps = data.rps;
                const avg = data.avg;
                const tp90 = data.tp90;
                const tp99 = data.tp99;

                const names1 = ["rps"];
                const names2 = ["tp90","tp99","avg"];
                this.option1.legend.data = names1;
                this.option2.legend.data = names2;
                this.option1.xAxis[0].data = times;
                this.option2.xAxis[0].data = times;
                this.option1.series[0].data = rps;
                this.option2.series[0].data = tp90;
                this.option2.series[1].data = tp99;
                this.option2.series[2].data = avg;

                this.chart1.setOption(this.option1);
                this.chart2.setOption(this.option2);
                this.chart1.hideLoading();
                this.chart2.hideLoading();
                this.chart1.resize();
                this.chart2.resize();
                if (this.flush) {
                    this.stop = setInterval(()=>{
                        const f = {
                            app:this.app,
                            method:this.method,
                            startTime:this.lastTime+1,
                            endTime:this.lastTime+1100
                        }
                        this.loopData(f);
                    },1000)
                }

            })
        },
        loopData(f) {
            if (!this.checkForm()) {
                return ;
            }
            const curr = new Date().getTime();
            if (f['startTime'] < 10000) {
                f['startTime'] = new Date().getTime() - 1800000;
            }
            if (f.endTime > curr) {
                f['endTime'] = curr;
            }
            method_metric(f).then(res => {
                const data = res.data;
                const x = data.x;
                const rps = data.rps;
                const avg = data.avg;
                const tp90 = data.tp90;
                const tp99 = data.tp99;

                const names1 = ["rps"];
                const names2 = ["tp90","tp99","avg"];
                this.option1.legend.data = names1
                this.option2.legend.data = names2
                x.forEach(t => {
                    this.lastTime = t;
                    const temp = formatDate(t, "MM-dd hh:mm:ss");
                    if (this.option1.xAxis[0].data.length > 100) {
                        this.option1.xAxis[0].data.shift()
                    }
                    this.option1.xAxis[0].data.push(temp)
                    if (this.option2.xAxis[0].data.length >100) {
                        this.option2.xAxis[0].data.shift()
                    }
                    this.option2.xAxis[0].data.push(temp)
                });

                rps.forEach(r => {
                    if (this.option1.series[0].data.length > 100) {
                        this.option1.series[0].data.shift()
                    }
                    this.option1.series[0].data.push(r);
                })
                tp90.forEach(t => {
                    if (this.option2.series[0].data.length > 100) {
                        this.option2.series[0].data.shift()
                    }
                    this.option2.series[0].data.push(t);
                });
                tp99.forEach(t => {
                    if (this.option2.series[1].data.length > 100) {
                        this.option2.series[1].data.shift()
                    }
                    this.option2.series[1].data.push(t);
                });
                avg.forEach(t => {
                    if (this.option2.series[2].data.length > 100) {
                        this.option2.series[2].data.shift()
                    }
                    this.option2.series[2].data.push(t);
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
        methodSelect(method) {
            this.method = method;
        },
        checkForm(){
            if (this.app != "" && this.method != "") {
                return true;
            }
            return false;
        }
    },
     beforeDestroy(){
        if (this.chart1) {
            this.chart1.dispose();
        }
        if (this.chart2) {
            this.chart2.dispose();
        }
        if (this.stop) {
            clearInterval(this.stop);
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
