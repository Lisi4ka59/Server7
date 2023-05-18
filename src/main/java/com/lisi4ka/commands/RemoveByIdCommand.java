package com.lisi4ka.commands;

import com.lisi4ka.models.City;

import java.util.List;

import static com.lisi4ka.commands.DefaultSave.defaultSave;

public class RemoveByIdCommand implements Command {
    private final List<City> collection;

    public RemoveByIdCommand(List<City> collection) {
        this.collection = collection;
    }

    private String remove(long id) {
        boolean removed = false;
        for (City city : collection) {
            if (city.getId() == id) {
                removed = collection.remove(city);
                break;
            }
        }
        if (removed)
            return String.format("City with ID %d removed!\n", id) + defaultSave(collection);
        else
            return String.format("City with ID %d does not exists!\n", id);
    }

    @Override
    public String execute(String stringId) {
        long id = Long.parseLong(stringId);
        return remove(id);
    }

}