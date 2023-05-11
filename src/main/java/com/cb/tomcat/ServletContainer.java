package com.cb.tomcat;

import java.util.HashMap;

/**
* @author: chenb
* @date: 2023/05/06
**/
public class ServletContainer {
    private HashMap<String,ServletMapping> servletMap = new HashMap<>();

    public void addServletMapping(ServletMapping servletMapping){
      servletMap.put(servletMapping.getUrl(),servletMapping);
    }

    public ServletMapping getServletMapping(String key){
      return servletMap.get(key);
    }

}
