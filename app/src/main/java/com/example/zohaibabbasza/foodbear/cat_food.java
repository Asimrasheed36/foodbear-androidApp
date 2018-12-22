package com.example.zohaibabbasza.foodbear;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import org.w3c.dom.Text;

public class cat_food extends AppCompatActivity {
    public String url_cat_res ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_food);
        Intent intent=this.getIntent();
        String c_id=intent.getStringExtra("cat_id");
        String img_url = intent.getStringExtra("cat_image");
        //System.out.println(img_url);

        ImageView iv =findViewById(R.id.url_image);
        Picasso.get()
                .load(img_url).placeholder(R.drawable.za)
                .fit()
                .into(iv);
        url_cat_res ="http://192.168.0.115:8000/api/get_filter_restaurants/"+c_id+"/";
        RestaurantCatView();

    }
    public void makeToast(String msg) {

        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    public void RestaurantCatView(){
        System.out.println(url_cat_res);
        Ion.with(this)
                .load(url_cat_res)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        LinearLayout layout = findViewById(R.id.res_cat_container);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                        if (result == null) {
                            makeToast("result null");
                        } else {

                            for(int i= 0 ; i < result.size() ; i++){
                                LinearLayout box=(LinearLayout)View.inflate(cat_food.this,R.layout.cat_res_dynamic,null);
                                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,300
                                ) ;
                                box.setLayoutParams(layoutParams1);
                                box.setId(result.get(i).getAsJsonObject().get("r_id").getAsInt());
                                layout.addView(box);
                                TextView textName = box.findViewById(R.id.name_cat_res);
                                RatingBar rbar = box.findViewById(R.id.cat_res_ratingBar);
                                rbar.setRating(result.get(i).getAsJsonObject().get("rating").getAsFloat());
                                TextView textLocation = box.findViewById(R.id.name_cat_location);
                                String url = result.get(i).getAsJsonObject().get("r_image").getAsString();
                                //System.out.println("url"+" " + url);
                                ImageView iview = (ImageView)box.findViewById(R.id.image_cat_res);
                                //System.out.println("Image"+ " " +iview.toString());
                                Picasso.get()
                                        .load(url).placeholder(R.drawable.za)
                                        .fit()
                                        .into(iview);
                                textName.setText(result.get(i).getAsJsonObject().get("r_name").getAsString());
                                textLocation.setText(result.get(i).getAsJsonObject().get("r_location").getAsString());
                            }

                        }
                    }
                });
    }

}
