package com.cb.tomcat.servlet;

import com.cb.tomcat.MyRequest;
import com.cb.tomcat.MyResponse;

/**
 * @author: chenb
 * @date: 2023/05/06
 **/
public class UserServlet extends MyServelt{

  @Override
  public void doGet(MyRequest request, MyResponse response) throws Exception {
    response.write("get user...");
  }

  @Override
  public void doPost(MyRequest request, MyResponse response) throws Exception {
    response.write("post user...");
  }
}
