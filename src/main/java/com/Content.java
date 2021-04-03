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
        int level;
        int numberOfGaps = 0;
        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                //String []splitArray = line.split("\\#");
                content.append("\n")
                        .append(line);
                level = StringUtils.countMatches(line, "#");
                String []splitArray = line.split(" +", 2);
                //level = splitArray.length - 1;

                if ((level > 0) && (level <= 6)) {
                    numberOfGaps = (level - 1) * 4;
                    levels.put(level, levels.get(level) + 1);
                    String gaps = StringUtils.repeat('-', numberOfGaps);
                    tableOfContents.append(StringUtils.repeat(' ', numberOfGaps)).
                            append(levels.get(level))
                                   .append(". [")
                            .append(splitArray[1])
                            .append("](")
                            .append("#")
                            .append(splitArray[1].toLowerCase().replace(' ', '-'))
                            .append(")\n");
                }
                line = reader.readLine();
            }

            // считаем сначала первую строку
        } catch (IOException e) {

        }

//        try {
//            ByteArrayOutputStream byteArrayOutStream = new ByteArrayOutputStream();
//            byteArrayOutStream.write(tableOfContents.toString().getBytes()); //someData - байты, которые нужно записать в начало файла
//            FileInputStream fileInputStream = new FileInputStream(file); //myFile - файл, в начало которого нужно дописать байты
//            while (fileInputStream.available() > 0)
//                byteArrayOutStream.write(fileInputStream.read());
//            fileInputStream.close();
//            FileOutputStream fileOS = new FileOutputStream(file);
//            byteArrayOutStream.writeTo(fileOS);
//            fileOS.close();
//            byteArrayOutStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

       tableOfContents.append(content);

        return tableOfContents.toString();
    }
}
