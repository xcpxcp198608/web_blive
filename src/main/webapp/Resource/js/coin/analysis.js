/**
 * Created by patrick on 2018/5/4.
 */
$(function () {
    var currentYear = getCurrentYear();
    var currentMonth = getCurrentMonth();


    $('#currentDate').html(currentYear + "-" + currentMonth);
    analysis.chart.month(currentYear, currentMonth);
    analysis.chart.days(currentYear, currentMonth);





    $('#btPrevious').click(function () {
        currentMonth --;
        if(currentMonth < 1){
            currentYear --;
            currentMonth = 12;
        }
        $('#currentDate').html(currentYear + "-" + currentMonth);
        analysis.chart.month(currentYear, currentMonth);
        analysis.chart.days(currentYear, currentMonth);
    });

    $('#btNext').click(function () {
        currentMonth ++;
        if(currentMonth > 12){
            currentYear ++;
            currentMonth = 1;
        }
        $('#currentDate').html(currentYear + "-" + currentMonth);
        analysis.chart.month(currentYear, currentMonth);
        analysis.chart.days(currentYear, currentMonth);
    })

});



var analysis = {

    value: {
        userId: function () {
            return $('#userId').html()
        }
    },

    chart:{
        month: function (year, month) {
            var chartMonth = echarts.init(document.getElementById('chart_month'));

            var url = baseUrl + '/coins/chart/' + analysis.value.userId() + '/' + year + '/' + month;
            $.get(url, { },function(result){
                if(result.code === 200){
                    var monthData = {'charge': 0, 'income': 0, 'consume': 0};
                    monthData['charge'] = result.data['chargeCoins'];
                    monthData['income'] = result.data['incomeCoins'];
                    monthData['consume'] = result.data['consumeCoins'];
                    chartMonth.setOption(createMonthOption(monthData));
                }
            });

            function createMonthOption(monthData) {
                return {
                    title: {
                        text: 'Monthly',
                        textStyle: {
                            align: 'middle',
                            color: '#f1dbb6'
                        }
                    },
                    backgroundColor: '#161616',
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        // x: 'right',
                        right: 5,
                        bottom: 5,
                        textStyle:{
                            color: 'rgba(255, 255, 255, 0.8)'
                        },
                        data:['charge','income','consume']
                    },
                    series: [
                        {
                            name:'Monthly',
                            type:'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: true,
                                    position: 'outside',
                                    formatter: '{b}: {c}'
                                },
                                emphasis: {
                                    show: true,
                                    position: 'center',
                                    textStyle: {
                                        fontSize: '20',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            color: ['#50b4c2','#29c131', '#9748d5'],
                            data:[
                                {value: monthData['charge'], name:'charge'},
                                {value: monthData['income'], name:'income'},
                                {value: monthData['consume'], name:'consume'}
                            ]
                        }
                    ]
                };
            }


        },

        days: function (year, month) {
            var chartDays = echarts.init(document.getElementById('chart_days'));

            var url = baseUrl + '/coins/chart/days/' + analysis.value.userId() + '/' + year + '/' + month;
            $.get(url, { },function(result){
                if(result.code === 200){
                    var list = result['dataList'];

                    var days = [];
                    var incomeCoins = [];
                    var chargeCoins = [];
                    var consumeCoins = [];

                    var d = getDaysOfCurrentMonth(year, month);
                    var length = list.length;

                    for(var i = 0; i < d; i ++){
                        var incomes = 0;
                        var charges = 0;
                        var consumes = 0;
                        for(var j = 0; j < length; j ++) {
                            var item = list[j];
                            if(item['day'] === i + 1){
                                if(item['type'] === 3){
                                    charges += item['coins']
                                }else if(item['type'] === 2){
                                    incomes += item['coins']
                                }else if(item['type'] === 1){
                                    consumes += item['coins']
                                }
                            }
                        }
                        days.push((i + 1).toString());
                        if(incomes === 0){
                            incomes = ''
                        }
                        if(charges === 0){
                            charges = ''
                        }
                        if(consumes === 0){
                            consumes = ''
                        }
                        incomeCoins.push(incomes);
                        chargeCoins.push(charges);
                        consumeCoins.push(consumes);
                    }
                    chartDays.setOption(createDaysOption(days.reverse(), incomeCoins.reverse(),
                        chargeCoins.reverse(), consumeCoins.reverse()));
                }
            });


            function createDaysOption(days, incomeCoins, chargeCoins, consumeCoins) {
                return {
                    title: {
                        text: 'Daily',
                        textStyle: {
                            align: 'middle',
                            color: '#f1dbb6'
                        }
                    },
                    backgroundColor: '#161616',
                    // tooltip : {
                    //     trigger: 'axis',
                    //     axisPointer : {
                    //         type : 'line'   // 默认为直线，可选为：'line' | 'shadow'
                    //     }
                    // },
                    legend: {
                        data: ['income', 'charge','consume'],
                        textStyle:{
                            color: 'rgba(255, 255, 255, 0.8)'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    textStyle:{
                        color: 'rgba(255, 255, 255, 0.8)'
                    },
                    xAxis:  {
                        type: 'value'
                    },
                    yAxis: {
                        type: 'category',
                        data: days
                    },
                    series: [
                        {
                            name: 'income',
                            type: 'bar',
                            stack: 'total',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideRight'
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#56b90c',
                                    shadowBlur: 200,
                                    shadowColor: 'rgba(0, 0, 0, 0.7)'
                                }
                            },
                            data: incomeCoins
                        },
                        {
                            name: 'charge',
                            type: 'bar',
                            stack: 'total',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideRight'
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#ffa532',
                                    shadowBlur: 200,
                                    shadowColor: 'rgba(0, 0, 0, 0.7)'
                                }
                            },
                            data: chargeCoins
                        },
                        {
                            name: 'consume',
                            type: 'bar',
                            stack: 'total',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideRight'
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: '#ce426a',
                                    shadowBlur: 200,
                                    shadowColor: 'rgba(0, 0, 0, 0.7)'
                                }
                            },
                            data: consumeCoins
                        }
                    ]
                };
            }
        }

    }

};