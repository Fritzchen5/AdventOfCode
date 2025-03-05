package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day3 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int result1 = 0;
            int result2 = 0;
            boolean enabled = true;
            while ((line = reader.readLine()) != null) {
                String[] mulParts = line.split("mul\\(");
                String lastPart = "";
                for (String s : mulParts) {
                    int lastEnable = lastPart.lastIndexOf("do()");
                    int lastDisable = lastPart.lastIndexOf("don't()");
                    if (lastEnable != -1) enabled = true;
                    if (lastDisable > lastEnable) enabled = false;
                    lastPart = s;
                    String[] endOfMul = s.split("\\)");
                    if (endOfMul.length <= 1 && !s.endsWith(")")) continue;
                    String[] importantParts = endOfMul[0].split(",");
                    if (importantParts.length != 2) continue;
                    if (!importantParts[0].chars().allMatch( Character::isDigit)) continue;
                    if (!importantParts[1].chars().allMatch( Character::isDigit)) continue;
                    int i1 = Integer.parseInt(importantParts[0]);
                    int i2 = Integer.parseInt(importantParts[1]);
                    int product = i1 * i2;
                    result1 += product;
                    if (enabled) result2 += product;
                }
            }
            System.out.println(result1);
            System.out.println(result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
