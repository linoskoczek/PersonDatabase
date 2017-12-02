package eu.glowacki.utp.assignment04.comparators;

import eu.glowacki.utp.assignment04.Person;

import java.util.Comparator;

public final class SurnameComparator implements Comparator<Person> {

	@Override
	public int compare(Person person1, Person person2) {
		return person1.getSurname().compareTo(person2.getSurname());
	}
}