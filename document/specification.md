Controller参数传递

1. (@RequestParam("xxx") BasicClass<T> a)

   ```java
   @RequestMapping(value="/addBook", method = RequestMethod.POST)
       public Msg addBook(@RequestParam("isbn") String isbn,
                          @RequestParam("name") String name,
                          @RequestParam("type") String type,
                          @RequestParam("author") String author,
                          @RequestParam("price") BigDecimal price,
                          @RequestParam("description") String description,
                          @RequestParam("inventory") Integer inventory,
                          @RequestParam("image") String image){
           log.info("add book");
           return bookService.addBook(isbn, name, type, author,
                                      price, description, inventory, image);
       }
   ```

   

2. 含有复杂数据结构的JSON使用JSONObject传递

   alibaba.fastjson

   ```xml
   <dependency>
   	<groupId>com.alibaba</groupId>
   	<artifactId>fastjson</artifactId>
   	<version>1.2.76</version>
   </dependency>
   ```

   example

   ```java
   @RequestMapping(value = "/order/generateOrder", method = RequestMethod.POST)
       public Msg generateOrder(@RequestBody String JSONStr) throws ParseException {
           System.out.println(JSONStr);
           JSONObject param = JSONObject.parseObject(JSONStr);
           log.info("invoked in order controller, generate order");
   
   
           Integer userId = (Integer) param.getInteger("userId");
           String  orderTimeStr = (String) param.getString("orderTime");
   
           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
           java.sql.Date orderTime = new java.sql.Date(simpleDateFormat.parse(orderTimeStr).getTime());
   
           JSONArray orderItems_json = param.getJSONArray("orderItems");
           List<OrderItem> orderItems = new LinkedList<>();
           BigDecimal price;
           for(int i = 0; i < orderItems_json.size(); i++){
               JSONObject target_json = (JSONObject)orderItems_json.get(i); //这里不能是get(i),get(i)只会得到键值对
               Integer bookId = (Integer) target_json.getInteger("bookId");
               Integer bookNum = (Integer) target_json.getInteger("bookNum");
               OrderItem orderItem = new OrderItem();
               Book book = bookService.findBookById(bookId);
               Integer inventory = book.getInventory();
               bookNum = (bookNum <= inventory) ? bookNum : inventory;
               if(bookNum > 0){
                   orderItem.setBookNum(bookNum);
                   orderItem.setBook(book);
                   orderItems.add(orderItem);
               }
               book.setInventory(inventory-bookNum);
               bookService.updateBook(book);
               cartService.removeCartItem(userId, bookId);
           }
           orderService.generateOrder(userId, orderTime, orderItems);
           return MsgUtil.makeMsg(MsgCode.SUCCESS);
       }
   ```

   