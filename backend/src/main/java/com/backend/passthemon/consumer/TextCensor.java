package com.backend.passthemon.consumer;

import com.backend.passthemon.utils.reviewUtils.HttpUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 文本审核接口
 */
@Component
public class TextCensor {

    private List<String> accessTokens = new ArrayList<>();
    private Integer size = 0;
    @PostConstruct
    public void getAccessToken() throws Exception {
        System.out.println("Get Text Review AccessToken...");
        // 启动时获取access_token
        accessTokens = AuthService.getAuth();
        size = accessTokens.size();
    };

    public String TextCensor(String text, Integer who) {
        try {
            String param = "text=" + text;
            String url = "https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined";
            String result = HttpUtil.post(url, accessTokens.get(who % size), param);
            System.out.println(result);
            System.out.println(accessTokens.get(who % size));
            // {"conclusion":"不合规","log_id":16288633345374445,"data":[{"msg":"存在低俗辱骂不合规","conclusion":"不合规","hits":[{"wordHitPositions":[],"probability":0.78747964,"datasetName":"百度默认文本反作弊库","words":[],"modelHitPositions":[[0,2,0.7875]]}],"subType":5,"conclusionType":2,"type":12}],"isHitMd5":false,"conclusionType":2}
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    };
    public String TextCensor(String text) {
        /* 随机调用任一一个接口 */
        return TextCensor(text, (int) Math.random() * size);
    };

    public List<String> TextListCensor(List<String> textList, Integer who) {
        List<String> resultList = new ArrayList<>();
        for (String text : textList) {
            resultList.add(TextCensor(text, who % size));
        };
        return resultList;
    };
    public List<String> TextListCensor(List<String> textList) {
        /* 随机调用任一一个接口 */
        return TextListCensor(textList, (int) Math.random() * size);
    };
}
