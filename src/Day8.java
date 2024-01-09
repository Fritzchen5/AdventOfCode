import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day8 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("res/input8.txt"))) {
            ArrayList<ArrayList<String>> lines = new ArrayList<>();
            String readerLine;
            int[] instructions = new int[0];
            Map<String, String[]> dataMap = new HashMap<>();
            while ((readerLine = reader.readLine()) != null) {
                if (readerLine.contains("=")) {
                    ArrayList<String> line = new ArrayList<>();
                    String[] lineArray = readerLine.replaceAll("[)(]", "").split("( = )|(, )");
                    line.add(lineArray[0]);
                    line.add(lineArray[1]);
                    line.add(lineArray[2]);
                    lines.add(line);
                    dataMap.put(lineArray[0], new String[]{lineArray[1], lineArray[2]});
                } else if (!readerLine.isEmpty()){
                    instructions = new int[readerLine.length()];
                    for (int i = 0; i < readerLine.length(); i++) {
                        instructions[i] = (readerLine.charAt(i) == 'L') ? 0 : ((readerLine.charAt(i) == 'R') ? 1 : -1);
                    }
                }
            }
            System.out.println("Part 1: " + followInstructions1(lines, instructions));
            System.out.println("Part 2: " + followInstructions2(dataMap, instructions));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int followInstructions1(ArrayList<ArrayList<String>> lines, int[] instructions) {
        String position = "AAA";
        int count = 0;
        String goal = "ZZZ";
        while (true) {
            for (int instruction : instructions) {
                for (ArrayList<String> line : lines) {
                    if (line.get(0).equals(position)) {
                        position = line.get(instruction + 1);
                        //System.out.println("new Position " + position + " after going to Index " + c);
                        count++;
                        if (position.equals(goal)) {
                            return count;
                        }
                        break;
                    }
                }
            }
        }
    }
    /*
    public static long followInstructions2BruteForce(Map<String, String[]> dataMap, int[] instructions) {
        int startPositions = 0;
        char endsWithStart = 'A';
        for (String key : dataMap.keySet()) {
            if (key.charAt(2) == endsWithStart) {
                startPositions++;
            }
        }
        String[] positions = new String[startPositions];
        int index = 0;
        for (String key : dataMap.keySet()) {
            if (key.charAt(2) == endsWithStart) {
                positions[index] = key;
                index++;
            }
        }

        long count = 0;
        while (true) {
            for (int instruction : instructions) {
                count++;
                int GoalPositions = 0;
                //String testOutput = "Step: " + count + " go to Index " + instruction;
                for (int i = 0; i < startPositions; i++) {
                    //if (!dataMap.containsKey(positions[i])) return -1;
                    positions[i] = dataMap.get(positions[i])[instruction];
                    if (positions[i].charAt(2) == 'Z') {
                        GoalPositions++;
                    }
                    //testOutput += "    go To " + positions.get(i);
                }
                //testOutput += "      Goal Positions: " + GoalPositions;
                //System.out.println(GoalPositions + " von " + startPositions);
                if (GoalPositions >= 3) {
                    System.out.println(count + ": " + GoalPositions);
                    if (GoalPositions >= startPositions) {
                        return count;
                    }
                }
            }
        }
    }*/

    public static long followInstructions2(Map<String, String[]> dataMap, int[] instructions) {
        int startPositions = 0;
        char endsWithStart = 'A';
        for (String key : dataMap.keySet()) {
            if (key.charAt(2) == endsWithStart) {
                startPositions++;
            }
        }
        String[] positions = new String[startPositions];
        int index = 0;
        for (String key : dataMap.keySet()) {
            if (key.charAt(2) == endsWithStart) {
                positions[index] = key;
                index++;
            }
        }

        long[] steps = new long[startPositions];
        for (int i = 0; i < startPositions; i++) {
            int instructionIndex = 0;
            int foundSolutions = 0;
            int count = 0;
            while (true) {
                count++;
                positions[i] = dataMap.get(positions[i])[instructions[instructionIndex]];
                if (positions[i].charAt(2) == 'Z') {
                    foundSolutions++;
                    if (foundSolutions == 1) {
                        steps[i] = count;
                    } else {
                        if (steps[i] * foundSolutions != count) {
                            return -1;
                        }
                    }
                    if (foundSolutions >= 10) {
                        break;
                    }
                }

                instructionIndex++;
                if (instructionIndex >= instructions.length) instructionIndex = 0;
            }
        }

        for (long l : steps) {
            System.out.println(l);
        }
        return lcm(Arrays.stream(steps).boxed().collect(Collectors.toList()));
    }

    static long lcm(List<Long> numbers)
    {
        return numbers.stream().reduce(
                (long) 1, (x, y) -> (x * y) / gcd(x, y));
    }

    static long gcd(long a, long b)
    {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }
}