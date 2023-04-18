package com.example.myandroidapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myandroidapp.entities.Terms;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Terms terms);

    @Update
    void update(Terms terms);

    @Delete
    void delete(Terms terms);

    @Query("SELECT * FROM terms ORDER BY termID ASC")
    List<Terms> getAllTerms();

    @Query("SELECT COUNT(*) FROM terms")
    int getCount();

    @Query("SELECT * FROM terms WHERE termID = :termID")
    Terms getTermByID(int termID);
}
