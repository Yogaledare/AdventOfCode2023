package org.example;

import java.util.List;

import static org.example.InputFilesReader.readLines;

public class Day6a {


    public static void main(String[] args) {

        Race[] races = parseInput();
        int product = findNumWinningCombinations(races);

        System.out.println(product);

    }


    record Pair(int low, int high) {
    }


    record Roots(double low, double high) {
    }


    record Race(int time, int distance) {
    }


    private static Pair findLimits(Roots roots) {

        int lower = (int) Math.floor(roots.low + 1);
        int upper = (int) Math.ceil(roots.high - 1);

        if (lower > upper) {
            throw new IllegalArgumentException("No integers satisfy the requirement");
        }

        return new Pair(lower, upper);
    }


    private static Roots findRootsToParabola(double p, double q) {

        double discriminant = Math.pow(p / 2, 2) - q;

        if (discriminant < 0) {
            throw new IllegalArgumentException("Error: complex roots");
        }

        double low = -p / 2 - Math.sqrt(discriminant);
        double high = -p / 2 + Math.sqrt(discriminant);

        return new Roots(low, high);
    }


    private static int findNumWinningCombinations(Race[] races) {

        int product = 1;

        for (Race race : races) {
            product *= findNumWinningSpeeds(race);
        }

        return product;
    }


    private static int findNumWinningSpeeds(Race race) {

        Roots roots = findRootsToParabola(-race.time, race.distance);
        Pair limits = findLimits(roots);

        return limits.high - limits.low + 1;
    }


    private static Race[] parseInput() {

        List<String> lines = readLines("input6a.txt");

        String timesStr = lines.get(0).replaceAll("Time:\\s+", "");
        int[] times = parseNumLineToInt(timesStr);

        String distsStr = lines.get(1).replaceAll("Distance:\\s+", "");
        int[] dists = parseNumLineToInt(distsStr);

        if (times.length != dists.length) {
            throw new IllegalArgumentException("input mismatch: times.length != dists.length");
        }

        Race[] races = new Race[times.length];

        for (int i = 0; i < races.length; i++) {
            races[i] = new Race(times[i], dists[i]);
        }

        return races;
    }


    private static int[] parseNumLineToInt(String line) {
        String[] numStrings = line.split("\\s+");
        int[] nums = new int[numStrings.length];

        for (int i = 0; i < numStrings.length; i++) {
            nums[i] = Integer.parseInt((numStrings[i]));
        }

        return nums;
    }


}


//
//    private static int[] convertLongArrayToIntArray(long[] longs) {
//
//        int[] ints = new int[longs.length];
//
//        for (int i = 0; i < longs.length; i++) {
//            ints[i] = (int) ints[i];
//        }
//
//        return ints;
//    }


//        int[] numWinningCombs = new int[races.length];
//
//
//        for (int i = 0; i < numWinningCombs.length; i++) {
//            numWinningCombs[i] = findNumWinningSpeeds(races[i]);
//        }





