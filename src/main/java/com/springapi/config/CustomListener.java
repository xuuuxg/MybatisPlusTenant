package com.springapi.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@WebListener
public class CustomListener implements ServletRequestListener {

    @Autowired
    private CustomContext customContext;

    public void requestDestroyed(ServletRequestEvent sre) {

    }

    public void requestInitialized(ServletRequestEvent sre) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {

            reader = sre.getServletRequest().getReader();
            while (null != (line = reader.readLine()))
                data.append(line);
            System.out.println(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(String.valueOf(data));
        Map<String, List<String>> map = new HashMap<>();
        List<String> tenantIds = new ArrayList<>();

        for(Object tenantId : JSONArray.parseArray(jsonObject.get("tenantId").toString())) {
            System.out.println(tenantId);
            tenantIds.add(tenantId.toString());
        }
        map.put("tenantIds",tenantIds);
        customContext.setTenantIdMap(map);
    }

}
