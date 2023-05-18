package com.lisi4ka.common;

import static com.lisi4ka.common.ServerApp.cities;
import static com.lisi4ka.common.ServerApp.queueMap;

public class InvokerManager implements Runnable{
    String socketChannel;
    String commandText;
    public InvokerManager(String socketChannel, String commandText){
        this.commandText = commandText;
        this.socketChannel = socketChannel;
    }
    @Override
    public void run() {
        Invoker invoker = new Invoker(cities);
        if (queueMap.get(socketChannel) != null) {
            queueMap.get(socketChannel).add(invoker.run(commandText));
        }
    }
}