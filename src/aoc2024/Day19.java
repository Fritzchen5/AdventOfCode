package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day19 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Set<String> towels = new HashSet<>();
            long result1 = 0, result2 = 0;
            for (int i = 1; (line = reader.readLine()) != null; i++) {
                if (i == 1 || i == 2)
                    towels.addAll(Arrays.asList(line.split(", ")));
                else {
                    long[] possible = new long[line.length()];
                    for (int j = 0; j < line.length(); j++)
                        for (int k = 0; k <= j; k++)
                            if ((k == 0 || possible[k - 1] > 0) && towels.contains(line.substring(k, j + 1)))
                                possible[j] += (k == 0) ? 1 : possible[k - 1];
                    if (possible[line.length() - 1] > 0) result1++;
                    result2 += possible[line.length() - 1];
                }
            }
            System.out.println(result1 + "\n" + result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
