package com.gooroomee.chapter10;

import com.gooroomee.AppTest;
import com.gooroomee.chapter10.study.GroupingBuilder;
import com.gooroomee.chapter10.study.MethodChainingOrderBuilder;
import com.gooroomee.domain.Dish;
import com.gooroomee.domain.Order;
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

import static com.gooroomee.chapter10.study.MethodChainingOrderBuilder.*;

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
    public void methodChainDSLTest() {
        // DSL을 이용한다면?
        order = MethodChainingOrderBuilder.forCustomer("Hana Bank")
                                          .buy(120)
                                          .stock("APPLE")
                                          .on("NYSE")
                                          .at(301.12)
                                          .sell(70)
                                          .stock("COCA_COLA")
                                          .on("NASDAQ")
                                          .at(126.03)
                                          .end();
        System.out.println(order);
        // 단점 : 연결되는 모든 빌더를 구현해아하고, 상위 빌더와 접착시키는 코드가 많이 필요하다.
    }

    @Test
    public void nestedFunctionDSLTest() {
        order = order("BigBank",
                      buy(80,
                          stock("IBM", on("NYSE")), at(125.00)),
                      sell(50,
                           stock("GOOGLE", on("NASDAQ")), at(375.00))
        );
        System.out.println(order);
        // 괄호를 너무 많이 사용해야한다... -> 가독성이 매우 떨어진다.
    }
}