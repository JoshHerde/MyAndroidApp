package com.example.myandroidapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Courses {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseName;
    private Date courseStartDate;
    private Date courseEndDate;
    private String courseStatus;
    private String ciName;
    private String ciPhone;
    private String ciEmail;
    private String notes;
    private int termID;

}
