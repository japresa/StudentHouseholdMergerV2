package service;

import datastructures.Student;
import datastructures.Household;
import java.util.List;

public class ReportMerger {
    public static void mergeHouseholdData(List<Student> students, List<Household> households) {
        for (Student student : students) {
            for (Household household : households) {
                if (student.getFamilyID() == household.getFamilyID()) {
                    student.setStudentCount(household.getStudentCount());
                    student.setAlumniCount(household.getAlumniCount());
                    student.setParentCount(household.getParentCount());
                    student.setSiblingCount(household.getSiblingCount());
                    break; // Stop searching once matched
                }
            }
        }
    }
}
