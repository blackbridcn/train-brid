package com.train.jdk.client.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Author: yuzzha
 * Date: 2020-08-17 11:34
 * Description: ${DESCRIPTION}
 */
public class TimeClientHandler implements Runnable {

    private String host;
    private int port;
    Selector selector;
    private SocketChannel socketChannel;

    public TimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("TimeClientHandler IOException ");
        }
    }

    private boolean stop;

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("doConnect  IOException :" + e.getMessage());
            System.exit(1);
        }
        try {
            while (!stop) {
                selector.select(1000);
                Set<SelectionKey> keys = selector.keys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    //iterator.remove();
                    try {
                        System.out.println("per  handlerInput ");
                        handlerInput(key);
                    } catch (Exception e) {
                        System.out.println("handlerInput Exception " + e.getMessage());
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("selector close IOException  ");
            }
        }

    }

    private void handlerInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel channel = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                if (channel.finishConnect()) {
                    channel.register(selector, SelectionKey.OP_READ);
                    doWrite(channel);
                } else {
                    System.out.println("handlerInput  System Exit");
                    System.exit(1);
                }
            }
            if (key.isReadable()) {
                ByteBuffer allocate = ByteBuffer.allocate(1024);
                int read = channel.read(allocate);
                if (read > 0) {
                    allocate.flip();
                    byte[] bytes = new byte[allocate.remaining()];
                    allocate.get(bytes);
                    System.out.println("handlerInput Now is " + new String(bytes, "UTF-8"));
                    this.stop = true;
                } else if (read < 0) {
                    key.cancel();
                    channel.close();
                } else {
                    System.out.println("handlerInput 读到0字节");
                }
            }
        }
    }


    private void doConnect() throws IOException {
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            System.out.println("doConnect   connect true ");
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            System.out.println("doConnect   socketChannel.register");
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        byte[] bytes = "QUERY TIME ODER".getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(bytes);
        buffer.flip();
        socketChannel.write(buffer);
        if (!buffer.hasRemaining()) {
            System.out.println("Send Oder 2 Server Success ");
        } else {
            System.out.println("Send Oder 2 Server fail ");
        }
    }
}
