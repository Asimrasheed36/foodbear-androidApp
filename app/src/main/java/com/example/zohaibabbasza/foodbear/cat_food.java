package com.example.zohaibabbasza.foodbear;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class cat_food extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_food);
        Intent intent=this.getIntent();
        String id=intent.getStringExtra("cat_id");
        TextView tv = findViewById(R.id.id);
        tv.setText(id);
    }
}
