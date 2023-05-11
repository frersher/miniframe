package com.cb.tomcat;
/**
 * servlet配置
* @author: chenb
* @date: 2023/05/06
**/

public class ServletMapping  {


  private String url;
  private String servletName;
  private String clazz;


  public void setClazz(String clazz) {
    this.clazz = clazz;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setServletName(String servletName) {
    this.servletName = servletName;
  }

  public String getUrl() {
      return url;
    }

    public String getServletName() {
      return servletName;
    }

    public String getClazz() {
      return clazz;
    }

    ServletMapping(String servletName, String url, String clazz) {
      this.servletName = servletName;
      this.url = url;
      this.clazz = clazz;
    }

  }






