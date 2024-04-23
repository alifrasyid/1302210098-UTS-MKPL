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
	
	public void setMonthlySalary(int grade) {	
		if (grade == 1) {
			salaryInfo.setMonthlySalary(3000000);
			if (isForeigner) {
				salaryInfo.setMonthlySalary((int) (3000000 * 1.5));
			}
		}else if (grade == 2) {
			salaryInfo.setMonthlySalary(5000000);
			if (isForeigner) {
				salaryInfo.setMonthlySalary((int) (3000000 * 1.5));
			}
		}else if (grade == 3) {
			salaryInfo.setMonthlySalary(7000000);
			if (isForeigner) {
				salaryInfo.setMonthlySalary((int) (3000000 * 1.5));
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
		
		//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
		LocalDate date = LocalDate.now();
		
		if (date.getYear() == dateJoined.getYear()) {
			monthWorkingInYear = date.getMonthValue() - dateJoined.getMonthValue();
		}else {
			monthWorkingInYear = 12;
		}
		
		boolean hasSpouseId = getIdNumber() != null && !getIdNumber().isEmpty();
		return TaxFunction.calculateTax(salaryInfo, monthWorkingInYear, hasSpouseId, childIdNumbers.size());
	}
}
