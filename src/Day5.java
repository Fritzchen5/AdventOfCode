import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Day5 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("res/input5.txt"))) {
            ArrayList<String> lines = new ArrayList<>();
            String readerLine;
            while ((readerLine = reader.readLine()) != null) {
                lines.add(readerLine);
            }
            //System.out.println(lines);
            long[] seeds = Arrays.stream(lines.get(0).substring(7).split(" ")).mapToLong(Long::parseLong).toArray();
            long smallestPart1 = -1;

            ArrayList<ArrayList<Long>> maps = new ArrayList<>();
            ArrayList<Long> map = new ArrayList<>();
            for (int i = 3; i < lines.size(); i++) {
                if (!Objects.equals(lines.get(i), "")) {
                    map.add(Long.valueOf(lines.get(i).split(" ")[0]));
                    map.add(Long.valueOf(lines.get(i).split(" ")[1]));
                    map.add(Long.valueOf(lines.get(i).split(" ")[2]));
                } else {
                    maps.add(map);
                    map = new ArrayList<>();
                    i++;
                }
            }
            if (!map.isEmpty()) maps.add(map);
            for (long seed : seeds) {
                long distance = getDistance(seed, maps);
                if (distance < smallestPart1 || smallestPart1 == -1) smallestPart1 = distance;
            }
            System.out.println("Part 1: " + smallestPart1);
            long smallestPart2 = -1;
            for (int i = 0; i < seeds.length; i += 2) {
                System.out.println("start " + seeds[i] + " times " + seeds[i + 1]);
                for (int j = 0; j < seeds[i + 1]; j++) {
                    long distance = getDistance(seeds[i] + j, maps);
                    if (distance < smallestPart2 || smallestPart2 == -1) smallestPart2 = distance;
                }
            }
            System.out.println("Part 2: " + smallestPart2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long getDistance(long seed, ArrayList<ArrayList<Long>> maps) {
        //System.out.println("new SEEEEDDD: " + seed);
        for (ArrayList<Long> map : maps) {
            for (int i = 0; i < map.size(); i += 3) {
                if (seed >= map.get(i + 1) && seed < map.get(i + 1) + map.get(i + 2)) {
                    //System.out.println(seed + " -> " + (seed + (map.get(i) - map.get(i + 1))));
                    seed += (map.get(i) - map.get(i + 1));
                    break;
                }
            }
        }
        return seed;
    }
}