package com.example.myandroidapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myandroidapp.entities.Courses;

import java.util.List;

@Dao
public interface CoursesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Courses courses);

    @Update
    void update(Courses courses);

    @Delete
    void delete(Courses courses);

    @Query("SELECT * FROM courses ORDER BY ID ASC")
    List<Courses> getAllCourses();

    @Query("SELECT COUNT(*) FROM courses")
    int getCount();

    @Query("SELECT * FROM courses WHERE ID = :courseID")
    Courses getCourseByID(int courseID);

    @Query("SELECT * FROM courses WHERE term_id = :termID")
    List<Courses> getCourseByTerm(int termID);
}
