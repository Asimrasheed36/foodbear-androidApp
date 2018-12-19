package com.example.zohaibabbasza.foodbear;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

public class FoodScreen extends AppCompatActivity {


    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public String res_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_screen);
        Intent intent=this.getIntent();
        String r_id=intent.getStringExtra("r_id");
        String img_url = intent.getStringExtra("r_image");
        System.out.println("id "+ r_id);
        System.out.println("url " +img_url);
        ImageView iv =findViewById(R.id.r_image);
        Picasso.get()
                .load(img_url).placeholder(R.drawable.za)
                .fit()
                .into(iv);

        res_url="http://192.168.0.104:8000/api/get_food_filter/"+r_id+"/";

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(), "Tab 1");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    public void makeToast(String msg) {

        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    }

