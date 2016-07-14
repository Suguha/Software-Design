var CronJob = require('cron').CronJob;
var Spider = require('../Spider');


//- 获取今天场次
new CronJob('0 10 11 * * *', function() {
	console.log('获取今天场次定时任务开始！');
	Spider.grabTimes(0);
}, function() {
    console.log('获取今天场次定时任务停止！');
}, true);

