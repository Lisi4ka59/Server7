package com.lisi4ka.commands;

import com.lisi4ka.models.*;
import com.lisi4ka.utils.CityComparator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static com.lisi4ka.commands.BdConnect.conn;
import static com.lisi4ka.utils.Checker.checkDate;
import static com.lisi4ka.utils.CityLinkedList.idRepeat;

/**
 * add command, use to make a city
 *
 * @version 1.0
 * @autor Mikhail Nachinkin
 */
public class AddCommand implements Command {
    private final List<City> collection;

    public AddCommand(List<City> collection) {
        this.collection = collection;
    }

    @Override
    public String execute(String args, String login) {
        City city = getCityArgs(args, login);

        collection.add(city);
        idRepeat += 1;
        collection.sort(new CityComparator());
        return "Congratulations! City added to collection\n";
    }

    public static City getCityArgs(String args, String login) {
        String[] cityArgs = args.split(",");
        String name = cityArgs[0];
        double x = Double.parseDouble(cityArgs[1]);
        float y = Float.parseFloat(cityArgs[2]);
        double area = Double.parseDouble(cityArgs[3]);
        long population = Long.parseLong(cityArgs[4]);
        int metersAboveSeaLevel = Integer.parseInt(cityArgs[5]);
        Climate climate = Climate.fromInt(Integer.parseInt(cityArgs[6]));
        Government government;
        if ("null".equals(cityArgs[7])) {
            government = null;
        } else {
            government = Government.fromInt(Integer.parseInt(cityArgs[7]));
        }
        StandardOfLiving standardOfLiving;
        if ("null".equals(cityArgs[8])) {
            standardOfLiving = null;
        } else {
            standardOfLiving = StandardOfLiving.fromInt(Integer.parseInt(cityArgs[8]));
        }
        long age;
        Human governor;
        if ("null".equals(cityArgs[9]) || "null".equals(cityArgs[10])) {
            governor = null;
        } else {
            age = Long.parseLong(cityArgs[9]);
            Date birthday = checkDate(cityArgs[10]);
            governor = new Human(age, birthday);
        }
        Coordinates coordinates = new Coordinates(x, y);
        City city = new City(1, name, coordinates, population, area, metersAboveSeaLevel, climate, government, standardOfLiving, governor, login);
        try {
            PreparedStatement stmt;
            stmt = conn.prepareStatement(" INSERT INTO city(name, coordinate_x, coordinate_y, area, population, meters_above_sea_level, climate, government, standard_of_living, governor_age, governor_birthday, user_id)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (select id from account where login = ?))");
            stmt.setString(1, city.getName());
            stmt.setDouble(2, city.getCoordinates().getX());
            stmt.setFloat(3, city.getCoordinates().getY());
            stmt.setDouble(4, city.getArea());
            stmt.setLong(5, city.getPopulation());
            stmt.setInt(6, city.getMetersAboveSeaLevel());
            stmt.setString(7, city.getClimate().name());
            if (city.getGovernment() == null){
                stmt.setNull(8, 12);
            }else {
                stmt.setString(8, city.getGovernment().name());
            }
            if (city.getStandardOfLiving() == null){
                stmt.setNull(9, 12);
            }else {
                stmt.setString(9, city.getStandardOfLiving().name());
            }
            if (city.getGovernor() == null){
                stmt.setNull(10, 4);
                stmt.setNull(11, 91);
            }else{
                stmt.setLong(10, city.getGovernor().age());
                stmt.setDate(11, new java.sql.Date(city.getGovernor().birthday().getTime()));
            }
            stmt.setString(12, city.getUser());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT currval(pg_get_serial_sequence('city','id'))");
            while (rs.next()){
                city.setId(rs.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace(System.out);
            throw  new RuntimeException();
        }
        return city;
    }
}