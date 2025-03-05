package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day12 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<List<Character>> farm = new ArrayList<>();
            while ((line = reader.readLine()) != null)
                farm.add(line.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));

            int result1 = 0, result2 = 0;
            List<Integer> visitedX = new ArrayList<>(), visitedY = new ArrayList<>();
            for (int i = 0; i < farm.size(); i++) {
                for (int j = 0; j < farm.get(i).size(); j++) {
                    boolean found = false;
                    for (int k = 0; k < visitedX.size(); k++)
                        if (visitedX.get(k) == i && visitedY.get(k) == j) {
                            found = true;
                            break;
                        }
                    if (found) continue;
                    List<Integer> vX = new ArrayList<>(), vY = new ArrayList<>();
                    List<Integer> sideDirection = new ArrayList<>();
                    List<Integer> side1 = new ArrayList<>(), side2 = new ArrayList<>();
                    int perimeter = calculatePerimeter(farm, i, j, vX, vY, sideDirection, side1, side2);
                    result1 += perimeter * vX.size();
                    int side = 0;
                    while (!sideDirection.isEmpty()) {
                        side++;
                        int d = sideDirection.remove(0);
                        int s1 = side1.remove(0), s2 = side2.remove(0);
                        removeNeighbors(d, s1, s2, sideDirection, side1, side2, -1);
                        removeNeighbors(d, s1, s2, sideDirection, side1, side2, 1);
                    }
                    result2 += side * vX.size();
                    visitedX.addAll(vX);
                    visitedY.addAll(vY);
                }
            }
            System.out.println(result1 + "\n" + result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int calculatePerimeter(List<List<Character>> farm, int x, int y, List<Integer> visitedX, List<Integer> visitedY , List<Integer> sideDirection, List<Integer> side1, List<Integer> side2) {
        visitedX.add(x);
        visitedY.add(y);
        char c = farm.get(x).get(y);
        int perimeter = 0;
        perimeter += calculatePerimeterHelper(farm, x + 1, y, c, visitedX, visitedY, 0, sideDirection, side1, side2);
        perimeter += calculatePerimeterHelper(farm, x - 1, y, c, visitedX, visitedY, 2, sideDirection, side1, side2);
        perimeter += calculatePerimeterHelper(farm, x, y + 1, c, visitedX, visitedY, 1, sideDirection, side1, side2);
        perimeter += calculatePerimeterHelper(farm, x, y - 1, c, visitedX, visitedY, 3, sideDirection, side1, side2);
        return perimeter;
    }

    private static int calculatePerimeterHelper(List<List<Character>> farm, int x, int y, char c, List<Integer> visitedX, List<Integer> visitedY, int direction, List<Integer> sideDirection, List<Integer> side1, List<Integer> side2) {
        if (x < 0 || y < 0 || x >= farm.size() || y >= farm.get(x).size() || farm.get(x).get(y) != c) {
            sideDirection.add(direction);
            side2.add(direction % 2 == 0 ? y : x);
            if (direction == 0) side1.add(x);
            else if (direction == 1) side1.add(y);
            else if (direction == 2) side1.add(x + 1);
            else side1.add(y + 1);
            return 1;
        }
        for (int i = 0; i < visitedX.size(); i++)
            if (visitedX.get(i) == x && visitedY.get(i) == y)
                return 0;
        return calculatePerimeter(farm, x, y, visitedX, visitedY, sideDirection, side1, side2);
    }

    private static void removeNeighbors(int d, Integer s1, Integer s2, List<Integer> sideDirection, List<Integer> side1, List<Integer> side2, int direction) {
        for (int i = 0; i < sideDirection.size(); i++) {
            if (d != sideDirection.get(i) || !Objects.equals(s1, side1.get(i)) || s2 + direction != side2.get(i)) continue;
            s2 += direction;
            sideDirection.remove(i);
            side1.remove(i);
            side2.remove(i);
            removeNeighbors(d, s1, s2, sideDirection, side1, side2, direction);
            return;
        }
    }
}
