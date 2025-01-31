package datastructures;

public class Student {
	private String firstName;
	private String lastName;
	private int mailingZip;
	private String city; // to county
	private String schoolDistrict;
	private int classOf;
	private String studentFamilyNameAndID;
	private int studentFamilyID;
	
	// Household information from the household report 
	private int parentCountHousehold;
	private int siblingCountHousehold;
	private int alumniCountHousehold;
	private int studentCountHousehold;

	
	/**
	 * constructor 
	 * @param firstName
	 * @param lastName
	 * @param mailingZip
	 * @param city
	 * @param schoolDistrict
	 * @param classOf
	 * @param studentFamilyNameAndID
	 * @param studentFamilyID
	 */
	public Student(String firstName, String lastName, int mailingZip, String city, String schoolDistrict, int classOf,
			String studentFamilyNameAndID, int studentFamilyID) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailingZip = mailingZip;
		this.city = city;
		this.schoolDistrict = schoolDistrict;
		this.classOf = classOf;
		this.studentFamilyNameAndID = studentFamilyNameAndID;
		this.studentFamilyID = studentFamilyID;
		this.parentCountHousehold = 0; // Default to 0
		this.siblingCountHousehold = 0; // Default to 0
		this.alumniCountHousehold = 0; // Default to 0
		this.studentCountHousehold = 0; // Default to 0
	}
	
	
	/**
	 * getter  
	 * @return firstName gets the student's first name 
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * getter  
	 * @return lastName gets the student's last name 
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * getter  
	 * @return city gets the student's city 
	 */
	public String getCity() {
		return city;
	}

	/**
	 * getter  
	 * @return studentFamilyID gets the student's family ID 
	 */
	public int getFamilyID() {
		return studentFamilyID;
	}
	
	/**
	 * getter 
	 * @return parent count in household 
	 */
	public int getParentCount() {
		return parentCountHousehold;
	}
	
	/**
	 * getter  
	 * @return sibling count in household 
	 */
	public int getSiblingCount() {
		return siblingCountHousehold;
	}
	
	/**
	 * getter  
	 * @return alumni count in household 
	 */
	public int getAlumniCount() {
		return alumniCountHousehold;
	}

	/**
	 * getter  
	 * @return student count in household 
	 */
	public int getStudentCount() {
		return studentCountHousehold;
	}
	
	
	/**
	 * adds the family member count to this specific student
	 * @param parentCount parent count from the household report
	 */
	public void setParentCount(int parentCount) {
		this.parentCountHousehold = parentCount;
	}
	
	/**
	 * adds family member count to student profile
	 * @param siblingCount sibling count from household report
	 */
	public void setSiblingCount(int siblingCount) {
		this.siblingCountHousehold = siblingCount;
	}
	
	/**
	 * adds family member count to student profile
	 * @param alumniCount alumni count from household report
	 */
	public void setAlumniCount(int alumniCount) {
		this.alumniCountHousehold = alumniCount;
	}
	
	/**
	 * setter family member count to student profile
	 * @param studentCount student count from household report
	 */
	public void setStudentCount(int studentCount) {
		this.studentCountHousehold = studentCount;
	}
	/**
	 * getter  
	 * @return schoolDistrict gets the student's school district 
	 */
	public String getSchoolDistrict() {
	    return schoolDistrict;
	}

	/**
	 * getter  
	 * @return classOf gets the student's graduation class year 
	 */
	public int getClassOf() {
	    return classOf;
	}

	
	/**
	 * getter  
	 * @return mailingZip gets the student's mailing ZIP code 
	 */
	public int getMailingZip() {
	    return mailingZip;
	}
	/**
	 * getter  
	 * @return studentFamilyNameAndID gets the household name and ID 
	 */
	public String getStudentFamilyNameAndID() {
	    return studentFamilyNameAndID;
	}

	
}
