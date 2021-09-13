# MySQL table demands
# demands_id description ideal_price name num state upload_time user
# data unrepeatable

import time, random
import pymysql
from pymysql import NULL, cursors
import logging
from configuration import db_user, db_password, generateRandomTimestamp, connectToMySQL

size = 0
isAddTo = True
table = 'tags'

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

def contains(main_str, substrlist):
    for ele in substrlist:
        if main_str.find(ele) != -1: return True
    return False

def generate():
    cursor, connection = connectToMySQL()
    min_goods_id, max_goods_id = getBasicInfo(cursor)
    
    start_id = 1
    if isAddTo is False: cursor.execute(''.join(['DELETE FROM ', table]))
    else:
        # 获取id最大值
        cursor.execute(''.join(['SELECT MAX(id) FROM ', table]))
        max_id_str = str(cursor.fetchone())[1:-2]
        max_id = int(max_id_str) if max_id_str != 'None' else 0
        start_id = max_id + 1
    # id time goods user  
    insert_sql = ''.join(['insert into ', table, ' values(%s,%s,%s)'])
    
    logging.info(''.join(['--------Start insert data into MySQL table ', table, '--------']))
    unrepeatable = {}
    # 1; //服饰装扮
    # 2; //饮料食品
    # 3; // 电子产品
    # 4; //游戏
    # 5; // 书籍
    # 6; // 日常用品
    # 7; // 其他用品
    
    for i in range(start_id, max_goods_id):
        id = goods = i
        find_sql = 'SELECT name FROM goods WHERE goods_id=' + str(i)
        cursor.execute(find_sql)
        name = str(cursor.fetchone())
        print(name)
        
        # break
        
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
        
        # break
        
        # 准备参数
        args = [id, tag_name, goods]
        try:
            cursor.execute(insert_sql, args)
            logging.info(args)
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