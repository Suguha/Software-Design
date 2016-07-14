var CronJob = require('cron').CronJob;
var Spider = require('../Spider');

//- 获取电影
new CronJob('0 40 16 * * *', function() {
	console.log('获取电影定时任务开始！');
	Spider.clearMovie();
	Spider.grabMovie();
}, function() {
    console.log('获取电影定时任务停止！');
}, true);

//-获取电影院信息
new CronJob('0 20 18 * * *', function() {
	console.log('获取电影院定时任务开始！');
	Spider.clearCinema();
	Spider.grabCinema();
}, function() {
    console.log('获取电影院定时任务停止！');
}, true);
