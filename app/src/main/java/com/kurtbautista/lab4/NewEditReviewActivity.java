package com.kurtbautista.lab4;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class NewEditReviewActivity extends AppCompatActivity {

    private File outputFile;
    private File thumbNailFile;
    private ArrayList<FoodReview> reviews;

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
            RadioButton[] rds = new RadioButton[] {(RadioButton)findViewById(R.id.radioButton), (RadioButton)findViewById(R.id.radioButton2), (RadioButton)findViewById(R.id.radioButton3),
                    (RadioButton)findViewById(R.id.radioButton4), (RadioButton)findViewById(R.id.radioButton5)};

            name.setText(review.getName());
            user.setText(review.getUser());
            price.setText(String.valueOf(review.getPrice()));
            desc.setText(review.getDescription());
            comment.setText(review.getComment());
            rds[review.getRating() - 1].setChecked(true);

            Bitmap b = BitmapFactory.decodeFile(review.getFilename());
            img.setImageBitmap(b);
        }
        else {
            RadioButton r = (RadioButton)findViewById(R.id.radioButton);
            r.setChecked(true);
        }
    }

    //region copy-paste stuff

    public void newImage(View v)
    {
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsolutePath() + "/CameraTest/");
        directory.mkdirs();

        // unique filename based on the time
        String name = String.valueOf(System.currentTimeMillis());
        outputFile = new File(directory, name+".jpg");
        thumbNailFile = new File(directory, name+"_tn.jpg");

        Uri outputFileUri = Uri.fromFile(outputFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK)
        {
            processPicture();
        }
    }


    class PictureProcessThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                // this is potentially time consuming and should be done in a thread

                // rescale the picture from the full size output file which is assumed to now be
                // in outputFile

                // create rescale 640
                // create thumbnail rescale 50

                ImageUtils.resizeSavedBitmap(outputFile.getAbsolutePath(), 110, thumbNailFile.getAbsolutePath());
                ImageUtils.resizeSavedBitmap(outputFile.getAbsolutePath(), 145, outputFile.getAbsolutePath());

                // UI updates must be done via the UI thread NOT here, this will
                // cause the Runnable to occur in the UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        updateImageView();
                    }
                });

            }
            catch(final Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public void processPicture()
    {
        new PictureProcessThread().start();

    }

    private void updateImageView()
    {
        ImageButton img = (ImageButton)findViewById(R.id.changeImageButton);
        Bitmap b = BitmapFactory.decodeFile(outputFile.getAbsolutePath());
        img.setImageBitmap(b);
        //Picasso.with(this).load(outputFile.getAbsolutePath()).fit().into(img);
    }

    //endregion

    public void saveReview(View v) {
        final EditText name = (EditText) findViewById(R.id.foodName);
        final EditText user = (EditText) findViewById(R.id.userName);
        final EditText price = (EditText) findViewById(R.id.foodPrice);
        final EditText desc = (EditText) findViewById(R.id.description);
        final EditText comment = (EditText) findViewById(R.id.comment);
        final ImageButton img = (ImageButton) findViewById(R.id.changeImageButton);
        final RadioGroup rating = (RadioGroup) findViewById(R.id.ratingGroup);

        View rg = rating.findViewById(rating.getCheckedRadioButtonId());
        final int index = rating.indexOfChild(rg) + 1;

        if (getTitle().equals("Edit review")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to edit this review?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent data = new Intent();
                            data.putExtra("review", getIntent().getIntExtra("pos", -1));
                            data.putExtra("name", name.getText().toString());
                            data.putExtra("user", user.getText().toString());
                            data.putExtra("price", Double.valueOf(price.getText().toString()));
                            data.putExtra("rating", index);
                            data.putExtra("desc", desc.getText().toString());
                            data.putExtra("comment", comment.getText().toString());
                            try {
                                data.putExtra("filename", outputFile.getAbsolutePath());
                                data.putExtra("thumbnail", thumbNailFile.getAbsolutePath());
                            }
                            catch (NullPointerException e)
                            {
                                FoodReview review = (FoodReview) getIntent().getSerializableExtra("review");
                                data.putExtra("filename", review.getFilename());
                                data.putExtra("thumbnail", review.getThumbnail());
                            }
                            setResult(RESULT_OK, data);
                            finish();
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            try {
                FoodReview review = new FoodReview(name.getText().toString(), user.getText().toString(), Double.valueOf(price.getText().toString()), desc.getText().toString(), comment.getText().toString(), index, outputFile.getAbsolutePath());
                review.setThumbnail(thumbNailFile.getAbsolutePath());
                Intent data = new Intent();
                data.putExtra("review", review);
                setResult(RESULT_OK, data);
                finish();
            }
            catch (NumberFormatException e) {
                Toast.makeText(this, "All fields needed", Toast.LENGTH_SHORT).show();
            }
            catch (NullPointerException e) {
                Toast.makeText(this, "Please take a picture", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
