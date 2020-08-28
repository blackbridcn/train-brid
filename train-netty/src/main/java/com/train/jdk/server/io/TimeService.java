package com.train.jdk.server.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeService {

    public void bind(int port) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            TimeServiceHandlerExecutePools singleExecutor = new TimeServiceHandlerExecutePools(50, 200);
            Socket socket = null;

            while (true) {
                socket = serverSocket.accept();
                if(socket!=null){
                    System.out.println("------------> socket "+socket.getRemoteSocketAddress().toString());
                    TimeServerHandlerTask task = new TimeServerHandlerTask(socket);
                    singleExecutor.executor(task);
                }

            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
            System.out.println("finally----------> ");
        }

    }

    public static void main(String[] args) {
        try {
            new TimeService().bind(5768);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Time Server IOException");
        }
    }
}
