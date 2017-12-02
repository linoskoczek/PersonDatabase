package eu.glowacki.utp.assignment04;

import eu.glowacki.utp.assignment04.comparators.BirthdateComparator;
import eu.glowacki.utp.assignment04.comparators.FirstNameComparator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Person implements Comparable<Person> {
	
	private final String firstName;
	private final String surname;
	private final Date birthdate;
	private static final SimpleDateFormat FORMAT_OF_DATE = new SimpleDateFormat("yyyy-MM-dd");


	public Person(String firstName, String surname, Date birthdate) {
		this.firstName = firstName;
		this.surname = surname;
		this.birthdate = birthdate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	// assignment 8
	public void serialize(DataOutputStream output) throws Assignment08Exception {
		// serialize birth date with getTime() method
		// encapsulate IOException in Assignment08Exception

		try {
			output.writeUTF(firstName);
			output.writeUTF(surname);
			output.writeLong(birthdate.getTime());
		} catch (IOException e) {
			throw new Assignment08Exception(e);
		}

	}

	public static Person deserialize(DataInputStream input) throws Assignment08Exception {
		try {
			String firstName = input.readUTF();
			String surname = input.readUTF();
			Date birthdate = new Date(input.readLong());

			return new Person(firstName, surname, birthdate);
		} catch (IOException e) {
			throw new Assignment08Exception(e);
		}

	}

	@Override
	public int compareTo(Person otherPerson) {
		Comparator<Person> birthdayComparator = new BirthdateComparator();
		Comparator<Person> firstNameComparator = new FirstNameComparator();
		int result = this.getSurname().compareTo(otherPerson.getSurname());
		if(result == 0) result = firstNameComparator.compare(this, otherPerson);
		if(result == 0) result = birthdayComparator.compare(this, otherPerson);
		return result;
	}

}