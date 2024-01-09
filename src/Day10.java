import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day10 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("res/input10.txt"))) {
            String lineString;
            ArrayList<String> lines = new ArrayList<>();
            while ((lineString = reader.readLine()) != null) {
                lines.add(lineString);
                System.out.println(lineString);
            }
            char[][] pipes = new char[lines.size()][lines.get(0).length()];
            int startRow = -1;
            int startCol = -1;
            for (int i = 0; i < lines.size(); i++) {
                for (int j = 0; j < lines.get(i).length(); j++) {
                    pipes[i][j] = lines.get(i).charAt(j);
                    if (pipes[i][j] == 'S') {
                        startRow = i;
                        startCol = j;
                    }
                }
            }
            char last = ' ';
            char nextDirection;
            int row = -1;
            int col = -1;
            char next = getPipeAt(pipes, startRow - 1, startCol, 'u');
            if (next == '|' || next == '7' || next == 'F') {
                last = 'd';
                row = startRow - 1;
                col = startCol;
            } else {
                next = getPipeAt(pipes, startRow, startCol, 'r');
                if (next == '-' || next == '7' || next == 'J') {
                    last = 'l';
                    row = startRow;
                    col = startCol + 1;
                } else {
                    next = getPipeAt(pipes, startRow, startCol, 'd');
                    if (next == '|' || next == 'L' || next == 'J') {
                        last = 'u';
                        row = startRow + 1;
                        col = startCol;
                    } else {
                        next = getPipeAt(pipes, startRow, startCol, 'l');
                        if (next == '-' || next == 'F' || next == 'L') {
                            last = 'r';
                            row = startRow;
                            col = startCol - 1;
                        }
                    }
                }
            }
            int secondX = col;
            int secondY = row;
            nextDirection = getNextDirection(next, last);
            pipes[row][col] = 'p';
            System.out.println(startRow + "   " + startCol);
            System.out.println("next   " + row + "   " + col + "   last: " + last + "   next: " + nextDirection);
            int distance = 1;
            int area = 0;
            int xBefore = col;
            int yBefore = row;
            int xTwoBefore = startCol;
            int yTwoBefore = startRow;
            while ((next = getPipeAt(pipes, row, col, nextDirection)) != 'S') {
                System.out.println("next: " + next + "    row: " + row + "  col: " + col + "   nextDirection: " + nextDirection);
                if (nextDirection == 'u') {
                    row--;
                    last = 'd';
                }
                else if (nextDirection == 'r') {
                    col++;
                    last = 'l';
                }
                else if (nextDirection == 'd') {
                    row++;
                    last = 'u';
                }
                else if (nextDirection == 'l') {
                    col--;
                    last = 'r';
                }
                System.out.println("area += xBefore * (row - yTwoBefore)   " + area + " += " + xBefore + " * (" + row + " - " + yTwoBefore + ")");
                area += xBefore * (row - yTwoBefore);

                xTwoBefore = xBefore;
                yTwoBefore = yBefore;
                xBefore = col;
                yBefore = row;
                pipes[row][col] = 'p';
                distance++;
                nextDirection = getNextDirection(next, last);
            }
            System.out.println(xTwoBefore + "  " + yTwoBefore + "       " + xBefore + "   " + yBefore + "       " + col + "   " + row + "        " + startCol + "  " + startCol + "      " + secondX + "  " + secondY);
            System.out.println("area += col * (startRow - yTwoBefore)   " + area + " += " + col + " * (" + startRow + " - " + yTwoBefore + ")");
            area += col * (startRow - yTwoBefore);
            System.out.println("area += startCol * (secondY - row)   " + area + " += " + startCol + " * (" + secondY + " - " + row + ")");
            area += startCol * (secondY - row);
            area = Math.abs(area);
            System.out.println("distance: " + distance);
            int part1 = (distance + 1) / 2;
            System.out.println("part 1: " + part1);
            System.out.println("area: " + area);
            System.out.println("0.5 * " + area + " - " + (distance + 1) + " * 0.5 + 1");
            System.out.println("Part 2: " + (0.5 * area - (distance + 1) * 0.5 + 1));
            /*
            for (char[] pipe : pipes) {
                System.out.println(String.valueOf(pipe));
            }
            char[][] newPipes = new char[pipes.length + 2][pipes[0].length + 2];
            for (int i = 0; i < pipes.length + 2; i++) {
                for (int j = 0; j < pipes[0].length + 2; j++) {
                    if (i == 0 || i == pipes.length + 1 || j == 0 || j == pipes[0].length + 1) newPipes[i][j] = '.';
                    else newPipes[i][j] = pipes[i - 1][j - 1];
                }
            }
            for (char[] pipe : newPipes) {
                System.out.println(String.valueOf(pipe));
            }
            findEnclosed(newPipes, 0, 0);
            System.out.println("finished");
            for (char[] pipe : newPipes) {
                System.out.println(String.valueOf(pipe));
            }
            int part2 = 0;
            for (char[] charRow : newPipes) {
                for (char c : charRow) {
                    if (c != 'o' && c != 'p' && c != 'S') part2++;
                }
            }
            System.out.println("part 2: " + part2);
            */
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void findEnclosed(char[][] pipes, int row, int col) {
        pipes[row][col] = 'o';
        char c = getPipeAt(pipes, row, col, 'u');
        if (c != 'e' && c != 'p' && c != 'o' && c != 'S') findEnclosed(pipes, row - 1, col);
        c = getPipeAt(pipes, row, col, 'r');
        if (c != 'e' && c != 'p' && c != 'o' && c != 'S') findEnclosed(pipes, row, col + 1);
        c = getPipeAt(pipes, row, col, 'd');
        if (c != 'e' && c != 'p' && c != 'o' && c != 'S') findEnclosed(pipes, row + 1, col);
        c = getPipeAt(pipes, row, col, 'l');
        if (c != 'e' && c != 'p' && c != 'o' && c != 'S') findEnclosed(pipes, row, col - 1);
    }

    public static char getPipeAt(char[][] pipes, int row, int col, char direction) {
        if (direction == 'u') row--;
        else if (direction == 'r') col++;
        else if (direction == 'd') row++;
        else if (direction == 'l') col--;
        if (row >= 0 && row < pipes.length && col >= 0 && col < pipes[row].length) return pipes[row][col];
        return 'e';
    }

    public static char getNextDirection(char pipe, char last) {
        if (pipe == '-') {
            if (last == 'l') return 'r';
            else if (last == 'r') return 'l';
        } else if (pipe == '|') {
            if (last == 'u') return 'd';
            else if (last == 'd') return 'u';
        } else if (pipe == 'L') {
            if (last == 'u') return 'r';
            else if (last == 'r') return 'u';
        } else if (pipe == 'J') {
            if (last == 'u') return 'l';
            else if (last == 'l') return 'u';
        } else if (pipe == '7') {
            if (last == 'l') return 'd';
            else if (last == 'd') return 'l';
        } else if (pipe == 'F') {
            if (last == 'd') return 'r';
            else if (last == 'r') return 'd';
        }
        return 'e';
    }
}