import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Day1 {
    public static void main(String[] args) {
        String[] numbers = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        ArrayList<ArrayList<Integer>> correct = new ArrayList<>();
        try (InputStream in = new FileInputStream("res/input1.txt")) {
            byte[] bytes = in.readAllBytes();
            String[] lines = new String(bytes).split("\n");
            int sum = 0;
            for (String s : lines) {
                correct.clear();
                for (String ignored : numbers) {
                    ArrayList<Integer> part = new ArrayList<>();
                    correct.add(part);
                }
                char first = '0', last = '0';
                boolean foundFirst = false;
                char number;
                for (char c : s.toCharArray()) {
                    number = c;
                    for (int i = 0; i < numbers.length; i++) {
                        for (int j = 0; j < correct.get(i).size(); j++) {
                            if (numbers[i].charAt(correct.get(i).get(j)) == c) {
                                correct.get(i).set(j, correct.get(i).get(j) + 1);
                                if (correct.get(i).get(j) >= numbers[i].length()) {
                                    number = (char) (i + 1 + '0');
                                    correct.get(i).remove(j);
                                    j--;
                                }
                            } else {
                                correct.get(i).remove(j);
                                j--;
                            }
                        }
                        if (numbers[i].charAt(0) == c) {
                            correct.get(i).add(1);
                        }
                    }
                    if (Character.isDigit(number)) {
                        if (!foundFirst) {
                            first = number;
                            foundFirst = true;
                        }
                        last = number;
                    }
                }
                sum += Character.getNumericValue(first) * 10 + Character.getNumericValue(last);
            }
            System.out.println(sum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}