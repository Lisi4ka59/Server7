package com.lisi4ka.commands;

import com.lisi4ka.models.*;
import com.lisi4ka.utils.CityComparator;

import java.sql.*;
import java.util.List;

import static com.lisi4ka.commands.BdConnect.conn;


public class LoadCommand implements Command {
    private final List<City> collection;
    public LoadCommand(List<City> collection) {
        this.collection = collection;
    }

    private String load() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select city.id, name, coordinate_x, coordinate_y, population, area, meters_above_sea_level, climate, government," +
                    " standard_of_living, governor_age, governor_birthday, creation_city_date, login from city left join account on user_id = account.id");
            while (rs.next()) {
                City city = new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        new Coordinates(rs.getDouble("coordinate_x"), rs.getFloat("coordinate_y")),
                        rs.getLong("population"),
                        rs.getDouble("area"),
                        rs.getInt("meters_above_sea_level"),
                        Climate.valueOf(rs.getString("climate")),
                        (rs.getString("government") == null) ? null :
                                Government.valueOf(rs.getString("government")),
                        (rs.getString("standard_of_living") == null) ? null :
                                StandardOfLiving.valueOf(rs.getString("standard_of_living")),
                        (rs.getInt("governor_age") == 0)  ? null :
                                new Human(rs.getLong("governor_age"), rs.getDate("governor_birthday")),
                        rs.getString("login")
                        );
                city.setCreationDate(rs.getTimestamp("creation_city_date").toLocalDateTime());
                collection.add(city);
                collection.sort(new CityComparator());
            }
        } catch (Exception ex) {
            System.out.println("ошибка при подключении к бд");
        }
        return "Collection uploaded";
    }

    @Override
    public String execute() {
        return load();
    }
}
