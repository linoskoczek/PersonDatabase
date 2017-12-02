package eu.glowacki.utp.assignment04.test;

import eu.glowacki.utp.assignment04.InputParser;
import eu.glowacki.utp.assignment04.Person;
import eu.glowacki.utp.assignment04.PersonDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.List;

import static eu.glowacki.utp.assignment04.test.InputParserTest.listList;

public class PersonDatabaseTest {

    private List<Person> list;
    private PersonDatabase pdb;

    @Before
    public void createList() {
        File file = new File("inputData");
        list = InputParser.parse(file);
        pdb = new PersonDatabase();
        pdb.addRecordsToList(list);
    }

    @Test
    public void listUnsorted() {
        System.out.println(">Unsorted list:");
        listList(pdb.getUnsortedList());
    }

    @After
    public void separator() {
        System.out.println("____\n");
    }

    @Test
    public void sortByFirstName() {
        System.out.println(">Sorting by first name:");
        listList(pdb.sortedByFirstName());
    }

    @Test
    public void sortByBirthdate() {
        System.out.println(">Sorting by birthdate:");
        listList(pdb.sortedByBirthdate());
    }

    @Test
    public void sortByEverything() {
        System.out.println(">Sorting by everything:");
        listList(pdb.sortedBySurnameFirstNameAndBirthdate());
    }

    @Test
    public void findPeopleByBirthdate() {
        String date = "2000-01-02";
        System.out.println(">Searching for people born on " + date);
        Date dateForSearch = InputParser.convertBirthdateToDate(date);
        listList(pdb.bornOnDay(dateForSearch));
    }
}