package com.lisi4ka.common;

import com.lisi4ka.utils.CityLinkedList;
import com.lisi4ka.utils.PackagedCommand;
import com.lisi4ka.utils.PackagedResponse;
import com.lisi4ka.utils.ResponseStatus;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {
    public static CityLinkedList cities = new CityLinkedList();
    public Queue<String> queue = new LinkedList<>();
    public static HashMap<String, String> users = new HashMap<>();
    public static HashMap<String, Queue<String>> queueMap = new HashMap<>();
    ExecutorService executor = Executors.newFixedThreadPool(3);
    public static ExecutorService invokeExecutor = Executors.newCachedThreadPool();

    private void run() {
        try {
            System.out.println("Server started");
            Invoker invoker = new Invoker(cities);
            queue.add(invoker.run("load"));
            InetAddress host = InetAddress.getByName("localhost");
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(host, 9856));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            SelectionKey key;

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
                        SocketChannel sc = (SocketChannel) key.channel();
                        users.put(sc.getRemoteAddress().toString(), "");
                        queueMap.put(sc.getRemoteAddress().toString(), new LinkedList<>());
                        queueMap.get(sc.getRemoteAddress().toString()).add("Enter login " + sc.getRemoteAddress());
                        ByteBuffer bb = ByteBuffer.allocate(8192);
                        boolean flag = true;
                        try {
                            sc.read(bb);
                        } catch (SocketException | EOFException ex) {
                            sc.close();
                            flag = false;
                            System.out.print("Client close connection!\nServer will keep running\nTry running another client to re-establish connection\n");
                        }
                        if (flag) {
                            String result = new String(bb.array()).trim();
                            byte[] data = Base64.getDecoder().decode(result);
                            PackagedCommand packagedCommand = null;
                            try {
                                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
                                packagedCommand = (PackagedCommand) ois.readObject();
                                ois.close();
                            } catch (EOFException ignored) {
                            }
                            if (packagedCommand != null) {
                                InvokerManager invokerManager;
                                if (packagedCommand.getCommandArguments() == null) {
                                    invokerManager = new InvokerManager(sc.getRemoteAddress().toString(), packagedCommand.getCommandName());
                                } else {
                                    invokerManager = new InvokerManager(sc.getRemoteAddress().toString(), packagedCommand.getCommandName() + " " + packagedCommand.getCommandArguments());
                                }
                                invokeExecutor.execute(invokerManager);
                            }
                        }
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
                        AnswerManager answerManager = new AnswerManager(socketChannel, answer);
                        executor.execute(answerManager);
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