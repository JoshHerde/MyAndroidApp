package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myandroidapp.Database.AppDatabaseBuilder;
import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Terms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    /*
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    EditText termNameEdit;
    EditText termStartDateEdit;
    EditText termEndDateEdit;

    Terms selectedTerm = new Terms();
    AppDatabaseBuilder adp = AppDatabaseBuilder.getDatabase(this);
    int termID;
    List<Courses> allCourses = adp.coursesDAO().getAllCourses();

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);


        Button termCancelButton = findViewById(R.id.termCancelButton);
        termCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetails.this, TermList.class);
                TermDetails.this.startActivity(intent);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }
}