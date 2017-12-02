package eu.glowacki.utp.assignment04.test;

import eu.glowacki.utp.assignment04.Assignment08Exception;
import eu.glowacki.utp.assignment04.InputParser;
import eu.glowacki.utp.assignment04.Person;
import eu.glowacki.utp.assignment04.PersonDatabase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class SerializationTest {

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
    public void serializationTest() {
        try {
            OutputStream outputStream = new FileOutputStream("databaseBackup.txt");
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            pdb.serialize(dataOutputStream);

            InputStream inputStream = new FileInputStream("databaseBackup.txt");
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            PersonDatabase pdb2 = PersonDatabase.deserialize(dataInputStream);

            Assert.assertEquals(pdb.getUnsortedList().size(), pdb2.getUnsortedList().size()); //sizes of databases

            pdb.getUnsortedList().forEach(p -> { //people
                boolean found = pdb2.getUnsortedList()
                       .parallelStream()
                       .anyMatch(p2 -> p.compareTo(p2) == 0);
                Assert.assertTrue(found);
            });

        } catch (FileNotFoundException e) {
            System.out.println("This file does not exist. Cannot proceed with backup.");
            return;
        } catch (Assignment08Exception e) {
            e.printStackTrace();
        }
    }



}
