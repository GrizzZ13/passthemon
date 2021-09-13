```
public Dto controller(@RequestParams String a){
	
	...
	
	return dto;
}
```



```
public Msg controller(@RequestParams String a){
	...
	JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(dto));
	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	Msg msg = MsgUtil.makeMsg(MsgUtil.ALL_OK, MsgUtil.ALL_OK_MSG, jsonnObject, request);
	return msg;
}
```

