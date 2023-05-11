package com.cb.tomcat;

import java.io.OutputStream;

/**
 * @author: chenb
 * @date: 2023/05/06
 **/
public class MyResponse {

    private OutputStream outputStream;

    public MyResponse(OutputStream outputStream) {
      this.outputStream = outputStream;
    }

    public void write(String s) throws Exception {
      StringBuilder sb = new StringBuilder();
      sb.append("HTTP/1.1 200 OK\n")
          .append("Content-Type: text/html;\n")
          .append("\r\n")
          .append(s);
      outputStream.write(sb.toString().getBytes());
    }
}
