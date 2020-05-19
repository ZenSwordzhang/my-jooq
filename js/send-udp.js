'use strict';

const udp = require('dgram');

// creating a client socket
const client = udp.createSocket('udp4');

//buffer msg
const data = Buffer.from('udpmessage');

//sending msg
client.send(data, 5000, 'localhost', function (error) {
    if (error) {
        client.close();
    } else {
        console.log('Data sent !!!');
        client.close();
    }
});