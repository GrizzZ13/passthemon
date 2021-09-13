import base64, requests, scrapy
from scrapy.crawler import CrawlerProcess

# python confucius_old_book_network.py
class BookInfoSpider(scrapy.Spider):
    name = 'books'
    
    custom_settings = {
        'CONCURRENT_REQUESTS': 12500,
        'CONCURRENT_REQUESTS_PER_DOMAIN': 50,
        'CONCURRENT_REQUESTS_PER_IP': 50,
        'RANDOMIZE_DOWNLOAD_DELAY': 0,
        'DOWNLOAD_DELAY': 0
    }
    start_urls = [
        'https://book.kongfz.com/Czihua/'
    ]
    idx, max = 1, 200
    
    # rm confucius_old_book_network.csv; scrapy runspider confucius_old_book_network.py -o confucius_old_book_network.csv -s FEED_EXPORT_ENCODING=GB2312
    def parse(self, response):
        for item in response.css('div.item.clearfix'):
            yield {
                'name':         item.xpath('div[2]/div[2]/div[1]/div[1]/span[2]/text()').extract_first(),
                'category':     item.xpath('div[2]/div[2]/div[1]/div[2]/span[2]/text()').extract_first(),
                'texture':      item.xpath('div[2]/div[2]/div[1]/div[3]/span[2]/text()').extract_first(),
                'description':  item.xpath('div[2]/div[1]/a/text()').extract_first(),
                'address':      item.xpath('div[2]/div[3]/div[1]/div[2]/text()').extract_first(),
                'delivery_time':item.xpath('div[2]/div[3]/div[2]/span[1]/i/text()').extract_first(),
                'success_rate': item.xpath('div[2]/div[3]/div[2]/span[2]/i/text()').extract_first(),
                'quality':      item.xpath('div[3]/div[1]/div[1]/text()').extract_first(),
                'price':        item.xpath('div[3]/div[1]/div[2]/span[2]/text()').extract_first(),
                'upload_time':  item.xpath('div[3]/div[4]/span[1]/text()').extract_first(),
                'photo_url':    item.xpath('div[1]/a/img/@src').extract_first(),
                'photo_base64': base64.b64encode(requests.get(item.xpath('div[1]/a/img/@src').extract_first()).content),
            }
            
        BookInfoSpider.idx = BookInfoSpider.idx + 1
        next_page = ''.join([BookInfoSpider.start_urls[0], 'w', str(BookInfoSpider.idx), '/'])
        if BookInfoSpider.idx > BookInfoSpider.max: return
        if next_page is not None:
            print(next_page)
            # print("--------------------")
            yield scrapy.Request(next_page, callback=self.parse, dont_filter = False)
    
process = CrawlerProcess(settings = {
    'FEED_FORMAT': 'csv',
    'FEED_URI': 'confucius_old_book_network-finish.csv',
    'FEED_EXPORT_ENCODING': 'GB2312',
})
process.crawl(BookInfoSpider)
process.start()