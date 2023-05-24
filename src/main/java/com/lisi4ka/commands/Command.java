package com.lisi4ka.commands;

public interface Command {
    default String execute(String args) {
        return "Unknown command!";
    }
    default String execute() {
        return "Unknown command!";
    }
    default String execute(String args, String user){
        return "Users not support!";
    }
}
