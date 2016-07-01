package com.kurtbautista.lab4;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionDialog extends Dialog {

    private FoodReview review;

    public DescriptionDialog(Context c, FoodReview review)
    {
        super(c);
        setTitle("More info");
        this.review = review;
    }

    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.description_dialog);

        TextView foodName = (TextView)findViewById(R.id.foodNameText2);
        ImageView img = (ImageView)findViewById(R.id.imageView2);
        TextView desc = (TextView)findViewById(R.id.descriptionText);
        TextView commment = (TextView)findViewById(R.id.commentText);

        foodName.setText(review.getName());
        //img
        desc.setText(review.getDescription());
        commment.setText(review.getComment());
    }

}
