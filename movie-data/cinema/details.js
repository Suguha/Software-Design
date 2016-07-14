var cheerio = require('cheerio'),
    request = require('request');

module.exports = function() {
    this.data = {
        state: false,
        details: {}
    };
    this.getData = function(url, callback) {
        var that = this;
        request(url, function(err, response, body) {
            if (err) {
                console.log('error:' + err + ' when visit ' + url);
                return;
            }

            if (response.statusCode == 200) {
                that.data.state = true;
                var $ = cheerio.load(body);
                try {
                    that.data.details['coordinate'] = body.match(/Point\(\d+\.\d+\,\d+\.\d+/)[0].replace(/Point\(/, '');
                }
                catch(err) {
                    that.data.details['coordinate'] = '';    
                }
                var movies = []
                that.data.details['tel'] = $('.ml5.fl .fs0').eq(-1).text();
                $('.fl.ofh > li').each(function(index, ele) {
                    var target = {};
                    target.filmName = $(ele).find('img').eq(0).attr('title');
                    target.filmId = $(ele).find('img').eq(0).attr('onclick').match(/\d{4,20}/)[0];
                    target.filmPost = $(ele).find('img').eq(0).attr('src');
                    movies.push(target);
                });
                that.data.details['movies'] = JSON.stringify(movies);

            } else {
                console.error('Error code:' + response.statusCode);
            }
            callback(that.data);
        });
    }
};