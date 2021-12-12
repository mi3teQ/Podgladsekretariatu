package com.example.podgladsekretariatu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText linkpodaj, linkpodaj2;
    Button szukaj;
    TextView tekst;
    String cos;
    URL linkacz;
    ListView lista;
    private String[] lv_arr = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkpodaj = (EditText) findViewById(R.id.edittext_podajlink);
        szukaj = (Button) findViewById(R.id.btn_zatwierdz);
        tekst = (TextView) findViewById(R.id.tekst);
        lista = (ListView) findViewById(R.id.lista);

        //ArrayList<String> arrayList = new ArrayList<String>();
        //arrayList.add("JAVA");
        //arrayList.add("ANDROID");
        //arrayList.add("C Language");
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, NextLine);
        //lista.setAdapter(arrayAdapter);

        szukaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable(){




                    public void run(){








                        ArrayList<String> urls=new ArrayList<String>(); //to read each line
                        //TextView t; //to show the result, please declare and find it inside onCreate()



                        try {
                            // Create a URL for the desired page
                            cos = linkpodaj.getText().toString();
                            //linkpodaj2.setText(cos);

                            URL url = new URL(cos); //My text file locationFirst open the connection
                           HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                            conn.setConnectTimeout(60000); // timing out in a minute

                           BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

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

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, urls);
                                lista.setAdapter(arrayAdapter);
                            }

                        });

                        try {
                            File csvfile = new File(Environment.getExternalStorageDirectory() + "/csvfile.csv");
                            CSVReader reader = new CSVReader(new FileReader(csvfile.getAbsolutePath()));
                            String[] nextLine;
                            while ((nextLine = reader.readNext()) != null) {
                                // nextLine[] is an array of values from the line
                                System.out.println(nextLine[0] + nextLine[1]);
                            }



                        }catch (IOException e) {

                        }
                    }
                }).start();
            }
        });
    }


}