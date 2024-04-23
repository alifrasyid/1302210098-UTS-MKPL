package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class Employee extends EmployeeData{

	private enum Gender{
		Male,
		Female,
	}

	private enum Grade {
        GRADE_ONE,
        GRADE_TWO,
        GRADE_THREE
    }
	
	private LocalDate dateJoined;
	private int monthWorkingInYear;
	
	private boolean isForeigner;
	private Gender gender;

	private SalaryInfo salaryInfo;
	
	private String spouseName;

	private List<String> childNames;
	private List<String> childIdNumbers;
	
	public Employee(EmployeeData employeeData, LocalDate dateJoined, boolean isForeigner, Gender gender) {
		this.setEmployeeId(employeeData.getEmployeeId());;
		this.setFirstName(employeeData.getFirstName());;
		this.setLastName(employeeData.getLastName());;
		this.setIdNumber(employeeData.getIdNumber());;
		this.setAddress(employeeData.getAddress());;
		this.dateJoined = dateJoined;
		this.isForeigner = isForeigner;
		this.gender = gender;
		
		childNames = new LinkedList<String>();
		childIdNumbers = new LinkedList<String>();
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */

	private final int SalaryGrade__one = 3000000;
	private final int SalaryGrade__Two = 5000000;
	private final int SalaryGrade__Three = 7000000;
	private final double SalaryForeigner = 0.5;
	
	public void setMonthlySalary(Grade grade) {	
		if (grade == Grade.GRADE_ONE) {
			salaryInfo.setMonthlySalary(SalaryGrade__one);
			if (isForeigner) {
				salaryInfo.setMonthlySalary((int) (SalaryGrade__one * SalaryForeigner));
			}
		}else if (grade == Grade.GRADE_TWO) {
			salaryInfo.setMonthlySalary(SalaryGrade__Two);
			if (isForeigner) {
				salaryInfo.setMonthlySalary((int) (SalaryGrade__Two * SalaryForeigner));
			}
		}else if (grade == Grade.GRADE_THREE) {
			salaryInfo.setMonthlySalary(SalaryGrade__Three);
			if (isForeigner) {
				salaryInfo.setMonthlySalary((int) (SalaryGrade__Three * SalaryForeigner));
			}
		}
	}
	
	public void setSpouse(String spouseName, IdNumber spouseIdNumber) {
		this.spouseName = spouseName;
		this.setIdNumber(spouseIdNumber.getIdNumber());
	}
	
	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}
	
	public int getAnnualIncomeTax() {
		
		final int WorkPreYear = 12;
		LocalDate date = LocalDate.now();
		
		if (date.getYear() == dateJoined.getYear()) {
			monthWorkingInYear = date.getMonthValue() - dateJoined.getMonthValue();
		}else {
			monthWorkingInYear = WorkPreYear;
		}
		
		boolean hasSpouseId = getIdNumber() != null && !getIdNumber().isEmpty();
		return TaxFunction.calculateTax(salaryInfo, monthWorkingInYear, hasSpouseId, childIdNumbers.size());
	}
}
