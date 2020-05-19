'use strict';

var udp = require('dgram');

var buffer = require('buffer');

// creating a client socket
var client = udp.createSocket('udp4');

//buffer msg
var data = Buffer.from('udpmessage');

//sending msg
client.send(data, 5000,'localhost',function(error) {
  if (error) {
    client.close();
  } else {
    console.log('Data sent !!!');
    client.close();
  }
});