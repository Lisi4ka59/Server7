package com.lisi4ka.commands;

import com.lisi4ka.models.City;
import java.util.List;
import static com.lisi4ka.commands.DefaultSave.defaultSave;


public class ClearCommand implements Command{
    private final List<City> collection;
    public ClearCommand(List<City> collection) {
        this.collection = collection;
    }
    @Override
    public String execute(String args, String user) {
        clearByUser(user);
        return "Collection cleaned!\n" + defaultSave(collection);
    }
    public void clearByUser(String user){
        int remove;
        int index = 0;
        do {
            remove = 0;
            for (int i = index; i < collection.size(); i++) {
                if (collection.get(i).getUser().equals(user)) {
                    collection.remove(i);
                    remove++;
                    index = i;
                    break;
                }
            }
        }while (remove>0);
    }
}
