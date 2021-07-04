$(document).ready(function() {
        google.load("visualization", "1", {packages:["corechart"]});
        google.setOnLoadCallback(drawCharts);
        function drawCharts() {
          // actual bar chart data
          var barData = google.visualization.arrayToDataTable([
            ['Dia', 'Faturas geradas', 'Receita'],
            ['Seg',  1050,      600],
            ['Ter',  1370,      910],
            ['Qua',  660,       400],
            ['Qui',  1030,      540],
            ['Sex',  1000,      480],
            ['Sáb',  1170,      960],
            ['Dom',  660,       320]
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
              maxValue: 1500,
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
        todaydate = new Date();

          //find the year of the current date
        var oneJan =  new Date(todaydate.getFullYear(), 0, 1);

           // calculating number of days in given year before a given date
        var numberOfDays =  Math.floor((todaydate - oneJan) / (24 * 60 * 60 * 1000));

           // adding 1 since to current date and returns value starting from 0
        var result = Math.ceil(( todaydate.getDay() + 1 + numberOfDays) / 7);

        var weekControl = document.querySelector('input[type="week"]');
        weekControl.value = '2021-W'+result;


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
                    .done(function(msg){
                        $("#asd").html(msg);

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
          for (let i = 0; i < 7; i++) // do this 7 times, once for every day
            days.push(new Date(year, 0, day - dayOffset + i).toISOString()); // add a new Date object to the array with an offset of i days relative to the first day of the week
          return days;
        }
});

