package com.ada.lark.servlet;

import com.ada.lark.Common;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet(name = "ReplyComMsg", urlPatterns = {"/ReplyComMsg"})
public class ReplyComMsg extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String ReqJsonStr = Common.getRequestJsonString(req);
        File f = new File("/home/chenliang/data/zzl/tomcat.out");
        BufferedWriter bw = new BufferedWriter( new FileWriter(f, true));
        bw.write(ReqJsonStr);
        bw.newLine();
        bw.flush();
        bw.close();
    }
}
