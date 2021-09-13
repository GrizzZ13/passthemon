package com.backend.passthemon.utils.authorityutils;

import java.util.List;

public class AuthorityUtil {
    //第一个参数为接口需要的权限，第二个参数为用户具有的权限，当values为authorList的子集时返回true
    public static boolean containCheck(List<String> values,List<String> authorList){
      authorList.retainAll(values);
        return authorList.containsAll(values) && values.containsAll(authorList);
    }
    //第一个参数为接口需要的权限，第二个参数为用户具有的权限，当两个集合存在交集时返回true
    public static boolean haveCheck(List<String> values,List<String> authorList){
        authorList.retainAll(values);
        return authorList.size() > 0;
    }
}
