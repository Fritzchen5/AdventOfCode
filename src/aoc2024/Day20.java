package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day20 {
    public static void main(String[] args) {
        boolean print = false;
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<int[]> lab = new ArrayList<>();
            int x = -1, y = -1;
            for (int i = 0; (line = reader.readLine()) != null; i++) {
                lab.add(new int[line.length()]);
                for (int j = 0; j < line.toCharArray().length; j++) {
                    char c = line.charAt(j);
                    if (c == '#') lab.get(i)[j] = -2;
                    else if (c == '.' || c == 'E') lab.get(i)[j] = -1;
                    else if (c == 'S') {
                        x = i;
                        y = j;
                    }
                }
            }
            for (int i = 0; ; i++) {
                lab.get(x)[y] = i;
                if (lab.get(x + 1)[y] == -1) x++;
                else if (lab.get(x - 1)[y] == -1) x--;
                else if (lab.get(x)[y + 1] == -1) y++;
                else if (lab.get(x)[y - 1] == -1) y--;
                else break;
            }
            int result1 = 0, result2 = 0;
            for (int i = 0; i < lab.size(); i++) {
                for (int j = 0; j < lab.get(i).length; j++) {
                    int startValue = lab.get(i)[j];
                    if (startValue == -2) continue;
                    for (int k = -20; k <= 20; k++) {
                        for (int l = -20 + Math.abs(k); l <= 20 - Math.abs(k); l++) {
                            if (i + k < 0 || j + l < 0 || i + k >= lab.size() || j + l >= lab.get(0).length) continue;
                            int value = lab.get(i + k)[j + l];
                            int distance = Math.abs(k) + Math.abs(l);
                            if (value >= startValue + distance + 100) {
                                if (distance == 2) result1++;
                                result2++;
                            }
                        }
                    }
                }
            }
            System.out.println(result1 + "\n" + result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
