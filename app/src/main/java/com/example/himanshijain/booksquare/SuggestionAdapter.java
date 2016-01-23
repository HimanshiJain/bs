package com.example.himanshijain.booksquare;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Himanshi Jain on 1/19/2016.
 */
public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder> implements Filterable{


    ArrayList<String> data;
    Context context;
    FragmentActivity fragmentActivity;

    SuggestionAdapter(ArrayList<String> data, Context context, FragmentActivity fragmentActivity,String subject){
        this.data=data;
        this.context=context;
        this.fragmentActivity=fragmentActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.suggestion_item, viewGroup, false);
        final ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TextView tv= (TextView)viewHolder.v.findViewById(R.id.suggestion_text);
        tv.setText(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View v;
        public ViewHolder(View v) {
            super(v);
            this.v = v;
        }
    }
}
