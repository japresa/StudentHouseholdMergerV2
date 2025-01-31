package service;

import datastructures.Household;
import datastructures.Student;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

//excel reader imports 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * reads data from the excel files and stores it in the List DataStructure using
 * library
 */
public class ExcelReader {

	public static List<Household> readHouseholdReport(String filePath) throws Exception {
		List<Household> households = new ArrayList<>();
		int startingRow = -1;

		// reader guts (loads filepath into workbook api reading from sheet 0
		try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheetAt(0);

			// Step 1: Find First Row Containing "Family ID"
			for (Row row : sheet) {
				Cell firstCell = row.getCell(3); // Family ID is in column 3
				if (firstCell != null && firstCell.getCellType() == CellType.NUMERIC) {
					startingRow = row.getRowNum() + 1; // moves to the actual first data row
					System.out.println("Found 'Family ID' header at row: " + startingRow);
					break;
				}
			}

			// If "Family ID" not found, throw an exception
			if (startingRow == -1) {
				throw new Exception("ERROR: 'Family ID' column header not found! Check the Excel file format.");
			}

			// Step 2: Read Household Data
			for (Row row : sheet) {
				if (row.getRowNum() < startingRow) // actually starting at the first row so dont need equal to
					continue;

				// starts at A
				Cell familyIdCell = row.getCell(2);
				Cell studentCountCell = row.getCell(14); // Assuming student count is in column 10
				Cell parentCountCell = row.getCell(10); // Assuming parent count is in column 8
				Cell siblingCountCell = row.getCell(12);//
				Cell alumniCountCell = row.getCell(6); // Assuming alumni count is in column 6

				System.out.println(familyIdCell + "student: " + studentCountCell + "parent: " + parentCountCell
						+ "sibling" + siblingCountCell + "alumni: " + alumniCountCell);
				if (familyIdCell != null && familyIdCell.getCellType() == CellType.NUMERIC) {
					int familyID = (int) familyIdCell.getNumericCellValue();
					int studentCount = studentCountCell != null && studentCountCell.getCellType() == CellType.NUMERIC
							? (int) studentCountCell.getNumericCellValue()
							: -1;
					int alumniCount = alumniCountCell != null && alumniCountCell.getCellType() == CellType.NUMERIC
							? (int) alumniCountCell.getNumericCellValue()
							: -1;
					int parentCount = parentCountCell != null && parentCountCell.getCellType() == CellType.NUMERIC
							? (int) parentCountCell.getNumericCellValue()
							: -1;
					int siblingCount = siblingCountCell != null && siblingCountCell.getCellType() == CellType.NUMERIC
							? (int) siblingCountCell.getNumericCellValue()
							: -1;

					if (familyID == 0) {
						System.out.println("stopping at Total row: " + row.getRowNum());
						break;
					}
					households.add(new Household(familyID, studentCount, siblingCount, alumniCount, parentCount));
					// System.out.println("added household: Family ID " + familyID);
					// System.out.println("Students: " + studentCount + " | Alumni: " + alumniCount
					// + " | Parents: " + parentCount);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return households;
	}

	/**
	 * reads the student report ughhhh hard
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static List<Student> readStudentReport(String filePath) throws Exception {
		List<Student> students = new ArrayList<>(); // list of students from excel list
		int startingRow = -1;

		// reader guts (loads filepath into Workbook API reading sheet 0)
		try (FileInputStream fis = new FileInputStream(new File(filePath)); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheetAt(0);

			// Step 1: Find first row containg "First Name"
			for (Row row : sheet) {
				Cell firstCell = row.getCell(1);
				if (firstCell != null && firstCell.getCellType() == CellType.STRING) {
					String cellValue = firstCell.getStringCellValue().trim();

					System.out.println("Checking row " + row.getRowNum() + " first cell value: " + cellValue);

					if (cellValue.equalsIgnoreCase("First Name")) {
						startingRow = row.getRowNum();
						System.out.println("Found 'First Name' header at row: " + startingRow);
						break;
					}
				}
			}
			// if FirstName not found
			if (startingRow == -1) {
				throw new Exception(
						" ERROR: 'First Name' Column header not found! Edit SF Report to make first name the first column.");
			}

			// Step 2: Read rows starting from 'startingRow', stopping at 'Total'
			for (Row row : sheet) {
				if (row.getRowNum() <= startingRow)
					continue;

				Cell firstCell = row.getCell(1);
				if (firstCell != null && firstCell.getCellType() == CellType.STRING) {
					String cellValue = firstCell.getStringCellValue().trim();

					System.out.println("Checking row " + row.getRowNum() + " first cell value: " + cellValue);

					if (cellValue.equalsIgnoreCase("Total")) {
						System.out.println("Stopping at 'Total' row: " + row.getRowNum());
						break;
					}

					// Step 3: load student data into data structure
					String firstName = getCellString(row.getCell(1));
					String lastName = getCellString(row.getCell(3));
					int zip = getCellInt(row.getCell(4)); // "Mailing Zip" is in column 4
					String city = getCellString(row.getCell(5));
					String schoolDistrict = getCellString(row.getCell(6));
					int classOf = getCellInt(row.getCell(7));
					String familyNameAndID = getCellString(row.getCell(8));
					int familyID = getCellInt(row.getCell(10));

					students.add(new Student(firstName, lastName, zip, city, schoolDistrict, classOf, familyNameAndID,
							familyID));
					System.out.println("added student: " + firstName + " " + lastName);
					System.out.println(firstName + " " + lastName + " " + zip + " " + city + " " + schoolDistrict + " "
							+ classOf + " " + familyNameAndID + " " + familyID);
				}

				// Debugging: Print row index
				System.out.print("Row " + row.getRowNum() + ": ");

				// Print full row for debugging
				for (Cell cell : row) {
					System.out.print(cell.toString() + " | ");
				}
				System.out.println(); // New line after each row
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return students;
	}

	
	public static List<List<String>> readCityToCountyMapping(String filePath) {
	    List<List<String>> cityCountyMapping = new ArrayList<>();

	    try (FileInputStream fis = new FileInputStream(new File(filePath));
	         Workbook workbook = new XSSFWorkbook(fis)) {

	        Sheet sheet = workbook.getSheetAt(0);
	        Row headerRow = sheet.getRow(0); // First row is headers

	        // Store counties from headers
	        List<String> counties = new ArrayList<>();
	        for (int i = 1; i < headerRow.getLastCellNum(); i++) {
	            counties.add(headerRow.getCell(i).getStringCellValue().trim());
	        }

	        // Read each city and assign to the appropriate county
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	            Row row = sheet.getRow(i);
	            if (row == null) continue;

	            for (int j = 1; j < row.getLastCellNum(); j++) { // Start from column 1 (skip "Unknown")
	                Cell cityCell = row.getCell(j);
	                if (cityCell != null && cityCell.getCellType() == CellType.STRING) {
	                    List<String> entry = Arrays.asList(cityCell.getStringCellValue().trim(), counties.get(j - 1));
	                    cityCountyMapping.add(entry);
	                }
	            }
	        }

	        System.out.println("City-to-county mapping loaded successfully.");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return cityCountyMapping;
	}

	
	
	/**
	 * helper function to safely extract string value from a cell
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellString(Cell cell) {
		if (cell == null)
			return ""; // return empty string is null

		return cell.getStringCellValue().trim();
	}

	/**
	 * 
	 * @param cell
	 * @return
	 */
	private static int getCellInt(Cell cell) {
		if (cell == null) {
			System.out.print(" cell null \n");
			return -1;
		}

		if (cell.getCellType() == CellType.STRING) {
			try {
				return Integer.parseInt(cell.getStringCellValue().trim());
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						"Invalid number format in string cell: " + cell.getStringCellValue());
			}
		}

		if (cell.getCellType() == CellType.NUMERIC) {
			return (int) cell.getNumericCellValue(); // Correct way to cast numeric cell value
		}

		throw new IllegalArgumentException("Unsupported cell type: " + cell.getCellType());

	}

}
