package com.cb.tomcat;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: chenb
 * @date: 2023/05/06
 **/
public class MyRequest {

      private String url;
      private String method;

  public MyRequest(InputStream ism) throws IOException {
    byte[] content = new byte[64];
    int readLen = ism.read(content);
    if (readLen > 0) {
      String s = new String(content, 0, readLen);
      String[] ss = s.split("\\s");
      url = ss[1];
      method = ss[0];
      }
      System.out.println(this);
  }

      public String getUrl() {
          return url;
      }

      public String getMethod() {
          return method;
      }

  @Override
  public String toString() {
    return "MyRequest{" +
        "url='" + url + '\'' +
        ", method='" + method + '\'' +
        '}';
  }
}
