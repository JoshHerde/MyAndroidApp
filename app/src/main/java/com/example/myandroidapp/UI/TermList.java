package com.example.myandroidapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myandroidapp.Database.Repository;
import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Terms;

import java.util.List;

public class TermList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        Repository repository = new Repository(getApplication());
        List<Terms> terms = repository.getAllTerms();

        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        final TermListAdapter termListAdapter = new TermListAdapter(this);
        recyclerView.setAdapter(termListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termListAdapter.setTerms(terms);






    }
}