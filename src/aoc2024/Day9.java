package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day9 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String input = reader.readLine();
            char[] line = input.toCharArray();
            int lastUsedSpace = line.length - 1;
            if (lastUsedSpace % 2 == 1) lastUsedSpace--;

            long result1 = 0;
            for (int i = 1, j = lastUsedSpace, charCounter = Character.getNumericValue(line[0]); i < j;) {
                boolean stop = false;
                while (line[i] == '0') {
                    i++;
                    int value = Character.getNumericValue(line[i]);
                    for (int k = 0; k < value; k++)
                        result1 += (long) charCounter++ * (i / 2);
                    if (++i >= j) {
                        stop = true;
                        break;
                    }
                }
                while (line[j] == '0') {
                    j -= 2;
                    if (i >= j) break;
                }
                if (stop) break;
                line[i] = (char) (Character.getNumericValue(line[i] - 1) + '0');
                line[j] = (char) (Character.getNumericValue(line[j] - 1) + '0');
                result1 += (long) charCounter++ * (j / 2);
            }
            System.out.println(result1);

            long result2 = 0;
            line = input.toCharArray();
            int[] startCounter = new int[line.length];
            for (int i = 0, counter = 0; i < line.length; i++) {
                startCounter[i] = counter;
                counter += Character.getNumericValue(line[i]);
            }
            for (int i = lastUsedSpace; i >= 0; i -= 2) {
                int size = Character.getNumericValue(line[i]);
                boolean foundOtherSpace = false;
                for (int j = 1; j < i; j += 2) {
                    int freeSize = Character.getNumericValue(line[j]);
                    if (freeSize < size) continue;
                    line[i] = '0';
                    line[j] = (char) (freeSize - size + '0');
                    for (int k = 0; k < size; k++) {
                        result2 += (long) (i / 2) * startCounter[j];
                        startCounter[j]++;
                    }
                    foundOtherSpace = true;
                    break;
                }
                if (!foundOtherSpace) {
                    for (int k = 0; k < size; k++) {
                        result2 += (long) (i / 2) * startCounter[i];
                        startCounter[i]++;
                    }
                }
            }
            System.out.println(result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
