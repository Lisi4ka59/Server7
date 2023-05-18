package com.lisi4ka.commands;

import com.github.cliftonlabs.json_simple.*;
import com.lisi4ka.models.*;
import com.lisi4ka.utils.CityComparator;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static com.lisi4ka.commands.BdConnect.conn;
import static com.lisi4ka.common.ServerApp.cities;

public class LoadCommand implements Command {
    private final List<City> collection;
    public static String filepath;

    public LoadCommand(List<City> collection){

        this.collection = collection;
    }
    private String load() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select id, name, coordinate_x, coordinate_y, population, area, meters_above_sea_level, climate, government, standard_of_living, governor_age, governor_birthday, creation_city_date from city");
            while (rs.next()) {
                City city = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        new Coordinates(rs.getDouble("coordinate_x"), rs.getFloat("coordinate_y")),
                        rs.getLong("population"),
                        rs.getDouble("area"),
                        rs.getInt("meters_above_sea_level"),
                        Climate.fromInt(rs.getInt("climate")),
                        Government.fromInt(rs.getInt("government")),
                        StandardOfLiving.fromInt(rs.getInt("standard_of_living")),
                        (rs.getInt("governor_age") == 0 || rs.getInt("governor_birthday") == 0)? null:
                        new Human(rs.getInt("governor_age"), rs.getDate("governor_birthday"))
                        );
                city.setCreationDate(rs.getTimestamp("creation_city_date").toLocalDateTime());
                collection.add(city);
            }
//
//            public City (long id, String name, Coordinates coordinates, Long population, double area, int metersAboveSeaLevel,
//            Climate climate, Government government, StandardOfLiving standardOfLiving, Human governor){

        }catch (Exception ex){
            System.out.println("ошибка при подключении к бд");
            System.out.println(ex);
        }
//        String path = System.getenv("CITIES_PATH");
//        try {
//            Reader reader = Files.newBufferedReader(Paths.get(path));
//            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(reader);
//            JsonArray jsonArray = (JsonArray)jsonObject.get("cities");
//            filepath = path;
//            for (Object obj: jsonArray) {
//                JsonObject jo = (JsonObject) obj;
//                City city = new City(jo);
//                collection.add(city);
//            }
//            collection.sort(new CityComparator());
//            return "Collection uploaded";
//        } catch (JsonException | IllegalArgumentException | NullPointerException e) {
//            return "Can not upload collection, data in the file incorrect! " + e.getMessage()  + "\n";
//        } catch (SecurityException e) {
//            return "Do not have sufficient rights to read file %s\n" + path + "\n";
//        } catch (IOException e) {
//            return "Can not upload collection, the file " + path + " does not exist!\n";
//        }
        return "Collection uploaded";
    }
    @Override
    public String execute(){
        return load();
    }
}
