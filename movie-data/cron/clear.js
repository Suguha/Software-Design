var CronJob = require('cron').CronJob;
var Spider = require('../Spider');


//- 删除场次
new CronJob('0 30 18 * * *', function() {
	console.log('删除场次定时任务开始！');
	Spider.clearTimes();
}, function() {
    console.log('删除场次定时任务停止！');
}, true);

