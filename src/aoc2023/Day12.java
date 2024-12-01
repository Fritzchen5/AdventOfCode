package aoc2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day12 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("res/aoc2023/input12.txt"))) {
            String lineString;
            int part1 = 0;
            while ((lineString = reader.readLine()) != null) {
                String line = lineString.split(" ")[0];
                //line = line + "?" + line + "?" + line + "?" + line + "?" + line;
                ArrayList<Integer> number = new ArrayList<>();
                for (int i = 0; i < 1; i++) {
                    for (String s : lineString.split(" ")[1].split(",")) {
                        number.add(Integer.parseInt(s));
                    }
                }

                int amount = 0;
                for (char c : line.toCharArray()) {
                    if (c == '?') amount++;
                }
                StringBuilder format = new StringBuilder();
                format.append(".".repeat(amount));
                int sum = checkCorrect(replaceQuestionMarks(line, String.valueOf(format)), number);
                for (int i = 0; i < Math.pow(2, amount) - 1; i++) {
                    int index = -1;
                    do {
                        index++;
                        format.replace(index, index + 1, format.charAt(index) == '.' ? "#" : ".");
                    } while (format.charAt(index) == '.');
                    sum += checkCorrect(replaceQuestionMarks(line, String.valueOf(format)), number);
                }
                System.out.println("Sum: " + sum);
                part1 += sum;
            }
            System.out.println(part1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int checkCorrect(String line, ArrayList<Integer> numbers) {
        //System.out.println(line + "   " + numbers);
        int count = 0;
        int index = 0;
        for (char c : line.toCharArray()) {
            if (c == '#') count++;
            else if (count > 0) {
                if (index >= numbers.size()) return 0;
                if (count == numbers.get(index)) {
                    index++;
                    count = 0;
                } else {
                    return 0;
                }
            }
        }
        if (count > 0) {
            if (index >= numbers.size()) return 0;
            if (count == numbers.get(index)) {
                index++;
            } else {
                return 0;
            }
        }
        if (index == numbers.size()) return 1;
        return 0;
    }

    public static String replaceQuestionMarks(String line, String format) {
        char[] c = line.toCharArray();
        int index = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '?') {
                c[i] = format.charAt(index);
                index++;
            }
        }
        return String.valueOf(c);
    }
}