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

/*
定义表
+-----------------+--------------+------+-----+---------+----------------+
| Field           | Type         | Null | Key | Default | Extra          |
+-----------------+--------------+------+-----+---------+----------------+
| id              | int(11)      | NO   | PRI | NULL    | auto_increment |
| img             | varchar(255) | YES  |     | NULL    |                |
| name            | varchar(255) | NO   |     | NULL    |                |
| onTime          | varchar(255) | YES  |     | NULL    |                |
| score           | varchar(255) | YES  |     | NULL    |                |
| description     | text         | YES  |     | NULL    |                |
| type            | text         | NO   |     | NULL    |                |
| timeAndLanguage | varchar(255) | YES  |     | NULL    |                |
| actors          | text         | YES  |     | NULL    |                |
| url             | varchar(255) | NO   |     | NULL    |                |
| createdAt       | datetime     | NO   |     | NULL    |                |
| updatedAt       | datetime     | NO   |     | NULL    |                |
+-----------------+--------------+------+-----+---------+----------------+
*/
var movie = sequelize.define('movie', {
    id: {
        type: Sequelize.BIGINT,
        unique: true,
        primaryKey: true,
        field: 'id'
    },
    img: {
        type: Sequelize.STRING,
        field: 'img'
    },
    name: {
        type: Sequelize.STRING,
        field: 'name',
        allowNull: false
    },
    onTime: {
        type: Sequelize.STRING,
        field: 'on_time'
    },
    score: {
        type: Sequelize.STRING,
        field: 'score'
    },
    description: {
        type: Sequelize.TEXT,
        field: 'description'
    },
    type: {
        type: Sequelize.TEXT,
        field: 'type',
        allowNull: false
    },
    timeAndLanguage: {
        type: Sequelize.STRING,
        field: 'time_and_language'
    },
    actors: {
        type: Sequelize.TEXT,
        field: 'actors'
    },
    url: {
        type: Sequelize.STRING,
        field: 'url',
        allowNull: false,
        unique: true //根据url排重
    }
}, {
    freezeTableName: true
});


module.exports = function() {

    //创建表
    movie.sync({
        force: false
    }).error(function(err, result) {
        console.error('[ ERROR - ] MOVIE TABLE BUILD ERROR');
        console.error(err);
        console.error(result);
    }).done(function() {
        console.log('[ DONE - ] MOVIE TABLE BUILD');
    });

    /*
     * @param record 为符合已定义orm对象格式的对象
     * @param callback 回调函数，需要传入两个参数，第一个参数表示是否插入成功，第二参数表示成功的返回信息/失败信息
     */
    this.create = function(record, callback) {
        movie.create(record)
            .then(function(result) {
                console.log('[SUCCESS -] INSERTED AN RECORD INTO MOVIE ' + record['name']);
                if (!!callback)
                    callback(true, result);
            })
            .catch(function(err) {
                console.log('[ERROR -] FAILED TO INSERT AN RECORD INTO MOVIE') + record['name'];
                console.log(err);
                callback(false, err);
            });
    };

    /*
     * @param condition 查找条件，为key-value对象
     * @param callback 回调函数，需要传入两个参数，第一个参数表示是否能查找到，第二参数表示查找的数据
     */
    this.findOne = function(condition, callback) {
        movie.findOne({ where: condition }).then(function(result) {
            if (!result) {
                callback(false, null);
            } else {
                callback(true, result['dataValues']);
            }
            console.log('[SUCCESS -] FOUND AN RECORD FROM MOVIE WHERE ' + log(condition));
        }).catch(function(err) {
            console.log('[ERROR -] FAILED TO FIND AN RECORD FROM MOVIE WHERE' + log(condition));
            console.log(err);
        });
    }

    /*
     * @param condition 查找条件，为key-value对象
     * @param callback 回调函数，需要传入两个参数，第一个参数表示是否能查找到，第二参数表示查找的数据的数组
     * @param limit  返回条数，默认情况下返回全部
     */
    this.find = function(condition, callback, limit) {
        movie.findAndCountAll({ where: condition, limit: limit })
            .then(function(result) {
                if (!result.count) {
                    callback(0, []);
                    return;
                }
                var movies = [];
                result.rows.forEach(function(ele) {
                    movies.push(ele['dataValues']);
                });
                console.log('[SUCCESS -] FOUND ALL RECORDS FROM MOVIE WHERE ' + log(condition));
                callback(result.count, movies);
            }).catch(function(err) {
                console.log('[ERROR -] FAILED TO FIND ALL RECORD FROM MOVIE WHERE ') + log(condition);
                console.log(err);
            });
    };

    /*
     * @param condition 查找条件，为key-value对象
     * @param callback 回调函数， 传入参数为删除的记录的条数
     */
    this.delete = function(condition, callback) {
        movie.findAll({ where: condition })
            .then(function(movies) {
                if (movies.length === 0) {
                    callback(0);
                    console.log('[SUCCESS -] DELETED 0 RECORD FROM MOVIE WHERE ' + log(condition));
                    return;
                } else {
                    var len = movies.length;
                    movies.forEach(function(ele, index) {
                        var name = ele['dataValues']['name'];
                        ele.destroy().then(function(info) {
                            console.log('[SUCCESS -] DELETED AN RECORD FROM MOVIE - ' + name);
                            if (index == len - 1)
                                callback(len);
                        });
                    });
                    console.log('[SUCCESS -] DELETED ' + len + ' RECORD FROM MOVIE WHERE ' + log(condition));
                }
            })
            .catch(function(err) {
                console.log('[ERROR -] FAILED TO DELETE RECORD FROM MOVIE WHERE ') + log(condition);
                console.log(err);
            });
    };

    /*
     * @param condition 查找条件，为key-value对象
     * @param callback 回调函数， 传入参数为修改的记录的条数
     */
    this.update = function(condition, newProp, callback) {
        movie.findAll({ where: condition })
            .then(function(movies) {
                if (movies.length === 0) {
                    callback(0);
                    console.log('[SUCCESS -] UPDATE 0 RECORD FROM MOVIE WHERE ' + log(condition));
                    return;
                } else {
                    var len = movies.length;
                    movies.forEach(function(ele, index) {
                        var name = ele['dataValues']['name'];
                        ele.update(newProp).then(function(info) {
                            console.log('[SUCCESS -] UPDATE AN RECORD FROM MOVIE - ' + name);
                            console.log('[SUCCESS -] UPDATE RESULT - ' + log(newProp));
                            if (index == len - 1)
                                callback(len);
                        });
                    });
                    console.log('[SUCCESS -] UPDATE ' + len + ' RECORD FROM MOVIE WHERE ' + log(condition));
                }
            })
            .catch(function(err) {
                console.log('[ERROR -] FAILED TO UPDATE ALL RECORD FROM MOVIE WHERE ') + log(condition);
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
