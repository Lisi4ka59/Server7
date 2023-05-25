package com.lisi4ka.commands;

import com.github.cliftonlabs.json_simple.Jsoner;
import com.lisi4ka.commands.LoadCommand;
import com.lisi4ka.models.City;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Statement;
import java.util.List;

import static com.lisi4ka.commands.BdConnect.conn;

public class DefaultSave {
    public static String defaultSave(List<City> collection) {
//        Statement stmt = conn.createStatement();
//        stmt.executeQuery("
//        INSERT INTO public.city(
//                id, name, coordinate_x, coordinate_y, area, population, meters_above_sea_level, climate, government, standard_of_living, governor_age, governor_birthday, user_id, creation_city_date)
//        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("sd"));
            String json = Jsoner.serialize(collection);
            json = Jsoner.prettyPrint(json);
            writer.write(json);
            writer.close();
            return "Changes saved";
        } catch (SecurityException e) {
            return String.format("Do not have sufficient rights to write file %s!\n", "sd");
        } catch (Exception ex) {
            return String.format("Error while saving file! %s\n", ex.getMessage());
        }
    }
}