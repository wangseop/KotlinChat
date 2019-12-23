# -*- coding: utf-8 -*-
from flask import Flask, request
from flask_restful import Resource, Api
from flask_restful import reqparse
import json
import sys
import os


# import seq2seq_predict
app = Flask(__name__)
api = Api(app)

# 이전 RegistUser 내용
class PredictSeq2SeqSentence(Resource):

    def post(self):
        data = request.get_json()
        message = "안녕하세요"
        ## 전달할 메세지 구성
        msg = [['message', message], ['sender', 'Trigobot'], ['receiver', data['name']]]
        msg = dict(msg)

        return msg

# 로그인 하는 부분
class LoginUser(Resource):
    
    def post(self):

        data = request.get_json()
        print(data)

        print("id :", data['id'])
        print("password :", data['password'])
        
        ####
        ## state( OK ), nick, cookie 얻기 위해 DB와 연동
        ####

        nick = data['id']
        cookie = data['id']

        msg = [['state', 'OK'], ['nick', nick], ['cookie', cookie]]
        msg = dict(msg)
        return msg
        
# 회원가입 하는 부분
class SignupUser(Resource):
    
    print("## SignupUser ##")
    def __init__(self):
        print("## Signup User/__init__ ##")
    def post(self):
        print("## Signup User/Post ##")

        data = request.get_json()
        print(data)

        print("id :", data['id'])
        print("password :", data['password'])
        
        ####
        ## state( OK ), nick, cookie 얻기 위해 DB와 연동
        ####

        state = "OK"

        msg = [['state', state]]
        msg = dict(msg)
        return msg
        


# class ImageMessage(Resource):

#     def post(self):
#         # global seq2seq_instance
#         # ## request message
#         # print(request.get_json())
#         data = request.get_json()
#         print(data)
#         global seq2seq_instance
#         global graph

#         with graph.as_default():
#             message = data['msg']
#             message = seq2seq_instance.seq2seq_run(message)
#             url = "http://prt.map.naver.com/mashupmap/print?key=p1571899889595_2122771601"
#             ## 전달할 메세지 구성
#             msg = [['message', "Image"], ['sender', 'Trigobot'], ['receiver', data['name']], ['imageurl', url]]
#             msg = dict(msg)

#             return msg

# api.add_resource(HelloUser, '/')
api.add_resource(PredictSeq2SeqSentence, '/msg')
# api.add_resource(MessageWithMap, '/msgmap')
# api.add_resource(ImageMessage, '/msgimage')
api.add_resource(LoginUser, '/login')
api.add_resource(SignupUser, '/signup')


if __name__ == "__main__":
    # encoder_model, decoder_model, word_to_index, index_to_word = seq2seq_model.seq2seq_run()

    app.run(host="0.0.0.0", port=5000)
    
