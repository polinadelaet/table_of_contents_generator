package com;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
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
        int level = 0;
        int numberOfGaps = 0;
        String []splitArray = new String[2];
        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                content.append("\n")
                        .append(line);
                String line2 = reader.readLine();
                if ((line2 != null) && ((line2.length() == StringUtils.countMatches(line2, "=")) ||
                        (line2.length() == StringUtils.countMatches(line2, "-")))) {
                    if (line2.length() == StringUtils.countMatches(line2, "=")) {
                        level = 1;
                    }
                    if (line2.length() == StringUtils.countMatches(line2, "-")) {
                        level = 2;
                    }
                    splitArray[1] = line;
                } else {
                    level = StringUtils.countMatches(line, "#");
                    //splitArray = line.split(" +", 2);
                    splitArray = line.split("[# ]+", 2);
                }


                if ((level > 0) && (level <= 6)) {
                    numberOfGaps = (level - 1) * 4;
                    levels.put(level, levels.get(level) + 1);
                    //String gaps = StringUtils.repeat('-', numberOfGaps);
                    tableOfContents.append(StringUtils.repeat(' ', numberOfGaps)).
                            append(levels.get(level))
                                   .append(". [")
                            .append(splitArray[1])
                            .append("](")
                            .append("#")
                            .append(splitArray[1].toLowerCase().replace(' ', '-'))
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
