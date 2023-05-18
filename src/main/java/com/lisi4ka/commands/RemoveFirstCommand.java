package com.lisi4ka.commands;
import com.lisi4ka.models.City;
import java.util.List;

import static com.lisi4ka.commands.DefaultSave.defaultSave;

public class RemoveFirstCommand implements Command{
    private final List<City> collection;
    public RemoveFirstCommand(List<City> collection){

        this.collection = collection;
    }
    @Override
    public String execute() {
        if (!collection.isEmpty()) {
            collection.remove(0);
            return "First element of collection removed\n" + defaultSave(collection);
        }
        else {
            return "Collection is empty";
        }
    }

}
