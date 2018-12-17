package com.example.zohaibabbasza.foodbear;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void signup(View v) {
        Intent in = new Intent();
        in.setClass(this, Signup.class);
        startActivity(in);
    }
    public void mainScreen(View v)
    {
            Intent in = new Intent();
            in.setClass(this, MainScreen.class);
            startActivity(in);

    }

    public void makeToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    public void getData(final View view){

        Ion.with(this)
                .load("http://192.168.0.104:8000/hello/")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        TextView Rview ;
                        Rview = findViewById(R.id.new_user);
                        if(result == null){
                            makeToast("error");
                        }
                        else{
                            Rview.setText(result.toString());
                        }
                    }
                });
    }
    public void verify(View v){
 //       EditText e;
   ///     e.findViewById(R.id.email);
      //  EditText p ;
       // p.findViewById(R.id.password);
        JsonObject json = new JsonObject();
        Ion.with(this)
                .load("http://192.168.0.104:8000/api/get_type_of_foods/")
                //.setBodyParameter("username", "zohaib")
                //.setBodyParameter("password", "za123456")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        TextView Rview ;
                        Rview = findViewById(R.id.new_user);

                        if(result == null){
                            makeToast("Wrong username or password");
                        }
                        else{

                            makeToast("Success");
                            Rview.setText(result.toString());
                            Intent in = new Intent();
                            in.setClass(login.this, MainScreen.class);
                            startActivity(in);
                        }
                    }
                });

    }


}
