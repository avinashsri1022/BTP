package com.example.android.btp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Random;


public class DetailsActivity extends AppCompatActivity {

    public Toolbar toolbar;
    private String title;
    public TextView price,text_rating;
    public RatingBar edit_rating1,edit_rating2;
    private ImageView imageView;
    private Firebase mRootRef;
    private ShareActionProvider mShare;
    private StorageReference storageRef,mountainsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Firebase.setAndroidContext(this);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://btpnew-1e2cb.appspot.com/Temporders");
        mountainsRef = storageRef.child("temp.jpg");

        title = getIntent().getStringExtra("title");
        Bitmap bitmap = getIntent().getParcelableExtra("image");

        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(title);

        imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);

        price = (TextView) findViewById(R.id.price_tv);
        text_rating = (TextView) findViewById(R.id.rating);
        edit_rating1 = (RatingBar) findViewById(R.id.ratingBar);
        edit_rating2 = (RatingBar) findViewById(R.id.ratingBar2);

        randomfunc();
        storage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void storage(){
        // Locate the image in res > drawable-hdpi
        Bitmap bitmap2 = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(image);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_share:
                mShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Download the new Trio app. Very useful for farmers. Visit the link below.");
                mShare.setShareIntent(shareIntent);
                return true;
            case R.id.cart:
                Intent intent = new Intent(DetailsActivity.this,Cart.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void randomfunc(){
        Random randomint = new Random();
        float randominteger = randomint.nextInt(5);
        double x = Math.random();
        float finalint = randominteger + (float)x;
        text_rating.setText(String.valueOf(finalint));
        edit_rating1.setRating(finalint);
        edit_rating2.setRating(finalint);
    }

    public void clicked(View view) {
        switch (view.getId()){
            case R.id.share:
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,title);
                Intent.createChooser(share,"Share via");
                startActivity(share);
                break;
            case R.id.buy_now:
                Intent intent = new Intent(this, BuyNow.class);
                intent.putExtra("Price", price.getText().toString());
                intent.putExtra("title", title);
                startActivity(intent);
                break;
            case R.id.cart_now:
                Toast.makeText(this,"Product added to cart",Toast.LENGTH_SHORT).show();
                mRootRef = new Firebase("https://btpnew-1e2cb.firebaseio.com/temporders");
                Firebase childRef = mRootRef.child(title);
                childRef.setValue(price.getText().toString());
                break;
            case R.id.review:
                break;


        }
    }
}
