package com.lisi4ka.utils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;


public class Checker {

    public static int inputInteger(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String testString;
        int value;
        System.out.print(prompt);
        String message = "The entered value is not integer, enter correct value\nRepeat input: ";
        do {
            testString = scanner.nextLine().trim();
            try {
                value = Integer.parseInt(testString);
                return value;
            } catch (IllegalArgumentException e) {
                System.out.print(message);
            }
        } while (true);
    }
    public static long inputLong(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String testString;
        long value;
        System.out.print(prompt);
        String message = "The entered value is not long, enter correct value\nRepeat input: ";
        do {
            testString = (scanner.nextLine()).trim();
            if ("".equals(testString)){
                throw new IllegalArgumentException();
            }
            try {
                value = Long.parseLong(testString);
                return value;
            } catch (IllegalArgumentException e) {
                System.out.print(message);
            }
        } while (true);
    }
    public static float inputFloat(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String testString;
        float value;
        System.out.print(prompt);
        String message = "The entered value is not float, enter correct value\nRepeat input: ";
        do {
            testString =scanner.nextLine().trim();
            try {
                value = Float.parseFloat(testString);
                return value;
            } catch (IllegalArgumentException e) {
                System.out.print(message);
            }
        } while (true);
    }
    public static double inputDouble(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String testString;
        double value;
        System.out.print(prompt);
        String message = "The entered value is not double, enter correct value\nRepeat input: ";
        do {
            testString = (scanner.nextLine()).trim();
            try {
                value = Double.parseDouble(testString);
                return value;
            } catch (IllegalArgumentException e) {
                System.out.print(message);
            }
        } while (true);
    }
    public static Date inputDate(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String message = "The entered value is not date, enter correct value\nRepeat input: ";
        String date;
        String dateTest;
        System.out.print(prompt);
        do {
            date = (scanner.nextLine()).trim();
            if ("".equals(date)){
                return null;
            }
            dateTest = date.replace(".", "");
            String[] dateArray = date.split("\\.");
            boolean isNumeric;
            try {
                Long.parseLong(dateTest);
                isNumeric = true;
            } catch (NumberFormatException e){
                isNumeric = false;
            }
            if (dateArray.length == 3 && isNumeric && Integer.parseInt(dateArray[2]) > 0 && Integer.parseInt(dateArray[1]) > 0 && Integer.parseInt(dateArray[1]) < 13 && Integer.parseInt(dateArray[0]) < 32 && Integer.parseInt(dateArray[0]) > 0) {
                try {
                    int day = Integer.parseInt(dateArray[0]);
                    int month = Integer.parseInt(dateArray[1]) - 1;
                    int year = Integer.parseInt(dateArray[2]) - 1900;
                    return new Date(year, month, day);
                } catch (NumberFormatException e) {
                    System.out.print(message);
                }
            } else {
                System.out.print(message);
            }
        } while (true);
    }

    public static Date checkDate(String Date) {
        Date = (Date).trim();
        Date = Date.replace(" ", ".");
        String[] DateArray = Date.split("\\.");
        if (DateArray.length == 3 && Integer.parseInt(DateArray[2]) > 0 && Integer.parseInt(DateArray[1]) > 0 && Integer.parseInt(DateArray[1]) < 13 && Integer.parseInt(DateArray[0]) < 32 && Integer.parseInt(DateArray[0]) > 0) {
            try {
                int day = Integer.parseInt(DateArray[0]);
                int month = Integer.parseInt(DateArray[1]) - 1;
                int year = Integer.parseInt(DateArray[2]) - 1900;
                return new Date(year, month, day);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }else {
            throw new IllegalArgumentException();
        }
    }
    public static LocalDateTime checkLocalDateTime(String localDate) {
            localDate = (localDate).trim();
            localDate = localDate.replace(":", ".");
            localDate = localDate.replace(" ", ".");
            String[] localDateArray = localDate.split("\\.");
                try {
                    int day = Integer.parseInt(localDateArray[0]);
                    int month = Integer.parseInt(localDateArray[1]);
                    int year = Integer.parseInt(localDateArray[2]);
                    int hour = Integer.parseInt(localDateArray[3]);
                    int minute = Integer.parseInt(localDateArray[4]);
                    int second = Integer.parseInt(localDateArray[5]);
                    return LocalDateTime.of(year, month, day, hour, minute, second);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException();
                }
    }
    public static String inputStringNotNull(String prompt){
        Scanner scanner = new Scanner(System.in);
        String message = "The entered string is null, enter correct string\nRepeat input: ";
        String string;
        System.out.print(prompt);
        do {
            string = (scanner.nextLine()).trim();
            if (string.isEmpty()) {
                System.out.print(message);
                }
            else{
                return string;
            }
        } while (true);
    }
    public static String inputString(String prompt){
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return (scanner.nextLine()).trim();
    }
    public static int inputEnumNumber(String prompt, int maxValue, Boolean isNull){
        Scanner scanner = new Scanner(System.in);
        String testString;
        int value;
        System.out.print(prompt);
        String message = "The entered value is not correct, enter correct value\nRepeat input: ";
        do {
            testString = (scanner.nextLine()).trim();
            if ("".equals(testString) && isNull){
                return 0;
            }
            else {
                try {
                    value = Integer.parseInt(testString);
                } catch (IllegalArgumentException e) {
                    System.out.print(message);
                    continue;
                }
                if (1 > value || value > maxValue) {
                    System.out.print(message);
                }
                else{
                    return value;
                }
            }
        } while (true);
    }
}
