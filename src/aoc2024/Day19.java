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
            int result1 = 0;
            for (int i = 1; (line = reader.readLine()) != null; i++) {
                if (i == 1) towels.addAll(Arrays.asList(line.split(", ")));
                else if (i == 2) continue;
                else {
                    boolean possible = tryDesired(line, 0, line.length() - 1, towels);
                    System.out.println(line + " " + possible);
                    if (possible)
                        result1++;
                }
            }
            System.out.println(result1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean tryDesired(String s, int start, int end, Set<String> towels) {
        //System.out.println(s + " " + start + " " + end);
        String substring = s.substring(start, end + 1);
        if (towels.contains(substring))
            return true;
        for (int i = 0; i < end - start; i++) {
            if (tryDesired(s, start, start + i, towels) && tryDesired(s, start + i + 1, end, towels)) {
                towels.add(substring);
                return true;
            }
        }
        return false;
    }
}
