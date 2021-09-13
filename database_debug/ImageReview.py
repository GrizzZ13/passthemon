# 图片审核
# encoding:utf-8
from multiprocessing.queues import Queue
import requests, base64, pymongo, logging
import multiprocessing

# 获取access_token, 有效期为30天
def getAccessToken():
    # client_id 为官网获取的AK， client_secret 为官网获取的SK
    client_id = 'ZceKr3h0PPucEfpCmeEGtY38'
    client_secret = 'EhiWcTEAwmhBEOeUYD4ZE0MBrpSHSrnj'
    host = ''.join(['https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=', client_id, '&client_secret=', client_secret])
    response = requests.get(host)
    if response:
        logging(response.json())
    return response.json()['access_token']
    
# 图像审核接口
def imageCheck(access_token, imgList):
    request_url = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined"
    # # 二进制方式打开图片文件
    # f = open('C:\\Users\\14475\\Desktop\\PassThemOn\\PassThemOnCheck\\InvalidPhoto\\sex1.jpg', 'rb')
    # img = base64.b64encode(f.read())
    # # 网页链接, param = {"imagUrl": imgUrl}, 二选一
    # imgUrl = ''
    ans = []
    for img in imgList:
        params = {"image": img}
        request_url = request_url + "?access_token=" + access_token
        headers = {'content-type': 'application/x-www-form-urlencoded'}
        response = requests.post(request_url, data = params, headers = headers)
        if response:
            logging (response.json())
        ans.append(response.json())
    return ans
    
# 连接MongoDB数据库
def connectMongoDB():
    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["passthemon"]
    mycol = mydb["Images"]
    return mycol

# 根据goods_id和display_order来查询数据库中照片
# display_order默认为1, 如果为-1则返回所有满足goods_id图片
def getBase64ByGoodsidAndDisplayorder(mycol, goods_id, display_order = 1):
    myquery = {}
    if (display_order == -1): myquery = {"goods_id": goods_id}
    else: myquery = {"goods_id": goods_id, "displayOrder": display_order}
    
    mydoc = mycol.find(myquery)
    ans = []
    for ele in mydoc:
        ans.append(ele['image'])
    return ans

mycol = connectMongoDB()
access_token = getAccessToken()

base64_list = getBase64ByGoodsidAndDisplayorder(mycol, 103)
check_result = imageCheck(access_token, base64_list)

logging.basicConfig(format = '%(asctime)s - %(pathname)s[line:%(lineno)d]\n%(levelname)s: %(message)s',
                    level = logging.DEBUG)