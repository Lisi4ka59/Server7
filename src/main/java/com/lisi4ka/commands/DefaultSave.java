package com.lisi4ka.commands;

import com.github.cliftonlabs.json_simple.Jsoner;
import com.lisi4ka.commands.LoadCommand;
import com.lisi4ka.models.City;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DefaultSave {
    public static String defaultSave(List<City> collection) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(LoadCommand.filepath));
            String json = Jsoner.serialize(collection);
            json = Jsoner.prettyPrint(json);
            writer.write(json);
            writer.close();
            return "Changes saved";
        } catch (SecurityException e) {
            return String.format("Do not have sufficient rights to write file %s!\n", LoadCommand.filepath);
        } catch (Exception ex) {
            return String.format("Error while saving file! %s\n", ex.getMessage());
        }
    }
}