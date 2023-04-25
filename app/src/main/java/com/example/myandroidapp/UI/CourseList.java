package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myandroidapp.Database.Repository;
import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Terms;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourseList extends AppCompatActivity {

    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseListAdapter courseListAdapter = new CourseListAdapter(this);
        recyclerView.setAdapter(courseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Courses> courses = repository.getAllCourses();
        courseListAdapter.setCourses(courses);


        FloatingActionButton FABAddCourse = findViewById(R.id.FABAddCourse);
        FABAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, CourseDetails.class);
                CourseList.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Courses> courses = repository.getAllCourses();
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseListAdapter courseListAdapter = new CourseListAdapter(this);
        recyclerView.setAdapter(courseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseListAdapter.setCourses(courses);
    }
}