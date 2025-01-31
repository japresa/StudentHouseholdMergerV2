package main;

import java.util.List;

import datastructures.Student;
import datastructures.Household;
import service.ExcelReader;
import service.ExcelWRiter;
import service.ReportMerger;
import service.ExcelWRiter;

public class Main {

	public static void main(String[] args) {
		String studentReportPath = "data/student_report.xlsx";
		String householdReportPath = "data/household_report.xlsx";
		String outputReportPath = "data/final_report.xlsx";
		String cityCountyLinkerPath = "data/cityCountyLinker.xlsx";

		boolean runCityToCountyDecoder = true;
		
		// tries to read reports and merge data, will send message if failed
		try {
			// Step 1: Read student data
			List<Student> students = ExcelReader.readStudentReport(studentReportPath);
			System.out.println("Finished reading the Student Report *****");

			// Step 2: Read household data
			List<Household> households = ExcelReader.readHouseholdReport(householdReportPath);
			System.out.println("Finished reading the Household Report *****");

			// Step 3: Merge household data into student records
			ReportMerger.mergeHouseholdData(students, households);
			System.out.println("Finished merging Student and Household data *****");

			// Step 4: Write the final merged report
			ExcelWRiter.writeFinalReport(outputReportPath, students);
			System.out.println("Final report generated successfully at: " + outputReportPath);

			//step 5: city to county mapping by reading final report
			if (runCityToCountyDecoder) {
				ExcelWRiter.decodeCityToCountiesForFinalReport(outputReportPath, cityCountyLinkerPath);
				System.out.println("updated final report with county data *****");
			}
			
		} catch (Exception e) {
			System.err.println(" ERROR: " + e.getMessage());
		}
	}
}
