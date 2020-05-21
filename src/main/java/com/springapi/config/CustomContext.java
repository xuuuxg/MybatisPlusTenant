package com.springapi.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomContext {


    private Map<String, List<String>> tenantIdMap = new HashMap<>();

    public Map<String, List<String>> getTenantIdMap() {
        return tenantIdMap;
    }

    public void setTenantIdMap(Map<String, List<String>> tenantIdMap) {
        this.tenantIdMap = tenantIdMap;
    }
}
