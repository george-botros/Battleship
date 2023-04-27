package org.cis1200.battleship;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Leaderboard {

    private static final String LEADERBOARD_FILE_PATH = "files/leaderboard.txt";

    public static void writeNewScoreToLeaderboard(String username, int newScore) {
        boolean notWritten = true;
        List<String[]> currLB = new LinkedList<>();
        FileLineIterator fl = new FileLineIterator(LEADERBOARD_FILE_PATH);

        while (fl.hasNext()) {
            String curr = fl.next();
            String[] line = curr.split(":");
            if (notWritten && Integer.parseInt(line[1]) <= newScore) {
                String[] newEntry = { username, Integer.toString(newScore) };
                currLB.add(newEntry);
                notWritten = false;
            }

            currLB.add(line);
        }

        // If file is empty or all scores greater than newScore.
        if (notWritten) {
            String[] newEntry = { username, Integer.toString(newScore) };
            currLB.add(newEntry);
            notWritten = false;
        }

        writeStringsToFile(currLB);
    }

    private static void writeStringsToFile(List<String[]> stringsToWrite) {
        File file = Paths.get(LEADERBOARD_FILE_PATH).toFile();

        try {
            FileWriter fw = new FileWriter(file, false);

            BufferedWriter bw = new BufferedWriter(fw);

            for (String[] s : stringsToWrite) {
                if (!s[0].equals("")) {
                    bw.write(s[0] + ":" + s[1]);
                    bw.newLine();
                }
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("[LEADERBOARD] Cannot write to leaderboard: " + e);
            for (String[] s : stringsToWrite) {
                if (!s[0].equals("")) {
                    System.out.println(s[0] + ":" + s[1]);
                }
            }
            return;
        }
    }

    public static String getLeaderboard() {
        FileLineIterator fl = new FileLineIterator(LEADERBOARD_FILE_PATH);
        StringBuilder sb = new StringBuilder();

        int index = 1;
        while (fl.hasNext() && index <= 5) {
            String curr = fl.next();
            String[] currArr = curr.split(":");
            sb.append(index);
            sb.append(". ");
            sb.append(currArr[0]);
            sb.append(": ");
            sb.append(currArr[1]);
            sb.append("\n");

            index++;
        }

        return sb.toString();
    }
}
