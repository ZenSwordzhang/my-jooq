#!/usr/bin/python
# -*- coding: UTF-8 -*-

from flask import Flask

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def home():
    return '<h1>hello world</h1>'


if __name__ == '__main__':
    app.run()
