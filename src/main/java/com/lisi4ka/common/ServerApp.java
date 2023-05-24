package com.lisi4ka.common;

import com.lisi4ka.utils.CityLinkedList;
import com.lisi4ka.utils.PackagedCommand;
import com.lisi4ka.utils.PackagedResponse;
import com.lisi4ka.utils.ResponseStatus;
import org.postgresql.util.MD5Digest;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import static java.lang.Thread.sleep;

public class ServerApp {
    public static CityLinkedList cities = new CityLinkedList();
    public Queue<String> queue = new LinkedList<>();
    public static HashMap<String, String> users = new HashMap<>();
    public static HashMap<String, Queue<String>> queueMap = new HashMap<>();
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    ExecutorService executor = Executors.newFixedThreadPool(3);
    public static ExecutorService invokeExecutor = Executors.newCachedThreadPool();
    public static HashMap<String, String> logins = new HashMap<>(){{
        put("123", "202cb962ac59075b964b07152d234b70");
    }};

    public ServerApp() {
    }

    private void run() {
        try {
            System.out.println("Server started");
            Invoker invoker = new Invoker(cities);
            queue.add(invoker.run("load", "nobody"));
            InetAddress host = InetAddress.getByName("localhost");
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(host, 9856));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            SelectionKey key;
            Runnable basic = () ->
            {
                MegaAnswerManager megaAnswerManager = new MegaAnswerManager();
                forkJoinPool.invoke(megaAnswerManager);
            };
            Thread thread = new Thread(basic);
            thread.start();
            while (true) {
                if (selector.select() <= 0)
                    continue;
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    assert key != null;
                    if (key.isValid() && key.isAcceptable()) {
                        SocketChannel sc = serverSocketChannel.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        System.out.println("Connection Accepted: " + sc.getLocalAddress());
                        queueMap.put(sc.getRemoteAddress().toString(), new LinkedList<>());
                    }
                    if (key.isValid() && key.isReadable()) {
                        RequestManager requestManager = new RequestManager(key);
                        Thread thread1 = new Thread(requestManager);
                        executor.submit(thread1);
                        sleep(1);
                    }
                    if (key.isValid() && key.isWritable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        String answer;
                        if (!queue.isEmpty()) {
                            answer = queue.poll();
                        } else if (queueMap.get(socketChannel.getRemoteAddress().toString()) != null) {
                            if (!queueMap.get(socketChannel.getRemoteAddress().toString()).isEmpty()) {
                                answer = queueMap.get(socketChannel.getRemoteAddress().toString()).poll();
                            } else {
                                iterator.remove();
                                continue;
                            }
                        } else {
                            iterator.remove();
                            continue;
                        }
                        MegaAnswerManager.keyQueue.add(key);
                        MegaAnswerManager.answerQueue.add(answer);
                    }
                    iterator.remove();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            System.out.println("This port is already in use!");
        }
    }

    public static void serverRun() {
        ServerApp serverApp = new ServerApp();
        serverApp.run();
    }
}