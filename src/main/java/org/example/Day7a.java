package org.example;

import java.util.*;

import static org.example.Day1a.readLines;

public class Day7a {


    public static void main(String[] args) {
        List<Hand> hands = parseInput();
        List<HandWithSortingHelpers> handWithValues = computeHandsWithSortingHelpers(hands);
        List<HandWithSortingHelpers> sortedHands = sortedHands(handWithValues);
        int sum = calculateBidRankProductSum(sortedHands);

        System.out.println(sum);
    }


    private enum Card {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;


        public static final Map<Character, Card> charCardMap = Map.ofEntries(
                Map.entry('2', Card.TWO),
                Map.entry('3', Card.THREE),
                Map.entry('4', Card.FOUR),
                Map.entry('5', Card.FIVE),
                Map.entry('6', Card.SIX),
                Map.entry('7', Card.SEVEN),
                Map.entry('8', Card.EIGHT),
                Map.entry('9', Card.NINE),
                Map.entry('T', Card.TEN),
                Map.entry('J', Card.JACK),
                Map.entry('Q', Card.QUEEN),
                Map.entry('K', Card.KING),
                Map.entry('A', Card.ACE)
        );


        public static final Map<Card, Integer> cardIntegerMap = Map.ofEntries(
                Map.entry(Card.TWO, 0),
                Map.entry(Card.THREE, 1),
                Map.entry(Card.FOUR, 2),
                Map.entry(Card.FIVE, 3),
                Map.entry(Card.SIX, 4),
                Map.entry(Card.SEVEN, 5),
                Map.entry(Card.EIGHT, 6),
                Map.entry(Card.NINE, 7),
                Map.entry(Card.TEN, 8),
                Map.entry(Card.JACK, 9),
                Map.entry(Card.QUEEN, 10),
                Map.entry(Card.KING, 11),
                Map.entry(Card.ACE, 12)
        );
    }


    private enum HandType {
        FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIR, PAIR, HIGH_CARD;


        public static final Map<HandType, Integer> handTypeValues = Map.ofEntries(
                Map.entry(HandType.HIGH_CARD, 1),
                Map.entry(HandType.PAIR, 2),
                Map.entry(HandType.TWO_PAIR, 3),
                Map.entry(HandType.THREE_OF_A_KIND, 4),
                Map.entry(HandType.FULL_HOUSE, 5),
                Map.entry(HandType.FOUR_OF_A_KIND, 6),
                Map.entry(HandType.FIVE_OF_A_KIND, 7)
        );


    }


    private record Hand(Card[] cards, int bid) {
    }


    private record HandWithSortingHelpers(Card[] cards, int bid, int value, HandType handType) {
    }


    private static int calculateBidRankProductSum(List<HandWithSortingHelpers> sortedList) {

        int rank = 1;
        int sum = 0;

        for (HandWithSortingHelpers hand : sortedList) {
            sum += hand.bid * (rank++);
        }

        return sum;
    }

    private static List<HandWithSortingHelpers> sortedHands(List<HandWithSortingHelpers> hands) {

        List<HandWithSortingHelpers> output = new ArrayList<>(hands);

        output.sort(Comparator.comparingInt((HandWithSortingHelpers h) -> HandType.handTypeValues
                .get(h.handType)).thenComparingInt(h -> h.value));

        return output;
    }


    private static HandType determineHandTypeFromStack(int[] stackCounter) {

        if (stackCounter[5] == 1) {
            return HandType.FIVE_OF_A_KIND;
        } else if (stackCounter[4] == 1) {
            return HandType.FOUR_OF_A_KIND;
        } else if (stackCounter[3] == 1 && stackCounter[2] == 1) {
            return HandType.FULL_HOUSE;
        } else if (stackCounter[3] == 1) {
            return HandType.THREE_OF_A_KIND;
        } else if (stackCounter[2] == 2) {
            return HandType.TWO_PAIR;
        } else if (stackCounter[2] == 1) {
            return HandType.PAIR;
        } else {
            return HandType.HIGH_CARD;
        }
    }


    private static HandType determineHandType(Hand hand) {
        int[] cardCounter = new int[13];

        for (Card card : hand.cards) {
            int cardValue = Card.cardIntegerMap.get(card);

            cardCounter[cardValue]++;
        }

        int[] stackCounter = new int[6];

        for (int i = 0; i < cardCounter.length; i++) {
            int stackSize = cardCounter[i];

            stackCounter[stackSize]++;
        }

        return determineHandTypeFromStack(stackCounter);
    }


    private static int determineHandValue(Hand hand) {
        Card[] cards = hand.cards;

        int value = 0;

        for (int i = cards.length - 1; i >= 0; i--) {

            int coefficient = Card.cardIntegerMap.get(cards[i]);
            int exponent = (cards.length - 1) - i;
            int base = 13;

            value += coefficient * (int) Math.pow(base, exponent);
        }

        return value;

    }


    private static List<HandWithSortingHelpers> computeHandsWithSortingHelpers(List<Hand> hands) {

        List<HandWithSortingHelpers> output = new ArrayList<>();

        for (Hand hand : hands) {

            int value = determineHandValue(hand);
            HandType handType = determineHandType(hand);

            output.add(new HandWithSortingHelpers(hand.cards, hand.bid, value, handType));

        }

        return output;
    }


    private static List<Hand> parseInput() {
        List<String> lines = readLines("input7a.txt");
        List<Hand> hands = new ArrayList<>();

        for (String line : lines) {

            String[] parts = line.split("\\s+");

            int bid = Integer.parseInt(parts[1]);
            char[] cardChars = parts[0].toCharArray();
            Card[] cards = new Card[cardChars.length];

            for (int i = 0; i < cards.length; i++) {
                cards[i] = Card.charCardMap.get(cardChars[i]);

            }
            hands.add(new Hand(cards, bid));
        }

        return hands;
    }


}
