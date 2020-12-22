package com.ada.lark.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class DocDetail {
    /**
     * 文档标题
     */
    public String title;

    /**
     * 文档所属知识库名称
     */
    public String bookName;


    /**
     * 文档操作类型：publish - 发布、 update - 更新、 delete - 删除
     */
    public String actionType;

    /**
     * 文档访问路径
     */
    public String path;

    /**
     * 操作者ID
     */
    public String actorId;

    public static DocDetail jsonStrToDocDetail(String reqJsonStr) {
        DocDetail docDetail = new DocDetail();
        JSONObject jsonObject = JSON.parseObject(reqJsonStr).getJSONObject("data");
        docDetail.title = jsonObject.getString("title");
        String actionType = jsonObject.getString("action_type");
        switch (actionType){
            case "update":
                docDetail.actionType = "更新";
                break;
            case "publish":
                docDetail.actionType = "发布";
                break;
            case "delete":
                docDetail.actionType = "删除";
                break;
            default:
                throw new IllegalArgumentException("参数异常");
        }
        docDetail.actorId = jsonObject.getString("actor_id");
        docDetail.bookName = jsonObject.getJSONObject("book").getString("name");
        docDetail.path = "https://www.yuque.com/" + jsonObject.getString("path");
        return docDetail;
    }
}
