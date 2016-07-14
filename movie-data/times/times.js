var request = require('request');
var cheerio = require('cheerio');

module.exports = function() {
    this.data = {
        state: false,
        times: []
    };
    this.getData = function(movieId, cinemaId, date, callback) {
        var url = 'http://film.spider.com.cn/jquery-second033.html?filmId=' + movieId + '&showDate=' + date + '&area=guangzhou&type=cinema&regionId=null&subwayId=null&cinemaId=' + cinemaId;
        var that = this;
        request(url, function(err, response, body) {
            if (err) {
                console.log('error:' + err + ' when visit ' + url);
                return;
            }

            if (response.statusCode == 200) {
                that.data.state = true;
                var $ = cheerio.load(body, { xmlMode: true });
                var item = $('item').eq(6).text();
                var $times = $(item);
                $times.find('tbody tr:contains("选座购票")').each(function(index, ele) {
                    var target = {};
                    target['startTime'] = $(ele).find('td').eq(0).find('div').eq(0).text();
                    target['endTime'] = $(ele).find('td').eq(0).find('div').eq(1).text().replace(/散场/, '');
                    target['languageAndEffect'] = $(ele).find('td').eq(1).text();
                    target['playingRoom'] = $(ele).find('td').eq(2).text();
                    target['price'] = $(ele).find('td').eq(4).find('strong').text();
                    target['id'] = $(ele).find('td').eq(-1).find('a').eq(0).attr('href');
                    target['date'] = date;
                    target['cinemaId'] = cinemaId;
                    target['movieId'] = movieId;
                    that.data.times.push(target);
                });
            } else {
                console.error('Error code:' + response.statusCode);
            }
            callback(that.data);
        });
    }
}
