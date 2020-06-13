'use strict';

const net = require('net');

const logHost = '127.0.0.1'
    , logPort = 5001
    , sender = require('os').hostname();

const conn = net.createConnection({host: logHost, port: logPort}, function () {
    const message = {
        '@tags': ['nodejs', 'test']
        , '@message': 'tcp test '
        , '@fields': {'sender': sender}
    };
    conn.write(JSON.stringify(message));
    process.exit(0);
})
    .on('error', function (err) {
        console.error(err);
        process.exit(1);
    });