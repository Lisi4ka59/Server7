package com.lisi4ka.common;

import com.lisi4ka.utils.PackagedResponse;
import com.lisi4ka.utils.ResponseStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static java.lang.Thread.sleep;

public class AnswerManager implements Runnable{
    String answer;
    SocketChannel socketChannel;
    public AnswerManager (SocketChannel socketChannel, String answer){
        this.answer = answer;
        this.socketChannel = socketChannel;
    }
    @Override
    public void run() {
        try {
        ByteArrayOutputStream stringOut = new ByteArrayOutputStream();
        ObjectOutputStream serializeObject;
        serializeObject = new ObjectOutputStream(stringOut);
        PackagedResponse packagedResponse = new PackagedResponse(answer, ResponseStatus.OK);
        serializeObject.writeObject(packagedResponse);
        socketChannel.write(ByteBuffer.wrap(answer.getBytes()));
        //sleep(10000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
