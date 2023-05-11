package com.cb.tomcat.servlet;

import com.cb.tomcat.MyRequest;
import com.cb.tomcat.MyResponse;

/**
 * @author: chenb
 * @date: 2023/05/06
 **/
public abstract class MyServelt {

    public void service(MyRequest request, MyResponse response) throws Exception {
      if ("GET".equalsIgnoreCase(request.getMethod())) {
        doGet(request, response);
      } else {
         doPost(request, response);
      }
    }

    public abstract void doGet(MyRequest request, MyResponse response) throws Exception;

    public abstract void doPost(MyRequest request, MyResponse response) throws Exception;

}
