**mysql**

```mysql
user table{
primary int user_id,
varchar(255) email,
varchar(255) password, 
varchar(255) username, 	
int state,
int credit,
int gender,
varchar(64) phone,
}
goods table{
primary int goods_id,
decimal(10,2) price,
varchar(255) name,
int inventory,
varchar(512) description,
datetime upload_time,
datetime sold_time,
int state,    //是否售出等等
}
order table{
primary int order_id,
foreign key buyer,
foreign key seller,
foreign key goods_id,
int num,
decimal(10,2) saleroom
}
follow table{
 primary int id,
 foreign key int user,
 int follower;
}
image table{
primary int id,
foreign key int goods_id,
base64 image
}
history table{
primary int id,
foreign key int user_id,
foreign key int goods_id,
datetime time
}
favorite table{
primary int id,
foreign key int user_id,
foreign key int goods_id,
}

```

