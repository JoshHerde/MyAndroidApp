package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myandroidapp.Database.AppDatabaseBuilder;
import com.example.myandroidapp.Database.Repository;
import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Terms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {


    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    DatePickerDialog.OnDateSetListener termStartDate;
    DatePickerDialog.OnDateSetListener termEndDate;
    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    String termName, startDate, endDate;
    int termID;
    Terms terms;
    Repository repository;
    CourseListAdapter courseListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);



        views();
        intentValues();

        termStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, day);

                sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                editStartDate.setText(sdf.format(calendarStart.getTime()));
            }
        };

        termEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, month);
                calendarEnd.set(Calendar.DAY_OF_MONTH, day);

                sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                editEndDate.setText(sdf.format(calendarEnd.getTime()));
            }
        };
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

        // Save Button
        Button termSaveButton = findViewById(R.id.termSaveButton);
        termSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        terms = new Terms(termID,
                                editName.getText().toString(),
                                editStartDate.getText().toString(),
                                editEndDate.getText().toString()
                        );
                        repository.update(terms);
                        //Toast.makeText(this, "Term is updated!", Toast.LENGTH_LONG).show();
                    }

            }
        });


        // Cancel Button
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

    private void listeners() {
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermDetails.this, termStartDate, calendarStart.get(Calendar.YEAR),
                        calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(TermDetails.this, termEndDate, calendarEnd.get(Calendar.YEAR),
                        calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    /* @Override
    public boolean OnOptionsDeleteSelected(MenuItem item) {

        return true;
    }

     */

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerViewForTerm);
        courseListAdapter = new CourseListAdapter(this);
        recyclerView.setAdapter(courseListAdapter);
    }
}