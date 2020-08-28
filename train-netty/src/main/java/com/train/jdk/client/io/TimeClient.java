package com.train.jdk.client.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {

    public void conn(String host, int port) {
        Socket socket =null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(host,port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("QUERY TIME ODER");
            out.flush();
            System.out.println("Time Client write" + "QUERY TIME ODER");

            String line = in.readLine();
            System.out.println("Time Client " + line);
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("--------> IOException : "+e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                out = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }

            System.out.println("finally ");
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i <10 ; i++) {
            new TimeClient().conn("192.168.31.77",5768);
        }
    }
}
