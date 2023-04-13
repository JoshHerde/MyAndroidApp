package com.example.myandroidapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myandroidapp.entities.Assessments;
import com.example.myandroidapp.entities.Courses;

import java.util.List;

@Dao
public interface AssessmentsDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessments assessments);

    @Update
    void update(Assessments assessments);

    @Delete
    void delete(Assessments assessments);

    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    List<Assessments> getAllAssessments();

    @Query("SELECT COUNT(*) FROM assessments")
    int getCount();

    @Query("SELECT * FROM assessments WHERE assessmentID = :assessmentID")
    Assessments getByID(int assessmentID);

    @Query("SELECT * FROM assessments WHERE course_id = :courseID")
    List<Assessments> getAssociatedAssessments(int courseID);
}
