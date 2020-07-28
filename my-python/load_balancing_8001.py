#!/usr/bin/python3
# -*- coding: UTF-8 -*-

from flask import Flask

app = Flask(__name__)


@app.route('/api', methods=['GET'])
def get_msg():
    """
    curl -X GET 'http://192.168.1.110:8001/api'
    """
    return '{"msg" : "The server port is 8001"}'


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8001, debug=True)
