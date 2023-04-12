package com.example.myandroidapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "terms")
public class Terms {
    @PrimaryKey(autoGenerate = true)
    private int termID;
    @ColumnInfo(name = "term_name")
    private String termName;
    @ColumnInfo(name = "start_date")
    private Date termStartDate;
    @ColumnInfo(name = "end_date")
    private Date termEndDate;

    public Terms(int termID, String termName, Date termStartDate, Date termEndDate) {
        this.termID = termID;
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

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
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
