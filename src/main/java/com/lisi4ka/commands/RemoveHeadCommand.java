package com.lisi4ka.commands;

import com.lisi4ka.models.City;
import java.util.List;

import static com.lisi4ka.commands.DefaultSave.defaultSave;

public class RemoveHeadCommand implements Command{
    private final List<City> collection;
    public RemoveHeadCommand(List<City> collection){
        this.collection = collection;
    }

    @Override
    public String execute() {
        if (!collection.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            City city = collection.get(0);
            stringBuilder.append(city.toString()).append("\n");
            collection.remove(0);
            stringBuilder.append("First element of collection removed").append("\n");
            return stringBuilder + defaultSave(collection);
        }
        else {
            return "Collection is empty\n";
        }
    }

}
