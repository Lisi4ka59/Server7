package com.lisi4ka.commands;

import com.lisi4ka.models.City;
import com.lisi4ka.utils.StandardOfLivingComparator;

import java.util.List;

public class PrintFieldAscendingStandardOfLivingCommand implements Command{
    private final List<City> collection;
    public PrintFieldAscendingStandardOfLivingCommand(List<City> collection){

        this.collection = collection;
    }

    @Override
    public String execute() {
        StringBuilder stringBuilder = new StringBuilder();
        for (City city: collection.stream().sorted(new StandardOfLivingComparator()).toList()) {
            stringBuilder.append(city.getStandardOfLiving()).append("\n");
        }
        return stringBuilder.toString();
    }

}
