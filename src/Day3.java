import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Day3 {
    public static void main(String[] args) {
        try (InputStream in = new FileInputStream("res/input3.txt")) {
            byte[] bytes = in.readAllBytes();
            String[] lines = new String(bytes).split("\n");
            ArrayList<ArrayList<Integer>> gears = new ArrayList<>();
            int sum = 0;
            int sumGear = 0;
            for (int i = 0; i < lines.length; i++) {
                boolean isNumber = false;
                boolean isCounting = false;
                ArrayList<String> foundGears = new ArrayList<>();
                int number = 0;
                for (int j = 0; j < lines[i].length() - 1; j++) {
                    char c = lines[i].charAt(j);
                    if (Character.isDigit(c)) {
                        if (isNumber) {
                            number *= 10;
                        }
                        number += Character.getNumericValue(c);
                        if (!isCounting) {
                            if (isSymbol(lines, i - 1, j - 1)) isCounting = true;
                            if (isSymbol(lines, i + 1, j + 1)) isCounting = true;
                            if (isSymbol(lines, i - 1, j + 1)) isCounting = true;
                            if (isSymbol(lines, i + 1, j - 1)) isCounting = true;
                            if (isSymbol(lines, i + 1, j)) isCounting = true;
                            if (isSymbol(lines, i - 1, j)) isCounting = true;
                            if (isSymbol(lines, i, j + 1)) isCounting = true;
                            if (isSymbol(lines, i, j - 1)) isCounting = true;
                        }
                        if (isGear(lines, i - 1, j - 1))
                            if (!foundGears.contains((i - 1) + " " + (j - 1)))
                                foundGears.add((i - 1) + " " + (j - 1));
                        if (isGear(lines, i + 1, j + 1))
                            if (!foundGears.contains((i + 1) + " " + (j + 1)))
                                foundGears.add((i + 1) + " " + (j + 1));
                        if (isGear(lines, i - 1, j + 1))
                            if (!foundGears.contains((i - 1) + " " + (j + 1)))
                                foundGears.add((i - 1) + " " + (j + 1));
                        if (isGear(lines, i + 1, j - 1))
                            if (!foundGears.contains((i + 1) + " " + (j - 1)))
                                foundGears.add((i + 1) + " " + (j - 1));
                        if (isGear(lines, i + 1, j))
                            if (!foundGears.contains((i + 1) + " " + (j)))
                                foundGears.add((i + 1) + " " + (j));
                        if (isGear(lines, i - 1, j))
                            if (!foundGears.contains((i - 1) + " " + (j)))
                                foundGears.add((i - 1) + " " + (j));
                        if (isGear(lines, i, j + 1))
                            if (!foundGears.contains((i) + " " + (j + 1)))
                                foundGears.add((i) + " " + (j + 1));
                        if (isGear(lines, i, j - 1))
                            if (!foundGears.contains((i) + " " + (j - 1)))
                                foundGears.add((i) + " " + (j - 1));
                        isNumber = true;
                    } else {
                        if (isNumber) {
                            if (isCounting) sum += number;
                            addFoundGears(gears, foundGears, number);
                            foundGears.clear();
                            isNumber = false;
                            isCounting = false;
                            number = 0;
                        }
                    }
                }
                if (isNumber && isCounting) sum += number;
                if (isNumber) {
                    addFoundGears(gears, foundGears, number);
                }
            }
            System.out.println("sum: " + sum);
            for (int i = 0; i < lines.length; i++) {
                for (int j = 0; j < lines[i].length() - 1; j++) {
                    char c = lines[i].charAt(j);
                    if (c == '*') {
                        int amountNum = 0;
                        int num1 = 0;
                        int num2 = 0;
                        for (ArrayList<Integer> gear : gears) {
                            if (gear.get(0) == i && gear.get(1) == j) {
                                amountNum++;
                                if (amountNum == 1) num1 = gear.get(2);
                                else num2 = gear.get(2);
                            }
                        }
                        if (amountNum == 2) {
                            sumGear += num1 * num2;
                        }
                    }
                }
            }
            System.out.println("gear sum: " + sumGear);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addFoundGears(ArrayList<ArrayList<Integer>> gears, ArrayList<String> foundGears, int number) {
        for (String foundGear : foundGears) {
            ArrayList<Integer> gear = new ArrayList<>();
            gear.add(Integer.parseInt(foundGear.split(" ")[0]));
            gear.add(Integer.parseInt(foundGear.split(" ")[1]));
            gear.add(number);
            gears.add(gear);
        }
    }

    public static boolean isSymbol(String[] lines, int i, int j) {
        if (i >= 0 && i < lines.length && j >= 0 && j < lines[i].length() - 1) {
            char c = lines[i].charAt(j);
            return !Character.isDigit(c) && c != '.';
        }
        return false;
    }

    public static boolean isGear(String[] lines, int i, int j) {
        if (i >= 0 && i < lines.length && j >= 0 && j < lines[i].length() - 1) {
            char c = lines[i].charAt(j);
            return c == '*';
        }
        return false;
    }
}