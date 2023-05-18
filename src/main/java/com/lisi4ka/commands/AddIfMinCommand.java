package com.lisi4ka.commands;

import com.lisi4ka.models.City;
import com.lisi4ka.utils.CityComparator;

import java.util.List;

import static com.lisi4ka.utils.CityLinkedList.idRepeat;
import static com.lisi4ka.commands.DefaultSave.defaultSave;

public class AddIfMinCommand implements Command{
    private final List<City> collection;
    public AddIfMinCommand(List<City> collection){
        this.collection = collection;
    }

    public boolean addIfMin (City city) {
        City minCity;
        try {
            minCity = collection.stream().min(new CityComparator()).get();
        }catch (Exception ex){
            collection.add(city);
            return true;
        }
        if (new CityComparator().compare(minCity, city) > 0) {
            collection.add(city);
            return true;
        }
        else return false;
    }

    @Override
    public String execute(String args) {
        City city = AddCommand.getCityArgs(args);
        if (addIfMin(city)) {
            idRepeat+=1;
            collection.sort(new CityComparator());
            return "City was successfully added to collection\n" + defaultSave(collection);
        }
        else {
            return "City is not added to collection";
        }
    }

}
