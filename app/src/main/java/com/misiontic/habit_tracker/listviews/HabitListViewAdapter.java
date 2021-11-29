package com.misiontic.habit_tracker.listviews;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.misiontic.habit_tracker.R;
import com.misiontic.habit_tracker.model.Habits;

import java.util.ArrayList;

public class HabitListViewAdapter extends ArrayAdapter<Habits> {

    ArrayList<Habits> list;
    Context context;

    public HabitListViewAdapter(Context context, ArrayList<Habits> items) {
        super(context, R.layout.habits_list_row, items);
        this.context=context;
        list=items;
    }

    static class ViewHolder {
        protected TextView tvHabitName,tvHabitCategory;
        protected CheckBox cbHabit;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view=null;
        if(convertView==null){
            LayoutInflater mInflater=(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view=mInflater.inflate(R.layout.habits_list_row, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.tvHabitName=(TextView) view.findViewById(R.id.tvName);
            viewHolder.tvHabitCategory=(TextView) view.findViewById(R.id.tvCategory);
            viewHolder.cbHabit=(CheckBox) view.findViewById(R.id.cbHabit);

            viewHolder.cbHabit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    Habits element = (Habits) viewHolder.cbHabit.getTag();
                    element.setSelected(buttonView.isChecked());

                }
            });
            view.setTag(viewHolder);
            viewHolder.cbHabit.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).cbHabit.setTag(list.get(position));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tvHabitName.setText(list.get(position).getName());
        holder.tvHabitCategory.setText(list.get(position).getCategory());
        holder.cbHabit.setChecked(list.get(position).isSelected());

        return view;
    }
}