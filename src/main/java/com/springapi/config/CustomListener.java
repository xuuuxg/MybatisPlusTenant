package com.springapi.config;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@WebListener
public class CustomListener implements ServletRequestListener {

    @Autowired
    private CustomContext customContext;

    public void requestDestroyed(ServletRequestEvent sre) {

    }

    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) sre.getServletRequest();
        String tenantId = httpServletRequest.getHeader("tenantIds");

        JSONArray jsonArray = JSONArray.parseArray(tenantId);
        Map<String, List<String>> map = new HashMap<>();
        List<String> tenantIds = new ArrayList<>();
        if(jsonArray != null) {
            for(Object tenantIdItem : jsonArray) {
                System.out.println(tenantIdItem);
                tenantIds.add(tenantIdItem.toString());
            }
        }else {
            tenantIds.add(null);
        }
        map.put("tenantIds",tenantIds);
        customContext.setTenantIdMap(map);
    }

}
