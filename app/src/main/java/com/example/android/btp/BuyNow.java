package com.example.android.btp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Sainadh Chilukamari on 7/13/2016.
 */
public class BuyNow extends AppCompatActivity{

    public Toolbar toolbar;
    private TextView quantitytv,priceTextView1,priceTextView2,priceTextView3,priceTextView4,descTextView,addressTextView;
    private Button mbutton,btn_address;
    private Firebase mRootRef;
    private StorageReference storageRef,mountainsRef;
    public String final_address,name,address,pincode,landmark,phone,prices,title,priceonly;
    private EditText edit_name,edit_address,edit_pincode,edit_landmark,edit_phone;
    private int pricenum,quantity=1,pricenum1;
    private ShareActionProvider mShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_activity);

        Firebase.setAndroidContext(this);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://btpnew-1e2cb.appspot.com/Temporders");
        mountainsRef = storageRef.child("temp.jpg");

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        prices = getIntent().getStringExtra("Price");
        priceonly = prices.replaceAll("[^0-9]","");
        pricenum = Integer.parseInt(priceonly);
        title = getIntent().getStringExtra("title");

        getstorage();


        priceTextView1 = (TextView) findViewById(R.id.price);
        priceTextView2 = (TextView) findViewById(R.id.pricebuy);
        priceTextView3 = (TextView) findViewById(R.id.pricebuy2);
        priceTextView4 = (TextView) findViewById(R.id.pricebuy3);
        quantitytv = (TextView) findViewById(R.id.quantity);
        descTextView = (TextView) findViewById(R.id.description);
        addressTextView = (TextView) findViewById(R.id.no_address);
        mbutton = (Button) findViewById(R.id.condata);
        btn_address = (Button) findViewById(R.id.add_address);
        descTextView.setText(title);
        priceTextView1.setText(prices);
        priceTextView2.setText(prices);
        priceTextView3.setText(prices);
        priceTextView4.setText(prices);

        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(BuyNow.this);

                dialog.setContentView(R.layout.address_dialog_layout);
                dialog.setTitle("Enter Shipping Address");


                edit_name = (EditText) dialog.findViewById(R.id.edittext1);
                edit_address = (EditText) dialog.findViewById(R.id.edittext2);
                edit_pincode = (EditText) dialog.findViewById(R.id.edittext3);
                edit_landmark = (EditText) dialog.findViewById(R.id.edittext4);
                edit_phone = (EditText) dialog.findViewById(R.id.edittext5);
                final Button btn_continue = (Button) dialog.findViewById(R.id.two);

                dialog.show();



                btn_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name = edit_name.getText().toString();
                        address = edit_address.getText().toString();
                        pincode = edit_pincode.getText().toString();
                        landmark = edit_landmark.getText().toString();
                        phone = edit_phone.getText().toString();
                        if (check()){
                            StringBuilder sb = new StringBuilder();
                            sb.append(name).append(" ").append(address).append(" ").append(landmark).append(" ").append(pincode).append(" ").append(phone);
                            final_address = sb.toString();
                            addressTextView.setText(final_address);

                            dialog.dismiss();
                        }

                    }
                });


            }
        });

        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressTextView = (TextView) findViewById(R.id.no_address);
                String new1 = addressTextView.getText().toString();
                if (new1.equals("Found no address")) {
                    Toast.makeText(BuyNow.this,"Address not found",Toast.LENGTH_SHORT).show();
                } else if (isNetworkAvailable()) {
                    mRootRef = new Firebase("https://btpnew-1e2cb.firebaseio.com/orders");

                    StringBuilder sf = new StringBuilder();
                    sf.append(title).append(" ").append(quantity).append(" ").append("Rs.").append(pricenum1);
                    String key = sf.toString();
                    key = key.replace("."," ");
                    Firebase childRef = mRootRef.child(key);
                    childRef.push().setValue(final_address);
                    Toast.makeText(BuyNow.this, "Your order is placed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BuyNow.this, "No network connectivity! Placing order through sms.", Toast.LENGTH_SHORT).show();
                    String number_dest = "9407079215";
                    StringBuilder sf = new StringBuilder();
                    sf.append(final_address).append(" ").append(pricenum1).append(" ").append(title);
                    String final_order = sf.toString();
                    SmsManager.getDefault().sendTextMessage(number_dest, null, final_order, null, null);
                }
            }

        });


    }

    public boolean check() {
        boolean success;
        if (name.length() < 1) {
            edit_name.setError("Please enter your name!");
            success = false;
        } else if (address.length() < 1) {
            edit_address.setError("Please enter valid address!");
            success = false;
        } else if (pincode.length() < 1) {
            edit_pincode.setError("Please enter valid pincode!");
            success = false;
        } else if (pincode.length() > 6) {
            edit_pincode.setError("Please enter valid pincode!");
            success = false;
        } else if (pincode.contentEquals("231302")) {
            success = true;
        } else if (pincode.contentEquals("231307")) {
            success = true;
        } else if (pincode.length() == 6) {
            edit_pincode.setError("We deliver only to 231302 & 231307");
            success = false;
        } else if (phone.length() != 10) {
            edit_phone.setError("Please enter valid phone no.!");
            success = false;
        } else {
            success = true;
        } return success;
    }

    public void increment(View view){
        quantity = quantity + 1;
        quantitytv.setText("" +quantity);
        pricenum1 = pricenum*quantity;
        priceTextView3.setText("Rs. " +pricenum1);
        priceTextView4.setText("Rs. " +pricenum1);
        priceTextView1.setText("Rs. " +pricenum1);
    }
    public void decrement(View view){
        quantity = quantity - 1;
        quantitytv.setText("" +quantity);
        pricenum1 = pricenum*quantity;
        priceTextView3.setText("Rs." +pricenum1);
        priceTextView4.setText("Rs." +pricenum1);
        priceTextView1.setText("Rs. " +pricenum1);
    }

    private void getstorage(){
        final long ONE_MEGABYTE = 1024 * 1024;
        mountainsRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                ImageView iv1 = (ImageView) findViewById(R.id.img_description);
                iv1.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
                Intent intent = new Intent(BuyNow.this,Cart.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
