/**
 * Created by patrick on 2018/5/4.
 */
$(function () {

    analysis.chart.days();
    analysis.chart.hours();
});



var analysis = {

    value: {
        userId: function () {
            return $('#userId').html()
        }
    },

    chart:{
        days: function () {
            var chartMonth = echarts.init(document.getElementById('chart_days'));

            var url = baseUrl + '/channels/chart/days/' + analysis.value.userId();
            $.get(url, { },function(result){
                if(result.code === 200){

                    var date = [];
                    var data = [];
                    var base = + new Date(2018,4,1);
                    var oneDay = 24 * 3600 * 1000;
                    var x = Math.abs(parseInt((base - new Date()) / 86400000));

                    var list = result['dataList'];
                    var length = list.length;
                    for (var i = 1; i <= x; i++) {
                        var now = new Date(base += oneDay);
                        var day = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/');
                        date.push(day);

                        var count = 0;
                        for(var j = 0; j < length; j ++){
                            var item = list[j];
                            if(item['day'] === day.toString()){
                                count += item['count'];
                            }
                        }
                        data.push(count);
                    }
                    chartMonth.setOption(getDaysOption(date, data));
                }
            });

            function getDaysOption(date, data){
                return {
                    tooltip: {
                        trigger: 'axis',
                        position: function (pt) {
                            return [pt[0], '10%'];
                        }
                    },
                    title: {
                        left: 'center',
                        text: 'Views of per day',
                        textStyle: {
                            align: 'middle',
                            color: '#f1dbb6'
                        }
                    },
                    textStyle:{
                        color: '#878787'
                    },
                    backgroundColor: '#151617',
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: date
                    },
                    yAxis: {
                        type: 'value',
                        boundaryGap: [0, '100%']
                    },
                    dataZoom: [
                        // {
                        //     type: 'inside',
                        //     start: 0,
                        //     end: 100
                        // },
                        {
                            start: 0,
                            end: 100,
                            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                            handleSize: '80%',
                            handleStyle: {
                                color: '#fff',
                                shadowBlur: 3,
                                shadowColor: '#64686d',
                                shadowOffsetX: 2,
                                shadowOffsetY: 2
                            }
                        }],
                    series: [
                        {
                            name:'viewers',
                            type:'line',
                            smooth:true,
                            symbol: 'none',
                            sampling: 'average',
                            itemStyle: {
                                normal: {
                                    color: '#29f5fc'
                                }
                            },
                            areaStyle: {
                                normal: {
                                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                        offset: 0,
                                        color: '#f2daf7'
                                    }, {
                                        offset: 1,
                                        color: '#f61e67'
                                    }])
                                }
                            },
                            data: data
                        }
                    ]
                };
            }
        },


        hours: function () {
            var chartHour = echarts.init(document.getElementById('chart_hours'));

            var url = baseUrl + '/channels/chart/time/' + analysis.value.userId();
            $.get(url, { },function(result){
                if(result.code === 200){
                    var list = result['dataList'];
                    var xData = ['00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11',
                        '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'];
                    var yData = [];

                    var length = list.length;
                    for(var i = 0; i < xData.length; i ++){
                        var count = 0;
                        for(var j = 0; j < length; j ++) {
                            var item = list[j];
                            if(item['hour'] === i){
                                count += item['count']
                            }
                        }
                        yData.push(count);
                    }


                    chartHour.setOption(getHoursOption(xData, yData));
                    // var zoomSize = 6;
                    // chartHour.on('click', function (params) {
                    //     console.log(xData[Math.max(params.dataIndex - zoomSize / 2, 0)]);
                    //     chartHour.dispatchAction({
                    //         type: 'dataZoom',
                    //         startValue: xData[Math.max(params.dataIndex - zoomSize / 2, 0)],
                    //         endValue: xData[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
                    //     });
                    // });
                }
            });


            function getHoursOption(xData, yData){
                return {
                    title: {
                        text: 'Time distribution',
                        subtext: '',
                        textStyle: {
                            align: 'middle',
                            color: '#f1dbb6'
                        }
                    },
                    textStyle:{
                        color: '#878787'
                    },
                    backgroundColor: '#151617',
                    xAxis: {
                        data: xData,
                        axisLabel: {
                            inside: false,
                            textStyle: {
                                color: '#fff'
                            }
                        },
                        axisTick: {
                            show: false
                        },
                        axisLine: {
                            show: false
                        },
                        z: 10
                    },
                    yAxis: {
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            textStyle: {
                                color: '#999'
                            }
                        }
                    },
                    // dataZoom: [
                    //     {
                    //         type: 'inside'
                    //     }
                    // ],
                    series: [
                        { // For shadow
                            type: 'bar',
                            itemStyle: {
                                normal: {color: 'rgba(0,0,0,0.05)'}
                            },
                            barGap:'-100%',
                            barCategoryGap:'40%',
                            data: yData,
                            animation: false
                        },
                        {
                            type: 'bar',
                            itemStyle: {
                                normal: {
                                    color: new echarts.graphic.LinearGradient(
                                        0, 0, 0, 1,
                                        [
                                            {offset: 0, color: '#b4f6e4'},
                                            {offset: 0.5, color: '#57f08b'},
                                            {offset: 1, color: '#15f044'}
                                        ]
                                    )
                                },
                                emphasis: {
                                    color: new echarts.graphic.LinearGradient(
                                        0, 0, 0, 1,
                                        [
                                            {offset: 0, color: '#a2f71f'},
                                            {offset: 0.7, color: '#77f723'},
                                            {offset: 1, color: '#cbf6af'}
                                        ]
                                    )
                                }
                            },
                            data: yData
                        }
                    ]
                };

            }
        }

    }

};