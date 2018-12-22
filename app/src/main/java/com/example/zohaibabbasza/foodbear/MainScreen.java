package com.example.zohaibabbasza.foodbear;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Callback;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public int a;
    public String url_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        categoryView();
        ResturantView();
        getRestaurantCount();

    }

    public void makeToast(String msg) {

        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void categoryView(){


        Ion.with(this)
                .load("http://192.168.0.115:8000/api/get_type_of_foods/")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(result == null){
                            makeToast("result null");
                        }
                        else{
                            JsonArray arrResults = result.getAsJsonArray("data");


                            LinearLayout layout = findViewById(R.id.image_container);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);



                            for (int i = 0; i < arrResults.size(); i++) {
                                LinearLayout box=(LinearLayout)View.inflate(MainScreen.this,R.layout.dynamic_content,null);
                                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,750
                                ) ;
                                box.setLayoutParams(layoutParams1);

                                box.setId(result.getAsJsonArray("data").get(i).getAsJsonObject().get("cat_id").getAsInt());

                                layout.addView(box);
                                TextView cat_id = box.findViewById(R.id.cat_id);

                                ImageView iview = (ImageView)box.findViewById(R.id.food_image);
                                //iview.setImageResource(R.drawable.za);
                                String url = result.getAsJsonArray("data").get(i).getAsJsonObject().get("cat_image").getAsString();
                                Picasso.get()
                                        .load(url)
                                        .fit()
                                        .into(iview);
                                TextView tv = box.findViewById(R.id.food_text);
                                tv.setText(result.getAsJsonArray("data").get(i).getAsJsonObject().get("cat_name").getAsString());
                                cat_id.setText(result.getAsJsonArray("data").get(i).getAsJsonObject().get("cat_id").getAsString());

                            }

                        }

                    }

                });


    }
    public void ResturantView(){
        Ion.with(this)
                .load("http://192.168.0.115:8000/api/get_list_of_restaurants/")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        LinearLayout layout = findViewById(R.id.resturant_container);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        if (result == null) {
                            makeToast("result null");
                        } else {
                            JsonArray arrResults = result.getAsJsonArray("data");
                            for(int i= 0 ; i < arrResults.size() ; i++){
                                LinearLayout box=(LinearLayout)View.inflate(MainScreen.this,R.layout.dynamic_resturants,null);
                                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,800
                                ) ;
                                box.setLayoutParams(layoutParams1);
                                box.setId(result.getAsJsonArray("data").get(i).getAsJsonObject().get("r_id").getAsInt());
                                layout.addView(box);
                                TextView textName = box.findViewById(R.id.res_name);
                                RatingBar rbar = box.findViewById(R.id.ratingBar);
                                rbar.setRating(result.getAsJsonArray("data").get(i).getAsJsonObject().get("rating").getAsFloat());
                                TextView textLocation = box.findViewById(R.id.location);
                                String url = result.getAsJsonArray("data").get(i).getAsJsonObject().get("r_image").getAsString();
                               // System.out.println("url"+" " + url);
                                ImageView iview = (ImageView)box.findViewById(R.id.res_image);
                                //System.out.println("Image"+ " " +iview.toString());
                                Picasso.get()
                                        .load(url).placeholder(R.drawable.za)
                                        .fit()
                                        .into(iview);
                                textName.setText(result.getAsJsonArray("data").get(i).getAsJsonObject().get("r_name").getAsString());
                                textLocation.setText(result.getAsJsonArray("data").get(i).getAsJsonObject().get("r_location").getAsString());
                            }

                        }
                    }
                });
    }
    void getRestaurantCount() {
        Ion.with(this)
                .load("http://192.168.0.115:8000/api/get_restaurant_count/")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        TextView tv = findViewById(R.id.res_count);
                        tv.setText("("+result.get(0).getAsJsonObject().get("data").getAsString()+")");
                        //System.out.println(result.get(0).getAsJsonObject().get("data").getAsString());
                    }
                });
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
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void foodScreen(View v)
    {
        Intent in=new Intent();
        in.setClass(this,FoodScreen.class);
        startActivity(in);
    }
    public void cat_foodScreen(View v){
         a =v.getId();

        String b =  Integer.toString(a);
        Ion.with(this)
                .load("http://192.168.0.115:8000/api/get_type_of_foods/")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonArray arrResults = result.getAsJsonArray("data");

                        for(int i = 0 ; i < arrResults.size() ; i++){
                            if(result.getAsJsonArray("data").get(i).getAsJsonObject().get("cat_id").getAsString().equals(Integer.toString(a))){
                                url_image = result.getAsJsonArray("data").get(i).getAsJsonObject().get("cat_image").getAsString();
                                System.out.println("link: "+url_image);
                                Intent in = new Intent();
                                in.setClass(MainScreen.this,cat_food.class);
                                in.putExtra("cat_id",Integer.toString(a));
                                in.putExtra("cat_image",url_image);
                                startActivity(in);

                            }
                        }
                    }
                });



    }
    public void ResturantFoodScreen(View v){
        a =v.getId();

        Ion.with(this)
                .load("http://192.168.0.115:8000/api/get_list_of_restaurants/")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonArray arrResults = result.getAsJsonArray("data");

                        for(int i = 0 ; i < arrResults.size() ; i++){
                            if(result.getAsJsonArray("data").get(i).getAsJsonObject().get("r_id").getAsString().equals(Integer.toString(a))){
                                url_image = result.getAsJsonArray("data").get(i).getAsJsonObject().get("r_image").getAsString();
                                System.out.println("link: "+url_image);
                                Intent in = new Intent();
                                in.setClass(MainScreen.this,FoodScreen.class);
                                in.putExtra("r_id",Integer.toString(a));
                                in.putExtra("r_image",url_image);
                                startActivity(in);

                            }
                        }
                    }
                });
    }
}
