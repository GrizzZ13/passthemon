# 修复数据库tag存入goods中保存格式问题

from configuration import db_user, db_password, generateRandomTimestamp, connectToMySQL
import pymysql, logging, random

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

def fixMySQLTags():
    cursor, connection = connectToMySQL()
    min_goods_id, max_goods_id = getBasicInfo(cursor)
    update_sql = 'UPDATE goods SET attrition = %s WHERE goods_id = %s'
    logging.info('Start change tags...')
    for goods_id in range(min_goods_id, max_goods_id + 1):
        attrtion = random.randint(-5, -1)
        cursor.execute(update_sql, [attrtion, goods_id])
        connection.commit()
        logging.info('Finish load goods ' + str(goods_id))
        
logging.basicConfig(format = '%(asctime)s - %(pathname)s[line:%(lineno)d]\n%(levelname)s: %(message)s',
                    level = logging.DEBUG)

fixMySQLTags()
        
    