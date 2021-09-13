# 修复数据库tag存入goods中保存格式问题

from configuration import db_user, db_password, generateRandomTimestamp, connectToMySQL
import pymysql, logging

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
    search_sql = 'SELECT tag_name FROM tags WHERE goods = %s'
    update_sql = 'UPDATE goods SET category = %s WHERE goods_id = %s'
    logging.info('Start change tags...')
    for goods_id in range(min_goods_id, max_goods_id + 1):
        cursor.execute(search_sql, goods_id)
        tag = cursor.fetchone()[0]
        print(tag)
        
        # break
    
        cursor.execute(update_sql, [tag, goods_id])
        connection.commit()
        logging.info('Finish load goods ' + str(goods_id))
        
logging.basicConfig(format = '%(asctime)s - %(pathname)s[line:%(lineno)d]\n%(levelname)s: %(message)s',
                    level = logging.DEBUG)

fixMySQLTags()
        
    