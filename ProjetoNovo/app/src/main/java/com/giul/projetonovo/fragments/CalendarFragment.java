package com.giul.projetonovo.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.giul.projetonovo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalendarFragment extends Fragment {


    @Nullable


 private TextView t1_temp;
    private TextView t2_city;
    private TextView t4_date;
    boolean doubleBackToExitPressedOnce = false;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




       View view =  inflater.inflate(R.layout.fragment_calendar, container, false);
        find_weather();

        t1_temp = (TextView) view.findViewById(R.id.temp);
        t2_city = (TextView) view.findViewById(R.id.city);
        t4_date = (TextView) view.findViewById(R.id.t4_temp);


        find_weather();
        return view;



    }
    public void find_weather(){
 String url = "http://api.openweathermap.org/data/2.5/weather?q=Joao Monlevade,br&appid=1873a38d23a1ae92f2437168ae23a17c&units=imperial";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try {
                   JSONObject main_object = response.getJSONObject("main");
                   JSONArray array = response.getJSONArray("weather");
                   JSONObject object = array.getJSONObject(0);
                   String temp = String.valueOf(main_object.getDouble("temp"));
                   String description = object.getString("description");
                   String city = response.getString("name");

                   t1_temp.setText(temp);
                   t2_city.setText(city);
                  // t3_description.setText(description);

                   Calendar calendar = Calendar.getInstance();
                   SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MM,DD");
                   String formatted_date = sdf.format(calendar.getTime());

                   t4_date.setText(formatted_date);

                   double temp_int= Double.parseDouble(temp);
                   double centi = (temp_int - 32) * 5/ 9;
                   centi = Math.round(centi);
                   int i = (int) centi;
                   t1_temp.setText(String.valueOf(i));







               }catch (JSONException e)
               {
                   e.printStackTrace();
               }
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jor);

    }
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            voltar();
        } else {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(getContext(), "Pressione novamente para sair", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void voltar() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);


    }
}
