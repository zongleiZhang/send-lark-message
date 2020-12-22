package com.ada.lark;

import com.ada.lark.sendMsg.SendFgMsg;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class App {

    public static void main(String[] args) throws Exception{
//        SendFgMsg.sendToMult("title",
//                "content",
//                "actor",
//                "time",
//                "https://www.yuque.com/hc2sql/nc8f1x/wg9tgr",
//                "remark");
        SendFgMsg.sendToOne("title",
                "content",
                "actor",
                "time",
                "https://www.yuque.com/hc2sql/nc8f1x/wg9tgr",
                "remark",
                "5453");
//        sendMsg();

    }

    private static void sendMsg() throws IOException {
        String url = "http://u.erpit.cn/api/message/send-user";//"http://u.erpit.cn/api/message/send";
        JSONObject TEMPLATE = new JSONObject();

        TEMPLATE.put("secret", "2924132fca40ff559f3f7b3612896e3e");
//        TEMPLATE.put("app_key", "11760262134d68422daadc502265e709");
        TEMPLATE.put("uid", "5453");
        TEMPLATE.put("template_id", "5uZIvSm5GAdUR1X25HNpjuOp6jSiL88v4opn4a4GLa0");
        TEMPLATE.put("url", "www.baidu.com");
        JSONObject jsonObject1 = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("value", "first");
        jsonObject2.put("color", "#173177");
        jsonObject1.put("first", jsonObject2);
        jsonObject2 = new JSONObject();
        jsonObject2.put("value", "keyword1");
        jsonObject2.put("color", "#173177");
        jsonObject1.put("keyword1", jsonObject2);
        jsonObject2 = new JSONObject();
        jsonObject2.put("value", "keyword2");
        jsonObject2.put("color", "#173177");
        jsonObject1.put("keyword2", jsonObject2);
        jsonObject2 = new JSONObject();
        jsonObject2.put("value", "keyword3");
        jsonObject2.put("color", "#173177");
        jsonObject1.put("keyword3", jsonObject2);
        jsonObject2 = new JSONObject();
        jsonObject2.put("value", "remark");
        jsonObject2.put("color", "#173177");
        jsonObject1.put("remark", jsonObject2);
        TEMPLATE.put("data", jsonObject1);

        String body = "";

        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        StringEntity s = new StringEntity(TEMPLATE.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));
        //设置参数到请求对象中
        httpPost.setEntity(s);
        System.out.println("请求地址："+url);
//        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "UTF-8");
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        System.out.println(body);
    }


}
