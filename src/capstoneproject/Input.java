package capstoneproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.time.LocalDate;

public interface Input {
	static Scanner input = new Scanner(System.in);
	//@formatter:off
	static final Pattern ACCEPTED_DATE = Pattern.compile( //regex for valid dates. This will reject invalid dates such as 1996-02-31 and all dates that are not existing
			"^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$" + 
					"|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"+  
					"|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$" +
			"|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");
	//@formatter:on

	public default boolean dateMatcher(String s) {
		return ACCEPTED_DATE.matcher(s).matches();
	}

	public default String dateInputChecker(String s) {
		String tempString = null;
		while (true) {
			try {

				if (dateMatcher(s)) {
					if (LocalDate.parse(s).isAfter(LocalDate.now().plusYears(2))) {// restrict adding dates which are
																					// way ahead of the future
						System.out.println("Please enter a date before or equal two (2) years from today");
						System.out.print("Please Try Again.\n--->");
						s = input.nextLine();
						continue;
					} else if (LocalDate.parse(s).isEqual(LocalDate.now())) {
						tempString = s;
						break;
					} else {
						tempString = s;
						break;
					}
				} else {
					System.out.println("That is not a valid date! Please enter again (yyyy-mm-dd)");
					System.out.print("Please Try Again.\n--->");

					s = input.nextLine();
					continue;
				}
			} catch (Exception e) {
				System.out.println("That is not a valid date! Please enter again (yyyy-mm-dd)");
				System.out.print("Please Try Again.\n--->");
				s = input.nextLine();
				continue;
			}
		}
		return tempString;

	}

	public default String licenseIssueDateInputChecker(String s, LocalDate phBirthday) {
		String tempString = null;
		while (true) {
			try {

				if (dateMatcher(s)) {
					if (LocalDate.parse(s).isAfter(LocalDate.now())) {
						System.out.println("License Issue Date should be before or the same as the date today: "
								+ LocalDate.now()); // restrict adding dates which are way ahead of the future
						System.out.print("Please Try Again.\n--->");
						s = input.nextLine();
						continue;
					} else if ((LocalDate.parse(s).isBefore(phBirthday.plusYears(16)))) {
						System.out
								.println("Policy Holder First License Issue Date cannot be before the legal age of 16");
						System.out.println("Please enter a date later than: " + phBirthday.plusYears(16).minusDays(1));
						System.out.print("Please Enter Again.\n--->");
						s = input.nextLine();
						continue;
					} else if (LocalDate.parse(s).isEqual(LocalDate.now())) {
						tempString = s;
						break;
					} else {
						tempString = s;
						break;
					}
				} else {
					System.out.println("That is not a valid date! Please enter again (yyyy-mm-dd)");
					System.out.print("Please Try Again.\n--->");

					s = input.nextLine();
					continue;
				}
			} catch (Exception e) {
				System.out.println("That is not a valid date! Please enter again (yyyy-mm-dd)");
				System.out.print("Please Try Again.\n--->");
				s = input.nextLine();
				continue;
			}
		}
		return tempString;
	}

	public default int intInput(String s, String instruction) { // includes input for Double
		int output = 0;
		Boolean inputBoolean = true;
		while (inputBoolean) {
			try {
				output = Integer.parseInt(s);
				if (output < 0) {
					System.out.println("Negative number not accepted. Please input a positive integer number. ");
					System.out.println(instruction);
					s = input.nextLine();
					inputBoolean = true;
					continue;
				}
				inputBoolean = false;
				break;
			} catch (Exception e) {
				System.out.print("Please input a valid integer number.\n--->");
				System.out.print(instruction);
				s = input.nextLine();
			}
		}
		return output;
	}

	public default int intEntry(String instruction) {
		int output = intInput(input.nextLine(), instruction);
		return output;
	}

	public default int accountNumInput(String s, String instruction) { // includes input for Double
		int output = 0;
		Boolean inputBoolean = true;
		while (inputBoolean) {

			if (s.length() > 4) {
				System.out.print("Please input a valid 4-digit account number: ");
				s = input.nextLine();
				inputBoolean = true;
				continue;
			}
			try {
				output = Integer.parseInt(s);
				if (output < 0) {
					System.out.println("Please input a valid 4-digit account number (1001-9999): ");
					s = input.nextLine();
					inputBoolean = true;
					continue;
				}
				inputBoolean = false;
				break;
			} catch (Exception e) {
				System.out.print("Please input a valid 4-digit account number: ");
				System.out.print(instruction);
				s = input.nextLine();
			}
		}
		return output;
	}

	public default int accNumEntry(String instruction) {
		int output = accountNumInput(input.nextLine(), instruction);
		return output;
	}

	public default int policyNumInput(String s, String instruction) { // includes input for Double
		int output = 0;
		Boolean inputBoolean = true;
		while (inputBoolean) {

			if (s.length() > 6) {
				System.out.print("Please input a valid 6-digit account number: ");
				s = input.nextLine();
				inputBoolean = true;
				continue;
			}
			try {
				output = Integer.parseInt(s);
				if (output < 0) {
					System.out.println("Please input a valid 6-digit account number (100001-999999): ");
					s = input.nextLine();
					inputBoolean = true;
					continue;
				}
				inputBoolean = false;
				break;
			} catch (Exception e) {
				System.out.print("Please input a valid 6-digit account number (100001-999999): ");
				System.out.print(instruction);
				s = input.nextLine();
			}
		}
		return output;
	}

