package com.lisi4ka.utils;

import java.io.Serializable;

public class PackagedCommand implements Serializable{
        private final String commandName;
        private final String commandArguments;
        public PackagedCommand(String name, String args){
            commandName = name;
            commandArguments = args;
        }
        public String getCommandName(){
            return commandName;
        }
        public String getCommandArguments(){
            return commandArguments;
        }
}
