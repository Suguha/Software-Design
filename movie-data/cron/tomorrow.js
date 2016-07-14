var CronJob = require('cron').CronJob;
var Spider = require('../Spider');

//- 获取明天场次
new CronJob('0 55 18 * * *', function() {
	console.log('获取明天场次定时任务开始！');
	Spider.grabTimes(1);
}, function() {
    console.log('获取明天场次定时任务停止！');
}, true);

