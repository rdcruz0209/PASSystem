package capstoneproject;

import java.time.LocalDate;
import java.time.Period;

public class PolicyHolder extends Policy {
	private String firstName;
	private String lastName;
	private LocalDate birthDay;
	private String Address;
	private String licenseNumber;
	private LocalDate licenseIssueDate;

	public PolicyHolder() {

	}

	public PolicyHolder(String firstName, String lastName, LocalDate birthDay, String address, String licenseNumber,
			LocalDate licenseIssueDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
		this.Address = address;
		this.licenseNumber = licenseNumber;
		this.licenseIssueDate = licenseIssueDate;
	}

	public PolicyHolder createNewPolicyHolderObj(String firstName, String lastName, LocalDate birthDay, String Address,
			String licenseNumber, LocalDate licenseIssueDate) {

		return new PolicyHolder(firstName, lastName, birthDay, Address, licenseNumber, licenseIssueDate);
	}

	public Boolean verifyHolderAge(LocalDate birthDay) {
		if (Period.between(birthDay, LocalDate.now()).getYears() < 16) {
			System.out.println(
					"Policy Holder is not of eligible age (at least 16 years old). Cannot add him/her as policy holder.");
			return true;
		} else {
			System.out.println("Policy Holder age is of allowable age. Please continue.");
			return false;
		}
	}

	// ******************************
	// getter and setter methods

	public LocalDate getLicenseIssueDate() {
		return licenseIssueDate;
	}

	public void setLicenseIssueDate(LocalDate licenseIssueDate) {
		this.licenseIssueDate = licenseIssueDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public String getAddress() {
		return Address;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	// ******************************
	// end of getters and setter methods

}
