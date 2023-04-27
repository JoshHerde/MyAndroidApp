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
import android.widget.Toast;

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

    Courses currentCourse;
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

    DatePickerDialog.OnDateSetListener courseDPStartDate;
    DatePickerDialog.OnDateSetListener courseDPEndDate;
    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


    Repository repository;
    AssessmentListAdapter assessmentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        views();
        intentValues();
        datePickers();

        // Associated assessment recycler view
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        assessmentListAdapter = new AssessmentListAdapter(this);
        recyclerView.setAdapter(assessmentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessments> courseAssessments = new ArrayList<>();
        for (Assessments assessments : repository.getAllAssessments()) {
            if (assessments.getCourseID() == currentCourse.getID())
                courseAssessments.add(assessments);
        }
        assessmentListAdapter.setAssessments(courseAssessments);


        // Status Spinner
        ArrayAdapter<Status> statusArrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Status.values());
        editStatus.setAdapter(statusArrayAdapter);
        editStatus.setSelection(statusArrayAdapter.getPosition(currentCourse.getCourseStatus()));

        // Term List
        ArrayList<String> termArrayList = new ArrayList<>();
        for (Terms t : repository.getAllTerms()) {
            termArrayList.add(t.getTermName());
            if (t.getID() == currentCourse.getTermID())
                terms = t;
        }

        // Term Spinner
        ArrayAdapter<String> termArrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, termArrayList);
        editTerm.setAdapter(termArrayAdapter);
        if (terms != null)
            editTerm.setSelection(termArrayAdapter.getPosition(terms.getTermName()));
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


        Button courseSaveButton = findViewById(R.id.courseSaveButton);
        courseSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCourse.setCourseName(editName.getText().toString());
                currentCourse.setCourseStartDate(editStartDate.getText().toString());
                currentCourse.setCourseEndDate(editEndDate.getText().toString());
                currentCourse.setCourseStatus(Status.valueOf(editStatus.getSelectedItem().toString()));
                currentCourse.setCiName(editCiName.getText().toString());
                currentCourse.setCiPhone(editCiPhone.getText().toString());
                currentCourse.setCiEmail(editCiEmail.getText().toString());
                currentCourse.setNotes(editNotes.getText().toString());
                terms = repository.getAllTerms().get(editTerm.getSelectedItemPosition());
                currentCourse.setTermID(terms.getID());

                if (editName.getText().toString().equals("") || editStartDate.getText().toString().equals("") || editEndDate.getText().toString().equals("") ||
                editStatus.getSelectedItem().toString().equals("") || editCiName.getText().toString().equals("") || editCiPhone.getText().toString().equals("") ||
                editCiEmail.getText().toString().equals("")) {
                    Toast.makeText(CourseDetails.this, "Fill out all above fields, notes not required.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (currentCourse.getID() == -1) {
                    currentCourse.setID(0);
                    repository.insert(currentCourse);
                    Toast.makeText(CourseDetails.this, "Course was created!", Toast.LENGTH_LONG).show();
                }
                else {
                    repository.update(currentCourse);
                    Toast.makeText(CourseDetails.this, "Course was updated!", Toast.LENGTH_LONG).show();
                }
                finish();
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
            currentCourse = new Courses(
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
            currentCourse = new Courses();
        }

        editName.setText(currentCourse.getCourseName());
        editStartDate.setText(currentCourse.getCourseStartDate());
        editEndDate.setText(currentCourse.getCourseEndDate());
        editCiName.setText(currentCourse.getCiName());
        editCiPhone.setText(currentCourse.getCiPhone());
        editCiEmail.setText(currentCourse.getCiEmail());
        editNotes.setText(currentCourse.getNotes());
    }

    private void datePickers() {
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CourseDetails.this, courseDPStartDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseDPStartDate = new DatePickerDialog.OnDateSetListener() {
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
                new DatePickerDialog(CourseDetails.this, courseDPEndDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        courseDPEndDate = new DatePickerDialog.OnDateSetListener() {
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

    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    public boolean OnOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.courseShareNotes:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
        }

        return false;
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
            if (assessments.getCourseID() == currentCourse.getID())
                courseAssessments.add(assessments);
        }
        assessmentListAdapter.setAssessments(courseAssessments);
    }
}