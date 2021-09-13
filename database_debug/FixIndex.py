import logging
import pymongo
# 修复MySQL数据库索引有误问题
# 连接MongoDB数据库
def connectMongoDB():
    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["passthemon"]
    mycol = mydb["Images"]
    return mycol, mydb

mycol, mydb = connectMongoDB()

def findLostIndex():
    all_idx = []
    lost_idx, more_idx = [], []
    mydoc = mycol.find()
    for ele in mydoc:
        all_idx.append(ele["goods_id"])
    all_idx.sort()
    
    length = len(all_idx)
    print("length " + str(length))
    for i in range(length - 1):
        if (all_idx[i + 1] - all_idx[i] != 1):
            lost_idx.append(i + 1)
            print(str(all_idx[i]) + ' ' + str(all_idx[i + 1]))
    for i in range(length):
        if (all_idx[i] > 172272):
            more_idx.append(all_idx[i])
    return lost_idx, more_idx

lost_idx, more_idx = findLostIndex()
print("lost_idx " + str(lost_idx))
print("more_idx " + str(more_idx))