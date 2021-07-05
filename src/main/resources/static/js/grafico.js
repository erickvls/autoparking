$(document).ready(function() {

        todaydate = new Date();
        var oneJan =  new Date(todaydate.getFullYear(), 0, 1);
        var numberOfDays =  Math.floor((todaydate - oneJan) / (24 * 60 * 60 * 1000));
        var result = Math.ceil(( todaydate.getDay() + 1 + numberOfDays) / 7);
        var weekControl = document.querySelector('input[type="week"]');
        weekControl.value = '2021-W'+result;


        google.load("visualization", "1", {packages:["corechart"]});
        google.setOnLoadCallback(drawCharts);

        function drawCharts() {
          var barData = google.visualization.arrayToDataTable([
                                      ['Dia', 'Faturas geradas', 'Receita'],
                                      ['Seg',  1,      2],
                                      ['Ter',  4,      5],
                                      ['Qua',  3,       0],
                                      ['Qui',  2,      1],
                                      ['Sex',  10,      2],
                                      ['Sáb',  10,      3],
                                      ['Dom',  15,       4]
                                    ]);
          // set bar chart options
          var barOptions = {
            focusTarget: 'category',
            backgroundColor: 'transparent',
            colors: ['cornflowerblue', 'tomato'],
            fontName: 'Open Sans',
            chartArea: {
              left: 50,
              top: 10,
              width: '100%',
              height: '70%'
            },
            bar: {
              groupWidth: '80%'
            },
            hAxis: {
              textStyle: {
                fontSize: 11
              }
            },
            vAxis: {
              minValue: 0,
              maxValue: 15,
              baselineColor: '#DDD',
              gridlines: {
                color: '#DDD',
                count: 4
              },
              textStyle: {
                fontSize: 11
              }
            },
            legend: {
              position: 'bottom',
              textStyle: {
                fontSize: 12
              }
            },
            animation: {
              duration: 1200,
              easing: 'out',
                    startup: true
            }
          };
          // draw bar chart twice so it animates
          var barChart = new google.visualization.ColumnChart(document.getElementById('bar-chart'));
          //barChart.draw(barZeroData, barOptions);
          barChart.draw(barData, barOptions);

        }



        $(".botao-semana").on("change", function(e) {
          let week = document.querySelector('#week');
            let dates = parseDates(week.value);
            console.log(dates);
            var url = '/admin/estatisticas/semanal';
            $.ajax({
                         url : url,
                         type : "POST",
                         data : {
                                   dataFrom : dates[0],
                                   dataTo: dates[6]
                          },
                         beforeSend : function(){

                         }
                    })
                    .done(function(data){
                        let week = document.querySelector('#week');
                        let dates = parseDatesSemString(week.value);
                        var dd = String(dates[0].getDate()).padStart(2, '0');
                        var mm = String(dates[0].getMonth() + 1).padStart(2, '0');
                        console.log(dd + '/' + mm);

                        var barData = google.visualization.arrayToDataTable([
                                                              ['Dia', 'Faturas geradas', 'Receita'],
                                                              [String(dates[0].getDate()).padStart(2, '0') + '/' + String(dates[0].getMonth() + 1).padStart(2, '0'),  checaFaturaPorDia(String(dates[0].getDate()).padStart(2, '0'),data),   checaReceitaPorDia(String(dates[0].getDate()).padStart(2, '0'),data)],
                                                              [String(dates[1].getDate()).padStart(2, '0') + '/' + String(dates[1].getMonth() + 1).padStart(2, '0'),  checaFaturaPorDia(String(dates[1].getDate()).padStart(2, '0'),data),   checaReceitaPorDia(String(dates[1].getDate()).padStart(2, '0'),data)],
                                                              [String(dates[2].getDate()).padStart(2, '0') + '/' + String(dates[2].getMonth() + 1).padStart(2, '0'),  checaFaturaPorDia(String(dates[2].getDate()).padStart(2, '0'),data),   checaReceitaPorDia(String(dates[2].getDate()).padStart(2, '0'),data)],
                                                              [String(dates[3].getDate()).padStart(2, '0') + '/' + String(dates[3].getMonth() + 1).padStart(2, '0'),  checaFaturaPorDia(String(dates[3].getDate()).padStart(2, '0'),data),   checaReceitaPorDia(String(dates[3].getDate()).padStart(2, '0'),data)],
                                                              [String(dates[4].getDate()).padStart(2, '0') + '/' + String(dates[4].getMonth() + 1).padStart(2, '0'),  checaFaturaPorDia(String(dates[4].getDate()).padStart(2, '0'),data),   checaReceitaPorDia(String(dates[4].getDate()).padStart(2, '0'),data)],
                                                              [String(dates[5].getDate()).padStart(2, '0') + '/' + String(dates[5].getMonth() + 1).padStart(2, '0'),  checaFaturaPorDia(String(dates[5].getDate()).padStart(2, '0'),data),   checaReceitaPorDia(String(dates[5].getDate()).padStart(2, '0'),data)],
                                                              [String(dates[6].getDate()).padStart(2, '0') + '/' + String(dates[6].getMonth() + 1).padStart(2, '0'),  checaFaturaPorDia(String(dates[6].getDate()).padStart(2, '0'),data),   checaReceitaPorDia(String(dates[6].getDate()).padStart(2, '0'),data)]
                                                            ]);
                          var barOptions = {
                                      focusTarget: 'category',
                                      backgroundColor: 'transparent',
                                      colors: ['cornflowerblue', 'tomato'],
                                      fontName: 'Open Sans',
                                      chartArea: {
                                        left: 50,
                                        top: 10,
                                        width: '100%',
                                        height: '70%'
                                      },
                                      bar: {
                                        groupWidth: '80%'
                                      },
                                      hAxis: {
                                        textStyle: {
                                          fontSize: 11
                                        }
                                      },
                                      vAxis: {
                                        minValue: 0,
                                        maxValue: 15,
                                        baselineColor: '#DDD',
                                        gridlines: {
                                          color: '#DDD',
                                          count: 4
                                        },
                                        textStyle: {
                                          fontSize: 11
                                        }
                                      },
                                      legend: {
                                        position: 'bottom',
                                        textStyle: {
                                          fontSize: 12
                                        }
                                      },
                                      animation: {
                                        duration: 1200,
                                        easing: 'out',
                                              startup: true
                                      }
                                    };
                         var barChart = new google.visualization.ColumnChart(document.getElementById('bar-chart'));
                         barChart.draw(barData, barOptions);
                    })
                    .fail(function(jqXHR, textStatus, msg){
                         alert("Falha na requisição");
                    });
        });

        let parseDates = (inp) => {
          let year = parseInt(inp.slice(0,4), 10);
          let week = parseInt(inp.slice(6), 10);

          let day = (1 + (week - 1) * 7) + 7; // 1st of January + 7 days for each week

          let dayOffset = new Date(year, 0, 1).getDay() -1; // we need to know at what day of the week the year start

          dayOffset--;  // depending on what day you want the week to start increment or decrement this value. This should make the week start on a monday

          let days = [];
          for (let i = 0; i < 7; i++) {
            var date = new Date(year, 0, day - dayOffset + i)
            date.setUTCHours(0,0,0,0);
            days.push(date.toISOString()); // add a new Date object to the array with an offset of i days relative to the first day of the week
          }
          return days;
        }

        let parseDatesSemString = (inp) => {
              let year = parseInt(inp.slice(0,4), 10);
              let week = parseInt(inp.slice(6), 10);

              let day = (1 + (week - 1) * 7) + 7; // 1st of January + 7 days for each week

              let dayOffset = new Date(year, 0, 1).getDay() -1; // we need to know at what day of the week the year start

              dayOffset--;  // depending on what day you want the week to start increment or decrement this value. This should make the week start on a monday

              let days = [];
              for (let i = 0; i < 7; i++) {
                var date = new Date(year, 0, day - dayOffset + i)
                date.setUTCHours(0,0,0,0);
                days.push(date); // add a new Date object to the array with an offset of i days relative to the first day of the week
              }
              return days;
            }

        var FaturaDTO = function(dia, faturaPorDia, total) {
              this.dia = dia;
              this.faturaPorDia = faturaPorDia;
              this.total = total;
        }

        function checaFaturaPorDia(dia,data){
            for(var i = 0; i<data.length; i++){
                if(dia==data[i].dia){
                    return data[i].faturaPorDia;
                }
            }
            return 0;
        }

        function checaReceitaPorDia(dia,data){
            for(var i = 0; i<data.length; i++){
                if(dia==data[i].dia){
                    console.log(data[i].total);
                    console.log(data[i]);
                    return data[i].total;
                }
            }
            return 0;
        }
});

