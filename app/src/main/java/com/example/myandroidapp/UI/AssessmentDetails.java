package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myandroidapp.R;

public class AssessmentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);









        Button assessmentCancelButton = findViewById(R.id.assessmentCancelButton);
        assessmentCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentDetails.this, AssessmentList.class);
                AssessmentDetails.this.startActivity(intent);
            }
        });
    }
}