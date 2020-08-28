package com.train.jdk.server.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandlerTask implements Runnable {

    private Socket socket;

    public TimeServerHandlerTask(Socket socket) {
        this.socket = socket;
    }

    BufferedReader in;
    PrintWriter out;

    @Override
    public void run() {
        String line = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream());
            while (true) {
                line = in.readLine();
                if (line != null) {
                    System.out.println("Time Server Recive Msg : " + line);
                    if (line.equalsIgnoreCase("QUERY TIME ODER")) {
                        String body = new Date(System.currentTimeMillis()).toString();
                        out.println(body);
                        out.flush();
                        System.out.println("println------> "+body);
                    }else {
                        System.out.println("------> ");
                    }

                }else{
                    System.out.println("----------> ");
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" ---------> Time Server Task  IOException ");
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
                out.close();
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
        }
    }
}
