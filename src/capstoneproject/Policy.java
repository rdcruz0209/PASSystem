package capstoneproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Policy extends CustomerAccount { // use composition to compose policy Holder vehicle

	private static int firstPolicyNumber = 100001;
	private int policyNumber;
	private LocalDate effectiveDate;
	private String status;
	private LocalDate expirationDate;
	private PolicyHolder policyHolderObj;
	private ArrayList<Vehicle> vehicleObjList; // arrayList of vehicle
	private ArrayList<Claim> claimList;
	private double premium;

	public Policy() { // empty constructor for policy driver
	}

	public Policy(LocalDate effectiveDate, LocalDate expirationDate, PolicyHolder policyHolderObj,
			ArrayList<Vehicle> vehicleObjList, double premium) {
		this.policyNumber = firstPolicyNumber++;
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;
		this.policyHolderObj = policyHolderObj;
		this.vehicleObjList = vehicleObjList;
		this.claimList = new ArrayList<Claim>();
		this.premium = premium;
	}

	public Policy createNewPolicyObject(LocalDate effectiveDate, LocalDate expirationDate, PolicyHolder policyHolderObj,
			ArrayList<Vehicle> vehicleObjList, double premium) {

		return new Policy(effectiveDate, expirationDate, policyHolderObj, vehicleObjList, premium);
	}

	public String[] policyDetailsString(int accIndex, int policyIndex) { //@formatter:off
		
		CustomerAccount targetAccObj = getAccountList().get(accIndex);
		Policy targetPolicyObj = getAccountList().get(accIndex).getPolicyArray().get(policyIndex);
		PolicyHolder targetHolderObj = getAccountList().get(accIndex).getPolicyArray().get(policyIndex).getPolicyHolderObj();

		String accNum = Integer.toString(targetAccObj.getAccNum());
		String accountOwner = targetAccObj.getFirstName() + " " + targetAccObj.getLastName();
		String policyNum = Integer.toString(targetPolicyObj.getPolicyNumber());
		String policyHolder = targetHolderObj.getFirstName() + " " + targetHolderObj.getLastName();
		String effectiveDate = targetPolicyObj.getEffectiveDate().toString();
		String expirationDate = targetPolicyObj.getExpirationDate().toString();
		String status = targetPolicyObj.getStatus();

		String[] policyDetails = new String[7];
		policyDetails[0] = accNum;
		policyDetails[1] = accountOwner;
		policyDetails[2] = policyNum;
		policyDetails[3] = policyHolder;
		policyDetails[4] = effectiveDate;
		policyDetails[5] = expirationDate;
		policyDetails[6] = status;

		return policyDetails;

	} //@formatter:on

	public void displayPolicyObjDetails(Policy policyObj) { //@formatter:off
		CustomerAccount targetAcc = getAccObjByPolicyObj(policyObj);
		int accountNumber = targetAcc.getAccNum();
		String accOwnerName = targetAcc.getFirstName() + " " + targetAcc.getLastName();
		int policyNumber = policyObj.getPolicyNumber();
		String holderName = policyObj.getPolicyHolderObj().getFirstName() + " " + policyObj.getPolicyHolderObj().getLastName();
		String premium = Double.toString(policyObj.getPremium());
		String status = policyObj.getStatus();

		System.out.printf("%-20s%-25s%-18s%-25s%-15s%-17s%n", 
				"Account Number", 
				"Account Owner", 
				"Policy Number", 
				"Policy Holder", 
				"Premium", 
				"Policy Status");
		
		System.out.printf("%-20s%-25s%-18s%-25s%-15s%-17s%n", 
				accountNumber,
				accOwnerName, // account owner full name
				policyNumber, 
				holderName, 
				premium, 
				status); // policy status
	}
	
	public CustomerAccount getAccObjByPolicyObj(Policy PolicyObj) {//@formatter:off
		return getAccountList().stream().filter(
				account -> account.getPolicyArray().contains(PolicyObj)
				).findAny().get();
	}

	public Policy getPolicyObjByPolicyNum(int policyNum) {
		int[] accArrayIndex = getAccAndPolicyIndexByPolicyNum(policyNum);
		int accIndex = accArrayIndex[0];
		int policyIndex = accArrayIndex[1];

		Policy policyObj = getAccountList().get(accIndex).getPolicyArray().get(policyIndex);
		return policyObj;
	}

	// this method will be used to return index of policy using policy number
	// so that vehicle can be added to arraylist of that policy object
	public int getPolicyIndexByPolicyNum(int policyNum) {//@formatter:on

		int policyIndex = -1;
		while (policyIndex == -1) {
			for (int k = 0; k < getAccountList().size(); k++) {
				for (int j = 0; j < getAccountList().get(k).getPolicyArray().size(); j++) {
					if (getPolicy(k, j).policyNumber == policyNum) {
						policyIndex = j;
						System.out.println("Match Found! ");
						return policyIndex;
					}
				}
			}
			if (policyIndex == -1) {
				System.out.println("No match found!");
				break;
			}
		}
		return policyIndex;
	}

	// this method will be used to add vehicle using Policy Number only as input.
	public int[] getAccAndPolicyIndexByPolicyNum(int policyNum) {

		int[] indexArray = new int[2];

		int policyIndex = -1;
		while (policyIndex == -1) {
			for (int k = 0; k < getAccountList().size(); k++) {
				for (int j = 0; j < getAccountList().get(k).getPolicyArray().size(); j++) {
					if (getPolicy(k, j).policyNumber == policyNum) {
						policyIndex = j;
						indexArray[0] = k;
						indexArray[1] = j;
						return indexArray;
					}
				}
			}
			if (policyIndex == -1) {
				System.out.println("No match found!");
				policyNum = intEntry("");
				continue;
			}
		}
		return indexArray;
	}

	public int getAccIndexByPolicyNum(int policyNum) {

		int accIndex = -1;
		while (accIndex == -1) {
			for (int k = 0; k < getAccountList().size(); k++) {
				for (int j = 0; j < getAccountList().get(k).getPolicyArray().size(); j++) {
					if (getPolicy(k, j).policyNumber == policyNum) {
						accIndex = k;
						return accIndex;
					}
				}
			}
			if (accIndex == -1) {
				System.out.println("No match found!");
				policyNum = intEntry("");
				continue;
			}
		} // - while (accIndex == -1)
		return accIndex;
	}

	public void policyExist(int policyNum) {
		while (true) {
			int policyIndex = getPolicyIndexByPolicyNum(policyNum);
			if (policyIndex == -1) {
				System.out.println("No existing Policy with the entered policy number.");
				policyNum = intEntry("");
				continue;
			}
			break;
		}
	}

	public boolean policyNumberExist(int policyNum) {
		int policyIndex = getPolicyIndexByPolicyNum(policyNum);
		if (policyIndex == -1) {
			System.out.println("No existing Policy with the entered policy number.");
			return false;
		} else {
			return true;
		}
	}

	public boolean accountAndPolicyExist() { // check if any policy exist

		try {
			// @formatter:off checks if policy account already exists. avoids array out of bounds exception.
			boolean accountExist = (getAccountList().stream().findFirst().isEmpty()); // check if Acc arraylist is empty
			if (accountExist) {
				System.out.println("No existing customer account. Returning to Main Menu");
				return false;
			} else {
				try {
					ArrayList<Policy> policyExist = new ArrayList<Policy>();
					Policy policyObj = getAccountList().stream().flatMap(
							p -> p.getPolicyArray().stream().filter(
									policyNum -> policyNum.getPolicyNumber() > 10000)
							).findFirst().get();
					policyExist.add(policyObj);
					boolean isPolicyNotExist = policyExist.isEmpty();

					if (isPolicyNotExist) {
						System.out.println("\nNo Existing Policy");
						return false;
					} else {
						return true;
					}
				} catch (Exception e) {
					System.out.println("\nNo Existing Policy");
					return false;
				}
			}

		} catch (Exception e) {
			System.out.println("No existing customer account. Cannot select this menu option.");
			System.out.println("Returning to Main Menu");
			return false;
		}
	}

	public void policyStatusUpdater() { // boolean for equal

		for (int i = 0; i < getAccountList().size(); i++) {
			for (int j = 0; j < getAccountList().get(i).getPolicyArray().size(); j++) {
				Policy targetPolicyObj = getAccountList().get(i).getPolicyArray().get(j);
				LocalDate policyEffDate = getAccountList().get(i).getPolicyArray().get(j).getEffectiveDate();
				LocalDate policyExpDate = getAccountList().get(i).getPolicyArray().get(j).getExpirationDate();
				
				Boolean isEqualEffDate = policyEffDate.isEqual(LocalDate.now());
				Boolean isAfterEffDate = policyEffDate.isAfter(LocalDate.now());
				Boolean isEqualExpDate = policyExpDate.isEqual(LocalDate.now());
				Boolean isBeforeExpDate = LocalDate.now().isBefore(policyExpDate);
				Boolean isActive = (isEqualEffDate||isAfterEffDate) && (isBeforeExpDate||isEqualExpDate);
				
				if (LocalDate.now().isBefore(policyEffDate)) {
					targetPolicyObj.setStatus("Inactive");
				}
				if (isActive) {
					targetPolicyObj.setStatus("Active");
				}
				if (LocalDate.now().isAfter(policyEffDate) && LocalDate.now().isAfter(policyExpDate)) {
					targetPolicyObj.setStatus("Expired");
				}

			} // inner for loop - iterate over all policy object in an account object then perform if statements for status update

		}// outer for loop - iterate over all account objects

	} // end of method

	public Boolean isValidClaimDate(Policy targetPolicyObj, LocalDate accidentDate) {
		Boolean isBeforeEqual =accidentDate.isAfter(targetPolicyObj.getEffectiveDate()) // before or equal
				|| accidentDate.isEqual(targetPolicyObj.getEffectiveDate())  ; 
		
		Boolean isAfterEqual = accidentDate.isBefore(targetPolicyObj.getExpirationDate()) //after or equal
				|| accidentDate.isEqual(targetPolicyObj.getExpirationDate());

		if (isBeforeEqual&&isAfterEqual) {
			return true;
		}else {
			return false;
		}
	}
	
	// ******************************
	// getters and setter methods

	public int getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(int policyNumber) {
		this.policyNumber = policyNumber;
	}

	public static int getFirstPolicyNumber() {
		return firstPolicyNumber;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public ArrayList<Vehicle> getVehicleObjArrayList() {
		return vehicleObjList;
	}

	public PolicyHolder getPolicyHolderObj() {
		return policyHolderObj;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public ArrayList<Claim> getClaimList() {
		return claimList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	// ******************************
		// end of getters and setter methods

}
