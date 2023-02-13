package capstoneproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class Claim {
	private static int firstClaimNumber = 10001;
	private String claimNumber;
	private LocalDate accDate;
	private String accAddress;
	private String accDesc;
	private String dmgDesc;
	private double repairCostEstimate;
	private static ArrayList<Claim> allClaimsList = new ArrayList<Claim>();

	public Claim(LocalDate accDate, String accAddress, String accDesc, String dmgDesc, double repairCostEstimate) {
		this.claimNumber = "C" + Integer.toString(firstClaimNumber++);
		this.accDate = accDate;
		this.accAddress = accAddress;
		this.accDesc = accDesc;
		this.dmgDesc = dmgDesc;
		this.repairCostEstimate = repairCostEstimate;
	}

	public Claim() {
	}

	public Claim claimCreator(LocalDate accDate, String accAddress, String accDesc, String dmgDesc,
			double repairCostEstimate) {

		return new Claim(accDate, accAddress, accDesc, dmgDesc, repairCostEstimate);
	}

	public void claimDisplay(String claimNum) {
		String claimNumber;
		LocalDate accDateDisplay;
		String accAddress;
		String accDesc;
		String dmgDescDisplay;
		double repairCostDisplay;
		Claim targetClaimObj;

		//@formatter:off
		
		//nested stream flatmap and filter to obtain the claim object that matches the claimNum that is passed in the method
		try { 
			targetClaimObj = CustomerAccount.getAccountList().stream().flatMap(
					
					account -> account.getPolicyArray().stream().flatMap(
							
							policy ->  policy.getClaimList().stream().filter(
									
									claim -> claim.getClaimNumber().equals(claimNum)
									)
							)
					).findAny().get();

			claimNumber = targetClaimObj.getClaimNumber();
			accDateDisplay = targetClaimObj.getAccDate();
			accAddress = targetClaimObj.getAccAddress();
			accDesc = targetClaimObj.getAccDesc();
			dmgDescDisplay = targetClaimObj.getDmgDesc();
			repairCostDisplay = targetClaimObj.getRepairCostEstimate();

			System.out.println("Details for claim number: " + claimNumber);
			System.out.println("--------------------------------------");
			System.out.println("Claim Number: " + claimNumber
								+ "\nAccident Date: " + accDateDisplay 
								+ "\nAccident Address: " + accAddress 
								+ "\nAccident Description: " + accDesc 
								+ "\nDamage Description: " + dmgDescDisplay 
								+ "\nRepair Cost Estimate: " + repairCostDisplay);
		} catch (Exception e) {
			System.out.println("No existing claim. Please register a claim before searching for a claim.");
			return;
		}
	}

	public Boolean claimNumberExist(String claimNumber) {
//@formatter:off
		try {
			boolean claimExist = allClaimsList.contains(allClaimsList.stream().filter(
								f -> claimNumber.equalsIgnoreCase(f.getClaimNumber())
							).findFirst().get()
					);

			if (claimNumber == "") {
				return false;

			} else if (claimExist) {
				System.out.println("Claim found!");
				return true;

			} else {
				System.out.println("No Claim found with the entered Claim Number.");
				return false;
			}

		} catch (Exception e) {
			System.out.println("No Claim found with the entered Claim Number.");
			return false;
		}

	}
	//@formatter:on

	// ******************************
	// getters and setter methods

	public static void addNewClaimObjtoStaticClaimList(Claim claimObj) {
		allClaimsList.add(claimObj);
	}

	public String getClaimNumber() {
		return claimNumber;
	}

	public LocalDate getAccDate() {
		return accDate;
	}

	public void setAccDate(LocalDate accDate) {
		this.accDate = accDate;
	}

	public String getAccAddress() {
		return accAddress;
	}

	public void setAccAddress(String accAddress) {
		this.accAddress = accAddress;
	}

	public String getAccDesc() {
		return accDesc;
	}

	public void setAccDesc(String accDesc) {
		this.accDesc = accDesc;
	}

	public String getDmgDesc() {
		return dmgDesc;
	}

	public void setDmgDesc(String dmgDesc) {
		this.dmgDesc = dmgDesc;
	}

	public double getRepairCostEstimate() {
		return repairCostEstimate;
	}

	public void setRepairCostEstimate(double repairCostEstimate) {
		this.repairCostEstimate = repairCostEstimate;
	}

	public static ArrayList<Claim> getAllClaimsList() {
		return allClaimsList;
	}

	// ******************************
	// end of getters setters methods

}// end of class
