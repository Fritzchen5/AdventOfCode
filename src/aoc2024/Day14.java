package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day14 {
    public static void main(String[] args) {
        boolean printTree = false;
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int[] quadrants = new int[4];
            int wide = 101, tall = 103, count = 0;
            int[] posX = new int[500], posY = new int[500], movX = new int[500], movY = new int[500];
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[=,\\s]");
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                int mX = Integer.parseInt(parts[4]);
                int mY = Integer.parseInt(parts[5]);
                posX[count] = x;
                posY[count] = y;
                movX[count] = mX;
                movY[count] = mY;
                x = (x + 100 * mX) % wide;
                y = (y + 100 * mY) % tall;
                if (x < 0) x += wide;
                if (y < 0) y += tall;
                if (x < wide / 2 && y < tall / 2) quadrants[0]++;
                else if (x < wide / 2 && y > tall / 2) quadrants[1]++;
                else if (x > wide / 2 && y < tall / 2) quadrants[2]++;
                else if (x > wide / 2 && y > tall / 2) quadrants[3]++;
                count++;
            }
            int result1 = 1;
            for (int q : quadrants)
                result1 *= q;
            System.out.println(result1);

            int result2 = 0;
            for (int i = 0; i < 10000; i++) {
                int[][] room = new int[tall][wide];
                boolean print = true;
                for (int j = 0; j < 500; j++) {
                    posX[j] = (posX[j] + movX[j]) % wide;
                    posY[j] = (posY[j] + movY[j]) % tall;
                    if (posX[j] < 0) posX[j] += wide;
                    if (posY[j] < 0) posY[j] += tall;
                    room[posY[j]][posX[j]]++;
                    if (room[posY[j]][posX[j]] > 1) print = false;
                }
                if (!print) continue;
                if (printTree) System.out.println("--------------\n" + i + 1 + "\n--------------");
                for (int[] j : room) {
                    StringBuilder s = new StringBuilder();
                    int inLine = 0;
                    for (int k : j) {
                        if (k == 0) s.append(" ");
                        else {
                            s.append(k);
                            inLine++;
                            if (inLine > 20 && result2 == 0)
                                result2 = i + 1;
                        }
                    }
                    if (printTree) System.out.println(s);
                }
            }
            System.out.println(result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
