package com.ada.lark.servlet;

import com.ada.lark.Common;
import com.ada.lark.model.DocDetail;
import com.ada.lark.model.User;
import com.ada.lark.sendMsg.SendFgMsg;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "DocRelatedMsg", urlPatterns = {"/DocRelatedMsg"})
public class DocRelatedMsg extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String ReqJsonStr = Common.getRequestJsonString(req);
        DocDetail docDetail = DocDetail.jsonStrToDocDetail(ReqJsonStr);
        User user = Common.users.get(docDetail.actorId);
        String content = (user.name != null ? user.name : user.larkId) +
                "在知识库\"" + docDetail.bookName + "\"中" +
                docDetail.actionType + "了文章\"" +
                docDetail.title + "\"";
        try {
            SendFgMsg.sendToMult("实时处理小组语雀文档更新",
                    content,
                    user.name != null ? user.name : user.larkId,
                    Common.sdf.format(new Date()),
                    docDetail.path,
                    "点击\"查看详情\"阅读文章");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
