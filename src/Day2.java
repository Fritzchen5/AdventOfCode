import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    public static void main(String[] args) {
        try (InputStream in = new FileInputStream("res/input2.txt")) {
            byte[] bytes = in.readAllBytes();
            String[] lines = new String(bytes).split("\n");
            int lineCount = 0;
            int sum = 0;
            int sumMinimum = 0;
            boolean possible;
            for (String line : lines) {
                lineCount++;
                possible = true;
                int maxRed = 0;
                int maxGreen = 0;
                int maxBlue = 0;
                for (String round : line.split(";")) {
                    Matcher matcherRed = Pattern.compile(" \\d+ red").matcher(round);
                    int red = 0;
                    while (matcherRed.find()) red += Integer.parseInt(round.substring(matcherRed.start() + 1, matcherRed.end() - 4));
                    if (red > maxRed) maxRed = red;
                    Matcher matcherGreen = Pattern.compile(" \\d+ green").matcher(round);
                    int green = 0;
                    while (matcherGreen.find()) green += Integer.parseInt(round.substring(matcherGreen.start() + 1, matcherGreen.end() - 6));
                    if (green > maxGreen) maxGreen = green;
                    Matcher matcherBlue = Pattern.compile(" \\d+ blue").matcher(round);
                    int blue = 0;
                    while (matcherBlue.find()) blue += Integer.parseInt(round.substring(matcherBlue.start() + 1, matcherBlue.end() - 5));
                    if (blue > maxBlue) maxBlue = blue;
                    //System.out.println("Red: " + red + " Green: " + green + " Blue: " + blue);
                    System.out.println("number: " + lineCount + " Red: " + red + " Green: " + green + " Blue: " + blue);
                    if (red > 12 || green > 13 || blue > 14) {
                        possible = false;
                    }
                }
                sumMinimum += maxRed * maxGreen * maxBlue;
                System.out.println(maxRed * maxGreen * maxBlue);
                //System.out.println(line);


                if (possible) sum += lineCount;
            }
            System.out.println(sum);
            System.out.println(sumMinimum);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}