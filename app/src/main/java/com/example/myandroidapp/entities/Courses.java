package com.example.myandroidapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses", foreignKeys = @ForeignKey(entity = Terms.class, parentColumns = "ID", childColumns = "term_id", onDelete = ForeignKey.CASCADE))
public class Courses {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    @ColumnInfo(name = "name")
    private String courseName;
    @ColumnInfo(name = "start_date")
    private String courseStartDate;
    @ColumnInfo(name = "end_date")
    private String courseEndDate;
    @ColumnInfo(name = "status")
    private String courseStatus;
    @ColumnInfo(name = "ci_name")
    private String ciName;
    @ColumnInfo(name = "ci_phone")
    private String ciPhone;
    @ColumnInfo(name = "ci_email")
    private String ciEmail;
    @ColumnInfo(name = "notes")
    private String notes;
    @ColumnInfo(name = "start_alert")
    private String startAlert;
    @ColumnInfo(name = "end_alert")
    private String endAlert;
    @ColumnInfo(name = "term_id")
    private int termID;

    // constructor
    public Courses(int ID, String courseName, String courseStartDate, String courseEndDate, String courseStatus,
                   String ciName, String ciPhone, String ciEmail, String notes, String startAlert, String endAlert, int termID) {
        this.ID = ID;
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.ciName = ciName;
        this.ciPhone = ciPhone;
        this.ciEmail = ciEmail;
        this.notes = notes;
        this.startAlert = startAlert;
        this.endAlert = endAlert;
        this.termID = termID;
    }

    @Ignore
    public Courses(String courseName, String courseStartDate, String courseEndDate, String courseStatus,
                   String ciName, String ciPhone, String ciEmail, String notes, String startAlert, String endAlert, int termID) {
        this.courseName = courseName;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.ciName = ciName;
        this.ciPhone = ciPhone;
        this.ciEmail = ciEmail;
        this.notes = notes;
        this.startAlert = startAlert;
        this.endAlert = endAlert;
        this.termID = termID;
    }

    @Ignore
    public Courses() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCiName() {
        return ciName;
    }

    public void setCiName(String ciName) {
        this.ciName = ciName;
    }

    public String getCiPhone() {
        return ciPhone;
    }

    public void setCiPhone(String ciPhone) {
        this.ciPhone = ciPhone;
    }

    public String getCiEmail() {
        return ciEmail;
    }

    public void setCiEmail(String ciEmail) {
        this.ciEmail = ciEmail;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

}
