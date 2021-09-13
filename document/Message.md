```json
{
    "message": "Invalid authentication!",
    "status": -10
}
```

```json
{
    "data": {
    	...
    },
    "message": "Access token will update",
    "status": 20,
    "token": "token"
}
```

```json
{
    "data": {
    	...
    },
    "message": "All OK!",
    "status": 1
}
```

```java
public class MsgUtil {
    public static final Integer TOKEN_VERIFIED = 1;
    public static final Integer ALL_OK = 1;
    public static final Integer TOKEN_UPDATE = 20;
    public static final Integer LOGIN_SUCCEED = 1;

    public static final Integer TOKEN_INVALID = -10;
    public static final Integer TOKEN_EXPIRED = -20;
    public static final Integer WRONG_PASSWORD = -30;
    public static final Integer ACCOUNT_NOT_EXIST = -40;
    public static final Integer ACCOUNT_ALREADY_EXIST = -50;
    public static final Integer CODE_EXPIRED = -60;
    public static final Integer ERROR = -100;

    public static final String TOKEN_VERIFIED_MSG = "token verification passed";
    public static final String TOKEN_EXPIRED_MSG = "Authentication expired, please login again!";
    public static final String TOKEN_INVALID_MSG = "Invalid authentication!";
    public static final String TOKEN_UPDATE_MSG = "Access token will update";

    public static final String LOGIN_SUCCEED_MSG = "login successfully";
    public static final String WRONG_PASSWORD_MSG = "wrong email or password";
    public static final String ACCOUNT_NOT_EXIST_MSG = "Account does not exist!";
    public static final String ACCOUNT_ALREADY_EXIST_MSG = "Account has already existed!";
    public static final String ALL_OK_MSG = "All OK!";
    public static final String ERROR_MSG = "Error!";
    public static final String CODE_EXPIRED_MSG = "Verification Code Expired!";
}
```

