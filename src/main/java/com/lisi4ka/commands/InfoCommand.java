package com.lisi4ka.commands;

import com.lisi4ka.models.City;
import java.util.List;

public class InfoCommand implements Command{
    private final List<City> collection;
    public InfoCommand(List<City> collection){

        this.collection = collection;
    }
    @Override
    public String execute() {
        return collection.toString();
    }
}
