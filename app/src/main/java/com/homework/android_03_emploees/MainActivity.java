package com.homework.android_03_emploees;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private int curItem = -1;
    private View curView = null;

    public Bitmap manPic;
    public Bitmap womenPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = this.findViewById(R.id.list);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        String jsonString = "{\"emploees\":[{\"firstName\":\"firstName1\", \"lastName\":\"lastName1\",\"gender\":\"true\",\"day\":\"11\",\"month\":\"3\",\"year\":\"1987\"},{\"firstName\":\"firstName2\", \"lastName\":\"lastName2\",\"gender\":\"true\",\"day\":\"17\",\"month\":\"11\",\"year\":\"1971\"},{\"firstName\":\"firstName3\", \"lastName\":\"lastName3\",\"gender\":\"false\",\"day\":\"13\",\"month\":\"1\",\"year\":\"1993\"},{\"firstName\":\"firstName1\", \"lastName\":\"lastName1\",\"gender\":\"true\",\"day\":\"23\",\"month\":\"7\",\"year\":\"1997\"}]}";

        DataManager dataManager = new DataManager(this);
        dataManager.create(jsonString);
        JSONObject obj = dataManager.getObj();

        ArrayList<Emploee> emploees = new ArrayList<>();
        try {
            JSONArray arr = obj.getJSONArray("emploees");
            for (int i = 0; i<arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                emploees.add(new Emploee(o.getString("firstName"),
                        o.getString("lastName"),
                        o.getBoolean("gender"),
                        o.getInt("day"),
                        o.getInt("month"),
                        o.getInt("year")));
            }
        }catch (Exception ex){
            return;
        }

        EmploeeAdapter adapter = new EmploeeAdapter(this, R.layout.activity_emploee, emploees);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (MainActivity.this.curItem != -1){
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
}
