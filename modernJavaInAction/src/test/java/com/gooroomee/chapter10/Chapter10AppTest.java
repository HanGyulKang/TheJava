package com.gooroomee.chapter10;

import com.gooroomee.AppTest;
import com.gooroomee.chapter10.study.GroupingBuilder;
import com.gooroomee.domain.Dish;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Map<Dish.Type, Map<Boolean, List<Dish>>> collect = menu.stream().collect(Collectors.groupingBy(Dish::getType,
                                                                                                       Collectors.groupingBy(Dish::isVegetable)));
        final Comparator<Dish> dishComparator = Comparator.comparing(Dish::getType)
                                                          .thenComparing(Dish::getCalories);
        List<Dish> list = menu.stream()
                              .sorted(dishComparator)
                              .toList();
        list.forEach(System.out::println);

        final Collector<Dish, ?, Map<Dish.Type, Map<Boolean, List<Dish>>>> dishMapCollector
                = Collectors.groupingBy(Dish::getType, Collectors.groupingBy(Dish::isVegetable));
        Map<Dish.Type, Map<Boolean, List<Dish>>> collect1 = menu.stream()
                                                                .sorted(dishComparator)
                                                                .collect(dishMapCollector);

        GroupingBuilder<Dish, Map<Dish.Type, List<Dish>>, Boolean> after = GroupingBuilder.groupOn(Dish::getType)
                                                                                          .after(Dish::isVegetable);
    }

    @Test
    public void orderDSLTest() {
        System.out.println(order);
    }
}