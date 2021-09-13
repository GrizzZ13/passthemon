# MySQL table demands
# demands_id description ideal_price name num state upload_time user
# data unrepeatable

import time, random
import pymysql, pymongo
from pymysql import NULL, cursors
import logging
from configuration import db_user, db_password, generateRandomTimestamp, connectToMySQL

size = 10000
isAddTo = False

def getBasicInfo(cursor):
    # 获取商品id最大值
    cursor.execute('SELECT MAX(goods_id) FROM goods')
    max_goods_id = int(str(cursor.fetchone())[1:-2])
    # 获取商品id最小值
    cursor.execute('SELECT MIN(goods_id) FROM goods')
    min_goods_id = int(str(cursor.fetchone())[1:-2])
    
    print('min_goods_id : {} max_goods_id : {}'
          .format(min_goods_id, max_goods_id))
    return min_goods_id, max_goods_id

def connectToMongoDB():
    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["passthemon"]
    mycol = mydb["Images"]
    return mycol
    

def disrapt():
    cursor, connection = connectToMySQL()
    mycol = connectToMongoDB()
    min_goods_id, max_goods_id = getBasicInfo(cursor)
    
    # id time goods user  
    update_goods_sql = 'UPDATE goods SET goods_id = %s WHERE goods_id = %s'
    update_tags_sql = 'UPDATE tags SET goods = %s WHERE goods = %s'
    logging.info(''.join(['--------Start insert data into MySQL table--------']))
    
    cursor.execute('SET FOREIGN_KEY_CHECKS = 0') # 解除外键设置
    tmp1, tmp2 = max_goods_id + 1, max_goods_id + 2 # 暂时写入的大主键
    for i in range(size):
        
        num1 = random.randint(min_goods_id, max_goods_id)
        num2 = random.randint(min_goods_id, max_goods_id)
        
        try:
            logging.info('exchange id = ' + str(num1) + ' and id = ' + str(num2))
            cursor.execute(update_goods_sql, [tmp1, num1])
            cursor.execute(update_goods_sql, [tmp2, num2])
            cursor.execute(update_tags_sql, [tmp1, num1])
            cursor.execute(update_tags_sql, [tmp2, num2])
            connection.commit()
            
            cursor.execute(update_goods_sql, [num2, tmp1])
            cursor.execute(update_goods_sql, [num1, tmp2])
            cursor.execute(update_tags_sql, [num2, tmp1])
            cursor.execute(update_tags_sql, [num1, tmp2])
            connection.commit()
            
            logging.info('exchange this in MongoDB...')
            mycol.update_many({"goods_id": num1}, {"$set": {"goods_id": tmp1}})
            mycol.update_many({"goods_id": num2}, {"$set": {"goods_id": tmp2}})
            mycol.update_many({"goods_id": tmp1}, {"$set": {"goods_id": num2}})
            mycol.update_many({"goods_id": tmp2}, {"$set": {"goods_id": num1}})
            logging.info('Successfully in the ' + str(i) + ' th execution')
        except Exception as e:
            logging.exception(e)
            connection.rollback()
    cursor.execute('SET FOREIGN_KEY_CHECKS = 1') # 设置外键约束
    connection.commit()
    cursor.close()
    connection.close()
    logging.info(''.join(['--------Finish load table into MySQL--------']))

logging.basicConfig(format = '%(asctime)s - %(pathname)s[line:%(lineno)d]\n%(levelname)s: %(message)s',
                    level = logging.DEBUG)
disrapt()