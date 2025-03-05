package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day5 {
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<Integer> rulesL = new ArrayList<>();
            List<Integer> rulesR = new ArrayList<>();
            boolean readRules = true;
            int result1 = 0;
            int result2 = 0;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    readRules = false;
                    continue;
                }
                if (readRules) {
                    rulesL.add(Integer.valueOf(line.split("\\|")[0]));
                    rulesR.add(Integer.valueOf(line.split("\\|")[1]));
                } else {
                    int[] updates = Stream.of(line.split(",")).mapToInt(Integer::parseInt).toArray();
                    if (isUpdateOk(updates, rulesL, rulesR))
                        result1 += updates[(updates.length - 1) / 2];
                    else {
                        List<Integer> newUpdate = new ArrayList<>();
                        while (newUpdate.size() < updates.length) {
                            for (int i = 0; i < updates.length; i++) {
                                if (updates[i] == -1) continue;
                                boolean breaksRule = false;
                                for (int j = 0; j < rulesL.size(); j++) {
                                    if (rulesR.get(j) != updates[i]) continue;
                                    if (newUpdate.contains(rulesL.get(j))) continue;
                                    if (!Arrays.stream(updates).boxed().toList().contains(rulesL.get(j))) continue;
                                    breaksRule = true;
                                    break;
                                }
                                if (!breaksRule) {
                                    newUpdate.add(updates[i]);
                                    updates[i] = -1;
                                }
                            }
                        }
                        result2 += newUpdate.get((newUpdate.size() - 1) / 2);
                    }
                }
            }
            System.out.println(result1);
            System.out.println(result2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isUpdateOk(int[] updates, List<Integer> rulesL, List<Integer> rulesR) {
        for (int i = 0; i < updates.length; i++)
            for (int j = i + 1; j < updates.length; j++)
                for (int k = 0; k < rulesL.size(); k++)
                    if (updates[i] == rulesR.get(k) && updates[j] == rulesL.get(k))
                        return false;
        return true;
    }
}
