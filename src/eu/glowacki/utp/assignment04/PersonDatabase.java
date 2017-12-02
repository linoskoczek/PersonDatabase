package eu.glowacki.utp.assignment04;

import eu.glowacki.utp.assignment04.comparators.BirthdateComparator;
import eu.glowacki.utp.assignment04.comparators.FirstNameComparator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public final class PersonDatabase {
	private List<Person> unsortedList = new ArrayList<>();
	private Map<Date, List<Person>> map = new TreeMap<>();
	private List<Person> sortedByFirstName = new ArrayList<>();
	private List<Person> sortedByBirthdate = new ArrayList<>();
	private List<Person> sortedBySurnameFirstNameAndBirthdate = new ArrayList<>();

	private final Comparator<Person> birthdateComparator = new BirthdateComparator();
	private final Comparator<Person> firstNameComparator = new FirstNameComparator();

	public List<Person> sortedByFirstName() {
		return sortedByFirstName;
	}
	
	public List<Person> sortedBySurnameFirstNameAndBirthdate() {
		return sortedBySurnameFirstNameAndBirthdate;
	}
	
	public List<Person> sortedByBirthdate() {
		return sortedByBirthdate;
	}

	private void generateListsAndMaps() {
		sortedByFirstName = new ArrayList<>(unsortedList);
		sortedByFirstName.sort(firstNameComparator);

		sortedBySurnameFirstNameAndBirthdate = new ArrayList<>(unsortedList);
		Collections.sort(sortedBySurnameFirstNameAndBirthdate);

		sortedByBirthdate = new ArrayList<>(unsortedList);
		sortedByBirthdate.sort(birthdateComparator);

		map = new TreeMap<>();
		map = unsortedList
				.stream()
				.collect(Collectors.groupingBy(Person::getBirthdate));
	}

	public List<Person> bornOnDay(Date date) {
		generateListsAndMaps();

		return map.entrySet()
				.stream()
				.filter(e -> e.getKey().compareTo(date) == 0)
				.map(Map.Entry::getValue)
				.findAny()
				.get();
	}

	public List<Person> getUnsortedList() {
		return unsortedList;
	}

	private List<Person> addRecordToListOmitUpdating(Person person) {
		unsortedList.add(person);
		return unsortedList;
	}

	public List<Person> addRecordToList(Person person) {
		addRecordToListOmitUpdating(person);
		generateListsAndMaps();
		return unsortedList;
	}

	public List<Person> addRecordsToList(List<Person> list) {
		list.forEach(this::addRecordToListOmitUpdating);
		generateListsAndMaps();
		return unsortedList;
	}

	// assignment 8 - factory method based on deserialization
	public static PersonDatabase deserialize(DataInputStream input) throws Assignment08Exception {
		try {
			int howMany = input.readInt();
			PersonDatabase personDatabase = new PersonDatabase();

			for (int i = 0; i < howMany; i++) {
				Person newPerson = Person.deserialize(input);
				personDatabase.addRecordToListOmitUpdating(newPerson);
			}
			personDatabase.generateListsAndMaps();
			return personDatabase;

		} catch (IOException e) {
			throw new Assignment08Exception(e);
		}
	}

	// assignment 8
	public void serialize(DataOutputStream output) throws Assignment08Exception {
		try {
			output.writeInt(unsortedList.size());
			for (Person person : unsortedList) {
				person.serialize(output);
			}
		} catch (IOException e) {
			throw new Assignment08Exception(e);
		}
	}
}