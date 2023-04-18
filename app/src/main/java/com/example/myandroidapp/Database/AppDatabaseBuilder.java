package com.example.myandroidapp.Database;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myandroidapp.dao.AssessmentsDAO;
import com.example.myandroidapp.dao.CoursesDAO;
import com.example.myandroidapp.dao.TermDAO;
import com.example.myandroidapp.entities.Assessments;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Terms;

@Database(entities = {Terms.class, Courses.class, Assessments.class}, version = 2, exportSchema = false)
public abstract class AppDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CoursesDAO coursesDAO();
    public abstract AssessmentsDAO assessmentsDAO();

    private static volatile AppDatabaseBuilder INSTANCE;

    public static AppDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (AppDatabaseBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabaseBuilder.class, "SchedulingAppDB.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
