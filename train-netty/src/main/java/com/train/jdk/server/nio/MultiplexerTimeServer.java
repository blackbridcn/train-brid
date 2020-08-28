package com.train.jdk.server.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Author: yuzzha
 * Date: 2020-08-17 10:31
 * Description: ${DESCRIPTION}
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    private boolean stop;

    /**
     * 1.创建ServerSocketChannel通道，并设置为非阻塞模式
     * 2.为channel绑定端口号
     * 3.创建Selector；
     * 4.将ServerSocketChannel注册到Selector中，监听accept事件
     * 5.
     *
     * @param port
     */

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            System.out.println("Selector " + selector.isOpen());
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", port), 1024);
             serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("MultiplexerTimeServer --------> ServerSocketChannel register ");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("MultiplexerTimeServer ----? IOException :" + e.getMessage());
        }
    }

    public void stop() {
        this.stop = true;
    }


    @Override
    public void run() {
        while (!stop) {
            try {
                //轮询多路复用器 Selector
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {//Selector 中有就绪的Key
                    SelectionKey next = iterator.next();
                    iterator.remove();
                    try {
                        handlerInput(next);
                    } catch (IOException e) {
                        if (next != null) {
                            next.channel();
                            if (next.channel() != null) {
                                next.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //多路复用器关闭后，所有注册在上面的channel和pipe
        // 等资源都会被自动注册并关闭，所有不需要重复释放资源
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerInput(SelectionKey key) throws IOException {
        if (key.isValid()) {

            //监听到有新的用户接入，处理新的新socket
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel accept = ssc.accept();
                accept.configureBlocking(false);
                accept.register(selector, SelectionKey.OP_READ);
            }
            //处理新接入的请求消息
            if (key.isReadable()) {
                //Read the data
                SocketChannel channel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int read = channel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("handlerInput ----------------> " + body);
                    if (body.equalsIgnoreCase("QUERY TIME ODER")) {
                        try {
                            doWrite(channel, new Date(System.currentTimeMillis()).toString());
                        } catch (IOException e) {
                            System.out.println("doWrite  IOException ");
                        }

                    }
                } else if (read < 0) {
                    //没有数据 关闭数据链路
                    key.channel();
                    channel.close();
                } else {
                    System.out.println("读到0个字节");
                }
            }
        }
    }

    public void doWrite(SocketChannel channel, String msg) throws IOException {
        if (msg != null && msg.length() > 0) {
            byte[] bytes = msg.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            int write = channel.write(byteBuffer);
            System.out.println(channel.getRemoteAddress().toString() + " byte size :" + write + "\n MSG ：" + msg);
        }
    }
}
