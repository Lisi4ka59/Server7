package com.lisi4ka.commands;

import com.lisi4ka.models.City;
import java.util.List;

public class ShowCommand implements Command{
    private final List<City> collection;
    public ShowCommand(List<City> collection){

        this.collection = collection;
    }
    @Override
    public String execute() {
        if (collection.isEmpty()) {
            return "No cities in collection";
        }
        else {
            StringBuilder stringShow = new StringBuilder();
            for (City city : collection) {
                 stringShow.append(String.format("\n" + city.toString() + "\n"));
            }
            return stringShow.toString();
        }
    }
}
