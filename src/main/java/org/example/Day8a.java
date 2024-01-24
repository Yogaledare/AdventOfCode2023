package org.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.InputFilesReader.readLines;

public class Day8a {


    public static void main(String[] args) {

        List<String> lines = readLines("input8a.txt");
        Direction[] directions = parseDirections(lines);
        Map<String, Connections> graph = parseGraph(lines);
        int steps = traverseGraph(graph, directions);
    }


    private enum Direction {
        L, R;
    }


    private record Connections(String left, String right) {
    }


    private static int traverseGraph(Map<String, Connections> graph, Direction[] directions) {

        System.out.println();
        int i = 0;
        String current = "AAA";
        String goal = "ZZZ";
        int steps = 0;

        while (true) {

            int index = i++ % directions.length;

            switch (directions[index]) {

                case L -> current = graph.get(current).left;
                case R -> current = graph.get(current).right;
            }

            steps++;

            if (current.equals(goal)) {
                System.out.println("steps: " + steps);
                break;
            }
        }

        return steps;

    }


    private static Map<String, Connections> parseGraph(List<String> lines) {

        Iterator<String> lineIterator = lines.iterator();
        Map<String, Connections> graph = new HashMap<>();
        Pattern pattern = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");

//        skip first line
        if (lineIterator.hasNext()) {
            lineIterator.next();
        }

//        skip second (blank) line
        if (lineIterator.hasNext()) {
            lineIterator.next();
        }

        while (lineIterator.hasNext()) {
            String line = lineIterator.next();
            Matcher matcher = pattern.matcher(line);

            if (!matcher.matches()) {
                continue;
            }

            String node = matcher.group(1);
            String left = matcher.group(2);
            String right = matcher.group(3);

            graph.put(node, new Connections(left, right));
        }

        return graph;
    }


    private static Direction[] parseDirections(List<String> lines) {

        String firstLine = lines.get(0);
        String[] charStrings = firstLine.split("");
        Direction[] output = new Direction[charStrings.length];

        for (int i = 0; i < charStrings.length; i++) {
            output[i] = Direction.valueOf(charStrings[i]);
        }

        return output;
    }
}


//    private static void parseInput() {
//
//        List<String> lines = readLines("input8a.txt");
//        Direction[] directions = parseDirections(lines);
//
//        Map<String, Connections> graph = parseGraph(lines);
//
//        System.out.println(Arrays.toString(directions));
//
//    }


//
//
//    private static String getStartingNode(List<String> lines) {
//        return lines.get(2).substring(0, 3);
//    }
//
//
