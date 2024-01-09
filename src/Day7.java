import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day7 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("res/input7.txt"))) {
            ArrayList<String> lines = new ArrayList<>();
            String readerLine;
            while ((readerLine = reader.readLine()) != null) {
                lines.add(readerLine);
            }
            int[][] values1 = new int[lines.size()][6];
            int[][] values2 = new int[lines.size()][6];
            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < 5; j++) {
                    values1[i][j] = Integer.parseInt(String.valueOf(lines.get(i).charAt(j)).replace("T", "10").replace("J", "11").replace("Q", "12").replace("K", "13").replace("A", "14")) - 2;
                    values2[i][j] = Integer.parseInt(String.valueOf(lines.get(i).charAt(j)).replace("T", "10").replace("J", "1").replace("Q", "11").replace("K", "12").replace("A", "13")) - 1;
                }
                values1[i][5] = Integer.parseInt(lines.get(i).substring(6));
                values2[i][5] = Integer.parseInt(lines.get(i).substring(6));
            }
            for (int i = 0; i < values1.length - 1; i++) {
                for (int j = 0; j < values1.length - i - 1; j++) {
                    if (compareCards(values1[j], values1[j + 1], 1)) {
                        int[] tmp = values1[j];
                        values1[j] = values1[j + 1];
                        values1[j + 1]= tmp;
                    }
                    if (compareCards(values2[j], values2[j + 1], 2)) {
                        int[] tmp = values2[j];
                        values2[j] = values2[j + 1];
                        values2[j + 1]= tmp;
                    }
                }
            }
            int part1 = 0;
            int part2 = 0;
            for (int i = 0; i < values1.length; i++) {
                part1 += (i + 1) * values1[i][5];
                part2 += (i + 1) * values2[i][5];
            }
            System.out.println("Part 1: " + part1);
            System.out.println("Part 2: " + part2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean compareCards(int[] hand1, int[] hand2, int part) {
        HashMap<Integer, Integer> map1 = new HashMap<>();
        HashMap<Integer, Integer> map2 = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map1.put(hand1[i], ((map1.get(hand1[i]) == null) ? 1 : map1.get(hand1[i]) + 1));
            map2.put(hand2[i], ((map2.get(hand2[i]) == null) ? 1 : map2.get(hand2[i]) + 1));
        }
        int category1 = getCategorie(map1, part);
        int category2 = getCategorie(map2, part);
        if (category1 > category2) return true;
        if (category1 < category2) return false;

        for (int i = 0; i < 5; i++) {
            if (hand1[i] > hand2[i]) return true;
            if (hand1[i] < hand2[i]) return false;
        }
        return false;
    }

    public static int getCategorie(HashMap<Integer, Integer> hand, int part) {
        boolean trible = false;
        boolean pair = false;
        if (part == 2 && hand.get(0) != null) {
            int jacks = hand.get(0);
            hand.put(0, 0);
            int biggestValue = 0;
            for (Map.Entry<Integer, Integer> entry : hand.entrySet()) {
                if (entry.getValue() > hand.getOrDefault(biggestValue, 0)) {
                    biggestValue = entry.getKey();
                }
            }
            hand.put(biggestValue, hand.getOrDefault(biggestValue, 0) + jacks);
        }
        for (int i : hand.values()) {
            if (i == 5) return 6;
            if (i == 4) return 5;
            if (i == 3) {
                if (pair) return 4;
                else trible = true;
            }
            if (i == 2) {
                if (trible) return 4;
                if (pair) return 2;
                else pair = true;
            }
        }
        if (trible) return 3;
        if (pair) return 1;
        return 0;
    }
}