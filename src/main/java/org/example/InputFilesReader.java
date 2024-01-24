package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class InputFilesReader {


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
}