	public default int policyEntry(String instruction) {
		int output = policyNumInput(input.nextLine(), instruction);
		return output;
	}

	public default double doubleInput(String s, String instruction) { // includes input for Double
		double output = 0;
		Boolean inputBoolean = true;
		while (inputBoolean) {
			try {
				output = Double.parseDouble(s);
				if (output < 0) {
					System.out.println("Negative value is not accepted. Please input a positive value. ");
					s = input.nextLine();
					inputBoolean = true;
					continue;
				}
				inputBoolean = false;
				break;
			} catch (Exception e) {
				System.out.print("Please input a valid value (letters and special characters are not allowed).\n--->");
				s = input.nextLine();
				continue;
			}
		}
		return output;
	}

	public default double doubleEntry(String instruction) {
		double output = doubleInput(input.nextLine(), instruction);

		return output;
	}

	public default double doubleNumRounder(double doubleValue) {
		return Math.round(doubleValue);
	}

	public default String nameEntry() {
		return nameStringInputChecker(input.nextLine());
	}

	public default String nameStringInputChecker(String s) {
		Boolean inputBoolean = true;
		while (inputBoolean) {

			s = s.trim().replaceAll("\\s+", " ");
			try {
				Pattern pattern = Pattern.compile("[^-a-zA-Z ]"); // compile everything set [a-zA-Z0-9]
				Matcher matcher = pattern.matcher(s); // matches everything not in set [a-zA-Z0-9]
				boolean splCharacterChecker = matcher.find();
				if (s == "") {
					System.out.print(
							"Blank input and a \"Space\" before or after a name is not allowed. Please try again: ");
					inputBoolean = true;
					s = input.nextLine();

					continue;

				} else if (splCharacterChecker) {
					System.out.print("Special characters and numbers are not allowed. Please try again: ");
					s = input.nextLine();
					inputBoolean = true;
					continue;
				} else {
					try {
						if (s.chars().anyMatch(Character::isDigit)) {
							System.out.print("Numbers are not allowed. Please try again: ");
							s = input.nextLine();
							inputBoolean = true;
							continue;
						}
					} catch (Exception e) {
						inputBoolean = false;
						System.out.print("INVALID input. Please try again: ");
						s = input.nextLine();
						break;
					}
				}
				inputBoolean = false;
				break;
			} catch (Exception e) {
				System.out.print("Please provide a valid input: ");
				s = input.nextLine();
			}
		}
		String words[] = s.split("\\s");
		String capitalizeWord = "";
		for (String w : words) {
			String first = w.substring(0, 1);
			String afterfirst = w.substring(1);
			capitalizeWord += first.toUpperCase() + afterfirst + " ";
		}
		return capitalizeWord.trim();
		// return s;
	}

	public default String modelEntry() {
		while (true) {
			String model = input.nextLine();
			if (model.trim().isBlank()) {
				System.out.print("\nINVALID Input. Blank input is not allowed. Please try again:");
				continue;

			} else {
				return model.trim().toUpperCase();
			}
		}

	}

	public default String addressEntry() {
		return addressStringInputChecker(input.nextLine());
	}

	public default String addressStringInputChecker(String s) {
		s.trim();
		while (true) {
			try {
				Pattern pattern = Pattern.compile("[^-a-zA-Z0-9 ñ.,#*]"); // compile everything set [a-zA-Z0-9]
				Matcher matcher = pattern.matcher(s); // matches everything not in set [a-zA-Z0-9]
				boolean splCharacterChecker = matcher.find();
				if (s == "" || s.charAt(0) == ' ') {
					System.out.print("Blank input and space before address is not allowed. Please try again: ");
					s = input.nextLine();
					continue;
				} else if (splCharacterChecker) {
					System.out.print("Some special characters [!@$%^&()+?<>] are not allowed. Please try again: ");
					s = input.nextLine();
					continue;
				} else if (s.length() < 5) {
					System.out.print("Please input address longer than 5 characters: ");
					s = input.nextLine();
					continue;
				} else {
					break;
				}
			} catch (Exception e) {
				System.out.print("Please provide a valid input: ");
				s = input.nextLine();
			}
		}
		String words[] = s.split("\\s");
		String capitalizeWord = "";
		for (String w : words) {
			String first = w.substring(0, 1);
			String afterfirst = w.substring(1);
			capitalizeWord += first.toUpperCase() + afterfirst + " ";
		}
		return capitalizeWord.trim();
	}

	public default String licenseOrPlateNumEntry(String inputTo, int inputLength, String exitInstruction) {
		return licenseOrPlateNumChecker(input.nextLine(), inputTo, inputLength, exitInstruction);
	}

