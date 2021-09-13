package com.backend.passthemon.consumer;

import org.springframework.stereotype.Component;
import com.backend.passthemon.dao.mongo.ImagesDao;
import com.backend.passthemon.utils.reviewUtils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 图像审核接口
 */
@Component
public class ImgCensor {
    @Autowired
    private ImagesDao imagesDao;

    private List<String> accessTokens = new ArrayList<>();
    private Integer size = 0;

    @PostConstruct
    public void getAccessToken() throws Exception {
        System.out.println("Get Text Review AccessToken...");
        // 启动时获取access_token
        accessTokens = AuthService.getAuth();
        size = accessTokens.size();
    };

    public List<String> ImgCensor(List<String> imgStrList, Integer who){
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined";
        List<String> resultList=new ArrayList<>();
        try{
            for (String imgStr : imgStrList) {
                String imgParam = URLEncoder.encode(imgStr, "UTF-8");
                String param = "image=" + imgParam;
                String result = HttpUtil.post(url, accessTokens.get(who % size), param);
                System.out.println(result);
                // ex: {"conclusion":"合规","log_id":16288628407963036,"isHitMd5":false,"conclusionType":1}
                resultList.add(result);
            };
            return resultList;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<String> ImgCensor(List<String> imgStrList) {
        /* 随机调用任一一个接口 */
        return ImgCensor(imgStrList, (int) Math.random() * size);
    };

    public List<String> ImgCensor(Integer goods_id, Integer display_order, Integer who) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined";
        try {
            // 本地文件路径
//            String filePath = "[本地文件路径]";
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
//            String imgStr = Base64Util.encode(imgData);
            /* 获取mongodb中字符串 */
            List<String> imgStrList = new ArrayList<>();
            if (display_order == -1) {
                imgStrList = imagesDao.findImagesByGoodsId(goods_id).getImageList();
            } else {
                String image = imagesDao.findImagesByGoodsId(goods_id).getImageList().get(display_order - 1);
                if (image != null) imgStrList.add(image);
            }
            /* 获得答案 */
            List<String> resultList = new ArrayList<>();

            for (String imgStr : imgStrList) {
                String imgParam = URLEncoder.encode(imgStr, "UTF-8");
                String param = "image=" + imgParam;
                String result = HttpUtil.post(url, accessTokens.get(who % size), param);
                System.out.println(result);
                // ex: {"conclusion":"合规","log_id":16288628407963036,"isHitMd5":false,"conclusionType":1}
                resultList.add(result);
            };

            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<String> ImgCensor(Integer goods_id, Integer display_order) {
        /* 随机调用任一一个接口 */
        return ImgCensor(goods_id, display_order, (int) Math.random() * size);
    }

    public List<String> ImgListCensor(List<Integer> goods_id_list, List<Integer> display_order_list, Integer who) {
        List<String> resultList = new ArrayList<>();
        int len = goods_id_list.size();
        for (int i = 0; i < len; i++)
            resultList.addAll(ImgCensor(goods_id_list.get(i), display_order_list.get(i), who % size));
        return resultList;
    }
    public List<String> ImgListCensor(List<Integer> goods_id_list, List<Integer> display_order_list) {
        /* 随机调用任一一个接口 */
        return ImgListCensor(goods_id_list, display_order_list, (int) Math.random() * size);
    }
}
