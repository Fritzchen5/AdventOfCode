package aoc2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day6 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("res/aoc2023/input6.txt"))) {
            int[] time1 = new int[0];
            int[] distance1 = new int[0];
            long time2 = 0;
            long distance2 = 0;
            String readerLine;
            while ((readerLine = reader.readLine()) != null) {
                if (readerLine.contains("Time")) time1 = Arrays.stream(readerLine.replaceAll("Time: +", "").split(" +")).mapToInt(Integer::parseInt).toArray();
                else if (readerLine.contains("Distance")) distance1 = Arrays.stream(readerLine.replaceAll("Distance: +", "").split(" +")).mapToInt(Integer::parseInt).toArray();
                if (readerLine.contains("Time")) time2 = Long.parseLong(readerLine.replaceAll("Time: +", "").replaceAll(" ", ""));
                else if (readerLine.contains("Distance")) distance2 = Long.parseLong(readerLine.replaceAll("Distance: +", "").replaceAll(" ", ""));
            }
            int part1 = 1;
            for (int i = 0; i < time1.length; i++) {
                int winningAmount = 0;
                for (int j = 0; j <= time1[i]; j++) {
                    if (j * (time1[i] - j) > distance1[i]) winningAmount++;
                }
                part1 *= winningAmount;
            }
            System.out.println("part 1: " + part1);
            long part2 = 0;
            for (long j = 0; j <= time2; j++) {
                if (j * (time2 - j) > distance2) part2++;
            }
            System.out.println("part 2: " + part2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}