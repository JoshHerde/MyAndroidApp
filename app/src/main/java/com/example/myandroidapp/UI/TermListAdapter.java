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
import com.example.myandroidapp.entities.Terms;

import java.util.List;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {

    private List<Terms> mTerms;
    private final Context context;
    private final LayoutInflater mInflator;


    class TermViewHolder extends RecyclerView.ViewHolder {

        private final TextView termItemView;
        private TermViewHolder(View itemview) {
            super(itemview);
            termItemView = itemview.findViewById(R.id.textView2);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Terms current = mTerms.get(position);

                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("ID", current.getID());
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("startDate", current.getTermStartDate());
                    intent.putExtra("endDate", current.getTermEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    public TermListAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermListAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermListAdapter.TermViewHolder holder, int position) {
        if(mTerms != null) {
            Terms current = mTerms.get(position);
            String name = current.getTermName();
            holder.termItemView.setText(name);
        } else {
            holder.termItemView.setText("No Term Name");
        }

    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }
    public void setTerms(List<Terms> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }


}
