package com.example.myandroidapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myandroidapp.R;
import com.example.myandroidapp.entities.Courses;
import com.example.myandroidapp.entities.Terms;


import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private List<Courses> mCourses;
    private final Context context;
    private final LayoutInflater mInflator;

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Courses current = mCourses.get(position);

                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("ID", current.getID());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("courseStartDate", current.getCourseStartDate());
                    intent.putExtra("courseEndDate", current.getCourseEndDate());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("ciName", current.getCiName());
                    intent.putExtra("ciPhone", current.getCiPhone());
                    intent.putExtra("ciEmail", current.getCiEmail());
                    intent.putExtra("notes", current.getNotes());
                    intent.putExtra("startAlert", current.getStartAlert());
                    intent.putExtra("endAlert", current.getEndAlert());
                    intent.putExtra("termID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }
    public CourseListAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseListAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListAdapter.CourseViewHolder holder, int position) {
        if(mCourses != null) {
            Courses current = mCourses.get(position);
            String name = current.getCourseName();
            holder.courseItemView.setText(name);
        } else {
            holder.courseItemView.setText("No Course Name");
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
        }
    public void setCourses(List<Courses> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }
}