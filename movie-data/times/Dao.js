var Sequelize = require('sequelize');
var mysql = require('mysql');
var crypto = require("crypto");

function md5(data) {
    var Buffer = require("buffer").Buffer;
    var buf = new Buffer(data);
    var str = buf.toString("binary");
    return crypto.createHash("md5").update(str).digest("hex");
}
var sequelize = new Sequelize('sekko', 'root', '123456', {
    host: '127.0.0.1',
    port: 3306,
    dialect: 'mysql',
    pool: {
        max: 5,
        min: 0,
        idle: 10000
    },
});


var times = sequelize.define('times', {
    id: {
        type: Sequelize.STRING,
        unique: true,
        primaryKey: true,
        field: 'id'
    },
    startTime: {
        type: Sequelize.STRING,
        field: 'start_time',
        allowNull: false
    },
    endTime: {
        type: Sequelize.STRING,
        field: 'end_time',
        allowNull: false
    },
    languageAndEffect: {
        type: Sequelize.STRING,
        field: 'language_and_effect'
    },
    playingRoom: {
        type: Sequelize.STRING,
        field: 'playing_room',
        allowNull: false
    },
    price: {
        type: Sequelize.STRING,
        field: 'price',
        allowNull: false
    },
    date: {
        type: Sequelize.STRING,
        field: 'date',
        allowNull: false
    },
    cinemaId: {
        type: Sequelize.STRING,
        field: 'cinema_id',
        allowNull: false
    },
    movieId: {
        type: Sequelize.STRING,
        field: 'movie_id',
        allowNull: false
    },
}, {
    freezeTableName: true
});


module.exports = function() {

    //创建表
    times.sync({
        force: false
    }).error(function(err, result) {
        console.error('[ ERROR - ] TIMES TABLE BUILD ERROR');
        console.error(err);
        console.error(result);
    }).done(function() {
        console.log('[ DONE - ] TIMES TABLE BUILD');
    });

    /*
     * @param record 为符合已定义orm对象格式的对象
     * @param callback 回调函数，需要传入两个参数，第一个参数表示是否插入成功，第二参数表示成功的返回信息/失败信息
     */
    this.create = function(record, callback) {
        times.create(record)
            .then(function(result) {
                console.log('[SUCCESS -] INSERTED AN RECORD INTO TIMES ' + record['name']);
                if (!!callback)
                    callback(true, result);
            })
            .catch(function(err) {
                console.log('[ERROR -] FAILED TO INSERT AN RECORD INTO TIMES') + record['name'];
                console.log(err);
                callback(false, err);
            });
    };

    /*
     * @param condition 查找条件，为key-value对象
     * @param callback 回调函数，需要传入两个参数，第一个参数表示是否能查找到，第二参数表示查找的数据
     */
    this.findOne = function(condition, callback) {
        times.findOne({ where: condition }).then(function(result) {
            if (!result) {
                callback(false, null);
            } else {
                callback(true, result['dataValues']);
            }
            console.log('[SUCCESS -] FOUND AN RECORD FROM TIMES WHERE ' + log(condition));
        }).catch(function(err) {
            console.log('[ERROR -] FAILED TO FIND AN RECORD FROM TIMES WHERE' + log(condition));
            console.log(err);
        });
    }

    /*
     * @param condition 查找条件，为key-value对象
     * @param callback 回调函数，需要传入两个参数，第一个参数表示是否能查找到，第二参数表示查找的数据的数组
     * @param limit  返回条数，默认情况下返回全部
     */
    this.find = function(condition, callback, limit) {
        times.findAndCountAll({ where: condition, limit: limit })
            .then(function(result) {
                if (!result.count) {
                    callback(0, []);
                    return;
                }
                var timess = [];
                result.rows.forEach(function(ele) {
                    timess.push(ele['dataValues']);
                });
                console.log('[SUCCESS -] FOUND ALL RECORDS FROM TIMES WHERE ' + log(condition));
                callback(result.count, timess);
            }).catch(function(err) {
                console.log('[ERROR -] FAILED TO FIND ALL RECORD FROM TIMES WHERE ') + log(condition);
                console.log(err);
            });
    };

    /*
     * @param condition 查找条件，为key-value对象
     * @param callback 回调函数， 传入参数为删除的记录的条数
     */
    this.delete = function(condition, callback) {
        times.findAll({ where: condition })
            .then(function(timess) {
                if (timess.length === 0) {
                    callback(0);
                    console.log('[SUCCESS -] DELETED 0 RECORD FROM TIMES WHERE ' + log(condition));
                    return;
                } else {
                    var len = timess.length;
                    timess.forEach(function(ele, index) {
                        var name = ele['dataValues']['name'];
                        ele.destroy().then(function(info) {
                            console.log('[SUCCESS -] DELETED AN RECORD FROM TIMES - ' + name);
                            if (index == len - 1)
                                callback(len);
                        });
                    });
                    console.log('[SUCCESS -] DELETED ' + len + ' RECORD FROM TIMES WHERE ' + log(condition));
                }
            })
            .catch(function(err) {
                console.log('[ERROR -] FAILED TO DELETE RECORD FROM TIMES WHERE ') + log(condition);
                console.log(err);
            });
    };

    /*
     * @param condition 查找条件，为key-value对象
     * @param callback 回调函数， 传入参数为修改的记录的条数
     */
    this.update = function(condition, newProp, callback) {
        times.findAll({ where: condition })
            .then(function(timess) {
                if (timess.length === 0) {
                    callback(0);
                    console.log('[SUCCESS -] UPDATE 0 RECORD FROM TIMES WHERE ' + log(condition));
                    return;
                } else {
                    var len = timess.length;
                    timess.forEach(function(ele, index) {
                        var name = ele['dataValues']['name'];
                        ele.update(newProp).then(function(info) {
                            console.log('[SUCCESS -] UPDATE AN RECORD FROM TIMES - ' + name);
                            console.log('[SUCCESS -] UPDATE RESULT - ' + log(newProp));
                            if (index == len - 1)
                                callback(len);
                        });
                    });
                    console.log('[SUCCESS -] UPDATE ' + len + ' RECORD FROM TIMES WHERE ' + log(condition));
                }
            })
            .catch(function(err) {
                console.log('[ERROR -] FAILED TO UPDATE ALL RECORD FROM TIMES WHERE ') + log(condition);
                console.log(err);
            });
    }
}


function log(condition) {
    var info = '';
    for (var i in condition)
        info += i.toString() + ' = ' + condition[i].toString() + ', ';
    return info;
}
