package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day4 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<List<Character>> chars = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                List<Character> list = new ArrayList<>();
                for (char c : line.toCharArray())
                    list.add(c);
                chars.add(list);
            }
            int result1 = 0;
            String key = "XMAS";
            for (int i = 0; i < chars.size(); i++)
                for (int j = 0; j < chars.get(0).size(); j++)
                    if (chars.get(i).get(j) == key.charAt(0))
                        for (int k = 0; k < 8; k++)
                            result1 += checkChar(chars, i, j, k, key);
            int result2 = 0;
            for (int i = 0; i < chars.size(); i++) {
                for (int j = 0; j < chars.get(0).size(); j++) {
                    if (chars.get(i).get(j) == 'A') {
                        int foundM = 0;
                        int foundS = 0;
                        for (int k = 0; k < 4; k++) {
                            char found = checkChar2(chars, i, j, k);
                            if (found == 'M') foundM++;
                            if (found == 'S') foundS++;
                            if (Math.abs(foundM - foundS) == 2) {
                                foundM = 0;
                                break;
                            }
                        }
                        if (foundM == 2 && foundS == 2) result2++;
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

    static int checkChar (List<List<Character>> chars, int i, int j, int k, String key) {
        for (int found = 1; found < key.length(); found++) {
            switch (k) {
                case 0 -> i++;
                case 1 -> i--;
                case 2 -> j++;
                case 3 -> j--;
                case 4 -> {
                    i++;
                    j++;
                }
                case 5 -> {
                    i++;
                    j--;
                }
                case 6 -> {
                    i--;
                    j++;
                }
                case 7 -> {
                    i--;
                    j--;
                }
            }
            if (!isInBounds(chars, i, j)) break;
            if (chars.get(i).get(j) != key.charAt(found)) break;
            if (found + 1 >= key.length()) return 1;
        }
        return 0;
    }

    static char checkChar2 (List<List<Character>> chars, int i, int j, int k) {
        switch (k) {
            case 0 -> {
                i++;
                j++;
            }
            case 1 -> {
                i--;
                j--;
            }
            case 2 -> {
                i--;
                j++;
            }
            case 3 -> {
                i++;
                j--;
            }
        }
        if (!isInBounds(chars, i, j)) return ' ';
        return chars.get(i).get(j);
    }

    static boolean isInBounds(List<List<Character>> chars, int x, int y) {
        try {
            chars.get(x).get(y);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
