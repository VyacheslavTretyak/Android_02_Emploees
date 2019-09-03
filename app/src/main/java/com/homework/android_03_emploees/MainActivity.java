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

        ArrayList<Emploee> emploees = new ArrayList<>();

        Emploee e1 = new Emploee("firstName1", "lastName1", true, 10, 3, 1986);
        Emploee e2 = new Emploee("firstName2", "lastName2", true, 20, 10, 1983);
        Emploee e3 = new Emploee("firstName3", "lastName3", false, 30, 11, 1991);
        Emploee e4 = new Emploee("firstName4", "lastName4", true, 15, 6, 1999);

        emploees.add(e1);
        emploees.add(e2);
        emploees.add(e3);
        emploees.add(e4);

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
