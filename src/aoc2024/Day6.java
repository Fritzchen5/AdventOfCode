package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<char[]> map = new ArrayList<>();
            int startX = 0, startY = 0, row = 0, direction = 0, x, y;
            while ((line = reader.readLine()) != null) {
                map.add(line.toCharArray());
                if (line.contains("^")) {
                    startX = row;
                    startY = line.indexOf("^");
                    map.get(startX)[startY] = 'X';
                }
                row++;
            }
            x = startX;
            y = startY;

            int result1 = 1;
            List<Integer> replaceX = new ArrayList<>();
            List<Integer> replaceY = new ArrayList<>();
            while (true) {
                int newX = x, newY = y;
                if (direction == 0) newX--;
                else if (direction == 1) newY++;
                else if (direction == 2) newX++;
                else newY--;
                char c = getChar(map, newX, newY);
                if (c == '#') direction = direction == 3 ? 0 : direction + 1;
                else if (c == 'o') break;
                else {
                    x = newX;
                    y = newY;
                    if (c != 'X') {
                        result1++;
                        replaceX.add(x);
                        replaceY.add(y);
                    }
                    map.get(x)[y] = 'X';
                }
            }
            System.out.println(result1);

            int result2 = 0;
            for (int i = 0; i < replaceX.size(); i++) {
                map.get(replaceX.get(i))[replaceY.get(i)] = '#';
                result2 += isLoop(map, startX, startY);
                map.get(replaceX.get(i))[replaceY.get(i)] = '.';
                for (char[] chars : map)
                    for (int k = 0; k < chars.length; k++)
                        if (Character.isDigit(chars[k])) chars[k] = '.';
            }
            System.out.println(result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static char getChar(List<char[]> map, int x, int y) {
        if (x < 0 || x >= map.size() || y < 0 || y >= map.get(x).length) return 'o';
        return map.get(x)[y];
    }

    public static int isLoop(List<char[]> map, int x, int y) {
        int direction = 0;
        map.get(x)[y] = '.';
        while (true) {
            int newX = x, newY = y;
            if (direction == 0) newX--;
            else if (direction == 1) newY++;
            else if (direction == 2) newX++;
            else newY--;
            char c = getChar(map, newX, newY);
            if (c == '#') direction = direction == 3 ? 0 : direction + 1;
            else if (c == 'o') return 0;
            else {
                if (c == '.' || c == 'X') map.get(x)[y] = '1';
                else {
                    int i = Character.getNumericValue(c);
                    if (++i >= 5) return 1;
                    else map.get(x)[y] = (char) (i + 48);
                }
                x = newX;
                y = newY;
                map.get(x)[y] = 'X';
            }
        }
    }
}
