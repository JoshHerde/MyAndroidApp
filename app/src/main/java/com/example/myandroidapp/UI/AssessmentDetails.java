package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myandroidapp.Database.Repository;
import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Assessments;
import com.example.myandroidapp.entities.Courses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    Assessments currentAssessment;
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

    Repository repository = new Repository(getApplication());


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
            if (c.getID() == currentAssessment.getCourseID());
            courses = c;

        }

        // Course Spinner
        ArrayAdapter<String> courseArrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, courseArrayList);
        editCourse.setAdapter(courseArrayAdapter);
        if (courses != null) {
            editCourse.setSelection(courseArrayAdapter.getPosition(courses.getCourseName()));
        }

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
                currentAssessment.setAssessmentName(editName.getText().toString());
                currentAssessment.setAssessmentType(editType.getText().toString());
                currentAssessment.setAssessmentStartDate(editStartDate.getText().toString());
                currentAssessment.setAssessmentEndDate(editEndDate.getText().toString());
                courses = repository.getAllCourses().get(editCourse.getSelectedItemPosition());
                currentAssessment.setCourseID(courses.getID());

                if (currentAssessment.getID() == -1) {
                    currentAssessment.setID(0);
                    repository.insert(currentAssessment);
                    //Toast.makeText(this, "Assessment was created!", Toast.LENGTH_LONG).show();
                }
                else {
                    repository.update(currentAssessment);
                    //Toast.makeText(this, "Assessment was updated!", Toast.LENGTH_LONG).show();
                }
                finish();
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
            currentAssessment = new Assessments(
                    getIntent().getIntExtra("ID", -1),
                    getIntent().getStringExtra("assessmentName"),
                    getIntent().getStringExtra("assessmentType"),
                    getIntent().getStringExtra("assessmentStartDate"),
                    getIntent().getStringExtra("assessmentEndDate"),
                    getIntent().getIntExtra("courseID", 0)
            );
        } catch (Exception e) {
            currentAssessment = new Assessments();
        }

        editName.setText(currentAssessment.getAssessmentName());
        editType.setText(currentAssessment.getAssessmentType());
        editStartDate.setText(currentAssessment.getAssessmentStartDate());
        editEndDate.setText(currentAssessment.getAssessmentEndDate());
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