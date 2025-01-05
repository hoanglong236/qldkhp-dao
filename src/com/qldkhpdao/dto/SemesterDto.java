package com.qldkhpdao.dto;

/**
 *
 * @author B1809367
 */
public class SemesterDto {

    private String semester;
    private String schoolYear;
    private boolean enrollmentStatus;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public boolean getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(boolean enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }
}
