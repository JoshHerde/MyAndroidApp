package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
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
import com.example.myandroidapp.dao.AssessmentsDAO;
import com.example.myandroidapp.entities.Assessments;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Status;
import com.example.myandroidapp.entities.Terms;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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



        // layout
        editName = findViewById(R.id.courseName);
        editStartDate = findViewById(R.id.courseStartDate);
        editEndDate = findViewById(R.id.courseEndDate);
        editStatus = findViewById(R.id.courseStatusSpinner);
        editCiName = findViewById(R.id.ciName);
        editCiPhone = findViewById(R.id.ciPhone);
        editCiEmail = findViewById(R.id.ciEmail);
        editTerm = findViewById(R.id.termSpinner);
        editNotes = findViewById(R.id.courseNotes);

        // Save button
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
                } else {
                    repository.update(currentCourse);
                    Toast.makeText(CourseDetails.this, "Course was updated!", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

        // Cancel Button
        Button courseCancelButton = findViewById(R.id.courseCancelButton);
        courseCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, CourseList.class);
                CourseDetails.this.startActivity(intent);
            }
        });


        // Intent values
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
        } catch (Exception e) {
            currentCourse = new Courses();
        }

        editName.setText(currentCourse.getCourseName());
        editStartDate.setText(currentCourse.getCourseStartDate());
        editEndDate.setText(currentCourse.getCourseEndDate());
        editCiName.setText(currentCourse.getCiName());
        editCiPhone.setText(currentCourse.getCiPhone());
        editCiEmail.setText(currentCourse.getCiEmail());
        editNotes.setText(currentCourse.getNotes());


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
        ArrayAdapter<Status> statusArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_selected_item, Status.values());
        statusArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items);
        editStatus.setAdapter(statusArrayAdapter);
        editStatus.setSelection(statusArrayAdapter.getPosition(currentCourse.getCourseStatus()));

        // Term List
        ArrayList<String> termArrayList = new ArrayList<>();
        for (Terms tList : repository.getAllTerms()) {
            termArrayList.add(tList.getTermName());
            if (tList.getID() == currentCourse.getTermID())
                terms = tList;
        }

        // Term Spinner
        ArrayAdapter<String> termArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_selected_item, termArrayList);
        termArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items);
        editTerm.setAdapter(termArrayAdapter);
        if (terms != null)
            editTerm.setSelection(termArrayAdapter.getPosition(terms.getTermName()));


        // Date pickers
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

    // Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        return true;
    }

    // Menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.courseDelete:
                repository.delete(currentCourse);
                Toast.makeText(CourseDetails.this, currentCourse.getCourseName() + " was deleted!", Toast.LENGTH_LONG).show();
                this.finish();
                return true;
            case R.id.courseShareNotes:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.courseNotifyStart:
                String startDateFromScreen = editStartDate.getText().toString();
                Date myDate = null;
                try {
                    myDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
                intent.putExtra("key", "Your next course " + editName.getText().toString() + " starts today!");
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, ++MainScreen.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.courseNotifyEnd:
                String endDateFromScreen = editEndDate.getText().toString();
                Date myDate1 = null;
                try {
                    myDate1 = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger1 = myDate1.getTime();
                intent = new Intent(CourseDetails.this, MyReceiver.class);
                intent.putExtra("key", "Your course " + editName.getText().toString() + " ends today!");
                PendingIntent sender1 = PendingIntent.getBroadcast(CourseDetails.this, ++MainScreen.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, trigger1, sender1);
                return true;
        }

        return super.onOptionsItemSelected(item);
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