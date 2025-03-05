package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18 {
    public static int size = 71;
    public static int[] vX = {1, -1, 0, 0}, vY = {0, 0, 1, -1};

    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int[][] lab = new int[size][size];
            for (int[] ints : lab) Arrays.fill(ints, -1);

            int result1 = -1;
            String result2 = "";
            for (int i = 1; (line = reader.readLine()) != null; i++) {
                lab[Integer.parseInt(line.split(",")[1])][Integer.parseInt(line.split(",")[0])] = -2;
                if (i < 1024) continue;
                for (int j = 0; j < lab.length; j++)
                    for (int k = 0; k < lab[j].length; k++)
                        if (lab[j][k] != -2) lab[j][k] = -1;
                lab[0][0] = 0;
                List<Integer> qX = new ArrayList<>(), qY = new ArrayList<>();
                qX.add(0);
                qY.add(0);
                boolean foundExit = false;
                for (int j = 1;;j++) {
                    if (qX.isEmpty()) break;
                    List<Integer> tX = new ArrayList<>(qX), tY = new ArrayList<>(qY);
                    qX.clear();
                    qY.clear();
                    for (int k = 0; k < tX.size(); k++) {
                        int x = tX.get(k), y = tY.get(k);
                        for (int l = 0; l < vX.length; l++) {
                            int xx = x + vX[l], yy = y + vY[l];
                            if (xx == size - 1 && yy == size - 1) {
                                if (i == 1024) result1 = j;
                                foundExit = true;
                                break;
                            }
                            if (getPos(lab, xx, yy) == -1) {
                                lab[xx][yy] = j;
                                qX.add(xx);
                                qY.add(yy);
                            }
                        }
                        if (foundExit) break;
                    }
                    if (foundExit) break;
                }
                if (!foundExit) {
                    result2 = line;
                    break;
                }
            }
            System.out.println(result1);
            System.out.println(result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getPos(int[][] lab, int x, int y) {
        if (x < 0 || x >= lab.length || y < 0 || y >= lab[x].length) return -3;
        return lab[x][y];
    }
}