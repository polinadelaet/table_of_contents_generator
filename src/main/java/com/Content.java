package com;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Content {
    private final Map<Integer, Integer> levels = new HashMap<>(){{
        put(1, 0);
        put(2, 0);
        put(3, 0);
        put(4, 0);
        put(5, 0);
        put(6, 0);
    }};
    private final File file;
    private StringBuilder tableOfContents = new StringBuilder();
    private StringBuilder content = new StringBuilder();

    public Content(File file) {
        this.file = file;
    }

    public String createContents() {
        FileReader fileReader = null;
        BufferedReader reader = null;

        int numberOfGaps = 0;
        String []splitArray = new String[2];
        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                int level = 0;
                content.append("\n")
                        .append(line);
                String line2 = reader.readLine();

                splitArray = line.split(" +", 2);

                if (Pattern.matches("#+", splitArray[0]) && splitArray[0].length() == StringUtils.countMatches(splitArray[0], "#")) {
                    level = StringUtils.countMatches(splitArray[0], "#");
                }
                line = line.replaceAll("((^#+\\s)|(\\s#+$))", "");
                line = line.replaceAll("((\\\\)+)", "");

                if (level == 0 ) {
                    if (line2 != null) {
                        if (line2.length() == StringUtils.countMatches(line2, "=")) {
                            level = 1;
                        }
                        if (line2.length() == StringUtils.countMatches(line2, "-")) {
                            level = 2;
                        }
                    }
                }

                if ((level > 0) && (level <= 6)) {
                    numberOfGaps = (level - 1) * 4;
                    levels.put(level, levels.get(level) + 1);
                    //String gaps = StringUtils.repeat('-', numberOfGaps);
                    tableOfContents.append(StringUtils.repeat(' ', numberOfGaps)).
                            append(levels.get(level))
                                   .append(". [")
                            //.append(splitArray[1])
                            .append(line)
                            .append("](")
                            .append("#")
                            //.append(splitArray[1].toLowerCase().replace(' ', '-'))
                            .append(line.toLowerCase().replace(' ', '-'))
                            .append(")\n");
                }
                line = line2;
            }

            // считаем сначала первую строку
        } catch (IOException e) {

        }



       tableOfContents.append(content);

        return tableOfContents.toString();
    }
}
