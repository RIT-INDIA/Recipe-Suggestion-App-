package com.example.akash.cook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> implements Filterable {

    public static List<StudentModel> studentList;
    public static List<StudentModel> ingredientsListTemp;
    private Context context;
    public static ArrayList<String> finallist = new ArrayList<String>();
    public StudentAdapter.ingredientsDataFilter ingredientsDataFilter;

    public StudentAdapter(Context context, List<StudentModel> lstStudent) {
        this.context = context;
        this.studentList = lstStudent;
        this.ingredientsListTemp=lstStudent;
    }

    public void toggleSelection(boolean isChecked) {          //select all
        if (isChecked) {
            for (int i = 0; i < studentList.size(); i++) {
                if (!(finallist.contains(studentList.get(i).getName()))) {
                    studentList.get(i).setSelected(isChecked);
                    finallist.add(studentList.get(i).getName());
                }
            }
        } else {
            for (int i = 0; i < studentList.size(); i++) {
                studentList.get(i).setSelected(isChecked);
                finallist.remove(studentList.get(i).getName());
            }
        }
        //don't forget to notify
        notifyDataSetChanged();
    }

    public Filter getFilter() {

        if (ingredientsDataFilter == null) {
            ingredientsDataFilter = new StudentAdapter.ingredientsDataFilter(ingredientsListTemp,this);
        }
        return ingredientsDataFilter;
    }


    private class ingredientsDataFilter extends Filter {    //custom filter

        StudentAdapter adapter;
        public List<StudentModel> ingredientsListTemp;

        public ingredientsDataFilter(List<StudentModel> ingredientsListTemp, StudentAdapter adapter)
        {
            this.adapter=adapter;
            this.ingredientsListTemp=ingredientsListTemp;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                List<StudentModel> arrayList1 = new ArrayList<StudentModel>();

                for (int k = 0;k<ingredientsListTemp.size();  k++) {
                    if (ingredientsListTemp.get(k).getName().toString().toLowerCase().contains(charSequence)) {
                        arrayList1.add(ingredientsListTemp.get(k));
                    }
                }
                filterResults.count = arrayList1.size();
                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = ingredientsListTemp;
                    filterResults.count = ingredientsListTemp.size();
                }
            }
            return filterResults;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            studentList = (List<StudentModel>)filterResults.values;
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView recycler_text_view;
            public CheckBox recycler_checkbox;

            public ViewHolder(View view) {
                super(view);
                recycler_text_view = (TextView) view.findViewById(R.id.recycler_text_view);
                recycler_checkbox = (CheckBox) view.findViewById(R.id.recycler_checkbox);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            if (studentList != null) {
                //in some cases, it will prevent unwanted situations
                holder.recycler_checkbox.setOnCheckedChangeListener(null);
                holder.recycler_text_view.setText(studentList.get(position).getName());
                holder.recycler_checkbox.setChecked(studentList.get(position).isSelected());
                holder.recycler_checkbox.setTag(studentList.get(position));

           /*
           //this also works...!
            holder.recycler_checkbox.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    CheckBox cb = (CheckBox) view;
                    StudentModel stakeHolder = (StudentModel) cb.getTag();
                    stakeHolder.setSelected(cb.isChecked());
                    studentList.get(position).setSelected(cb.isChecked());
                    Toast.makeText(view.getContext(),
                            "Clicked on Checkbox: " + studentList.get(position).getUserName() + " is "
                                    + cb.isChecked(), Toast.LENGTH_LONG).show();
                }
            });
            */
                holder.recycler_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String item = studentList.get(position).getName().toString();
                        if (isChecked) {
                            studentList.get(position).setSelected(true);
                            Toast.makeText(context, "Checked : " + studentList.get(position).getName(), Toast.LENGTH_LONG).show();
                            if (!(finallist.contains(studentList.get(position).getName().toString()))) {
                                finallist.add(item);
                            }
                        } else {
                            studentList.get(position).setSelected(false);
                            Toast.makeText(context, "Unchecked : " + studentList.get(position).getName(), Toast.LENGTH_LONG).show();
                            finallist.remove(item);
                        }
                    }
                });

            }
        }

    @Override
    public int getItemCount() {
        return (studentList == null) ? 0 : studentList.size();
    }
}