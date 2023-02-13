package capstoneproject;

import java.util.ArrayList;

public class CustomerAccount implements Input {

	private int accNum;
	private static int firstAccNum = 1001;
	private String firstName;
	private String lastName;
	private String address;
	private ArrayList<Policy> policy;
	private ArrayList<PolicyHolder> policyHolderList;
	private static ArrayList<CustomerAccount> accountList = new ArrayList<CustomerAccount>();

	public CustomerAccount(int accNum, String firstName, String lastName, String address) {
		this.accNum = accNum;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.policy = new ArrayList<Policy>(); // construct a new ArrayList inside Customer Account constructor
		this.policyHolderList = new ArrayList<PolicyHolder>();
		firstAccNum++;
		System.out.println("Account successfully created. Please take note of your account number: " + accNum);
	}

	public CustomerAccount() {
	}

	// ******************************
	// Public methods

	public void accountCreator() {
		System.out.println("\nYou chose: Create a new customer account.");
		if (firstAccNum == 10000) {
			System.out.println(
					"\nMax account number of account is created. Cannot add a new customer account. Returning to Main Menu.");
			return;
		}
		System.out.print("Please input First name: ");
		String tempFirst = nameStringInputChecker(input.nextLine());
		System.out.print("Please input Lastname: ");
		String tempLast = nameStringInputChecker(input.nextLine());
		System.out.print("Please input Address: ");
		String tempAddress = addressEntry();

		System.out.print("Proceed creating a new Customer Account? (y/n): ");
		String choice = ynChoiceInput(input.nextLine());
		if (choice.equals("y")) {

			accountList.add(new CustomerAccount(firstAccNum, tempFirst, tempLast, tempAddress));

		} else if (choice.equals("n")) {
			System.out.println("All details were not saved. Returning to Main Menu.");
		}
	}

	public CustomerAccount getAccObjByAccNum(int accNum) {
		CustomerAccount accObj = null;
		try {
			accObj = accountList.stream().filter(e -> e.getAccNum() == accNum).findAny().get();
			System.out.println("Match Found!");
			return accObj;
		} catch (Exception e2) {
			System.out.println("No Account exist with the entered account number.");
			return accObj;
		}
	}

	public CustomerAccount getAccObjByName(String firstName, String lastName) {
		try {//@formatter:off
			return accountList.stream().filter(
						e -> lastName.equalsIgnoreCase(e.getLastName()) && firstName.equalsIgnoreCase(e.getFirstName())
					).findFirst().get();
			//@formatter:on
		} catch (Exception ex) {
			System.out.println("No account exist with the provided First Name and Lastname");
			return null;
		}
	}

	public void displaySingleAccountDetails(CustomerAccount accObj) { //@formatter:off

		if (accObj.getPolicyHolderList().isEmpty()) {
			System.out.printf("%-20s%-19s%-20s%n", 
					"Account Number", 
					"First Name", 
					"Last Name");
			System.out.printf("%10d%19s%19s%n", 
					accObj.getAccNum(), 
					accObj.getFirstName(), 
					accObj.getLastName());
		} else {
			System.out.printf("%-20s%-19s%-20s%-20s%n", 
					"Account Number", 
					"First Name", 
					"Last Name", 
					"Policy Holder");
			//for loop to get and print each of the policy holder details in the customer account object
			for (int i = 0; i < accObj.getPolicyHolderList().size(); i++) {
				String phFirstName = accObj.getPolicyHolderList().get(i).getFirstName();
				String phLastName = accObj.getPolicyHolderList().get(i).getLastName();
				String phFullName = phFirstName + " " + phLastName;
				System.out.printf("%10d%19s%19s%24s%n", 
						accObj.getAccNum(), 
						accObj.getFirstName(), 
						accObj.getLastName(), 
						phFullName);
			} // end of for loop

		}

	} 
	//@formatter:on

	public void addPolicy(Policy policyToAdd, int index) {
		accountList.get(index).getPolicyArray().add(policyToAdd);
	}

	public Policy getPolicy(int accNumIndex, int policyIndex) {
		return accountList.get(accNumIndex).policy.get(policyIndex);
	}

	public Policy getPolicyByIndex(int[] accountIndexes) {

		int accNumIndex = accountIndexes[0];
		int policyIndex = accountIndexes[1];
		return accountList.get(accNumIndex).policy.get(policyIndex);
	}

	public boolean accountExist() {
		try {
			// if ((accountList.stream().findFirst().get().getPolicyArray().isEmpty())) { //
			// checks if policy account already exists. avoids array out of bounds
			// exception.
			if ((accountList.stream().findFirst().isEmpty())) {
				System.out.println("No existing account. Returning to Main Menu");
				return false;
			} else {
				// System.out.println("Please see list of Customer Account");
				// displayAccountDetails();
				return true;
			}

		} catch (Exception e) {
			System.out.println("No existing customer account. Cannot select this menu option.");
			System.out.println("Returning to Main Menu");
			return false;
		}

	}

	// ******************************
	// getters and setter methods

	public static ArrayList<CustomerAccount> getAccountList() {
		return accountList;
	}

	public int getAccNum() {
		return accNum;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public ArrayList<Policy> getPolicyArray() {
		return policy;
	}

	public ArrayList<PolicyHolder> getPolicyHolderList() {
		return policyHolderList;
	}
	// ****************************** end of getter and setter methdods

	@Override
	public String toString() {
		return "CustomerAccount [accNum=" + accNum + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + "]";
	}

}
