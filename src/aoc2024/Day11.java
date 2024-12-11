package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day11 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            ArrayList<BigInteger> stones = new ArrayList<>();
            HashMap<BigInteger, Long> map = new HashMap<>();
            for (String s : line.split("\\s")) {
                BigInteger i = new BigInteger(s);
                stones.add(i);
                map.put(i, map.getOrDefault(i, 0L) + 1);
            }

            for (int i = 0; i < 25; i++) {
                int length = stones.size();
                for (int j = 0; j < length; j++) {
                    String stoneS = stones.get(j).toString();
                    if (stones.get(j).equals(BigInteger.ZERO))
                        stones.set(j, BigInteger.ONE);
                    else if (stoneS.length() % 2 == 0) {
                        stones.set(j, new BigInteger(stoneS.substring(0, stoneS.length() / 2)));
                        stones.add(new BigInteger(stoneS.substring(stoneS.length() / 2)));
                    } else
                        stones.set(j, stones.get(j).multiply(BigInteger.valueOf(2024)));
                }
            }
            int result1 = stones.size();
            System.out.println(result1);

            for (int i = 0; i < 75; i++) {
                HashMap<BigInteger, Long> newMap = new HashMap<>();
                for (Map.Entry<BigInteger, Long> set : map.entrySet()) {
                    BigInteger k = set.getKey();
                    String s = k.toString();
                    long v = set.getValue();
                    if (Objects.equals(s, "0"))
                        newMap.put(BigInteger.ONE, newMap.getOrDefault(BigInteger.ONE, 0L) + v);
                    else if (s.length() % 2 == 0) {
                        BigInteger n1 = new BigInteger(s.substring(0, s.length() / 2));
                        BigInteger n2 = new BigInteger(s.substring(s.length() / 2));
                        newMap.put(n1, newMap.getOrDefault(n1, 0L) + v);
                        newMap.put(n2, newMap.getOrDefault(n2, 0L) + v);
                    } else {
                        BigInteger n = k.multiply(BigInteger.valueOf(2024));
                        newMap.put(n, newMap.getOrDefault(n, 0L) + v);
                    }
                }
                map = newMap;
            }
            long result2 = 0;
            for (Map.Entry<BigInteger, Long> set : map.entrySet())
                result2 += set.getValue();
            System.out.println(result2);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
