package capstoneproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Menu extends Policy implements Runnable {
	private static int choice;
	private Vehicle vehicleObjDriver = new Vehicle();
	private PolicyHolder policyHolderObjDriver = new PolicyHolder();
	private RatingEngine ratingDriver = new RatingEngine();
	private Claim claimDriver = new Claim();
	private static boolean flag = true;
	private static final int THREADSLEEP = 500;

	public void startMenu() { // input checker @ Input interface
		startThread();
		// Main Menu Options @formatter:off
		String mainMenuOptions = "\nAutomobile PAS Menu Options\n"
				+ "1. Create a new Customer Account\n"
				+ "2. Get a policy quote and buy the policy\n"
				+ "3. Cancel a specific policy\n"
				+ "4. File an accident claim against a policy\n"
				+ "5. Search for a Customer account\n"
				+ "6. Search for and display a specific policy\n"
				+ "7. Search for and display a specific claim\n"
				+ "8. Exit the PAS System\n"
				+ "Please enter your choice: ";
		//@formatter:on
		do {
			System.out.print(mainMenuOptions);
			choice = intEntry(mainMenuOptions);
			switch (choice) {
				case 1: // enter method - create customer account

					accountCreator();

					break;

				case 2: // Add a new policy option - cannot choose if no existing customer account
					caseTwo();

					break;

				case 3: // cancel a specific policy - cannot choose if no existing policy
					caseThree();
					break;

				case 4:// File an accident claim against a policy. All claims must be maintained by
						// system and should be searchable - cannot choose if not existing policy
					caseFour();
					break;

				case 5:// invoke search method - only allow search if an account exist, otherwise,
						// return to main menu
					//@formatter:off
					caseFive();
					break;

				case 6: // Search for and display a specific policy - only allow search if a policy
					// exist, otherwise, return to main menu
					caseSix();
					break;

				case 7: // search for and display a specific claim
					caseSeven();
					break;

				case 8: // exit of the PAS console program
					System.out.println("Exiting PASApp. Thank you. Goodbye.");
					input.close(); // close scanner
					stopThread(); // stop thread
					break;
				default:
					System.out.print("INVALID input: Please enter whole numbers from 1 - 8 only: ");
			}
		} while (choice != 8);
		// return;
	}

	public void caseTwo() { // add a policy option

		System.out.println("You chose to get a quote and buy the policy");
		int optionTwoChoice;
		do {
			if (getAccountList().size() == 0) { // checks if customer account already exists. avoids array out of bounds
				// exception.
				System.out.println("No existing customer account. Please create an account before adding a policy. ");
				break;
			}//@formatter:off
			String optionTwoMenu = "\nOption 2 Menu: Get A Policy Quote and Buy a Policy\n" + 
					"1. Add a new Policy\n" + 
					"2. Add a vehicle to existing Policy\n" +
					"3. Return to Main Menu\n" + 
					"Please enter your choice: ";
			//@formatter:on
			System.out.print(optionTwoMenu);
			optionTwoChoice = intEntry(optionTwoMenu);

			switch (optionTwoChoice) {

				case 1: // add policy to account. get the details
					addNewPolicyAndVehicle();
					break;

				case 2:
					if (!accountAndPolicyExist()) {
						break;
					}
					addVehicleToExistingPolicy();
					break;

				case 3: // exit add policy option and return to main menu

					break;

				default:
					System.out.print("INVALID input: Please enter whole numbers from 1 - 3 only: ");
			}

		} while (optionTwoChoice != 3);

	}

	// add policy and vehicle because a policy should have a vehicle
	public void addNewPolicyAndVehicle() {
		String choiceContinue;
		do {
			System.out.println("You chose: Add a Policy and Vehicle");
			String instruction = "Please input a valid 4-digit account number to add policy to: ";
			System.out.print(instruction);

			int accNumAddPolicy = accNumEntry(instruction);
			CustomerAccount accObj = getAccObjByAccNum(accNumAddPolicy);
			while (true) {
				if (accObj == null) {

					System.out.print("\n" + instruction);
					accObj = getAccObjByAccNum(intInput(input.nextLine(), ""));
					continue;
				}
				break;
			}

			// policy details
			LocalDate tempEffDate;
			System.out.print("Please input effective date in yyyy-mm-dd format: ");
			while (true) {
				tempEffDate = LocalDate.parse(dateInputChecker(input.nextLine()));
				if (tempEffDate.isBefore(LocalDate.now())) {
					System.out.println("\nINVALID Input. Policy Effective date must be within six months from today.");
					System.out.print(
							"Please input an effective date after or the same as : " + LocalDate.now() + "\n--->");
					continue;
				} else if (tempEffDate.isAfter(LocalDate.now().plusMonths(6))) {
					System.out.println("\nINVALID Input. Policy Effective date must be within six months from today.");
					System.out.print("Please input an effective date before or the same as : "
							+ LocalDate.now().plusMonths(6) + "\n--->");
					continue;
				} else {
					break;
				}
			}
			LocalDate tempExpirationDate = tempEffDate.plusMonths(6);

			// policyHolderDetails
			String phFirstName;
			String phLastName;
			String phAddress;
			System.out.println("\nInput policy holder details:");
			System.out.print("\nMake policy holder the same as account owner (y/n)?: ");
			String holderIs = ynChoiceInput(input.nextLine());
			if (holderIs == "y") {
				phFirstName = accObj.getFirstName();
				phLastName = accObj.getLastName();
				phAddress = accObj.getAddress();
			} else {
				System.out.print("\nPlease provide policy holder First Name: ");
				phFirstName = nameEntry();
				System.out.print("\nPlease provide policy holder Lastname: ");
				phLastName = nameEntry();
				System.out.print("\nPlease provide policy holder Address: ");
				phAddress = addressEntry();
			}

			LocalDate phBirthday;
			while (true) {
				System.out.print("\nPlease provide policy holder Birthday (yyyy-mm-dd): ");
				phBirthday = LocalDate.parse(dateInputChecker(input.nextLine()));
				if (policyHolderObjDriver.verifyHolderAge(phBirthday)) {
					System.out.print(
							"Do you want to change provided policy holder(y/n)?\n\"Pressing \"n\" will return you to Add Policy Menu.\n(y/n)---> ");
					String otherHolderChoice = ynChoiceInput(input.nextLine());
					if (otherHolderChoice == "y") {
						continue;
					} else if (otherHolderChoice == "n") {
						return;
					}
				}
				break;
			}
			System.out.print(
					"\nPlease provide policy holder 8-digit alphanumeric License Number (press \"q\" to quit): ");
			String phLicenseNum = licenseOrPlateNumEntry("license number", 8, "(press \"q\" to cancel): ");
			if (phLicenseNum.equalsIgnoreCase("q")) {
				return;
			}
			System.out.print("\nPlease provide policy holder's license first issue date in yyyy-mm-dd format: ");
			LocalDate phLicenseIssueDate = LocalDate.parse(licenseIssueDateInputChecker(input.nextLine(), phBirthday));

			// vehicle details
			String plateNumber;
			do {
				System.out.println("\nInput Vehicle Details:");
				System.out.print("Please enter the vehicle plate number (press \"q\" to cancel): ");
				plateNumber = licenseOrPlateNumEntry("plate number", 6, "(press \"q\" to cancel): ");
				if (plateNumber.equalsIgnoreCase("q")) {
					break;
				}
				Boolean vehicleExist = vehicleObjDriver.AddPolicyAndVehicleCheckExist(plateNumber);
				if (vehicleExist) {
					continue;
				} else if (!vehicleExist) {
					break;
				}
			} while (!plateNumber.equalsIgnoreCase("q"));
			if (plateNumber.equalsIgnoreCase("q")) {
				break;
			}

			System.out.print("\nPlease input Make of the vehicle: ");
			String vehicleMake = nameEntry(); // provide Make regex input checker

			System.out.print("\nPlease input Model of the vehicle: ");
			String vehicleModel = modelEntry(); // provide Model regex input checker

			System.out.print("\nPlease input Year model of the vehicle: ");
			int vehicleYear = yearInput(input.nextLine());

			System.out.println("Please choose Type of the vehicle ");
			String vehicleType = vehicleObjDriver.vehicleTypeChoice(); // provide type input checker

			System.out.println("Please choose Fuel Type of the vehicle ");
			String vehicleFuelType = vehicleObjDriver.fuelTypeChoice(); // provide fuel type input checker;

			System.out.print("Please input color of the vehicle: "); // provide inputchecker regex for all color strings
																		// only
			String vehicleColor = validColorCheck(input.nextLine());

			String priceInputInstruction = "Please input Purchase Price of the vehicle: ";
			System.out.print(priceInputInstruction);
			double vehiclePurchasePrice = doubleEntry(priceInputInstruction); // provided purchase price checker >0 -
																				// prints instruction if input is
																				// invalid

			double vehiclePremium = doubleNumRounder(
					ratingDriver.singleVehicleRating(vehiclePurchasePrice, vehicleYear, phLicenseIssueDate));
			double initialPolicyPremium = vehiclePremium;
			System.out.println("\nThe total premium for the new policy " + Policy.getFirstPolicyNumber() + " is $"
					+ vehiclePremium);

			while (true) { //@formatter:off
				System.out.print("\nDo you want to save the details and proceed purchase of policy? (y/n): ");
				String choice = ynChoiceInput(input.nextLine());
				if (choice.equalsIgnoreCase("y")) {// only create objects upon confirmation

					//policy holder obj creation with provided policy holder details
					PolicyHolder newPolicyHolder = policyHolderObjDriver.createNewPolicyHolderObj(
							phFirstName, 
							phLastName, 
							phBirthday, 
							phAddress, 
							phLicenseNum, 
							phLicenseIssueDate);
					
					//new policyVehicleList and create vehicle object with provided vehicle details
					ArrayList<Vehicle> policyVehicleList = new ArrayList<Vehicle>();
					Vehicle newVehicle = vehicleObjDriver.newVehicleConstructor(
							vehicleMake, 
							vehicleModel, 
							vehicleYear, 
							vehicleType, 
							vehicleFuelType, 
							plateNumber, 
							vehiclePurchasePrice, 
							vehicleColor,
							vehiclePremium);
					// add the new vehicle to the policyVehicleList
					policyVehicleList.add(newVehicle);
					
					//then create the policy object with the provided information and newly created policy holder and vehicle objects
					Policy newPolicy = createNewPolicyObject(
							tempEffDate, 
							tempExpirationDate, 
							newPolicyHolder, 
							policyVehicleList, 
							initialPolicyPremium);
					
					//finally add the policy in the customer account policy arraylist using customeraccount index
					addPolicy(newPolicy, getAccountList().indexOf(accObj));
					// also add the policy holder to the specified customer account holder arraylist
					accObj.getPolicyHolderList().add(newPolicyHolder); 
					
					//prints account and policy number information
					System.out.println("\nPlease take note of your policy number: " + newPolicy.getPolicyNumber() + " under account: " + accObj.getAccNum());

				} else if (choice.equalsIgnoreCase("n")) {// if n is selected temp details will only be float and will be collected by
					// garbage collector-
					System.out.println("\nAll details were not saved. Returning to Option 2 Menu");

					break;
				} else {
					System.out.println("\nINVALID input. Please input " + "\"" + "y" + "\"" + " or \"" + "n" + "\"" + " only: ");
					continue;
				}
				break;
			}
			System.out.print("\nDo you want to purchase another policy? (y/n): ");

			choiceContinue = ynChoiceInput(input.nextLine());

			if (choiceContinue.equalsIgnoreCase("n")) {
				System.out.println("Returning to Add Policy Menu.");
			}

		} while (choiceContinue == "y");

	}

	public void addVehicleToExistingPolicy() {
		String addVehicleContinue;
		do {
			System.out.println("\nYou chose: Add a Vehicle to existing Policy");
			String instruction = "\nPlease input the policy number to add vehicle to: ";
			System.out.print(instruction);
			int policyNum = policyEntry(instruction);
			while (true) {
				int policyIndex = getPolicyIndexByPolicyNum(policyNum);
				if (policyIndex == -1) {
					System.out.println("\nNo existing Policy with the entered policy number.");
					System.out.print(instruction);
					policyNum = policyEntry(instruction);
					continue;
				}
				break;
			}
			Policy targetPolicyObj = getPolicyObjByPolicyNum(policyNum);
			LocalDate phLicenseIssueDate = targetPolicyObj.getPolicyHolderObj().getLicenseIssueDate(); // get licenseissueDate by policy number
			// add method
			// vehicle details
			String plateNumber;
			do {
				System.out.print("\nPlease enter the vehicle plate number(press \"enter\" to cancel): ");
				plateNumber = licenseOrPlateNumEntry("plate", 6, "(press \"q\" to cancel): "); // nameStringInputChecker(input.nextLine());
				Boolean vehicleExist = vehicleObjDriver.AddVehicleOnlyCheckExist(plateNumber);
				if (vehicleExist) {
					continue;
				} else if (!vehicleExist) {
					break;
				}
			} while (plateNumber != "");
			if (plateNumber == "") {
				break;
			}
			System.out.print("\nPlease input Make of the vehicle: ");
			String vehicleMake = nameStringInputChecker(input.nextLine());
			
			System.out.print("\nPlease input Model of the vehicle: ");
			String vehicleModel =modelEntry();
			
			System.out.print("\nPlease input Year model of the vehicle: ");
			int vehicleYear = yearInput(input.nextLine());
			
			System.out.println("\nPlease choose Type of the vehicle: ");
			String vehicleType = vehicleObjDriver.vehicleTypeChoice(); // provide type input checker
			
			System.out.println("\nPlease choose Fuel Type of the vehicle: ");
			String vehicleFuelType = vehicleObjDriver.fuelTypeChoice(); // provide fuel type input checker
			
			System.out.print("\nPlease input color of the vehicle: "); // provide inputchecker regex for all color strings only
			String vehicleColor = validColorCheck(input.nextLine());
			
			System.out.print("\nPlease input Purchase Price of the vehicle: ");
			double vehiclePurchasePrice = doubleEntry("Please input Purchase Price of the vehicle: "); // provide purchase price checker >0
			
			double vehiclePremium = doubleNumRounder(ratingDriver.singleVehicleRating(vehiclePurchasePrice, vehicleYear, phLicenseIssueDate));
			System.out.println("Premium cost for this vehicle is: "+ vehiclePremium);
			double policyPremium = vehiclePremium + doubleNumRounder(ratingDriver.policyRating(policyNum));
			System.out.println("The total premium for the policy is: " + (policyPremium));
			System.out.print("Proceed adding a new vehicle to policy " + policyNum + "? (y/n): ");

			while (true) { //@formatter:off
				String choice = input.nextLine();
				if (choice.equalsIgnoreCase("y")) {// only create objects upon confirmation
					// add policy to customer account
					Vehicle newVehicle = vehicleObjDriver.newVehicleConstructor(
							vehicleMake, 
							vehicleModel, 
							vehicleYear, 
							vehicleType, 
							vehicleFuelType, 
							plateNumber, 
							vehiclePurchasePrice, 
							vehicleColor,
							vehiclePremium);
					
					targetPolicyObj.getVehicleObjArrayList().add(newVehicle);
					targetPolicyObj.setPremium(policyPremium);
					System.out.println("Vehicle successfully added to policy: " + policyNum);
					// add holder method @formatter:on
				} else if (choice.equalsIgnoreCase("n")) {// if n is selected temp details will only be float and will
															// be collected by
					// garbage collector
					System.out.println("\nAll details were not saved. Returning to Option 2 Menu");
					break;
				} else {
					System.out.println(
							"\nINVALID input. Please input " + "\"" + "y" + "\"" + " or \"" + "n" + "\"" + " only: ");
					continue;
				}
				break;
			}
			System.out.print("\nDo you want to add another vehicle (y/n)?: ");
			addVehicleContinue = ynChoiceInput(input.nextLine());
		} while (addVehicleContinue.equalsIgnoreCase("y"));
	}

	public void caseThree() { // cancel a specific policy
		String stringCancelPolicy;
		String optionThreeChoice = "y";
		System.out.println("You chose to cancel a Policy");
		do {
			if (!accountAndPolicyExist()) {
				break;
			}
			System.out
					.println("Please enter policy number to cancel (press " + "\"enter\" " + "to return Main Menu): ");
			stringCancelPolicy = input.nextLine();
			if (stringCancelPolicy == "") {
				break;
			}
			// policyStatusUpdater();
			int intCancelPolicyInput = policyNumInput(stringCancelPolicy, "Please enter policy number to cancel: ");
			if (!policyNumberExist(intCancelPolicyInput)) {
				continue;
			}
			// fix cancelPolicy int value

			Policy cancelPolicyObj = getPolicyByIndex(getAccAndPolicyIndexByPolicyNum(intCancelPolicyInput));
			int cancelPolicyObjPolicyNum = cancelPolicyObj.getPolicyNumber();
			System.out.println("Please input new expiration date for the policy: " + cancelPolicyObjPolicyNum);
			LocalDate newExpirationDate = LocalDate.parse(dateInputChecker(input.nextLine()));

			Boolean validCancelDate = newExpirationDate.isAfter(cancelPolicyObj.getExpirationDate())
					|| newExpirationDate.isBefore(cancelPolicyObj.getEffectiveDate());

			while (true) {
				if (validCancelDate) {
					System.out.println("Please enter date between effective date: " + cancelPolicyObj.getEffectiveDate()
							+ " and expiration date: " + cancelPolicyObj.getExpirationDate());
					System.out.print("Please try again: ");
					newExpirationDate = LocalDate.parse(dateInputChecker(input.nextLine()));
					continue;
				} else {
					break;
				}
			}
			//@formatter:off
			System.out.print("Do you want to save the new expiration date for: " + cancelPolicyObjPolicyNum + " (y/n)?: ");
			String ynChoiceSaveExpDate = ynChoiceInput(input.nextLine());
			if (ynChoiceSaveExpDate == "y") {
				cancelPolicyObj.setExpirationDate(newExpirationDate);
				System.out.println("Please see new effective and expiration dates for policy: " + cancelPolicyObjPolicyNum);
				System.out.print("Effective date: " + cancelPolicyObj.getEffectiveDate());
//				System.out.println(getAccountList().stream().
//						flatMap(p -> p.getPolicyArray().stream().
//						filter(f -> f.getPolicyNumber() == cancelPolicyObjPolicyNum)).
//						findFirst().get().getEffectiveDate());
				System.out.print("New Expiration Date: ");
				System.out.println(getAccountList().stream().
						flatMap(p -> p.getPolicyArray().stream().
						filter(f -> f.getPolicyNumber() == cancelPolicyObjPolicyNum)).
						findFirst().get().getExpirationDate()); //findFirst().orElse(null) previous code
				System.out.println(cancelPolicyObj.getExpirationDate());
			} else if (ynChoiceSaveExpDate == "n") {
				System.out.println("New expiration date for policy: " + cancelPolicyObjPolicyNum + " is not saved. Returning to Main Menu");
				break;
			}
			System.out.print("Do you want to cancel another policy (y/n)?: ");
			optionThreeChoice = ynChoiceInput(input.nextLine());
		} while (optionThreeChoice == "y");
	}
	//@formatter:on
	public void caseFour() { // register a claim to a policy
		String stringPolicyNumber;
		String optionFourYNChoice = "y";
		System.out.println("\nYou chose to file an accident Claim against a Policy");
		do {

			if (!accountAndPolicyExist()) {
				break;
			}

			System.out.print(
					"Please input policy number to register claim (press Enter to cancel and return to main menu): ");
			stringPolicyNumber = input.nextLine();
			if (stringPolicyNumber == "") {
				break;
			}
			// policyStatusUpdater();
			int intPolicyNumber = policyNumInput(stringPolicyNumber, "Please input policy number to register claim: ");
			if (!policyNumberExist(intPolicyNumber)) {
				continue;
			}
			Policy targetPolicyObj = getPolicyObjByPolicyNum(intPolicyNumber);
			String policyStatus = targetPolicyObj.getStatus();
			if (policyStatus == "Inactive") {
				System.out.println("Cannot register a claim. Policy status is Inactive");
				continue;
			}
			LocalDate tempAccDate = null;
			int numberOfTries = 0;
			while (true) {
				if (numberOfTries == 3) {
					System.out.println("Max number of tries achieved. Returning to main menu.");
					return;
				}
				System.out.print("\nPlease input accident date (yyyy-mm-dd): ");
				tempAccDate = LocalDate.parse(dateInputChecker(input.nextLine()));

				if (tempAccDate.isAfter(LocalDate.now())) {
					System.out.println("Entered accident date is in the future. Cannot accept input");
					numberOfTries++;
					System.out.println("Remaining Tries: " + (3 - numberOfTries));
					continue;
				}
				Boolean isValidClaim = isValidClaimDate(targetPolicyObj, tempAccDate);
				if (!isValidClaim) {
					System.out.println(
							"Accident happened while Policy is not active. Cannot register a claim against this policy: "
									+ targetPolicyObj.getPolicyNumber());
					numberOfTries++;
					System.out.println("Remaining Tries: " + (3 - numberOfTries));
					continue;
				} else if (isValidClaim) {
					break;
				}

			}
			System.out.print("\nPlease provide where the accident happened (complete address): ");
			String tempAccAddress = addressEntry();
			System.out.print("\nPlease provide description of the Accident: ");
			String tempAccDesc = input.nextLine();
			System.out.print("\nPlease provide description of the Damage to the Vehicle: ");
			String tempDmgDesc = input.nextLine();
			System.out.print("\nPlease provide repair cost estimate: ");
			double tempRepairCostEst = doubleEntry("");

			System.out.print("\nDo you want continue save the details and register Claim (y/n)?: ");
			String saveClaim = ynChoiceInput(input.nextLine());
			if (saveClaim == "y") {
				Claim newClaim = claimDriver.claimCreator(tempAccDate, tempAccAddress, tempAccDesc, tempDmgDesc,
						tempRepairCostEst);
				getPolicyObjByPolicyNum(intPolicyNumber).getClaimList().add(newClaim);
				Claim.getAllClaimsList().add(newClaim);
				System.out.println("Please take note of the claim number: " + newClaim.getClaimNumber());
			} else {
				break;
			}

			System.out.print("Do you want to register another Claim? Press (y/n):\n--->");

			optionFourYNChoice = ynChoiceInput(input.nextLine());
		} while (optionFourYNChoice == "y");
	}

	public void caseFive() { // Search for a specific customer account
		String optionFiveYNChoice = "y";
		String searchFirstName = "";
		String searchLastName = "";

		do {
			System.out.println("\nSearch for customer account using first name and last name.");

			if (!accountExist()) {
				break;
			}

			while (true) {

				// policyStatusUpdater();
				System.out.print("Please Input First Name of the account you are searching: ");
				searchFirstName = nameInputChecker(input.nextLine()).trim();

				if (searchFirstName.isEmpty() || searchFirstName.isBlank()) {

					System.out.println("Blank inputs are not allowed. Please try again.");
					continue;
				}

				break;
			}

			while (true) {

				System.out.print("Please Input Last Name of the account you are searching: ");
				searchLastName = nameInputChecker(input.nextLine()).trim();
				if (searchLastName.isBlank() || searchLastName.isEmpty()) {

					System.out.println("Blank inputs are not allowed. Please try again.");
					continue;
				}
				break;

			}

			CustomerAccount targetAccObj = getAccObjByName(searchFirstName, searchLastName);
			if (targetAccObj == null) {
				System.out.print("No customer account found. Do you want to search for another account (y/n)?: ");
				optionFiveYNChoice = ynChoiceInput(input.nextLine());
				if (optionFiveYNChoice == "y") {
					continue;
				} else if (optionFiveYNChoice == "n") {
					System.out.println("Returning to main menu");
					break;
				}

			}
			displaySingleAccountDetails(targetAccObj);
			System.out.print("Do you want to search for another account (y/n)?");
			optionFiveYNChoice = ynChoiceInput(input.nextLine());
		} while (optionFiveYNChoice == "y");
	}

	public void caseSix() { // search for a specific policy
		System.out.println("\nYou chose to search for and display a specific policy.");
		String searchPolicyNumber;
		String optionSixYNChoice = "y";
		// policyStatusUpdater();
		do {
			if (!accountAndPolicyExist()) {
				break;
			}
			System.out.print(
					"Please input policy number to search and display (press enter to cancel and return to main menu): ");
			searchPolicyNumber = input.nextLine();
			if (searchPolicyNumber == "") {
				break;
			}
			int intPolicyNumber = policyNumInput(searchPolicyNumber,
					"Please input policy number to search and display: ");
			if (!policyNumberExist(intPolicyNumber)) {
				continue;
			}

			Policy targetPolicyObj = getPolicyObjByPolicyNum(intPolicyNumber);
			displayPolicyObjDetails(targetPolicyObj);

			System.out.print("Do you want to search for another policy (y/n)?: ");
			optionSixYNChoice = ynChoiceInput(input.nextLine());
		} while (optionSixYNChoice == "y");
	}

	public void caseSeven() { // search for and display a specific claim
		String claimDisplay = "y";
		System.out.println("\nYou chose search for a claim and display its details");
		do {
			if (Claim.getAllClaimsList().isEmpty()) {
				System.out.println("No Existing Claim. Please add a claim before choosing this menu.");
				break;
			}
			System.out.print("Please enter Claim number to search for (press enter to cancel): ");
			claimDisplay = input.nextLine();
			// check if a claim exist

			if (!claimDriver.claimNumberExist(claimDisplay)) {
				System.out.print("Do you want to search for another Claim (y/n)?: ");
				claimDisplay = ynChoiceInput(input.nextLine());
				continue;
			}
			claimDriver.claimDisplay(claimDisplay);
			System.out.print("Do you want to search for another Claim (y/n)?: ");
			claimDisplay = ynChoiceInput(input.nextLine());
		} while (claimDisplay == "y");
	}

	public void run() {
		do {
			try {
				policyStatusUpdater();
				Thread.sleep(THREADSLEEP);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (flag);
	}

	public void startThread() {
		(new Thread(new Menu())).start();

	}

	public void stopThread() {
		flag = false;
	}

}
