package com.lisi4ka.common;

import org.checkerframework.checker.units.qual.A;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.RecursiveAction;

import static java.lang.Thread.sleep;

public class MegaAnswerManager extends RecursiveAction {
    public static volatile Queue<String> answerQueue = new LinkedList<>();
    public static volatile Queue<SelectionKey> keyQueue = new LinkedList<>();
    static volatile int threads = 0;
    @Override
    protected void compute() {
        int i = 0;
        threads++;
        System.out.println(threads);
        if (threads < 2) {
            MegaAnswerManager megaAnswerManager1 = new MegaAnswerManager();
            MegaAnswerManager megaAnswerManager2 = new MegaAnswerManager();
            megaAnswerManager1.fork();
            megaAnswerManager2.fork();
        } else {
            try {
                while (true) {
                    if(i<keyQueue.size()){
                        i=keyQueue.size();
                    }
                    //System.out.println(i);
                    if (!keyQueue.isEmpty()) {
                        SelectionKey key = keyQueue.poll();
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        System.out.println(socketChannel.getRemoteAddress().toString());
                        if (!answerQueue.isEmpty()) {
                            String answer = answerQueue.poll();
                            AnswerManager answerManager = new AnswerManager(socketChannel, answer);
                            System.out.println(answer);
                            answerManager.run();
                        }
                    }
                    //sleep(50);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
