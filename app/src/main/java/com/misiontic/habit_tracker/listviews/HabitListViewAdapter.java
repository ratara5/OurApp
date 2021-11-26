package com.misiontic.habit_tracker.listviews;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.misiontic.habit_tracker.R;

import java.util.ArrayList;

public class HabitListViewAdapter extends ArrayAdapter<String> {

    ArrayList<String> list;
    Context context;

    public HabitListViewAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.habits_list_row, items);
        this.context=context;
        list=items;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
       if(convertView==null){
           LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
           convertView=mInflater.inflate(R.layout.habits_list_row, null);
       }
       TextView tvHabitName=convertView.findViewById(R.id.tvName);
       TextView tvCategoryName=convertView.findViewById(R.id.tvCategory);

       tvHabitName.setText(list.get(position));

       return convertView;

    }


}
