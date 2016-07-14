var CronJob = require('cron').CronJob;
var Spider = require('../Spider');

//- 获取后天场次
new CronJob('0 33 11 * * *', function() {
	console.log('获取后天场次定时任务开始！');
	Spider.grabTimes(2);
}, function() {
    console.log('获取后天场次定时任务停止！');
}, true);
