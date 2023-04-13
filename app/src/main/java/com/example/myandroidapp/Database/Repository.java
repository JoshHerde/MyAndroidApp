package com.example.myandroidapp.Database;

import android.app.Application;

import androidx.room.Query;

import com.example.myandroidapp.dao.AssessmentsDAO;
import com.example.myandroidapp.dao.CoursesDAO;
import com.example.myandroidapp.dao.TermDAO;
import com.example.myandroidapp.entities.Assessments;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Terms;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private CoursesDAO mCoursesDAO;
    private AssessmentsDAO mAssessmentsDAO;
    private List<Terms> mAllTerms;
    private List<Courses> mAllCourses;
    private List<Assessments> mAllAssessments;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        AppDatabaseBuilder db = AppDatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCoursesDAO = db.coursesDAO();
        mAssessmentsDAO = db.assessmentsDAO();
    }

    public List<Terms> getAllTerms() {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert (Terms terms) {
        databaseExecutor.execute(() -> {
            mTermDAO.insert(terms);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update (Terms terms) {
        databaseExecutor.execute(() -> {
            mTermDAO.update(terms);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete (Terms terms) {
        databaseExecutor.execute(() -> {
            mTermDAO.delete(terms);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Courses> getAllCourses() {
        databaseExecutor.execute(() -> {
            mAllCourses = mCoursesDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert (Courses courses) {
        databaseExecutor.execute(() -> {
            mCoursesDAO.insert(courses);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update (Courses courses) {
        databaseExecutor.execute(() -> {
            mCoursesDAO.update(courses);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete (Courses courses) {
        databaseExecutor.execute(() -> {
            mCoursesDAO.delete(courses);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Assessments> getAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentsDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert (Assessments assessments ) {
        databaseExecutor.execute(() -> {
            mAssessmentsDAO.insert(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update (Assessments assessments ) {
        databaseExecutor.execute(() -> {
            mAssessmentsDAO.update(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete (Assessments assessments ) {
        databaseExecutor.execute(() -> {
            mAssessmentsDAO.delete(assessments);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
