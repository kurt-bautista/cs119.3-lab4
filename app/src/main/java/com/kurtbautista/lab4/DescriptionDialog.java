package com.kurtbautista.lab4;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DescriptionDialog extends Dialog {

    private FoodReview review;

    public DescriptionDialog(Context c, FoodReview review)
    {
        super(c);
        setTitle("More info");
        this.review = review;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.description_dialog);

        TextView foodName = (TextView)findViewById(R.id.foodNameText2);
        ImageView img = (ImageView)findViewById(R.id.imageView2);
        TextView desc = (TextView)findViewById(R.id.descriptionText);
        TextView commment = (TextView)findViewById(R.id.commentText);

        foodName.setText(review.getName());
        //Picasso.with(getContext()).load(review.getFilename()).fit().into(img);
        Bitmap b = BitmapFactory.decodeFile(review.getFilename());
        img.setImageBitmap(b);
        desc.setText(review.getDescription());
        commment.setText(review.getComment());
    }

}
