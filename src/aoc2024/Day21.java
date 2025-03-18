package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day21 {
    static int[] positions = {0, 1, 1, 0, 1, 1, 1, 2, 0, 2};
    static Map<String, Long> memo = new HashMap<>();

    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            long result1 = 0, result2 = 0;
            int[] positions = {3, 1, 2, 0, 2, 1, 2, 2, 1, 0, 1, 1, 1, 2, 0, 0, 0, 1, 0, 2, 3, 2};
            while ((line = reader.readLine()) != null) {
                int x = 3, y = 2;
                long r1 = 0, r2 = 0;
                for (char c : line.toCharArray()) {
                    StringBuilder sb1 = new StringBuilder(), sb2 = new StringBuilder();
                    int i = c == 'A' ? 10 : Character.getNumericValue(c);
                    String h = (y - positions[i * 2 + 1] > 0 ? "<" : ">").repeat(Math.abs(y - positions[i * 2 + 1]));
                    String v = (x - positions[i * 2] > 0 ? "^" : "v").repeat(Math.abs(x - positions[i * 2]));
                    if (positions[i * 2 + 1] >= 1 || x <= 2 || y - positions[i * 2 + 1] <= 0) sb1.append(h).append(v).append("A");
                    if (positions[i * 2] <= 2     || y >= 1 || x - positions[i * 2] >= 0) sb2.append(v).append(h).append("A");
                    x = positions[i * 2];
                    y = positions[i * 2 + 1];
                    long f = Long.MAX_VALUE, f2 = Long.MAX_VALUE;
                    if (!sb1.isEmpty()) {
                        f = shortestSequence(String.valueOf(sb1), 2);
                        f2 = shortestSequence(String.valueOf(sb1), 25);
                    }
                    if (!sb2.isEmpty()) {
                        f = Math.min(f, shortestSequence(String.valueOf(sb2), 2));
                        f2 = Math.min(f2, shortestSequence(String.valueOf(sb2), 25));
                    }
                    r1 += f;
                    r2 += f2;
                }
                int numericPart = Integer.parseInt(line.substring(0, line.length() - 1));
                result1 += r1 * numericPart;
                result2 += r2 * numericPart;
            }
            System.out.println(result1 + "\n" + result2);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long shortestSequence (String sequence, int remainingSteps) {
        if (remainingSteps <= 0) return sequence.length();
        if (memo.containsKey(sequence + remainingSteps)) return memo.get(sequence + remainingSteps);
        int x = 0, y = 2, last = -1;
        StringBuilder sb1 = new StringBuilder(), sb2 = new StringBuilder();
        long count = 0;
        for (char c : sequence.toCharArray()) {
            int i = switch (c) {
                case '^': yield 0;
                case '<': yield 1;
                case 'v': yield 2;
                case '>': yield 3;
                case 'A': yield 4;
                default: yield 5;
            };
            if (last != i) {
                if (last != -1) {
                    count += shortestSequenceHelper(sb1, sb2, remainingSteps - 1);
                    sb1 = new StringBuilder();
                    sb2 = new StringBuilder();
                }
                String h = (y - positions[i * 2 + 1] > 0 ? "<" : ">").repeat(Math.abs(y - positions[i * 2 + 1]));
                String v = (x - positions[i * 2] > 0 ? "^" : "v").repeat(Math.abs(x - positions[i * 2]));
                if (positions[i * 2 + 1] > 0 || x > 0 || y - positions[i * 2 + 1] <= 0) sb1.append(h).append(v).append("A");
                if (positions[i * 2] > 0     || y > 0 || x - positions[i * 2] <= 0) sb2.append(v).append(h).append("A");
                x = positions[i * 2];
                y = positions[i * 2 + 1];
                last = i;
            } else {
                if (!sb1.isEmpty()) sb1.append("A");
                if (!sb2.isEmpty()) sb2.append("A");
            }
        }
        count += shortestSequenceHelper(sb1, sb2, remainingSteps - 1);
        memo.put(sequence + remainingSteps, count);
        return count;
    }

    private static long shortestSequenceHelper(StringBuilder sb1, StringBuilder sb2, int r) {
        if (sb1.isEmpty()) return shortestSequence(String.valueOf(sb2), r);
        if (sb2.isEmpty()) return shortestSequence(String.valueOf(sb1), r);
        return Math.min(shortestSequence(String.valueOf(sb1), r), shortestSequence(String.valueOf(sb2), r));
    }
}
