package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day8 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<Integer> x = new ArrayList<>(), y = new ArrayList<>();
            List<Character> f = new ArrayList<>();
            int lengthX = 0, lengthY = 0;
            for (int i = 0; (line = reader.readLine()) != null; i++) {
                lengthX = i + 1;
                lengthY = line.length();
                for (int j = 0; j < line.length(); j++)
                    if (line.charAt(j) != '.') {
                        x.add(i);
                        y.add(j);
                        f.add(line.charAt(j));
                    }
            }
            List<Integer> foundX1 = new ArrayList<>(), foundY1 = new ArrayList<>();
            List<Integer> foundX2 = new ArrayList<>(), foundY2 = new ArrayList<>();
            for (int i = 0; i < f.size(); i++) {
                int x1 = x.get(i), y1 = y.get(i);
                char f1 = f.get(i);
                for (int j = i + 1; j < f.size(); j++) {
                    if (f1 != f.get(j)) continue;
                    int x2 = x.get(j), y2 = y.get(j);
                    int dx = x2 - x1, dy = y2 - y1;
                    addNewAntinode(x1, y1, lengthX, lengthY, foundX2, foundY2);
                    addNewAntinode(x2, y2, lengthX, lengthY, foundX2, foundY2);
                    int x3 = x1 - dx, y3 = y1 - dy, x4 = x2 + dx, y4 = y2 + dy;
                    addNewAntinode(x3, y3, lengthX, lengthY, foundX1, foundY1);
                    addNewAntinode(x4, y4, lengthX, lengthY, foundX1, foundY1);
                    while (addNewAntinode(x3, y3, lengthX, lengthY, foundX2, foundY2)) {
                        x3 -= dx;
                        y3 -= dy;
                    }
                    while (addNewAntinode(x4, y4, lengthX, lengthY, foundX2, foundY2)) {
                        x4 += dx;
                        y4 += dy;
                    }
                }
            }
            int result1 = foundX1.size(), result2 = foundX2.size();
            System.out.println(result1 + "\n" + result2);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addNewAntinode(int x, int y, int lengthX, int lengthY, List<Integer> foundX, List<Integer> foundY) {
        if (x < 0 || y < 0 || x >= lengthX || y >= lengthY) return false;
        for (int i = 0; i < foundX.size(); i++)
            if (foundX.get(i) == x && foundY.get(i) == y)
                return true;
        foundX.add(x);
        foundY.add(y);
        return true;
    }
}
