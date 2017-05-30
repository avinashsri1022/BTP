package com.example.android.btp;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by Sainadh Chilukamari on 9/22/2016.
 */
public class Cart extends AppCompatActivity {
    public Toolbar toolbar;
    private TextView tv_1,tv_2,tv_3,tv_4,tv_cost1,tv_cost2,tv_cost3,tv_cost4,addressTextView;
    private EditText edit_name,edit_address,edit_pincode,edit_landmark,edit_phone;
    private Button btn_address,mbutton;
    private String final_address,name,address,pincode,landmark,phone;
    private Firebase mRootRef,mRootRef2;
    private ArrayList<String> mProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        Firebase.setAndroidContext(this);
        mRootRef = new Firebase("https://btpnew-1e2cb.firebaseio.com/temporders");


        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        btn_address = (Button) findViewById(R.id.add_address);
        mbutton = (Button) findViewById(R.id.condata);
        addressTextView = (TextView) findViewById(R.id.no_address);

        final ListView listView = (ListView) findViewById(R.id.listView);

        // Create a new Adapter
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mProducts);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // Use Firebase to populate the list.
        Firebase.setAndroidContext(this);

        mRootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                String keyvalue = key + " " + value;
                mProducts.add(keyvalue);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Cart.this);

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
                        if (check()) {
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
                    Toast.makeText(Cart.this,"Address not found",Toast.LENGTH_SHORT).show();
                } else if (isNetworkAvailable()) {
                    avinash();
                    Firebase firebase = new Firebase("https://btpnew-1e2cb.firebaseio.com");
                    firebase.child("temporders").removeValue();
                } else {
                    Toast.makeText(Cart.this, "No network connectivity! Placing order through sms.", Toast.LENGTH_SHORT).show();
                    String number_dest = "9407079215";
                    StringBuilder sf = new StringBuilder();
                    sf.append(final_address);
                    String final_order = sf.toString();
                    SmsManager.getDefault().sendTextMessage(number_dest, null, final_order, null, null);
                }
            }

        });

    }
    public void avinash(){
        mRootRef2 = new Firebase("https://btpnew-1e2cb.firebaseio.com/orders");
        StringBuilder sf = new StringBuilder();
        for(String products : mProducts){
            sf.append(products).append(" ");
        }
        String key = sf.toString();
        key = key.replace("."," ");
        Firebase childRef = mRootRef2.child(key);
        childRef.push().setValue(final_address);
        Toast.makeText(Cart.this, "Your order is placed", Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
}
