package eu.glowacki.utp.assignment04.test;

import eu.glowacki.utp.assignment04.InputParser;
import eu.glowacki.utp.assignment04.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Objects;

public final class InputParserTest {

    private List<Person> list;

    @Before
    public void createList() {
        File file = new File("inputData");
        list = InputParser.parse(file);
    }

    @After
    public void separator() {
        System.out.println("____\n");
    }

    @Test
    public void inputParser() {
        listList(this.list);
    }

    static void listList(List<Person> list) {
        list.stream()
                .filter(Objects::nonNull)
                .forEach(e -> System.out.println(e.getSurname() + "\t" + e.getFirstName() + "\t" + e.getBirthdate()));
    }

}