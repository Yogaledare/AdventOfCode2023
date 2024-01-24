package org.example;

import java.util.*;

import static org.example.InputFilesReader.readLines;

public class Day2a {


    public static void main(String[] args) {


        List<String> lines = readLines("input2a.txt");

        List<Day> days = parseAllDays(lines);

        int sum = addIdsOfImpossibleDays(days);

        System.out.println(sum);
    }


    private enum Color {
        BLUE, RED, GREEN
    }


    private record Day(int dayNumber, List<Map<Color, Integer>> cubeSets) {
    }


    private static int addIdsOfImpossibleDays(List<Day> days) {

        int sum = 0;

        for (Day day : days) {
            if (isDayImpossible(12, 13, 14, day)) {
                sum += day.dayNumber;
            }
        }

        return sum;
    }


    private static boolean isDayImpossible(int redLimit, int greenLimit, int blueLimit, Day day) {

        List<Map<Color, Integer>> sets = day.cubeSets;

        for (Map<Color, Integer> set : sets) {
            if (set.getOrDefault(Color.RED, 0) > redLimit) {
                return true;
            }

            if (set.getOrDefault(Color.GREEN, 0) > greenLimit) {
                return true;
            }

            if (set.getOrDefault(Color.BLUE, 0) > blueLimit) {
                return true;
            }
        }

        return false;
    }


    private static List<Day> parseAllDays(List<String> lines) {

        List<Day> days = new ArrayList<>();

        for (String line : lines) {
            Day day = parseDayString(line);
            days.add(day);
        }

        return days;
    }


    private static Day parseDayString(String line) {

        String[] parts = line.split(": ");
        int dayNumber;
        List<Map<Color, Integer>> cubeSets = new ArrayList<>();

//        process head
        String gameNumberStr = parts[0].replace("Game ", "").trim();
        dayNumber = Integer.parseInt(gameNumberStr);

//        process tail
        String[] gamesStr = parts[1].split("; ");
        for (String s : gamesStr) {

            Map<Color, Integer> cubeSet = new HashMap<>();
            String[] set = s.split(", ");

            for (String string : set) {
                String[] amountColorStr = string.split(" ");
                int amount = Integer.parseInt(amountColorStr[0]);
                Color color = Color.valueOf(amountColorStr[1].toUpperCase());

                cubeSet.put(color, amount);
            }

            cubeSets.add(cubeSet);
        }

        Day day = new Day(dayNumber, cubeSets);
        return day;
    }


}
