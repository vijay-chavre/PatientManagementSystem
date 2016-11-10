package com.example.vijayc.patientmanagementsystem.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vijayc.patientmanagementsystem.R;
import com.example.vijayc.patientmanagementsystem.models.PatientProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

/**
 * Created by vijayc on 09/11/16.
 */

public class PatientListDataAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public PatientListDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static  class LayoutHandler{
        TextView fname,village;

    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {

        return list.size();

    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row= convertView;
        LayoutHandler layoutHandler;
        if(row==null){
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_example_entry,parent,false);
            layoutHandler =new LayoutHandler();
            layoutHandler.fname=(TextView) row.findViewById(R.id.name_entry);
            layoutHandler.village=(TextView) row.findViewById(R.id.village_entry);
            row.setTag(layoutHandler);

        }

        else{
            layoutHandler =(LayoutHandler) row.getTag();

        }
        PatientProvider patientProvider =(PatientProvider) this.getItem(position);
        layoutHandler.fname.setText(patientProvider.getFname());
        layoutHandler.village.setText(patientProvider.getVillage());

        return row;

    }
}
