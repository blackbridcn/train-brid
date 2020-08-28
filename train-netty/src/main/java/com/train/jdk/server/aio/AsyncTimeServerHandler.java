package com.train.jdk.server.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable {

    AsynchronousServerSocketChannel serverSocketChannel;
    CountDownLatch latch;

    public AsyncTimeServerHandler(int port) {
        try {
            serverSocketChannel = AsynchronousServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("AsyncTimeServerHandler -------------->  ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();

        try {
            latch.await();
        } catch (Exception e) {

        }
    }

    private void doAccept() {
        serverSocketChannel.accept(this,new AcceptCompletionHandler<AsynchronousSocketChannel,AsyncTimeServerHandler>());

    }

    public static void main(String[] args) {
        AsyncTimeServerHandler  serverHandler=new AsyncTimeServerHandler(5768);
        new Thread(serverHandler,"AIO-AsyncTimeServerHandler ").start();
    }



}
