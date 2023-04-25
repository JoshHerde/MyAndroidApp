package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

    Repository repository;
    AssessmentListAdapter assessmentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        views();
        intentValues();
        listeners();



        courseStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, day);

                sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                editStartDate.setText(sdf.format(calendarStart.getTime()));
            }
        };

        courseEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, day);

                sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                editEndDate.setText(sdf.format(calendarEnd.getTime()));
            }
        };


    }

    private void views() {
        editName = findViewById(R.id.courseName);
        editStartDate = findViewById(R.id.courseStartDate);
        editEndDate = findViewById(R.id.courseEndDate);
        editStatus = findViewById(R.id.courseStatusSpinner);
        editCiName = findViewById(R.id.ciName);
        editCiPhone = findViewById(R.id.ciPhone);
        editCiEmail = findViewById(R.id.ciEmail);
        editTerm = findViewById(R.id.termSpinner);
        editNotes = findViewById(R.id.courseNotes);

        // Status Spinner
        ArrayAdapter<Status> statusArrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Status.values());
        editStatus.setAdapter(statusArrayAdapter);
        editStatus.setSelection(statusArrayAdapter.getPosition(courses.getCourseStatus()));

        // Term List
        ArrayList<String> termArrayList = new ArrayList<>();
        for (Terms t : repository.getAllTerms()) {
            termArrayList.add(t.getTermName());
            if (t.getID() == courses.getTermID())
                terms = t;
        }

        // Term Spinner
        ArrayAdapter<String> termArrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, termArrayList);
        editTerm.setAdapter(termArrayAdapter);
        if (terms != null)
            editTerm.setSelection(termArrayAdapter.getPosition(terms.getTermName()));


        // Associated assessment recycler view
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        assessmentListAdapter = new AssessmentListAdapter(this);
        recyclerView.setAdapter(assessmentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessments> courseAssessments = new ArrayList<>();
        for (Assessments assessments : repository.getAllAssessments()) {
            if (assessments.getCourseID() == courses.getID())
                courseAssessments.add(assessments);
        }
        assessmentListAdapter.setAssessments(courseAssessments);


        Button courseSaveButton = findViewById(R.id.courseSaveButton);
        courseSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button courseCancelButton = findViewById(R.id.courseCancelButton);
        courseCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, CourseList.class);
                CourseDetails.this.startActivity(intent);
            }
        });
    }

    private void intentValues() {
        try {
            courses = new Courses(
                    getIntent().getIntExtra("ID", -1),
                    getIntent().getStringExtra("courseName"),
                    getIntent().getStringExtra("courseStartDate"),
                    getIntent().getStringExtra("courseEndDate"),
                    (Status) getIntent().getSerializableExtra("courseStatus"),
                    getIntent().getStringExtra("ciName"),
                    getIntent().getStringExtra("ciPhone"),
                    getIntent().getStringExtra("ciEmail"),
                    getIntent().getStringExtra("notes"),
                    getIntent().getIntExtra("termID", 0)
            );
        }
        catch (Exception e) {
            courses = new Courses();
        }

        editName.setText(courses.getCourseName());
        editStartDate.setText(courses.getCourseStartDate());
        editEndDate.setText(courses.getCourseEndDate());
        editCiName.setText(courses.getCiName());
        editCiPhone.setText(courses.getCiPhone());
        editCiEmail.setText(courses.getCiEmail());
        editNotes.setText(courses.getNotes());

        repository = new Repository(getApplication());
    }

    private void listeners() {
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseDetails.this, courseStartDate, calendarStart.get(Calendar.YEAR),
                        calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseDetails.this, courseEndDate, calendarEnd.get(Calendar.YEAR),
                        calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        assessmentListAdapter = new AssessmentListAdapter(this);
        recyclerView.setAdapter(assessmentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessments> courseAssessments = new ArrayList<>();
        for (Assessments assessments : repository.getAllAssessments()) {
            if (assessments.getCourseID() == courses.getID())
                courseAssessments.add(assessments);
        }
        assessmentListAdapter.setAssessments(courseAssessments);
    }
}