package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day13 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int readState = 0;
            int aX = 0, aY = 0, bX = 0, bY = 0;
            long result1 = 0, result2 = 0;
            while ((line = reader.readLine()) != null) {
                if (readState == 0 || readState == 1) {
                    String[] parts = line.split("(X\\+)|(,\\s)|(Y\\+)");
                    if (readState == 0) {
                        aX = Integer.parseInt(parts[1]);
                        aY = Integer.parseInt(parts[3]);
                    } else {
                        bX = Integer.parseInt(parts[1]);
                        bY = Integer.parseInt(parts[3]);
                    }
                } else if (readState == 2) {
                    String[] parts = line.split("(X=)|(,\\s)|(Y=)");
                    long cX = Integer.parseInt(parts[1]);
                    long cY = Integer.parseInt(parts[3]);
                    for (int i = 0; i <= 100; i++) {
                        int x = i * aX;
                        int y = i * aY;
                        if (x > cX || y > cY) break;
                        boolean found = false;
                        for (int j = 0; j <= 100; j++) {
                            if (x == cX && y == cY) {
                                found = true;
                                result1 += i * 3 + j;
                                break;
                            }
                            x += bX;
                            y += bY;
                            if (x > cX || y > cY) break;
                        }
                        if (found) break;
                    }

                    cX += 10000000000000L;
                    cY += 10000000000000L;
                    double y = (1.0 * cX * aY - cY * aX) / (bX * aY - bY * aX);
                    double x = (-bX * y + cX) / aX;
                    if (y % 1 == 0 && x % 1 == 0)
                        result2 += (long) (3 * x + y);
                }
                readState++;
                if (readState >= 4) readState = 0;
            }
            System.out.println(result1 + "\n" + result2);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
