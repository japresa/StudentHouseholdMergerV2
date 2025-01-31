package datastructures;

public class Household {
    private int familyID;
    private int studentCount;
    private int alumniCount;
    private int parentCount;
    private int siblingCount;

    public Household(int familyID, int studentCount, int siblingCount, int alumniCount, int parentCount) {
        this.familyID = familyID;
        this.studentCount = studentCount;
        this.siblingCount = siblingCount;
        this.alumniCount = alumniCount;
        this.parentCount = parentCount;
    }

    public int getSiblingCount(){
    	return siblingCount;
    }
    
    public int getFamilyID() {
        return familyID;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public int getAlumniCount() {
        return alumniCount;
    }

    public int getParentCount() {
        return parentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public void setAlumniCount(int alumniCount) {
        this.alumniCount = alumniCount;
    }

    public void setParentCount(int parentCount) {
        this.parentCount = parentCount;
    }
}
