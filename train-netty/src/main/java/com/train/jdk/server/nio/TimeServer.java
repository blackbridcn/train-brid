package com.train.jdk.server.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeServer {

    private void test(String host, int port) {
        try {
            //打开ServerSocketChannel 用于监听客户端连接，所有客户端连接的父通道
            ServerSocketChannel acceptorSver = ServerSocketChannel.open();
            //绑定ip和端口；
            acceptorSver.socket().bind(new InetSocketAddress(host, port));
            //设置为非阻塞模式
            acceptorSver.configureBlocking(false);
            //创建Selector线程，创建多路复用器，并启动线程
            Selector selector = Selector.open();
            //TODO new SelectorTask

            //将ServerSocketChannel注册到Selector多路复用器上，监听Accept事件
            /*   acceptorSver.register(acceptorSver, SelectionKey.OP_ACCEPT, ioHandler);*/
            //多路复用器在线程run方法的无限循环体内轮询准备就绪的Key
            int num = selector.select();
            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                //TODO  …… deal with I/O event ……


            }
            //6 .多路复用器监听到新的客户端接入，处理新的接入请求，
            // 完成TCP三次握手，建立物理链路
            SocketChannel socketChannel = acceptorSver.accept();
            //7.设置客户端为非阻塞模式
            socketChannel.configureBlocking(false);
            socketChannel.socket().setReuseAddress(true);
            //8.将新接入的客户端注册到Reactor线程多路复用器上，监听读写客户端发送的网络消息
            /*    socketChannel.register(selector, SelectionKey.OP_READ, ioHandler);*/
            //9.异步读取客户端请求消息到缓冲区，
            /*    socketChannel.read(receivedBuffer);*/
            //10.对ByteBuffer进行编解码，如果有半包消息指针reset
            ByteBuffer buffer = null;
            while (buffer.hasRemaining()) {

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bind(String host, int port) {

    }

    public static void main(String[] args) {
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(5768);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001 ").start();
    }

}
