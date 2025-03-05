package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<List<Integer>> map = reader.lines()
                    .map(line -> line.chars()
                            .mapToObj(c -> Character.getNumericValue((char) c))
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
            int result1 = 0, result2 = 0;
            for (int i = 0; i < map.size(); i++) {
                for (int j = 0; j < map.get(i).size(); j++) {
                    if (map.get(i).get(j) != 0) continue;
                    List<Integer> foundX = new ArrayList<>();
                    List<Integer> foundY = new ArrayList<>();
                    result2 += tryHike(map, i, j, foundX, foundY);
                    result1 += foundX.size();
                }
            }
            System.out.println(result1 + "\n" + result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int tryHike(List<List<Integer>> map, int x, int y, List<Integer> foundX, List<Integer> foundY) {
        int value = map.get(x).get(y), rating = 0;
        if (value == 9) {
            boolean found = false;
            for (int i = 0; i < foundX.size(); i++) {
                if (x == foundX.get(i) && y == foundY.get(i)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                foundX.add(x);
                foundY.add(y);
            }
            return 1;
        }
        if (x > 0 && map.get(x - 1).get(y) - 1 == value) rating += tryHike(map, x - 1, y, foundX, foundY);
        if (y > 0 && map.get(x).get(y - 1) - 1 == value) rating += tryHike(map, x, y - 1, foundX, foundY);
        if (x + 1 < map.size() && map.get(x + 1).get(y) - 1 == value) rating += tryHike(map, x + 1, y, foundX, foundY);
        if (y + 1 < map.get(x).size() && map.get(x).get(y + 1) - 1 == value) rating += tryHike(map, x, y + 1, foundX, foundY);
        return rating;
    }
}
