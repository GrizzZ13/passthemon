# coding: UTF-8
# 执行所有测试命令
import sys, os, logging, pymysql, pymongo, shutil
import subprocess, paramiko
from importlib import reload
from subprocess import Popen, PIPE

# 连接mysql数据库
def connectToMySQL(database_type):
    mysql_user = "root"
    mysql_password = "200176" if database_type else "SJTUse2021"
    hostname = "localhost" if database_type else "123.60.78.242"
    connection = pymysql.connect(host = hostname, #host属性
                        user = mysql_user, #用户名 
                        password = mysql_password,  #此处填登录数据库的密码
                        db = 'mysql', #数据库名
                        )
    # 创建光标对象，一个连接可以有很多光标，一个光标跟踪一种数据状态。
    # 光标对象作用是：创建、删除、写入、查询等等
    cursor = connection.cursor()
    return cursor, connection
# 连接mongo数据库
def connectToMongo(database_type):
    hostname = "localhost" if database_type else "123.60.78.242"
    myclient = pymongo.MongoClient("mongodb://" + hostname + ":27017/")
    mydb = myclient["passthemon"]
    mycol = mydb["Images"]
    return mycol
def exeDatabase(database = 0):
    # 操作数据库
    database_type = database
    logging.info("You have chosen to execute database on " + "local" if database_type else "remote" + " server...")
    
    if database_type == 0:
        hostname = "123.60.78.242"
        port = 22
        username = "root"
        password = "SJTUse2021"
        ssh = paramiko.SSHClient() #调用paramiko模块下的SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy()) # 允许连接不在know_hosts文件中的主机
        ssh.load_system_host_keys() #加载本地的known_hosts文件
        ssh.connect(hostname,port,username,password)  #连接远程主机，端口号，用户名，密码
    
    # 删除原有mysql数据库
    cursor, connection = connectToMySQL(database_type)
    cursor.execute('drop database passthemon;')
    cursor.execute('create database passthemon;')
    connection.commit()
    cursor.close()
    connection.close()
    
    # 重新导入mysql数据库
    mysql_user = "root" if database_type else "root"
    mysql_password = "200176" if database_type else "SJTUse2021"
    mysql_database = "passthemon"
    mysql_data = "./data/passthemon-8.sql" if database_type else "/usr/local/passthemon/passthemon-8.sql"
    
    cur_command = 'mysql -u%s -p%s -D%s < %s' %(mysql_user, mysql_password, mysql_database, mysql_data)
    if database_type == 0:
        stdin,stdout,stderr = ssh.exec_command(cur_command) 
        logging.info(stdout.read())
        logging.info(stderr.read())
    elif database_type == 1:
        subprocess.Popen(cur_command, shell=True)
    logging.info("Import MySQL database successfully...")
    
    # 重新导入mongo数据库
    db = "passthemon"
    collection = "Images"
    mongo_data = "../../passthemon-3.json" if database_type else "/usr/local/passthemon/imagesNewFixed.json"
    mycol = connectToMongo(database_type)
    mycol.delete_many({})
    
    cur_command = "mongoimport --db %s --collection %s --file %s" %(db, collection, mongo_data)
    if database_type == 0:
        stdin,stdout,stderr = ssh.exec_command(cur_command)
        logging.info(stdout.read())
        logging.info(stderr.read())
        ssh.close()
    elif database_type == 1:
        subprocess.Popen(cur_command, shell=True)
    logging.info("Import Mongo database successfully...")

def exeMysql(database = 0):
    # 操作数据库
    database_type = database
    logging.info("You have chosen to execute database on " + "local" if database_type else "remote" + " server...")
    
    if database_type == 0:
        hostname = "123.60.78.242"
        port = 22
        username = "root"
        password = "SJTUse2021"
        ssh = paramiko.SSHClient() #调用paramiko模块下的SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy()) # 允许连接不在know_hosts文件中的主机
        ssh.load_system_host_keys() #加载本地的known_hosts文件
        ssh.connect(hostname,port,username,password)  #连接远程主机，端口号，用户名，密码
    
    # 删除原有mysql数据库
    cursor, connection = connectToMySQL(database_type)
    cursor.execute('drop database passthemon;')
    cursor.execute('create database passthemon;')
    connection.commit()
    cursor.close()
    connection.close()
    
    # 重新导入mysql数据库
    mysql_user = "root" if database_type else "root"
    mysql_password = "200176" if database_type else "SJTUse2021"
    mysql_database = "passthemon"
    mysql_data = "./data/passthemon-8.sql" if database_type else "/usr/local/passthemon/passthemon-8.sql"
    
    cur_command = 'mysql -u%s -p%s -D%s < %s' %(mysql_user, mysql_password, mysql_database, mysql_data)
    if database_type == 0:
        stdin,stdout,stderr = ssh.exec_command(cur_command) 
        logging.info(stdout.read())
        logging.info(stderr.read())
    elif database_type == 1:
        subprocess.Popen(cur_command, shell=True)
    logging.info("Import MySQL database successfully...")

