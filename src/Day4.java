import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("res/input4.txt"))) {
            List<String> lines = new ArrayList<>();
            ArrayList<Integer> scrachCardsAmountEach = new ArrayList<>();
            String readerLine;
            while ((readerLine = reader.readLine()) != null) {
                lines.add(readerLine);
                scrachCardsAmountEach.add(1);
            }
            int sum = 0;
            int scratchCards = 0;
            int lineNumber = 0;
            for (String line : lines) {
                scratchCards += scrachCardsAmountEach.get(lineNumber);
                int worth = 0;
                int equal = 0;
                int[] yourNums = Arrays.stream(line.split("(: +| \\| )")[1].split(" +")).mapToInt(Integer::parseInt).toArray();
                int[] winningNums = Arrays.stream(line.split(" \\| +")[1].split(" +")).mapToInt(Integer::parseInt).toArray();
                for (int i : winningNums) {
                    for (int j : yourNums) {
                        if (i == j) {
                            equal++;
                            if (worth == 0) worth = 1;
                            else worth *= 2;
                            break;
                        }
                    }
                }
                for (int i = 0; i < equal; i++) {
                    if (lineNumber + 1 + i < lines.size()) {
                        scrachCardsAmountEach.set(lineNumber + 1 + i, scrachCardsAmountEach.get(lineNumber + 1 + i) + scrachCardsAmountEach.get(lineNumber));
                    }
                }
                sum += worth;
                lineNumber++;
            }
            System.out.println("Part 1: " + sum);
            System.out.println("Part 2: " + scratchCards);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}