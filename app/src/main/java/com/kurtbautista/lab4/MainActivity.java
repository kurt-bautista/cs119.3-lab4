package com.kurtbautista.lab4;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

        ListView lv = (ListView)findViewById(R.id.reviewsListView);
        adapter = new FoodReviewAdapter(this, foodReviewList);
        lv.setAdapter(adapter);
    }

    private void newReview(View v)
    {
        Intent i = new Intent(this, com.kurtbautista.lab4.NewEditReviewActivity.class);
        i.putExtra("action", "New food review");
        startActivity(i);
        adapter.notifyDataSetChanged();
    }

    private void openDescription(View v)
    {
        DescriptionDialog d = new DescriptionDialog(this, foodReviewList.get((Integer)v.getTag()));
        d.show();
    }

    private void editReview(View v)
    {
        Intent i = new Intent(this, com.kurtbautista.lab4.NewEditReviewActivity.class);
        i.putExtra("action", "Edit review");
        i.putExtra("review", foodReviewList.get((Integer)v.getTag()));
        startActivity(i);
        adapter.notifyDataSetChanged();
    }

    private void deleteReview(View v)
    {
        final View view = v;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this review?")
                .setCancelable(false)
                .setPositiveButton("DELETE!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int x = (Integer)view.getTag();
                        foodReviewList.remove(x);
                        adapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void clearReviews(View v)
    {
        foodReviewList.clear();
        adapter.notifyDataSetChanged();
    }
}
