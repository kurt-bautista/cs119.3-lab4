package com.kurtbautista.lab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

public class NewEditReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_review);
        String action = getIntent().getStringExtra("action");
        setTitle(action);
        if(action.equals("Edit review"))
        {
            FoodReview review = (FoodReview) getIntent().getSerializableExtra("review");
            EditText name = (EditText)findViewById(R.id.foodName);
            EditText user = (EditText)findViewById(R.id.userName);
            EditText price = (EditText)findViewById(R.id.foodPrice);
            EditText desc = (EditText)findViewById(R.id.description);
            EditText comment = (EditText)findViewById(R.id.comment);
            ImageButton img = (ImageButton)findViewById(R.id.changeImageButton);
            RatingBar rating = (RatingBar)findViewById(R.id.ratingBar);

            name.setText(review.getName());
            user.setText(review.getUser());
            price.setText(String.valueOf(review.getPrice()));
            desc.setText(review.getDescription());
            comment.setText(review.getComment());
            //set img src
            rating.setRating(review.getRating());
        }
    }

    private void saveReview(View v)
    {
        try
        {
            if (getTitle().equals("Edit review")) {
                //dialog
            }
        }
        catch (NullPointerException e)
        {

        }
    }
}
