package com.gooroomee.chapter10;

import com.gooroomee.AppTest;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Chapter10AppTest extends AppTest {

    @Test
    public void streamApiHandleCollectionDSLTest() throws IOException {
        String fileName = "test.txt";
        ArrayList<String> errors = new ArrayList<>();
        int errorCount = 0;

        // legacy
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String line = bufferedReader.readLine();
        while (errorCount < 40 && line != null) {
            if (line.startsWith("ERROR")) {
                errors.add(line);
                errorCount++;
            }
            line = bufferedReader.readLine();
        }

        // improved
        List<String> errorList = Files.lines(Paths.get(fileName))
                                      .filter(l -> l.startsWith("ERROR"))
                                      .limit(40)
                                      .toList();
    }

    @Test
    public void dataCollectDSLTest() {

    }
}