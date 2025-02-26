package aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Day17 {
    public static boolean[] combo = {true, false, true, false, false, true, true, true};
    
    public static void main(String[] args) {
        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
        String filePath = "res/" + currentClass.getPackageName() + "/" + currentClass.getSimpleName() + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Long[] registers = new Long[3];
            for (int lineCounter = 0; (line = reader.readLine()) != null; lineCounter++) {
                if (lineCounter < 3) {
                    registers[lineCounter] = Long.parseLong(line.split(": ")[1]);
                } else if (lineCounter == 4) {
                    int[] code = Arrays.stream(line.split("[, ]")).skip(1).mapToInt(Integer::valueOf).toArray();
                    int iPointer = 0;
                    StringBuilder result1 = new StringBuilder();
                    while (iPointer + 1 < code.length) {
                        int r = performOpCode(code[iPointer], code[iPointer+1], registers);
                        if (code[iPointer] == 5) {
                            if (!result1.isEmpty()) result1.append(",");
                            result1.append(r);
                        }
                        if (code[iPointer] == 3 && r != -1)
                            iPointer = r;
                        else
                            iPointer += 2;
                    }
                    System.out.println(result1);

                    Set<Long> goodPrefix = new HashSet<>();
                    goodPrefix.add(0L);
                    for (int i = code.length - 1; i >= 0; i--) {
                        //System.out.print("index: " + i + ", good Prefix length: " + goodPrefix.size());
                        //System.out.println(", min: " + Collections.min(goodPrefix));
                        Set<Long> oldPrefix = new HashSet<>(goodPrefix);
                        goodPrefix.clear();
                        for (Long j : oldPrefix) {
                            for (long k = 0b0L; k <= 0b111L; k++) {
                                long aRegister = j * 8 + k;
                                iPointer = 0;
                                registers[0] = aRegister; //setting other registers is not required with this program
                                while (iPointer + 1 < code.length) {
                                    int r = performOpCode(code[iPointer], code[iPointer+1], registers);
                                    if (code[iPointer] == 5) {
                                        if (r == code[i])
                                            goodPrefix.add(aRegister);
                                        break;
                                    }
                                    iPointer += 2;
                                }
                            }
                        }
                    }
                    long result2 = Collections.min(goodPrefix);
                    System.out.println(result2);
                    //System.out.println(goodPrefix);
                }
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int performOpCode(int opCode, int operant, Long[] registers) {
        Long finalOperant = (long) operant;
        if (combo[opCode] && operant > 3) finalOperant = registers[operant - 4];
        switch (opCode) {
            case 0:
                registers[0] = (long) (registers[0] / Math.pow(2, finalOperant.intValue()));
                break;
            case 1:
                registers[1] = registers[1] ^ finalOperant;
                break;
            case 2:
                registers[1] = finalOperant % 8L;
                break;
            case 3:
                if (registers[0] != 0L) return finalOperant.intValue();
                break;
            case 4:
                registers[1] = registers[1] ^ registers[2];
                break;
            case 5:
                return (int) (finalOperant % 8L);
            case 6:
                registers[1] = (long) (registers[0] / Math.pow(2, finalOperant.intValue()));
                break;
            case 7:
                registers[2] = (long) (registers[0] / Math.pow(2, finalOperant.intValue()));
                break;
        }
        return -1;
    }
}
