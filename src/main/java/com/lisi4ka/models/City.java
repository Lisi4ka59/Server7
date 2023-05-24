package com.lisi4ka.models;

import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import static com.lisi4ka.common.ServerApp.cities;
import static com.lisi4ka.utils.Checker.checkLocalDateTime;
import static com.lisi4ka.utils.CityLinkedList.idRepeat;

public class City implements Jsonable, Comparable<City> {
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private double area; //Значение поля должно быть больше 0
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null
    private int metersAboveSeaLevel;
    private Climate climate; //Поле не может быть null
    private Government government; //Поле может быть null
    private StandardOfLiving standardOfLiving; //Поле может быть null
    private Human governor; //Поле может быть null
    private String user;
    public City (int id, String name, Coordinates coordinates, Long population, double area, int metersAboveSeaLevel,
                 Climate climate, Government government, StandardOfLiving standardOfLiving, Human governor, String user){
        //Date date = new Date();
        //id = date.getTime() + idRepeat;
        this.id = id;
        creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.population = population;
        this.area = area;
        this.climate = climate;
        this.standardOfLiving = standardOfLiving;
        this.government = government;
        this.governor = governor;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.user = user;
    }
    public City (JsonObject jsonObject){
        final JsonKey nameKey = Jsoner.mintJsonKey("name", "");
        final JsonKey populationKey = Jsoner.mintJsonKey("population", "");
        final JsonKey areaKey = Jsoner.mintJsonKey("area", "");
        final JsonKey xKey = Jsoner.mintJsonKey("x", "");
        final JsonKey yKey = Jsoner.mintJsonKey("y", "");
        final JsonKey standardOfLivingKey = Jsoner.mintJsonKey("standard_of_living", "");
        final JsonKey governmentKey = Jsoner.mintJsonKey("government", "");
        final JsonKey climateKey = Jsoner.mintJsonKey("climate", "");
        final JsonKey ageKey = Jsoner.mintJsonKey("age", "");
        final JsonKey birthdayKey = Jsoner.mintJsonKey("birthday", "");
        final JsonKey idKey = Jsoner.mintJsonKey("id", "");
        final JsonKey creationDateKey = Jsoner.mintJsonKey("creation_date", "");
        final JsonKey metersAboveSeaLevelKey = Jsoner.mintJsonKey("meters_above_sea_level", "");
        String creationDateString = jsonObject.getString(creationDateKey);
        creationDate = checkLocalDateTime(creationDateString);
        setCreationDate(creationDate);
        setName(jsonObject.getString(nameKey));
        setId(jsonObject.getInteger(idKey));
        JsonObject joCoordinates  = (JsonObject)jsonObject.get ("coordinates");
        setJsonCoordinates(joCoordinates.getDouble(xKey),joCoordinates.getFloat(yKey));
        setPopulation(jsonObject.getLong(populationKey));
        setMetersAboveSeaLevel(jsonObject.getInteger(metersAboveSeaLevelKey));
        setArea(jsonObject.getDouble(areaKey));
        if (jsonObject.getString(standardOfLivingKey)==null)
            setStandardOfLiving(null);
        else
            setStandardOfLiving(StandardOfLiving.valueOf(jsonObject.getString(standardOfLivingKey)));
        if (jsonObject.getString(governmentKey)==null)
            setGovernment(null);
        else
            setGovernment(Government.valueOf(jsonObject.getString(governmentKey)));
        this.climate =Climate.valueOf(jsonObject.getString(climateKey));
        JsonObject joGovernor = (JsonObject) jsonObject.get("governor");
        if (joGovernor == null){
            setGovernor(null);
        }
        else{
            String birthdayString=joGovernor.getString(birthdayKey);
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            Date birthday;
            try {
                birthday = df.parse(birthdayString);
            } catch (ParseException i) {
                birthday = null;
            }
            setGovernor(new Human(joGovernor.getLong(ageKey), birthday));
        }
    }

    public void setCreationDate(LocalDateTime creationDate){
        if (creationDate == null) {
            throw new NullPointerException("Field creationDate can not be null");
        }
        this.creationDate=creationDate;
    }
    public String getUser(){return user;}

