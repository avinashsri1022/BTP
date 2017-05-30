package com.example.android.btp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    private NavigationView navigationView;
    private ShareActionProvider mShare;
    public DrawerLayout drawer;
    public ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Trio");


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            case R.id.action_share:
                mShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Download the new Trio app. Very useful for farmers. Visit the link below.");
                mShare.setShareIntent(shareIntent);
                return true;
            case R.id.cart:
                Intent intent = new Intent(MainActivity.this,Cart.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_animalfeed) {
            // Handle the action here

            FragmentAnimalFeedCategories fragment = new FragmentAnimalFeedCategories();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flContent, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            setTitle("Animal Feed");

            Toast.makeText(this, "Cattle Feed", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_home) {
            Intent intent = getIntent();
            finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);

            Toast.makeText(this, "Trio's Home", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_fertilizers) {
            Toast.makeText(this, "Fertilizers coming soon!", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_seeds) {
            FragmentSeedsCategories fragment = new FragmentSeedsCategories();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flContent, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            setTitle("Seeds");

            Toast.makeText(this, "Seeds", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_pesticides) {
            Toast.makeText(this, "Pesticides coming soon!", Toast.LENGTH_SHORT).show();

        }  else if (id == R.id.nav_support) {
            Toast.makeText(this, "Support", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ContactActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_account) {
            Toast.makeText(this, "Your account", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,UserAccount.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View view) {
        Intent intent;
        FragmentTransaction transaction;
        ImageView iv;
        TextView tv;
        Bitmap bm;
        switch (view.getId()) {
            case R.id.Button01:
                FragmentCategories fragment = new FragmentCategories();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContent, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.Button02:
                FragmentCategories fragment1 = new FragmentCategories();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContent, fragment1);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.Button03:
                FragmentSeeds fragment2 = new FragmentSeeds();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContent, fragment2);
                transaction.addToBackStack(null);
                transaction.commit();
                navigationView.getMenu().getItem(3).setChecked(true);
                setTitle("Seeds");
                break;
            case R.id.Button04:
                FragmentCattleFeed fragment3 = new FragmentCattleFeed();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContent, fragment3);
                transaction.addToBackStack(null);
                transaction.commit();
                navigationView.getMenu().getItem(1).setChecked(true);
                setTitle("Cattle Feed");
                break;
            case R.id.categories_layout:
                FragmentCategories fragment4 = new FragmentCategories();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContent, fragment4);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.brands_layout:
                FragmentCategories fragment5 = new FragmentCategories();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContent, fragment5);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.seeds_product1:
                iv = (ImageView)findViewById(R.id.product1_image);
                tv = (TextView)findViewById(R.id.product1_text);
                bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
                intent = new Intent(this, DetailsActivity.class);

                intent.putExtra("title", tv.getText());
                intent.putExtra("image", bm);

                //Start details activity
                startActivity(intent);
                break;
            case R.id.seeds_product2:
                iv = (ImageView)findViewById(R.id.product2_image);
                tv = (TextView)findViewById(R.id.product2_text);
                bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
                intent = new Intent(this, DetailsActivity.class);

                intent.putExtra("title", tv.getText());
                intent.putExtra("image", bm);

                //Start details activity
                startActivity(intent);
                break;
            case R.id.seeds_product3:
                iv = (ImageView)findViewById(R.id.product3_image);
                tv = (TextView)findViewById(R.id.product3_text);
                bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
                intent = new Intent(this, DetailsActivity.class);

                intent.putExtra("title", tv.getText());
                intent.putExtra("image", bm);

                //Start details activity
                startActivity(intent);
                break;
            case R.id.seeds_product4:
                iv = (ImageView)findViewById(R.id.product4_image);
                tv = (TextView)findViewById(R.id.product4_text);
                bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
                intent = new Intent(this, DetailsActivity.class);

                intent.putExtra("title", tv.getText());
                intent.putExtra("image", bm);

                //Start details activity
                startActivity(intent);
                break;
            case R.id.fertil_product1:
                iv = (ImageView)findViewById(R.id.fertil_product1_image);
                tv = (TextView)findViewById(R.id.fertil_product1_text);
                bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
                intent = new Intent(this, DetailsActivity.class);

                intent.putExtra("title", tv.getText());
                intent.putExtra("image", bm);

                //Start details activity
                startActivity(intent);
                break;
            case R.id.fertil_product2:
                iv = (ImageView)findViewById(R.id.fertil_product2_image);
                tv = (TextView)findViewById(R.id.fertil_product2_text);
                bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
                intent = new Intent(this, DetailsActivity.class);

                intent.putExtra("title", tv.getText());
                intent.putExtra("image", bm);

                //Start details activity
                startActivity(intent);
                break;
            case R.id.fertil_product3:
                iv = (ImageView)findViewById(R.id.fertil_product3_image);
                tv = (TextView)findViewById(R.id.fertil_product3_text);
                bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
                intent = new Intent(this, DetailsActivity.class);

                intent.putExtra("title", tv.getText());
                intent.putExtra("image", bm);

                //Start details activity
                startActivity(intent);
                break;
            case R.id.fertil_product4:
                iv = (ImageView)findViewById(R.id.fertil_product4_image);
                tv = (TextView)findViewById(R.id.fertil_product4_text);
                bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
                intent = new Intent(this, DetailsActivity.class);

                intent.putExtra("title", tv.getText());
                intent.putExtra("image", bm);

                //Start details activity
                startActivity(intent);
                break;
            case R.id.hate:
                intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.love:
                intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                break;


        }
    }


}
