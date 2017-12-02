package eu.glowacki.utp.assignment04;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class InputParser {
	
	// 1. Use regular expresssions (Pattern) for validating input data
	//    U?y? regularnych wyra?e? (Pattern) do walidacji danych wej?ciowych
	//
	// 2. Convert input string representing date using SimpleDateFormat "yyyy-MM-dd" 
	//    Konwersj? wej?ciowego ci?gu znak?w reprezentuj?cego dat? nale?y oprze? np. DateFormat 
	//    SimpleDateFormat format "yyyy-MM-dd"

	private static final String FIRST_NAME_PATTERN = "[A-Za-z?ó????????Ó???????]{2,}";
	private static final String SURNAME_PATTERN = "[A-Za-z?ó????????Ó???????]{2,}([\\-][A-Za-z?ó????????Ó???????]+)*";
	private static final String YEAR_PATTERN = "[0-9]{1,4}";
	private static final String MONTH_PATTERN = "([1-9])|([1][012]?)";
	private static final String DAY_PATTERN = "(([1-9])|([12][0-9]?)|([3][01]?))";
	private static final SimpleDateFormat FORMAT_OF_DATE = new SimpleDateFormat("yyyy-MM-dd");

	public static List<Person> parse(File file) {
		List<Person> personList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] splits = line.split(" ");
				if (splits.length != 3) continue;
				if (validate(splits[0], FIRST_NAME_PATTERN) && validate(splits[1], SURNAME_PATTERN) && validateBirthdate(splits[2])) {
					Person person = new Person(splits[0], splits[1], convertBirthdateToDate(splits[2]));
					personList.add(person);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			return null;
		} catch (IOException e) {
			System.out.println("Error while reading a file!");
			return null;
		}
		return personList;
	}

	private static boolean validate(String input, String pattern) {
		return input.matches(pattern);
	}

	private static boolean validateBirthdate(String birthdate) {
		String[] split = birthdate.split("-");
		return split.length == 3 &&
				validate(stripZerosFromBeginning(split[0]), YEAR_PATTERN) &&
				validate(stripZerosFromBeginning(split[1]), MONTH_PATTERN) &&
				validate(stripZerosFromBeginning(split[2]), DAY_PATTERN);
	}

	private static String stripZerosFromBeginning(String input) {
		return input.replaceFirst("^0+(?!$)", "");
	}

	public static Date convertBirthdateToDate(String birthdate) {
		try {
			return FORMAT_OF_DATE.parse(birthdate);
		} catch (ParseException e) {
			return null;
		}
	}
}