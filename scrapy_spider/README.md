基于 Scrapy 框架的对当当网商品信息数据的爬虫

PassThemOnSpider

* MySQL
export : mysqldump -u 用户名 -p 密码 数据库名 表名 > 导出的文件名
import : source 文件名
* MongoDB
export : mongoexport -d 数据库名 -c 集合名 -o 输出文件路径 --type csv/json
import : mongoimport -d 数据库名 -c 集合名 --file 导入文件路径 --type csv/json

