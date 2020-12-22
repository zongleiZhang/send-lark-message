package com.ada.lark;

import com.ada.lark.model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Common {

    public static final Map<String, User> users = new HashMap<>();

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        User user = new User();
        user.larkId = "2348639";
        user.name = "张宗雷";
        user.fgID = "5453";
        user.email = "654310097@qq.com";
        users.put("2348639", user);
    }

    public static String getRequestJsonString(HttpServletRequest req) throws IOException {
        BufferedReader streamReader = new BufferedReader( new InputStreamReader(req.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        return responseStrBuilder.toString();
    }

}
