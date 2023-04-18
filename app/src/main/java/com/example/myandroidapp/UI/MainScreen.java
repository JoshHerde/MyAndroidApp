package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myandroidapp.Database.Repository;
import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Terms;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Button termsButton = findViewById(R.id.termsButton);
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainScreen.this, TermList.class);
                MainScreen.this.startActivity(intent);
            }
        });
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
                Repository repository = new Repository(getApplication());
                repository.insert();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

 */
}