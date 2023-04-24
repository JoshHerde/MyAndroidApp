package com.example.myandroidapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments", foreignKeys = @ForeignKey(entity = Courses.class, parentColumns = "ID", childColumns = "course_id", onDelete = ForeignKey.CASCADE))
public class Assessments {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    @ColumnInfo(name = "name")
    private String assessmentName;
    @ColumnInfo(name = "type")
    private String assessmentType;
    @ColumnInfo(name = "start_date")
    private String assessmentStartDate;
    @ColumnInfo(name = "end_date")
    private String assessmentEndDate;
    @ColumnInfo(name = "start_alert")
    private String startAlert;
    @ColumnInfo(name = "end_alert")
    private String endAlert;
    @ColumnInfo(name = "course_id")
    private int courseID;

    public Assessments(int ID, String assessmentName, String assessmentType, String assessmentStartDate,
                       String assessmentEndDate, String startAlert, String endAlert, int courseID) {
        this.ID = ID;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.startAlert = startAlert;
        this.endAlert = endAlert;
        this.courseID = courseID;
    }

    @Ignore
    public Assessments(String assessmentName, String assessmentType, String assessmentStartDate,
                       String assessmentEndDate, String startAlert, String endAlert, int courseID) {
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.startAlert = startAlert;
        this.endAlert = endAlert;
        this.courseID = courseID;
    }

    @Ignore
    public Assessments() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public String getStartAlert() {
        return startAlert;
    }

    public void setStartAlert(String startAlert) {
        this.startAlert = startAlert;
    }

    public String getEndAlert() {
        return endAlert;
    }

    public void setEndAlert(String endAlert) {
        this.endAlert = endAlert;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
