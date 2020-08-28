package com.train.jdk.server.aio;


import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler<A, A1> implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

    public AcceptCompletionHandler() {
    }

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
        attachment.serverSocketChannel.accept(attachment, this);
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        result.read(allocate, allocate, new ReadCompletiponHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
