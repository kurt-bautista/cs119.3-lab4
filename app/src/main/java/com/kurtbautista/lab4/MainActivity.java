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
    private final int NEW_REVIEW = 1;
    private final int EDIT_REVIEW = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView)findViewById(R.id.reviewsListView);
        adapter = new FoodReviewAdapter(this, foodReviewList);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case NEW_REVIEW:
                switch (resultCode)
                {
                    case RESULT_OK:
                        foodReviewList.add((FoodReview) data.getSerializableExtra("review"));
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                break;
            case EDIT_REVIEW:
                switch (resultCode)
                {
                    case RESULT_OK:
                        FoodReview r = foodReviewList.get(data.getIntExtra("review", 0));
                        r.setName(data.getStringExtra("name"));
                        r.setUser(data.getStringExtra("user"));
                        r.setPrice(data.getDoubleExtra("price", 0));
                        r.setRating(data.getIntExtra("rating", 0));
                        r.setDescription(data.getStringExtra("desc"));
                        r.setComment(data.getStringExtra("comment"));
                        r.setFilename(data.getStringExtra("filename"));
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
        }
    }

    public void newReview(View v)
    {
        Intent i = new Intent(this, com.kurtbautista.lab4.NewEditReviewActivity.class);
        i.putExtra("action", "New food review");
        startActivityForResult(i, NEW_REVIEW);
    }

    public void openDescription(View v)
    {
        DescriptionDialog d = new DescriptionDialog(this, foodReviewList.get((Integer)v.getTag()));
        d.show();
    }

    public void editReview(View v)
    {
        Intent i = new Intent(this, com.kurtbautista.lab4.NewEditReviewActivity.class);
        i.putExtra("action", "Edit review");
        i.putExtra("review", foodReviewList.get((Integer)v.getTag()));
        i.putExtra("pos", (Integer)v.getTag());
        startActivityForResult(i, EDIT_REVIEW);
        adapter.notifyDataSetChanged();
    }

    public void deleteReview(View v)
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

    public void clearReviews(View v)
    {
        foodReviewList.clear();
        adapter.notifyDataSetChanged();
    }
}
