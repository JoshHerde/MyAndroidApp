package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myandroidapp.Database.Repository;
import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Assessments;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Status;
import com.example.myandroidapp.entities.Terms;

public class MainScreen extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Button termsButton = findViewById(R.id.termsButton);
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainScreen.this, TermList.class);
                MainScreen.this.startActivity(intent);
            }
        });

        Button coursesButton = findViewById(R.id.coursesButton);
        coursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, CourseList.class);
                MainScreen.this.startActivity(intent);
            }
        });

        Button assessmentsButton = findViewById(R.id.assessmentsButton);
        assessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, AssessmentList.class);
                MainScreen.this.startActivity(intent);
            }
        });

        //sampleData();
    }

    private void sampleData() {
        Repository repository = new Repository(getApplication());

        repository.insert(new Terms("Term 1", "10/10/2022", "03/10/2023"));
        repository.insert(new Terms("Term 2", "03/15/2023", "09/15/2023"));

        repository.insert(new Courses("C196", "10/10/2022", "12/15/2022", Status.Completed, "Josh Herde", "111-111-1111", "jherde@wgu.edu", "test", 1));

        repository.insert(new Assessments("Assessment 1", "Objective", "12/15/2022", "12/15/2022", 1));
        repository.insert(new Assessments("Assessment 2", "Objective", "12/15/2022", "12/15/2022", 1));
    }
}