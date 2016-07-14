var cheerio = require('cheerio'),
    request = require('request');

module.exports = function() {
    this.data = {
        state: false,
        cinemas: []
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
                $('.res_cinema > .res_movie_in').each(function(index, ele) {
                    var info = {};
                    var ele = $(ele);
                    try {
                        info['img'] = ele.find('.res_cinema_pic img').attr('data-original');
                        info['name'] = ele.find('.res_cinema_text .res_movie_text_in a').attr('title');
                        info['url'] = ele.find('.res_cinema_text .res_movie_text_in a').attr('href');
                        info['cinemaId'] = info['url'].match(/-.*(?=\/)/)[0].replace(/-/g, ''); 
                        var tmp = ele.find('.res_cinema_text .res_movie_text_in.pt10').eq(2).text().replace(/(\s)+(\r|\n|\t)+/g, "@");

                        info['location'] = tmp.split('@')[0].replace(/地址：/, '');
                        info['transport'] = tmp.split('@')[2].replace(/路线：/, '');
                        that.data.cinemas.push(info);
                    }
                    catch(err) {

                    }
                });

            } else {
                console.error('Error code:' + response.statusCode);
            }
            callback(that.data);
        });
    }
}