Apex.grid = {
  padding: {
    right: 0,
    left: 0 } };



Apex.dataLabels = {
  enabled: false };


var randomizeArray = function (arg) {
  var array = arg.slice();
  var currentIndex = array.length,temporaryValue,randomIndex;

  while (0 !== currentIndex) {if (window.CP.shouldStopExecution(0)) break;

    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex -= 1;

    temporaryValue = array[currentIndex];
    array[currentIndex] = array[randomIndex];
    array[randomIndex] = temporaryValue;
  }window.CP.exitedLoop(0);

  return array;
};

// data for the sparklines that appear below header area
var sparklineData = [47, 45, 54, 38, 56, 24, 65, 31, 37, 39, 62, 51, 35, 41, 35, 27, 93, 53, 61, 27, 54, 43, 19, 46];

// the default colorPalette for this dashboard
//var colorPalette = ['#01BFD6', '#5564BE', '#F7A600', '#EDCD24', '#F74F58'];
var colorPalette = ['#00D8B6', '#008FFB', '#FEB019', '#FF4560', '#775DD0'];

var spark1 = {
  chart: {
    id: 'sparkline1',
    group: 'sparklines',
    type: 'area',
    height: 160,
    sparkline: {
      enabled: true } },


  stroke: {
    curve: 'straight' },

  fill: {
    opacity: 1 },

  series: [{
    name: 'Engagement ratio',
    data: randomizeArray(sparklineData) }],

  labels: [...Array(24).keys()].map(n => `2018-09-0${n + 1}`),
  yaxis: {
    min: 0 },

  xaxis: {
    type: 'datetime' },

  title: {
    text: '2.93%',
    offsetX: 30,
    style: {
      fontSize: '24px',
      cssClass: 'apexcharts-yaxis-title' } },


  subtitle: {
    text: 'Engagement ratio',
    offsetX: 30,
    style: {
      fontSize: '14px',
      cssClass: 'apexcharts-yaxis-title' } } };




var spark2 = {
  chart: {
    id: 'sparkline2',
    group: 'sparklines',
    type: 'area',
    height: 160,
    sparkline: {
      enabled: true } },


  stroke: {
    curve: 'straight' },

  fill: {
    opacity: 1 },

  series: [{
    name: 'Average likes per post',
    data: randomizeArray(sparklineData) }],

  labels: [...Array(24).keys()].map(n => `2018-09-0${n + 1}`),
  yaxis: {
    min: 0 },

  xaxis: {
    type: 'datetime' },

  title: {
    text: '154.37',
    offsetX: 30,
    style: {
      fontSize: '24px',
      cssClass: 'apexcharts-yaxis-title' } },


  subtitle: {
    text: 'Average likes per post',
    offsetX: 30,
    style: {
      fontSize: '14px',
      cssClass: 'apexcharts-yaxis-title' } } };




var spark3 = {
  chart: {
    id: 'sparkline3',
    group: 'sparklines',
    type: 'area',
    height: 160,
    sparkline: {
      enabled: true } },


  stroke: {
    curve: 'straight' },

  fill: {
    opacity: 1 },

  series: [{
    name: 'Average comments per post',
    data: randomizeArray(sparklineData) }],

  labels: [...Array(24).keys()].map(n => `2018-09-0${n + 1}`),
  xaxis: {
    type: 'datetime' },

  yaxis: {
    min: 0 },

  title: {
    text: '5.38',
    offsetX: 30,
    style: {
      fontSize: '24px',
      cssClass: 'apexcharts-yaxis-title' } },


  subtitle: {
    text: 'Average comments per post',
    offsetX: 30,
    style: {
      fontSize: '14px',
      cssClass: 'apexcharts-yaxis-title' } } };




new ApexCharts(document.querySelector("#spark1"), spark1).render();
new ApexCharts(document.querySelector("#spark2"), spark2).render();
new ApexCharts(document.querySelector("#spark3"), spark3).render();
//# sourceURL=pen.js