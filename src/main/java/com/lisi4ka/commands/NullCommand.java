package com.lisi4ka.commands;

public class NullCommand implements Command{
    @Override
    public String execute(){
        return "Can not execute this command on server!";
    }
}
