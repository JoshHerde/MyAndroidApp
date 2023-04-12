package com.example.myandroidapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments", foreignKeys = @ForeignKey(entity = Courses.class, parentColumns = "assessmentID", childColumns = "course_id", onDelete = ForeignKey.CASCADE))
public class Assessments {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    @ColumnInfo(name = "name")
    private String assessmentName;
    @ColumnInfo(name = "type")
    private Date assessmentType;
    @ColumnInfo(name = "start_date")
    private Date assessmentStartDate;
    @ColumnInfo(name = "end_date")
    private Date assessmentEndDate;
    @ColumnInfo(name = "start_alert")
    private Date startAlert;
    @ColumnInfo(name = "end_alert")
    private Date endAlert;
    @ColumnInfo(name = "course_id")
    private int courseID;

    public Assessments(int assessmentID, String assessmentName, Date assessmentType, Date assessmentStartDate,
                       Date assessmentEndDate, Date startAlert, Date endAlert, int courseID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentType = assessmentType;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.startAlert = startAlert;
        this.endAlert = endAlert;
        this.courseID = courseID;
    }

    @Ignore
    public Assessments(String assessmentName, Date assessmentType, Date assessmentStartDate,
                       Date assessmentEndDate, Date startAlert, Date endAlert, int courseID) {
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

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public Date getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(Date assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Date getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(Date assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public Date getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(Date assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public Date getStartAlert() {
        return startAlert;
    }

    public void setStartAlert(Date startAlert) {
        this.startAlert = startAlert;
    }

    public Date getEndAlert() {
        return endAlert;
    }

    public void setEndAlert(Date endAlert) {
        this.endAlert = endAlert;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
