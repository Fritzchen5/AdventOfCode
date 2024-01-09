import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day9 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("res/input9.txt"))) {
            String line;
            int sum1 = 0;
            int sum2 = 0;
            while ((line = reader.readLine()) != null) {
                ArrayList<ArrayList<Integer>> v = new ArrayList<>();
                ArrayList<Integer> line1 = new ArrayList<>();
                line1.add(0);
                for (String s : line.split(" ")) {
                    line1.add(Integer.parseInt(s));
                }
                v.add(line1);

                boolean allZero;
                int rowIndex = 0;
                do {
                    allZero = true;
                    ArrayList<Integer> row = new ArrayList<>();
                    row.add(0);
                    for (int i = 2; i < v.get(rowIndex).size(); i++) {
                        row.add(v.get(rowIndex).get(i) - v.get(rowIndex).get(i - 1));
                        if (row.get(i - 1) != 0) allZero = false;
                    }
                    v.add(row);
                    rowIndex++;
                } while (!allZero);
                for (ArrayList<Integer> i : v) {
                    System.out.println(i);
                }
                System.out.println("-----");
                v.get(v.size() - 1).add(0);
                for (ArrayList<Integer> i : v) {
                    System.out.println(i);
                }
                System.out.println("-----");
                for (int i = v.size() - 2; i >= 0; i--) {
                    v.get(i).add(v.get(i).get(v.get(i).size() - 1) + v.get(i + 1).get(v.get(i + 1).size() - 1));
                    v.get(i).set(0, v.get(i).get(1) - v.get(i + 1).get(0));
                }
                for (ArrayList<Integer> i : v) {
                    System.out.println(i);
                }
                System.out.println("-----");
                sum1 += v.get(0).get(v.get(0).size() - 1);
                sum2 += v.get(0).get(0);
            }
            System.out.println(sum1);
            System.out.println(sum2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}