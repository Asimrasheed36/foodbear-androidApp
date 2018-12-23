package com.example.zohaibabbasza.foodbear;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

public class Order extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        String za = readFromFile(Order.this);
        char order [] = za.toCharArray();
        for(int i = 0 ; i < za.length() ;i++){
            if(order[i] != ';' ){
                list.add(Character.toString(order[i]));
            }
        }
        System.out.println(list);
        showOrders();
    }
    public void showOrders() {


        Ion.with(this)
                .load("http://192.168.0.104:8000/api/get_list_of_food/")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonArray arrResults = result.getAsJsonArray("data");
                        LinearLayout layout = findViewById(R.id.order_container);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        for(int i = 0 ; i < list.size() ; i++){
                            for(int j = 0 ; j < arrResults.size() ;j++){
                                if(result.getAsJsonArray("data").get(j).getAsJsonObject().get("f_id").getAsString().equals(list.get(i))){
                                    LinearLayout box=(LinearLayout)View.inflate(Order.this,R.layout.dynamic_order,null);
                                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,100
                                    ) ;
                                    box.setLayoutParams(layoutParams1);
                                    layout.addView(box);
                                    TextView f_name = findViewById(R.id.f_name);
                                    TextView f_quantity = findViewById(R.id.f_quantity);
                                    TextView f_Restaurant = findViewById(R.id.f_res);
                                    TextView f_price = findViewById(R.id.f_price);
                                    f_name.setText(result.getAsJsonArray("data").get(j).getAsJsonObject().get("f_name").getAsString());
                                    f_quantity.setText("1");
                                    f_Restaurant.setText(result.getAsJsonArray("data").get(j).getAsJsonObject().get("restaurant_r_id").getAsString());
                                    f_price.setText(result.getAsJsonArray("data").get(j).getAsJsonObject().get("price").getAsString());
                                    System.out.println(f_name.getText().toString());
                                    System.out.println(f_price.getText().toString());
                            }

                            }
                        }
                    }
                });
    }
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("order.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
