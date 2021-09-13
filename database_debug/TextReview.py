# 文字审核

# encoding:utf-8

import requests, logging

# 获取access_token, 30天后过期
def getAccessToken():
    # client_id 为官网获取的AK， client_secret 为官网获取的SK
    client_id = 'iaKnzdcjQ0jzDDcSv65uYmOe'
    client_secret = 'FZX98ogldUqfhsIwpSLT5sRlgY5ViYqP'
    host = ''.join(['https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=', client_id, '&client_secret=', client_secret])
    response = requests.get(host)
    if response:
        logging(response.json())
    return response.json()['access_token']

'''
文本审核接口
'''
def textReview(access_token, text):
    request_url = "https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined"
    params = {"text": text}
    request_url = request_url + "?access_token=" + access_token
    headers = {'content-type': 'application/x-www-form-urlencoded'}
    response = requests.post(request_url, data = params, headers = headers)
    if response:
        logging(response.json())
    return response.json()

access_token = getAccessToken()
textReview(access_token, "坏孩子")

logging.basicConfig(format = '%(asctime)s - %(pathname)s[line:%(lineno)d]\n%(levelname)s: %(message)s',
                    level = logging.DEBUG)