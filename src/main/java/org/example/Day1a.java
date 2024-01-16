package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Day1a {
    public static void main(String[] args) {
        List<String> inputs =  readLines("input1a.txt");
        List<Pair> values = findPairs(inputs);
        int output = sumPairs(values);
        System.out.println(output);
    }


    record Pair(int first, int second) {
    }


    public static List<String> readLines(String name) {
        List<String> lines = new ArrayList<>();
        ClassLoader classLoader = Day1a.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(name);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lines;
    }


    private static List<Pair> findPairs(List<String> lines) {

        List<Pair> pairs = new ArrayList<>();

        for (String line : lines) {
            char[] chars = line.toCharArray();
            int firstDigit = -1, lastDigit = -1;

            for (int i = 0; i < line.length(); i++) {
                if (Character.isDigit(chars[i])) {
                    firstDigit = Integer.parseInt(String.valueOf(chars[i]));
                    break;
                }
            }

            for (int j = line.length() - 1; j >= 0; j--) {
                if (Character.isDigit(chars[j])) {
                    lastDigit = Integer.parseInt(String.valueOf(chars[j]));
                    break;
                }
            }
            pairs.add(new Pair(firstDigit, lastDigit));
        }

        return pairs;
    }

    private static int sumPairs(List<Pair> input) {

        int sum = 0;

        for (Pair pair : input) {
            int number = 10 * pair.first + pair.second;
            sum += number;
        }

        return sum;
    }

}


