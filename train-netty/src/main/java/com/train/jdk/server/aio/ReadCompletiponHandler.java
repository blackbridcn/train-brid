package com.train.jdk.server.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;

public class ReadCompletiponHandler implements CompletionHandler<Integer, ByteBuffer> {

    AsynchronousSocketChannel channel;

    public ReadCompletiponHandler(AsynchronousSocketChannel channel) {
        if(this.channel==null){
            this.channel = channel;
        }
    }

    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] bytes = new byte[attachment.remaining()];
        attachment.get(bytes);
        try {
            String body = new String(bytes, "UTF-8");
            System.out.println("ReadCompletiponHandler  completed : "+body);
            if (body.equalsIgnoreCase("QUERY TIME ODER")) {
                doWrite(new Date(System.currentTimeMillis()).toString());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String msg) {
        byte[] bytes = msg.getBytes();
        ByteBuffer allocate = ByteBuffer.allocate(bytes.length);
        allocate.put(bytes);
        allocate.flip();
        channel.write(allocate, allocate, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                //如果没有发完,继续发送
                if (allocate.hasRemaining()) {
                    channel.write(attachment, attachment, this);
                }

            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
