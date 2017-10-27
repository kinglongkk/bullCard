define([], function () {

    /**
     * ----------------------
     * key          value
     * ----------------------
     * path     |   Controller: xxx.js
     */
    var config = [
        {
            'url' :  '/',
            'controller' :'app/controller/Index',
            'filter' : ''
        },
        {
            'url' :  '/login',
            'controller' :'app/controller/Login',
            'filter' : ''
        },
        {
            'url' :  '/game',
            'controller' :'app/controller/Game',
            'filter' : ''
        },
        {
            'url' :  '/model/:type',
            'controller' :'app/controller/GameModel',
            'filter' : ''
        },
        {
            'url' :  '/model/bull100/room/:roomId',
            'controller' :'bull100/init',
            'filter' : 'app/filter/GameRoomFilter'
        },
        {//新百人
            'url' :  '/model/newBull100/room/:roomId',
            'controller' :'app/controller/Bull100',
            'filter' : 'app/filter/GameRoomFilter'
        },
        {
            'url' :  '/model/bullclassic/room/:roomId',
            'controller' :'app/controller/BullClassic',
            'filter' : 'app/filter/GameRoomFilter'
        },
        {
            'url' :  '/model/bullBao/room/:roomId',
            'controller' :'app/controller/BullBao',
            'filter' : 'app/filter/GameRoomFilter'
        }
    ];

    return config;

});