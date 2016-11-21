package com.example.vijayc.patientmanagementsystem.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vijayc.patientmanagementsystem.R;
import com.example.vijayc.patientmanagementsystem.models.PatientProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.zip.Inflater;

/**
 * Created by vijayc on 09/11/16.
 */

public class PatientListDataAdapter extends BaseAdapter implements Filterable{
    ArrayList<PatientProvider> list=new ArrayList<PatientProvider>();
    CustomFilter filter;
    ArrayList<PatientProvider> filterList;
    Context c;
//    public PatientListDataAdapter(Context context, int resource) {
//        super(context, resource);
//    }

    public PatientListDataAdapter(Context context, ArrayList<PatientProvider> list) {

        this.list = list;
        this.c=context;
        this.filterList=list;

    }

    static  class LayoutHandler{
        TextView fname,village;
        ImageView img;

    }

    public void add(PatientProvider object) {

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

    @Override
    public long getItemId(int position) {
        return list.indexOf(getItem(position));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row= convertView;
        LayoutHandler layoutHandler;
        if(row==null){
           // LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater layoutInflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_example_entry,parent,false);
            layoutHandler =new LayoutHandler();
            layoutHandler.fname=(TextView) row.findViewById(R.id.name_entry);
            layoutHandler.village=(TextView) row.findViewById(R.id.village_entry);
            layoutHandler.img=(ImageView) row.findViewById(R.id.listimage);
            row.setTag(layoutHandler);

        }

        else{
            layoutHandler =(LayoutHandler) row.getTag();

        }

        PatientProvider patientProvider =(PatientProvider) this.getItem(position);
        // get the image path
//        File imgFile = new  File(patientProvider.getImagepath());
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//       Bitmap myBitmap = BitmapFactory.decodeFile(patientProvider.getImagepath(),options);

        if(patientProvider.getImagepath()==null){

            layoutHandler.fname.setText(toTitleCase(patientProvider.getFname()));
            layoutHandler.village.setText(patientProvider.getVillage());


        }else {


            Bitmap myBitmap = getImage(patientProvider.getImagepath());
            layoutHandler.fname.setText(toTitleCase(patientProvider.getFname()));
            layoutHandler.village.setText(patientProvider.getVillage());
            layoutHandler.img.setImageBitmap(myBitmap);
        }
        return row;

    }
    @Override
    public Filter getFilter() {

        if(filter==null){
            filter=new CustomFilter();
        }
        return filter;
    }

   class CustomFilter extends Filter{


       @Override
       protected FilterResults performFiltering(CharSequence constraint) {

           FilterResults results=new FilterResults();
           if(constraint!=null && constraint.length()>0){
               constraint=constraint.toString().toUpperCase();
               ArrayList<PatientProvider> filters=new ArrayList<PatientProvider>();

               for(int i=0;i<filterList.size();i++){
                   if(filterList.get(i).getFname().toUpperCase().contains(constraint)){
                       PatientProvider patient=new PatientProvider(filterList.get(i).getId(),filterList.get(i).getFname(),filterList.get(i).getMname(),filterList.get(i).getLname(),filterList.get(i).getVillage(),filterList.get(i).getMobile(),filterList.get(i).getImagepath());
                       filters.add(patient);

                   }
               }

               results.count=filters.size();
               results.values=filters;


           }else {
               results.count=filterList.size();
               results.values=filterList;


           }

           return results;
       }

       @Override
       protected void publishResults(CharSequence constraint, FilterResults results) {

           list= (ArrayList<PatientProvider>) results.values;
           notifyDataSetChanged();
       }
   }



// Convert the bytearray TO BITMAP format
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

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

/// clipping the rectagular image into 
    public static Bitmap getclip(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
