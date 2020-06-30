#!/usr/bin/python3
# -*- coding: UTF-8 -*-

import random
import string

from flask import Flask, jsonify, request

app = Flask(__name__)


@app.route('/api', methods=['GET'])
def get_port():
    """
    无参
    curl -X GET 'http://192.168.1.110:8001/api'
    """
    return jsonify({'port': '8001'})


@app.route('/api/num', methods=['GET'])
def get_num():
    """
    无参
    curl -X GET 'http://192.168.1.110:8001/api/num'
    """
    return {'number': random.randint(100, 5000)}


@app.route('/api/msg', methods=['GET'])
def get_msg():
    """
    接收form表单传递的参数
    curl --location --request GET 'http://192.168.1.110:8001/api/msg' --form 'name=zsx'
    curl -X GET 'http://192.168.1.110:8001/api/msg' --form 'name=zsx'
    """
    return {'msg': f"hello, {request.form['name']}"}


@app.route('/api/age', methods=['GET'])
def get_age():
    """
    接收'x-www-form-urlencoded'传递的参数
    curl --location --request GET 'http://192.168.1.110:8001/api/age' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --data-urlencode 'id=1'

    curl -X GET --data-urlencode "id=1" 'http://192.168.1.110:8001/api/age'
    """
    user_id = request.form.get('id')
    if user_id:
        age = random.randint(18, 100)
    else:
        age = None
    return {'age': age}


@app.route('/api/name', methods=['GET'])
def get_name():
    """
    接收url传递的参数
    curl --location --request GET 'http://192.168.1.110:8001/api/name?id=1'
    curl -X GET 'http://192.168.1.110:8001/api/name?id=1'
    """
    try:
        # 方式1
        # params = parse.parse_qs(parse.urlparse(request.url).query)
        # user_id = params['id'][0].strip()

        # 方式2
        user_id = request.args.get("id").strip()

        if user_id == '1':
            name = "zsx"
        else:
            name = ''.join(random.sample(string.ascii_letters + string.digits, 5))
    except Exception as e:
        print(e)
        # raise e
        return {'msg': 'error'}
    else:
        return {'msg': name}


@app.route('/api/data', methods=['GET'])
def get_data():
    """
    接收传递的JSON参数
    curl --location --request GET 'http://192.168.1.110:8001/api/data' \
    --header 'Content-Type: application/json' \
    --data-raw '{"id": 1, "name": "lisi"}'

    curl --header "Content-Type:application/json" \
    -X GET --data '{"id": 1, "name": "lisi"}' \
    http://192.168.1.110:8001/api/data
    """
    if not request.json:
        return jsonify({'msg': 'data is null'})
    elif 'id' not in request.json:
        return {'msg': 'id is null'}
    elif 'name' not in request.json:
        return {'msg': 'name is null'}
    try:
        user_id = request.json['id']
        name = request.json['name']
    except Exception as e:
        print(e)
        # raise e
        return {'msg': 'error'}
    else:
        return {'id': user_id, 'name': name, 'age': random.randint(18, 100)}


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8001, debug=True)
