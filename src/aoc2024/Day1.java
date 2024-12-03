package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                list1.add(Integer.valueOf(line.split("\\s+")[0]));
                list2.add(Integer.valueOf(line.split("\\s+")[1]));
            }
            Collections.sort(list1);
            Collections.sort(list2);
            int result1 = 0;
            for (int i = 0; i < list1.size() && i < list2.size(); i++)
                result1 += Math.abs(list1.get(i) - list2.get(i));
            System.out.println(result1);

            int result2 = 0;
            for (int i : list1)
                for (int j : list2)
                    if (i == j)
                        result2 += i;
            System.out.println(result2);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
