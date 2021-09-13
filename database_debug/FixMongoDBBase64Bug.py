# 修复数据库base64保存格式问题

import pymongo

# 连接MongoDB数据库
def connectMongoDB():
    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["passthemon"]
    mycol = mydb["Images"]
    return mycol

mycol = connectMongoDB()

def fixBase64Bug():
    photo_base64_list = mycol.find()
    for photo_base64 in photo_base64_list:
        _id = photo_base64['_id']
        image = photo_base64['image']
        print(image[-10:])
        if image != '' and image[-1] == '\'': mycol.update_one({'_id': _id}, {"$set": { "image": image[:-1] }})
        
fixBase64Bug()
        
        