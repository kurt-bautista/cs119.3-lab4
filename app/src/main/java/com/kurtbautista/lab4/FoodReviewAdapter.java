package com.kurtbautista.lab4;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Student on 6/30/2016.
 */
public class FoodReviewAdapter extends RealmBaseAdapter<FoodReview> {

    private Activity context;
    private RealmResults<FoodReview> foodReviews;

    public FoodReviewAdapter(Activity context, RealmResults<FoodReview> foodReviews)
    {
        super(context, foodReviews);
        this.context = context;
        this.foodReviews = foodReviews;
    }

    @Override
    public int getCount() {
        return foodReviews.size() + 1;
    }

    @Override
    public FoodReview getItem(int i) {
        if(i < foodReviews.size()) return foodReviews.get(i);
        else return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(i < foodReviews.size()) {
            View v = context.getLayoutInflater().inflate(R.layout.review_row, null);

            ImageView img = (ImageView) v.findViewById(R.id.imageView);
            TextView name = (TextView) v.findViewById(R.id.foodNameText);
            TextView user = (TextView) v.findViewById(R.id.reviewByText);
            TextView price = (TextView) v.findViewById(R.id.foodPriceText);
            CheckBox[] rating = new CheckBox[]{(CheckBox) v.findViewById(R.id.oneCheckBox), (CheckBox) v.findViewById(R.id.twoCheckBox),
                    (CheckBox) v.findViewById(R.id.threeCheckBox), (CheckBox) v.findViewById(R.id.fourCheckBox), (CheckBox) v.findViewById(R.id.fiveCheckBox)};
            Button description = (Button) v.findViewById(R.id.descriptionButton);
            Button edit = (Button) v.findViewById(R.id.editButton);
            Button delete = (Button) v.findViewById(R.id.deleteButton);

            FoodReview review = foodReviews.get(i);

            //Picasso.with(context).load(review.getThumbnail()).fit().into(img);
            Bitmap b = BitmapFactory.decodeFile(review.getThumbnail());
            img.setImageBitmap(b);
            name.setText(review.getName());
            user.setText(review.getUser());
            price.setText("Php " + review.getPrice());
            for (int x = 0; x < review.getRating(); x++) {
                rating[x].setChecked(true);
            }

            description.setTag(review.getId());
            edit.setTag(review.getId());
            delete.setTag(review.getId());

            return v;
        }
        else {
            return context.getLayoutInflater().inflate(R.layout.button_row, null);
        }
    }
}
