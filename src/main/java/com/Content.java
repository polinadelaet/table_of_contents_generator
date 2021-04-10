package com;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Content {

    private final File file;
    private final StringBuilder tableOfContents;
    private final StringBuilder content;

    /**
     * A number of headers at each level.
     */
    private final Map<Integer, Integer> levels = new HashMap<>(){{
        put(1, 0);
        put(2, 0);
        put(3, 0);
        put(4, 0);
        put(5, 0);
        put(6, 0);
    }};

    public Content(File file) {
        this.file = file;
        this.tableOfContents = new StringBuilder();
        this.content = new StringBuilder();
    }

    public String createContents() {
        String []splitArray;
        int lastHeaderLevel = 0;
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String currentLine = reader.readLine();
            while (currentLine != null) {
                int headerLevel = 0;

                content.append("\n")
                       .append(currentLine);

                String nextLine = reader.readLine();
                splitArray = currentLine.split(" +", 2);

                if (Pattern.matches("#+", splitArray[0])
                                    && splitArray[0].length() == StringUtils.countMatches(splitArray[0], "#")) {
                    headerLevel = StringUtils.countMatches(splitArray[0], "#");
                }
                currentLine = currentLine
                        .replaceAll("((^#+\\s)|(\\s#+$))", "")
                        .replaceAll("((\\\\)+)", "");

                if (headerLevel == 0 ) {
                    if (nextLine != null) {
                        if (nextLine.length() == StringUtils.countMatches(nextLine, "=")) {
                            headerLevel = 1;
                        }
                        if (nextLine.length() == StringUtils.countMatches(nextLine, "-")) {
                            headerLevel = 2;
                        }
                    }
                }


                if ((headerLevel > 0) && (headerLevel <= 6)) {
                    createHeader(lastHeaderLevel, headerLevel, currentLine);
                }
                lastHeaderLevel = headerLevel;
                currentLine = nextLine;
            }
        } catch (IOException e) {
            System.out.println("File is empty.");
        }
        tableOfContents.append(content);
        return tableOfContents.toString();
    }

    private void createHeader(int lastHeaderLevel, int headerLevel, String line) {
        int numberOfGaps = (headerLevel - 1) * 4;
        if (lastHeaderLevel > headerLevel) {
            levels.put(lastHeaderLevel, 0);
        }
        levels.put(headerLevel, levels.get(headerLevel) + 1);
        tableOfContents.append(StringUtils.repeat(' ', numberOfGaps))
                .append(levels.get(headerLevel))
                .append(". [")
                .append(line)
                .append("](")
                .append("#")
                .append(line.toLowerCase().replace(' ', '-'))
                .append(")\n");
    }
}
