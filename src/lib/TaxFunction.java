package lib;

public class TaxFunction {
	
	private static final double countTax = 0.05;
	private static final int noMarried = 54000000;
	private static final int Married = 4500000;

	public static int calculateTax(SalaryInfo salaryInfo, int numberOfMonthWorking, boolean isMarried, int numberOfChildren) {
		
		int tax = 0;
		
		if (numberOfMonthWorking > 12) {
			System.err.println("More than 12 month working per year");
		}
		
		if (numberOfChildren > 3) {
			numberOfChildren = 3;
		}
		
		if (isMarried) {
			tax = (int) Math.round(countTax * (((salaryInfo.getMonthlySalary() + salaryInfo.getOtherMonthlyIncome()) * numberOfMonthWorking) - salaryInfo.getAnnualDeductible() - (noMarried + Married + (numberOfChildren * Married))));
		}else {
			tax = (int) Math.round(countTax * (((salaryInfo.getMonthlySalary() + salaryInfo.getOtherMonthlyIncome()) * numberOfMonthWorking) - salaryInfo.getAnnualDeductible() - noMarried));
		}
		
		if (tax < 0) {
			return 0;
		}else {
			return tax;
		}
			 
	}
	
}
