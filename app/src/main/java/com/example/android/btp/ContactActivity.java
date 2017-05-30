package com.example.android.btp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

/**
 * Created by Sainadh Chilukamari on 9/12/2016.
 */
public class ContactActivity extends AppCompatActivity {
    private Firebase mRootRef;
    public Toolbar toolbar;
    public EditText edit_name, edit_query, edit_phone;
    private Button mBtnQuery;
    public String names, queries, phones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us_layout);
        Firebase.setAndroidContext(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        setTitle("Support");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        mBtnQuery = (Button) findViewById(R.id.btnquery);
        edit_name = (EditText) findViewById(R.id.namebox);
        edit_query = (EditText) findViewById(R.id.querybox);
        edit_phone = (EditText) findViewById(R.id.phonebox);

        mBtnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                names = edit_name.getText().toString();
                queries = edit_query.getText().toString();
                phones = edit_phone.getText().toString();
                if(check()) {
                    mRootRef = new Firebase("https://btpnew-1e2cb.firebaseio.com/queries");

                    StringBuilder sf = new StringBuilder();
                    sf.append(names).append(" ").append(phones);
                    String key = sf.toString();
                    Firebase childRef = mRootRef.child(key);
                    childRef.push().setValue(queries);
                    Toast.makeText(ContactActivity.this, "Your query sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        if (names.length() < 1) {
            edit_name.setError("Please enter your name!");
            success = false;
        } else if (queries.length() < 1) {
            edit_query.setError("Please enter valid address!");
            success = false;
        } else if (phones.length() != 10) {
            edit_phone.setError("Please enter valid phone no.!");
            success = false;
        }  else {
            success = true;
        } return success;
    }
}

