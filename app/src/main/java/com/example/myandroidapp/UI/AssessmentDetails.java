package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myandroidapp.Database.Repository;
import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Assessments;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Status;
import com.example.myandroidapp.entities.Terms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    Assessments assessments;
    Courses courses;

    EditText editName;
    EditText editType;
    EditText editStartDate;
    EditText editEndDate;
    Spinner editCourse;

    DatePickerDialog.OnDateSetListener assessmentDPStartDate;
    DatePickerDialog.OnDateSetListener assessmentDPEndDate;
    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        views();
        intentValues();
        datePickers();


        // Course List
        ArrayList<String> courseArrayList = new ArrayList<>();
        for (Courses c : repository.getAllCourses()) {
            courseArrayList.add(c.getCourseName());

        }

        // Course Spinner
        ArrayAdapter<String> courseArrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, courseArrayList);
        editCourse.setAdapter(courseArrayAdapter);
        editCourse.setSelection(courseArrayAdapter.getPosition(courses.getCourseName()));
    }

    private void views() {
        editName = findViewById(R.id.assessmentName);
        editType = findViewById(R.id.assessmentType);
        editStartDate = findViewById(R.id.assessmentStartDate);
        editEndDate = findViewById(R.id.assessmentEndDate);
        editCourse = findViewById(R.id.courseSpinner);

        Button assessmentSaveButton = findViewById(R.id.assessmentSaveButton);
        assessmentSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button assessmentCancelButton = findViewById(R.id.assessmentCancelButton);
        assessmentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                AssessmentDetails.this.startActivity(intent);
            }
        });
    }

    private void intentValues() {
        try {
            assessments = new Assessments(
                    getIntent().getIntExtra("ID", -1),
                    getIntent().getStringExtra("assessmentName"),
                    getIntent().getStringExtra("assessmentType"),
                    getIntent().getStringExtra("assessmentStartDate"),
                    getIntent().getStringExtra("assessmentEndDate"),
                    getIntent().getIntExtra("courseID", 0)
            );
        } catch (Exception e) {
            assessments = new Assessments();
        }

        editName.setText(assessments.getAssessmentName());
        editType.setText(assessments.getAssessmentType());
        editStartDate.setText(assessments.getAssessmentStartDate());
        editEndDate.setText(assessments.getAssessmentEndDate());
    }

    private void datePickers() {
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AssessmentDetails.this, assessmentDPStartDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        assessmentDPStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, day);

                sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                editStartDate.setText(sdf.format(myCalendarStart.getTime()));
            }
        };

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AssessmentDetails.this, assessmentDPEndDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        assessmentDPEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, day);

                sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
            }
        };
    }
}