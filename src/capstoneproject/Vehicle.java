package capstoneproject;

public class Vehicle extends Policy {
	private String make;
	private String model;
	private String color;
	private String type;
	private String fuelType;
	private String plateNumber;
	private double purchasePrice;
	private int year;
	private double premium;
	private static String[] typeChoice = { "4-door sedan", "2-door sports car", "SUV", "truck" };
	private static String[] fuelTypeChoice = { "Diesel", "Electric", "Petrol" };

	public Vehicle() {
	}

	public Vehicle(String make, String model, int year, String type, String fuelType, String plateNumber,
			double purchasePrice, String color, double premium) {

		this.make = make;
		this.model = model;
		this.year = year;
		this.type = type;
		this.fuelType = fuelType;
		this.plateNumber = plateNumber;
		this.purchasePrice = purchasePrice;
		this.color = color;
		this.premium = premium;
	}

	public Vehicle newVehicleConstructor(String make, String model, int year, String type, String fuelType,
			String plateNumber, double purchasePrice, String color, double premium) {

		return new Vehicle(make, model, year, type, fuelType, plateNumber, purchasePrice, color, premium);
	}

	// checks if the passed plate number already exist in one of the vehicle object
	//
	public Boolean AddPolicyAndVehicleCheckExist(String plateNumber) {//@formatter:off
		//fix the logic to check if vehicle is already added in an active policy
		if (plateNumber == "") {
			return true;
		}
		do {
			if (plateNumber.length()<6 || plateNumber.length()>10) {
				System.out.println("Invalid Input.");
				System.out.print("Please enter an alpha-numeric plate number (6-10 digits): ");
				plateNumber = input.nextLine();
				continue;
			}else {
				break;
			}
		}while(true);
		String validPlateNumber = plateNumber;
		
		try {	
			Vehicle sampleVehicle = getAccountList().stream().flatMap(
										a-> a.getPolicyArray().stream().flatMap(
											b -> b.getVehicleObjArrayList().stream().
												filter(c-> 	{
													return c.getPlateNumber().equals(validPlateNumber);
												})// filter
											)// inner flatmap
										)// outer flatmap
									.findFirst().get(); //returns vehicle object with that matched the plate number
			
			//@formatter:on
			System.out.println(sampleVehicle.getPlateNumber());
			//@formatter:off
			Policy policyObjContainsVehicle = getAccountList().stream().
			flatMap(a -> a.getPolicyArray().stream().
			filter(b -> b.getVehicleObjArrayList().contains(sampleVehicle))).findFirst().get();
			//@formatter:on
			if (policyObjContainsVehicle.getStatus().equals("Active")) {
				System.out.println("This Vehicle is already added in a policy.");
				return true;

			} else if (policyObjContainsVehicle.getStatus().equals("Inactive")) {
				System.out.println("This vehicle is already added in an \"Inactive Policy\". Cannot add this vehicle.");
				return true;

			} else if (policyObjContainsVehicle.getStatus().equals("Expired")) {
				System.out.println(
						"Reminder: This vehicle is already added in an \"Expired Policy\". You can proceed to enter the same vehicle in this new Policy.");
				return false;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println("No existing vehicle with plate number. Please proceed to enter details.");
			return false;
		}
		//@formatter:on
	}

	public Boolean AddVehicleOnlyCheckExist(String plateNumber) {//@formatter:on
		// fix the logic to check if vehicle is already added in an active policy
		if (plateNumber == "") {
			return true;
		}
		do {
			if (plateNumber.length() < 6 || plateNumber.length() > 10) {
				System.out.println("Invalid Input.");
				System.out.print("Please enter an alpha-numeric plate number (6-10 digits): ");
				plateNumber = input.nextLine();
				continue;
			} else {
				break;
			}
		} while (true);
		String validPlateNumber = plateNumber;
		try {//@formatter:off

			Vehicle sampleVehicle = getAccountList().stream().flatMap(
					a-> a.getPolicyArray().stream().flatMap(
							b -> b.getVehicleObjArrayList().stream().filter(
									c-> c.getPlateNumber().equalsIgnoreCase(validPlateNumber)
									) // policy - vehicle filter. filters vehicle object that matches the platenumber
						) //account - policy flatMap
					).findFirst().get(); //returns vehicle object with that matched the plate number
			//@formatter:on
			System.out.println(sampleVehicle.getPlateNumber());
			//@formatter:off
			Policy policyObjContainsVehicle = getAccountList().stream().flatMap(a -> 
			a.getPolicyArray().stream().filter(b -> 
			b.getVehicleObjArrayList().contains(sampleVehicle))).findFirst().get();
			//@formatter:on
			if (policyObjContainsVehicle.getStatus().equals("Active")) {
				System.out.println("This Vehicle is already added in a policy.");
				return true;

			} else if (policyObjContainsVehicle.getStatus().equals("Inactive")) {
				System.out.println("This vehicle is already added in an \"Inactive Policy\". Cannot add this vehicle.");
				return true;

			} else if (policyObjContainsVehicle.getStatus().equals("Expired")) {
				System.out.println(
						"This vehicle is already added in an \"Expired Policy\". Add a new policy to add this vehicle.");
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println("No existing vehicle with plate number. Please proceed to enter details.");
			return false;
		}
		//@formatter:on
	}

	public String vehicleTypeChoice() {
		do {
			for (int i = 0; i < typeChoice.length; i++) {
				System.out.println((i + 1) + ") " + typeChoice[i]);
			}
			System.out.print("Please enter your choice: ");
			int vehicleTypeChoice = intEntry("");
			switch (vehicleTypeChoice) {
				case 1:
					return typeChoice[0];
				case 2:
					return typeChoice[1];
				case 3:
					return typeChoice[2];
				case 4:
					return typeChoice[3];

				default:
					System.out.println("Entered value is not in the choice. Please try again. ");
					continue;
			}

		} while (true);
	}

	public String fuelTypeChoice() {
		do {
			for (int i = 0; i < fuelTypeChoice.length; i++) {
				System.out.println((i + 1) + ") " + fuelTypeChoice[i]);
			}
			System.out.print("Please enter your choice: ");
			int fuelChoice = intEntry("");
			switch (fuelChoice) {
				case 1:
					return fuelTypeChoice[0];
				case 2:
					return fuelTypeChoice[1];
				case 3:
					return fuelTypeChoice[2];
				default:
					System.out.println("Entered value is not in the choice. Please try again. ");
					continue;
			}

		} while (true);
	}

	// ******************************
	// getters and setter methods
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getPremium() {
		return premium;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	// ******************************
	// end of getters and setter methods

}
