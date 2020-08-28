package com.train.jdk.client.aio;

public class TimeClient {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new AsyncTimeClientHandler("127.0.0.1",5768)).start();
        }

    }
}
