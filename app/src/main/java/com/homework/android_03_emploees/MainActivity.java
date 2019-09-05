package com.homework.android_03_emploees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private TextView tvBirthdate;
    private AlertDialog dialog;
    private EditText etFirstName;
    private EditText etLastName;
    private RadioButton radioMale;
    private RadioButton radioFemale;
    private Emploee currentEmploee;
    private int curItem = -1;
    private View curView = null;
    private EmploeeAdapter adapter;
    private int year = 0;
    private int month = 0;
    private int day = 0;
    private boolean isEdit = false;

    public Bitmap manPic;
    public Bitmap womenPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (year == 0 || month == 0 || day == 0) {
            Calendar C = Calendar.getInstance();
            year = C.get(Calendar.YEAR);
            month = C.get(Calendar.MONTH);
            day = C.get(Calendar.DAY_OF_MONTH);
        }



        listView = this.findViewById(R.id.list);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);



        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);
        builder.setTitle("New emploee");
        LayoutInflater inflater	= this.getLayoutInflater();
        final View view	= inflater.inflate(R.layout.activity_edit,null,false);
        builder.setView(view);
        etFirstName = (EditText) view.findViewById(R.id.etFirstName);
        etLastName = (EditText) view.findViewById(R.id.etLastName);
        radioMale = view.findViewById(R.id.radio_male);
        radioFemale = view.findViewById(R.id.radio_female);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(!isEdit) {
                    Emploee emploee = new Emploee(etFirstName.getText().toString(), etLastName.getText().toString(), radioMale.isChecked(), day, month, year);
                    adapter.add(emploee);
                }else{
                    currentEmploee.firstName = etFirstName.getText().toString();
                    currentEmploee.lastName = etLastName.getText().toString();
                    currentEmploee.gender = radioMale.isChecked();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DATE, day);
                    currentEmploee.birthDay = calendar;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,	int	id){

            }
        });
        dialog = builder.create();

        tvBirthdate = view.findViewById(R.id.textview_birthdate);
        tvBirthdate.setText(getBirthDateString());

        String jsonString = "{\"emploees\":[{\"firstName\":\"firstName1\", \"lastName\":\"lastName1\",\"gender\":\"true\",\"day\":\"11\",\"month\":\"3\",\"year\":\"1987\"},{\"firstName\":\"firstName2\", \"lastName\":\"lastName2\",\"gender\":\"true\",\"day\":\"17\",\"month\":\"11\",\"year\":\"1971\"},{\"firstName\":\"firstName3\", \"lastName\":\"lastName3\",\"gender\":\"false\",\"day\":\"13\",\"month\":\"1\",\"year\":\"1993\"},{\"firstName\":\"firstName1\", \"lastName\":\"lastName1\",\"gender\":\"true\",\"day\":\"23\",\"month\":\"7\",\"year\":\"1997\"}]}";

        DataManager dataManager = new DataManager(this);
        dataManager.create(jsonString);
        JSONObject obj = dataManager.getObj();

        ArrayList<Emploee> emploees = new ArrayList<>();
        try {
            JSONArray arr = obj.getJSONArray("emploees");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                emploees.add(new Emploee(o.getString("firstName"),
                        o.getString("lastName"),
                        o.getBoolean("gender"),
                        o.getInt("day"),
                        o.getInt("month"),
                        o.getInt("year")));
            }
        } catch (Exception ex) {
            return;
        }

        adapter = new EmploeeAdapter(this, R.layout.activity_emploee, emploees);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (MainActivity.this.curItem != -1) {
                    MainActivity.this.curView.setBackgroundColor(Color.parseColor("#EEEEEE"));
                }
                MainActivity.this.curItem = position;
                MainActivity.this.curView = view;
                MainActivity.this.curView.setBackgroundColor(Color.parseColor("#aEEEaE"));
            }
        });


        AssetManager AM = this.getAssets();

        try {
            InputStream IS = AM.open("man.png");
            manPic = BitmapFactory.decodeStream(IS);
            IS = AM.open("women.png");
            womenPic = BitmapFactory.decodeStream(IS);
        } catch (IOException ioe) {
            Log.println(Log.ERROR, "error", ioe.getMessage());
        }
    }

    private String getBirthDateString(){
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append("-");
        sb.append(month);
        sb.append("-");
        sb.append(day);
        return sb.toString();
    }

    private String getBirthDateString(Calendar calendar){
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
        return getBirthDateString();
    }

    public void onClickSetBirthdate(View view){

        DatePickerDialog DPD = new DatePickerDialog(this, null, year, month, day) {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                MainActivity.this.year = year;
                MainActivity.this.month = month;
                MainActivity.this.day = day;
            }
        };
        DPD.setButton(DialogInterface.BUTTON_POSITIVE, "Choose", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvBirthdate.setText(getBirthDateString());
            }
        });
        DPD.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        DPD.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_add:
                isEdit = false;
                dialog.show();
                break;
            case R.id.action_edit:
                if(curItem == -1){
                    break;
                }
                isEdit = true;
                currentEmploee = adapter.getItem(curItem);
                if(currentEmploee.gender) {
                    radioMale.setChecked(true);
                    radioFemale.setChecked(false);
                }else{
                    radioMale.setChecked(false);
                    radioFemale.setChecked(true);
                }
                etFirstName.setText(currentEmploee.firstName);
                etLastName.setText(currentEmploee.lastName);
                tvBirthdate.setText(getBirthDateString(currentEmploee.birthDay));
                dialog.show();
                break;
            case R.id.action_delete:
                adapter.remove(adapter.getItem(MainActivity.this.curItem));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}