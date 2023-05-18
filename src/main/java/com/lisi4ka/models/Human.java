package com.lisi4ka.models;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

/**
 * @param age Значение поля должно быть больше 0
 */
public record Human(long age, java.util.Date birthday) implements Jsonable {
    public String getStringBirthday() {
        return new SimpleDateFormat("dd.MM.yyyy").format(birthday());
    }

    @Override
    public String toJson() {
        final StringWriter writable = new StringWriter();
        try {
            this.toJson(writable);
        } catch (final IOException e) {
            System.out.println("Illegal arguments in human!");
        }
        return writable.toString();
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("age", this.age());
        json.put("birthday", getStringBirthday());
        json.toJson(writer);
    }

    @Override
    public String toString() {
        return String.format("Age = %d, birthday = %s\n", age(), getStringBirthday());
    }
}