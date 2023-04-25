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
import com.example.myandroidapp.entities.Assessments;

import java.util.List;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentViewHolder> {

    private List<Assessments> mAssessments;
    private final Context context;
    private final LayoutInflater mInflator;

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessments current = mAssessments.get(position);

                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("ID", current.getID());
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("assessmentStartDate", current.getAssessmentStartDate());
                    intent.putExtra("assessmentEndDate", current.getAssessmentEndDate());
                    intent.putExtra("courseID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }
    public AssessmentListAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public AssessmentListAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentListAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments != null) {
            Assessments current = mAssessments.get(position);
            String name = current.getAssessmentName();
            holder.assessmentItemView.setText(name);
        } else {
            holder.assessmentItemView.setText("No Assessment Name");
        }
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }
    public void setAssessments(List<Assessments> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
