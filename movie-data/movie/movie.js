var cheerio = require('cheerio'),
    request = require('request');

module.exports = function() {
	this.data = {
		state:false,
		movies:[]
	};
	this.getData = function(url, callback) {
		var that = this;
		request(url, function(err, response, body) {
			if (err) {
				console.log('error:'+err+' when visit '+url);
				return;
			}

			if (response.statusCode == 200) {
				that.data.state = true;
				var $ = cheerio.load(body);
				$('.res_cinema > .res_movie_in').each(function(index, ele) {
					var info = {};
					var ele = $(ele);
					try {
						info['img'] = ele.find('.res_movie_pic img').attr('src');
						info['name'] = ele.find('.res_movie_text .cy_res_title a').attr('title');
						info['url'] = ele.find('.res_movie_text .cy_res_title a').attr('href');
						info['id'] = info['url'].match(/\d{6,20}/)[0];
						info['onTime'] = ele.find('.res_movie_text .fs9.ml5').text().replace(/[\[\]]/g, '');
						info['score'] = ele.find('.res_movie_text .res_movie_text_in:first-child .redfc').text();
						info['description'] = ele.find('.res_movie_text .res_movie_text_in .res_movie_ms').text();
						ele.find('.res_movie_text .res_movie_text_in.mt7 a').each(function(index, ele) {
							if (!info['type']) info['type'] = [];
							info['type'].push($(ele).text());
						});
						info['type'] = info['type'].toString();
						info['timeAndLanguage'] = ele.find('.res_movie_text .res_movie_text_in.mt7 .res_movie_dy').prev()['0'].prev['data'].replace(/[\r\n\t ]/g, '').replace(/.*：/, '');
						info['actors'] = ele.find('.res_movie_text .res_movie_text_in.mt7 .res_movie_dy').text().replace(/[\r\n\t ]/g, '').replace(/.*：/, '');
						that.data.movies.push(info);
					}
					catch(err) {
						console.log(err);
					}
				});
				
			} else {
				console.error('Error code:' + response.statusCode);
			}
			callback(that.data);
		});
	}
}
