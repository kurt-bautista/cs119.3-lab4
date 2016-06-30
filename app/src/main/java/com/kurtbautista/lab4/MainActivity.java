package com.kurtbautista.lab4;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Activity context;
    private ArrayList<FoodReview> foodReviewList = new ArrayList<>();
    private FoodReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView)findViewById(R.id.listView);
        adapter = new FoodReviewAdapter(this, foodReviewList);
        lv.setAdapter(adapter);
    }

    private void openDescription(View v)
    {
        //Open activity with description
    }

    private void editReview(View v)
    {
        //Edit dialog
    }

    private void deleteReview(View v)
    {
        //Add an alert dialog before delete
        int i = (Integer)v.getTag();
        foodReviewList.remove(i);
        adapter.notifyDataSetChanged();
    }
}
