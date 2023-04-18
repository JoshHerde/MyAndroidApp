package com.example.myandroidapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity (tableName = "terms")
public class Terms {
    @PrimaryKey(autoGenerate = true)
    private int ID;
    @ColumnInfo(name = "term_name")
    private String termName;
    @ColumnInfo(name = "start_date")
    private Date termStartDate;
    @ColumnInfo(name = "end_date")
    private Date termEndDate;

    // constructor
    public Terms(int ID, String termName, Date termStartDate, Date termEndDate) {
        this.ID = ID;
        this.termName = termName;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    @Ignore
    public Terms(String termName, Date termStartDate, Date termEndDate) {
        this.termName = termName;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    @Ignore
    public Terms() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(Date termStartDate) {
        this.termStartDate = termStartDate;
    }

    public Date getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(Date termEndDate) {
        this.termEndDate = termEndDate;
    }
}
