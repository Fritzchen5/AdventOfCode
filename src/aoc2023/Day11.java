package aoc2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day11 {
    public static void main(String[] args) {
        int adding = 100000 - 1;
        try (BufferedReader reader = new BufferedReader(new FileReader("res/aoc2023/input11.txt"))) {
            String lineString;
            ArrayList<String> lines = new ArrayList<>();
            int row = 0;
            ArrayList<ArrayList<Long>> points = new ArrayList<>();
            while((lineString = reader.readLine()) != null) {
                lines.add(lineString);
                for (int i = 0; i < lineString.length(); i++) {
                    if (lineString.charAt(i) == '#') {
                        ArrayList<Long> point = new ArrayList<>();
                        point.add((long) row);
                        point.add((long) i);
                        points.add(point);
                    }
                }
                row++;
            }
            for (int i = lines.size() - 1; i >= 0; i--) {
                if (!lines.get(i).contains("#")) {
                    for (ArrayList<Long> point : points) {
                        if (point.get(0) > i) point.set(0, point.get(0) + adding);
                    }
                }
            }
            for (int i = lines.get(0).length() - 1; i >= 0; i--) {
                boolean found = false;
                for (String line : lines) {
                    if (line.charAt(i) == '#') {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    for (ArrayList<Long> point : points) {
                        if (point.get(1) > i) point.set(1, point.get(1) + adding);
                    }
                }
            }
            long sum = 0;
            for (int i = 0; i < points.size(); i++) {
                for (int j = i; j < points.size(); j++) {
                    sum += Math.abs(points.get(i).get(0) - points.get(j).get(0)) + Math.abs(points.get(i).get(1) - points.get(j).get(1));
                }
            }
            System.out.println(sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}