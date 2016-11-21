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
import com.example.vijayc.patientmanagementsystem.models.VisitsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by vijayc on 19/11/16.
 */

public class VisitsListAdapter extends ArrayAdapter {
    List list=new ArrayList<>();
    public VisitsListAdapter(Context context, int resource) {
        super(context, resource);
    }
    static  class LayoutHandler{
        TextView name,date;
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
            row = layoutInflater.inflate(R.layout.visit_list_item,parent,false);
            layoutHandler =new LayoutHandler();
            layoutHandler.name=(TextView) row.findViewById(R.id.patient_name);
            layoutHandler.date=(TextView) row.findViewById(R.id.date);
            row.setTag(layoutHandler);

        }

        else{
            layoutHandler =(LayoutHandler) row.getTag();

        }

        VisitsProvider visitsProvider =(VisitsProvider) this.getItem(position);
            layoutHandler.name.setText(toTitleCase(visitsProvider.getName()));
            layoutHandler.date.setText(visitsProvider.getDate());

        return row;

    }


//    public void filter(String charText) {
//
//        charText = charText.toLowerCase(Locale.getDefault());
//        if (charText.length() == 0) {
//            parkingList.addAll(list);
//
//        } else {
//            for (PatientProvider postDetail : list) {
//                if (charText.length() != 0 && postDetail.getPostTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    parkingList.add(postDetail);
//                } else if (charText.length() != 0 && postDetail.getPostSubTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    parkingList.add(postDetail);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

    // metho to capitalise each word

    public static String toTitleCase(String givenString) {
        String[] arr = givenString.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                    .append(arr[i].substring(1)).append(" ");
        }
        return sb.toString().trim();
    }
}
