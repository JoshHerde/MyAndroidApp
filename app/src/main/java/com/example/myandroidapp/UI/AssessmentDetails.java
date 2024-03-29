package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.myandroidapp.entities.Assessments;
import com.example.myandroidapp.entities.Courses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        // Layout
        editName = findViewById(R.id.assessmentName);
        editType = findViewById(R.id.assessmentType);
        editStartDate = findViewById(R.id.assessmentStartDate);
        editEndDate = findViewById(R.id.assessmentEndDate);
        editCourse = findViewById(R.id.courseSpinner);

        // Save Button
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

                if (editName.getText().toString().equals("") || editType.getText().toString().equals("") || editStartDate.getText().toString().equals("") ||
                        editEndDate.getText().toString().equals("")) {
                    Toast.makeText(AssessmentDetails.this, "Fill out all above fields.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (currentAssessment.getID() == -1) {
                    currentAssessment.setID(0);
                    repository.insert(currentAssessment);
                    Toast.makeText(AssessmentDetails.this, editName.getText().toString() + " was created!", Toast.LENGTH_LONG).show();
                } else {
                    repository.update(currentAssessment);
                    Toast.makeText(AssessmentDetails.this, editName.getText().toString() + " was updated!", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

        // Cancel Button
        Button assessmentCancelButton = findViewById(R.id.assessmentCancelButton);
        assessmentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                AssessmentDetails.this.startActivity(intent);
            }
        });


        // Intent values
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

        // Course List
        ArrayList<String> courseArrayList = new ArrayList<>();
        for (Courses cList : repository.getAllCourses()) {
            courseArrayList.add(cList.getCourseName());
            if (cList.getID() == currentAssessment.getCourseID())
                courses = cList;
        }

        // Course Spinner
        ArrayAdapter<String> courseArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_selected_item, courseArrayList);
        courseArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items);
        editCourse.setAdapter(courseArrayAdapter);

        // Set values
        editName.setText(currentAssessment.getAssessmentName());
        editType.setText(currentAssessment.getAssessmentType());
        editStartDate.setText(currentAssessment.getAssessmentStartDate());
        editEndDate.setText(currentAssessment.getAssessmentEndDate());
        if (courses != null)
            editCourse.setSelection(courseArrayAdapter.getPosition(courses.getCourseName()));


        // Date pickers
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

    // Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    // Menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.assessmentDelete:
                repository.delete(currentAssessment);
                Toast.makeText(AssessmentDetails.this, currentAssessment.getAssessmentName() + " was deleted!", Toast.LENGTH_LONG).show();
                this.finish();
                return true;
            case R.id.assessmentNotifyStart:
                String startDateFromScreen = editStartDate.getText().toString();
                Date myDate = null;
                try {
                    myDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", "Your assessment " + editName.getText().toString() + " starts today!");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainScreen.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.assessmentNotifyEnd:
                String endDateFromScreen = editEndDate.getText().toString();
                Date myDate1 = null;
                try {
                    myDate1 = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger1 = myDate1.getTime();
                intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", "Your assessment " + editName.getText().toString() + " ends today!");
                PendingIntent sender1 = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainScreen.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, trigger1, sender1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}