package com.example.demo.util;


import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class IscUtils {

    private static  final Logger logger=LoggerFactory.getLogger(IscUtils.class);
    public static synchronized String  getResult(String ip,String appkey,String appsecret,String url,JSONObject jsonBody){

        String result="";
        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        try {
            ArtemisConfig.host =ip; // artemis网关服务器ip端口
            ArtemisConfig.appKey = appkey;  // 秘钥appkey
            ArtemisConfig.appSecret = appsecret;// 秘钥appSecret

            /**
             * STEP2：设置OpenAPI接口的上下文
             */
            final String ARTEMIS_PATH = "/artemis";

            /**
             * STEP3：设置接口的URI地址
             */
            final String previewURLsApi = ARTEMIS_PATH +url;
            Map<String, String> path = new HashMap<String, String>(2) {
                {
                    put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
                }
            };

            /**
             * STEP4：设置参数提交方式
             */
            String contentType = "application/json";

            /**
             * STEP5：组装请求参数
             */


            String body = jsonBody.toJSONString();
            System.out.println(body);

            /**
             * STEP6：调用接口
             */
            result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType , null);// post请求application/json类型参数


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
