package com.lisi4ka.commands;

import com.lisi4ka.models.City;
import com.lisi4ka.models.StandardOfLiving;

import java.util.ArrayList;
import java.util.List;

public class PrintUniqueStandardOfLivingCommand implements Command{
    private final List<City> collection;
    public PrintUniqueStandardOfLivingCommand(List<City> collection){

        this.collection = collection;
    }

    @Override
    public String execute() {
        ArrayList<StandardOfLiving> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for (City city : collection) {
            if (!list.contains(city.getStandardOfLiving())) {
                list.add((city.getStandardOfLiving()));
                stringBuilder.append(city.getStandardOfLiving()).append("\n");
            }
        }
        return stringBuilder.toString();
    }
}
