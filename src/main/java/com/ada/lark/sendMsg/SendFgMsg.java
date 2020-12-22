package com.ada.lark.sendMsg;

import com.ada.lark.model.User;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 向微信公众号飞鸽快信中发送语雀通知
 */
public class SendFgMsg {

    private static final Queue<HttpSource> groupSources = new ArrayDeque<>(5);

    private static final Queue<HttpSource> singleSources = new ArrayDeque<>(5);

    static {
        for (int i = 0; i < 5; i++) {
            groupSources.add(new HttpSource(true));
            singleSources.add(new HttpSource(false));
        }
    }



    public static void sendToMult(String title,
                                  String content,
                                  String actor,
                                  String time,
                                  String url,
                                  String remark) throws Exception {
        HttpSource source = groupSources.poll();
        while (source == null){
            Thread.sleep(50L);
            source = groupSources.poll();
        }
        sendMsg(title, content, actor, time, url, remark, source);
    }


    public static void sendToOne(String title,
                                 String content,
                                 String actor,
                                 String time,
                                 String url,
                                 String remark,
                                 String toUid) throws Exception {
        HttpSource source = singleSources.poll();
        while (source == null){
            Thread.sleep(50L);
            source = singleSources.poll();
        }
        source.template.replace("uid", toUid);
        sendMsg(title, content, actor, time, url, remark, source);
    }

    private static void sendMsg(String title,
                                String content,
                                String actor,
                                String time,
                                String url,
                                String remark,
                                HttpSource source) throws IOException {
        source.template.replace("url", url);
        JSONObject data = source.template.getJSONObject("data");
        data.getJSONObject("first").replace("value", title);
        data.getJSONObject("keyword1").replace("value", content);
        data.getJSONObject("keyword2").replace("value", actor);
        data.getJSONObject("keyword3").replace("value", time);
        data.getJSONObject("remark").replace("value", remark);

        //装填参数
        StringEntity s = new StringEntity(source.template.toString(), "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                "application/json"));
        //设置参数到请求对象中
        source.httpPost.setEntity(s);
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = source.client.execute(source.httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            String body = EntityUtils.toString(entity, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(body);
            if (jsonObject.getIntValue("code") != 200) {
                System.out.println(body);
            }
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
    }


    private static class HttpSource{
        //httpclient对象
        private CloseableHttpClient client;

        //post方式请求对象
        private HttpPost httpPost;

        //
        private JSONObject template;

        HttpSource(boolean isGroup){
            client = HttpClients.createDefault();
            template = new JSONObject();
            template.put("secret", "2924132fca40ff559f3f7b3612896e3e");
            template.put("template_id", "5uZIvSm5GAdUR1X25HNpjuOp6jSiL88v4opn4a4GLa0");
            template.put("url", null);
            JSONObject jsonObject1 = new JSONObject();
            JSONObject jsonObject2;
            jsonObject2 = new JSONObject();
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
            template.put("data", jsonObject1);
            if (isGroup){
                httpPost = new HttpPost("http://u.erpit.cn/api/message/send");
                template.put("app_key", "11760262134d68422daadc502265e709");
            }else {
                httpPost = new HttpPost("http://u.erpit.cn/api/message/send-user");
                template.put("uid", null);
            }
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        }
    }

}
