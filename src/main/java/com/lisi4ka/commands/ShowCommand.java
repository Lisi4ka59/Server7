package com.lisi4ka.commands;

import com.lisi4ka.models.City;
import java.util.List;

import static com.lisi4ka.utils.CityLinkedList.reentrantlock;
import static java.lang.Thread.sleep;

public class ShowCommand implements Command{
    private final List<City> collection;
    public ShowCommand(List<City> collection){

        this.collection = collection;
    }
    @Override
    public String execute() {
        StringBuilder stringShow = new StringBuilder();
        reentrantlock.lock();
        try {
            if (collection.isEmpty()) {
//                sleep(10000);
                return "No cities in collection";
            } else {
                for (City city : collection) {
                    stringShow.append(String.format("\n" + city.toString() + "\n"));
                }
            }
            //sleep(1000);
        }catch (Exception ignored){
        }
        finally
        {
            reentrantlock.unlock();
        }
        return stringShow.toString();
    }
}
