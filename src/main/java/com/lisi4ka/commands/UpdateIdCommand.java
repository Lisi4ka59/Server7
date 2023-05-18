package com.lisi4ka.commands;

import com.lisi4ka.models.*;
import com.lisi4ka.utils.CityComparator;

import java.util.Date;
import java.util.List;

import static com.lisi4ka.utils.Checker.checkDate;
import static com.lisi4ka.commands.DefaultSave.defaultSave;

public class UpdateIdCommand implements Command{
    private final List<City> collection;
    public UpdateIdCommand(List<City> collection){

        this.collection = collection;
    }
    private String updateArgs(String[] args){
        boolean update = false;
        long id = Long.parseLong(args[0]);
        for (City city:collection) {
            if (city.getId()==id){
                city.setName(args[1]);
                double x = Double.valueOf(args[2]);
                float y = Float.valueOf(args[3]);
                city.setCoordinates(new Coordinates(x, y));
                city.setArea(Double.valueOf(args[4]));
                city.setPopulation(Long.valueOf(args[5]));
                city.setMetersAboveSeaLevel(Integer.valueOf(args[6]));
                city.setClimate(Climate.fromInt(Integer.valueOf(args[7])));
                if ("null".equals(args[8])){
                    city.setGovernment(null);
                }
                else {
                    city.setGovernment(Government.fromInt(Integer.valueOf(args[8])));
                }
                if ("null".equals(args[9])){
                    city.setStandardOfLiving(null);
                }
                else {
                    city.setStandardOfLiving(StandardOfLiving.fromInt(Integer.valueOf(args[9])));
                }
                if ("null".equals(args[10]) || "null".equals(args[11])){
                    city.setGovernor(null);
                }
                else {
                    long age = Long.valueOf(args[10]);
                    Date birthday = checkDate(args[11]);
                    city.setGovernor(new Human(age,birthday));
                }
                update = true;
                break;
            }
        }
        if (update)
            return String.format("City %d updated\n%s", id, defaultSave(collection));
        else
            return String.format("City %d doesn't exist\n", id);
    }
    @Override
    public String execute(String args){
        String[] cityArgs = args.split(",");
        String answer = updateArgs(cityArgs);
        collection.sort(new CityComparator());
        return answer;
    }
}
