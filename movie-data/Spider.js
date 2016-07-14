var Movie = require('./movie/movie');
var movie = new Movie();
var MovieDao = require('./movie/Dao');
var movieDao = new MovieDao();

var Cinema = require('./cinema/cinema');
var cinema = new Cinema();
var CinemaDao = require('./cinema/Dao');
var cinemaDao = new CinemaDao();

var Detail = require('./cinema/details');
var detail = new Detail();

var Times = require('./times/times');
var times = new Times();

var TimesDao = require('./times/Dao');
var timesDao = new TimesDao();

var conf = require('./conf')

var MAX_DAYS = 3;

module.exports = {
    grabMovie: function() {
        var movieTypeUrl = conf['hotMovieIndexUrl'];
        for (key in movieTypeUrl) {
            movie.getData(movieTypeUrl[key], callback);

            function callback(data) {
                if (!data['state']) {
                    console.log('[ ERROR - ] FAILED TO GRAB DATA FROM SOURCE WEBSITE');
                    return;
                }
                data['movies'].forEach(function(movie) {
                    movieDao.create(movie, function(isSuccess, result) {

                    });
                });
            }
        }
    },
    grabCinema: function() {
        var cinemaList = conf['cinemaList'];
        cinemaList.forEach(function(url) {
            cinema.getData(url, callback);

            function callback(data) {
                if (!data['state']) {
                    console.log('[ ERROR - ] FAILED TO GRAB DATA FROM SOURCE WEBSITE');
                    return;
                }
                data['cinemas'].forEach(function(cinema) {
                    detail.getData(cinema['url'], function(d) {
                        if (!d['state']) {
                            return;
                        }
                        var c = cinema;
                        c['movies'] = JSON.stringify(d['details']['movies']);
                        c['coordinate'] = d['details']['coordinate'];
                        c['tel'] = d['details']['tel'];
                        cinemaDao.create(c, function(isSuccess, result) {

                        });
                    })
                });
            }
        });
    },
    grabTimes: function(addDay) {
        cinemaDao.find({}, function(isSuccess, cinemas) {
            if (isSuccess) {
                var dates = [];
                dates.push(getDateStr(addDay||0));
                cinemas.forEach(function(cinema, index) {
                    var films = JSON.parse(cinema['movies']);
                    films = JSON.parse(films);
                    films.forEach(function(film, index) {
                        dates.forEach(function(date) {
                            times.getData(film['filmId'], cinema['cinemaId'], date, function(data) {
                                if (data.state) {
                                    data.times.forEach(function(time) {
                                        if (time) {
                                            try {
                                                timesDao.create(time, function(isSuccess, result) {

                                                });
                                            }
                                            catch(err){

                                            }
                                        }
                                    });
                                }
                            });
                        });
                    });
                });
            }
        })
    },
    clearMovie: function() {
        movieDao.delete({}, function(n) {
            console.log('delete ' + n + ' Movie');
        });
    },
    clearCinema: function() {
        cinemaDao.delete({}, function(n) {
            console.log('delete ' + n + ' Cinema');
        });
    },
    clearTimes: function() {
        timesDao.delete({}, function(n) {
            console.log('delete ' + n + ' Times');
        });
    }
}

function getDateStr(addDay) {
    var dd = new Date();
    dd.setDate(dd.getDate() + addDay);
    return dd.toJSON().match(/\d{4}-\d{2}-\d{2}/)[0];
}


