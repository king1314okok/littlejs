layui.define(function (e) {
    layui.use(['echarts', 'layer'], function () {
        var myChart = echarts.init(document.getElementById('saleChart'));
        myChart.setOption({
            title: {
                text: '花卉交易量'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:['订单交易量']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['前6日','前5日','前4日','前3日','前2日','前1日','今日']
            },
            yAxis: {},
            series: []
        });
        myChart.showLoading();
        var xValue = [];
        $.ajax({
            url: '/gatewayOrder/getWeekSale',
            type: 'post',
            dataType: 'json',
            async: 'true',
            success: function (result) {
                if (result) {
                    for(var i=0;i<result.obj.length;i++){
                        xValue.push(result.obj[i]);
                    }
                    myChart.hideLoading();
                    myChart.setOption({
                        series: [{
                            name: '订单交易量',
                            type: 'line',
                            stack: '总量',
                            data: xValue
                        }]
                    });
                }
            },
            error: function () {
                layer.msg('图表加载失败');
                myChart.hideLoading();
            }
        });
    });
    e('charts');
});