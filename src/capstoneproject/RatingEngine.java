package capstoneproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class RatingEngine extends Policy {

	private double vp; //vehicle purchase price
	private double vpf; // vehicle price factor
	LocalDate dlx; // drivers license first issue date
	private double premium;

	public double policyRating(int policyNumber) {
		double policyRating = 0;
		ArrayList<Vehicle> vehicleList;
		vehicleList = getPolicyObjByPolicyNum(policyNumber).getVehicleObjArrayList();

		// use for loop to go through all the vehicle enrolled in the policy
		for (int i = 0; i < vehicleList.size(); i++) {
			double pricePerVehicle = vehicleList.get(i).getPremium();
			policyRating += pricePerVehicle;
		}
		return policyRating;
	}

	public double singleVehicleRating(double vpInput, int modelYear, LocalDate dlxYearInput) {
		this.vp = vpInput;
		this.dlx = dlxYearInput;

		this.vpf = priceFactorSelector(modelYear);
		int dlxYear = LocalDate.now().getYear() - dlx.getYear();
		if (dlxYear == 0) {
			dlxYear = 1;
		}
		this.premium = (vp * vpf) + ((vp / 100) / dlxYear);

		return premium;
	}

	public double priceFactorSelector(int modelYear) {
		double vpf = -1;

		int age = LocalDate.now().getYear() - modelYear;
		if (age <= 1) {
			vpf = 0.01;
		} else if (age > 1 & age <= 3) {
			vpf = 0.008;

		} else if (age > 3 & age <= 5) {
			vpf = 0.007;

		} else if (age > 5 & age <= 10) {
			vpf = 0.006;

		} else if (age > 10 & age <= 15) {
			vpf = 0.004;

		} else if (age > 15 & age <= 20) {
			vpf = 0.002;

		} else if (age > 20 & age <= 40) {
			vpf = 0.007;

		} else {
			System.out.println("cannot enroll vehicle if age is more than 40 years"); // restrict addition of vehicle

		}
		return vpf;
	}

}
