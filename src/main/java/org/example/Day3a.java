package org.example;

import java.util.*;

import static org.example.InputFilesReader.readLines;

public class Day3a {

    public static void main(String[] args) {


        List<String> lines = readLines("input3a.txt");
        char[][] grid = createGrid(lines);
        int sum = findSumOfGridNumbersAdjacentToSymbol(grid);
        System.out.println(sum);
    }


    private record GridNumber(int jStart, int jEnd, int iPos, int value) {

    }

    private record NumberPosition(int i, int j, int value) {

    }


    private static int findSumOfGridNumbersAdjacentToSymbol(char[][] grid) {

        List<GridNumber> gridNumbers = findGridNumbers(grid);

        int sum = 0;

        for (GridNumber gridNumber : gridNumbers) {
            if (isAdjacentToSymbol(gridNumber, grid)) {
                sum += gridNumber.value;
            }
        }

        return sum;
    }


    private static List<GridNumber> findGridNumbers(char[][] grid) {

        List<GridNumber> output = new ArrayList<>();
        Stack<NumberPosition> numberStack = new Stack<>();
        for (int i = 0; i < grid.length; i++) {
//        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                char c = grid[i][j];
                if (Character.isDigit(c)) {
                    int digit = c - '0';
                    numberStack.add(new NumberPosition(i, j, digit));
                } else if (!numberStack.isEmpty()) {
                    GridNumber gridNumber = buildNumber(numberStack);
                    output.add(gridNumber);
                }
            }
            if (!numberStack.isEmpty()) {
                GridNumber gridNumber = buildNumber(numberStack);
                output.add(gridNumber);
            }
        }

        return output;
    }


    private static GridNumber buildNumber(Stack<NumberPosition> stack) {

        if (stack.isEmpty()) {
            throw (new IllegalArgumentException("empty stack passed"));
        }

        int power = 0;
        int value = 0;

        NumberPosition current = stack.peek();

        int end = current.j;
        int line = current.i;

        while (!stack.isEmpty()) {
            current = stack.pop();
            value += (int) (current.value * Math.pow(10, power++));
        }

        int start = current.j;

        return new GridNumber(start, end, line, value);

    }


    private static boolean isAdjacentToSymbol(GridNumber gridNumber, char[][] grid) {

        String symbols = "+-/*=#$@&";
        List<Character> border = new ArrayList<>();

        for (int i = gridNumber.iPos - 1; i <= gridNumber.iPos + 1; i++) {
            for (int j = gridNumber.jStart - 1; j <= gridNumber.jEnd + 1; j++) {
                if (i == gridNumber.iPos && j >= gridNumber.jStart && j <= gridNumber.jEnd) {
                    continue;
                }

                if (!isInsideGrid(i, j, grid)) {
                    continue;
                }

                border.add(grid[i][j]);
            }
        }

        for (Character c : border) {
            if (symbols.indexOf(c) != -1) {
                return true;
            }
        }

        return false;
    }


    private static boolean isInsideGrid(int i, int j, char[][] grid) {

        if (grid.length == 0 || grid[0].length == 0) {
            return false;
        }

        if (i < 0) {
            return false;
        }

        if (i >= grid.length) {
            return false;
        }

        if (j < 0) {
            return false;
        }

        if (j >= grid[0].length) {
            return false;
        }

        return true;
    }


    private static char[][] createGrid(List<String> lines) {

        int height = lines.size();
        int width = lines.get(0).length();
        char[][] output = new char[height][width];

        for (int i = 0; i < height; i++) {
            String line = lines.get(i);
            for (int j = 0; j < width; j++) {
                output[i][j] = line.charAt(j);
            }
        }

        return output;
    }


}