    public String getCreationDate (){
        return creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
    public Integer getId (){
        return id;
    }
    public void setId (Integer id){
        if (id == null) {
            throw new NullPointerException("Field id can not be null!");
        }
        if (id<1){
            throw new IllegalArgumentException("Field id can not be less than 1!");
        }
        ArrayList<Integer> list = new ArrayList<>();
        boolean right = true;
        for (City city:cities) {
            list.add(city.id);
            if (list.contains(id)) {
                right = false;
                System.out.printf("%s ID is not unique! ID for city %s will be generate automatic\n", getName(), getName());
                Date date = new Date();
                id = Math.toIntExact(date.getTime() + idRepeat);
                idRepeat+=1;
                this.id = id;
            }
        }
        if (right){
            this.id = id;
        }
    }

    public void setCoordinates (Coordinates coordinates){
        if (coordinates == null) {
            throw new NullPointerException("Field coordinates can not be null");
        }
        this.coordinates = coordinates;
    }
    public void setJsonCoordinates(double x, float y){
        if (x<=-25){
            throw new IllegalArgumentException("X can not be equals or less than -25");
        }
        this.coordinates = new Coordinates(x, y);
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public void setPopulation (Long population){
        if (population == null ) {
            throw new NullPointerException("Field population can not be null or less than 0");
        }
        if (population <= 0){
            throw new IllegalArgumentException("Field population can not be less or equals than 0");
        }
        this.population=population;
    }

    public Long getPopulation (){
        return population;
    }

    public void setArea (double area){
        if (area <= 0) {
            throw new IllegalArgumentException("Field area can not be less or equals than 0");
        }
        this.area = area;
    }

    public double getArea (){
        return area;
    }
    public String getStringArea(){
        return String.format("%.2f\n", area);
    }

    public void setMetersAboveSeaLevel (int metersAboveSeaLevel){
        if (metersAboveSeaLevel < -432 || metersAboveSeaLevel > 8849) {
            throw new IllegalArgumentException("Field metersAboveSeaLevel can not be less than -432 or more than 8849!");
        }
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public Integer getMetersAboveSeaLevel (){
        return metersAboveSeaLevel;
    }

    public void setClimate (Climate climate){
        if (climate == null) {
            throw new NullPointerException("Field climate can not be null");

        }

        this.climate = climate;
    }

    public Climate getClimate (){
        return climate;
    }

    public void setGovernment (Government government){
        this.government = government;
    }
    public Government getGovernment (){
        return government;
    }

    public void setStandardOfLiving (StandardOfLiving standardOfLiving){
        this.standardOfLiving = standardOfLiving;
    }

    public StandardOfLiving getStandardOfLiving (){
        return standardOfLiving;
    }

    public void setGovernor (Human governor){
        this.governor = governor;
    }
    public void setJsonGovernor(long age, Date birthday){
        this.governor = new Human(age, birthday);
    }

    public Human getGovernor (){
        return governor;
    }

    public void setName (String name){
        if (name == null) {
            throw new NullPointerException("Field name can not be null");
        }
        this.name = name;
    }

    public String getName (){
        return name;
    }
    @Override
    public String toString(){
        return String.format("Name: %s\nUser: %s\nID: %d\nCreation date: %s\nCoordinates: %sStandard of living: %s\nGovernment: %s\nClimate: %s\nArea: %sPopulation: %d\nMeters above sea level: %d\nGovernor %s", getName(), getUser(), getId(), getCreationDate(), getCoordinates(), getStandardOfLiving(), getGovernment(), getClimate(), getStringArea(), getPopulation(), getMetersAboveSeaLevel(), getGovernor());
    }

    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            this.toJson(writable);
        } catch (final IOException ignored) {
        }
        return writable.toString();
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("name", this.getName());
        json.put("id", this.getId());
        json.put("creation_date", this.getCreationDate());
        json.put("coordinates", this.getCoordinates());
        json.put("population", this.getPopulation());
        json.put("area", this.getArea());
        json.put("meters_above_sea_level", this.getMetersAboveSeaLevel());
        json.put("climate", this.getClimate().name());
        if (this.getGovernment()==null)
            json.put("government", null);
        else
            json.put("government", this.getGovernment().name());
        if (this.getStandardOfLiving()==null)
            json.put("standard_of_living", null);
        else
            json.put("standard_of_living", this.getStandardOfLiving().name());
        if (this.getGovernor()==null)
            json.put("governor", null);
        else
            json.put("governor", this.getGovernor());
        json.toJson(writer);
    }

    @Override
    public int compareTo(City o) {
        if (o!=null)
            return  -name.compareToIgnoreCase(o.name);
        else
            return 1;
    }
}