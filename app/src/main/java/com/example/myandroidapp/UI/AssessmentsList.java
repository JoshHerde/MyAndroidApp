package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myandroidapp.Database.Repository;
import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Assessments;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AssessmentsList extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_list);

        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentListAdapter assessmentListAdapter = new AssessmentListAdapter(this);
        recyclerView.setAdapter(assessmentListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Assessments> assessments = repository.getAllAssessments();
        assessmentListAdapter.setAssessments(assessments);

        FloatingActionButton FABAddAssessment = findViewById(R.id.FABAddAssessment);
        FABAddAssessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentsList.this, AssessmentDetails.class);
                AssessmentsList.this.startActivity(intent);
            }
        });
    }
}