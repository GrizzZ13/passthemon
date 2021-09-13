# MySQL table demands
# demands_id description ideal_price name num state upload_time user
# data unrepeatable

import time, random
import pymysql
from pymysql import NULL, cursors
import logging
from configuration import db_user, db_password, generateRandomTimestamp, connectToMySQL

size = 30000
isAddTo = True
table = 'demands'

def contains(main_str, substrlist):
    for ele in substrlist:
        if main_str.find(ele) != -1: return True
    return False

def judgeCategory(name):
    clothes_list = ['包', '裤', '衣', '袖', '衫', '羽绒', '恤', '棉', '打底', '鞋',
                        ]
    drinks_and_snacks_list = ['肉', '牛', '鸡', '食', '水果', '肠', '饮料']
    electronic_products = ['手机', '电脑', '电视', '洗衣机', '冰箱', '充电']
    game_props = ['游戏']
    paper = ['书']
    daily_use = ['瓶', '婴', '香水', '兰蔻', '笔', '球', '化妆']
    other_kinds = []
    
    tag_name = 7
    if contains(name, clothes_list): tag_name = 1
    elif contains(name, drinks_and_snacks_list): tag_name = 2
    elif contains(name, electronic_products): tag_name = 3
    elif contains(name, game_props): tag_name = 4
    elif contains(name, paper): tag_name = 5
    elif contains(name, daily_use): tag_name = 6
    
    print(tag_name)
    return tag_name

def getBasicInfo(cursor):
    # 获取用户id最大值
    cursor.execute('SELECT MAX(user_id) FROM user')
    max_user_id = int(str(cursor.fetchone())[1:-2])
    # 获取用户id最小值
    cursor.execute('SELECT MIN(user_id) FROM user')
    min_user_id = int(str(cursor.fetchone())[1:-2])
    # 获取商品id最大值
    cursor.execute('SELECT MAX(goods_id) FROM goods')
    max_goods_id = int(str(cursor.fetchone())[1:-2])
    # 获取商品id最小值
    cursor.execute('SELECT MIN(goods_id) FROM goods')
    min_goods_id = int(str(cursor.fetchone())[1:-2])
    
    print('min_user_id : {} max_user_id : {} min_goods_id : {} max_goods_id : {}'
          .format(min_user_id, max_user_id, min_goods_id, max_goods_id))
    return min_user_id, max_user_id, min_goods_id, max_goods_id

def generate():
    cursor, connection = connectToMySQL()
    min_user_id, max_user_id, min_goods_id, max_goods_id = getBasicInfo(cursor)
    
    start_id = 1
    if isAddTo is False: cursor.execute(''.join(['DELETE FROM ', table]))
    else:
        # 获取id最大值
        cursor.execute(''.join(['SELECT MAX(demands_id) FROM ', table]))
        max_id_str = cursor.fetchone()[0]
        max_id = int(max_id_str) if max_id_str != 'None' else 0
        start_id = max_id + 1
    # id time goods user  
    sql = ''.join(['insert into ', table, ' values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)'])
    
    logging.info(''.join(['--------Start insert data into MySQL table ', table, '--------']))
    unrepeatable = {}
    name_template = ['衣服', '裤子', '鞋子', '饮料', '水', '食物', '电子游戏', '电子产品', '电脑', '书籍', '教科书', '香水', '羽毛球', '篮球', '水杯', '台灯', '闹钟']
    for i in range(start_id, start_id + size):
        demands_id = i
        description = '要特别好的, 耐用的'
        ideal_price = random.randint(1, 1000)
        name = random.sample(name_template, 1)[0]
        num = random.randint(1, 4)
        state = 1
        upload_time = generateRandomTimestamp()
        user = random.randint(min_user_id, max_user_id)
        if unrepeatable.get(''.join([str(name), str(user)])) == None: unrepeatable[''.join([str(name), str(user)])] = True
        else: 
            while (unrepeatable.get(''.join([str(name), str(user)])) != None):
                name = random.sample(name_template, 1)[0]
                user = random.randint(min_user_id, max_user_id)
        attrition = random.randint(-5, -1)
        category = judgeCategory(name)
        # 准备参数
        args = [demands_id, description, ideal_price, name, num, state, upload_time, user, attrition, category]
        try:
            cursor.execute(sql, args)
            logging.info(args)
            connection.commit()
        except Exception as e:
            logging.exception(e)
            connection.rollback()
    connection.commit()
    cursor.close()
    connection.close()
    logging.info(''.join(['--------Finish load table ', table, ' into MySQL--------']))

logging.basicConfig(format = '%(asctime)s - %(pathname)s[line:%(lineno)d]\n%(levelname)s: %(message)s',
                    level = logging.DEBUG)
generate()