package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int result1 = 0;
            int result2 = 0;
            while ((line = reader.readLine()) != null) {
                String[] numbersString = line.split("\\s+");
                List<Integer> numbers = new ArrayList<Integer>();
                for (String s : numbersString)
                    numbers.add(Integer.parseInt(s));

                if (isSave(numbers))
                    result1++;

                for (int i = 0; i < numbers.size(); i++) {
                    List<Integer> copy = new ArrayList<>(numbers);
                    copy.remove(i);
                    if (isSave(copy)) {
                        result2++;
                        break;
                    }
                }
            }

            System.out.println(result1);
            System.out.println(result2);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isSave(List<Integer> numbers) {
        boolean increasing = true;
        int last = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            int difference = numbers.get(i) - last;
            int abs = Math.abs(difference);

            if (i == 1)
                increasing = difference > 0;
            else
            if (increasing != difference > 0)
                return false;

            if (abs <= 0 || abs > 3)
                return false;

            last = numbers.get(i);

            if (i + 1 == numbers.size())
                return true;
        }
        return false;
    }
}
