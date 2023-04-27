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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myandroidapp.Database.Repository;
import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Terms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {


    EditText editName;
    EditText editStartDate;
    EditText editEndDate;

    DatePickerDialog.OnDateSetListener termDPStartDate;
    DatePickerDialog.OnDateSetListener termDPEndDate;
    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    String termName, startDate, endDate;
    int termID;
    Terms currentTerm;
    Repository repository;
    CourseListAdapter courseListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);



        views();
        intentValues();
        datePickers();


    }

    private void views(){
        editName = findViewById(R.id.termName);
        editStartDate = findViewById(R.id.termStartDate);
        editEndDate = findViewById(R.id.termEndDate);


        // Associated Courses Recycler View
        repository = new Repository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerViewForTerm);
        courseListAdapter = new CourseListAdapter(this);
        recyclerView.setAdapter(courseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Courses> termCourses = new ArrayList<>();
        for (Courses courses : repository.getAllCourses()) {
            if (courses.getTermID() == termID)
                termCourses.add(courses);
        }
        courseListAdapter.setCourses(termCourses);

        // Save Button
        Button termSaveButton = findViewById(R.id.termSaveButton);
        termSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editName.getText().toString().equals("") || editStartDate.getText().toString().equals("") || editEndDate.getText().toString().equals(""));
                //Toast.makeText(this, "Fill out all above fields.", Toast.LENGTH_LONG).show();

                if(termID == -1) {
                    currentTerm = new Terms(0, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repository.insert(currentTerm);
                    //Toast.makeText(this, "Term was created!", Toast.LENGTH_LONG).show();
                } else {
                    currentTerm = new Terms(termID, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repository.update(currentTerm);
                    //Toast.makeText(this, "Term was updated!", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

        Button termCancelButton = findViewById(R.id.termCancelButton);
        termCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, TermList.class);
                TermDetails.this.startActivity(intent);
            }
        });
    }

    private void intentValues() {

        termID = getIntent().getIntExtra("ID", -1);
        termName = getIntent().getStringExtra("termName");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");


        editName.setText(termName);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        repository = new Repository(getApplication());

    }

    private void datePickers() {
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermDetails.this, termDPStartDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termDPStartDate = new DatePickerDialog.OnDateSetListener() {
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
                new DatePickerDialog(TermDetails.this, termDPEndDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        termDPEndDate = new DatePickerDialog.OnDateSetListener() {
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
        getMenuInflater().inflate(R.menu.menu_term_details, menu);
        return true;

    }

    public boolean OnOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.termDelete:
                for (Terms t : repository.getAllTerms()) {
                    if (t.getID() == termID) currentTerm = t;
                }

                int associatedCourses = 0;
                for (Courses courses : repository.getAllCourses()) {
                    if (courses.getTermID() == termID) ++associatedCourses;
                }

                if (associatedCourses == 0) {
                    repository.delete(currentTerm);
                    Toast.makeText(TermDetails.this, currentTerm.getTermName() + " was deleted!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(TermDetails.this, "Cant delete a Term with associated Courses.", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.termNotifyStart:
                String startDateFromScreen = editStartDate.getText().toString();
                Date myDate = null;
                try {
                    myDate = sdf.parse(startDateFromScreen);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(TermDetails.this, MyReceiver.class);
                intent.putExtra("key", "Your next term " + editName.getText().toString() + " starts today!");
                PendingIntent sender = PendingIntent.getBroadcast(TermDetails.this, ++MainScreen.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
            case R.id.termNotifyEnd:
                return false;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerViewForTerm);
        courseListAdapter = new CourseListAdapter(this);
        recyclerView.setAdapter(courseListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Courses> termCourses = new ArrayList<>();
        for (Courses courses : repository.getAllCourses()) {
            if (courses.getTermID() == termID)
                termCourses.add(courses);
        }
        courseListAdapter.setCourses(termCourses);
    }
}

      /*
                if(editName.getText().toString().equals("") || editStartDate.getText().toString().equals("") || editEndDate.getText().toString().equals(""))
                    //Toast.makeText(this, "Fill out all above fields.", Toast.LENGTH_LONG).show();

                    if(termID == -1) {
                        terms = new Terms(
                                0,
                                editName.getText().toString(),
                                editStartDate.getText().toString(),
                                editEndDate.getText().toString()
                        );
                        repository.insert(terms);
                        //Toast.makeText(this, "Term is created!", Toast.LENGTH_LONG).show();
                    } else {
                        terms = new Terms(
                                termID,
                                editName.getText().toString(),
                                editStartDate.getText().toString(),
                                editEndDate.getText().toString()
                        );
                        repository.update(terms);
                        //Toast.makeText(this, "Term is updated!", Toast.LENGTH_LONG).show();
                    }
                finish();
            }
        });

        */