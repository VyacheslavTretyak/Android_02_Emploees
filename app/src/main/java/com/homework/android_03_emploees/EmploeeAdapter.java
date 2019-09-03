package com.homework.android_03_emploees;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class EmploeeAdapter extends ArrayAdapter<Emploee> {
    private MainActivity mainActivity;
    private int resourceId;
    private List<Emploee> emploees;
    public EmploeeAdapter(Context context, int resource, List<Emploee> objects) {
        super(context, resource, objects);
        this.mainActivity = (MainActivity)context;
        emploees = objects;
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Emploee emploee = emploees.get(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView pic = (ImageView) view.findViewById(R.id.pic);
        TextView firstName = (TextView) view.findViewById(R.id.tvFirstName);
        TextView lastName = (TextView) view.findViewById(R.id.tvLastName);
        TextView birthDate = (TextView) view.findViewById(R.id.tvBirthDate);
        if(emploee.gender){
            pic.setImageBitmap(mainActivity.manPic);
        }else{
            pic.setImageBitmap(mainActivity.womenPic);
        }
        firstName.setText(emploee.firstName);
        lastName.setText(emploee.lastName);
        birthDate.setText(emploee.getBirthDayString());
        return view;
    }
}