	public default String licenseOrPlateNumChecker(String s, String inputTo, int inputLength, String exitInstruction) {
		Boolean inputBoolean = true;
		while (inputBoolean) {
			try {
				Pattern pattern = Pattern.compile("[^a-zA-Z0-9]"); // compile everything set [a-zA-Z0-9]
				Matcher matcher = pattern.matcher(s); // matches everything not in set [a-zA-Z0-9]
				boolean splCharacterChecker = matcher.find();
				if (s == "") {
					System.out.print("Blank input is not allowed. Please try again " + exitInstruction);
					s = input.nextLine();
					inputBoolean = true;
					continue;
				} else if (s.equalsIgnoreCase("q")) {
					return s;

				} else if (splCharacterChecker) {
					System.out
							.print("Special characters and space are not allowed. Please try again " + exitInstruction);
					s = input.nextLine();
					inputBoolean = true;
					continue;
				} else if (s.length() != inputLength) {
					System.out.print("INVALID " + inputTo + " input. Enter a " + inputLength + "-digit alphanumeric "
							+ inputTo + " number " + exitInstruction);
					s = input.nextLine();
					inputBoolean = true;
					continue;
				}
				inputBoolean = false;
				break;
			} catch (Exception e) {
				System.out.print("INVALID input. Please provide a valid " + inputLength + "-digit alphanumeric input: "
						+ exitInstruction);
				s = input.nextLine();
			}
		}
		s = s.toUpperCase();
		return s;
	}

	public default int yearInput(String s) {
		int yearOutput = 0;
		Boolean inputBoolean = true;
		while (inputBoolean) {
			try {
				int output = Integer.parseInt(s);
				if (output < 1900) {
					System.out.print("Please enter a year after 1900: ");
					s = input.nextLine();
				} else if (output > LocalDate.now().getYear()) {
					System.out.println("INVALID input. Allowed input is only this year " + LocalDate.now().getYear()
							+ " or earlier.");
					s = input.nextLine();
				} else {
					yearOutput = output;
					inputBoolean = false;
				}

			} catch (Exception e) {
				System.out.print("Please input a valid integer number.\n--->");
				s = input.nextLine();
			}
		}
		return yearOutput;
	}

	public default String nameInputChecker(String s) {

		while (nameDigitChecker(s) || nameSpaceChecker(s)) {
			System.out.print("Numbers and empty input are not allowed for name input. Please try again.\n--->");
			s = input.nextLine();
		}
		return s;
	}

	public default boolean nameSpaceChecker(String s) { // helper method for nameInputChecker
		boolean newLineChecker = false;
		if (s == "") {
			newLineChecker = true;
		} else {
			newLineChecker = false;
		}
		return newLineChecker;
	}

	public default boolean nameDigitChecker(String s) { // helper method for nameInputChecker
		boolean numberChecker = false;

		char[] chars = s.toCharArray();
		for (char d : chars) {
			if (Character.isDigit(d)) {
				numberChecker = true;
				break;
			} else {
				numberChecker = false;
			}
		}
		if (numberChecker == true) {
			System.out.println("The input has number");
		}
		return numberChecker;
	}

	public default String ynChoiceInput(String s) {
		while (true) {
			String choice = s;
			if (choice.equalsIgnoreCase("y")) {
				return "y";
			} else if (choice.equalsIgnoreCase("n")) {
				// System.out.println("Returning to Previous Menu");
				return "n";
			} else {
				System.out
						.print("INVALID input. Please input " + "\"" + "y" + "\"" + " or \"" + "n" + "\"" + " only: ");
				s = input.nextLine();
				continue;
			}
		}
	}

	public default String validColorCheck(String col) {
		String color = null;
		ArrayList<String> colorList = new ArrayList<String>();
		Collections.addAll(colorList, "Black", "Browm", "Blue", "Cyan", "Dark Gray", "Gray", "Green", "Yellow",
				"Light Gray", "Magenta", "Orange", "Pink", "Red", "White", "Maroon", "Brown", "Silver");

		do {
			switch (col.trim().toLowerCase()) {
				case "black":
					color = "Black";
					break;
				case "blue":
					color = "Blue";
					break;
				case "brown":
					color = "Brownn";
				case "cyan":
					color = "Cyan";
					break;
				case "dark gray":
					color = "Dark Gray";
					break;
				case "gray":
					color = "Gray";
					break;
				case "green":
					color = "Green";
					break;
				case "yellow":
					color = "Yellow";
					break;
				case "lightgray":
					color = "Light Gray";
					break;
				case "magenta":
					color = "Magenta";
					break;
				case "orange":
					color = "Orange";
					break;
				case "pink":
					color = "Pink";
					break;
				case "red":
					color = "Red";
					break;
				case "white":
					color = "White";
					break;
				case "maroon":
					color = "Maroon";
					break;
				case "silver":
					color = "Silver";
				default:
					System.out.println("Not a valid color. Please enter a valid color");

					System.out.println("List of valid colors:\n" + colorList.stream().toList());
					System.out.print("Please enter the vehicle color: ");
					col = input.nextLine();
					continue;
			}
			return color;
		} while (true);

	}
}// end of interface