def exeMongo(database = 0):
    # 操作数据库
    database_type = database
    logging.info("You have chosen to execute database on " + "local" if database_type else "remote" + " server...")
    
    if database_type == 0:
        hostname = "123.60.78.242"
        port = 22
        username = "root"
        password = "SJTUse2021"
        ssh = paramiko.SSHClient() #调用paramiko模块下的SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy()) # 允许连接不在know_hosts文件中的主机
        ssh.load_system_host_keys() #加载本地的known_hosts文件
        ssh.connect(hostname,port,username,password)  #连接远程主机，端口号，用户名，密码
    
    # 重新导入mongo数据库
    db = "passthemon"
    collection = "Images"
    mongo_data = "../../passthemon-3.json" if database_type else "/usr/local/passthemon/imagesNewFixed.json"
    mycol = connectToMongo(database_type)
    mycol.delete_many({})
    
    cur_command = "mongoimport --db %s --collection %s --file %s" %(db, collection, mongo_data)
    if database_type == 0:
        stdin,stdout,stderr = ssh.exec_command(cur_command)
        logging.info(stdout.read())
        logging.info(stderr.read())
        ssh.close()
    elif database_type == 1:
        subprocess.Popen(cur_command, shell=True)
    logging.info("Import Mongo database successfully...")

def testAll():
    
    reload(sys)

    version = 5 # 版本号
    threadCount = 2000 # 线程数
    logging.info("Thread count = " + str(threadCount))

    result_data_file = "testdata-%s-%s.csv" %(str(version), threadCount) # 测试结果csv文件
    output = "report-%s-%s" %(str(version), threadCount) # 测试结果html文件
                
    params = [
            {
                "file_path": "./demand-test",
                "folder": "demand-test",
                "jmx_file": "PassThemOnDemandTest.jmx",
                "is": False, # 是否测试
            },
            {
                "file_path": "./favorite-test",
                "folder": "favorite-test",
                "jmx_file": "PassThemOnFavoriteTest.jmx",
                "is": False, # 是否测试
            },
            {
                "file_path": "./goods-test",
                "folder": "goods_test",
                "jmx_file": "PassThemOnGoodsTest.jmx",
                "is": True, # 是否测试
            },
            {
                "file_path": "./history-test",
                "folder": "history-test",
                "jmx_file": "PassThemOnHistoryTest.jmx",
                "is": False, # 是否测试,不需要，但不全
            },
            {
                "file_path": "./image-test",
                "folder": "image-test",
                "jmx_file": "PassThemOnImageTest.jmx",
                "is": False, # 是否测试,不需要
            },
            {
                "file_path": "./order-test",
                "folder": "order-test",
                "jmx_file": "PassThemOnOrderTest.jmx",
                "is": False, # 是否测试
            },
            {
                "file_path": "./wants-test",
                "folder": "wants-test",
                "jmx_file": "PassThemOnWantsTest.jmx",
                "is": False, # 是否测试
            },
            {
                "file_path": "./user-test",
                "folder": "user-test",
                "jmx_file": "PassThemOnUserTest.jmx",
                "is": False, # 是否测试
            }
        ]

    basic_command = "jmeter -JthreadCount=%s -n -t %s -l %s -e -o %s" # 测试基本命令

    for param in params:
        # if param["file_path"] is"./image-test":
        #     exeMongo(0)
        # else:
        #     exeMysql(0)
        #exeMysql(0)
        if param["is"] is False: 
            logging.info("Skip test " + param["folder"] + "...")
            continue
        logging.info("Start test " + param["folder"] + "...")
        jmx_file = '/'.join([param["file_path"], param["jmx_file"]])
        result_file = '/'.join([param["file_path"], result_data_file])
        output_file = '/'.join([param["file_path"], output])
        cur_command = basic_command %(threadCount, jmx_file, result_file, output_file)
        
        # 检查是否存在该测试版本
        if os.path.exists(jmx_file) is False: 
            logging.error("Error: " + jmx_file + " not exists! Continue to the next test...")
            continue
        if os.path.exists(result_file) is True:
            logging.warn("Warning: " + result_file + " exists!")
            while True:
                ch = input("Whether to delete original " + result_file + "?(Y/N)")
                if ch == "Y" or ch == 'y':
                    with open(result_file, 'rb') as f:
                        f.close()
                        os.remove(result_file)
                    break
                if ch == "N" or ch == 'n':
                    break
                else: continue
            if ch == "N" or ch == 'n': continue
        if os.path.exists(output_file) is True:
            logging.warn("Warning: " + output_file + " exists!")
            while True:
                ch = input("Whether to delete original " + output_file + "?(Y/N)")
                if ch == "Y" or ch == 'y':
                    shutil.rmtree(output_file)
                    break
                if ch == "N" or ch == 'n':
                    break
                else: continue
            if ch == "N" or ch == 'n': continue

        # subprocess.Popen(cur_command, shell=True)
        os.system(cur_command)   
        logging.info("End test " + param["folder"] + "...")
        #exeDatabase(0)


logging.basicConfig(format = '%(asctime)s - %(pathname)s[line:%(lineno)d]\n%(levelname)s: %(message)s',
                    level = logging.DEBUG)

#exeDatabase(0) # 0是部署服务器, 1是本地服务器, 默认0
testAll()