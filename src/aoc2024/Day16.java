package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day16 {
    public static void main(String[] args) {
        boolean print = false;
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<int[]> lab = new ArrayList<>();
            int x = -1, y = -1, eX = -1, eY = -1;
            for (int i = 0; (line = reader.readLine()) != null; i++) {
                lab.add(new int[line.length()]);
                for (int j = 0; j < line.toCharArray().length; j++) {
                    char c = line.charAt(j);
                    if (c == '#') lab.get(i)[j] = -2;
                    else if (c == '.') lab.get(i)[j] = -1;
                    else if (c == 'S') {
                        x = i;
                        y = j;
                        lab.get(i)[j] = 0;
                    } else if (c == 'E') {
                        eX = i;
                        eY = j;
                        lab.get(i)[j] = Integer.MAX_VALUE;
                    }
                }
            }
            List<Integer> wayX = new ArrayList<>(), wayY = new ArrayList<>(), wayD = new ArrayList<>();
            List<Integer> distances = new ArrayList<>();
            distances.add(solveLab(lab, x, y + 1, 1, 1, wayX, wayY, wayD));
            distances.add(solveLab(lab, x + 1, y, 2, 1001, wayX, wayY, wayD));
            distances.add(solveLab(lab, x - 1, y, 0, 1001, wayX, wayY, wayD));
            distances.add(solveLab(lab, x, y - 1, 3, 2001, wayX, wayY, wayD));

            if (print) {
                int biggestX = 0, biggestY = 0;
                for (int i = 0; i < wayX.size(); i++) {
                    if (wayX.get(i) >= biggestX) biggestX = wayX.get(i) + 1;
                    if (wayY.get(i) >= biggestY) biggestY = wayY.get(i) + 1;
                }
                char[][] visualize = new char[biggestX][biggestY];
                visualize[x][y] = 'O';
                visualize[eX][eY] = 'O';
                for (int i = 0; i < wayX.size(); i++)
                    if (wayD.get(i) == getSmallest(distances)) visualize[wayX.get(i)][wayY.get(i)] = 'O';
                for (char[] s : visualize) {
                    StringBuilder b = new StringBuilder();
                    for (char c : s) {
                        if (c == 'O') b.append(c);
                        else b.append(' ');
                    }
                    System.out.println(b);
                }
            }

            int result1 = getSmallest(distances);
            System.out.println(result1);
            int result2 = result1 != -1 ? 2 : 0;
            for (int d : wayD)
                if (d == result1)
                    result2++;
            System.out.println(result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int solveLab(List<int[]> lab, int x, int y, int d, int costs,
                                List<Integer> wayX, List<Integer> wayY, List<Integer> wayD) {
        int pos = lab.get(x)[y];
        if (pos == Integer.MAX_VALUE) return costs;
        d = d % 4;
        if (d < 0) d += 4;
        if (pos == -2 || (pos != -1 && pos + 1000 < costs)) return - 1;
        lab.get(x)[y] = costs;
        List<Integer> dis = new ArrayList<>();
        dis.add(solveLab(lab, getX(x, d), getY(y, d), d, costs+1, wayX, wayY, wayD));
        dis.add(solveLab(lab, getX(x, d+1), getY(y, d+1), d+1, costs+1001, wayX, wayY, wayD));
        dis.add(solveLab(lab, getX(x, d-1), getY(y, d-1), d-1, costs+1001, wayX, wayY, wayD));
        int distance = getSmallest(dis);
        if (distance != -1)
            addCoordinate(x, y, distance, wayX, wayY, wayD);
        return distance;
    }

    private static int getX(int x, int direction) {
        direction = direction % 4;
        if (direction < 0) direction += 4;
        if (direction == 0) return x - 1;
        else if (direction == 2) return x + 1;
        else return x;
    }

    private static int getY(int y, int direction) {
        direction = direction % 4;
        if (direction < 0) direction += 4;
        if (direction == 1) return y + 1;
        else if (direction == 3) return y - 1;
        else return y;
    }

    private static int getSmallest(List<Integer> numbers) {
        int result = -1;
        for (int n : numbers) {
            if (result == -1) result = n;
            else if (n != -1) result = Math.min(result, n);
        }
        return result;
    }

    private static void addCoordinate(int x, int y, int d, List<Integer> wayX, List<Integer> wayY, List<Integer> wayD) {
        for (int i = 0; i < wayX.size(); i++) {
            if (x == wayX.get(i) && y == wayY.get(i)) {
                if (d < wayD.get(i))
                    wayD.set(i, d);
                return;
            }
        }
        wayX.add(x);
        wayY.add(y);
        wayD.add(d);
    }
}
