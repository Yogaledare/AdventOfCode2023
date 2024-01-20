package org.example;

import java.util.*;

import static org.example.Day1a.readLines;

public class Day5a {

    public static void main(String[] args) {
        List<String> lines = readLines("input5a.txt");
        long[] seeds = parseSeeds(lines);
        Map<ResourcePair, List<Mapping>> mappings = parseAllMappings(lines);
        long[] locations = findLocations(seeds, mappings);
        System.out.println(Arrays.toString(seeds) + " (seeds)");
        System.out.println(Arrays.toString(locations) + " (locations)");

        long min = findMin(locations);
        System.out.println("Min: " + min);
    }


    private enum Resource {
        SEED, SOIL, FERTILIZER, WATER, LIGHT, TEMPERATURE, HUMIDITY, LOCATION
    }


    private record ResourcePair(Resource from, Resource to) {
    }


    private record Mapping(long sourceStart, long destStart, long range) implements Comparable<Mapping> {
        @Override
        public int compareTo(Mapping other) {
            return Long.compare(this.sourceStart, other.sourceStart());
        }
    }


    private static long findMin(long[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("array null or size 0");
        }

        long min = array[0];

        for (long l : array) {
            if (l < min) {
                min = l;
            }
        }

        return min;
    }


    private static void printMappings(Map<ResourcePair, List<Mapping>> mappings) {
        for (ResourcePair resourcePair : mappings.keySet()) {
            System.out.println(resourcePair.toString());
            for (Mapping mapping : mappings.get(resourcePair)) {
                System.out.println(mapping);
            }
            System.out.println();

        }
    }


    private static long[] findLocations(long[] seeds, Map<ResourcePair, List<Mapping>> mappings) {

        List<List<Mapping>> sequence = new ArrayList<>();
        long[] locations = new long[seeds.length];

        sequence.add(mappings.get(new ResourcePair(Resource.SEED, Resource.SOIL)));
        sequence.add(mappings.get(new ResourcePair(Resource.SOIL, Resource.FERTILIZER)));
        sequence.add(mappings.get(new ResourcePair(Resource.FERTILIZER, Resource.WATER)));
        sequence.add(mappings.get(new ResourcePair(Resource.WATER, Resource.LIGHT)));
        sequence.add(mappings.get(new ResourcePair(Resource.LIGHT, Resource.TEMPERATURE)));
        sequence.add(mappings.get(new ResourcePair(Resource.TEMPERATURE, Resource.HUMIDITY)));
        sequence.add(mappings.get(new ResourcePair(Resource.HUMIDITY, Resource.LOCATION)));

        for (int i = 0; i < seeds.length; i++) {
            long current = seeds[i];
            for (List<Mapping> step : sequence) {
                current = findMappedDest(current, step);
            }
            locations[i] = current;
        }

        return locations;
    }


    private static long findMappedDest(long source, List<Mapping> mappings) {

        int low = 0;
        int high = mappings.size() - 1;

        while (low <= high) {

            int mid = (low + high) / 2;
            Mapping currentMapping = mappings.get(mid);
            long intervalStart = currentMapping.sourceStart;
            long intervalEnd = intervalStart + currentMapping.range - 1;

            if (source < intervalStart) {
                high = mid - 1;
            } else if (source > intervalEnd) {
                low = mid + 1;
            } else {
                long delta = source - intervalStart;
                return currentMapping.destStart + delta;
            }
        }

        return source;
    }


    private static Map<ResourcePair, List<Mapping>> parseAllMappings(List<String> lines) {
        Map<ResourcePair, List<Mapping>> mappings = new HashMap<>();
        Iterator<String> iterator = lines.iterator();

//        skip first line (seeds line)
        if (iterator.hasNext()) {
            iterator.next();
        }

        while (iterator.hasNext()) {
            String line = iterator.next();

            if (line.endsWith("map:")) {
                ResourcePair key = parseMappingHeader(line);
                List<Mapping> localMapping = parseMappingBody(iterator);
                mappings.put(key, localMapping);
            }
        }

        return mappings;
    }


    private static ResourcePair parseMappingHeader(String line) {
        String[] fromTo = line.split("\\s+")[0].split("-to-");
        Resource from = Resource.valueOf(fromTo[0].toUpperCase());
        Resource to = Resource.valueOf(fromTo[1].toUpperCase());

        return new ResourcePair(from, to);
    }


    private static List<Mapping> parseMappingBody(Iterator<String> listIterator) {
        List<Mapping> mappings = new ArrayList<>();

        while (listIterator.hasNext()) {
            String line = listIterator.next();

            if (line.isEmpty()) {
                break;
            }

            Mapping mapping = parseMappingLine(line);
            mappings.add(mapping);
        }

        Collections.sort(mappings);

        return mappings;
    }


    private static Mapping parseMappingLine(String line) {
        long[] nums = parseNumLineToLong(line);
        long sourceStart = nums[1];
        long destStart = nums[0];
        long range = nums[2];

        return new Mapping(sourceStart, destStart, range);
    }


    private static long[] parseSeeds(List<String> lines) {
        String seeds = lines.get(0);
        String numbers = seeds.replaceAll("seeds:\\s+", "");
        return parseNumLineToLong(numbers);
    }


    public static long[] parseNumLineToLong(String line) {
        String[] numStrings = line.split("\\s+");
        long[] nums = new long[numStrings.length];

        for (int i = 0; i < numStrings.length; i++) {
            nums[i] = Long.parseLong((numStrings[i]));
        }

        return nums;
    }


}
