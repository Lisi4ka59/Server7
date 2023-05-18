package com.lisi4ka.commands;

import com.lisi4ka.models.City;

import java.util.List;

public class PrintDescendingCommand implements Command{
    private final List<City> collection;
    public PrintDescendingCommand(List<City> collection){
        this.collection = collection;
    }

    @Override
    public String execute() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object city: collection.stream().sorted().toList()) {
            stringBuilder.append(city.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

}
