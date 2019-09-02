package com.homework.android_03_emploees;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = this.findViewById(R.id.list);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);



        ArrayList<Emploee> emploees = new ArrayList<>();
        ArrayList<String> arr = new ArrayList<String>(Arrays.asList(
                "Geeks",
                "for",
                "Geeks","alskd","alksjdf","lkaejrlk"));


        Emploee e1 = new Emploee("firstName1", "lastName1", true, 10, 3, 1986);
        Emploee e2 = new Emploee("firstName2", "lastName2", true, 20, 10, 1983);
        Emploee e3 = new Emploee("firstName3", "lastName3", false, 30, 11, 1991);
        Emploee e4 = new Emploee("firstName4", "lastName4", true, 15, 6, 1999);

        emploees.add(e1);
        emploees.add(e2);
        emploees.add(e3);
        emploees.add(e4);

        ArrayAdapter<Emploee> adapter = new ArrayAdapter<Emploee>(this, R.layout.activity_emploee, emploees);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter1);
    }
}
