package com.lisi4ka.common;

import com.lisi4ka.utils.PackagedCommand;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Objects;

import static com.lisi4ka.common.ServerApp.*;

public class RequestManager implements Runnable {
    SelectionKey key;

    public RequestManager(SelectionKey key) {
        this.key = key;
    }

    @Override
    public void run() {
        try {
            SocketChannel sc = (SocketChannel) key.channel();
            if (!users.containsKey(sc.getRemoteAddress().toString())){
                users.put(sc.getRemoteAddress().toString(), "");
            }
            queueMap.put(sc.getRemoteAddress().toString(), new LinkedList<>());
            if ("".equals(users.get(sc.getRemoteAddress().toString()))) {
                queueMap.get(sc.getRemoteAddress().toString()).add("Enter login " + sc.getRemoteAddress());
            }else {
                queueMap.get(sc.getRemoteAddress().toString()).add("You are working as " + users.get(sc.getRemoteAddress().toString()));
            }
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
                } catch (EOFException | ClassNotFoundException e) {
                    e.printStackTrace(System.out);
                }
                if (packagedCommand != null) {
                    if ("".equals(users.get(sc.getRemoteAddress().toString()))) {
                        String[] logpswd = packagedCommand.getCommandArguments().split("@");
                        if (logins.containsKey(logpswd[0])){
                            if (logpswd[1].equals(logins.get(logpswd[0]))){
                                users.put(sc.getRemoteAddress().toString(), logpswd[0]);
                            }else {
                                queueMap.get(sc.getRemoteAddress().toString()).add("parol nepravilnuy");
                            }
                        }else{
                            queueMap.get(sc.getRemoteAddress().toString()).add("вы зарегестрированы под логином");
                            logins.put(logpswd[0], logpswd[1]);
                        }
                    } else {
                        InvokerManager invokerManager;
                        if (packagedCommand.getCommandArguments() == null) {
                            invokerManager = new InvokerManager(sc.getRemoteAddress().toString(), packagedCommand.getCommandName(), users.get(sc.getRemoteAddress().toString()));
                        } else {
                            invokerManager = new InvokerManager(sc.getRemoteAddress().toString(), packagedCommand.getCommandName() + " " + packagedCommand.getCommandArguments(), users.get(sc.getRemoteAddress().toString()));
                        }
                        invokeExecutor.execute(invokerManager);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
