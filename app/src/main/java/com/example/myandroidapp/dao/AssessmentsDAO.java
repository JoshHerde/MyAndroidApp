package com.example.myandroidapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myandroidapp.entities.Assessments;

import java.util.List;

@Dao
public interface AssessmentsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Assessments assessments);

    @Update
    void update(Assessments assessments);

    @Delete
    void delete(Assessments assessments);

    @Query("SELECT * FROM assessments ORDER BY ID ASC")
    List<Assessments> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE course_id = :courseID")
    List<Assessments> getAssociatedAssessments (int courseID);
}
