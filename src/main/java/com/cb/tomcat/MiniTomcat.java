package com.cb.tomcat;

import com.cb.tomcat.servlet.MyServelt;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: chenb
 * @date: 2023/05/06
 **/
public class MiniTomcat {

  private int port;
  private ServletContainer servletConfig;

  public MiniTomcat(int port) {
    this.port = port;
  }


  public void init() {
    servletConfig = new ServletContainer();
  }

  public void initServlet() {
    ServletMapping servletMapping1 = new ServletMapping("people", "/people",
        "com.cb.tomcat.servlet.PeopleServlet");
    ServletMapping servletMapping2 = new ServletMapping("user", "/user",
        "com.cb.tomcat.servlet.UserServlet");
    servletConfig.addServletMapping(servletMapping1);
    servletConfig.addServletMapping(servletMapping2);
  }

  public void start() {
    init();
    initServlet();
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port);
      Socket accept = serverSocket.accept();
      // 解析http协议内容
      MyRequest myRequest = new MyRequest(accept.getInputStream());
      ServletMapping servletMapping = servletConfig.getServletMapping(myRequest.getUrl());
      MyServelt servlet = (MyServelt) Class.forName(servletMapping.getClazz()).newInstance();
      servlet.service(myRequest,new MyResponse(accept.getOutputStream()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    MiniTomcat miniTomcat = new MiniTomcat(8080);
    miniTomcat.start();
  }

}
