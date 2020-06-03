#!/usr/bin/python
# -*- coding: UTF-8 -*-

from flask import Flask, abort, request, jsonify

app = Flask(__name__)

users = []

'''
curl --header "Content-Type:application/json" -X POST http://127.0.0.1:8088/user -d '{"id":2, "name":"lisi", "age":18}'
'''


@app.route('/user', methods=['POST'])
def add_user():
    if not request.json or 'id' not in request.json or 'name' not in request.json:
        abort(400)
    user = {
        'id': request.json['id'],
        'name': request.json['name'],
        'age': request.json['age']
    }
    users.append(user)
    return jsonify({'result': 'success'})


'''
curl --header "Content-Type:application/json" -X PUT http://127.0.0.1:8088/user -d '{"id":2, "name":"libai", "age":16}'
'''


@app.route('/user', methods=['PUT'])
def update_user():
    if not request.json or 'id' not in request.json:
        return jsonify({'result': 'id is null'})
    else:
        user_id = request.json['id']
        name = request.json['name']
        age = request.json['age']
        user_list = list(filter(lambda u: u['id'] == int(user_id), users))
        if user_list:
            user = user_list.pop()
            if name:
                user["name"] = name
            if age:
                user["age"] = age
            return jsonify({'result': 'update success'})
        else:
            return jsonify({'result': 'id not exists'})


'''
curl --header "Content-Type:application/x-www-form-urlencoded" -X GET 'http://127.0.0.1:8088/user' -d '{"id":2}'
'''


@app.route('/user', methods=['GET'])
def get_user():
    if not request.args or 'id' not in request.args:
        # 没有指定id则返回全部
        return jsonify(users)
    else:
        user_id = request.args['id']
        user = filter(lambda u: u['id'] == int(user_id), users)
        return jsonify(user) if user else jsonify({'result': 'not found'})


'''
curl --header "Content-Type:application/json" -X DELETE 'http://127.0.0.1:8088/user' -d '{"id":2}'
'''


@app.route('/user', methods=['DELETE'])
def delete_user():
    if not request.json or 'id' not in request.json:
        return jsonify({'result': 'id is null'})
    else:
        user_id = request.json['id']
        user_list = list(filter(lambda u: u['id'] == int(user_id), users))
        if user_list:
            users.remove(user_list.pop())
            return jsonify({'result': 'deletion success'})
        else:
            return jsonify({'result': 'id not exists'})


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8088, debug=True)
