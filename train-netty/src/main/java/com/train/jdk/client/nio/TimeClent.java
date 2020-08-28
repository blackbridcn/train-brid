package com.train.jdk.client.nio;

/**
 * Author: yuzzha
 * Date: 2020-08-17 14:01
 * Description: ${DESCRIPTION}
 */
public class TimeClent {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            TimeClientHandler clientHandler = new TimeClientHandler("127.0.0.1",5768);
            new Thread(clientHandler,"NIO TimeClientHandler "+i).start();
        }
    }
}
