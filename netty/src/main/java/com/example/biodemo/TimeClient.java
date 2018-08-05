package com.example.biodemo;

import java.io.*;
import java.net.Socket;

public class TimeClient {
    public static void main(String[] args) {
        int port = 8092;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException ne) {
                port = 8092;
            }
        }
        new Thread(new AsyncTimeClientHandler("127.0.0.1",port)).start();



//        new Thread(new TimeClientHandles("127.0.0.1",port),"TimeClient-001").start();
      /* 代码改造注释掉以下代码
       Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);
            System.out.println(socket.getInputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("QUERY TIME ORDER");
            System.out.println("send order 2 server succeed.");
            String resp = in.readLine();
            System.out.println("now is :" + resp);
        } catch (IOException e1) {

        } finally {
            if (out != null) {
                out.close();
                out = null;
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                in = null;
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }

                }
                socket = null;
            }
        }*/
    }
}

