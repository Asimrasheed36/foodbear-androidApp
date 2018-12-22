package com.example.zohaibabbasza.foodbear;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

public class FoodScreen extends AppCompatActivity {


    public String res_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_screen);
        Intent intent=this.getIntent();
        String r_id=intent.getStringExtra("r_id");
        String img_url = intent.getStringExtra("r_image");
        ImageView iv =findViewById(R.id.r_image);
        Picasso.get()
                .load(img_url).placeholder(R.drawable.za)
                .fit()
                .into(iv);

        res_url="http://192.168.0.115:8000/api/get_food_filter/"+r_id+"/";


        makeFood();
    }
    public void makeToast(String msg) {

        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    public void makeFood(){

        System.out.println(res_url);
        Ion.with(this)
                .load(res_url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        LinearLayout layout = findViewById(R.id.food_container);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        if (result == null) {
                            makeToast("result null");
                        } else {

                            for(int i= 0 ; i < result.size() ; i++){
                                LinearLayout box=(LinearLayout)View.inflate(FoodScreen.this,R.layout.food_list,null);
                                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,280
                                ) ;
                                box.setLayoutParams(layoutParams1);
                                box.setId(result.get(i).getAsJsonObject().get("f_id").getAsInt());
                                layout.addView(box);
                                TextView textName = box.findViewById(R.id.food_name);
                                TextView textprice = box.findViewById(R.id.food_price);
                                textName.setText(result.get(i).getAsJsonObject().get("f_name").getAsString());
                                textprice.setText("Rs."+result.get(i).getAsJsonObject().get("price").getAsString());
                            }

                        }
                    }
                });
    }

    }

