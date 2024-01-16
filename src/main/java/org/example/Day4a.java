package org.example;

import java.util.*;

import static org.example.Day1a.readLines;

public class Day4a {


    public static void main(String[] args) {

        List<String> lines = readLines("input4a.txt");
        List<Game> wins = parseAllLines(lines);
        List<Integer> numsWinningGames = findNumberOfWinningGames(wins);
        int totalScore = calculateTotalScore(numsWinningGames);
        System.out.println(totalScore);

    }


    private record Game(List<Integer> winning, List<Integer> own) {

    }


    private static int calculateTotalScore(List<Integer> numWinsList) {

        int output = 0;

        for (Integer numWins : numWinsList) {
            output += calculateScoreOfGame(numWins);
        }

        return output;
    }


    private static int calculateScoreOfGame(int numWins) {

        if (numWins == 0) {
            return 0;
        }

        return 1 << (numWins - 1);
    }


    private static List<Integer> findNumberOfWinningGames(List<Game> games) {

        List<Integer> output = new ArrayList<>(games.size());

        for (Game game : games) {

            List<Integer> winning = new ArrayList<>(game.winning);
            List<Integer> own = new ArrayList<>(game.own);

            Collections.sort(winning);
            Collections.sort(own);

            int indexWin = 0;
            int indexOwn = 0;

            int numMatches = 0;

            while (true) {

                if (indexWin >= winning.size()) {
                    break;
                }

                if (indexOwn >= own.size()) {
                    break;
                }

                int currentWin = winning.get(indexWin);
                int currentOwn = own.get(indexOwn);

                if (currentWin == currentOwn) {
                    numMatches++;
                    indexOwn++;
                } else if (currentWin > currentOwn) {
                    indexOwn++;
                } else if (currentWin < currentOwn) {
                    indexWin++;
                }
            }
            output.add(numMatches);
        }

        return output;
    }


    private static List<Game> parseAllLines(List<String> lines) {

        List<Game> output = new ArrayList<>();

        for (String line : lines) {
            Game game = parseLine(line);
            output.add(game);
        }

        return output;

    }


    private static Game parseLine(String line) {

        String body = line.split(":\\s+")[1];
        String[] parts = body.split("\\s+\\|\\s+");

        String[] winningStrs = parts[0].split("\\s+");
        String[] ownStrs = parts[1].split("\\s+");

        List<Integer> winningInts = new ArrayList<>();
        List<Integer> ownInts = new ArrayList<>();

        for (String s : winningStrs) {
            winningInts.add(Integer.parseInt(s));
        }

        for (String s : ownStrs) {
            ownInts.add(Integer.parseInt(s));
        }

        return new Game(winningInts, ownInts);
    }


}
