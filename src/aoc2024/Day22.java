package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day22 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            long result1 = 0, result2 = 0;
            Map<String, Integer> m = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                long l = Long.parseLong(line);
                Set<String> s = new HashSet<>();
                List<Integer> differences = new ArrayList<>();
                int lastPrice = (int) (l % 10);
                for (int i = 0; i < 2000; i++) {
                    l = mixAndPrune(l, l * 64);
                    l = mixAndPrune(l, (long) l / 32);
                    l = mixAndPrune(l, l * 2048);
                    int price = (int) (l % 10);
                    differences.add(price - lastPrice);
                    lastPrice = price;
                    if (differences.size() > 4) {
                        differences.remove(0);
                        String priceVariation = differences.toString();
                        if (s.contains(priceVariation)) continue;
                        s.add(priceVariation);
                        m.put(priceVariation, m.getOrDefault(priceVariation, 0) + price);
                    }
                }
                result1 += l;
            }

            for (Integer i : m.values())
                if (i > result2)
                    result2 = i;

            System.out.println(result1 + "\n" + result2);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long mixAndPrune(long secret, long l) {
        return (secret ^ l) % 16777216L;
    }
}