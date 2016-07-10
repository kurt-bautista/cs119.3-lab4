package com.kurtbautista.lab4;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    private Activity context;
//    private RealmList<FoodReview> foodReviewList = new RealmList<>();
    private FoodReviewAdapter adapter;
//    private final int NEW_REVIEW = 1;
//    private final int EDIT_REVIEW = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);
        Realm realm = Realm.getDefaultInstance();
        ListView lv = (ListView)findViewById(R.id.reviewsListView);
        RealmResults<FoodReview> results = realm.where(FoodReview.class).findAll();
        adapter = new FoodReviewAdapter(this, results);
        lv.setAdapter(adapter);
    }

    /*@Override
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
                        r.setThumbnail(data.getStringExtra("thumbnail"));
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
        }
    }*/

    public void newReview(View v)
    {
        Intent i = new Intent(this, com.kurtbautista.lab4.NewEditReviewActivity.class);
        i.putExtra("action", "New food review");
        startActivity(i);
    }

    public void openDescription(View v)
    {
        Realm realm = Realm.getDefaultInstance();
        FoodReview review = realm.where(FoodReview.class).equalTo("id", (String)v.getTag()).findFirst();
        DescriptionDialog d = new DescriptionDialog(this, review);
        d.show();
    }

    public void editReview(View v)
    {
        Intent i = new Intent(this, com.kurtbautista.lab4.NewEditReviewActivity.class);
        i.putExtra("action", "Edit review");
        //i.putExtra("review", foodReviewList.get((Integer)v.getTag()));
        i.putExtra("id", (String)v.getTag());
        startActivity(i);
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
                        String x = (String)view.getTag();
                        //foodReviewList.remove(x);
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        FoodReview review = realm.where(FoodReview.class).equalTo("id", x).findFirst();
                        review.deleteFromRealm();
                        realm.commitTransaction();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete all reviews?")
                .setCancelable(false)
                .setPositiveButton("DELETE!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        RealmResults<FoodReview> reviews = realm.where(FoodReview.class).findAll();
                        reviews.deleteAllFromRealm();
                        realm.commitTransaction();
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
}
