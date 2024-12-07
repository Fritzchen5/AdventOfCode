package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day7 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            long result1 = 0, result2 = 0;
            while ((line = reader.readLine()) != null) {
                List<Long> numbers = Stream.of(line.split(":?\\s")).map(Long::parseLong).toList();
                result1 += tryLine(numbers, true);
                result2 += tryLine(numbers, false);
            }
            System.out.println(result1 + "\n" + result2);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long tryLine(List<Long> numbers, boolean partOne) {
        if (numbers.size() == 2) return Objects.equals(numbers.get(0), numbers.get(1)) ? numbers.get(0) : 0;
        List<Long> n = new ArrayList<>(numbers);
        long n1 = n.get(1), n2 = n.get(2);
        n.set(1, n1 + n2);
        n.remove(2);
        long result = tryLine(n, partOne);
        if (result != 0) return result;
        n.set(1, n1 * n2);
        result = tryLine(n, partOne);
        if (partOne || result != 0) return result;
        n.set(1, Long.valueOf(n1 + "" + n2));
        return tryLine(n, false);
    }
}
