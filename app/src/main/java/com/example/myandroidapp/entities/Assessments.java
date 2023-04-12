package com.example.myandroidapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments")
public class Assessments {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    private String termName;
    private Date termStartDate;
    private Date termEndDate;
}
