package service;

import datastructures.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelWRiter {

	public static void writeFinalReport(String filePath, List<Student> students) {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Final Report");

			// Create Header Row
			Row header = sheet.createRow(0);
			String[] columns = { "Student's First Name", "Student's Last Name", "Zip Code", "City",
					"School or School District", "Student's Class Year/Grad Year", "Household Name & Household #",
					"Household Number", "Number of Students in Household", // 🔹 Added column
					"Number of Parents in Household", "Number of Siblings in Household",
					"Number of Alumni in Household" };

			for (int i = 0; i < columns.length; i++) {
				header.createCell(i).setCellValue(columns[i]);
				sheet.setColumnWidth(i, 6000);
			}

			// Write Student Data + Household Data
			int rowIndex = 1;
			for (Student student : students) {
				Row row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(student.getFirstName());
				row.createCell(1).setCellValue(student.getLastName());
				row.createCell(2).setCellValue(student.getMailingZip());
				row.createCell(3).setCellValue(student.getCity());
				row.createCell(4).setCellValue(student.getSchoolDistrict());
				row.createCell(5).setCellValue(student.getClassOf());
				row.createCell(6).setCellValue(student.getStudentFamilyNameAndID());
				row.createCell(7).setCellValue(student.getFamilyID());
				row.createCell(8).setCellValue(student.getStudentCount()); // 🔹 Added field
				row.createCell(9).setCellValue(student.getParentCount());
				row.createCell(10).setCellValue(student.getSiblingCount());
				row.createCell(11).setCellValue(student.getAlumniCount());
			}

			FileOutputStream fos = new FileOutputStream(new File(filePath));
			workbook.write(fos);
			fos.close();

			System.out.println("Final report successfully written to " + filePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	/**
	 * method that reads from an excel sheet that has county and city data connected and pushes that data to the finaldraft and requests for cities to be grouped if not grouped
	 * @param finalReportPath
	 * @param cityCountyLinkerPath
	 */
	public static void decodeCityToCountiesForFinalReport(String finalReportPath, String cityCountyLinkerPath) {
	    try (FileInputStream fis = new FileInputStream(new File(finalReportPath));
	         Workbook workbook = new XSSFWorkbook(fis)) {

	        Sheet sheet = workbook.getSheetAt(0);
	        int lastColumn = sheet.getRow(0).getLastCellNum();
	        boolean countyColumnExists = false;

	        // Check if County column exists and create it if missing
	        if (sheet.getRow(0).getCell(lastColumn - 1).getStringCellValue().equalsIgnoreCase("County")) {
	            countyColumnExists = true;
	        }
	        if (!countyColumnExists) {
	            sheet.getRow(0).createCell(lastColumn).setCellValue("County");
	        }

	        // Read dictionary map: my City-to-County excel data into working list
	        List<List<String>> cityCountyList = ExcelReader.readCityToCountyMapping(cityCountyLinkerPath);
	        List<String> missingCities = new ArrayList<>();

	        // Step 3: Loop through each student in the final report and map their city to a county
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Start from row 1 (skip headers)
	            Row row = sheet.getRow(i);
	            if (row == null) continue;

	            // Step 3A: Extract city from student report row
	            String city = row.getCell(3).getStringCellValue().trim();
	            String county = "Unknown"; // Default to Unknown if not found

	            // Step 3B: Check if city exists in city-county map
	            for (List<String> entry : cityCountyList) {
	                if (entry.get(0).equalsIgnoreCase(city)) {
	                    county = entry.get(1); // Get county name from dictionary
	                    break; // Stop searching once found
	                }
	            }

	            // Step 3C: If city not found in mapping, add it to missing cities list for manual assignment
	            if (county.equals("Unknown") && !missingCities.contains(city)) {
	                missingCities.add(city);
	            }

	            // Step 3D: Write county to the final report
	            row.createCell(lastColumn).setCellValue(county);
	        }

	        // Step 4: Save the updated final report
	        try (FileOutputStream fos = new FileOutputStream(new File(finalReportPath))) {
	            workbook.write(fos);
	        }
	        System.out.println("Final report successfully updated with county mappings.");

	        // Step 5: If new cities are found, update the city-county mapping file
	        if (!missingCities.isEmpty()) {
	            updateCityCountyFile(cityCountyLinkerPath, missingCities);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * method that writes unclassified cities into the city-county excel sheet for manual review
	 * @param filePath  path to city-county mapping excel sheet
	 * @param missingCities  list of missing cities found in student report
	 */
	private static void updateCityCountyFile(String filePath, List<String> missingCities) {
	    try (Workbook workbook = new XSSFWorkbook();
	         FileOutputStream fos = new FileOutputStream(new File(filePath))) {

	        Sheet sheet = workbook.createSheet("New Cities");

	        // Step 1: Create Header Row
	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("City");
	        header.createCell(1).setCellValue("County (Manually Assign)");

	        // Step 2: Write Missing Cities into Excel Sheet for Manual County Assignment
	        int rowIndex = 1;
	        for (String city : missingCities) {
	            Row row = sheet.createRow(rowIndex++);
	            row.createCell(0).setCellValue(city);
	            row.createCell(1).setCellValue(""); // Empty cell for manual county input
	        }

	        // Step 3: Save the file
	        workbook.write(fos);
	        System.out.println("New cities written to " + filePath + " for manual county assignment.");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}
