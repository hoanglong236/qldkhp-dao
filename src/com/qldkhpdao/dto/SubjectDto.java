package com.qldkhpdao.dto;

/**
 *
 * @author B1809367
 */
public class SubjectDto {

    private String subjectId;
    private String subjectName;
    private int numberCredits;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getNumberCredits() {
        return numberCredits;
    }

    public void setNumberCredits(int numberCredits) {
        this.numberCredits = numberCredits;
    }
}
