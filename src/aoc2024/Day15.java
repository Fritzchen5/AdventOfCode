package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day15 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<char[]> warehouse1 = new ArrayList<>();
            List<char[]> warehouse2 = new ArrayList<>();
            boolean readWarehouse = true;
            int x1 = -1, y1 = -1, x2 = -1, y2 = -1, xCount = 0;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    readWarehouse = false;
                    continue;
                }
                if (readWarehouse) {
                    warehouse1.add(line.toCharArray());
                    if (line.contains("@")) {
                        x1 = xCount;
                        y1 = line.indexOf("@");
                    }
                    warehouse2.add(new char[line.length() * 2]);
                    int yCount = 0;
                    for (char c : line.toCharArray()) {
                        if (c == '#' || c == '.') {
                            warehouse2.get(xCount)[yCount++] = c;
                            warehouse2.get(xCount)[yCount++] = c;
                        } else if (c == '@'){
                            x2 = xCount;
                            y2 = yCount;
                            warehouse2.get(xCount)[yCount++] = '@';
                            warehouse2.get(xCount)[yCount++] = '.';
                        } else {
                            warehouse2.get(xCount)[yCount++] = '[';
                            warehouse2.get(xCount)[yCount++] = ']';
                        }
                    }
                    xCount++;
                } else {
                    for (char direction : line.toCharArray()) {
                        if (move(warehouse1, x1, y1, direction)) {
                            int newX = getX(x1, direction), newY = getY(y1, direction);
                            warehouse1.get(newX)[newY] = warehouse1.get(x1)[y1];
                            warehouse1.get(x1)[y1] = '.';
                            x1 = newX;
                            y1 = newY;
                        }
                        boolean movePossible;
                        List<Integer> moveX = new ArrayList<>(), moveY = new ArrayList<>();
                        List<Character> moveC = new ArrayList<>();
                        if (direction == '<' || direction == '>') movePossible = move(warehouse2, x2, y2, direction);
                        else movePossible = move2(warehouse2, x2, y2, direction, moveX, moveY, moveC);
                        if (movePossible) {
                            int j = direction == '^' ? -1 : 1;
                            for (int i = 0; i < moveX.size(); i++) {
                                warehouse2.get(moveX.get(i))[moveY.get(i)] = '.';
                                warehouse2.get(moveX.get(i) + j)[moveY.get(i)] = moveC.get(i);
                            }
                            int newX = getX(x2, direction), newY = getY(y2, direction);
                            warehouse2.get(newX)[newY] = '@';
                            warehouse2.get(x2)[y2] = '.';
                            x2 = newX;
                            y2 = newY;
                        }
                        //for (char[] c : warehouse2) System.out.println(Arrays.toString(c));
                    }
                }
            }

            int result1 = 0, result2 = 0;
            for (int i = 0; i < warehouse1.size(); i++)
                for (int j = 0; j < warehouse1.get(i).length; j++)
                    if (warehouse1.get(i)[j] == 'O')
                        result1 += 100 * i + j;
            for (int i = 0; i < warehouse2.size(); i++)
                for (int j = 0; j < warehouse2.get(i).length; j++)
                    if (warehouse2.get(i)[j] == '[')
                        result2 += 100 * i + j;
            System.out.println(result1 + "\n" + result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean move(List<char[]> warehouse, int x, int y, char direction) {
        int newX = getX(x, direction), newY = getY(y, direction);
        char c = warehouse.get(newX)[newY];
        if (c == '#' || (warehouse.get(newX)[newY] != '.' && !move(warehouse, newX, newY, direction))) return false;
        warehouse.get(newX)[newY] = warehouse.get(x)[y];
        return true;
    }

    private static boolean move2(List<char[]> warehouse, int x, int y, char d, List<Integer> mX, List<Integer> mY, List<Character> mC) {
        int newX = getX(x, d);
        for (int i = 0; i < mX.size(); i++)
            if (newX == mX.get(i) && y == mY.get(i)) return true;
        char c = warehouse.get(newX)[y];
        if (c == '#') return false;
        if (c == '.') return true;
        if (!(move2(warehouse, newX, y, d, mX, mY, mC) &&
                move2(warehouse, newX, y + (c == '[' ? 1 : -1), d, mX, mY, mC))) return false;
        mX.add(newX);
        mX.add(newX);
        mY.add(y);
        mY.add(y + (c == '[' ? 1 : -1));
        mC.add(c);
        mC.add(c == '[' ? ']' : '[');
        return true;
    }

    private static int getX(int x, char direction) {
        if (direction == '^') return x - 1;
        else if (direction == 'v') return x + 1;
        else return x;
    }

    private static int getY(int y, char direction) {
        if (direction == '<') return y - 1;
        else if (direction == '>') return y + 1;
        else return y;
    }
}
