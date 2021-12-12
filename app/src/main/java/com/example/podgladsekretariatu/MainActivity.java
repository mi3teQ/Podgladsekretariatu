package com.example.podgladsekretariatu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText linkpodaj;
    Button szukaj;
    TextView tekst;
    URL linkacz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkpodaj = (EditText) findViewById(R.id.edittext_podajlink);
        szukaj = (Button) findViewById(R.id.btn_zatwierdz);
        tekst = (TextView) findViewById(R.id.tekst);


        szukaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable(){

                    public void run(){


                        ArrayList<String> urls=new ArrayList<String>(); //to read each line
                        //TextView t; //to show the result, please declare and find it inside onCreate()



                        try {
                            // Create a URL for the desired page

                            URL url = new URL("https://pastebin.com/raw/gum3VcR8"); //My text file location
                            //First open the connection
                            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                            conn.setConnectTimeout(60000); // timing out in a minute

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                            //t=(TextView)findViewById(R.id.TextView1); // ideally do this in onCreate()
                            String str;
                            while ((str = in.readLine()) != null) {
                                urls.add(str);
                            }
                            in.close();
                        } catch (Exception e) {
                            Log.d("MyTag",e.toString());
                        }

                        //since we are in background thread, to post results we have to go back to ui thread. do the following for that

                        MainActivity.this.runOnUiThread(new Runnable(){
                            public void run(){
                                tekst.setText(urls.get(0)); // My TextFile has 3 lines
                            }
                        });

                    }
                }).start();
            }
        });
    }


}