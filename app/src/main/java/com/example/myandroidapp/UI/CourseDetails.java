package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Terms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {

    Courses courses;
    Terms terms;

    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    Spinner editStatus;
    EditText editCiName;
    EditText editCiPhone;
    EditText editCiEmail;
    Spinner editTerm;
    EditText editNotes;

    DatePickerDialog.OnDateSetListener courseStartDate;
    DatePickerDialog.OnDateSetListener courseEndDate;
    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);









        Button courseCancelButton = findViewById(R.id.courseCancelButton);
        courseCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, CourseList.class);
                CourseDetails.this.startActivity(intent);
            }
        });
    }
}