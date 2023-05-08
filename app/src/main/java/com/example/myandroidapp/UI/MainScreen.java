package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        /*
        Terms terms = new Terms("Term Test", "10/10/2022", "03/10/2023");
        Repository repository = new Repository(getApplication());
        repository.insert(terms);
         */
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, TermList.class);
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
    }
}